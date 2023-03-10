package com.Dream11.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {
    private String matchId;
    private String team1Id;
    private String team2Id;
    private int team1Score;
    private int team2Score;
}
