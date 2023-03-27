package com.Dream11.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchUserStatsResponseDTO {
    private String userName;
    private List<String> playerNames;// TODO: 17/03/23  add this to response DTO
    private int creditChange;
    private int teamPoints;
    private int creditsSpentByUser;
}
