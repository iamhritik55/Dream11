package com.Dream11.repository;

import com.Dream11.entity.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, Integer> {
}
