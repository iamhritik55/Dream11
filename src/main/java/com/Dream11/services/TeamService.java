package com.Dream11.services;

import com.Dream11.entity.Team;
import com.Dream11.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    TeamRepo teamRepo;
    public Team getTeamBYId(int teamId) {
        if (teamRepo.findById(teamId).isPresent()) {
            return teamRepo.findById(teamId).get();
        }
        else {
            System.out.println("TeamID invalid!");
            return null;
        }

    }
}
