package com.Dream11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDetailsDTO {
    private int matchId;
    private int team1Id;
    private int team2Id;
}
