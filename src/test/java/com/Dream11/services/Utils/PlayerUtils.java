package com.Dream11.services.Utils;

import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;

public class PlayerUtils {
    public static Player createPLayer(String id, String name, int battingRating, int bowlingRating, PlayerTitle title, int creditCost){
        Player player = new Player();
        player.setId(id);
        player.setName(name);
        player.setTitle(title);
        player.setBattingRating(battingRating);
        player.setBowlingRating(bowlingRating);
        player.setCreditCost(creditCost);
        return player;
    }
}
