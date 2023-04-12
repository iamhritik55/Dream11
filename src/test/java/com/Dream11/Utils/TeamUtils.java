package com.Dream11.Utils;

import com.Dream11.services.models.Team;

import java.util.List;

public class TeamUtils {
    public static Team createTeam1(String teamId, String teamName){
        Team team = new Team();
        team.setId(teamId);
        team.setName(teamName);
        List<String> playerIds = List.of("1","2","3","4","5");
        team.setTeamPlayerIds(playerIds);
        return team;
    }

    public static Team createTeam2(String teamId, String teamName){
        Team team = new Team();
        team.setId(teamId);
        team.setName(teamName);
        List<String> playerIds = List.of("6","7","8","9","10");
        team.setTeamPlayerIds(playerIds);
        return team;
    }
}
