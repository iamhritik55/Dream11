package com.Dream11.services.repo;

import com.Dream11.services.models.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepo extends MongoRepository<Team, String> {
}
