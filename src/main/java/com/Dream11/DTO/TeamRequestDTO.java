package com.Dream11.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDTO {
    private String name;
    private List<String> teamPlayerIds;
}
