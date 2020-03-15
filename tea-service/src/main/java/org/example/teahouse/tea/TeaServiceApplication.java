package org.example.teahouse.tea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TeaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeaServiceApplication.class, args);
    }
}
