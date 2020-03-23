package org.example.teahouse.tea;

import org.example.teahouse.core.actuator.config.ActuatorConfig;
import org.example.teahouse.core.log.access.AccessLogConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableFeignClients
@EnableSwagger2WebMvc
@SpringBootApplication
@Import({ActuatorConfig.class, AccessLogConfig.class, SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class TeaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeaServiceApplication.class, args);
    }
}
