package com.Dream11.controller;

import com.Dream11.entity.MatchStats;
import com.Dream11.entity.PlayerTitle;
import com.Dream11.services.MatchStatsService;
import com.Dream11.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class TestAPI {
    @Autowired
    MatchStatsService matchStatsService;


}
