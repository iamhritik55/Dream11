package com.Dream11.repo;

import com.Dream11.entity.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchDetailsRepo extends MongoRepository<Match, String> {
}
