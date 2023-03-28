package com.Dream11.services.validation;

import com.Dream11.DTO.request.TeamRequestDTO;
import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.services.UtilityService;
import jdk.jshell.execution.Util;
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
    @Autowired
    public UtilityService utilityService;
    public void validateTeam(TeamRequestDTO team) throws Exception {
        if(team.getTeamPlayerIds().size()!=TEAM_SIZE) throw new Exception("Team Size not valid");
        utilityService.validatePlayerIds(team.getTeamPlayerIds());
    }
}
