package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepo playerRepo;

    public Player getPlayerFromId(String playerId) {
        Player playerValue = null;
        Optional<Player> player = playerRepo.findById(playerId);
        if (player.isPresent()) {
            playerValue = player.get();
        }
        return playerValue;

    }

    public List<Player> getPlayerListFromIdList(List<String> playerIdList) {
        List<Player> playerList = new ArrayList<>();
        for (String playerId : playerIdList) {
            playerList.add(getPlayerFromId(playerId));
        }
        return playerList;
    }

    public Player addPlayer(Player player) {
        return playerRepo.save(player);
    }

    public List<Player> getPlayers() {
        return playerRepo.findAll();
    }
}

