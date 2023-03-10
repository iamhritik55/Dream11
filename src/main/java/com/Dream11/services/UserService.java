package com.Dream11.services;

import com.Dream11.entity.User;
import com.Dream11.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.Dream11.Counter.counter;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void addUserCredits(String userId, int credits) throws Exception{
        Optional<User> userOptional= userRepo.findById(userId);
        System.out.println("yes");
        if(userOptional.isPresent()){
            User user = userOptional.get();
            int creditsToUpdate = credits + user.getCredits();
            user.setCredits(creditsToUpdate);
            userRepo.save(user);
            counter+=2;
        }
        else{

            throw new Exception("Invalid userId");
        }
    }
}
