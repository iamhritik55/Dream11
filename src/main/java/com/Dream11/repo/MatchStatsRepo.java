package com.Dream11.repo;

import com.Dream11.entity.MatchStats;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchStatsRepo extends MongoRepository <MatchStats, String>{
}
