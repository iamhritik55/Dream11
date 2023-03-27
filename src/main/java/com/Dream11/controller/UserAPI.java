package com.Dream11.controller;

import com.Dream11.DTO.response.UserResponseDTO;
import com.Dream11.DTO.request.UserRequestDTO;
import com.Dream11.services.MatchUserService;
import com.Dream11.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAPI {
    @Autowired
    public UserService userService;
    @Autowired
    public MatchUserService matchUserService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody @Validated UserRequestDTO requestDto) {
        UserResponseDTO responseDto = userService.addUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);

    }

    @PostMapping("/match/{matchId}/user/{userId}")
    public ResponseEntity<Object> createUserTeam(@PathVariable String matchId, @PathVariable String userId, @RequestBody @Validated List<String> playerIds) throws Exception {

        matchUserService.createUserTeam(matchId, userId, playerIds);
        return new ResponseEntity<>("Team Created Successfully.", HttpStatus.OK);

    }
}
