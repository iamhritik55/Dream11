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
    private PlayerTitle title;
    private int creditCost;
    @Transient
    private PlayerStatus playerStatus;

}
