package com.Dream11.services.transformer;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.Dream11.services.transformer.PlayerTransformer.requestDtoToPlayer;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PlayerTransformerTest {
    @Test
    void requestDtoToPlayerTest() {
        PlayerRequestDTO playerRequestDTO=new PlayerRequestDTO();
        playerRequestDTO.setName("test");
        playerRequestDTO.setTitle(PlayerTitle.STRONG_BATSMAN);
        playerRequestDTO.setBowlingRating(3);
        playerRequestDTO.setBattingRating(5);
        playerRequestDTO.setCreditCost(10);

        Player playerExpected=new Player();
        playerExpected.setName("test");
        playerExpected.setTitle(PlayerTitle.STRONG_BATSMAN);
        playerExpected.setBowlingRating(3);
        playerExpected.setBattingRating(5);
        playerExpected.setCreditCost(10);
        Player player= requestDtoToPlayer(playerRequestDTO);
        assertEquals(playerExpected,player);

    }
}