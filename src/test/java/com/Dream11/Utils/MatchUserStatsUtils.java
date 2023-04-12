package com.Dream11.Utils;

import com.Dream11.services.models.MatchUserStats;

import java.util.List;

public class MatchUserStatsUtils {
    public static MatchUserStats createMatchUserStats(List<String> chosenPlayerIdList, int teamPoints){
        MatchUserStats matchUserStats = new MatchUserStats();
        matchUserStats.setTeamPoints(teamPoints);
        matchUserStats.setChosenPlayerIdList(chosenPlayerIdList);
        return matchUserStats;
    }
}
