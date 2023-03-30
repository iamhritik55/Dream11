package com.Dream11.services;

import com.Dream11.DTO.request.TeamRequestDTO;
import com.Dream11.DTO.response.TeamResponseDTO;
import com.Dream11.services.models.Team;
import com.Dream11.services.repo.TeamRepo;
import com.Dream11.services.validation.TeamValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Dream11.services.transformer.TeamTransformer.*;

@Service
public class TeamService {

    @Autowired
    TeamRepo teamRepo;

    public Team getTeamById(String teamId) throws Exception {
        return teamRepo.findById(teamId).orElseThrow(() -> new Exception("Invalid teamId!"));
    }

    @Autowired
    TeamValidation teamValidation;

    public TeamResponseDTO addTeam(TeamRequestDTO teamRequestDTO) throws Exception {
        teamValidation.validateTeam(teamRequestDTO);
        Team team = requestDtoToTeam(teamRequestDTO);
        return teamToResponseDto(teamRepo.save(team));
    }

    public List<TeamResponseDTO> getTeams() {
        return createListOfTeamResponseDto(teamRepo.findAll());
    }

    public TeamResponseDTO getTeam(String teamId) throws Exception {
        return teamToResponseDto(teamRepo.findById(teamId).orElseThrow(
                () -> new Exception("team with teamId - " + teamId + "doesn't exist")));
    }

    public List<Team> getTeamsById(List<String> teamIds) {
        return teamRepo.findAllById(teamIds);
    }
}
