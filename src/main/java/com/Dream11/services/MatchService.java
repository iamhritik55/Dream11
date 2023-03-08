package com.Dream11.services;

import com.Dream11.entity.*;

import com.Dream11.entity.MatchDetails;
import com.Dream11.repo.MatchDetailsRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.MatchUserStatsRepo;
import com.Dream11.repo.MatchRepo;
import com.Dream11.utility.CombinedMatchUserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private InningService inningService;
    @Autowired
    public MatchRepo matchRepo;
    @Autowired
    private MatchDetailsRepo matchDetailsRepo;
    @Autowired
    private MatchUserStatsRepo matchUserStatsRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchDetailsService matchDetailsService;
    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private PlayerService playerService;

    public MatchUserStats addMatchUserStats (MatchUserStats matchUserStats){
        return matchUserStatsRepo.save(matchUserStats);
    }
    public List<MatchUserStats> getAllStats(){
        return matchUserStatsRepo.findAll();
    }
    public MatchUserStats getUserStats(CombinedMatchUserId combinedMatchUserId) throws Exception {
        MatchUserStats matchUserStats = new MatchUserStats();
        //finding matchUserStats by passing matchId and userId
        Optional<MatchUserStats> optionalStats = matchUserStatsRepo.findById(combinedMatchUserId);
        if(optionalStats.isPresent()){
            MatchUserStats stats = optionalStats.get();

            //making a list to store player names
            List<String> players = new ArrayList<>();

            //if player present then fetch player names by their ids
            for(Integer playerId : stats.getChosenPlayerIdList()){
                Player player = playerRepo.findById(playerId).orElse(null);
                if(player != null){
                    players.add(player.getName());
                }
            }
            matchUserStats.setId(stats.getId());
            matchUserStats.setCreditChange(stats.getCreditChange());
            matchUserStats.setTeamPoints(stats.getTeamPoints());
            matchUserStats.setChosenPlayerIdList(stats.getChosenPlayerIdList());
            matchUserStats.setPlayerName(players);
            return matchUserStats;
        }else{
            throw new Exception("Data not found");
        }
    }
    SecureRandom secureRandom = new SecureRandom();

    public MatchDetails addMatch(MatchDetails matchDetails) {
        return matchRepo.save(matchDetails);
    }

    public List<MatchDetails> getMatches() {
        return matchRepo.findAll();
    }

    public MatchDetails getMatch(int matchId) {
        return matchRepo.findById(matchId).get();
    }

    public void startMatch(int matchId) {
        //So I am fetch team1Id and team2Id from matchDetails
        MatchDetails matchDetails;
        if (matchDetailsRepo.findById(matchId).isPresent()) {
            matchDetails = matchDetailsRepo.findById(matchId).get();
        } else {
            System.out.println("MatchID not found!");
            return;
        }


        Team team1 = teamService.getTeamBYId(matchDetails.getTeam1Id());
        Team team2 = teamService.getTeamBYId(matchDetails.getTeam2Id());

        //Toss 0-> team1 wins and bats, 1-> team2 wins and bats
        if (secureRandom.nextInt(2) == 0) {
            System.out.println(team1.getName() + " has won the toss and chosen to bat!");
            inningService.playInning(team1, team2, true, matchId);
            inningService.playInning(team2, team1, false, matchId);
        } else {
            System.out.println(team2.getName() + " has won the toss and chosen to bat!");
            inningService.playInning(team2, team1, true, matchId);
            inningService.playInning(team1, team2, false, matchId);
        }

        int team1score = matchDetailsService.getTeamScore(matchId, matchDetails.getTeam1Id());
        int team2score = matchDetailsService.getTeamScore(matchId, matchDetails.getTeam2Id());

        if (team1score > team2score) {
            System.out.println(team1.getName() + " has won the match");
        } else if (team2score > team1score) {
            System.out.println(team2.getName() + " has won the match");
        } else {
            System.out.println("The match is tied!");

        }
    }

    }
