package com.Dream11.DTO;

import com.Dream11.enums.PlayerTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponseDTO {
    private String id;
    private String name;
    private int battingRating,bowlingRating;
    private PlayerTitle title;
    private int creditCost;
}
