package com.Dream11.services;
import com.Dream11.entity.User;
import com.Dream11.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public User addUser(User user){
        Optional<User> existingUser = userRepo.findById(user.getId());
        if(existingUser.isPresent()){
            throw new IllegalArgumentException("User with given ID already exists, try with another ID.");
        }
        return userRepo.save(user);
    }
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    public void createUserTeam(int id, List<Integer> playerIds) throws Exception {
        Optional<User> optionalUser = userRepo.findById(id);
        if(optionalUser.isEmpty()){
            throw new Exception("User with this id does not exist.");
        }
        User updateUser = optionalUser.get();
        updateUser.setChosenPlayerIdList(playerIds);
        userRepo.save(updateUser);
    }
}
