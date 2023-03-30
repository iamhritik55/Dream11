package com.Dream11.services.context;


import com.Dream11.services.models.Match;
import com.Dream11.services.models.Team;
import com.Dream11.services.gamecontroller.CricketUtility;
import com.Dream11.services.MatchService;
import com.Dream11.services.TeamService;
import com.Dream11.services.validation.CricketMatchValidation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class CricketMatchContext {
    @Autowired
    MatchService matchService;
    @Autowired
    CricketMatchValidation matchValidation;
    @Autowired
    TeamService teamService;
    private Match match;
    private Team team1;
    private Team team2;
    private String tossWinner;
    private boolean secondInning;

    // TODO: 29/03/23 take only fields in context (no autowired, no methods)
    // TODO: 29/03/23 create validateAndCreateContext
    public CricketMatchContext fetchCricketContext(String matchId) throws Exception{
        this.match = matchService.getMatchById(matchId);
        matchValidation.matchCompletedValidation(match);
        this.team1 = teamService.getTeamById(match.getTeam1Id());
        this.team2 = teamService.getTeamById(match.getTeam2Id());
        this.secondInning = false;
        return CricketUtility.cricketToss(this);
    }
}
