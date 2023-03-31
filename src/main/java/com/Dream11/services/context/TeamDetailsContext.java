package com.Dream11.services.context;

import com.Dream11.services.models.Player;
import com.Dream11.services.models.Team;
import lombok.Data;

import java.util.List;
@Data
public class TeamDetailsContext {
    private Team team1;
    private Team team2;
    private List<Player> players;
}
