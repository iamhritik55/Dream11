package com.Dream11.services.utils;

import com.Dream11.services.context.TeamDetailsContext;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.Team;

import java.util.List;

public class TeamDetailsUtils {
    public static TeamDetailsContext createTeamDetailsContextForTest(Team team1, Team team2, List<Player> players){
        TeamDetailsContext teamDetailsContext=new TeamDetailsContext();
        teamDetailsContext.setTeam1(team1);
        teamDetailsContext.setTeam2(team2);
        teamDetailsContext.setPlayers(players);
        return teamDetailsContext;
    }
}
