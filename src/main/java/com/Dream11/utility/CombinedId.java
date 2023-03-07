package com.Dream11.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombinedId implements Serializable {
    private int matchId;
    private int playerId;
}
