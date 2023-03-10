package com.Dream11.controller;

import com.Dream11.DTO.TeamDTO;
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
    public ResponseEntity<Object> addTeam(@RequestBody TeamDTO teamDTO) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(teamService.addTeam(DTOToTeam(teamDTO)));
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<TeamDTO> getTeams() {
        return teamService.getTeams();
    }

    @GetMapping("/{teamId}")
    public TeamDTO getTeam(@PathVariable String teamId) {
        try {
            return teamToDTO(teamService.getTeam(teamId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
