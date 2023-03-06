package com.Dream11.controller;

import com.Dream11.entity.MatchDetails;
import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.repo.MatchRepo;
import com.Dream11.services.MatchService;
import com.Dream11.services.PlayerService;
import com.Dream11.services.TeamService;
import com.Dream11.utility.DispTeamDetResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchAPI {
    @Autowired
    public MatchService matchService;
    @Autowired
    public TeamService teamService; //doubt- can I use teamService in matchAPI
    @Autowired
    public PlayerService playerService;//same doubt as above
    @PostMapping
    public MatchDetails addMatch(@RequestBody MatchDetails matchDetails){
        return matchService.addMatch(matchDetails);
    }
    @GetMapping
    public List<MatchDetails> getMatches(){
        return matchService.getMatches();
    }
    @GetMapping("/{matchId}")
    public DispTeamDetResp displayTeamDetails(@PathVariable int matchId){
        MatchDetails matchDetails;
        try {
            matchDetails=matchService.getMatch(matchId);
        }
        catch (Exception e){
            System.out.println("match with matchId - "+matchId+" doesn't exist");
            return null;
        }
        DispTeamDetResp dispTeamDetResp=new DispTeamDetResp();
        dispTeamDetResp.setTeam1Id(matchDetails.getTeam1Id());
        dispTeamDetResp.setTeam2Id(matchDetails.getTeam2Id());
        Team team1=teamService.getTeam(matchDetails.getTeam1Id());
        Team team2=teamService.getTeam(matchDetails.getTeam2Id());
        dispTeamDetResp.setTeam1Name(team1.getName());
        dispTeamDetResp.setTeam2Name(team2.getName());
        List<Integer> team1PlayerIds=team1.getTeamPlayerIds();
        List<Integer> team2PlayerIds=team2.getTeamPlayerIds();
        List<Player> team1Players=new ArrayList<>();
        List<Player> team2Players=new ArrayList<>();

        for (Integer playerId: team1PlayerIds
             ) {
            team1Players.add(playerService.getPlayer(playerId));
        }
        for (Integer playerId: team2PlayerIds
        ) {
            team2Players.add(playerService.getPlayer(playerId));
        }
        dispTeamDetResp.setTeam1Players(team1Players);
        dispTeamDetResp.setTeam2Players(team2Players);
        return dispTeamDetResp;
    }

}
