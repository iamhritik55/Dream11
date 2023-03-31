package com.Dream11.services;

import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.MatchStats;
import com.Dream11.services.repo.MatchStatsRepo;
import com.Dream11.services.transformer.MatchStatsTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


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

        if (Objects.equals(matchContext.getTeam1().getId(), inningContext.getBattingTeamId())) {
            matchStats.setTeam1PlayerStats(
                    MatchStatsTransformer.createPlayerStatsList(inningContext.getBattingPlayerList()));
            matchStats.setTeam2PlayerStats(
                    MatchStatsTransformer.createPlayerStatsList(inningContext.getBowlingPlayerList()));
        } else {
            matchStats.setTeam1PlayerStats(
                    MatchStatsTransformer.createPlayerStatsList(inningContext.getBowlingPlayerList()));
            matchStats.setTeam2PlayerStats(
                    MatchStatsTransformer.createPlayerStatsList(inningContext.getBattingPlayerList()));
        }
        matchStats.setWinnerTeamName(getWinnerTeamName(matchContext));
        matchStatsRepo.save(matchStats);
        return matchStats;
    }


    public MatchStats findMatchStatsById(String id) throws Exception {
        Optional<MatchStats> matchStats = matchStatsRepo.findById(id);
        if (matchStats.isPresent()) {
            return matchStats.get();
        } else {
            throw new Exception("MatchStats id not found!");
        }
    }

}
