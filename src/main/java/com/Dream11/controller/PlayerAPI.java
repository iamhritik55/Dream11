package com.Dream11.controller;

import com.Dream11.DTO.PlayerDTO;
import com.Dream11.DTO.PlayerRequestDTO;
import com.Dream11.DTO.PlayerResponseDTO;
import com.Dream11.entity.Player;
import com.Dream11.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PlayerResponseDTO> addPlayer(@RequestBody PlayerRequestDTO playerRequestDTO)
            throws Exception {
        return ResponseEntity.ok(playerService.addPlayer(playerRequestDTO));
    }

    @GetMapping
    public ResponseEntity< List<PlayerResponseDTO>> getAllPlayers() {
        return ResponseEntity.ok( playerService.getPlayers());
    }

    @GetMapping("/getPlayer/{playerId}")
    public ResponseEntity< PlayerResponseDTO> getPlayerById(@PathVariable String playerId) throws Exception {
        return ResponseEntity.ok( playerService.getPlayer(playerId));
    }
}
