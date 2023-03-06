package com.Dream11.entity;
import com.Dream11.utility.CombinedMatchPlayerId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "MatchPlayerStats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchPlayerStats {
    @Id
    private CombinedMatchPlayerId id;
    private int battingRuns;
    private int bowlingWickets;
    private int foursScored;
    private int sixesScored;
    private int playerPoints;
}
