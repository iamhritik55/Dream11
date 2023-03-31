package com.Dream11.services.gamecontroller;

import com.Dream11.DTO.response.LeaderboardResponseDTO;
import com.Dream11.services.MatchService;
import com.Dream11.services.MatchStatsService;
import com.Dream11.services.MatchUserService;
import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.PlayerStats;
import com.Dream11.utility.ContextUtility;
import com.Dream11.utility.PlayerStatsUtility;
import com.Dream11.utility.ResultOnBall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Dream11.utility.ApplicationUtils.*;

@Service
public class CricketControllerService implements GameControllerService {
    @Autowired
    private MatchService matchService;
    @Autowired
    private ContextUtility contextUtility;
    @Autowired
    private CricketMatchContext matchContext;
    @Autowired
    private CricketInningContext inningContext;
    @Autowired
    private CricketUtility cricketUtility;
    @Autowired
    private MatchStatsService matchStatsService;
    @Autowired
    private MatchUserService matchUserService;

    @Override
    public List<LeaderboardResponseDTO> startMatch(String matchId) throws Exception {

        //Fetching matchContext and validating and toss
        matchContext = contextUtility.createAndValidateCricketContext(matchId);

        //playing 2 innings
        playInning();
        playInning();

        //Update match completed status
        matchService.matchCompleted(matchId);

        //Store match data in DB
        matchStatsService.storeAllMatchData(matchContext, inningContext);

        //Update MatchUserStats (credits, team points etc.)
        return matchUserService.updateMatchUserStats(matchContext, inningContext);

    }

    private void playInning() throws Exception {
        inningContext = contextUtility.fetchInningContext(matchContext, inningContext);

        Player playerOnStrike = cricketUtility.findPlayerOnStrike(inningContext.getBattingPlayerList());
        Player playerOffStrike = cricketUtility.findPlayerOffStrike(inningContext.getBattingPlayerList());
        Player bowler = cricketUtility.findPlayerBowling(inningContext.getBowlingPlayerList());
        int wickets = 0;
        int bowlingTeamRuns = 0;
        int battingTeamRuns = 0;
        PlayerStats playerToUpdate;
        if (matchContext.isSecondInning()) {
            bowlingTeamRuns = cricketUtility.getBowlingTeamRuns(matchContext, inningContext);
        }
        loop:
        for (int ball = 1; ball <= TOTAL_BALLS; ball++) {
            //At the start of an over
            if (((ball - 1) % 6 == 0) && ball != 1) {
                //Changing strike
                cricketUtility.swapOnStrikeOffStrike(inningContext.getBattingPlayerList());
                playerOnStrike = cricketUtility.findPlayerOnStrike(inningContext.getBattingPlayerList());
                playerOffStrike = cricketUtility.findPlayerOffStrike(inningContext.getBattingPlayerList());


                //Changing bowler
                cricketUtility.nextBowler(inningContext.getBowlingPlayerList());
                bowler = cricketUtility.findPlayerBowling(inningContext.getBowlingPlayerList());
            }

            int resultOnBall = ResultOnBall.resultOnBall(playerOnStrike.getTitle(), bowler.getTitle());
            switch (resultOnBall) {
                case WICKET -> {
                    wickets++;
                    if (wickets == 10) {
                        break loop;
                    }
                    playerToUpdate = cricketUtility.fetchPlayerStats(inningContext.getPlayerStatsList(), bowler);
                    PlayerStatsUtility.addWicket(playerToUpdate);
                    PlayerStatsUtility.addPoints(playerToUpdate, FIFTEEN_POINTS);

                    cricketUtility.nextBatsman(inningContext.getBattingPlayerList());
                    playerOnStrike = cricketUtility.findPlayerOnStrike(inningContext.getBattingPlayerList());
                }
                case ZERO_RUNS, TWO_RUNS -> {
                    playerToUpdate = cricketUtility.fetchPlayerStats(inningContext.getPlayerStatsList(), playerOnStrike);
                    PlayerStatsUtility.addRuns(playerToUpdate, resultOnBall);
                    battingTeamRuns += resultOnBall;
                    if (matchContext.isSecondInning()) {
                        if (battingTeamRuns > bowlingTeamRuns) {
                            break loop;
                        }
                    }
                }
                case ONE_RUN, THREE_RUNS -> {
                    playerToUpdate = cricketUtility.fetchPlayerStats(inningContext.getPlayerStatsList(), playerOnStrike);
                    PlayerStatsUtility.addRuns(playerToUpdate, resultOnBall);
                    battingTeamRuns += resultOnBall;
                    Player temp = playerOnStrike;
                    playerOnStrike = playerOffStrike;
                    playerOffStrike = temp;
                    if (matchContext.isSecondInning()) {
                        if (battingTeamRuns > bowlingTeamRuns) {
                            break loop;
                        }
                    }
                }
                case FOUR_RUNS -> {
                    playerToUpdate = cricketUtility.fetchPlayerStats(inningContext.getPlayerStatsList(), playerOnStrike);
                    PlayerStatsUtility.addRuns(playerToUpdate, resultOnBall);
                    PlayerStatsUtility.addFour(playerToUpdate);
                    PlayerStatsUtility.addPoints(playerToUpdate, FIVE_POINTS);
                    battingTeamRuns += resultOnBall;
                    if (matchContext.isSecondInning()) {
                        if (battingTeamRuns > bowlingTeamRuns) {
                            break loop;
                        }
                    }
                }
                case SIX_RUNS -> {
                    playerToUpdate = cricketUtility.fetchPlayerStats(inningContext.getPlayerStatsList(), playerOnStrike);
                    PlayerStatsUtility.addRuns(playerToUpdate, resultOnBall);
                    PlayerStatsUtility.addSix(playerToUpdate);
                    PlayerStatsUtility.addPoints(playerToUpdate, TEN_POINTS);
                    battingTeamRuns += resultOnBall;
                    if (matchContext.isSecondInning()) {
                        if (battingTeamRuns > bowlingTeamRuns) {
                            break loop;
                        }
                    }
                }
            }

        }

        //Updating runs of batting team
        matchContext = cricketUtility.addBattingTeamRuns(matchContext, inningContext, battingTeamRuns);
        //If it was first inning, then I have to swap
        if (!matchContext.isSecondInning()) {
            matchContext.setSecondInning(true);
        }


    }
}
