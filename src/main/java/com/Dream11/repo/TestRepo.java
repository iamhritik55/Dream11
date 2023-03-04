package com.Dream11.repo;

import com.Dream11.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepo extends MongoRepository<Test,Integer> {

}
