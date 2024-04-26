package com.image.controller.health;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "check")
public class HealthController {
    @ReadOperation
    public String checkHealth() {
        return "Everything seems good!";
    }
}
