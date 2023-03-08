package com.Dream11.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchPlayerStatsResp {
    private String team1Name;
    private String team2Name;
    List<MatchPlayerStatsAttributes> team1Stats;
    List<MatchPlayerStatsAttributes> team2Stats;
}
