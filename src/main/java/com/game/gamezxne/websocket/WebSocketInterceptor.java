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
       String authHeader = request.getHeaders().getFirst("Authorization");

       System.out.println(authHeader);


       if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            
            // Validate the token (add your JWT validation logic here)
            boolean isValidToken = validateJwtToken(jwtToken);
            if (isValidToken) {
                // Proceed with WebSocket connection
                return true;
            }
        }
        
        // Reject the connection if the token is invalid or absent
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
