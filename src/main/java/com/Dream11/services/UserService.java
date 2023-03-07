package com.Dream11.services;

import com.Dream11.entity.User;
import com.Dream11.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void updateUserCredits(String userId, int credits){
        if(userRepo.findById(userId).isPresent()){
            User user = userRepo.findById(userId).get();
            int creditsToUpdate = credits + user.getCredits();
            user.setCredits(creditsToUpdate);
            userRepo.save(user);
        }
        else{
            System.out.println("Invalid userId");
        }
    }


}
