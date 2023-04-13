package com.Dream11.services;

import com.Dream11.DTO.request.PlayerRequestDTO;
import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;
import com.Dream11.services.repo.PlayerRepo;
import com.Dream11.services.validation.PlayerValidation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.Dream11.services.utils.PlayerUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlayerServiceTest {

    @Mock
    private PlayerRepo playerRepo;
    @InjectMocks
    private PlayerService playerService;
    @Mock
    private PlayerValidation playerValidation;

    /**
     *
     * INput
     * Ouput
     * Test Description
     */

    @Test
    void addPlayerTest_validCase() throws Exception {
        PlayerRequestDTO playerRequestDTO = createPlayerRequestDto("test", PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
        PlayerResponseDTO playerResponseDTOexpected = createPlayerResponseDto("id", "test", PlayerTitle.STRONG_BATSMAN,
                5, 3, 10);
        Player player = createPlayer("id", "test", PlayerTitle.STRONG_BATSMAN, 5, 3, 10);
        doNothing().when(playerValidation).validatePlayer(playerRequestDTO);
        when(playerRepo.save(player)).thenReturn(player);
        PlayerResponseDTO playerResponseDTO = playerService.addPlayer(playerRequestDTO);
        assertEquals(playerResponseDTOexpected, playerResponseDTO);
        verify(playerRepo).save(player);
    }
}