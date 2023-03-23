package com.Dream11.controller;

import com.Dream11.DTO.TeamDTO;
import com.Dream11.DTO.TeamRequestDTO;
import com.Dream11.DTO.TeamResponseDTO;
import com.Dream11.repo.TeamRepo;
import com.Dream11.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.Dream11.transformer.TeamTransformer.DTOToTeam;
import static com.Dream11.transformer.TeamTransformer.teamToDTO;

@RestController
@RequestMapping("/team")
public class TeamAPI {

    @Autowired
    public TeamService teamService;
    @Autowired
    public TeamRepo teamRepo;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> addTeam(@RequestBody TeamRequestDTO teamRequestDTO) throws Exception {
        return ResponseEntity.ok(teamService.addTeam(teamRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> getTeam(@PathVariable String teamId) throws Exception {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }
}
