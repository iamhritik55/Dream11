package com.Dream11.services;

import com.Dream11.DTO.MatchUserStatsResponseDTO;
import com.Dream11.entity.Match;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.Player;
import com.Dream11.entity.User;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.MatchUserStatsRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.UserRepo;
import com.Dream11.services.validation.MatchUserValidation;
import com.Dream11.transformer.MatchUserStatsTransformer;
import com.mongodb.lang.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLOutput;
import java.util.*;

import static com.Dream11.Counter.counter;

@Service
public class MatchUserService {

    @Autowired
    PlayerRepo playerRepo;
    @Autowired
    MatchUserStatsRepo matchUserStatsRepo;
    @Autowired
    MatchRepo matchRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UtilityService utilityService;
    @Autowired
    UserService userService;
    @Autowired
    MatchUserValidation matchUserValidation;

    public List<MatchUserStats> getAllStats() {
        return matchUserStatsRepo.findAll();
    }

    public MatchUserStatsResponseDTO getUserStats(String match_userId) throws Exception {
        try {
            Optional<MatchUserStats> optionalStats = matchUserStatsRepo.findById(match_userId);
            MatchUserStats stats = new MatchUserStats();
            //making a list to store player names
            List<String> players = new ArrayList<>();
            //making a variable to store userName
            String userName = "";
            if (optionalStats.isPresent()) {
                stats = optionalStats.get();
                //get userName by using userId
                Optional<User> userId = userRepo.findById(stats.getUserId());
                if (userId.isPresent()) {
                    User user = userId.get();
                    userName = user.getName();
                }
                //if player present then fetch player names by their ids
                List<Player> listOfPlayerIds = playerRepo.findAllById(stats.getChosenPlayerIdList());
                if (!CollectionUtils.isEmpty(listOfPlayerIds)) {
                    for (Player player : listOfPlayerIds) {
                        players.add(player.getName());
                    }
                }
            }
            return MatchUserStatsTransformer.matchUserToResponseDto(stats, players, userName);
        } catch (Exception e) {
            throw new Exception("Data not found", e);
        }
    }

    public void createUserTeam(String matchId, String userId, List<String> playerIds) throws Exception {
        try {
            matchUserValidation.validateMatchUserIds(matchId, userId);
            utilityService.validatePlayerIds(playerIds);
            utilityService.validateTeamSize(playerIds);
            int totalCost = utilityService.calculateTeamCost(playerIds);
            utilityService.restrictPlayerIds(playerIds);

            //updating the credits of user
            userService.subtractUserCredits(userId, totalCost);
            MatchUserStats matchUserStats = new MatchUserStats();
            matchUserStats.setMatchId(matchId);
            matchUserStats.setUserId(userId);
            matchUserStats.setCreditsSpentByUser(totalCost);
            matchUserStats.setChosenPlayerIdList(playerIds);
            matchUserStatsRepo.save(matchUserStats);
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    //So MatchUserStats already exist, I want to fetch it from db and update it
    public List<MatchUserStats> updateMultipleMatchUserStats(String matchId, List<Player> combinedPlayerList)
            throws Exception {
        List<MatchUserStats> matchUserStatsList = findByMatchId(matchId);
        List<MatchUserStats> matchUserStatsList1 = new ArrayList<>();
        for (MatchUserStats matchUserStats : matchUserStatsList) {
            matchUserStatsList1.add(updateSingleMatchUserStats(matchUserStats, combinedPlayerList));
        }
        counter++;
        return matchUserStatsRepo.saveAll(matchUserStatsList1);

    }

    public MatchUserStats updateSingleMatchUserStats(MatchUserStats matchUserStats, List<Player> playerList) {
        List<String> chosenList = matchUserStats.getChosenPlayerIdList();
        Collections.sort(chosenList);

        for (int chosenListNumber = 0; chosenListNumber < chosenList.size(); chosenListNumber++) {
            for (Player player : playerList) {
                if (Objects.equals(chosenList.get(chosenListNumber), player.getId())) {
                    int teamPoints = matchUserStats.getTeamPoints();
                    teamPoints += player.getPlayerPoints();
                    matchUserStats.setTeamPoints(teamPoints);
                    break;
                }
            }
        }
        return matchUserStats;
    }

    public List<MatchUserStats> findByMatchId(String matchId) throws Exception {
        List<MatchUserStats> matchUserStats = matchUserStatsRepo.findByMatchId(matchId);
        counter++;
        if (!CollectionUtils.isEmpty(matchUserStats)) {
            return matchUserStats;
        } else {
            throw new Exception("No record found with corresponding matchId");
        }

    }

    public MatchUserStats createMatchUserStat(MatchUserStats matchUserStats) {
        counter++;
        return matchUserStatsRepo.save(matchUserStats);

    }

    public void updateWinnerUserPoints(String matchId) throws Exception {
        List<MatchUserStats> matchUserStatsList = findByMatchId(matchId);
        String winnerId = "";
        int teamPoints = 0;
        int pointsPool = 0;
        //        boolean equal = false;
        for (MatchUserStats matchUserStats : matchUserStatsList) {
            pointsPool += matchUserStats.getCreditsSpentByUser();
            if (matchUserStats.getTeamPoints() > teamPoints) {
                winnerId = matchUserStats.getMatch_userId();
                teamPoints = matchUserStats.getTeamPoints();
                //                equal = false;
            }

        }

        for (MatchUserStats matchUserStats : matchUserStatsList) {
            if (Objects.equals(winnerId, matchUserStats.getMatch_userId())) {
                matchUserStats.setCreditChange(pointsPool - matchUserStats.getCreditsSpentByUser());
                userService.addUserCredits(matchUserStats.getUserId(), pointsPool);
            } else {
                matchUserStats.setCreditChange(-matchUserStats.getCreditsSpentByUser());
            }

        }
        counter++;
        matchUserStatsRepo.saveAll(matchUserStatsList);
    }
}
