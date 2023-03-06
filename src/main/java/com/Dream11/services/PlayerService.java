package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepo playerRepo;

    public Player getPlayerFromId(int playerId) {
        if (playerRepo.findById(playerId).isPresent()) {
            return playerRepo.findById(playerId).get();
        } else {
            System.out.println("PlayerId not found!");
            return null;
        }
    }

    public Player addPlayer (Player player){
        return playerRepo.save(player);
    }

    public List<Player> getPlayers () {
        return playerRepo.findAll();
    }
}

