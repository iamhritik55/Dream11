package com.Dream11.services;
import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.Match;
import com.Dream11.services.models.MatchStats;
import com.Dream11.services.models.PlayerStats;
import com.Dream11.services.models.Team;
import com.Dream11.services.repo.MatchStatsRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MatchStatsServiceTest {
    @Mock
    MatchStatsRepo matchStatsRepo;
    @InjectMocks
    private MatchStatsService matchStatsService;

    @Test
    void getWinnerTeamNameTest_team1Wins() {
        CricketMatchContext matchContext = new CricketMatchContext();
        Team team1 = new Team();
        team1.setName("team1");
        team1.setTeamRuns(200);

        Team team2 = new Team();
        team2.setName("team2");
        team2.setTeamRuns(180);

        matchContext.setTeam1(team1);
        matchContext.setTeam2(team2);

        String real = matchStatsService.getWinnerTeamName(matchContext);

        assertEquals("team1", real);
    }

    @Test
    void getWinnerTeamNameTest_team2Wins() {
        CricketMatchContext matchContext = new CricketMatchContext();
        Team team1 = new Team();
        team1.setName("team1");
        team1.setTeamRuns(160);

        Team team2 = new Team();
        team2.setName("team2");
        team2.setTeamRuns(180);

        matchContext.setTeam1(team1);
        matchContext.setTeam2(team2);

        String real = matchStatsService.getWinnerTeamName(matchContext);

        assertEquals("team2", real);
    }

    @Test
    void getWinnerTeamNameTest_tied() {
        CricketMatchContext matchContext = new CricketMatchContext();
        Team team1 = new Team();
        team1.setName("team1");
        team1.setTeamRuns(200);

        Team team2 = new Team();
        team2.setName("team2");
        team2.setTeamRuns(200);

        matchContext.setTeam1(team1);
        matchContext.setTeam2(team2);

        String real = matchStatsService.getWinnerTeamName(matchContext);

        assertEquals("Tied", real);
    }


    @Test
    void findMatchStatsById_ValidId() throws Exception{
        String id = "123";
        MatchStats expectedMatchStats = new MatchStats();
        expectedMatchStats.setId(id);

        when(matchStatsRepo.findById(id)).thenReturn(Optional.of(expectedMatchStats));

        MatchStats actualMatchStats = matchStatsService.findMatchStatsById(id);

        verify(matchStatsRepo).findById(id);
        assertEquals(expectedMatchStats, actualMatchStats);

    }

    @Test
    void findMatchStatsById_InvalidId() throws Exception{
        String id = "invalidId";
        when(matchStatsRepo.findById(id)).thenReturn(Optional.empty());

        Throwable exception =  assertThrows(Exception.class, ()->matchStatsService.findMatchStatsById(id));

        assertEquals("MatchStats id not found!",exception.getMessage());
    }

    @Test
    void storeAllMatchDataTest_CorrectData() {
        CricketMatchContext matchContext = new CricketMatchContext();

        Match match = new Match();
        match.setMatchId("1");
        match.setTeam1Id("t1");
        match.setTeam2Id("t2");

        Team team1 = new Team();
        team1.setId("t1");
        team1.setName("team1");
        team1.setTeamRuns(100);
        team1.setTeamPlayerIds(List.of("1","2","3"));

        Team team2 = new Team();
        team2.setId("t2");
        team2.setName("team2");
        team2.setTeamRuns(200);
        team2.setTeamPlayerIds(List.of("4","5","6"));
        matchContext.setTeam1(team1);
        matchContext.setTeam2(team2);
        matchContext.setMatch(match);


        CricketInningContext inningContext = new CricketInningContext();
        PlayerStats playerStat1 = new PlayerStats();
        playerStat1.setPlayerName("Player1");
        playerStat1.setPlayerId("1");
        playerStat1.setBattingRuns(10);

        PlayerStats playerStat2 = new PlayerStats();
        playerStat2.setPlayerName("Player2");
        playerStat2.setPlayerId("2");
        playerStat2.setBattingRuns(20);

        PlayerStats playerStat3 = new PlayerStats();
        playerStat3.setPlayerName("Player3");
        playerStat3.setPlayerId("3");
        playerStat3.setBattingRuns(30);

        PlayerStats playerStat4 = new PlayerStats();
        playerStat4.setPlayerName("Player4");
        playerStat4.setPlayerId("4");
        playerStat4.setBattingRuns(40);

        PlayerStats playerStat5 = new PlayerStats();
        playerStat5.setPlayerName("Player5");
        playerStat5.setPlayerId("5");
        playerStat5.setBattingRuns(50);

        PlayerStats playerStat6 = new PlayerStats();
        playerStat6.setPlayerName("Player6");
        playerStat6.setPlayerId("6");
        playerStat6.setBattingRuns(60);

        List<PlayerStats> playerStatsList = List.of(playerStat1,playerStat2,playerStat3,playerStat4,playerStat5,
                playerStat6);

        inningContext.setPlayerStatsList(playerStatsList);
        MatchStats expectedMatchStats = new MatchStats();
        expectedMatchStats.setTeam1PlayerStats(List.of(playerStat1,playerStat2,playerStat3));
        expectedMatchStats.setTeam2PlayerStats(List.of(playerStat4,playerStat5,playerStat6));
        expectedMatchStats.setTeam1Name(team1.getName());
        expectedMatchStats.setTeam2Name(team2.getName());
        expectedMatchStats.setTeam1Score(team1.getTeamRuns());
        expectedMatchStats.setTeam2Score(team2.getTeamRuns());
        expectedMatchStats.setId(match.getMatchId());
        expectedMatchStats.setWinnerTeamName(team2.getName());

        when(matchStatsRepo.save(any())).thenReturn(expectedMatchStats);

        MatchStats matchStats = matchStatsService.storeAllMatchData(matchContext, inningContext);

        verify(matchStatsRepo).save(expectedMatchStats);


    }
}