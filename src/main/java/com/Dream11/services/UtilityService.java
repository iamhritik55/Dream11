package com.Dream11.services;

import com.Dream11.services.repo.DAO.MatchDAO;
import com.Dream11.DTO.response.MatchResponseDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.DTO.response.TeamResponseDTO;
import com.Dream11.services.models.Match;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.Team;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.services.repo.TeamRepo;
import com.Dream11.utility.ApplicationUtils;
import com.Dream11.DTO.response.TeamDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.Dream11.services.transformer.MatchTransformer.generateMatchResponseDto;
import static com.Dream11.services.transformer.PlayerTransformer.playerToResponseDto;
import static com.Dream11.services.transformer.TeamTransformer.teamToResponseDto;

@Service
public class UtilityService {

    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private TeamRepo teamRepo;

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

        List<Player> listOfPlayerIds = playerRepo.findAllById(playerIds);    //.contains
        if (listOfPlayerIds.size() < playerIds.size()) {
            throw new Exception("One or more player Ids not found.");
        }
        //there should not any duplicate id provided by the user
        Set<String> playerIdSet = new HashSet<>(playerIds);
        if (playerIdSet.size() < playerIds.size()) {
            throw new Exception("Duplicate player Ids found in team.");
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
        //user can only choose either two strong players or 1 all-rounder
        if (strongPlayerCount > 2 || allRounderCount > 1) {
            throw new Exception(
                    "Team can have at most 1 All-rounder with 1 strong player, or 2 strong players with no all rounders.");
        }
        //user can have at most 1 All-rounder with 1 strong player
        else if (strongPlayerCount > 1 && allRounderCount >= 1) {
            throw new Exception(
                    "Team can have at most 1 All-rounder with 1 strong player, or 2 strong players with no all rounders.");
        }
    }

    public List<MatchResponseDTO> createListOfMatchResponseDTO(List<Match> matches) {
        List<MatchResponseDTO> matchResponseDTOS = new ArrayList<>();
        for (Match match : matches) {
            matchResponseDTOS.add(generateMatchResponseDto(match));
        }
        return matchResponseDTOS;
    }// TODO: 28/03/23 add it to match transformer

    public TeamDetailsResponse createTeamDetails(MatchDAO match) throws Exception {
        TeamDetailsResponse teamDetails = new TeamDetailsResponse();
        List<String> teamIds = new ArrayList<>();
        teamIds.add(match.getTeam1Id());
        teamIds.add(match.getTeam2Id());
        List<Team> teams = teamRepo.findAllById(teamIds);

        TeamResponseDTO team1 = getTeamResponseByTeamList(match.getTeam1Id(), teams);
        TeamResponseDTO team2 = getTeamResponseByTeamList(match.getTeam2Id(), teams);
        // TODO: 28/03/23 use streams
        List<String> playerIds = new ArrayList<>();
        playerIds.addAll(team1.getTeamPlayerIds());
        playerIds.addAll(team2.getTeamPlayerIds());
        List<Player> players = playerRepo.findAllById(playerIds);
        // TODO: 16/03/23 only one repo call should be there-done
        //todo make code cleaner

        List<PlayerResponseDTO> team1PlayerDTOs = createListOfTeamPlayerResponseDTO(team1.getTeamPlayerIds(), players);
        List<PlayerResponseDTO> team2PlayerDTOs = createListOfTeamPlayerResponseDTO(team2.getTeamPlayerIds(), players);
        // TODO: 28/03/23 use match context to create teamDetails
        teamDetails.setTeam1Id(match.getTeam1Id());
        teamDetails.setTeam2Id(match.getTeam2Id());
        teamDetails.setTeam1Name(team1.getName());
        teamDetails.setTeam2Name(team2.getName());
        teamDetails.setTeam1Players(team1PlayerDTOs);
        teamDetails.setTeam2Players(team2PlayerDTOs);
        return teamDetails;
    }

    public List<PlayerResponseDTO> createListOfTeamPlayerResponseDTO(List<String> teamPlayerIds, List<Player> players) {
        List<PlayerResponseDTO> playerResponseDTOS = new ArrayList<>();
        for (Player player : players) {
            if (teamPlayerIds.contains(player.getId())) {
                playerResponseDTOS.add((playerToResponseDto(player)));
            }
        }
        return playerResponseDTOS;
    }

    public List<PlayerResponseDTO> createListOfPlayerResponseDTO(List<Player> players) {
        List<PlayerResponseDTO> playerResponseDTOS = new ArrayList<>();
        for (Player player : players) {
            playerResponseDTOS.add((playerToResponseDto(player)));
        }
        return playerResponseDTOS;
    }

    public TeamResponseDTO getTeamResponseByTeamList(String teamId, List<Team> teams) {
        if (Objects.equals(teamId, teams.get(0).getId())) {
            return teamToResponseDto(teams.get(0));
        } else {
            return teamToResponseDto(teams.get(1));
        }
    }

}
