package com.Dream11.utility;

import com.Dream11.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispTeamDetResp {
    private int team1Id;
    private int team2Id;
    private String team1Name;
    private String team2Name;
    private List<Player> team1Players;
    private List<Player> team2Players;
}
