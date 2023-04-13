package com.Dream11.services.validation;

import com.Dream11.DTO.request.PlayerRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PlayerValidationTest {

    @Autowired
    private PlayerValidation playerValidation;

    @Test
    void validatePlayerTest(){
        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO();
        playerRequestDTO.setName("");
        Throwable exception = assertThrows(Exception.class, () -> playerValidation.validatePlayer(playerRequestDTO));
         assertEquals("Player name can't be empty", exception.getMessage());
    }
}