package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    public PlayerRepo playerRepo;

    public Player addPlayer(Player player) {
        return playerRepo.save(player);
    }

    public List<Player> getPlayers() {
        return playerRepo.findAll();
    }

    public Player getPlayer(String playerId) {
        try {
            return  playerRepo.findById(playerId).get();
        }
        catch (Exception e){
            System.out.println("Player with playerId - "+playerId+" doesn't exist");
            return null;
        }
    }
}
