package com.Dream11.utility;

import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<Player> playerList = new ArrayList<>();
        Player player1 = new Player();
        player1.setTitle(com.Dream11.services.enums.PlayerTitle.STRONG_BOWLER);
        Player player2 = new Player();
        player2.setTitle(com.Dream11.services.enums.PlayerTitle.STRONG_BATSMAN);
        playerList.add(player1);
        playerList.add(player2);
        System.out.println(player1.getTitle().getPreference());
        System.out.println(player2.getTitle().getPreference());
        playerList.sort((player3, player4)->Integer.compare(player3.getTitle().getPreference(),
                player4.getTitle().getPreference()));
        System.out.println(playerList);
    }
}
