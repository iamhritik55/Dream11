package com.Dream11.services.models;

import com.Dream11.services.enums.MatchStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Match")
@Data
public class Match {

    @Id
//    @Field("_id")
    private String matchId;// make it Id
    private String team1Id;
    private String team2Id;
    @Indexed
    private MatchStatus status; // TODO: 16/03/23  make it enum-done
//    private long team1Score;
//    private long team2Score;
}

