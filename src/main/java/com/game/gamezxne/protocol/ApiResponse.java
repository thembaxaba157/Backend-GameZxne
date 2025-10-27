package com.game.gamezxne.protocol;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String message;



    public ApiResponse(boolean success, T data, String message){
        this.success = success;
        this.data = data;
        this.message = message;
    }
    
    public ApiResponse(boolean success, T data){
        this.success = success;
        this.data = data;
        this.message = "Success";
    }

    

    

    
}
