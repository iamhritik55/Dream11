package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeamValidation {
    @Autowired
    public PlayerRepo playerRepo;
    public boolean teamValid(Team team) {
        List<String> playerIds = team.getTeamPlayerIds();
        for (String playerId : playerIds) {
            if(!(playerRepo.findById(playerId).isPresent())) return false;
        }
        return true;
    }
}
