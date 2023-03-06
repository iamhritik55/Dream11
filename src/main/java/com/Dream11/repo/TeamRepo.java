package com.Dream11.repo;

import com.Dream11.entity.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepo extends MongoRepository<Team,Integer> {

}
