package site.offload.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "site.offload.api",
        "site.offload.common",
        "site.offload.external",
        "site.offload.enums",
        "site.offload.cache",
        "site.offload.db"
}, excludeFilters = {@ComponentScan.Filter(
        type = FilterType.CUSTOM,
        classes = {TypeExcludeFilter.class}
)})
@EnableJpaRepositories(basePackages = "site.offload.db")
@EntityScan(basePackages = {"site.offload.db"})
public class OffloadserverApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OffloadserverApiApplication.class, args);
    }

}
