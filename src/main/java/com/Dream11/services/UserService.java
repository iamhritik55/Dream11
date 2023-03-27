package com.Dream11.services;

import com.Dream11.DTO.UserResponseDTO;
import com.Dream11.DTO.UserRequestDTO;
import com.Dream11.transformer.UserTransformer;
import com.Dream11.entity.User;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.UserRepo;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.Dream11.Counter.counter;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private UtilityService utilityService;

    //creating a user
    public UserResponseDTO addUser(@NonNull UserRequestDTO requestDto) {
        User user = UserTransformer.requestDtoToUser(requestDto);
        userRepo.save(user);
        return UserTransformer.userToResponseDto(user);
    }

    //get all users from collection
    public List<User> getUsers() {
        return userRepo.findAll();
    }
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
        if (userRepo.findById(userId).isPresent()) {
            User user = userRepo.findById(userId).get();
            int creditsToUpdate = credits + user.getCredits();
            user.setCredits(creditsToUpdate);
            userRepo.save(user);
        } else {
            System.out.println("Invalid userId");
        }
    }

    public void addUserCredits(String userId, int credits) throws Exception {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            int creditsToUpdate = credits + user.getCredits();
            user.setCredits(creditsToUpdate);
            userRepo.save(user);
            counter += 2;
        } else {

            throw new Exception("Invalid userId");
        }
    }

}
