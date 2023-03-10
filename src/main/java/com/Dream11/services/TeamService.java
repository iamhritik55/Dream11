package com.Dream11.services;

import com.Dream11.entity.Team;
import com.Dream11.repo.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.Dream11.Counter.counter;

@Service
public class TeamService {
    @Autowired
    TeamRepo teamRepo;
    public Team getTeamBYId(String teamId) throws Exception{
        Optional<Team> teamOptional = teamRepo.findById(teamId);
        if (teamOptional.isPresent()) {
            counter++;
            return teamOptional.get();
        }
        else {
            throw new Exception("Invalid teamId!");
        }

    }
}
