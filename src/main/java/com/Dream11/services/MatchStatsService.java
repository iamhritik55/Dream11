package com.Dream11.services;

import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.MatchStats;
import com.Dream11.services.repo.MatchStatsRepo;
import com.Dream11.utility.MatchStatsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MatchStatsService {

    @Autowired
    MatchStatsRepo matchStatsRepo;

    private String getWinnerTeamName(CricketMatchContext matchContext) {
        int team1Runs = matchContext.getTeam1().getTeamRuns();
        int team2Runs = matchContext.getTeam2().getTeamRuns();

        return team1Runs > team2Runs ? matchContext.getTeam1().getName()
                                     : team1Runs < team2Runs ? matchContext.getTeam2().getName() : "Tied";
    }

    public MatchStats storeAllMatchData(CricketMatchContext matchContext, CricketInningContext inningContext) {
        MatchStats matchStats = new MatchStats();

        matchStats.setId(matchContext.getMatch().getMatchId());

        matchStats.setTeam1Name(matchContext.getTeam1().getName());
        matchStats.setTeam2Name(matchContext.getTeam2().getName());

        matchStats.setTeam1Score(matchContext.getTeam1().getTeamRuns());
        matchStats.setTeam2Score(matchContext.getTeam2().getTeamRuns());


        matchStats.setTeam1PlayerStats(
                MatchStatsUtility.segregateCombinedPlayerStatsList(inningContext.getPlayerStatsList(),
                        matchContext.getTeam1().getTeamPlayerIds()));
        matchStats.setTeam2PlayerStats(
                MatchStatsUtility.segregateCombinedPlayerStatsList(inningContext.getPlayerStatsList(),
                        matchContext.getTeam2().getTeamPlayerIds()));

        matchStats.setWinnerTeamName(getWinnerTeamName(matchContext));
        matchStatsRepo.save(matchStats);
        return matchStats;
    }


    public MatchStats findMatchStatsById(String id) throws Exception {
        return matchStatsRepo.findById(id).orElseThrow(() -> new Exception("MatchStats id not found!"));
    }

}
