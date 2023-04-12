package com.Dream11.utility;

import com.Dream11.services.MatchService;
import com.Dream11.services.TeamService;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.gamecontroller.CricketUtility;
import com.Dream11.services.models.Match;
import com.Dream11.services.models.Team;
import com.Dream11.services.validation.CricketMatchValidation;
import com.Dream11.services.validation.MatchValidation;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.Dream11.Utils.TeamUtils.createTeam2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.Dream11.Utils.MatchContextUtils.createMatchContext;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static com.Dream11.Utils.MatchUtils.createMatch;
import static com.Dream11.Utils.TeamUtils.createTeam1;
import static org.hamcrest.MatcherAssert.assertThat;


@SpringBootTest
class ContextUtilityTest {
    @InjectMocks
    ContextUtility contextUtility;

    @Mock
    MatchService matchService;

    @Mock
    TeamService teamService;

    @Mock
    CricketMatchValidation matchValidation;

    @Test
    void createAndValidateCricketContextTest_ValidMatchId() throws Exception {
        Match match = createMatch("1","1","2");
        Team team1 = createTeam1("1","team1");
        Team team2 = createTeam2("2","team2");

        when(matchService.getMatchById("1")).thenReturn(match);
        when(teamService.getTeamById("1")).thenReturn(team1);
        when(teamService.getTeamById("2")).thenReturn(team2);
        doNothing().when(matchValidation).matchCompletedValidation(match);

        CricketMatchContext expectedMatchContext1 = createMatchContext(match,team1,team2, "1");
        CricketMatchContext expectedMatchContext2 = createMatchContext(match,team1,team2, "2");

        CricketMatchContext actualMatchContext = contextUtility.createAndValidateCricketContext("1");


//        assertEquals(expectedMatchContext2,actualMatchContext);
//        assertTrue(actualMatchContext==expectedMatchContext1);
        assertThat(actualMatchContext, Matchers.anyOf(Matchers.equalTo(expectedMatchContext1),Matchers.equalTo(expectedMatchContext2)));
    }

    @Test
    void fetchInningContext() {
    }

    @Test
    void swapInningContextTeams() {
    }
}