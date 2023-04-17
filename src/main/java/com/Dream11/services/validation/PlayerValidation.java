package com.Dream11.services.validation;

import com.Dream11.DTO.request.PlayerRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class PlayerValidation {
    public void validatePlayer(PlayerRequestDTO player) throws Exception{
        if(player.getName().isEmpty()) throw new Exception("Player name can't be empty");
    }
}
