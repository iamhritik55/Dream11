package com.Dream11.controller;

import com.Dream11.entity.PlayerTitle;
import com.Dream11.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/test")
public class TestAPI {
    @Autowired
    TestService testService;

    @PostMapping
    public ResponseEntity<Object> test(@RequestParam("batsman")PlayerTitle batsman,
                                       @RequestParam("bowler") PlayerTitle bowler){
        return ResponseEntity.accepted().body(testService.test(batsman,bowler));
    }

}
