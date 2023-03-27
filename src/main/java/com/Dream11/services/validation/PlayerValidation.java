package com.Dream11.services.validation;

import com.Dream11.DTO.PlayerRequestDTO;
import org.springframework.stereotype.Service;

import static com.Dream11.utility.ApplicationUtils.playerTitles;

@Service
public class PlayerValidation {
    public void validatePlayer(PlayerRequestDTO player) throws Exception{
        if(player.getName().isEmpty()) throw new Exception("Player name can't be empty");
        else if (!(playerTitles.contains(player.getTitle()))) throw new Exception("Invalid player title");
    }
}
