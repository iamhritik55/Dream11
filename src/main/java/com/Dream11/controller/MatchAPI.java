package com.Dream11.controller;

import com.Dream11.DTO.MatchRequestDTO;
import com.Dream11.DTO.MatchResponseDTO;
import com.Dream11.entity.MatchStats;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.services.MatchService;
import com.Dream11.services.MatchStatsService;
import com.Dream11.services.MatchUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/match")
@Slf4j
public class MatchAPI {

    @Autowired
    public MatchService matchService;
    @Autowired
    public MatchStatsService matchStatsService;
    @Autowired
    public MatchUserService matchUserService;

    // TODO: 21/03/23 add requestDTO-done
    @PostMapping
    public ResponseEntity<MatchResponseDTO> addMatch(@RequestBody @Validated MatchRequestDTO matchRequestDTO) throws Exception {
        return ResponseEntity.ok(matchService.addMatch(matchRequestDTO));
    }

    @PostMapping("/stats")
    public ResponseEntity<Object> addMatchUserStats(@RequestBody MatchUserStats matchUserStats) { //2
        try {// TODO: 16/03/23 add matchUserStats DTO
            // TODO: 16/03/23 add dto validation 
            matchUserService.addMatchUserStats(matchUserStats);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<MatchResponseDTO> getAllMatches() {
        return matchService.getMatches();
    }

    @GetMapping("/unplayed")
    public List<MatchResponseDTO> getUnplayedMatches() {
        return matchService.getUnplayedMatches();
    }

    @GetMapping("/played")
    public List<MatchResponseDTO> getPlayedMatches() {
        return matchService.getPlayedMatches();
    }

    @GetMapping("/getTeamDetails/{matchId}")//todo give meaningfull url-done
    // TODO: 06/03/23 rename this var DispTeamDetResp-done
    // TODO: 06/03/23 Take string as input-done
    public ResponseEntity<Object> getTeamDetails(@PathVariable String matchId) throws Exception{//3
        return ResponseEntity.ok(matchService.getTeamDetails(matchId));
    }

    @GetMapping("/stats/{match_userId}")
    public ResponseEntity<Object> displayMatchUserStats(@PathVariable String match_userId) {// TODO: 16/03/23 take a generic name
        try {
            MatchUserStats matchUserStats = matchUserService.getUserStats(match_userId);
            return new ResponseEntity<>(matchUserStats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.NOT_FOUND); // TODO: 16/03/23  add try catch in service
        }
    }

    @GetMapping("/matchStats/{matchId}")
    public ResponseEntity<MatchStats> getMatchStats(@PathVariable String matchId) throws Exception {
        return ResponseEntity.ok(matchStatsService.findMatchStatsById(matchId));
    }

    @PostMapping("/start/{matchId}")//5
    public ResponseEntity<Object> startMatch(@PathVariable(value = "matchId") String matchId) {
        try {
            List<MatchUserStats> matchUserStatsList = matchService.startMatch(matchId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(matchUserStatsList);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/stats")
    public List<MatchUserStats> getAllStats() {
        return matchUserService.getAllStats();
    }

}
