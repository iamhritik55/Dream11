package com.Dream11.services.repo;

import com.Dream11.services.models.Match;
import com.Dream11.services.enums.MatchStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MatchRepo extends MongoRepository<Match,String> {
    @Query("{status: ?0}")
    List<Match> findMatchesByStatus(MatchStatus status);
}
