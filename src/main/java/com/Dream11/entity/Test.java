package com.Dream11.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    private String testId;
}
