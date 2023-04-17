package com.Dream11.utility;

import com.Dream11.services.models.PlayerStats;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerStatsUtility {
    public static void addRuns(PlayerStats playerStats, int runs) {
        playerStats.setBattingRuns(runs+ playerStats.getBattingRuns());
    }
    public static void addWicket(PlayerStats playerStats) {
        playerStats.setBowlingWickets(playerStats.getBowlingWickets()+1);
    }

    public static void addFour(PlayerStats playerStats) {
        playerStats.setFoursScored(playerStats.getFoursScored()+1);
    }

    public static void addSix(PlayerStats playerStats) {
        playerStats.setSixesScored(playerStats.getSixesScored()+1);
    }
    public static void addPoints(PlayerStats playerStats, int points) {
        playerStats.setPlayerPoints(points+ playerStats.getPlayerPoints());
    }

}
