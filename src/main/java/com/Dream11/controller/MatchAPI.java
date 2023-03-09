package com.Dream11.controller;

import com.Dream11.DTO.MatchDTO;
import com.Dream11.entity.*;
import com.Dream11.services.*;
import com.Dream11.utility.CombinedMatchPlayerId;
import com.Dream11.utility.TeamDetails;
import com.Dream11.utility.MatchPlayerStatsAttributes;
import com.Dream11.utility.MatchPlayerStatsResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.Dream11.transformer.MatchTransformer.DTOToMatch;
import static com.Dream11.transformer.MatchTransformer.matchToDTO;

@RestController
@RequestMapping("/match")
public class MatchAPI {

    @Autowired
    public MatchService matchService;
    @Autowired
    public TeamService teamService; //doubt- can I use teamService in matchAPI
    @Autowired
    public PlayerService playerService;//same doubt as above
    @Autowired
    public MatchPlayerService matchPlayerService;
    @Autowired
    public MatchStatsService matchStatsService;

    @PostMapping
    // TODO: 06/03/23  take match DTO which will update the necessary fields-done
    // TODO: 06/03/23  Give in response whatever fields are required.-done
    public MatchDTO addMatch(@RequestBody MatchDTO matchDTO) {
        return matchToDTO(matchService.addMatch(DTOToMatch(matchDTO)));
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

    @GetMapping("/matchStats/{matchId}")
    public ResponseEntity<Object> getMatchStats(@PathVariable String matchId) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(matchStatsService.findMatchStatsById(matchId));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
