package com.Dream11.controller;

import com.Dream11.entity.Test;
import com.Dream11.repo.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestAPI {
    @Autowired
    public TestRepo testRepo;

    @GetMapping
    public List<Test> getTests() {
        return testRepo.findAll();
    }

    @PostMapping
    public Test addTest(@RequestBody Test test) {
        return testRepo.save(test);
    }
}
