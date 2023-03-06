package com.Dream11.repo;

import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.utility.CombinedMatchPlayerId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchPlayerStatsRepo extends MongoRepository<MatchPlayerStats, CombinedMatchPlayerId> {

}
