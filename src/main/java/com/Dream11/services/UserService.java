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
    public User addUser(User user){
        //if there is a existing user with same id, throw error
        Optional<User> existingUser = userRepo.findById(user.getId());
        if(existingUser.isPresent()) {
            throw new IllegalArgumentException("User with given ID already exists, try with another ID.");
        }
        return userRepo.save(user);
    }
    //get all users from collection
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    //user creates its own team
    public void createUserTeam(int id, List<Integer> playerIds) throws Exception {
        Optional<User> optionalUser = userRepo.findById(id);
        //if user does not present in database throw error
        if(optionalUser.isEmpty()){
            throw new Exception("User with this id does not exist.");
        }

        utilityService.validatePlayerIds(playerIds);

        utilityService.validateTeamSize(playerIds);

        User updateUser = optionalUser.get();
        //checks if user have enough credits to buy the team or not
        if(utilityService.calculateTeamCost(playerIds) > updateUser.getCredits()){
            throw new Exception("You don't have enough credits to select this team.");
        }
        utilityService.restrictPlayerIds(playerIds);
        updateUser.setChosenPlayerIdList(playerIds);
        updateUser.setCredits(updateUser.getCredits() - utilityService.calculateTeamCost(playerIds));
        userRepo.save(updateUser);
    }


    public void updateUserCredits(int userId, int credits){
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
