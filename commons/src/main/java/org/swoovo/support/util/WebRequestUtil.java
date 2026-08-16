package org.swoovo.support.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public final class WebRequestUtil {
    private final WebClient webClient;

    public <T> T get(String url, Class<T> responseType) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T, R> T post(String url, R request, Class<T> responseType, MediaType requestMediaType) {
        return webClient.post()
                .uri(url)
                .contentType(requestMediaType)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public Void delete(String url) {
        return webClient.delete()
                .uri(url)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}

