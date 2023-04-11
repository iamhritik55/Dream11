package com.Dream11.services.repo;

import com.Dream11.services.models.MatchUserStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class MatchUserStatsRepoTest {
    @Autowired
    private MatchUserStatsRepo matchUserStatsRepo;

    @Test
    void findByMatchId() {
        MatchUserStats matchUserStats = new MatchUserStats();
    }

}
