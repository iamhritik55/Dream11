package com.Dream11.entity;
import com.Dream11.utility.CombinedMatchUserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "MatchUserStats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchUserStats {
    @Id
    private String match_userid;
    private String matchId;
    private String userId;
    private List<String> chosenPlayerIdList;
    private int creditChange;
    private int teamPoints;
    private int creditsSpentByUser;
}