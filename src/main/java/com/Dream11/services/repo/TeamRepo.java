package com.Dream11.services.repo;

import com.Dream11.services.models.Team;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TeamRepo extends ElasticsearchRepository<Team, String> {
    @Override
    List<Team> findAll();

    @Override
    List<Team> findAllById(Iterable<String> strings);
}
