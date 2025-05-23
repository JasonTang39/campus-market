package com.example.campusmarketserver.websocket;

import com.example.campusmarketserver.model.dto.MessageRequest;
import com.example.campusmarketserver.service.MessageService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class MessageWebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final MessageService messageService;
    public MessageWebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    private String getUserId(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("userId=")) {
            return query.substring("userId=".length());
        }
        return null;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserId(session);
        if (userId != null) {
            sessions.put(userId, session);
            System.out.println("用户 " + userId + " 已连接");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        MessageRequest messageRequest = MessageRequest.fromJson(payload);

        // 保存消息到数据库
        messageService.saveMessage(messageRequest);

        // 推送给接收方（如果在线）
        WebSocketSession receiverSession = sessions.get(messageRequest.getReceiverId());
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(payload));
        }

        // 回显给发送方
        session.sendMessage(new TextMessage(payload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserId(session);
        if (userId != null) {
            sessions.remove(userId);
            System.out.println("用户 " + userId + " 已断开连接");
        }
    }
}
