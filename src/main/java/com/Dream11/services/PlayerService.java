package com.Dream11.services;

import com.Dream11.DTO.PlayerDTO;
import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.Dream11.transformer.PlayerTransformer.playerToDTO;

@Service
public class PlayerService {

    @Autowired
    public PlayerRepo playerRepo;

    public Player addPlayer(Player player) {
        return playerRepo.save(player);
    }

    public List<PlayerDTO> getPlayers() {
        List<PlayerDTO> playerDTOs=new ArrayList<>();
        List<Player> players=playerRepo.findAll();
        for (Player player:players
             ) {
            playerDTOs.add(playerToDTO(player));
        }
        return playerDTOs;
    }

    public Player getPlayer(String playerId) throws Exception{
        if(playerRepo.findById(playerId).isPresent())
            return playerRepo.findById(playerId).get();
        else{
            throw new Exception("Player with playerID - "+playerId+"doesn't exist");
        }
    }
}
