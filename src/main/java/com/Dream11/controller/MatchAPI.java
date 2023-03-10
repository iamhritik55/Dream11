package com.Dream11.controller;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.Dream11.DTO.MatchDTO;
import com.Dream11.entity.*;

import java.util.ArrayList;

import static com.Dream11.transformer.MatchTransformer.DTOToMatch;
import static com.Dream11.transformer.MatchTransformer.matchToDTO;

import com.Dream11.services.MatchService;

@RestController
@RequestMapping("/match")
public class MatchAPI {
    @Autowired
    public MatchService matchService;
//    @Autowired
//    public TeamService teamService;
//    @Autowired
//    public PlayerService playerService;
    @Autowired
    public MatchStatsService matchStatsService;
    @Autowired
    public MatchUserService matchUserService;

    @PostMapping
    // TODO: 06/03/23  take match DTO which will update the necessary fields-done
    // TODO: 06/03/23  Give in response whatever fields are required.-done
    public MatchDTO addMatch(@RequestBody MatchDTO matchDTO) {
        return matchToDTO(matchService.addMatch(DTOToMatch(matchDTO)));
    }
    @PostMapping("/stats")
    public MatchUserStats addMatchUserStats(@RequestBody MatchUserStats matchUserStats) {
        return matchUserService.addMatchUserStats(matchUserStats);
    }

    // TODO: 06/03/23 Add a new API to get the live matches.-done
    @GetMapping
    public List<MatchDTO> getMatches() {
        List<MatchDTO> matchDTOs = new ArrayList<>();
        List<Match> matches = matchService.getMatches();
        for (Match match : matches) {
            matchDTOs.add(matchToDTO(match));
        }
        return matchDTOs;
    }

    @GetMapping("/unplayed")
    public List<MatchDTO> getUnplayedMatches() {
        List<MatchDTO> matchDTOs = new ArrayList<>();
        List<Match> matches = matchService.getUnplayedMatches();
        for (Match match : matches) {
            matchDTOs.add(matchToDTO(match));
        }
        return matchDTOs;
    }

    @GetMapping("/played")
    public List<MatchDTO> getPlayedMatches() {
        List<MatchDTO> matchDTOs = new ArrayList<>();
        List<Match> matches = matchService.getPlayedMatches();
        for (Match match : matches) {
            matchDTOs.add(matchToDTO(match));
        }
        return matchDTOs;
    }

    @GetMapping("/{matchId}")
    // TODO: 06/03/23 rename this var DispTeamDetResp-done
    // TODO: 06/03/23 Take string as input-done
    public ResponseEntity<Object> getTeamDetails(@PathVariable String matchId) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(matchService.getTeamDetails(matchId));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stats/{match_userId}")
    public ResponseEntity<Object> displayMatchUserStats(@PathVariable String match_userId) {
        try {
            MatchUserStats matchUserStats = matchUserService.getUserStats(match_userId);
            return new ResponseEntity<>(matchUserStats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/matchStats/{matchId}")
    public ResponseEntity<Object> getMatchStats(@PathVariable String matchId) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(matchStatsService.findMatchStatsById(matchId));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/start/{matchId}")
    public ResponseEntity<Object> startMatch(@PathVariable(value = "matchId") String matchId) {
        try {
            List<MatchUserStats> matchUserStatsList = matchService.startMatch(matchId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(matchUserStatsList);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/stats")
    public List<MatchUserStats> getMatchUserStats() {
        return matchUserService.getAllStats();
    }

}
