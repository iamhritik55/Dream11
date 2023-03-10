package com.Dream11.services;

import com.Dream11.entity.MatchStats;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.utility.PlayingOrder;
import com.Dream11.utility.ResultOnBall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static com.Dream11.utility.ApplicationUtils.*;

@Service
public class InningService {

    @Autowired
    MatchDetailsService matchDetailsService;
    @Autowired
    PlayerService playerService;
    @Autowired
    PlayingOrder playingOrder;
    @Autowired
    MatchPlayerService matchPlayerService;
    @Autowired
    MatchStatsService matchStatsService;
    @Autowired
    private MatchUserService matchUserService;

    public List<MatchUserStats> playInning(Team battingTeam, Team bowlingTeam, boolean isFirstInning,
    String matchId) throws Exception{
        MatchStats matchStats;
        //Now I want to fetch List<Players> from List<playerId>
        List<Player> battingPlayerList = playerService.getPlayerListFromIdList(battingTeam.getTeamPlayerIds());
        List<Player> bowlingPlayerList = playerService.getPlayerListFromIdList(bowlingTeam.getTeamPlayerIds());

        battingPlayerList = playingOrder.battingOrder(battingPlayerList);
        bowlingPlayerList = playingOrder.battingOrder(bowlingPlayerList);


        Player playerOnStrike = battingPlayerList.get(0);
        Player playerOffStrike = battingPlayerList.get(1);
        Player bowler = bowlingPlayerList.get(0);
        int wickets = 0;
        int playerNumber = 1;
        int bowlerNumber = 0;
        int bowlingTeamRuns = 0;
        if(!isFirstInning){
            bowlingTeamRuns = matchDetailsService.getTeamScore(matchId, bowlingTeam.getId());
        }

        //MatchStart
        loop:
        for(int ball = 1; ball<= TOTAL_BALLS; ball++){

            //At the start of an over
            if(((ball-1)%6==0)  && ball!=1){
                //Changing strike
                Player temp = playerOnStrike;
                playerOnStrike = playerOffStrike;
                playerOffStrike = temp;


                //Changing bowler
                bowlerNumber++;
                if(bowlerNumber>=bowlingPlayerList.size())
                    bowlerNumber = 0;
                bowler = bowlingPlayerList.get(bowlerNumber);
            }

            int resultOnBall = ResultOnBall.resultOnBall(playerOnStrike.getTitle(), bowler.getTitle());

            switch (resultOnBall){
                case WICKET:
                    wickets++;
                    if(wickets==10){
                        break loop;
                    }
                    bowler.addWicket();
                    bowler.addPoints(FIFTEEN_POINTS);
                    playerNumber++;
                    playerOnStrike = battingPlayerList.get(playerNumber);
                    break;
                case ZERO_RUNS:
                case TWO_RUNS:
                    playerOnStrike.addRuns(resultOnBall);
                    battingTeam.addRuns(resultOnBall);
                    if(!isFirstInning){
                        if(battingTeam.getTeamRuns()>bowlingTeamRuns){
                            break loop;
                        }
                    }
                    break;
                case ONE_RUN:
                case THREE_RUNS:
                    playerOnStrike.addRuns(resultOnBall);
                    battingTeam.addRuns(resultOnBall);

                    Player temp = playerOnStrike;
                    playerOnStrike = playerOffStrike;
                    playerOffStrike = temp;

                    if(!isFirstInning){
                        if(battingTeam.getTeamRuns()>bowlingTeamRuns){
                            break loop; //see this
                        }
                    }
                    break;
                case FOUR_RUNS:
                    playerOnStrike.addRuns(resultOnBall);
                    battingTeam.addRuns(resultOnBall);
                    playerOnStrike.addFour();
                    playerOnStrike.addPoints(FIVE_POINTS);
                    if(!isFirstInning){
                        if(battingTeam.getTeamRuns()>bowlingTeamRuns){
                            break loop;
                        }
                    }
                    break;
                case SIX_RUNS:
                    playerOnStrike.addRuns(resultOnBall);
                    battingTeam.addRuns(resultOnBall);
                    playerOnStrike.addSix();
                    playerOnStrike.addPoints(TEN_POINTS);
                    if(!isFirstInning){
                        if(battingTeam.getTeamRuns()>bowlingTeamRuns){
                            break loop;
                        }
                    }
                    break;
            }
//            System.out.println(battingTeam.getName()+" = "+battingTeam.getTeamRuns()+" - " + wickets+" wickets"+" " +
//                    "resultOnBall-> "+resultOnBall);
        }

        //Storing all the data
        matchStats = matchStatsService.updateMatchStats(matchId,battingPlayerList, battingTeam.getId());
        matchStats = matchStatsService.updateMatchStats(matchId,bowlingPlayerList, bowlingTeam.getId());
        matchDetailsService.updateTeamScoreMatchDetails(matchId,battingTeam.getId(),battingTeam.getTeamRuns());
        battingPlayerList.addAll(bowlingPlayerList);
        //Updating teamPoints in MatchUserStats and storing it
        return matchUserService.updateMultipleMatchUserStats(matchId, battingPlayerList);
    }
}
