package com.Dream11.utility;

import com.Dream11.services.enums.PlayerStatus;
import com.Dream11.services.models.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayingOrder {

    public List<Player> battingOrder(List<Player> playerList) throws Exception {

        playerList.sort((player1, player2) -> Integer.compare(player1.getTitle().getPreference(), player2.getTitle().getPreference()));
        playerList.forEach(player -> player.setPlayerStatus(PlayerStatus.NOT_PLAYING));

        if (playerList.size() < 2)
            throw new Exception("playerList invalid!");

        playerList.get(0).setPlayerStatus(PlayerStatus.ON_STRIKE);
        playerList.get(1).setPlayerStatus(PlayerStatus.OFF_STRIKE);
//      playerList.forEach(player -> System.out.println(player.getPlayerStatus()));
        return playerList;
    }

    public List<Player> bowlingOrder(List<Player> playerList) throws Exception {
        playerList.sort((player1, player2) -> Integer.compare(player2.getTitle().getPreference(),
                player1.getTitle().getPreference()));
        playerList.forEach(player -> player.setPlayerStatus(PlayerStatus.NOT_PLAYING));

        if (playerList.isEmpty())
            throw new Exception("playerList invalid!");
        playerList.get(0).setPlayerStatus(PlayerStatus.BOWLING);
        return playerList;
    }
}
