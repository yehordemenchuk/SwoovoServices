package com.swoovo.notify.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.service.account-file}")
    private String firebaseConfigFilePath;

    @PostConstruct
    public void init() throws IOException {
        InputStream serviceAccount = getClass()
                .getClassLoader()
                .getResourceAsStream(firebaseConfigFilePath);

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(getOptions(serviceAccount));
        }
    }

    private FirebaseOptions getOptions(InputStream serviceAccount) throws IOException {
        return FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
    }
}
