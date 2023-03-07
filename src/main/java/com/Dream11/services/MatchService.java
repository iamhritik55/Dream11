package com.Dream11.services;

import com.Dream11.entity.*;
import com.Dream11.repo.MatchDetailsRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.MatchUserStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {
    @Autowired
    private MatchDetailsRepo matchDetailsRepo;
    @Autowired
    private MatchUserStatsRepo matchUserStatsRepo;
    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
//    private final List<MatchUserStats> statsList = new ArrayList<>();
    public MatchUserStats addMatchUserStats (MatchUserStats matchUserStats){
        return matchUserStatsRepo.save(matchUserStats);
    }
    public List<MatchUserStats> getAllStats(){
        return matchUserStatsRepo.findAll();
    }
    public MatchUserStats getUserStats(int matchId, int userId) throws Exception {

        //finding matchUserStats by passing matchId and userId
        Optional<MatchUserStats> optionalStats = matchUserStatsRepo.findByMatchIdAndUserId(matchId, userId);
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
            return new MatchUserStats(stats.getMatchId(), stats.getUserId(), stats.getCreditChange(), stats.getTeamPoints(), stats.getChosenPlayerIdList(), players);
        }else{
            throw new Exception("Data not found");
        }
    }
    public void startMatch(int matchId){
        //So I am fetch team1Id and team2Id from matchDetails
        MatchDetails matchDetails;
        if(matchDetailsRepo.findById(matchId).isPresent()){
            matchDetails = matchDetailsRepo.findById(matchId).get();
        }
        else{
            System.out.println("MatchID not found!");
            return;
        }

        Team team1 = teamService.getTeamBYId(matchDetails.getTeam1Id());
        Team team2 = teamService.getTeamBYId(matchDetails.getTeam2Id());

        //Now I want to fetch List<Players> from List<playerId>
        List<Player> playerList1 = new ArrayList<>();
        for(int playerId: team1.getTeamPlayerIds()){
            playerList1.add(playerService.getPlayerFromId(playerId));
        }

        List<Player> playerList2 = new ArrayList<>();
        for(int playerId: team2.getTeamPlayerIds()){
            playerList2.add(playerService.getPlayerFromId(playerId));
        }
        

    }

}
