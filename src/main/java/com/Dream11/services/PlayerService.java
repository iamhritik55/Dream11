package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;

    public Player getPlayerFromId(int playerId){
        if(playerRepository.findById(playerId).isPresent()){
            return playerRepository.findById(playerId).get();
        }
        else {
            System.out.println("PlayerId not found!");
            return null;
        }

    }
}
