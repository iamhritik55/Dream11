package com.Dream11.services.enums;

import com.Dream11.services.models.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public enum PlayerTitle {
    STRONG_BATSMAN(1), STRONG_BOWLER(5), ALL_ROUNDER(3), AVERAGE_BATSMAN(2), AVERAGE_BOWLER(4);

    private final int preference;

    PlayerTitle(int preference) {
        this.preference = preference;
    }


}
