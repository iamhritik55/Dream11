package com.Dream11.services.repo;

import com.Dream11.services.models.MatchStats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MatchStatsRepo extends ElasticsearchRepository<MatchStats, String> {
}
