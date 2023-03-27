package com.Dream11.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponseDTO {
    private String id;
    private String team1Id;
    private String team2Id;
    private long team1Score;// consider long
    private long team2Score;
}
