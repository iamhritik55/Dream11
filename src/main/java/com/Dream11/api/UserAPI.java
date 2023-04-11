package com.Dream11.api;

import com.Dream11.DTO.response.MatchUserStatsResponseDTO;
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
@RequestMapping("/users")
public class UserAPI {
    @Autowired
    public UserService userService;
    @Autowired
    public MatchUserService matchUserService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody @Validated UserRequestDTO requestDto) {
        UserResponseDTO responseDto = userService.addUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/create-team/{matchId}/{userId}")
    public ResponseEntity<MatchUserStatsResponseDTO> createUserTeam(@PathVariable String matchId, @PathVariable String userId, @RequestBody @Validated List<String> playerIds) throws Exception {

        MatchUserStatsResponseDTO responseDto = matchUserService.createUserTeam(matchId, userId, playerIds);
        return ResponseEntity.ok(responseDto);
    }
}
