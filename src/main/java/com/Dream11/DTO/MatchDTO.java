package com.Dream11.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {// make response and request DTOs
    @NonNull
    private String matchId;// remove ID
    private String team1Id;
    private String team2Id;
    private int team1Score;// consider long
    private int team2Score;
}
