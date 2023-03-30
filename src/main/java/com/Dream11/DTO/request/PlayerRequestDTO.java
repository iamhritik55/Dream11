package com.Dream11.DTO.request;

import com.Dream11.services.enums.PlayerTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequestDTO {
    @NonNull
    private String name;
    private int battingRating;
    private int bowlingRating;
    @NonNull
    private PlayerTitle title;
    private int creditCost;
}
