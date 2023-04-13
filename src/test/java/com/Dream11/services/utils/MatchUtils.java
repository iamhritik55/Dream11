package com.Dream11.services.utils;

import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.DTO.response.TeamDetailsResponse;
import com.Dream11.services.models.Match;
import com.Dream11.services.repo.DAO.MatchDAO;

import java.util.List;

public class MatchUtils {

    public static Match createMatch(String testTeam1Id, String testTeam2Id) {
        Match match = new Match();
        match.setTeam1Id(testTeam1Id);
        match.setTeam2Id(testTeam2Id);
        return match;
    }
    public static MatchDAO createMatchDao(String testTeam1Id, String testTeam2Id) {
        MatchDAO matchDAO = new MatchDAO();
        matchDAO.setTeam1Id(testTeam1Id);
        matchDAO.setTeam2Id(testTeam2Id);
        return matchDAO;
    }
    public static TeamDetailsResponse createTeamDetailsResponse(String team1Id, String team2Id, String team1Name,
                                                                String team2Name, List<PlayerResponseDTO> team1Players,
                                                                List<PlayerResponseDTO> team2Players) {
        TeamDetailsResponse teamDetailsResponse = new TeamDetailsResponse();
        teamDetailsResponse.setTeam1Id(team1Id);
        teamDetailsResponse.setTeam2Id(team2Id);
        teamDetailsResponse.setTeam1Name(team1Name);
        teamDetailsResponse.setTeam2Name(team2Name);
        teamDetailsResponse.setTeam1Players(team1Players);
        teamDetailsResponse.setTeam2Players(team2Players);
        return teamDetailsResponse;
    }
}
