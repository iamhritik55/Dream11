package com.Dream11.services;

import com.Dream11.DTO.request.UserRequestDTO;
import com.Dream11.DTO.response.UserResponseDTO;
import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.models.User;
import com.Dream11.services.repo.UserRepo;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.services.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public User getUserById(String id){
        return userRepo.findById(id)
                       .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void subtractUserCredits(String userId, int credits) throws Exception {
        userRepo.findById(userId).ifPresent(user -> {
            if (credits > user.getCredits()) {
                throw new RuntimeException("You don't have enough credits to select this team.");
            }
            int creditsToUpdate = user.getCredits() - credits;
            user.setCredits(creditsToUpdate);
            userRepo.save(user);
        });
    }

    public void updateUserCredits(String userId, int credits) throws Exception {
        User user = userRepo.findById(userId).orElseThrow(() -> new Exception("User with this id does not exist."));

        int creditsToUpdate = credits + user.getCredits();
        user.setCredits(creditsToUpdate);
        userRepo.save(user);

    }
    public List<User> findUserListByIdList(List<String> userIdList) throws Exception{
        List<User> userList = userRepo.findAllById(userIdList);
        userList.stream().findFirst().orElseThrow(()-> new Exception("No users found!"));
        return userList;
    }

    public void addCreditsUsingMatchUserStats(List<MatchUserStats> matchUserStatsList) throws Exception{
        List<String> userIdList = new ArrayList<>();
        matchUserStatsList.forEach(matchUserStats -> userIdList.add(matchUserStats.getUserId()));

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
        return userRepo.findById(userId).orElseThrow(()-> new Exception("userId not found")).getName();
    }

}
