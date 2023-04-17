package com.Dream11.services.validation;

import com.Dream11.DTO.request.TeamRequestDTO;
import com.Dream11.services.models.Player;
import com.Dream11.services.repo.PlayerRepo;
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

    public void validateTeam(TeamRequestDTO team) throws Exception {
        if(team.getTeamPlayerIds().size()!=TEAM_SIZE) throw new Exception("Team Size not valid");
        Set<String> playerIdSet = new HashSet<>(team.getTeamPlayerIds());
        if (playerIdSet.size() < team.getTeamPlayerIds().size()) {
            throw new Exception("Duplicate player Ids found in team.");
        }
        List<Player> listOfPlayerIds = playerRepo.findAllById(team.getTeamPlayerIds());    //.contains
        if (listOfPlayerIds.size() < team.getTeamPlayerIds().size()) {
            throw new Exception("One or more player Ids not found.");
        }
        //there should not any duplicate id provided by the user
    }
}
