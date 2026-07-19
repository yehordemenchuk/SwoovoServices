package com.swoovo.notify.service.push;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {
    public void send(String token, String title, String body)
            throws FirebaseMessagingException {
        FirebaseMessaging.getInstance()
                .send(createMessage(token, title, body));
    }

    private Message createMessage(String token, String title, String body) {
        return Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build()
                ).build();
    }
}
