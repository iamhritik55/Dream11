package com.Dream11.services;

import com.Dream11.entity.*;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.MatchUserStatsRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.UserRepo;
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

    public MatchUserStats addMatchUserStats(MatchUserStats matchUserStats) throws Exception {
        String matchId = matchUserStats.getMatchId();
        Optional<Match> optionalMatch = matchRepo.findById(matchId);
        String userId = matchUserStats.getUserId();
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalMatch.isPresent() && optionalUser.isPresent()) {
            Match matchObj = optionalMatch.get();
            if (matchObj.getCompleted() == MatchStatus.PLAYED) {
                throw new Exception("This match is already played please choose another one.");
            }
            String matchUserId = matchUserStats.getMatchId() + "_" + matchUserStats.getUserId();
            matchUserStats.setMatch_userId(matchUserId);
            return matchUserStatsRepo.save(matchUserStats);
        } else {
            throw new Exception("IDs not found.");
        }
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
            matchUserStats.setMatch_userId(stats.getMatch_userId()); // TODO: 16/03/23 creaete a transformer
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

    public void createUserTeam(String match_userId, List<String> playerIds) throws Exception {
        Optional<MatchUserStats> optionalMatchUserStats = matchUserStatsRepo.findById(match_userId);
        if (optionalMatchUserStats.isEmpty()) {
            throw new Exception("ID not found.");
        }
        utilityService.validatePlayerIds(playerIds);

        utilityService.validateTeamSize(playerIds);
        MatchUserStats updateMatchUserStats = optionalMatchUserStats.get();
        String userId = updateMatchUserStats.getUserId();
        System.out.println(userId);
        int totalCost = utilityService.calculateTeamCost(playerIds);

        utilityService.restrictPlayerIds(playerIds);

        //updating the credits of user
        userService.subtractUserCredits(userId, totalCost);
        updateMatchUserStats.setCreditsSpentByUser(totalCost);
        updateMatchUserStats.setChosenPlayerIdList(playerIds);

        matchUserStatsRepo.save(updateMatchUserStats);
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
