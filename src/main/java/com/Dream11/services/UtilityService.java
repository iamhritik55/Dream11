package com.Dream11.services;

import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.DTO.response.TeamDetailsResponse;
import com.Dream11.DTO.response.TeamResponseDTO;
import com.Dream11.services.context.TeamDetailsContext;
import com.Dream11.services.models.Player;
import com.Dream11.services.repo.DAO.MatchDAO;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.utility.ApplicationUtils;
import com.Dream11.utility.TeamDetailsContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.Dream11.services.transformer.PlayerTransformer.createListOfPlayerResponse;
import static com.Dream11.services.transformer.TeamTransformer.teamToResponseDto;

@Service
public class UtilityService {

    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private TeamDetailsContextUtility teamDetailsContextUtility;
    //checking if user creates a team of at least 3 players and maximum 5 players
    public void validateUserTeamSize(List<String> playerIds) throws Exception {
        if (playerIds.size() < ApplicationUtils.MIN_SIZE) {
            throw new Exception("You have to choose a team of at least 3 players.");
        }
        if (playerIds.size() > ApplicationUtils.MAX_SIZE) {
            throw new Exception("You can only choose a team of maximum 5 players.");
        }
    }

    public void validatePlayerIds(List<String> playerIds) throws Exception {
        Set<String> playerIdSet = new HashSet<>(playerIds);
        if (playerIdSet.size() < playerIds.size()) {
            throw new Exception("Duplicate player Ids found in team.");
        }
        List<Player> listOfPlayerIds = playerRepo.findAllById(playerIds);    //.contains
        if (listOfPlayerIds.size() < playerIds.size()) {
            throw new Exception("One or more player Ids not found.");
        }

    }

    public int calculateTeamCost(List<String> playerIds) {
        int totalCost = 0;
        List<Player> listOfPlayerIds = playerRepo.findAllById(playerIds);
        if (!CollectionUtils.isEmpty(listOfPlayerIds)) {
            for (Player player : listOfPlayerIds) {
                totalCost += player.getCreditCost();
            }
        }
        return totalCost;
    }

    //restrictions on user to choose players of different credits
    public void restrictPlayerIds(List<String> playerIds) throws Exception {
        int strongPlayerCount = 0;
        int allRounderCount = 0;
        List<Player> listOfPlayerIds = playerRepo.findAllById(playerIds);
        for (Player player : listOfPlayerIds) {
            if (player.getCreditCost() == ApplicationUtils.STRONG_PLAYER_COST) {
                strongPlayerCount++;
            } else if (player.getCreditCost() == ApplicationUtils.ALL_ROUNDER_COST) {
                allRounderCount++;
            }
        }
        if ((strongPlayerCount > 1 && allRounderCount >= 1)||(strongPlayerCount > 2 || allRounderCount > 1)) {
            throw new Exception(
                    "Team can have at most 1 All-rounder with 1 strong player, or 2 strong players with no all rounders.");
        }
    }
    public TeamDetailsResponse createTeamDetails(MatchDAO match) throws Exception {
        TeamDetailsResponse teamDetails = new TeamDetailsResponse();
        TeamDetailsContext teamDetailsContext = teamDetailsContextUtility.createTeamDetailsContext(match);
        TeamResponseDTO team1 = teamToResponseDto(teamDetailsContext.getTeam1());
        TeamResponseDTO team2 = teamToResponseDto(teamDetailsContext.getTeam2());
        List<Player> team1Players = teamDetailsContext.getPlayers().stream().filter(player -> team1.getTeamPlayerIds()
                                                                                                   .contains(
                                                                                                           player.getId()))
                                                      .toList();
        List<Player> team2Players = teamDetailsContext.getPlayers().stream().filter(player -> team2.getTeamPlayerIds()
                                                                                                   .contains(
                                                                                                           player.getId()))
                                                      .toList();

        List<PlayerResponseDTO> team1PlayerDTOs = createListOfPlayerResponse(team1Players);
        List<PlayerResponseDTO> team2PlayerDTOs = createListOfPlayerResponse(team2Players);

        teamDetails.setTeam1Id(match.getTeam1Id());
        teamDetails.setTeam2Id(match.getTeam2Id());
        teamDetails.setTeam1Name(team1.getName());
        teamDetails.setTeam2Name(team2.getName());
        teamDetails.setTeam1Players(team1PlayerDTOs);
        teamDetails.setTeam2Players(team2PlayerDTOs);
        return teamDetails;
    }
}
