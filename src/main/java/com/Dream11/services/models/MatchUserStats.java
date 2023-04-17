package com.Dream11.services.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "MatchUserStats")
@Data
@CompoundIndex(def = "{'userId': 1, 'matchId': 1}",unique = true)
public class MatchUserStats {
    @Id
    private String id;
    private String matchId;
    private String userId;
    private List<String> chosenPlayerIdList;
    private int creditChange;
    private int teamPoints;
    private int creditsSpentByUser;
}
