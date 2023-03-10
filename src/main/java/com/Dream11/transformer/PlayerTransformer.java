package com.Dream11.transformer;

import com.Dream11.DTO.PlayerDTO;
import com.Dream11.entity.Player;

public class PlayerTransformer {
    public static Player DTOToPlayer(PlayerDTO playerDTO){
        Player player=new Player();
        player.setName(playerDTO.getName());
        player.setId(playerDTO.getId());
        player.setTitle(playerDTO.getTitle());
        player.setBattingRating(playerDTO.getBattingRating());
        player.setBowlingRating(playerDTO.getBowlingRating());
        player.setCreditCost(playerDTO.getCreditCost());
        return player;
    }
    public static PlayerDTO playerToDTO(Player player){
        PlayerDTO playerDTO=new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());
        playerDTO.setTitle(player.getTitle());
        playerDTO.setBowlingRating(player.getBowlingRating());
        playerDTO.setBattingRating(player.getBattingRating());
        playerDTO.setCreditCost(player.getCreditCost());
        return playerDTO;
    }
}
