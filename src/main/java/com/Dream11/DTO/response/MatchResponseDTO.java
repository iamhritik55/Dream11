package com.Dream11.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data

public class MatchResponseDTO {
    private String id;
    private String team1Id;
    private String team2Id;
}
