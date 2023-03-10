package com.Dream11.controller;

import com.Dream11.DTO.TeamDTO;
import com.Dream11.entity.Team;
import com.Dream11.repo.TeamRepo;
import com.Dream11.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public TeamDTO addTeam(@RequestBody TeamDTO teamDTO) {
        return teamToDTO(teamService.addTeam(DTOToTeam(teamDTO)));
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
