package com.game.gamezxne.websocket;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        // Get token from query parameters
        String token = request.getURI().getQuery();
        System.out.println(token);
    
        if (token != null && token.startsWith("token=")) {
            token = token.substring(6); // Remove 'token=' prefix
            boolean isValidToken = validateJwtToken(token);
            if (isValidToken) {
                return true;
            }
        }
    
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }
   

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            @Nullable Exception exception) {
        // TODO Auto-generated method stub
      
    }


    private boolean validateJwtToken(String jwtToken) {
         // Add your JWT validation logic (check signature, expiry, etc.)
        // Return true if valid, false otherwise
        return true;  // Example: Assume all tokens are valid for now
    }
    
}
