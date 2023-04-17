package com.Dream11.services.validation;

import com.Dream11.services.enums.MatchStatus;
import com.Dream11.services.models.Match;
import org.springframework.stereotype.Component;

@Component
public class CricketMatchValidation {

    public void matchCompletedValidation(Match match) throws Exception {
        if (match.getStatus() == MatchStatus.PLAYED) {
            throw new Exception("Match has already been played");
        }
    }
}
