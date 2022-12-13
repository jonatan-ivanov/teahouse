package org.example.teahouse.tea.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LogBookConfig {

    // TODO: After Logbook releases support for Boot 3
//    @Bean
//    public LogbookInterceptor logbookInterceptor(Logbook logbook) {
//        return new LogbookInterceptor(logbook);
//    }
//
//    @Bean
//    public OkHttpClientFactory okHttpClientFactory(OkHttpClient.Builder builder, LogbookInterceptor logbookInterceptor) {
//        builder.addNetworkInterceptor(logbookInterceptor);
//        return new DefaultOkHttpClientFactory(builder);
//    }
}
