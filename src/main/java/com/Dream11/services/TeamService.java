package com.Dream11.services;

import com.Dream11.DTO.TeamDTO;
import com.Dream11.controller.TeamAPI;
import com.Dream11.entity.Team;
import com.Dream11.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.LabelUI;
import java.util.ArrayList;
import java.util.List;

import static com.Dream11.transformer.TeamTransformer.teamToDTO;

@Service
public class TeamService {
    @Autowired
    public TeamRepo teamRepo;

    public Team addTeam(Team team) {
        return teamRepo.save(team);
    }
    public List<TeamDTO> getTeams(){
        List<TeamDTO> teamDTOs=new ArrayList<>();
        List<Team> teams=teamRepo.findAll();
        for (Team team:teams
             ) {
            teamDTOs.add(teamToDTO(team));
        }
        return teamDTOs;
    }

    public Team getTeam(String teamId) throws Exception{
        if(teamRepo.findById(teamId).isPresent()) return teamRepo.findById(teamId).get();
        else throw new Exception("team with teamId - "+teamId+"doesn't exist");
    }
}
