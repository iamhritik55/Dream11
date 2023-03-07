package com.Dream11.utility;

import com.Dream11.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class MatchUtils {
    public void declareWinner(Team team1, Team team2, int team1Score, int team2Score){
        if(team1Score>team2Score){
            System.out.println(team1.getName()+" has won the match");
        }
        else if(team2Score>team1Score){
            System.out.println(team2.getName()+" has won the match");
        }
        else {
            System.out.println("The match is tied!");
        }
    }
}
