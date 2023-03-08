package com.Dream11.services;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.Player;
import com.Dream11.repo.MatchUserStatsRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.utility.CombinedMatchUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return matchUserStatsRepo.save(matchUserStats);
    }

    public List<MatchUserStats> getAllStats() {
        return matchUserStatsRepo.findAll();
    }

    public MatchUserStats getUserStats(CombinedMatchUserId combinedMatchUserId) throws Exception {
        MatchUserStats matchUserStats = new MatchUserStats();
        //finding matchUserStats by passing matchId and userId
        Optional<MatchUserStats> optionalStats = matchUserStatsRepo.findById(combinedMatchUserId);
        if (optionalStats.isPresent()) {
            MatchUserStats stats = optionalStats.get();

            //making a list to store player names
            List<String> players = new ArrayList<>();

            //if player present then fetch player names by their ids
            for (Integer playerId : stats.getChosenPlayerIdList()) {
                Player player = playerRepo.findById(playerId).orElse(null);
                if (player != null) {
                    players.add(player.getName());
                }
            }
            matchUserStats.setId(stats.getId());
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

    public void createUserTeam(int matchId, int userId, List<Integer> playerIds) throws Exception {
        CombinedMatchUserId combinedMatchUserId = new CombinedMatchUserId(matchId, userId);
        Optional<MatchUserStats> optionalMatchUserStats = matchUserStatsRepo.findById(combinedMatchUserId);
        if (optionalMatchUserStats.isEmpty()) {
            throw new Exception("User with this id does not exist.");
        }
        utilityService.validatePlayerIds(playerIds);

        utilityService.validateTeamSize(playerIds);
        MatchUserStats updateMatchUserStats = optionalMatchUserStats.get();

        int totalCost = utilityService.calculateTeamCost(playerIds);

        utilityService.restrictPlayerIds(playerIds);
        userService.subtractUserCredits(userId, totalCost);
        updateMatchUserStats.setCreditsSpentByUser(totalCost);
        updateMatchUserStats.setChosenPlayerIdList(playerIds);

        matchUserStatsRepo.save(updateMatchUserStats);
    }
}
