package com.Dream11.transformer;

import com.Dream11.DTO.PlayerRequestDTO;
import com.Dream11.DTO.PlayerResponseDTO;
import com.Dream11.entity.Player;

public class PlayerTransformer {
    public static Player requestDtoToPlayer(PlayerRequestDTO playerRequestDTO){
        Player player=new Player();
        player.setName(playerRequestDTO.getName());
        player.setBattingRating(playerRequestDTO.getBattingRating());
        player.setBowlingRating(playerRequestDTO.getBowlingRating());
        player.setTitle(playerRequestDTO.getTitle());
        player.setCreditCost(playerRequestDTO.getCreditCost());
        return player;
    }
    public static PlayerResponseDTO playerToResponseDto(Player player){
        PlayerResponseDTO playerResponseDTO=new PlayerResponseDTO();
        playerResponseDTO.setId(player.getId());
        playerResponseDTO.setName(player.getName());
        playerResponseDTO.setBattingRating(player.getBattingRating());
        playerResponseDTO.setBowlingRating(player.getBowlingRating());
        playerResponseDTO.setTitle(player.getTitle());
        playerResponseDTO.setCreditCost(player.getCreditCost());
        return playerResponseDTO;
    }
}
