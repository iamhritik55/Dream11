package com.Dream11.controller;

import com.Dream11.DTO.request.TeamRequestDTO;
import com.Dream11.DTO.response.TeamResponseDTO;
import com.Dream11.repo.TeamRepo;
import com.Dream11.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamAPI {

    @Autowired
    public TeamService teamService;
    @Autowired
    public TeamRepo teamRepo;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> addTeam(@RequestBody @Validated TeamRequestDTO teamRequestDTO) throws Exception {
        return ResponseEntity.ok(teamService.addTeam(teamRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable String teamId) throws Exception {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }
}
