package com.Dream11.services.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MatchUserStats")
@Data
@CompoundIndex(def = "{'userId': 1, 'matchId': 1}",unique = true)
public class MatchUserStats {
    @Id
    private String id;
    private String matchId;//
    private String userId;// TODO: 17/03/23 make these nonNull
    private List<String> chosenPlayerIdList;
    private int creditChange;
    private int teamPoints;
    private int creditsSpentByUser;
}
