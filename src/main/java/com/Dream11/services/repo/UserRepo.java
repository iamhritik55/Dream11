package com.Dream11.services.repo;

import com.Dream11.services.models.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepo extends ElasticsearchRepository<User, String> {
    @Override
    List<User> findAll();

    @Override
    List<User> findAllById(Iterable<String> strings);
}
