package com.Dream11.services.context;


import com.Dream11.services.gamecontroller.CricketUtility;
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

    // TODO: 29/03/23 take only fields in context (no autowired, no methods) - done
    // TODO: 29/03/23 create validateAndCreateContext - done

}
