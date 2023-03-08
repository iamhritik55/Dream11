package com.Dream11.entity;
import com.Dream11.utility.CombinedMatchUserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "MatchUserStats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchUserStats {
    @Id
    CombinedMatchUserId id;
    private List<Integer> chosenPlayerIdList;
    private List<String> playerName;
    private int creditChange;
    private int teamPoints;

}
