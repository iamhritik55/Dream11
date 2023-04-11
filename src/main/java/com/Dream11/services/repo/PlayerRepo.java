package com.Dream11.services.repo;

import com.Dream11.services.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepo extends MongoRepository<Player, String> {

}
