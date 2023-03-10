package com.Dream11.controller;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchAPI {
    @Autowired
    public MatchUserService matchUserService;

    @PostMapping("/")
    public MatchUserStats addMatchUserStats(@RequestBody MatchUserStats matchUserStats) {
        return matchUserService.addMatchUserStats(matchUserStats);
    }

    @GetMapping("/{match_userId}")
    public ResponseEntity<Object> displayMatchUserStats(@PathVariable String match_userId) {
        try {
            MatchUserStats matchUserStats = matchUserService.getUserStats(match_userId);
            return new ResponseEntity<>(matchUserStats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public List<MatchUserStats> getMatchStats() {
        return matchUserService.getAllStats();
    }


}
