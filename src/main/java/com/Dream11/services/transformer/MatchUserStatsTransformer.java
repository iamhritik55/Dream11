package com.Dream11.services.transformer;

import com.Dream11.DTO.response.MatchUserStatsResponseDTO;
import com.Dream11.services.models.MatchUserStats;

public class MatchUserStatsTransformer {

    public static MatchUserStatsResponseDTO generateResponseDto(MatchUserStats matchUserStats) {
        MatchUserStatsResponseDTO responseDto = new MatchUserStatsResponseDTO();
        responseDto.setChosenPlayerIdList(matchUserStats.getChosenPlayerIdList());
        responseDto.setCreditChange(matchUserStats.getCreditChange());
        responseDto.setTeamPoints(matchUserStats.getTeamPoints());
        responseDto.setCreditsSpentByUser(matchUserStats.getCreditsSpentByUser());
        return responseDto;
    }
}
