package com.Dream11.validation;

import com.Dream11.entity.Match;
import com.Dream11.entity.MatchStatus;
import org.springframework.stereotype.Component;

@Component
public class CricketMatchValidation {
    public void matchCompletedValidation(Match match) throws Exception{
     if(match.getStatus() == MatchStatus.PLAYED){
         throw new Exception("Match has already been played");
     }
    }
}
