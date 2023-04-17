package com.Dream11.services.enums;

import lombok.Getter;

@Getter
public enum PlayerTitle {
    STRONG_BATSMAN(1),
    AVERAGE_BATSMAN(2),
    ALL_ROUNDER(3),
    AVERAGE_BOWLER(4),
    STRONG_BOWLER(5);



    private final int preference;

    PlayerTitle(int preference) {
        this.preference = preference;
    }


}
