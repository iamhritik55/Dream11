package com.Dream11.utility;

import com.Dream11.entity.PlayerTitle;

import java.util.ArrayList;
import java.util.List;

public class Rating {
    private static List<Integer> fiveStarBatting=new ArrayList<>(){{
        add(4);add(4);add(4);add(6);add(6);add(6);add(2);add(2);add(1);add(7);
    }};
    private static List<Integer> fourStarBatting=new ArrayList<>(){{
        add(4);add(4);add(6);add(6);add(2);add(2);add(2);add(1);add(1);add(7);
    }};
    private static List<Integer> threeStarBatting=new ArrayList<>(){{
        add(4);add(6);add(2);add(2);add(2);add(1);add(1);add(1);add(7);add(7);
    }};

    private static List<Integer> twoStarBatting=new ArrayList<>(){{
        add(4);add(2);add(2);add(1);add(1);add(7);add(2);add(2);add(7);add(7);
    }};
    private static List<Integer> fiveStarBowler=new ArrayList<>(){{
        add(0);add(0);add(0);add(7);add(7);
    }};
    private static List<Integer> fourStarBowler=new ArrayList<>(){{
        add(0);add(0);add(0);add(7);
    }};
    private static List<Integer> threeStarBowler=new ArrayList<>(){{
        add(0);add(0);add(7);
    }};
    private static List<Integer> twoStarBowler=new ArrayList<>(){{
        add(0);add(0);
    }};

    public static List<Integer> playerBattingArray(PlayerTitle playerTitle){
        if(PlayerTitle.STRONG_BATSMAN == playerTitle)
            return fiveStarBatting;
        else if(PlayerTitle.STRONG_BOWLER == playerTitle)
            return threeStarBatting;
        else if(PlayerTitle.ALL_ROUNDER == playerTitle)
            return fourStarBatting;
        else if(PlayerTitle.AVERAGE_BATSMAN == playerTitle)
            return threeStarBatting;
        else if(PlayerTitle.AVERAGE_BOWLER == playerTitle)
            return twoStarBatting;
        else {
            System.out.println("Player title not found!");
            return null;
        }
    }

    public static List<Integer> playerBowlingArray(PlayerTitle playerTitle){
        if(PlayerTitle.STRONG_BATSMAN == playerTitle)
            return threeStarBowler;
        else if(PlayerTitle.STRONG_BOWLER == playerTitle)
            return fiveStarBowler;
        else if(PlayerTitle.ALL_ROUNDER == playerTitle)
            return fourStarBowler;
        else if(PlayerTitle.AVERAGE_BATSMAN == playerTitle)
            return twoStarBowler;
        else if(PlayerTitle.AVERAGE_BOWLER == playerTitle)
            return threeStarBowler;
        else {
            System.out.println("Player title not found!");
            return null;
        }
    }
}
