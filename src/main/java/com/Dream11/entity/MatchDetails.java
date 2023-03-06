package com.Dream11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "MatchDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDetails {

    @Id
    private int matchId;
    private int team1Id;
    private int team2Id;
    @Indexed
    private boolean completed;
    private int team1Score;
    private int team2Score;
}

