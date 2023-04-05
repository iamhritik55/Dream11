package com.Dream11.services;

import com.Dream11.DTO.request.MatchRequestDTO;
import com.Dream11.DTO.response.MatchResponseDTO;
import com.Dream11.DTO.response.TeamDetailsResponse;
import com.Dream11.services.enums.MatchStatus;
import com.Dream11.services.models.Match;
import com.Dream11.services.repo.DAO.MatchDAO;
import com.Dream11.services.repo.MatchRepo;
import com.Dream11.services.validation.MatchValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

import static com.Dream11.services.transformer.MatchTransformer.*;


@Service
@Slf4j
public class MatchService {
    @Autowired
    private  MatchRepo matchRepo;
    @Autowired
    private  UtilityService utilityService;

    public MatchService(MatchRepo matchRepo, UtilityService utilityService){
        this.matchRepo = matchRepo;
        this.utilityService = utilityService;
    }
    @Autowired
    private MatchValidation matchValidation;

    public MatchResponseDTO addMatch(MatchRequestDTO matchRequestDTO) throws Exception {
        matchValidation.validateMatch(matchRequestDTO);
        Match match = requestDtoToMatch(matchRequestDTO);
        match.setStatus(MatchStatus.UNPLAYED);
        return generateMatchResponseDto(matchRepo.save(match));
    }

    public List<MatchResponseDTO> getMatches() {
        List<Match> matches = matchRepo.findAll();
        return utilityService.createListOfMatchResponseDTO(matches);
    }

    public Match getMatchById(String matchId) throws Exception {
        return matchRepo.findById(matchId)
                        .orElseThrow(() -> new Exception("Match with matchId - " + matchId + " " + "doesn't exist"));
    }

//    public List<MatchResponseDTO> getUnplayedMatches() {
//        List<Match> matches = matchRepo.findMatchByStatus(MatchStatus.UNPLAYED);
//        return utilityService.createListOfMatchResponseDTO(matches);
//    }

//    public List<MatchResponseDTO> getPlayedMatches() {
//        List<Match> matches = matchRepo.findMatchByStatus(MatchStatus.PLAYED);
//        return utilityService.createListOfMatchResponseDTO(matches);
//    }

    public TeamDetailsResponse getTeamDetails(String matchId) throws Exception {
        MatchDAO match = matchToDao(getMatchById(matchId));
        return utilityService.createTeamDetails(match);
    }

    public void matchCompleted(String matchId) {
        Match match = matchRepo.findById(matchId).get();
//        matchId can't be null as this method is called after match completion
        match.setStatus(MatchStatus.PLAYED);
        matchRepo.save(match);
    }
}
