package com.Dream11.services;

import com.Dream11.DTO.PlayerDTO;
import com.Dream11.entity.*;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.utility.MatchUtils;
import com.Dream11.utility.TeamDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static com.Dream11.transformer.PlayerTransformer.playerToDTO;

@Service
public class MatchService {
    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchDetailsService matchDetailsService;
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
    SecureRandom secureRandom = new SecureRandom();
    public Match addMatch(Match match) {
        return matchRepo.save(match);
    }

    public List<Match> getMatches() {
        return matchRepo.findAll();
    }

    public Match getMatch(String matchId) throws Exception {// TODO: 16/03/23 make only one repo call
        if (matchRepo.findById(matchId).isPresent()) {
            return matchRepo.findById(matchId).get();
        } else {
            throw new Exception("Match with matchId - " + matchId + " doesn't exist");
        }
    }
    public List<Match> getUnplayedMatches(){
        return matchRepo.findMatchesByStatus(MatchStatus.UNPLAYED);
    }
    public List<Match> getPlayedMatches() {
        return matchRepo.findMatchesByStatus(MatchStatus.PLAYED);
    }
    public TeamDetails getTeamDetails(String matchId) throws Exception {
        Match match;
        match =getMatch(matchId);
        TeamDetails teamDetails = new TeamDetails();
        teamDetails.setTeam1Id(match.getTeam1Id());
        teamDetails.setTeam2Id(match.getTeam2Id());
        Team team1 = teamService.getTeam(match.getTeam1Id());
        Team team2 = teamService.getTeam(match.getTeam2Id());// TODO: 16/03/23 one repo call should be there
        teamDetails.setTeam1Name(team1.getName());
        teamDetails.setTeam2Name(team2.getName());
        List<String> team1PlayerIds = team1.getTeamPlayerIds();
        List<String> team2PlayerIds = team2.getTeamPlayerIds();
        List<PlayerDTO> team1PlayerDTOs = new ArrayList<>();
        List<PlayerDTO> team2PlayerDTOs = new ArrayList<>();
        List<Player> team1Players=playerRepo.findAllById(team1PlayerIds);//make code cleaner
        List<Player> team2Players=playerRepo.findAllById(team2PlayerIds);// TODO: 16/03/23 only one repo call should be there
        for (Player player:team1Players
             ) {
            team1PlayerDTOs.add(playerToDTO(player));
        }
        for (Player player:team2Players
        ) {
            team2PlayerDTOs.add(playerToDTO(player));
        } // TODO: 16/03/23 make a separate method for these

        teamDetails.setTeam1Players(team1PlayerDTOs);
        teamDetails.setTeam2Players(team2PlayerDTOs);
        return teamDetails;
    }

}
