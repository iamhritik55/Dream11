package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.entity.PlayerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerStatsService {
    @Autowired
    private PlayerService playerService;

    public PlayerStats convertPlayerToPlayerStat(Player player){
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayerName(player.getName());
        playerStats.setPlayerId(player.getId());
        return playerStats;
    }
}
