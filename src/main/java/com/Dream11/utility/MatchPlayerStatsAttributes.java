package com.Dream11.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchPlayerStatsAttributes {
    private String playerName;
    private int battingRuns;
    private int bowlingWickets;
    private int foursScored;
    private int sixesScored;
    private int playerPoints;
}
