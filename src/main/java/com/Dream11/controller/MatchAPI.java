package com.Dream11.controller;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchAPI {
    @Autowired
    public MatchService matchService;
    @GetMapping("/{matchId}/{userId}")
    public ResponseEntity<Object> displayMatchUserStats(@PathVariable int matchId, @PathVariable int userId){
        try{
            MatchUserStats matchUserStats = matchService.getUserStats(matchId, userId);
            return new ResponseEntity<>(matchUserStats, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/")
    public List<MatchUserStats> getMatchStats(){
        return matchService.getAllStats();
    }
}
