package com.Dream11.utility;

import com.Dream11.services.models.Player;
import com.Dream11.services.enums.PlayerTitle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.Dream11.services.enums.PlayerTitle.STRONG_BOWLER;

@Component
public class PlayingOrder {
    private List<Player> playerOrder;

    public List<Player> battingOrder(List<Player> playerList) {

        playerList.sort((player1, player2)->Integer.compare(player1.getTitle().getPreference(),player2.getTitle().getPreference()));

//        playerOrder = new ArrayList<>();
//        addToList(PlayerTitle.STRONG_BATSMAN.getPreference(), playerList);
//        addToList(PlayerTitle.ALL_ROUNDER, playerList);
//        addToList(PlayerTitle.AVERAGE_BATSMAN, playerList);
//        addToList(STRONG_BOWLER, playerList);
//        addToList(PlayerTitle.AVERAGE_BOWLER, playerList);
        return playerOrder;
    }

    public List<Player> bowlingOrder(List<Player> playerList) {
        playerOrder = new ArrayList<>();
        addToList(PlayerTitle.STRONG_BOWLER, playerList);
        addToList(PlayerTitle.ALL_ROUNDER, playerList);
        addToList(PlayerTitle.AVERAGE_BOWLER, playerList);
        addToList(PlayerTitle.AVERAGE_BATSMAN, playerList);
        addToList(PlayerTitle.STRONG_BATSMAN, playerList);
        return playerOrder;
    }

    private void addToList(PlayerTitle playerTitle, List<Player> playerList) {
        for (Player player : playerList) {
            if (player.getTitle() == playerTitle) {
                playerOrder.add(player);
            }
        }
    }
}
