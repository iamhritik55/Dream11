package com.Dream11.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Player")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    private int id;
    private String name;
    private int battingRating,bowlingRating;
    private PlayerTitle title;
    private int creditCost;
}
