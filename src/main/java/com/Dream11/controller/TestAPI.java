package com.Dream11.controller;

import com.Dream11.entity.MatchStats;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.gamecontroller.CricketControllerService;
import com.Dream11.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestAPI {
    @Autowired
    CricketControllerService cricketControllerService;
    @Autowired
    UserService userService;
    @GetMapping("/test/{id}")
    public List<MatchUserStats> test(@PathVariable String id) throws Exception{
        return cricketControllerService.startMatch(id);
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody List<String> idList) throws Exception{
        return ResponseEntity.accepted().body(userService.findUserListByIdList(idList));
    }

}
