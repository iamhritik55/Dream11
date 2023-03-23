package com.Dream11.services.validation;

import com.Dream11.DTO.TeamRequestDTO;
import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.repo.PlayerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.Dream11.utility.ApplicationUtils.TEAM_SIZE;

@Service
public class TeamValidation {
    @Autowired
    public PlayerRepo playerRepo;
    public void teamValid(TeamRequestDTO team) throws Exception {
        if(team.getTeamPlayerIds().size()!=TEAM_SIZE) throw new Exception("Team Size not valid");
        List<Player> players = playerRepo.findAllById(team.getTeamPlayerIds());//to check if all PlayerIds exits
        Set<String> playerIdSet = new HashSet<>(team.getTeamPlayerIds());//to check all playerIds are unique
        if (players.size() != team.getTeamPlayerIds().size() || playerIdSet.size() != team.getTeamPlayerIds().size()) {
            throw new Exception("Invalid PlayerID found in team");
        }
    }
}
