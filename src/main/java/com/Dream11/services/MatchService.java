package com.Dream11.services;

import com.Dream11.DAO.MatchDAO;
import com.Dream11.DTO.request.MatchRequestDTO;
import com.Dream11.DTO.response.MatchResponseDTO;
import com.Dream11.entity.*;
import com.Dream11.enums.MatchStatus;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.services.validation.MatchValidation;
import com.Dream11.DTO.response.TeamDetailsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import static com.Dream11.transformer.MatchTransformer.*;


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
    MatchValidation matchValidation;
    public MatchResponseDTO addMatch(MatchRequestDTO matchRequestDTO) throws Exception {
        matchValidation.validateMatch(matchRequestDTO);
        Match match = requestDtoToMatch(matchRequestDTO);
        match.setStatus(MatchStatus.UNPLAYED);
        return MatchToResponseDto(matchRepo.save(match));
    }
    public List<MatchResponseDTO> getMatches() {
        List<Match> matches = matchRepo.findAll();
        List<MatchResponseDTO> matchResponseDTOS = utilityService.createListOfMatchResponseDTO(matches);
        return matchResponseDTOS;
    }

    public Match getMatch(String matchId) throws Exception {// TODO: 16/03/23 make only one repo call-done
        Optional<Match> match = matchRepo.findById(matchId);
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("Match with matchId - " + matchId + " doesn't exist");
        }
    }

    public List<MatchResponseDTO> getUnplayedMatches() {
        List<Match> matches = matchRepo.findMatchesByStatus(MatchStatus.UNPLAYED);
        List<MatchResponseDTO> matchResponseDTOS = utilityService.createListOfMatchResponseDTO(matches);
        return matchResponseDTOS;
    }

    public List<MatchResponseDTO> getPlayedMatches() {
        List<Match> matches = matchRepo.findMatchesByStatus(MatchStatus.PLAYED);
        List<MatchResponseDTO> matchResponseDTOS = utilityService.createListOfMatchResponseDTO(matches);
        return matchResponseDTOS;
    }

    public TeamDetailsResponse getTeamDetails(String matchId) throws Exception {
        MatchDAO match = matchToDao(getMatch(matchId)); // TODO: 17/03/23 add matchDAO  instead of using match-done
        TeamDetailsResponse teamDetails = utilityService.createTeamDetails(match);
        return teamDetails;
    }
    public void matchCompleted(String matchId){
        Match match = matchRepo.findById(matchId).get();
        match.setStatus(MatchStatus.PLAYED);
        matchRepo.save(match);
    }
}
