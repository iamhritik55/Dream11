package com.Dream11.controller;

import com.Dream11.entity.MatchStats;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.gamecontroller.CricketControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestAPI {
    @Autowired
    CricketControllerService cricketControllerService;
    @GetMapping("/test/{id}")
    public List<MatchUserStats> test(@PathVariable String id) throws Exception{
        return cricketControllerService.startMatch(id);
    }
}
