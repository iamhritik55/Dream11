package com.Dream11.services.repo;

import com.Dream11.services.models.Player;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PlayerRepo extends ElasticsearchRepository<Player, String> {
    @Override
    List<Player> findAllById(Iterable<String> strings);

    @Override
    List<Player> findAll();
}
