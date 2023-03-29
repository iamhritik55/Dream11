package com.Dream11.services;

import com.Dream11.DTO.response.LeaderboardResponseDTO;
import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.DTO.response.MatchUserStatsResponseDTO;
import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.User;
import com.Dream11.services.repo.MatchRepo;
import com.Dream11.services.repo.MatchUserStatsRepo;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.services.repo.UserRepo;
import com.Dream11.services.validation.MatchUserValidation;
import com.Dream11.services.transformer.LeaderboardTransformer;
import com.Dream11.services.transformer.MatchUserStatsTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
    @Autowired
    LeaderboardTransformer leaderboardTransformer;

    public List<MatchUserStats> getAllStats() {
        return matchUserStatsRepo.findAll();
    }

    public MatchUserStatsResponseDTO getUserStats(String id) throws Exception {

        Optional<MatchUserStats> optionalStats = matchUserStatsRepo.findById(id);
        //making a list to store player names
        // TODO: 28/03/23 use ifPresent()
        if (optionalStats.isPresent()) {
            List<String> players = new ArrayList<>();
            //making a variable to store userName
            String userName = "";
            MatchUserStats stats = optionalStats.get();
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
            }// TODO: 28/03/23  don't give names is response
            return MatchUserStatsTransformer.matchUserToResponseDto(stats, players, userName);
        } else {
            throw new Exception("Data not found for this Id");
        }

    }

    public void createUserTeam(String matchId, String userId, List<String> playerIds) throws Exception {

        matchUserValidation.validateMatchUserIds(matchId, userId);
        utilityService.validatePlayerIds(playerIds);
        utilityService.validateUserTeamSize(playerIds);
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

    }

    public List<LeaderboardResponseDTO> updateMatchUserStats(CricketMatchContext matchContext,
                                                             CricketInningContext inningContext) throws Exception {

        //first fetch matchUser from db, if it does not exist throw and exception.
        List<MatchUserStats> matchUserStatsList = findByMatchId(matchContext.getMatch().getMatchId());

        List<Player> playerList = new ArrayList<>();
        playerList.addAll(inningContext.getBattingPlayerList());
        playerList.addAll(inningContext.getBowlingPlayerList());
        List<MatchUserStats> matchUserStatsList1 = new ArrayList<>();

        //update team points
        for (MatchUserStats matchUserStats : matchUserStatsList) {
            matchUserStatsList1.add(updateTeamPoints(matchUserStats, playerList));
        }

        //update credits
        matchUserStatsList1 = distributeCredits(matchUserStatsList1);

        //save the data
        matchUserStatsRepo.saveAll(matchUserStatsList1);

        //returning the leaderboard list
        return leaderboardTransformer.matchUserListToLeaderboardList(matchUserStatsList1);

    }

    private MatchUserStats updateTeamPoints(MatchUserStats matchUserStats, List<Player> playerList) {
        List<String> userPlayerList = matchUserStats.getChosenPlayerIdList();
        Collections.sort(userPlayerList);

        for (String s : userPlayerList) {
            for (Player player : playerList) {
                if (Objects.equals(s, player.getId())) {
                    int teamPoints = matchUserStats.getTeamPoints();
                    teamPoints += player.getPlayerPoints();
                    matchUserStats.setTeamPoints(teamPoints);
                    break;
                }
            }
        }
        return matchUserStats;
    }

    private List<MatchUserStats> distributeCredits(List<MatchUserStats> matchUserStatsList) throws Exception {
        //Sorting the array based on team points (Descending order)
        matchUserStatsList.sort(new Comparator<MatchUserStats>() {

            @Override
            public int compare(MatchUserStats m1, MatchUserStats m2) {
                return Integer.compare(m2.getTeamPoints(), m1.getTeamPoints());
            }
        });

        int creditPool = 0;
        int numberOfWinners = 0;
        int winnerPoints = matchUserStatsList.get(0).getTeamPoints();
        //Calculating pointsPool and number of winners.
        for (MatchUserStats matchUserStats : matchUserStatsList) {
            creditPool += matchUserStats.getCreditsSpentByUser();
            if (winnerPoints == matchUserStats.getTeamPoints()) {
                numberOfWinners++;
            }
        }
        //distributing points equally to all the winners
        int pointsToDistribute = creditPool / numberOfWinners;
        if (numberOfWinners != 2 && matchUserStatsList.size() != 2) {
            for (MatchUserStats matchUserStats : matchUserStatsList) {
                if (numberOfWinners == 0) {
                    matchUserStats.setCreditChange(-matchUserStats.getCreditsSpentByUser());
                } else {
                    matchUserStats.setCreditChange(pointsToDistribute - matchUserStats.getCreditsSpentByUser());
                    numberOfWinners--;
                }
            }
        }
        userService.addCreditsUsingMatchUserStats(matchUserStatsList);
        return matchUserStatsList;
    }

    public List<MatchUserStats> findByMatchId(String matchId) throws Exception {

        List<MatchUserStats> matchUserStatsList = matchUserStatsRepo.findByMatchId(matchId);
        if (matchUserStatsList == null) {
            throw new Exception("No users registered for this match");
        }
        return matchUserStatsList;
    }
}
