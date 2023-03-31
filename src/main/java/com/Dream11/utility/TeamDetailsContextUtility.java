package com.Dream11.utility;

import com.Dream11.services.PlayerService;
import com.Dream11.services.TeamService;
import com.Dream11.services.context.TeamDetailsContext;
import com.Dream11.services.models.Team;
import com.Dream11.services.repo.DAO.MatchDAO;
import com.Dream11.services.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TeamDetailsContextUtility {
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    public TeamDetailsContext createTeamDetailsContext(MatchDAO match) {
        TeamDetailsContext teamDetailsContext = new TeamDetailsContext();
        List<String> teamIds = new ArrayList<>();
        teamIds.add(match.getTeam1Id());
        teamIds.add(match.getTeam2Id());
        List<Team> teams= teamService.getTeamsById(teamIds);
        teamDetailsContext.setTeam1(teams.stream().filter(team -> Objects.equals(team.getId(), match.getTeam1Id())).findFirst().get());
        teamDetailsContext.setTeam2(teams.stream().filter(team -> Objects.equals(team.getId(), match.getTeam2Id())).findFirst().get());
        List<String> playerIds = new ArrayList<>();
        playerIds.addAll(teamDetailsContext.getTeam1().getTeamPlayerIds());
        playerIds.addAll(teamDetailsContext.getTeam2().getTeamPlayerIds());
        teamDetailsContext.setPlayers(playerService.getPlayerListFromIdList(playerIds));
        return teamDetailsContext;
    }
}
