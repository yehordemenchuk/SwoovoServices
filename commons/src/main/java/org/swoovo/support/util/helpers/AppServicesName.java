package org.swoovo.support.util.helpers;

import lombok.Getter;

@Getter
public enum AppServicesName {
    USER_SERVICE("user-service"),
    COMPLAINT_SERVICE("complaint-service");

    private final String serviceName;

    AppServicesName(String serviceName) {
        this.serviceName = serviceName;
    }
}
