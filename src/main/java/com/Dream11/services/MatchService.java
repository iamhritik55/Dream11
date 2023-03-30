package com.Dream11.services;

import com.Dream11.DTO.request.MatchRequestDTO;
import com.Dream11.DTO.response.MatchResponseDTO;
import com.Dream11.DTO.response.TeamDetailsResponse;
import com.Dream11.services.enums.MatchStatus;
import com.Dream11.services.models.Match;
import com.Dream11.services.repo.DAO.MatchDAO;
import com.Dream11.services.repo.MatchRepo;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.services.validation.MatchValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import static com.Dream11.services.transformer.MatchTransformer.*;


@Service
@Slf4j
public class MatchService {

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchStatsService matchStatsService;
    @Autowired
    private MatchUserService matchUserService;
    @Autowired
    public PlayerService playerService;
    @Autowired
    public PlayerRepo playerRepo;
    @Autowired
    public UtilityService utilityService;
    SecureRandom secureRandom = new SecureRandom();

    //validation and create context
    //TODO create a MatchController service

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

//    public Match getMatch(String matchId) throws Exception {
//        Optional<Match> match = matchRepo.findById(matchId); // TODO: 28/03/23 use ifPresent()
//        if (match.isPresent()) {
//            return match.get();
//        } else {
//            throw new Exception("Match with matchId - " + matchId + " doesn't exist");
//        }
//    }
public Match getMatch(String matchId) throws Exception {
    return matchRepo.findById(matchId)
            .orElseThrow(() -> new Exception("Match with matchId - " + matchId + " doesn't exist"));
}

    public List<MatchResponseDTO> getUnplayedMatches() {
        List<Match> matches = matchRepo.findMatchesByStatus(MatchStatus.UNPLAYED);
        return utilityService.createListOfMatchResponseDTO(matches);
    }

    public List<MatchResponseDTO> getPlayedMatches() {
        List<Match> matches = matchRepo.findMatchesByStatus(MatchStatus.PLAYED);
        return utilityService.createListOfMatchResponseDTO(matches);
    }

    public TeamDetailsResponse getTeamDetails(String matchId) throws Exception {
        MatchDAO match = matchToDao(getMatch(matchId)); // TODO: 17/03/23 add matchDAO  instead of using match-done
        return utilityService.createTeamDetails(match);
    }

    public void matchCompleted(String matchId) {
        Match match = matchRepo.findById(matchId).get();
        match.setStatus(MatchStatus.PLAYED);
        matchRepo.save(match);
    }
}
