package com.Dream11.utility;

import com.Dream11.services.models.MatchUserStats;
import com.Dream11.services.models.PlayerStats;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static com.Dream11.Utils.MatchUserStatsUtils.createMatchUserStats;
import static com.Dream11.Utils.PlayerStatsUtils.createListOfPlayerStats;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class MatchUserUtilityTest {

    @InjectMocks
    MatchUserUtility matchUserUtility;
    @Test
    void updateTeamPointsTest() {
        MatchUserStats matchUserStats = createMatchUserStats(List.of("1","2","3","4","5"),100);

        List<PlayerStats> playerStatsList = createListOfPlayerStats(List.of("1","2","3","4","5"), List.of(100,40,50,
                80,70));

        MatchUserStats actualMatchUserStats = matchUserUtility.updateTeamPoints2(matchUserStats,playerStatsList);
//        doNothing().when(Collections.class).
        MatchUserStats expectedMatchUserStats = createMatchUserStats(List.of("1","2","3","4","5"),440);

        assertEquals(expectedMatchUserStats,actualMatchUserStats);
    }

    @Test
    void distributeCredits() {
    }
}