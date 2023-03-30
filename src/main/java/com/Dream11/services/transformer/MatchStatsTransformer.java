package com.Dream11.services.transformer;

import com.Dream11.services.models.Player;
import com.Dream11.services.models.PlayerStats;

import java.util.ArrayList;
import java.util.List;

public class MatchStatsTransformer {
    public static List<PlayerStats> createPlayerStatsList(List<Player> playerList){
        List<PlayerStats> playerStatsList = new ArrayList<>();
        for(Player player: playerList){
            playerStatsList.add(createPlayerStatsFromPlayer(player));
        }
        return playerStatsList;
    }

    public static PlayerStats createPlayerStatsFromPlayer(Player player){
        PlayerStats playerStats= new PlayerStats();
        playerStats.setPlayerId(player.getId());
        playerStats.setPlayerName(player.getName());
        playerStats.setPlayerPoints(player.getPlayerPoints());
        playerStats.setFoursScored(player.getFoursScored());
        playerStats.setBowlingWickets(player.getBowlingWickets());
        playerStats.setSixesScored(player.getSixesScored());
        playerStats.setBattingRuns(player.getBattingRuns());
        return playerStats;
    }
}
