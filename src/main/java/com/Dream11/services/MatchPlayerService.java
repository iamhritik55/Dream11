package com.Dream11.services;

import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.entity.Player;
import com.Dream11.repo.MatchPlayerStatsRepo;
import com.Dream11.utility.CombinedId;
import com.Dream11.utility.CombinedMatchPlayerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchPlayerService {
    @Autowired
    public MatchPlayerStatsRepo matchPlayerStatsRepo;


    //    public List<MatchPlayerStats> getMatchStats(int matchId) {
    //        return matchPlayerStatsRepo.findByMatchId(matchId);
    //    }
    public MatchPlayerStats getMatchStats(CombinedMatchPlayerId combinedId){

        return matchPlayerStatsRepo.findById(combinedId).get();
    }
    public void updateMatchPlayerStats(Player player, int matchId){
        CombinedMatchPlayerId combinedMatchPlayerId = new CombinedMatchPlayerId(matchId, player.getId());

        if(matchPlayerStatsRepo.findById(combinedMatchPlayerId).isPresent()){
            MatchPlayerStats playerStats = matchPlayerStatsRepo.findById(combinedMatchPlayerId).get();
            int foursScored = player.getFoursScored()+ playerStats.getFoursScored();
            int sixesScored = player.getSixesScored()+playerStats.getSixesScored();
            int battingRuns = player.getBattingRuns()+ playerStats.getBattingRuns();
            int bowlingWickets = player.getBowlingWickets()+ playerStats.getBowlingWickets();
            int playerPoints = player.getPlayerPoints()+ playerStats.getPlayerPoints();

            playerStats.setPlayerPoints(playerPoints);
            playerStats.setBattingRuns(battingRuns);
            playerStats.setBowlingWickets(bowlingWickets);
            playerStats.setSixesScored(sixesScored);
            playerStats.setFoursScored(foursScored);

            matchPlayerStatsRepo.save(playerStats);
        }
        else {
            MatchPlayerStats playerStats = new MatchPlayerStats();
            playerStats.setId(combinedMatchPlayerId);
            playerStats.setPlayerPoints(player.getPlayerPoints());
            playerStats.setFoursScored(player.getFoursScored());
            playerStats.setBattingRuns(player.getBattingRuns());
            playerStats.setBowlingWickets(player.getBowlingWickets());
            playerStats.setSixesScored(player.getSixesScored());
            matchPlayerStatsRepo.save(playerStats);
        }
    }

    public void deleteAll(){
        matchPlayerStatsRepo.deleteAll();
    }
}
