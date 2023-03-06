package com.Dream11.services;

import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.utility.PlayingOrder;
import com.Dream11.utility.ResultOnBall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void playInning(Team battingTeam, Team bowlingTeam, boolean isFirstInning, int matchId){
        //Now I want to fetch List<Players> from List<playerId>
        List<Player> battingPlayerList = new ArrayList<>();
        for(int playerId: battingTeam.getTeamPlayerIds()){
            battingPlayerList.add(playerService.getPlayerFromId(playerId));
        }


        List<Player> bowlingPlayerList = new ArrayList<>();
        for(int playerId: bowlingTeam.getTeamPlayerIds()){
            bowlingPlayerList.add(playerService.getPlayerFromId(playerId));
        }

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
        for(int ball=1; ball<=120; ball++){

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

            if(resultOnBall == 7){
                //System.out.println(playerOnStrike.getName()+" is out now and has scored "+ playerOnStrike
                // .getBattingRuns());
                wickets++;
                if(wickets==10){
                    //System.out.println(battingTeam.getName()+" is all out now!"+" and has scored "+ battingTeam
                    // .getTeamRuns());
                    break;
                }
                bowler.addWicket();
                bowler.addPoints(15);
                playerNumber++;
                playerOnStrike = battingPlayerList.get(playerNumber);
            }
            else if(resultOnBall == 1){
                //add runs and change strike
                playerOnStrike.addRuns(1);
                battingTeam.addRuns(1);

                Player temp = playerOnStrike;
                playerOnStrike = playerOffStrike;
                playerOffStrike = temp;

                if(!isFirstInning){
                    if(battingTeam.getTeamRuns()>bowlingTeamRuns){
//                        System.out.println(battingTeam.getName()+" has won the match!");
                        break;
                    }
                }


            }
            else{
                playerOnStrike.addRuns(resultOnBall);
                battingTeam.addRuns(resultOnBall);

                if(resultOnBall==6){
                    playerOnStrike.addSix();
                    playerOnStrike.addPoints(10);
                }
                else if (resultOnBall==4) {
                    playerOnStrike.addFour();
                    playerOnStrike.addPoints(5);
                }

                if(!isFirstInning){
                    if(battingTeam.getTeamRuns()>bowlingTeamRuns){
//                        System.out.println(battingTeam.getName()+" has won the match!");
                        break;
                    }
                }

            }

        }

        //Storing all the data
        for(Player player: battingPlayerList){
            matchPlayerService.updateMatchPlayerStats(player,matchId);
        }
        System.out.println(battingPlayerList);
        for(Player player: bowlingPlayerList){
            matchPlayerService.updateMatchPlayerStats(player,matchId);
        }
        System.out.println(battingTeam.getTeamRuns());
        matchDetailsService.updateTeamScoreMatchDetails(matchId,battingTeam.getId(),battingTeam.getTeamRuns());

    }


}
