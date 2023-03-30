package com.Dream11.services.repo;

import com.Dream11.services.models.MatchStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchStatsRepo extends MongoRepository <MatchStats, String>{
}
