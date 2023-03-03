package com.Dream11.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "MatchPlayerStats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchPlayerStats {
    private int matchId;
    private int playerId;
    private int battingRuns;
    private int bowlingRuns;
    private int foursScored;
    private int sixesScored;
    private int playerPoints;
}
