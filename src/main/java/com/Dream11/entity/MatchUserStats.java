package com.Dream11.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "MatchUserStats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchUserStats {
    private int matchId;
    private int userId;
    private ArrayList<Integer> chosenPlayerIdList;
    private int creditChange;
    private int teamPoints;
}
