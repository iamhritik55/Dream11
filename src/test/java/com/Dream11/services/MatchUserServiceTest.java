package com.Dream11.services;

import com.Dream11.DTO.response.MatchUserStatsResponseDTO;
import com.Dream11.services.Utils.MatchUserStatsUtils;
import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.repo.MatchUserStatsRepo;
import com.Dream11.services.transformer.MatchUserStatsTransformer;
import com.Dream11.services.validation.MatchUserValidation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@RunWith(PowerMockRunner.class)
@PrepareForTest({MatchUserStatsTransformer.class})
class MatchUserServiceTest {
    @Mock
    MatchUserStatsRepo matchUserStatsRepo;
    @InjectMocks
    MatchUserService matchUserService;
    @Mock
    MatchUserValidation matchUserValidation;
    @Mock
    UtilityService utilityService;
    @Mock
    UserService userService;
    @Test
    void testGetAllStats() {
        List<MatchUserStats> expectedMatchUserStatsList = new ArrayList<>();
        expectedMatchUserStatsList.add(MatchUserStatsUtils.createMatchUserStats("1", "2","3",Arrays.asList("P1", "P2", "P3"),56,100,25));
        expectedMatchUserStatsList.add(MatchUserStatsUtils.createMatchUserStats("2", "3","4",Arrays.asList("P1", "P2", "P3"),56,100,25));

        when(matchUserStatsRepo.findAll()).thenReturn(expectedMatchUserStatsList);

        List<MatchUserStats> actualMatchUserStatsList = matchUserService.getAllStats();

        assertEquals(expectedMatchUserStatsList.size(), actualMatchUserStatsList.size());
        assertEquals(expectedMatchUserStatsList.get(0).getId(), actualMatchUserStatsList.get(0).getId());
        assertEquals(expectedMatchUserStatsList.get(0).getMatchId(), actualMatchUserStatsList.get(0).getMatchId());
        assertEquals(expectedMatchUserStatsList.get(0).getUserId(), actualMatchUserStatsList.get(0).getUserId());
        assertEquals(expectedMatchUserStatsList.get(0).getChosenPlayerIdList(), actualMatchUserStatsList.get(0).getChosenPlayerIdList());
        assertEquals(expectedMatchUserStatsList.get(0).getCreditChange(), actualMatchUserStatsList.get(0).getCreditChange());
        assertEquals(expectedMatchUserStatsList.get(0).getTeamPoints(), actualMatchUserStatsList.get(0).getTeamPoints());
        assertEquals(expectedMatchUserStatsList.get(0).getCreditChange(), actualMatchUserStatsList.get(0).getCreditChange());

        assertEquals(expectedMatchUserStatsList.get(1).getId(), actualMatchUserStatsList.get(1).getId());
        assertEquals(expectedMatchUserStatsList.get(1).getMatchId(), actualMatchUserStatsList.get(1).getMatchId());
        assertEquals(expectedMatchUserStatsList.get(1).getUserId(), actualMatchUserStatsList.get(1).getUserId());
        assertEquals(expectedMatchUserStatsList.get(1).getChosenPlayerIdList(), actualMatchUserStatsList.get(1).getChosenPlayerIdList());
        assertEquals(expectedMatchUserStatsList.get(1).getCreditChange(), actualMatchUserStatsList.get(1).getCreditChange());
        assertEquals(expectedMatchUserStatsList.get(1).getTeamPoints(), actualMatchUserStatsList.get(1).getTeamPoints());
        assertEquals(expectedMatchUserStatsList.get(1).getCreditChange(), actualMatchUserStatsList.get(1).getCreditChange());
    }

    @Test
    void testGetUserStats_valid() throws Exception {
        String userId = "2";
        String matchId = "3";
        MatchUserStats matchUserStats = MatchUserStatsUtils.createMatchUserStats("1", userId,matchId,Arrays.asList("P1", "P2", "P3"),56,100,25);

        when(matchUserStatsRepo.findByUserIdAndMatchId(matchUserStats.getUserId(), matchUserStats.getMatchId())).thenReturn(Optional.of(matchUserStats));
        MatchUserStatsResponseDTO responseDTO = matchUserService.getUserStats(matchUserStats.getUserId(), matchUserStats.getMatchId());

        assertNotNull(responseDTO);
        assertEquals(Arrays.asList("P1", "P2", "P3"), responseDTO.getChosenPlayerIdList());
        assertEquals(56, responseDTO.getCreditChange());
        assertEquals(100, responseDTO.getTeamPoints());
        assertEquals(25, responseDTO.getCreditsSpentByUser());
    }
    @Test
    void testGetUserStats_invalid(){
        String userId = "invalid";
        String matchId = "invalid";

        MatchUserStats matchUserStats = MatchUserStatsUtils.createMatchUserStats("1", userId,matchId,Arrays.asList("P1", "P2", "P3"),56,100,25);
        when(matchUserStatsRepo.findByUserIdAndMatchId(matchUserStats.getUserId(), matchUserStats.getMatchId())).thenReturn(Optional.empty());

        assertThrows(Exception.class, ()->{
            matchUserService.getUserStats(userId, matchId);
        });
    }

    @Test
    void testCreateUserTeam() throws Exception {
        String userId = "1";
        String matchId = "2";
        List<String> playerIds = Arrays.asList("P1","P2","P3");
        MatchUserStats matchUserStats = MatchUserStatsUtils.createMatchUserStats("1", userId,matchId,playerIds,0,0,27);

        doNothing().when(matchUserValidation).validateMatchUserIds(matchId, userId);
        doNothing().when(utilityService).validatePlayerIds(playerIds);
        doNothing().when(utilityService).validateUserTeamSize(playerIds);
        when(utilityService.calculateTeamCost(playerIds)).thenReturn(27);
        doNothing().when(utilityService).restrictPlayerIds(playerIds);
        doNothing().when(userService).subtractUserCredits(userId, 27);
        MatchUserStatsResponseDTO expectedResponseDTO  = new MatchUserStatsResponseDTO();
        expectedResponseDTO.setChosenPlayerIdList(playerIds);
        expectedResponseDTO.setCreditChange(matchUserStats.getCreditChange());
        expectedResponseDTO.setTeamPoints(matchUserStats.getTeamPoints());
        expectedResponseDTO.setCreditsSpentByUser(matchUserStats.getCreditsSpentByUser());
        when(matchUserStatsRepo.save(any(MatchUserStats.class))).thenReturn(matchUserStats);

        MatchUserStatsResponseDTO actualResponseDTO = matchUserService.createUserTeam(matchId, userId, playerIds);

        assertEquals(expectedResponseDTO, actualResponseDTO);
    }
}