package sites.offload.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "sites.offload")
@Configuration
public class OpenFeignConfig {
}
