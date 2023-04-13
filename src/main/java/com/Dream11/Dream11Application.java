package com.Dream11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Dream11Application {
    public static void main(String[] args) {
        try {
            SpringApplication.run(Dream11Application.class, args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}