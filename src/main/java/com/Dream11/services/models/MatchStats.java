package com.Dream11.services.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "matchstats")
@Data
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
