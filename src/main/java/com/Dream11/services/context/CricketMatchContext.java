package com.Dream11.services.context;


import com.Dream11.services.models.Match;
import com.Dream11.services.models.Team;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CricketMatchContext {

    private Match match;
    private Team team1;
    private Team team2;
    private String tossWinner;
    private boolean secondInning;

}
