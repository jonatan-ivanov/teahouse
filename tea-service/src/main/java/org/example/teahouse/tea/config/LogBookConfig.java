package org.example.teahouse.tea.config;

import okhttp3.OkHttpClient;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientFactory;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.okhttp.LogbookInterceptor;

@Configuration
public class LogBookConfig {

    @Bean
    public LogbookInterceptor logbookInterceptor(Logbook logbook) {
        return new LogbookInterceptor(logbook);
    }

    @Bean
    public OkHttpClientFactory okHttpClientFactory(OkHttpClient.Builder builder, LogbookInterceptor logbookInterceptor) {
        builder.addNetworkInterceptor(logbookInterceptor);
        return new DefaultOkHttpClientFactory(builder);
    }
}
