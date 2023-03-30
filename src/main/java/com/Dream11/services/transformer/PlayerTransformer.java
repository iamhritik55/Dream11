package com.Dream11.services.transformer;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.services.models.Player;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> playerListToNameList(List<Player> playerList){
        List<String> nameList = new ArrayList<>();
        playerList.forEach(player-> nameList.add(player.getName()));
        return nameList;
    }
}
