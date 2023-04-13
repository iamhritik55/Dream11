package com.Dream11.services;

import com.Dream11.DTO.response.PlayerResponseDTO;
import com.Dream11.DTO.response.TeamDetailsResponse;
import com.Dream11.services.models.Match;
import com.Dream11.services.repo.DAO.MatchDAO;
import com.Dream11.services.repo.MatchRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.Dream11.services.utils.MatchUtils.createMatch;
import static com.Dream11.services.utils.MatchUtils.createTeamDetailsResponse;
import static com.Dream11.utility.ApplicationUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MatchServiceTest {
    @Mock
    private UtilityService utilityService;
    @InjectMocks
    private MatchService matchService;
    @Mock
    private MatchRepo matchRepo;
    @Test
    void getTeamDetailsTest() throws Exception {

        String matchId=TEST_MATCH_ID;
        Match match=createMatch(TEST_TEAM1_ID,TEST_TEAM2_ID);
        TeamDetailsResponse teamDetailsResponseExpected=createTeamDetailsResponse(TEST_TEAM1_ID,TEST_TEAM2_ID,
                TEST_TEAM1_NAME
                ,TEST_TEAM2_NAME,TEAM1_PLAYERS,TEAM2_PLAYERS);
        when(matchRepo.findById(TEST_MATCH_ID)).thenReturn(Optional.of(match));
        when(utilityService.createTeamDetails(new MatchDAO(TEST_TEAM1_ID,TEST_TEAM2_ID))).thenReturn(teamDetailsResponseExpected);
        TeamDetailsResponse teamDetailsResponse=matchService.getTeamDetails(TEST_MATCH_ID);
        assertEquals(teamDetailsResponseExpected,teamDetailsResponse);
        verify(utilityService).createTeamDetails(new MatchDAO(TEST_TEAM1_ID,TEST_TEAM2_ID));
    }


}