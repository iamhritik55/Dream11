package com.Dream11.Utils;

import com.Dream11.services.models.PlayerStats;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsUtils {

    public static PlayerStats createPlayerStats(String playerId, int playerPoints){
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayerId(playerId);
        playerStats.setPlayerPoints(playerPoints);
        return playerStats;
    }

    public static List<PlayerStats> createListOfPlayerStats(List<String> playerIds,List<Integer> playerPoints){
        int i=0;
        List<PlayerStats> playerStatsList = new ArrayList<>();
        while(i<playerIds.size()){
            playerStatsList.add(createPlayerStats(playerIds.get(i), playerPoints.get(i)));
            i++;
        }

        return playerStatsList;
        }
    }

