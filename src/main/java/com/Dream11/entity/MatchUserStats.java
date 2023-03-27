package com.Dream11.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MatchUserStats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchUserStats {
    @Id
    private String id;
    private String match_userId;
    @NonNull
    private String matchId;//
    @NonNull
    private String userId;// TODO: 17/03/23 make these nonNull
    private String userName;
    @NonNull
    private List<String> chosenPlayerIdList;
    private List<String> playerNames;// TODO: 17/03/23  add this to response DTO
    private int creditChange;
    private int teamPoints;
    private int creditsSpentByUser;
}
