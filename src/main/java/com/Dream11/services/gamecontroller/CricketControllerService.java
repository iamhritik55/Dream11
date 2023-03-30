package com.Dream11.services.gamecontroller;
import com.Dream11.DTO.response.LeaderboardResponseDTO;
import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.Player;
import com.Dream11.services.MatchService;
import com.Dream11.services.MatchStatsService;
import com.Dream11.services.MatchUserService;
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
        matchContext = matchContext.fetchCricketContext(matchId);

        //playing 2 innings
        playInning();
        playInning();

        //Update match completed status
        matchService.matchCompleted(matchId);

        //Store match data in DB
        matchStatsService.storeAllMatchData(matchContext,inningContext);

        //Update MatchUserStats (credits, team points etc.)
        return matchUserService.updateMatchUserStats(matchContext,inningContext);

    }

    private void playInning() {
        inningContext = cricketUtility.assignPlayerListInningContext(matchContext, inningContext);
        // TODO: 28/03/23 Null check whenever we use dot (.)

        //Now I have battingPlayerList and bowlingPlayerList
        // TODO: 28/03/23 Never use postions in list, instead use enums (OnStrike, OffStrike etc)
        Player playerOnStrike = inningContext.getBattingPlayerList().get(0);
        Player playerOffStrike = inningContext.getBattingPlayerList().get(1);
        Player bowler = inningContext.getBowlingPlayerList().get(0);
        int wickets = 0;
        int playerNumber = 1;
        int bowlerNumber = 0;
        int bowlingTeamRuns = 0;
        int battingTeamRuns = 0;
        if (matchContext.isSecondInning()) {
            bowlingTeamRuns=cricketUtility.getBowlingTeamRuns(matchContext,inningContext);
        }
        loop:
        for (int ball = 1; ball <= TOTAL_BALLS; ball++) {
            //At the start of an over
            if (((ball - 1) % 6 == 0) && ball != 1) {
                //Changing strike
                Player temp = playerOnStrike;
                playerOnStrike = playerOffStrike;
                playerOffStrike = temp;


                //Changing bowler
                bowlerNumber++;
                if (bowlerNumber >= inningContext.getBowlingPlayerList().size()) {
                    bowlerNumber = 0;
                }
                bowler = inningContext.getBowlingPlayerList().get(bowlerNumber);
            }

            int resultOnBall = ResultOnBall.resultOnBall(playerOnStrike.getTitle(), bowler.getTitle());
            switch (resultOnBall) {
                case WICKET -> {
                    wickets++;
                    if (wickets == 10) {
                        break loop;
                    }
                    bowler.addWicket();
                    bowler.addPoints(FIFTEEN_POINTS);// TODO: 16/03/23  calculate teamPoints after the match
                    playerNumber++;
                    playerOnStrike = inningContext.getBattingPlayerList().get(playerNumber);
                }
                case ZERO_RUNS, TWO_RUNS -> {
                    playerOnStrike.addRuns(resultOnBall);
                    battingTeamRuns += resultOnBall;
                    if (matchContext.isSecondInning()) {
                        if (battingTeamRuns > bowlingTeamRuns) {
                            break loop;
                        }
                    }
                }
                case ONE_RUN, THREE_RUNS -> {
                    playerOnStrike.addRuns(resultOnBall);
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
                    playerOnStrike.addRuns(resultOnBall);
                    battingTeamRuns += resultOnBall;
                    playerOnStrike.addFour();
                    playerOnStrike.addPoints(FIVE_POINTS);
                    if (matchContext.isSecondInning()) {
                        if (battingTeamRuns > bowlingTeamRuns) {
                            break loop;
                        }
                    }
                }
                case SIX_RUNS -> {
                    playerOnStrike.addRuns(resultOnBall);
                    battingTeamRuns += resultOnBall;
                    playerOnStrike.addSix();
                    playerOnStrike.addPoints(TEN_POINTS);
                    if (matchContext.isSecondInning()) {
                        if (battingTeamRuns > bowlingTeamRuns) {
                            break loop;
                        }
                    }
                }
            }

        }

        //Updating runs of batting team
        matchContext=cricketUtility.addBattingTeamRuns(matchContext,inningContext,battingTeamRuns);
        //If it was first inning, then I have to swap
        if(!matchContext.isSecondInning()) {
            matchContext.setSecondInning(true);
        }

    }
}
