package org.example.teahouse.core.log.access;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.apache.catalina.Context;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessLogConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addContextCustomizers(this::tomcatContextCustomizer);
    }

    private void tomcatContextCustomizer(Context context) {
        LogbackValve logbackValve = new LogbackValve();
        logbackValve.setAsyncSupported(true);
        logbackValve.setFilename("logback-access.xml");
        context.getPipeline().addValve(logbackValve);
    }
}
