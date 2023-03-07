package com.Dream11.repo;

import com.Dream11.entity.MatchDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepo extends MongoRepository<MatchDetails,Integer> {

}
