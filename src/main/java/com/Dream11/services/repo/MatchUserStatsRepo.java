package com.Dream11.services.repo;

import com.Dream11.services.models.MatchUserStats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface MatchUserStatsRepo extends ElasticsearchRepository<MatchUserStats, String> {
    List<MatchUserStats> findByMatchId(String matchId);
    Optional<MatchUserStats> findByUserIdAndMatchId(String userId, String matchId);

    @Override
    List<MatchUserStats> findAll();

}
