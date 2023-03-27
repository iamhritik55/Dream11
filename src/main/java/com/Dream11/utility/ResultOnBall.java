package com.Dream11.utility;

import com.Dream11.helperClasses.PlayerTitle;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResultOnBall {
    static SecureRandom secureRandom = new SecureRandom();

    public static int resultOnBall(PlayerTitle batsmanTitle, PlayerTitle bowlerTitle) {
        List<Integer> batsmanArray = Rating.playerBattingArray(batsmanTitle);
        List<Integer> bowlerArray = Rating.playerBowlingArray(bowlerTitle);

        List<Integer> combinedArray = Stream.concat(batsmanArray.stream(), bowlerArray.stream()).collect(Collectors.toList());
        return combinedArray.get(secureRandom.nextInt(combinedArray.size()));
    }
}
