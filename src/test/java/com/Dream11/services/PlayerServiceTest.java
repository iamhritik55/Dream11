package com.Dream11.services;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.services.validation.PlayerValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class PlayerServiceTest {
    @Mock
    private PlayerRepo playerRepo;
    @InjectMocks
    private PlayerService playerService;
    @Mock
    private PlayerValidation playerValidation;

    @Test
    void addPlayerTest() throws Exception{
        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO();
        playerRequestDTO.setName("test");
        playerRequestDTO.setTitle(PlayerTitle.STRONG_BATSMAN);
        playerRequestDTO.setBattingRating(5);
        playerRequestDTO.setCreditCost(10);
        playerRequestDTO.setBowlingRating(3);
        PlayerResponseDTO playerResponseDTOexpected = new PlayerResponseDTO();
        playerResponseDTOexpected.setName("test");
        playerResponseDTOexpected.setTitle(PlayerTitle.STRONG_BATSMAN);
        playerResponseDTOexpected.setBattingRating(5);
        playerResponseDTOexpected.setCreditCost(10);
        playerResponseDTOexpected.setBowlingRating(3);
        Player player = new Player();
        player.setName("test");
        player.setTitle(PlayerTitle.STRONG_BATSMAN);
        player.setBattingRating(5);
        player.setCreditCost(10);
        player.setBowlingRating(3);
//      `  doNothing().when(playerValidation.validatePlayer(playerRequestDTO));
        when(playerRepo.save(player)).thenReturn(player);
        PlayerResponseDTO playerResponseDTO = playerService.addPlayer(playerRequestDTO);
        assertEquals(playerResponseDTOexpected,playerResponseDTO);
        verify(playerRepo).save(player);
    }
}