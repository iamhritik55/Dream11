package com.Dream11.services;

import com.Dream11.DTO.response.LeaderboardResponseDTO;
import com.Dream11.DTO.response.MatchUserStatsResponseDTO;
import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.PlayerStats;
import com.Dream11.services.models.User;
import com.Dream11.services.repo.MatchRepo;
import com.Dream11.services.repo.MatchUserStatsRepo;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.services.repo.UserRepo;
import com.Dream11.services.transformer.LeaderboardTransformer;
import com.Dream11.services.transformer.MatchUserStatsTransformer;
import com.Dream11.services.validation.MatchUserValidation;
import com.Dream11.utility.MatchUserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class MatchUserService {

    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private MatchUserStatsRepo matchUserStatsRepo;
    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private UserService userService;
    @Autowired
    private MatchUserValidation matchUserValidation;
    @Autowired
    private LeaderboardTransformer leaderboardTransformer;
    @Autowired
    private MatchUserUtility matchUserUtility;

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

    public List<LeaderboardResponseDTO> updateMatchUserStats(CricketMatchContext matchContext, CricketInningContext inningContext) throws Exception {

        //first fetch matchUser from db, if it does not exist throw and exception.
        List<MatchUserStats> matchUserStatsList = findByMatchId(matchContext.getMatch().getMatchId());

        List<MatchUserStats> matchUserStatsList1 = new ArrayList<>();

        //update team points
        for (MatchUserStats matchUserStats : matchUserStatsList) {
            matchUserStatsList1.add(MatchUserUtility.updateTeamPoints(matchUserStats,
                    inningContext.getPlayerStatsList()));
        }

        //update credits
        matchUserStatsList1 = matchUserUtility.distributeCredits(matchUserStatsList1);

        //save the data
        matchUserStatsRepo.saveAll(matchUserStatsList1);

        //returning the leaderboard list
        return leaderboardTransformer.matchUserListToLeaderboardList(matchUserStatsList1);

    }



    public List<MatchUserStats> findByMatchId(String matchId) throws Exception {

        List<MatchUserStats> matchUserStatsList = matchUserStatsRepo.findByMatchId(matchId);
        matchUserStatsList.stream().findFirst().orElseThrow(()->new Exception("No users registered for this match"));
        return matchUserStatsList;
    }
}
