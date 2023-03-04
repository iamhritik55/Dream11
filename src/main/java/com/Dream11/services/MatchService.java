package com.Dream11.services;

import com.Dream11.entity.MatchDetails;
import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.repo.MatchDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    @Autowired
    MatchDetailsRepo matchDetailsRepo;
    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;
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
