package com.Dream11.controller;

import com.Dream11.DTO.PlayerDTO;
import com.Dream11.entity.Player;
import com.Dream11.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.Dream11.transformer.PlayerTransformer.DTOToPlayer;
import static com.Dream11.transformer.PlayerTransformer.playerToDTO;

@RestController
@RequestMapping("/player")
public class PlayerAPI {
    @Autowired
    public PlayerService playerService;
    @PostMapping()
    public PlayerDTO addPlayer(@RequestBody PlayerDTO playerDTO){
        return playerToDTO(playerService.addPlayer(DTOToPlayer(playerDTO)));
    }

    @GetMapping
    public List<PlayerDTO> getPlayers(){
        return playerService.getPlayers();
    }
    @GetMapping("/{playerId}")
    public PlayerDTO getPlayer(@PathVariable String playerId){
        try {
            return playerToDTO(playerService.getPlayer(playerId));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
