package com.Dream11.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Team")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    private int id;
    private String name;
    private List<Integer> teamPlayerIds;
}
