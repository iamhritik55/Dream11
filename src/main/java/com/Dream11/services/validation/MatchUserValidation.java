package com.Dream11.services.validation;

import com.Dream11.services.models.Match;
import com.Dream11.services.models.User;
import com.Dream11.services.enums.MatchStatus;
import com.Dream11.services.repo.MatchRepo;
import com.Dream11.services.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchUserValidation {
    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private UserRepo userRepo;

    public void validateMatchUserIds(String matchId, String userId) {
        matchRepo.findById(matchId).ifPresentOrElse(match -> {
            userRepo.findById(userId).ifPresentOrElse(user -> {
                if(match.getStatus()==MatchStatus.PLAYED){
                    throw new RuntimeException("This match is already played please choose another match");
                }
            }, ()->{throw new RuntimeException("User Id not found");});
        }, ()-> {throw new RuntimeException("Match Id not found");});

    }
}
