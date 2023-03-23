package com.Dream11.context;

import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.gamecontroller.CricketUtility;
import com.Dream11.services.PlayerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Data
public class CricketInningContext {
    @Autowired
    CricketUtility cricketUtility;
    //This contains list of all the players of the inning
    private List<Player> battingPlayerList;
    private List<Player> bowlingPlayerList;
    private String battingTeamId;
    private String bowlingTeamId;

}
