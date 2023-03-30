package com.Dream11.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class LeaderboardResponseDTO {
    private String userName;
    private List<String> userTeam;
    private int creditChange;
    private int teamPoints;
    private int creditsSpentByUser;
}
