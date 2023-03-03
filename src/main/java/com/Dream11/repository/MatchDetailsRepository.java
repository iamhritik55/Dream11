package com.Dream11.repository;

import com.Dream11.entity.MatchDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchDetailsRepository extends MongoRepository<MatchDetails, Integer> {
}
