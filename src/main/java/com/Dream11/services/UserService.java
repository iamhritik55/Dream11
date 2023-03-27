package com.Dream11.services;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.User;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void subtractUserCredits(String userId, int credits) throws Exception {
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

    public void updateUserCredits(String userId, int credits) {
        Optional<User> optional = userRepo.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            int creditsToUpdate = credits + user.getCredits();
            user.setCredits(creditsToUpdate);
            userRepo.save(user);
        } else {
            System.out.println("Invalid userId");
        }
    }
    public List<User> findUserListByIdList(List<String> userIdList) throws Exception{
        List<User> userList = userRepo.findAllById(userIdList);
        if(userList.isEmpty()){
            throw new Exception("No users found!");
        }
        return userList;
    }

    public void addCreditsUsingMatchUserStats(List<MatchUserStats> matchUserStatsList) throws Exception{
        List<String> userIdList = new ArrayList<>();
        for (MatchUserStats matchUserStats: matchUserStatsList){
            userIdList.add(matchUserStats.getUserId());
        }
        List<User> userList = findUserListByIdList(userIdList);
        for(int userNumber = 0; userNumber<matchUserStatsList.size(); userNumber++){
            int credits = userList.get(userNumber).getCredits();
            credits+=matchUserStatsList.get(userNumber).getCreditChange();
            userList.get(userNumber).setCredits(credits);
        }

        userRepo.saveAll(userList);
    }

}
