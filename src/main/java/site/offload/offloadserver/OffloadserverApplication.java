package site.offload.offloadserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OffloadserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(OffloadserverApplication.class, args);
    }

}
