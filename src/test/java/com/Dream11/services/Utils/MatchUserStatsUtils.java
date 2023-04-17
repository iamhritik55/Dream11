package com.Dream11.services.Utils;

import com.Dream11.services.models.MatchUserStats;

import java.util.List;

public class MatchUserStatsUtils {
    public static MatchUserStats createMatchUserStats(String id, String matchId, String userId, List<String> chosenPlayerIdList, int creditChange, int teamPoints, int creditsSpentByUser){
        MatchUserStats matchUserStats = new MatchUserStats();
        matchUserStats.setId(id);
        matchUserStats.setMatchId(matchId);
        matchUserStats.setUserId(userId);
        matchUserStats.setChosenPlayerIdList(chosenPlayerIdList);
        matchUserStats.setCreditChange(creditChange);
        matchUserStats.setTeamPoints(teamPoints);
        matchUserStats.setCreditsSpentByUser(creditsSpentByUser);
        return matchUserStats;
    }
}
