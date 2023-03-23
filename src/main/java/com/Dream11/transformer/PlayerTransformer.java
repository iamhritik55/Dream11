package com.Dream11.transformer;

import com.Dream11.DTO.PlayerDTO;
import com.Dream11.DTO.PlayerRequestDTO;
import com.Dream11.DTO.PlayerResponseDTO;
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
        playerResponseDTO.setName(player.getName());
        playerResponseDTO.setBattingRating(player.getBattingRating());
        playerResponseDTO.setBowlingRating(player.getBowlingRating());
        playerResponseDTO.setTitle(player.getTitle());
        playerResponseDTO.setCreditCost(player.getCreditCost());
        return playerResponseDTO;
    }
}
