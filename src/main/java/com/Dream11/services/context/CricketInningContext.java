package com.Dream11.services.context;

import com.Dream11.services.PlayerService;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.PlayerStats;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class CricketInningContext {
    //This contains list of all the players of the inning
    private List<Player> battingPlayerList;
    private List<Player> bowlingPlayerList;
    private String battingTeamId;
    private String bowlingTeamId;
    private List<PlayerStats> playerStatsList;

}
