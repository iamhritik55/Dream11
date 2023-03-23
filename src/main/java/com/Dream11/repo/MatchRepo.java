package com.Dream11.repo;

import com.Dream11.entity.Match;
import com.Dream11.entity.MatchStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MatchRepo extends MongoRepository<Match,String> {
    @Query("{completed: ?0}")
    List<Match> findMatchesByStatus(MatchStatus completed);
}
