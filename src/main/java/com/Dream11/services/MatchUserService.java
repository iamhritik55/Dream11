package com.Dream11.services;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.Player;
import com.Dream11.repo.MatchUserStatsRepo;
import com.Dream11.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            if(!CollectionUtils.isEmpty(listOfPlayerIds)){
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
    }
}
