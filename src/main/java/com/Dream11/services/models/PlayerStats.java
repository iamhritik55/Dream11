package com.Dream11.services.models;

import lombok.Data;

@Data
public class PlayerStats {
    private String playerId;
    private String playerName;
    private int battingRuns;
    private int bowlingWickets;
    private int foursScored;
    private int sixesScored;
    private int playerPoints;
}
