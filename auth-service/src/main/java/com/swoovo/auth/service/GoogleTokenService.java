package com.swoovo.auth.service;

import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken.Payload;
import com.google.auth.oauth2.TokenVerifier;
import com.swoovo.auth.dto.GoogleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleTokenService {
    private final TokenVerifier verifier;

    public GoogleUser verify(String token) {
        try {
            JsonWebSignature signature = verifier.verify(token);

            Payload payload = signature.getPayload();

            return new GoogleUser((String) payload.get("sub"),
                                  (String) payload.get("email"),
                                  (String) payload.get("name"),
                                  (String) payload.get("picture"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
