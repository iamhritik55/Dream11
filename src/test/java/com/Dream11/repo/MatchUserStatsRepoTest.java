package com.Dream11.repo;

import com.Dream11.entity.MatchUserStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MatchUserStatsRepoTest {
    @Autowired
    private MatchUserStatsRepo matchUserStatsRepo;
    @Test
    void findByMatchId() {
        MatchUserStats matchUserStats = new MatchUserStats();
    }

}