package com.Dream11.services;

import com.Dream11.DTO.TeamDTO;
import com.Dream11.DTO.TeamRequestDTO;
import com.Dream11.DTO.TeamResponseDTO;
import com.Dream11.entity.Team;
import com.Dream11.repo.TeamRepo;
import com.Dream11.services.validation.TeamValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.Dream11.Counter.counter;
import static com.Dream11.transformer.TeamTransformer.*;

import java.util.ArrayList;
import java.util.List;

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
    public TeamResponseDTO addTeam(TeamRequestDTO teamRequestDTO) throws Exception{
        teamValidation.teamValid(teamRequestDTO);
        Team team=requestDtoToTeam(teamRequestDTO);
        return teamToResponseDTO(teamRepo.save(team));
    }
    public List<TeamResponseDTO> getTeams(){
        List<TeamResponseDTO> teamResponseDTOS=new ArrayList<>();
        List<Team> teams=teamRepo.findAll();
        for (Team team:teams
             ) {
            teamResponseDTOS.add(teamToResponseDTO(team));
        }
        return teamResponseDTOS;
    }

    public TeamResponseDTO getTeam(String teamId) throws Exception{ // TODO: 16/03/23 make one repo call-done
        Optional<Team> team=teamRepo.findById(teamId);
        if(team.isPresent()) return teamToResponseDTO(team.get());
        else throw new Exception("team with teamId - "+teamId+"doesn't exist");
    }
}
