package com.Dream11.context;


import com.Dream11.entity.Match;
import com.Dream11.entity.Team;
import com.Dream11.gamecontroller.CricketUtility;
import com.Dream11.services.MatchDetailsService;
import com.Dream11.services.TeamService;
import com.Dream11.validation.CricketMatchValidation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class CricketMatchContext {
    @Autowired
    MatchDetailsService matchDetailsService;
    @Autowired
    CricketMatchValidation matchValidation;
    @Autowired
    TeamService teamService;
    private Match match;
    private Team team1;
    private Team team2;
    private String tossWinner;
    private boolean secondInning;

    public CricketMatchContext fetchCricketContext(String matchId) throws Exception{
        this.match = matchDetailsService.findMatchDetailsById(matchId);
        matchValidation.matchCompletedValidation(match);
        this.team1 = teamService.getTeamById(match.getTeam1Id());
        this.team2 = teamService.getTeamById(match.getTeam2Id());
        this.secondInning = false;
        return CricketUtility.cricketToss(this);
    }
}
