package com.Dream11.services.validation;

import com.Dream11.DTO.request.MatchRequestDTO;
import com.Dream11.services.models.Team;
import com.Dream11.services.repo.MatchRepo;
import com.Dream11.services.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.Dream11.utility.ApplicationUtils.TWO_TEAMS;

@Service
public class MatchValidation {

    @Autowired
    private TeamRepo teamRepo;
    @Autowired
    private MatchRepo matchRepo;

    public void validateMatch(MatchRequestDTO match) throws Exception {
        List<String> teamIds=List.of(match.getTeam1Id(), match.getTeam2Id());
        List<Team> teams=teamRepo.findAllById(teamIds);
        if(teams.size()!=TWO_TEAMS) throw new Exception("invalid team Id");
        if (match.getTeam1Id().equals(match.getTeam2Id())) {
            throw new Exception("Both teamIDs can't be same");
        }
    }

}
