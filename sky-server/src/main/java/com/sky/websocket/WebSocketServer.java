package com.sky.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket Server
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    //Store session object
    private static Map<String, Session> sessionMap = new HashMap();

    /**
     * Method called when connection is established successfully
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("Client：" + sid + "establish connection");
        sessionMap.put(sid, session);
    }

    /**
     * Method called after receiving client message
     *
     * @param message Message sent by client
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("Received from client：" + sid + "info:" + message);
    }

    /**
     * Method called on connection close
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("Disconnect: " + sid);
        sessionMap.remove(sid);
    }

    /**
     * Bulk sending
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                //Server sends message to client
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
