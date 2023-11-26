package org.example.teahouse.tea.config;

import okhttp3.OkHttpClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.okhttp.LogbookInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogBookConfig {
    @Bean
    public OkHttpClient.Builder okHttpClientBuilder(Logbook logbook) {
        return new OkHttpClient.Builder()
            .addNetworkInterceptor(new LogbookInterceptor(logbook));
    }
}
