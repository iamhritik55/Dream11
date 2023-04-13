package com.Dream11.services.utils;

import com.Dream11.services.models.Team;

import java.util.List;

public class TeamUtils {
    public static Team createTeam(String id, String name, List<String> teamPlayerIds){
        Team team=new Team();
        team.setId(id);
        team.setName(name);
        team.setTeamPlayerIds(teamPlayerIds);
        return team;
    }
}
