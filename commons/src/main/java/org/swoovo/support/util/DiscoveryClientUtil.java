package org.swoovo.support.util;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.swoovo.support.util.helpers.AppServicesName;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public final class DiscoveryClientUtil {
    private final DiscoveryClient discoveryClient;

    private final WebRequestUtil webRequestUtil;

    public <T> T callGetMethod(AppServicesName serviceName, String urlPath, Class<T> responseType) {
        return callService(url -> webRequestUtil.get(url, responseType),
                serviceName, urlPath);
    }

    public <T, R> T callPostMethod(AppServicesName serviceName, String urlPath,
                                   R request, Class<T> responseType, MediaType requestMediaType) {
        return callService(url -> webRequestUtil.post(url, request,
                responseType, requestMediaType), serviceName, urlPath);
    }

    public <T> void callDeleteMethod(AppServicesName serviceName, String urlPath, Class<T> responseType) {
        callService(webRequestUtil::delete, serviceName, urlPath);
    }

    public <T> T callService(Function<String, T> requestSender, AppServicesName serviceName, String urlPath) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName.getServiceName());

        if (!instances.isEmpty())  {
            String url = instances.get(0).getUri() + urlPath;

            return requestSender.apply(url);
        }

        return null;
    }
}
