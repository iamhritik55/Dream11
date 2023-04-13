package com.Dream11.services.utils;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;

public class PlayerUtils {

    public static PlayerRequestDTO createPlayerRequestDto(String name, PlayerTitle title, Integer battingRating,
                                                          Integer bowlingRating, Integer creditCost) {
        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO();
        playerRequestDTO.setName(name);
        playerRequestDTO.setTitle(title);
        playerRequestDTO.setBattingRating(battingRating);
        playerRequestDTO.setBowlingRating(bowlingRating);
        playerRequestDTO.setCreditCost(creditCost);
        return playerRequestDTO;
    }

    public static PlayerResponseDTO createPlayerResponseDto(String id,String name, PlayerTitle title,
                                                            Integer battingRating,
                                                            Integer bowlingRating, Integer creditCost) {
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
        playerResponseDTO.setId(id);
        playerResponseDTO.setName(name);
        playerResponseDTO.setTitle(title);
        playerResponseDTO.setBattingRating(battingRating);
        playerResponseDTO.setBowlingRating(bowlingRating);
        playerResponseDTO.setCreditCost(creditCost);
        return playerResponseDTO;
    }

    public static Player createPlayer(String id,String name, PlayerTitle title, Integer battingRating,
                                      Integer bowlingRating,
                                      Integer creditCost) {
        Player player = new Player();
        player.setId(id);
        player.setName(name);
        player.setTitle(title);
        player.setBattingRating(battingRating);
        player.setBowlingRating(bowlingRating);
        player.setCreditCost(creditCost);
        return player;
    }
}
