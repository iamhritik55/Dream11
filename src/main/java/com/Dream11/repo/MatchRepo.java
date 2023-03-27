package com.Dream11.repo;

import com.Dream11.entity.Match;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MatchRepo extends MongoRepository<Match, String> {
    @Query("{completed: ?0}")
    List<Match> findMatchesByStatus(boolean completed);
}
