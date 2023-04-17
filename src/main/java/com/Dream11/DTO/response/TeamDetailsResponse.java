package com.Dream11.DTO.response;

import lombok.Data;

import java.util.List;

@Data
public class TeamDetailsResponse {
    private String team1Id;
    private String team2Id;
    private String team1Name;
    private String team2Name;
    private List<PlayerResponseDTO> team1Players;
    private List<PlayerResponseDTO> team2Players;
}
