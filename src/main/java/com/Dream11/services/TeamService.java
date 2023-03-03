package com.Dream11.services;

import com.Dream11.entity.Team;
import com.Dream11.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;
    public Team getTeamBYId(int teamId) {
        if (teamRepository.findById(teamId).isPresent()) {
            return teamRepository.findById(teamId).get();
        }
        else {
            System.out.println("TeamID invalid!");
            return null;
        }

    }
}
