package com.Dream11.transformer;

import com.Dream11.DTO.MatchUserStatsResponseDTO;
import com.Dream11.DTO.UserResponseDTO;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.User;

import java.util.List;

public class MatchUserStatsTransformer {

    public static MatchUserStatsResponseDTO matchUserToResponseDto(MatchUserStats matchUserStats, List<String> players, String userName) {
        MatchUserStatsResponseDTO responseDto = new MatchUserStatsResponseDTO();
        responseDto.setUserName(userName);
        responseDto.setCreditChange(matchUserStats.getCreditChange());
        responseDto.setTeamPoints(matchUserStats.getTeamPoints());
        responseDto.setPlayerNames(players);
        responseDto.setCreditsSpentByUser(matchUserStats.getCreditsSpentByUser());
        return responseDto;
    }
}
