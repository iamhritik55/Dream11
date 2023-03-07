package com.Dream11.controller;

import com.Dream11.entity.Team;
import com.Dream11.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamAPI {
    @Autowired
    public TeamService teamService;
    @PostMapping
    public Team addTeam(@RequestBody Team team){
        return teamService.addTeam(team);
    }

    @GetMapping
    public List<Team> getTeams(){
        return teamService.getTeams();
    }
    @GetMapping("/{teamId}")
    public Team getTeam(@PathVariable int teamId){
        return teamService.getTeam(teamId);
    }
}
