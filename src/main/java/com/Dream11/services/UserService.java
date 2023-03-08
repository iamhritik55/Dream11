package com.Dream11.services;

import com.Dream11.entity.User;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private UtilityService utilityService;

    //creating a user
    public User addUser(User user) {
        //if there is a existing user with same id, throw error
        Optional<User> existingUser = userRepo.findById(user.getId());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with given ID already exists, try with another ID.");
        }
        return userRepo.save(user);
    }

    //get all users from collection
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public void subtractUserCredits(int userId, int credits) throws Exception {
        if (userRepo.findById(userId).isPresent()) {
            User user = userRepo.findById(userId).get();
            if (credits > user.getCredits()) {
                throw new Exception("You don't have enough credits to select this team.");
            }
            int creditsToUpdate = user.getCredits() - credits;
            user.setCredits(creditsToUpdate);
            userRepo.save(user);
        } else {
            throw new Exception("User with this id does not exist.");
        }
    }

    public void updateUserCredits(int userId, int credits) {
        if (userRepo.findById(userId).isPresent()) {
            User user = userRepo.findById(userId).get();
            int creditsToUpdate = credits + user.getCredits();
            user.setCredits(creditsToUpdate);
            userRepo.save(user);
        } else {
            System.out.println("Invalid userId");
        }
    }


}
