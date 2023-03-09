package com.Dream11.services;

import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.utility.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UtilityService {
    @Autowired
    private PlayerRepo playerRepo;

    //checking if user creates a team of at least 3 players and maximum 5 players
    public void validateTeamSize(List<String> playerIds) throws Exception {
        if (playerIds.size() < ApplicationUtils.MIN_SIZE) {
            throw new Exception("You have to choose a team of at least 3 players.");
        }
        if (playerIds.size() > ApplicationUtils.MAX_SIZE) {
            throw new Exception("You can only choose a team of maximum 5 players.");
        }
    }
    public void validatePlayerIds(List<String> playerIds) throws Exception {
        List<Player> listOfPlayerIds = playerRepo.findAllById(playerIds);
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
        if(!CollectionUtils.isEmpty(listOfPlayerIds)){
            for (Player player : listOfPlayerIds) {
                    totalCost += player.getCreditCost();
            }
        }
        return totalCost;
    }

    //checking the list of player ids providing by the user present in database or not

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
            throw new Exception("Team can have at most 1 All-rounder with 1 strong player, or 2 strong players with no all rounders.");
        }
        //user can have at most 1 All-rounder with 1 strong player
        else if (strongPlayerCount > 1 && allRounderCount >= 1) {
            throw new Exception("Team can have at most 1 All-rounder with 1 strong player, or 2 strong players with no all rounders.");
        }
    }
}
