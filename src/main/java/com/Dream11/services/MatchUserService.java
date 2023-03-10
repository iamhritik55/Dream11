package com.Dream11.services;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.Player;
import com.Dream11.repo.MatchUserStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import com.Dream11.entity.Player;
import com.Dream11.repo.MatchUserStatsRepo;
import com.Dream11.repo.PlayerRepo;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static com.Dream11.Counter.counter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchUserService {
    @Autowired
    PlayerRepo playerRepo;
    @Autowired
    MatchUserStatsRepo matchUserStatsRepo;
    @Autowired
    UtilityService utilityService;
    @Autowired
    UserService userService;

    public MatchUserStats addMatchUserStats(MatchUserStats matchUserStats) {
        String matchUserId = matchUserStats.getMatchId() + "_" + matchUserStats.getUserId();
        matchUserStats.setMatch_userId(matchUserId);
        return matchUserStatsRepo.save(matchUserStats);
    }

    public List<MatchUserStats> getAllStats() {
        return matchUserStatsRepo.findAll();
    }

    public MatchUserStats getUserStats(String match_userId) throws Exception {
        MatchUserStats matchUserStats = new MatchUserStats();
        //finding matchUserStats by passing matchId and userId
        Optional<MatchUserStats> optionalStats = matchUserStatsRepo.findById(match_userId);
        if (optionalStats.isPresent()) {
            MatchUserStats stats = optionalStats.get();

            //making a list to store player names
            List<String> players = new ArrayList<>();

            //if player present then fetch player names by their ids
            List<Player> listOfPlayerIds = playerRepo.findAllById(stats.getChosenPlayerIdList());
            if (!CollectionUtils.isEmpty(listOfPlayerIds)) {
                for (Player player : listOfPlayerIds) {
                    players.add(player.getName());
                }
            }
            matchUserStats.setMatch_userId(stats.getMatch_userId());
            matchUserStats.setMatchId(stats.getMatchId());
            matchUserStats.setUserId(stats.getUserId());
            matchUserStats.setCreditChange(stats.getCreditChange());
            matchUserStats.setTeamPoints(stats.getTeamPoints());
            matchUserStats.setChosenPlayerIdList(stats.getChosenPlayerIdList());
            matchUserStats.setPlayerName(players);
            matchUserStats.setCreditsSpentByUser(stats.getCreditsSpentByUser());
            return matchUserStats;
        } else {
            throw new Exception("Data not found");
        }
    }

    public void createUserTeam(String match_userId, String userId, List<String> playerIds) throws Exception {
        Optional<MatchUserStats> optionalMatchUserStats = matchUserStatsRepo.findById(match_userId);
        if (optionalMatchUserStats.isEmpty()) {
            throw new Exception("User with this id does not exist.");
        }
        utilityService.validatePlayerIds(playerIds);

        utilityService.validateTeamSize(playerIds);
        MatchUserStats updateMatchUserStats = optionalMatchUserStats.get();

        int totalCost = utilityService.calculateTeamCost(playerIds);

        utilityService.restrictPlayerIds(playerIds);

        //updating the credits of user
        userService.subtractUserCredits(userId, totalCost);
        updateMatchUserStats.setCreditsSpentByUser(totalCost);
        updateMatchUserStats.setChosenPlayerIdList(playerIds);

        matchUserStatsRepo.save(updateMatchUserStats);

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
