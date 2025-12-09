package com.game.gamezxne.protocol;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //exclude values that null on the response
public class ApiError {

    private Instant timestamp;
    private int code;
    private String type;
    private String path;
    private Map<String, String> details;

    public ApiError(Instant timestamp, int code, String type, String path){

        this.timestamp = timestamp;
        this.code = code;
        this.type = type;
        this.path = path;
    }

    public ApiError(Instant timestamp, int code, String type, String path, Map<String, String> details){
        this.timestamp = timestamp;
        this.code = code;
        this.type = type;
        this.path = path;
        this.details = details;
    }
}
