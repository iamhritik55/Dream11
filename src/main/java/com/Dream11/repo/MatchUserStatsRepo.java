package com.Dream11.repo;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.utility.CombinedMatchUserId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchUserStatsRepo extends MongoRepository <MatchUserStats, String>{
    List<MatchUserStats> findByMatchId(String matchId);
    Optional<MatchUserStats> findById(String matchUserId);
}
