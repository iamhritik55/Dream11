package com.Dream11.controller;

import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.services.MatchPlayerService;
import com.Dream11.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchAPI {
    @Autowired
    public MatchService matchService;
    @Autowired
    MatchPlayerService matchPlayerService;
    @PostMapping("/start/{matchId}")
    public void startMatch(@PathVariable (value = "matchId") String matchId){
        matchService.startMatch(matchId);
    }

    @DeleteMapping
    public void deleteAllMatchPlayerStats(){
        matchPlayerService.deleteAll();
    }


}
