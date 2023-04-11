package com.Dream11.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDTO {
    @NonNull
    private String name;
    @NonNull
    private List<String> teamPlayerIds;
}
