package com.Dream11.utility;

import com.Dream11.services.models.PlayerStats;

import java.util.List;
import java.util.stream.Collectors;

public class MatchStatsUtility {
    public static List<PlayerStats> segregateCombinedPlayerStatsList(List<PlayerStats> combinedPlayerStats, List<String> playerIdList){
       return combinedPlayerStats.stream().filter(playerStats -> playerIdList.contains(playerStats.getPlayerId())).collect(Collectors.toList());
    }
}
