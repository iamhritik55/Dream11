package com.Dream11.repo;

import com.Dream11.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepo extends MongoRepository<Player,Integer> {

}
