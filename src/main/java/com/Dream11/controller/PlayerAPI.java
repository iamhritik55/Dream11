package com.Dream11.controller;

import com.Dream11.entity.Player;
import com.Dream11.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerAPI {
    @Autowired
    public PlayerService playerService;
    @PostMapping()
    public Player addPlayer(@RequestBody Player player){
        return playerService.addPlayer(player);
    }

    @GetMapping
    public List<Player> getPlayers(){
        return playerService.getPlayers();
    }
    @GetMapping("/{playerId}")
    public Player getPlayer(@PathVariable int playerId){
        return playerService.getPlayer(playerId);
    }
}
