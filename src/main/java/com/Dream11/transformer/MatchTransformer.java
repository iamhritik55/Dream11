package com.Dream11.transformer;

import com.Dream11.DAO.MatchDAO;
import com.Dream11.DTO.request.MatchRequestDTO;
import com.Dream11.DTO.response.MatchResponseDTO;
import com.Dream11.entity.Match;

public class MatchTransformer {

    public static Match requestDtoToMatch(MatchRequestDTO matchRequestDTO){
        Match match=new Match();
        match.setTeam1Id(matchRequestDTO.getTeam1Id());
        match.setTeam2Id(matchRequestDTO.getTeam2Id());
        return match;
    }
    public static MatchResponseDTO MatchToResponseDto(Match match){
        MatchResponseDTO matchResponseDTO=new MatchResponseDTO();
        matchResponseDTO.setId(match.getMatchId());
        matchResponseDTO.setTeam1Id(match.getTeam1Id());
        matchResponseDTO.setTeam2Id(match.getTeam2Id());

        return matchResponseDTO;
    }
    public static MatchDAO matchToDao(Match match){
        MatchDAO matchDAO=new MatchDAO();
        matchDAO.setTeam1Id(match.getTeam1Id());
        matchDAO.setTeam2Id(match.getTeam2Id());
        return matchDAO;
    }
}
