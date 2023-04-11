package com.Dream11.services.repo;

import com.Dream11.services.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
}
