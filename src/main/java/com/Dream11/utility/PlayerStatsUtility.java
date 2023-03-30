package com.Dream11.utility;

import com.Dream11.services.models.PlayerStats;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerStatsUtility {
    public PlayerStats addRuns(PlayerStats playerStats, int runs) {
        playerStats.setBattingRuns(runs+ playerStats.getBattingRuns());
        return playerStats;
    }
    public PlayerStats addWicket(PlayerStats playerStats) {
        playerStats.setBowlingWickets(playerStats.getBowlingWickets()+1);
        return playerStats;
    }

    public PlayerStats addFour(PlayerStats playerStats) {
        playerStats.setFoursScored(playerStats.getFoursScored()+1);
        return playerStats;
    }

    public PlayerStats addSix(PlayerStats playerStats) {
        playerStats.setSixesScored(playerStats.getSixesScored()+1);
        return playerStats;
    }
    public PlayerStats addPoints(PlayerStats playerStats, int points) {
        playerStats.setPlayerPoints(points+ playerStats.getPlayerPoints());
        return playerStats;
    }
}
