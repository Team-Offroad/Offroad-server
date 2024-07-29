package site.offload.external.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "site.offload")
@Configuration
public class OpenFeignConfig {
}
