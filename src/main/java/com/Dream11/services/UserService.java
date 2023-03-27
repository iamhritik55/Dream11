package com.Dream11.services;

import com.Dream11.DTO.response.UserResponseDTO;
import com.Dream11.DTO.request.UserRequestDTO;
import com.Dream11.transformer.UserTransformer;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.User;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public UserResponseDTO addUser(UserRequestDTO requestDto) {
        User user = UserTransformer.requestDtoToUser(requestDto);
        userRepo.save(user);
        return UserTransformer.userToResponseDto(user);
    }

    //get all users from collection
    public List<User> getUsers() {
        return userRepo.findAll();
    }//doubt should i use UserResponseDTO
    public User getUserById(String id) throws Exception{
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isEmpty()){
            throw new Exception("Not Present");
        }
        return optionalUser.get();
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
        int userNumber = 0;
        for(String userId: userIdList){
            for(User user: userList){
                if(Objects.equals(user.getId(), userId)){
                    if(matchUserStatsList.get(userNumber).getCreditChange()<0){
                        userNumber++;
                        break;
                    }
                    int credits = user.getCredits();
                    credits+=matchUserStatsList.get(userNumber).getCreditChange()+matchUserStatsList.get(userNumber).getCreditsSpentByUser();
                    user.setCredits(credits);
                    userNumber++;
                    break;
                }
            }
        }

        userRepo.saveAll(userList);
    }

    public String findUserNameById(String userId) throws Exception{
        Optional<User> optional = userRepo.findById(userId);
        if (optional.isPresent()){
            return optional.get().getName();
        }
        else {
            throw new Exception("userId not found!");
        }
    }

}
