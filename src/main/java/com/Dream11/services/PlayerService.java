package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    PlayerRepo playerRepo;

    public Player getPlayerFromId(int playerId){
        if(playerRepo.findById(playerId).isPresent()){
            return playerRepo.findById(playerId).get();
        }
        else {
            System.out.println("PlayerId not found!");
            return null;
        }

    }
}
