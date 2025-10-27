package com.game.gamezxne.protocol;

import java.time.Instant;

import lombok.Data;

@Data
public class ApiError {

    private Instant timestamp;
    private int code;
}
