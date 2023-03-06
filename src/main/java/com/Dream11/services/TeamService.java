package com.Dream11.services;

import com.Dream11.entity.Team;
import com.Dream11.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepo teamRepo;

    public Team addTeam(Team team) {
        return teamRepo.save(team);
    }
    public List<Team> getTeams(){
        return teamRepo.findAll();
    }

    public Team getTeam(int teamId) {
        try {
            return teamRepo.findById(teamId).get();
        }
        catch (Exception e){
            System.out.println("Team with teamId - "+teamId+" doesn't exist");
            return null;
        }
    }
    public Team getTeamBYId(int teamId) {
        if (teamRepo.findById(teamId).isPresent()) {
            return teamRepo.findById(teamId).get();
        }
        else {
            System.out.println("TeamID invalid!");
            return null;
        }

    }
}
