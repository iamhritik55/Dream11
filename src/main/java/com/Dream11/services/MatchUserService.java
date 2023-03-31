package com.Dream11.services;

import com.Dream11.DTO.response.LeaderboardResponseDTO;
import com.Dream11.DTO.response.MatchUserStatsResponseDTO;
import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.repo.MatchUserStatsRepo;
import com.Dream11.services.transformer.LeaderboardTransformer;
import com.Dream11.services.transformer.MatchUserStatsTransformer;
import com.Dream11.services.validation.MatchUserValidation;
import com.Dream11.utility.MatchUserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchUserService {


    @Autowired
    private MatchUserStatsRepo matchUserStatsRepo;


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


    public MatchUserStatsResponseDTO getUserStats(String userId, String matchId) throws Exception {
        MatchUserStats matchUserStats =
                matchUserStatsRepo.findByUserIdAndMatchId(userId, matchId).orElseThrow(()-> new Exception("Invalid matchId or UserId"));
        return MatchUserStatsTransformer.generateResponseDto(matchUserStats);
    }

    public MatchUserStatsResponseDTO createUserTeam(String matchId, String userId, List<String> playerIds) throws Exception {

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
        return MatchUserStatsTransformer.generateResponseDto(matchUserStats);
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
