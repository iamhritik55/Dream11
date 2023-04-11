package com.Dream11.api;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerAPI {

    @Autowired
    public PlayerService playerService;

    @PostMapping()
    public ResponseEntity<PlayerResponseDTO> addPlayer(@RequestBody @Validated PlayerRequestDTO playerRequestDTO)
            throws Exception {
        return ResponseEntity.ok(playerService.addPlayer(playerRequestDTO));
    }

    @GetMapping
    public ResponseEntity< List<PlayerResponseDTO>> getAllPlayers() {
        return ResponseEntity.ok( playerService.getPlayers());
    }

    @GetMapping("/getPlayer/{id}")
    public ResponseEntity< PlayerResponseDTO> getPlayerById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok( playerService.getPlayer(id));
    }
}
