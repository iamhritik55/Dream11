package com.Dream11.utility;

import com.Dream11.services.enums.PlayerTitle;
import com.Dream11.services.models.Player;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PlayingOrderTest {

    @InjectMocks
    private PlayingOrder playingOrder;

    @Test
    public void testCase(){
        List<Player> playerList = new ArrayList<>();
        Player player1 = new Player();
        player1.setTitle(PlayerTitle.STRONG_BOWLER);
        Player player2 = new Player();
        player1.setTitle(PlayerTitle.STRONG_BATSMAN);

        playerList.add(player1);
        playerList.add(player2);

        playingOrder.battingOrder(playerList);

        playingOrder.battingOrder(playerList);



    }

}