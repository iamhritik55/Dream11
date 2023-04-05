package com.Dream11.services.models;

import com.Dream11.services.enums.MatchStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "match")
@Data
public class Match {

    @Id
    private String matchId;// make it Id
    private String team1Id;
    private String team2Id;
    private MatchStatus status; // TODO: 16/03/23  make it enum-done
//    private long team1Score;
//    private long team2Score;
}

