package com.swoovo.messaging.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public final class KeyBundleRequest {
    @NotEmpty
    public String userId;

    @PositiveOrZero
    public int deviceId = 0;

    @NotEmpty
    public List<String> preKeysBase64;
}
