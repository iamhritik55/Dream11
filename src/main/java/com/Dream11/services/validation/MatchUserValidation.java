package com.Dream11.services.validation;

import com.Dream11.entity.Match;
import com.Dream11.entity.User;
import com.Dream11.enums.MatchStatus;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchUserValidation {
    @Autowired
    MatchRepo matchRepo;
    @Autowired
    UserRepo userRepo;

    public void validateMatchUserIds(String matchId, String userId) throws Exception{
        Optional<Match> optionalMatch = matchRepo.findById(matchId);
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalMatch.isPresent() && optionalUser.isPresent()){
            Match matchObj = optionalMatch.get();
            if (matchObj.getStatus() == MatchStatus.PLAYED) {
                throw new Exception("This match is already played please choose another one.");
            }
        }
        else{
            throw new RuntimeException("Ids not found");
        }
    }
}
