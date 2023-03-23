package com.Dream11.utility;

import com.Dream11.entity.Team;
import com.Dream11.services.MatchStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchUtils {
    @Autowired
    MatchStatsService matchStatsService;
    public String declareWinner(Team team1, Team team2, long team1Score, long team2Score){
        if(team1Score>team2Score){
            System.out.println(team1.getName()+" has won the match");
            return team1.getName();
        }
        else if(team2Score>team1Score){
            System.out.println(team2.getName()+" has won the match");
            return team2.getName();
        }
        else {
            System.out.println("The match is tied!");
            return "Tied!";
        }
    }
}
