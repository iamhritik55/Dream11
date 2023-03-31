package com.Dream11.DTO.response;

import com.Dream11.services.enums.PlayerTitle;
import lombok.Data;

@Data

public class PlayerResponseDTO {
    private String id;
    private String name;
    private int battingRating;
    private int bowlingRating;
    private PlayerTitle title;
    private int creditCost;
}
