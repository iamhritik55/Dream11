package com.Dream11.Utils;

import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.Match;
import com.Dream11.services.models.Team;

public class MatchContextUtils {
    public static CricketMatchContext createMatchContext(Match match, Team team1, Team team2, String tossWinner){
        CricketMatchContext matchContext = new CricketMatchContext();
        matchContext.setMatch(match);
        matchContext.setTeam2(team2);
        matchContext.setTeam1(team1);
        matchContext.setSecondInning(false);
        matchContext.setTossWinner(tossWinner);
        return matchContext;
    }
}
