package com.Dream11.DTO;

import com.Dream11.entity.PlayerTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequestDTO {
    private String name;
    private int battingRating,bowlingRating;
    private PlayerTitle title;
    private int creditCost;
}
