package com.Dream11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashMap;
import java.util.List;

@Document("matchStats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchStats {
    @Id
    private String id; //Equals matchId
    private String team1Name;
    private int team1Score;
    private List<PlayerStats> team1PlayerStats;
    private String team2Name;
    private int team2Score;
    private List<PlayerStats> team2PlayerStats;
    private String winnerTeamName;

}
