package site.offload.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"site.offload.api", "sites.offload.common",
        "sites.offload.external", "sites.offload.enums"})
@EnableJpaRepositories(basePackages = "sites.offload.db")
@EntityScan(basePackages = {"sites.offload.db"})
public class OffloadserverApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OffloadserverApiApplication.class, args);
    }

}
