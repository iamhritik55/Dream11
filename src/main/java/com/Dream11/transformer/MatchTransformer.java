package com.Dream11.transformer;

import com.Dream11.DTO.MatchDTO;
import com.Dream11.DTO.MatchRequestDTO;
import com.Dream11.DTO.MatchResponseDTO;
import com.Dream11.entity.Match;

public class MatchTransformer {
//    public static MatchDTO matchToDTO(Match match){// add null checks
//        MatchDTO matchDTO=new MatchDTO();
//        matchDTO.setMatchId(match.getMatchId()); // remove matchid
//        matchDTO.setTeam1Id(match.getTeam1Id());
//        matchDTO.setTeam2Id(match.getTeam2Id());
//        matchDTO.setTeam1Score(match.getTeam1Score());
//        matchDTO.setTeam2Score(match.getTeam2Score());
//        return matchDTO;
//    }
//    public static Match DTOToMatch(MatchDTO matchDTO){
//        Match match=new Match();
//        match.setMatchId(matchDTO.getMatchId());
//        match.setTeam1Id(matchDTO.getTeam1Id());
//        match.setTeam2Id(matchDTO.getTeam2Id());
//        match.setTeam1Score(matchDTO.getTeam1Score());
//        match.setTeam2Score(matchDTO.getTeam2Score());
//        return match;
//    }
    public static Match requestDtoToMatch(MatchRequestDTO matchRequestDTO){
        Match match=new Match();
        match.setTeam1Id(matchRequestDTO.getTeam1Id());
        match.setTeam2Id(matchRequestDTO.getTeam2Id());
        return match;
    }
    public static MatchResponseDTO MatchToResponseDto(Match match){
        MatchResponseDTO matchResponseDTO=new MatchResponseDTO();
        matchResponseDTO.setTeam1Id(match.getTeam1Id());
        matchResponseDTO.setTeam2Id(match.getTeam2Id());
        matchResponseDTO.setTeam1Score(match.getTeam1Score());
        matchResponseDTO.setTeam2Score(match.getTeam2Score());
        return matchResponseDTO;
    }
}
