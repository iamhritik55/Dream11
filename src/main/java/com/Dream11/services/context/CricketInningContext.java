package com.Dream11.services.context;

import com.Dream11.services.models.Player;
import com.Dream11.services.gamecontroller.CricketUtility;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
