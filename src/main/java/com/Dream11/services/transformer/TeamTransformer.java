package com.Dream11.services.transformer;

import com.Dream11.DTO.request.TeamRequestDTO;
import com.Dream11.DTO.response.TeamResponseDTO;
import com.Dream11.services.models.Team;

public class TeamTransformer {
    public static Team requestDtoToTeam(TeamRequestDTO teamRequestDTO){
        Team team=new Team();
        team.setName(teamRequestDTO.getName());
        team.setTeamPlayerIds(teamRequestDTO.getTeamPlayerIds());
        return team;
    }
    public static TeamResponseDTO teamToResponseDto(Team team){
        TeamResponseDTO teamResponseDTO=new TeamResponseDTO();
        teamResponseDTO.setId(team.getId());
        teamResponseDTO.setName(team.getName());
        teamResponseDTO.setTeamPlayerIds(team.getTeamPlayerIds());
        return teamResponseDTO;
    }
}
