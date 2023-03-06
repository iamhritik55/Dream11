package com.Dream11.repo;

import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.utility.CombinedId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchPlayerStatsRepo extends MongoRepository<MatchPlayerStats, CombinedId> {

}
