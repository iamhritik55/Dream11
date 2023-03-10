package com.Dream11.controller;

import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.entity.MatchStats;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.services.MatchPlayerService;
import com.Dream11.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.Dream11.Counter.counter;

@RestController
@RequestMapping("/match")
public class MatchAPI {
    @Autowired
    public MatchService matchService;
    @Autowired
    MatchPlayerService matchPlayerService;
    @PostMapping("/start/{matchId}")
    public ResponseEntity<Object> startMatch(@PathVariable (value = "matchId") String matchId){
        try {
            List<MatchUserStats> matchUserStatsList = matchService.startMatch(matchId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(matchUserStatsList);

        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);        }

    }

    @DeleteMapping
    public void deleteAllMatchPlayerStats(){
        matchPlayerService.deleteAll();
    }


}
