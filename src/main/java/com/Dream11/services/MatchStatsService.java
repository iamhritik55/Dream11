package com.Dream11.services;

import com.Dream11.entity.MatchStats;
import com.Dream11.repo.MatchStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchStatsService {
    @Autowired
    public MatchStatsRepo matchStatsRepo;

    public MatchStats findMatchStatsById(String id) throws Exception{
        if(matchStatsRepo.findById(id).isPresent()){
            return matchStatsRepo.findById(id).get();
        }
        else {
            throw new Exception("MatchStats id not found!");
        }
    }
}
