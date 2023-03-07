package com.Dream11.controller;
import com.Dream11.entity.User;
import com.Dream11.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserAPI {
    @Autowired
    public UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> addUser(@RequestBody User user){
        try {
            User newUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
           }
    }
    @GetMapping("/")
    public ResponseEntity<Object> getUsers(){
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> createUserTeam(@PathVariable int id,@RequestBody List<Integer> playerIds){
        try{
            userService.createUserTeam(id, playerIds);
            return new ResponseEntity<>("Team Created Successfully.",HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
