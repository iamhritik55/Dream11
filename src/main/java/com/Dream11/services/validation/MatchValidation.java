package com.Dream11.services.validation;

import com.Dream11.DTO.MatchRequestDTO;
import com.Dream11.entity.Match;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Dream11.utility.ApplicationUtils.TWO_TEAMS;

@Service
public class MatchValidation {

    @Autowired
    public TeamRepo teamRepo;
    @Autowired
    public MatchRepo matchRepo;

    public void validateMatch(MatchRequestDTO match) throws Exception {
        Optional<String> team1Id = Optional.ofNullable(match.getTeam1Id());
        Optional<String> team2Id = Optional.ofNullable(match.getTeam2Id());
        if (team1Id.isEmpty() || team2Id.isEmpty()) {
            throw new Exception("provided TeamId is null");
        }
        List<String> teamIds = new ArrayList<>();
        teamIds.add(match.getTeam1Id());
        teamIds.add(match.getTeam2Id());
         if (match.getTeam1Id().equals(match.getTeam2Id())) {
            throw new Exception("Both teamIDs can't be same");
        }else if (teamRepo.findAllById(teamIds).size() != TWO_TEAMS) {
            throw new Exception("Invalid TeamIDs provided");
        }
    }

}
