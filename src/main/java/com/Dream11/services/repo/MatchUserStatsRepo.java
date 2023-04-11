package com.Dream11.services.repo;

import com.Dream11.services.models.MatchUserStats;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MatchUserStatsRepo extends MongoRepository <MatchUserStats, String>{
    List<MatchUserStats> findByMatchId(String matchId);
    MatchUserStats findByUserIdAndMatchId(String userId, String matchId);
}
