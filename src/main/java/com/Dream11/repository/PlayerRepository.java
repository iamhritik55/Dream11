package com.Dream11.repository;

import com.Dream11.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, Integer> {
}
