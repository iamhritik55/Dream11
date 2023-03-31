package com.Dream11.services.validation;

import com.Dream11.DTO.request.MatchRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class MatchValidation {

    public void validateMatch(MatchRequestDTO match) throws Exception {
        if (match.getTeam1Id().equals(match.getTeam2Id())) {
            throw new Exception("Both teamIDs can't be same");
        }
    }

}
