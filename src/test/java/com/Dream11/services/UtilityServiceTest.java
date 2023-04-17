package com.Dream11.services;

import com.Dream11.services.Utils.PlayerUtils;
import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;
import com.Dream11.services.repo.PlayerRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UtilityServiceTest {
    @Mock
    PlayerRepo playerRepo;
    @InjectMocks
    UtilityService utilityService;
    @Test
    void validateUserTeamSize_withValidSize_shouldNotThrowException(){
        List<String> playerIds = Arrays.asList("1","2","3","4");

        assertDoesNotThrow(() -> utilityService.validateUserTeamSize(playerIds));
    }
    @Test
    void validateUserTeamSize_withLessThanMinTeamSize_shouldThrowException() {
        List<String> playerIds = Arrays.asList("1","2");

       Exception exception = assertThrows(Exception.class, () -> {
            utilityService.validateUserTeamSize(playerIds);
        });
        assertEquals("You have to choose a team of at least 3 players.", exception.getMessage());

    }

    @Test
    void validateUserTeamSize_withMoreThanMaxTeamSize_shouldThrowException() {
        List<String> playerIds = Arrays.asList("1","2","3","4","5","6");

        Exception exception = assertThrows(Exception.class, () -> {
            utilityService.validateUserTeamSize(playerIds);
        });
        assertEquals("You can only choose a team of maximum 5 players.", exception.getMessage());

    }

    @Test
    void validatePlayerIds_withValidIds_shouldNotThrowException() {
        List<String> playerIds = Arrays.asList("1","2","3","4");

        List<Player> players = new ArrayList<>();
        players.add(PlayerUtils.createPLayer("1","Test1",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("2","Test2",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("3","Test3",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("4","Test4",5,3, PlayerTitle.STRONG_BATSMAN,10));

        when(playerRepo.findAllById(playerIds)).thenReturn(players);
        assertDoesNotThrow(() -> utilityService.validatePlayerIds(playerIds));

    }
    @Test
    void validatePlayerIds_withDuplicateIds_shouldThrowException() {
        List<String> playerIds = Arrays.asList("1","2","1","4");

        List<Player> players = new ArrayList<>();
        players.add(PlayerUtils.createPLayer("1","Test1",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("2","Test2",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("1","Test3",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("4","Test4",5,3, PlayerTitle.STRONG_BATSMAN,10));

        when(playerRepo.findAllById(playerIds)).thenReturn(players);
        Exception exception = assertThrows(Exception.class, () -> {
            utilityService.validatePlayerIds(playerIds);
        });
        assertEquals("Duplicate player Ids found in team.", exception.getMessage());

    }
    @Test
    void validatePlayerIds_withInvalidIds_shouldThrowException() {
        List<String> playerIds = Arrays.asList("1","2","3","5");

        List<Player> players = new ArrayList<>();
        players.add(PlayerUtils.createPLayer("1","Test1",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("2","Test2",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("3","Test3",5,3, PlayerTitle.STRONG_BATSMAN,10));

        when(playerRepo.findAllById(playerIds)).thenReturn(players);
        Exception exception = assertThrows(Exception.class, () -> {
            utilityService.validatePlayerIds(playerIds);
        });
        assertEquals("One or more player Ids not found.", exception.getMessage());

    }

    @Test
    void calculateTeamCost() {
        List<String> playerIds = Arrays.asList("1","2","3");

        List<Player> players = new ArrayList<>();
        players.add(PlayerUtils.createPLayer("1","Test1",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("2","Test2",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("3","Test3",5,3, PlayerTitle.AVERAGE_BOWLER,7));

        when(playerRepo.findAllById(playerIds)).thenReturn(players);

        int teamCost = utilityService.calculateTeamCost(playerIds);

        assertEquals(27, teamCost);
    }

    @Test
    void restrictPlayerIds_valid(){
        List<String> playerIds = Arrays.asList("1","2","3");

        List<Player> players = new ArrayList<>();
        players.add(PlayerUtils.createPLayer("1","Test1",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("2","Test2",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("3","Test3",5,3, PlayerTitle.AVERAGE_BOWLER,7));

        when(playerRepo.findAllById(playerIds)).thenReturn(players);
        assertDoesNotThrow(() -> utilityService.restrictPlayerIds(playerIds));
    }
    @Test
    void restrictPlayerIds_invalid() {
        List<String> playerIds = Arrays.asList("1","2","3","4");

        List<Player> players = new ArrayList<>();
        players.add(PlayerUtils.createPLayer("1","Test1",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("2","Test2",5,3, PlayerTitle.STRONG_BATSMAN,10));
        players.add(PlayerUtils.createPLayer("3","Test3",4,4, PlayerTitle.ALL_ROUNDER,12));

        when(playerRepo.findAllById(playerIds)).thenReturn(players);
        Exception exception = assertThrows(Exception.class, () -> {
            utilityService.restrictPlayerIds(playerIds);
        });
        assertEquals("Team can have at most 1 All-rounder with 1 strong player, or 2 strong players with no all rounders.", exception.getMessage());
    }
}