package com.Dream11.DTO.response;

import lombok.Data;

import java.util.List;

@Data

public class MatchUserStatsResponseDTO {

    private List<String> chosenPlayerIdList;
    private int creditChange;
    private int teamPoints;
    private int creditsSpentByUser;
}
