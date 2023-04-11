package com.Dream11.DTO.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class MatchRequestDTO {
    @NonNull
    private String team1Id;
    @NonNull
    private String team2Id;
}
