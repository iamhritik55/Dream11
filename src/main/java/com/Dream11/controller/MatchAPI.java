package com.Dream11.controller;

import com.Dream11.DTO.request.MatchRequestDTO;
import com.Dream11.DTO.response.MatchResponseDTO;
import com.Dream11.entity.MatchStats;
import com.Dream11.DTO.response.MatchUserStatsResponseDTO;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.services.MatchService;
import com.Dream11.services.MatchStatsService;
import com.Dream11.services.MatchUserService;
import lombok.extern.slf4j.Slf4j;
import com.Dream11.gamecontroller.CricketControllerService;
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
    CricketControllerService cricketControllerService;
    @Autowired
    public MatchStatsService matchStatsService;
    @Autowired
    public MatchUserService matchUserService;

    @PostMapping
    public ResponseEntity<MatchResponseDTO> addMatch(@RequestBody @Validated MatchRequestDTO matchRequestDTO) throws Exception {
        return ResponseEntity.ok(matchService.addMatch(matchRequestDTO));
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

    @GetMapping("/team-details/{id}")
    public ResponseEntity<Object> getTeamDetails(@PathVariable String id) throws Exception{//3
        return ResponseEntity.ok(matchService.getTeamDetails(id));
    }

    @GetMapping("/user-stats/{id}")
    public ResponseEntity<Object> displayMatchUserStats(@PathVariable String id) throws Exception {// TODO: 16/03/23 take a genric name
        // TODO: 28/03/23 take matchId and userId
        MatchUserStatsResponseDTO matchUserStats = matchUserService.getUserStats(id);
        return new ResponseEntity<>(matchUserStats, HttpStatus.OK);

    }

    @GetMapping("/stats/{match-id}")
    public ResponseEntity<MatchStats> getMatchStats(@PathVariable String matchId) throws Exception {
        return ResponseEntity.ok(matchStatsService.findMatchStatsById(matchId));
    }

    @PostMapping("/start/{matchId}")//5
    public ResponseEntity<Object> startMatch(@PathVariable(value = "matchId") String matchId) throws Exception {
        return  ResponseEntity.accepted().body(cricketControllerService.startMatch(matchId));
    }

    @GetMapping("/user-stats")
    public List<MatchUserStats> getAllMatchUserStats() {
        return matchUserService.getAllStats();
    }

}
