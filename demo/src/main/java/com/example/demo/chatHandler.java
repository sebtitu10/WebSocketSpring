package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class chatHandler extends TextWebSocketHandler {
    //administra las conexiones a nuestro WebSocket

    //Que va a suceder cuendo alguien se conecte a nuestro webSocket
    private static final Logger LOGGER = LoggerFactory.getLogger(chatHandler.class);
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();//Arraylist no se debe usar para grandes concurrencia

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // el mensaje se envia a todas las sesiones activas
        for(WebSocketSession webSocketSession: sessions){
            webSocketSession.sendMessage(message);

        }
    }
}
