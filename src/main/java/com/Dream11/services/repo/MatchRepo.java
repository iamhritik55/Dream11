package com.Dream11.services.repo;

import com.Dream11.services.models.Match;
import lombok.NonNull;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MatchRepo extends ElasticsearchRepository<Match, String> {
    @Override
    List<Match> findAll();
}
