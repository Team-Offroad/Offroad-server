package site.offload.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController implements HealthCheckControllerSwagger {

    @GetMapping("/api/health")
    public String healthCheck() {
        return "OK";
    }
}
