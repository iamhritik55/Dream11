package com.Dream11.services.transformer;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.PlayerStats;

import java.util.ArrayList;
import java.util.List;

public class PlayerTransformer {

    public static Player requestDtoToPlayer(PlayerRequestDTO playerRequestDTO) {
        Player player = new Player();
        player.setName(playerRequestDTO.getName());
        player.setBattingRating(playerRequestDTO.getBattingRating());
        player.setBowlingRating(playerRequestDTO.getBowlingRating());
        player.setTitle(playerRequestDTO.getTitle());
        player.setCreditCost(playerRequestDTO.getCreditCost());
        return player;
    }

    public static PlayerResponseDTO playerToResponseDto(Player player) {
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
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

    public static List<PlayerResponseDTO> createListOfPlayerResponse(List<Player> players) {
        List<PlayerResponseDTO> playerResponseDTOS=new ArrayList<>();
        for (Player player : players) {
            playerResponseDTOS.add(playerToResponseDto(player));
        }
        return playerResponseDTOS;
    }

    public static List<PlayerStats> createPlayerStatList(List<Player> players){
        List<PlayerStats> playerStatsList = new ArrayList<>();
        players.forEach(player -> {
            PlayerStats playerStats = new PlayerStats();
            playerStats.setPlayerId(player.getId());
            playerStats.setPlayerName(player.getName());
            playerStatsList.add(playerStats);
        });

        return playerStatsList;
    }
}
