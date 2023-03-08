package com.Dream11.services;
import com.Dream11.entity.Player;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.utility.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UtilityService {
    @Autowired
    private PlayerRepo playerRepo;
    //checking if user creates a team of at least 3 players and maximum 5 players
    public void validateTeamSize(List<Integer> playerIds) throws Exception{
        if(playerIds.size() < ApplicationUtils.MIN_SIZE){
            throw new Exception("You have to choose a team of at least 3 players.");
        }
        if(playerIds.size() > ApplicationUtils.MAX_SIZE){
            throw new Exception("You can only choose a team of maximum 5 players.");
        }
    }
    public int calculateTeamCost(List<Integer> playerIds){
        int totalCost = 0;
        for(Integer playerId : playerIds) {
            Player player = playerRepo.findById(playerId).orElse(null);
            if(player != null){
                totalCost += player.getCreditCost();
            }
        }
        return totalCost;
    }
    //checking the list of player ids providing by the user present in database or not
    public void validatePlayerIds(List<Integer> playerIds) throws Exception{
        //there should not any duplicate id provided by the user
        Set<Integer> playerIdSet = new HashSet<>(playerIds);
        if(playerIdSet.size() < playerIds.size()){
            throw new Exception("Duplicate player Ids found in team.");
        }
        for(Integer playerId : playerIds){
            Player player = playerRepo.findById(playerId).orElse(null);
            //if player not present throw error
            if(player == null){
                throw new Exception("Player of id " + playerId + " not found.");
            }
        }
    }
    //restrictions on user to choose players of different credits
    public void restrictPlayerIds(List<Integer> playerIds) throws Exception{
        int strongPlayerCount = 0;
        int allRounderCount = 0;
        for(Integer playerId : playerIds){
            Player player = playerRepo.findById(playerId).orElse(null);

            if(player.getCreditCost() == ApplicationUtils.STRONG_PLAYER_COST){
                strongPlayerCount++;
            }
            else if(player.getCreditCost() == ApplicationUtils.ALL_ROUNDER_COST){
                allRounderCount++;
            }
        }
        //user can only choose either two strong players or 1 all-rounder
        if(strongPlayerCount > 2 || allRounderCount > 1){
            throw new Exception("Team can have at most 1 All-rounder with 1 strong player, or 2 strong players with no all rounders.");
        }
        //user can have at most 1 All-rounder with 1 strong player
        else if(strongPlayerCount > 1 && allRounderCount >= 1){
            throw new Exception("Team can have at most 1 All-rounder with 1 strong player, or 2 strong players with no all rounders.");
        }
    }
}
