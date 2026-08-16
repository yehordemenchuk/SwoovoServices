package com.swoovo.messaging.service;

import com.swoovo.messaging.dto.request.KeyBundleRequest;
import com.swoovo.messaging.dto.response.PreKeyResponse;
import com.swoovo.messaging.entity.PreKey;
import com.swoovo.messaging.mapper.PreKeyMapper;
import com.swoovo.messaging.repository.PreKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyException;

@Service
@RequiredArgsConstructor
public class KeyService {
    private final PreKeyRepository preKeyRepository;
    private final PreKeyMapper preKeyMapper;

    public void registerPreKeys(KeyBundleRequest keyBundleRequest) {
        preKeyRepository.save(preKeyMapper.fromBundle(keyBundleRequest));
    }

    public PreKeyResponse consumePreKeys(String userId, int deviceId) {
        try {
            PreKey preKey = preKeyRepository.consumePreKey(userId, deviceId)
                    .orElseThrow(KeyException::new);

            return preKeyMapper.toPreKeyResponse(preKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
