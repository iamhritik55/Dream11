package com.Dream11.services;

import com.Dream11.DTO.MatchRequestDTO;
import com.Dream11.DTO.MatchResponseDTO;
import com.Dream11.DTO.PlayerDTO;
import com.Dream11.DTO.TeamResponseDTO;
import com.Dream11.entity.*;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.services.validation.MatchValidation;
import com.Dream11.utility.MatchUtils;
import com.Dream11.utility.TeamDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Dream11.Counter.counter;
import static com.Dream11.transformer.MatchTransformer.*;
import static com.Dream11.transformer.PlayerTransformer.playerToDTO;

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
        if (match.getCompleted()==MatchStatus.PLAYED) {
            throw new Exception("Match has already happened!");
        }
        //Create an object of matchStats and store it in DB
        matchStatsService.createMatchStats(matchId);
        // TODO: 16/03/23
        //Fetching team objects from db
        Team team1 = teamService.getTeamBYId(match.getTeam1Id());
        Team team2 = teamService.getTeamBYId(match.getTeam2Id());
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
        List<MatchResponseDTO> matchResponseDTOS=utilityService.createListOfMatchResponse(matches);
        return matchResponseDTOS;
    }

    public Match getMatch(String matchId) throws Exception {// TODO: 16/03/23 make only one repo call-done
        Optional<Match> match=matchRepo.findById(matchId);
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new Exception("Match with matchId - " + matchId + " doesn't exist");
        }
    }

    public List<MatchResponseDTO> getUnplayedMatches() {
        List<Match> matches= matchRepo.findMatchesByStatus(MatchStatus.UNPLAYED);
        List<MatchResponseDTO> matchResponseDTOS = utilityService.createListOfMatchResponse(matches);
        return matchResponseDTOS;
    }

    public List<MatchResponseDTO> getPlayedMatches() {
        List<Match> matches= matchRepo.findMatchesByStatus(MatchStatus.PLAYED);
        List<MatchResponseDTO> matchResponseDTOS = utilityService.createListOfMatchResponse(matches);
        return matchResponseDTOS;
    }

    public TeamDetails getTeamDetails(String matchId) throws Exception {
        Match match = getMatch(matchId); // TODO: 17/03/23 add matchDAO  instead of using match
        TeamDetails teamDetails = new TeamDetails();
        teamDetails.setTeam1Id(match.getTeam1Id());
        teamDetails.setTeam2Id(match.getTeam2Id());
        TeamResponseDTO team1 = teamService.getTeam(match.getTeam1Id());
        TeamResponseDTO team2 = teamService.getTeam(match.getTeam2Id());// TODO: 16/03/23 one repo call should be there
        teamDetails.setTeam1Name(team1.getName());
        teamDetails.setTeam2Name(team2.getName());
        List<String> team1PlayerIds = team1.getTeamPlayerIds();
        List<String> team2PlayerIds = team2.getTeamPlayerIds();
        List<PlayerDTO> team1PlayerDTOs = new ArrayList<>();
        List<PlayerDTO> team2PlayerDTOs = new ArrayList<>();
        List<Player> team1Players = playerRepo.findAllById(team1PlayerIds);//todo make code cleaner
        List<Player> team2Players = playerRepo.findAllById(team2PlayerIds);// TODO: 16/03/23 only one repo call should be there
        for (Player player : team1Players) {
            team1PlayerDTOs.add(playerToDTO(player));
        }
        for (Player player : team2Players) {
            team2PlayerDTOs.add(playerToDTO(player));
        } // TODO: 16/03/23 make a separate method for these

        //        if (!CollectionUtils.isEmpty(team1PlayerIds)) {
        //            for (String playerId : team1PlayerIds) {
        //                team1Players.add(playerService.getPlayer(playerId));
        //            }
        //        }
        //
        //        for (String playerId : team2PlayerIds) {
        //            team2Players.add(playerService.getPlayer(playerId));
        //        }
        teamDetails.setTeam1Players(team1PlayerDTOs);
        teamDetails.setTeam2Players(team2PlayerDTOs);
        return teamDetails;
    }

}
