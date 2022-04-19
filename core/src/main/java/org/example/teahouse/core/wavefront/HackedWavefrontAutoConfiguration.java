package org.example.teahouse.core.wavefront;

import brave.TracingCustomizer;
import brave.handler.SpanHandler;
import com.wavefront.sdk.common.WavefrontSender;
import com.wavefront.sdk.common.application.ApplicationTags;
import com.wavefront.spring.autoconfigure.WavefrontProperties;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.wavefront.WavefrontConfig;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.autoconfig.brave.BraveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Tracer.class, TracingCustomizer.class, SpanHandler.class,  SpanNamer.class, MeterRegistry.class, WavefrontConfig.class, WavefrontSender.class })
@AutoConfigureBefore(BraveAutoConfiguration.class)
public class HackedWavefrontAutoConfiguration {
    @Bean
    TracingCustomizer wavefrontTracingCustomizer(
        WavefrontSender wavefrontSender,
        MeterRegistry meterRegistry,
        WavefrontConfig wavefrontConfig,
        ApplicationTags applicationTags,
        WavefrontProperties wavefrontProperties) {
        return t -> t.traceId128Bit(true).supportsJoin(false).addSpanHandler(
            new HackedWavefrontSleuthBraveSpanHandler(new HackedWavefrontSleuthSpanHandler(
                50000,
                wavefrontSender,
                meterRegistry,
                wavefrontConfig.source(),
                applicationTags,
                wavefrontProperties)
            )
        );
    }
}
