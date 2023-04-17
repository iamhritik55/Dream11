package com.Dream11.DTO.response;

import lombok.Data;

import java.util.List;

@Data
public class TeamResponseDTO {
    private String id;
    private String name;
    private List<String> teamPlayerIds;
}
