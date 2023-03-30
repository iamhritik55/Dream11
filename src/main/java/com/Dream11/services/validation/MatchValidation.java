package com.Dream11.services.validation;

import com.Dream11.DTO.request.MatchRequestDTO;
import com.Dream11.services.repo.MatchRepo;
import com.Dream11.services.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchValidation {

    @Autowired
    public TeamRepo teamRepo;
    @Autowired
    public MatchRepo matchRepo;

    public void validateMatch(MatchRequestDTO match) throws Exception {
        if (match.getTeam1Id().equals(match.getTeam2Id())) {
            throw new Exception("Both teamIDs can't be same");
        }
    }

}
