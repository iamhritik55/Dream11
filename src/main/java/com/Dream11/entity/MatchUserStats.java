package com.Dream11.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "MatchUserStats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchUserStats {
    private int matchId;
    private int userId;
    private List<Integer> chosenPlayerIdList;
    private List<String> playerName;
    private int creditChange;
    private int teamPoints;

    public MatchUserStats(int matchId, int userId, int creditChange, int teamPoints,List<Integer> chosenPlayerIdList, List<String> playerName) {
        this.matchId = matchId;
        this.userId = userId;
        this.creditChange = creditChange;
        this.teamPoints = teamPoints;
        this.playerName = playerName;
        this.chosenPlayerIdList = chosenPlayerIdList;
    }
}
