package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.repo.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TeamValidation {
    @Autowired
    public PlayerRepo playerRepo;
    public boolean teamValid(Team team) {
        List<Player> players = playerRepo.findAllById(team.getTeamPlayerIds());
        Set<String> playerIdSet = new HashSet<>(team.getTeamPlayerIds());
        if (players.size() == team.getTeamPlayerIds().size() && playerIdSet.size() == team.getTeamPlayerIds().size()) {
            return true;
        } else {
            return false;
        }
    }
}
