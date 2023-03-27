package com.Dream11.services;

import com.Dream11.DAO.MatchDAO;
import com.Dream11.DTO.*;
import com.Dream11.entity.*;
import com.Dream11.helperClasses.MatchStatus;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.services.validation.MatchValidation;
import com.Dream11.utility.MatchUtils;
import com.Dream11.utility.TeamDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import static com.Dream11.Counter.counter;
import static com.Dream11.transformer.MatchTransformer.*;

@Service
@Slf4j
public class MatchService {

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchDetailsService matchDetailsService;
    @Autowired
    private InningService inningService;
    @Autowired
    private MatchUtils matchUtils;
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
    public List<MatchUserStats> startMatch(String matchId) throws Exception {
        counter = 0;
        //Fetching match object from db
        // TODO: 16/03/23 create a context ,first fetch all the data then start the logic
        Match match = matchDetailsService.findMatchDetailsById(matchId);
        if (match.getStatus() == MatchStatus.PLAYED) {
            throw new Exception("Match has already happened!");
        }
        //Create an object of matchStats and store it in DB
        matchStatsService.createMatchStats(matchId);
        // TODO: 16/03/23
        //Fetching team objects from db
        Team team1 = teamService.getTeamById(match.getTeam1Id());
        Team team2 = teamService.getTeamById(match.getTeam2Id());
        MatchStats matchStats;
        List<MatchUserStats> matchUserStatsList;
        //Toss 0-> team1 wins and bats, 1-> team2 wins and bats
        if (secureRandom.nextInt(2) == 0) {
            System.out.println(team1.getName() + " has won the toss and chosen to bat!");
            matchUserStatsList = inningService.playInning(team1, team2, true, matchId);
            matchUserStatsList = inningService.playInning(team2, team1, false, matchId);
        } else {
            System.out.println(team2.getName() + " has won the toss and chosen to bat!");
            matchUserStatsList = inningService.playInning(team2, team1, true, matchId);
            matchUserStatsList = inningService.playInning(team1, team2, false, matchId);
        }// TODO- create a toss method in separate file

        long team1score = matchDetailsService.getTeamScore(matchId, match.getTeam1Id());
        long team2score = matchDetailsService.getTeamScore(matchId, match.getTeam2Id());

        String winnerTeamName = matchUtils.declareWinner(team1, team2, team1score, team2score);
        matchStats = matchStatsService.declareWinner(matchId, winnerTeamName);
        matchDetailsService.matchCompleted(matchId);
        //Updating points for user

        matchUserService.updateWinnerUserPoints(matchId);
        return matchUserService.findByMatchId(matchId);
    }

    @Autowired
    MatchValidation matchValidation;

    public MatchResponseDTO addMatch(MatchRequestDTO matchRequestDTO) throws Exception {
        matchValidation.validateMatch(matchRequestDTO);
        Match match = requestDtoToMatch(matchRequestDTO);
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

    public TeamDetails getTeamDetails(String matchId) throws Exception {
        MatchDAO match = matchToDao(getMatch(matchId)); // TODO: 17/03/23 add matchDAO  instead of using match-done
        TeamDetails teamDetails = utilityService.createTeamDetails(match);
        return teamDetails;
    }

}
