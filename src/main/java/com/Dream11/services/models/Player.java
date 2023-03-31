package com.Dream11.services.models;

import com.Dream11.services.enums.PlayerStatus;
import com.Dream11.services.enums.PlayerTitle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Player")
@Data
public class Player {
    @Id
    private String id;
    private String name;
    private int battingRating;
    private int bowlingRating;
    private PlayerTitle title;//strong , avg , allrounder
    private String playerType;//batsman or bowler or allrounder
    private int creditCost;

    @Transient
    private int battingRuns = 0;

    public void addRuns(int runs) {
        this.battingRuns += runs;
    }

    @Transient
    private int bowlingWickets = 0;

    public void addWicket() {
        bowlingWickets++;
    }

    @Transient
    private int foursScored = 0;

    public void addFour() {
        foursScored++;
    }

    @Transient
    private int sixesScored = 0;

    public void addSix() {
        sixesScored++;
    }

    @Transient
    private int playerPoints;

    public void addPoints(int playerPoints) {
        this.playerPoints += playerPoints;
    }

    @Transient
    private PlayerStatus playerStatus;

}
