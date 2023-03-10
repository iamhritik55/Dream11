package com.Dream11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MatchDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @Id
    private String matchId;
    private String team1Id;
    private String team2Id;
    private int team1Score;
    private int team2Score;
}

