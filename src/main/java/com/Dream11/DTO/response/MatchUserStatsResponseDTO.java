package com.Dream11.DTO.response;

import lombok.Data;

import java.util.List;

@Data

public class MatchUserStatsResponseDTO {
    private String userName;
    private List<String> playerNames;// TODO: 17/03/23  add this to response DTO
    private int creditChange;
    private int teamPoints;
    private int creditsSpentByUser;
}
