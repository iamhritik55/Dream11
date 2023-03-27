package com.Dream11.transformer;

import com.Dream11.DTO.response.LeaderboardResponseDTO;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.PlayerStats;
import com.Dream11.services.PlayerService;
import com.Dream11.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeaderboardTransformer {
    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;
    public List<LeaderboardResponseDTO> matchUserListToLeaderboardList(List<MatchUserStats> matchUserStatsList) throws Exception{
    List<LeaderboardResponseDTO> leaderboardResponseDTOList = new ArrayList<>();
    for (MatchUserStats matchUserStats: matchUserStatsList){
        leaderboardResponseDTOList.add(matchUserToLeaderboardDTO(matchUserStats));
     }
    return leaderboardResponseDTOList;
    }
    private LeaderboardResponseDTO matchUserToLeaderboardDTO(MatchUserStats matchUserStats) throws Exception{
        LeaderboardResponseDTO leaderboard = new LeaderboardResponseDTO();
        leaderboard.setCreditChange(matchUserStats.getCreditChange());
        leaderboard.setTeamPoints(matchUserStats.getTeamPoints());
        leaderboard.setUserTeam(playerService.playerIdListToNameList(matchUserStats.getChosenPlayerIdList()));
        leaderboard.setUserName(userService.findUserNameById(matchUserStats.getUserId()));
        leaderboard.setCreditsSpentByUser(matchUserStats.getCreditsSpentByUser());
        return leaderboard;
    }
}
