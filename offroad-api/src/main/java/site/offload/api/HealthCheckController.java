package site.offload.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HealthCheck API", description = "HealthCheck 관련 API")
@RestController
public class HealthCheckController implements HealthCheckControllerSwagger {

    @GetMapping("/api/health")
    public String healthCheck() {
        return "OK";
    }
}
