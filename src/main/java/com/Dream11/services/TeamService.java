package com.Dream11.services;

import com.Dream11.DTO.TeamDTO;
import com.Dream11.entity.Team;
import com.Dream11.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.Dream11.Counter.counter;

import java.util.ArrayList;
import java.util.List;

import static com.Dream11.transformer.TeamTransformer.teamToDTO;

@Service
public class TeamService {
    @Autowired
    TeamRepo teamRepo;
    public Team getTeamBYId(String teamId) throws Exception{
        Optional<Team> teamOptional = teamRepo.findById(teamId);
        if (teamOptional.isPresent()) {
            counter++;
            return teamOptional.get();
        }
        else {
            throw new Exception("Invalid teamId!");
        }
    }
    @Autowired
    TeamValidation teamValidation ;
    public Team addTeam(Team team) throws Exception{
        if(teamValidation.teamValid(team)) return teamRepo.save(team);
        else throw new Exception("Invalid playerId found in TeamPlayerIdList");
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

    public Team getTeam(String teamId) throws Exception{ // TODO: 16/03/23 make one repo call
        if(teamRepo.findById(teamId).isPresent()) return teamRepo.findById(teamId).get();
        else throw new Exception("team with teamId - "+teamId+"doesn't exist");
    }
}
