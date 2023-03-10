package com.Dream11.services;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.Player;
import com.Dream11.repo.MatchUserStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static com.Dream11.Counter.counter;

@Service
public class MatchUserService {

    @Autowired
    MatchUserStatsRepo matchUserStatsRepo;
    @Autowired
    UserService userService;

    //So MatchUserStats already exist, I want to fetch it from db and update it
    public List<MatchUserStats> updateMultipleMatchUserStats(String matchId, List<Player> combinedPlayerList) throws Exception{
        List<MatchUserStats> matchUserStatsList = findByMatchId(matchId);
        for(MatchUserStats matchUserStats: matchUserStatsList){
            matchUserStats = updateSingleMatchUserStats(matchUserStats,combinedPlayerList);
        }
        counter++;
        return matchUserStatsRepo.saveAll(matchUserStatsList);

    }

    public MatchUserStats updateSingleMatchUserStats(MatchUserStats matchUserStats, List<Player> playerList){
        List<String> chosenList = matchUserStats.getChosenPlayerIdList();
            Collections.sort(chosenList);

        for(int chosenListNumber=0; chosenListNumber<chosenList.size(); chosenListNumber++){
            for(Player player: playerList){
                if(chosenList.get(chosenListNumber) == player.getId()){
                    int teamPoints = matchUserStats.getTeamPoints();
                    teamPoints+=player.getPlayerPoints();
                    matchUserStats.setTeamPoints(teamPoints);
                    break;
                }
            }
        }
        return matchUserStats;
    }

    public List<MatchUserStats> findByMatchId(String matchId) throws Exception{
        List<MatchUserStats> matchUserStats= matchUserStatsRepo.findByMatchId(matchId);
        counter++;
        if(!CollectionUtils.isEmpty(matchUserStats))
            return matchUserStats;
        else
            throw new Exception("No record found with corresponding matchId");

    }

    public MatchUserStats createMatchUserStat(MatchUserStats matchUserStats){
        counter++;
        return matchUserStatsRepo.save(matchUserStats);

    }

    public void updateWinnerUserPoints(String matchId) throws Exception{
        List<MatchUserStats> matchUserStatsList = findByMatchId(matchId);
        String winnerId="";
        int teamPoints = 0;
        int pointsPool = 0;
//        boolean equal = false;
        for(MatchUserStats matchUserStats: matchUserStatsList){
            pointsPool+=matchUserStats.getCreditsSpentByUser();
            if(matchUserStats.getTeamPoints()>teamPoints){
                winnerId = matchUserStats.getMatch_userId();
                teamPoints = matchUserStats.getTeamPoints();
//                equal = false;
            }

        }

        for(MatchUserStats matchUserStats: matchUserStatsList){
            if(winnerId==matchUserStats.getMatch_userId()){
                matchUserStats.setCreditChange(pointsPool-matchUserStats.getCreditsSpentByUser());
                userService.addUserCredits(matchUserStats.getUserId(), pointsPool);
            }
            else {
                matchUserStats.setCreditChange(-matchUserStats.getCreditsSpentByUser());
            }

        }
        counter++;
        matchUserStatsRepo.saveAll(matchUserStatsList);
    }
}
