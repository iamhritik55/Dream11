package com.Dream11.services;

import com.Dream11.DTO.response.TeamDetailsResponse;
import com.Dream11.services.context.TeamDetailsContext;
import com.Dream11.services.repo.DAO.MatchDAO;
import com.Dream11.utility.TeamDetailsContextUtility;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static com.Dream11.services.utils.MatchUtils.createMatchDao;
import static com.Dream11.services.utils.MatchUtils.createTeamDetailsResponse;
import static com.Dream11.services.utils.TeamDetailsUtils.createTeamDetailsContextForTest;
import static com.Dream11.utility.ApplicationUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest
class UtilityServiceTest {
    @Mock
    private TeamDetailsContextUtility teamDetailsContextUtility;
    @InjectMocks
    private UtilityService utilityService;
    @Mock
    TeamDetailsContext teamDetailsContext;
    @Test
    void createTeamDetailsTest() {
        MatchDAO match=createMatchDao(TEST_TEAM1_ID,TEST_TEAM2_ID);
        TeamDetailsResponse teamDetailsResponseExpected=createTeamDetailsResponse(TEST_TEAM1_ID,TEST_TEAM2_ID,
                TEST_TEAM1_NAME
                ,TEST_TEAM2_NAME,TEAM1_PLAYERS,TEAM2_PLAYERS);
        TeamDetailsContext teamDetailsContext= createTeamDetailsContextForTest(TEAM1,TEAM2,ALL_PLAYERS);
        when(teamDetailsContextUtility.createTeamDetailsContext(match)).thenReturn(teamDetailsContext);
        TeamDetailsResponse teamDetailsResponse=utilityService.createTeamDetails(match);
        assertEquals(teamDetailsResponseExpected,teamDetailsResponse);
        verify(teamDetailsContextUtility.createTeamDetailsContext(match));
    }
}