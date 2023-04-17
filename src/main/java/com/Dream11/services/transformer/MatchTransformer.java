package com.Dream11.services.transformer;

import com.Dream11.services.repo.DAO.MatchDAO;
import com.Dream11.DTO.request.MatchRequestDTO;
import com.Dream11.DTO.response.MatchResponseDTO;
import com.Dream11.services.models.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchTransformer {

    public static Match requestDtoToMatch(MatchRequestDTO matchRequestDTO) {
        Match match = new Match();
        match.setTeam1Id(matchRequestDTO.getTeam1Id());
        match.setTeam2Id(matchRequestDTO.getTeam2Id());
        return match;
    }

    public static MatchResponseDTO generateMatchResponseDto(Match match) {
        MatchResponseDTO matchResponseDTO = new MatchResponseDTO();
        matchResponseDTO.setId(match.getMatchId());
        matchResponseDTO.setTeam1Id(match.getTeam1Id());
        matchResponseDTO.setTeam2Id(match.getTeam2Id());

        return matchResponseDTO;
    }

    public static MatchDAO matchToDao(Match match) {
        MatchDAO matchDAO = new MatchDAO();
        matchDAO.setTeam1Id(match.getTeam1Id());
        matchDAO.setTeam2Id(match.getTeam2Id());
        return matchDAO;
    }
    public static List<MatchResponseDTO> createListOfMatchResponseDTO(List<Match> matches) {
        List<MatchResponseDTO> matchResponseDTOS = new ArrayList<>();
        for (Match match : matches) {
            matchResponseDTOS.add(generateMatchResponseDto(match));
        }
        return matchResponseDTOS;
    }
}
