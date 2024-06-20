package site.offload.offloadserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthCheckController {

    @GetMapping("/api/health")
    public String healthCheck() {
        return "OK";
    }
}
