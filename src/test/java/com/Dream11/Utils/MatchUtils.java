package com.Dream11.Utils;

import com.Dream11.services.enums.MatchStatus;
import com.Dream11.services.models.Match;

public class MatchUtils {
    public static Match createMatch(String matchId, String team1Id, String team2Id){
        Match match = new Match();
        match.setMatchId(matchId);
        match.setTeam2Id(team2Id);
        match.setTeam1Id(team1Id);
        match.setStatus(MatchStatus.UNPLAYED);
        return match;
    }
}
