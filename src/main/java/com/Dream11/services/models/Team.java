package com.Dream11.services.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Team")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    private String id;
    private String name;
    private List<String> teamPlayerIds;
    @Transient
    private int teamRuns = 0;
    public void addRuns(int runs) {
        this.teamRuns += runs;
    }
}
