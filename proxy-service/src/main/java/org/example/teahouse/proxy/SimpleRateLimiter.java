package org.example.teahouse.proxy;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.cloud.gateway.route.RouteDefinitionRouteLocator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.stereotype.Component;

@Component
public class SimpleRateLimiter extends AbstractRateLimiter<SimpleRateLimiter.Config> {

    private static final Logger log = LoggerFactory.getLogger(SimpleRateLimiter.class);

    private final AtomicLong acceptedRequests = new AtomicLong();

    private final AtomicLong firstRequestTimeStamp = new AtomicLong();

    public static final String CONFIGURATION_PROPERTY_NAME = "simple-rate-limiter";

    private final Config defaultConfig = new Config();

    private final AtomicBoolean enabled = new AtomicBoolean(false);

    private final ObservationRegistry observationRegistry;

    private final QuotaObservationConvention quotaObservationConvention;

    @Autowired
    public SimpleRateLimiter(ObservationRegistry observationRegistry) {
        super(SimpleRateLimiter.Config.class, CONFIGURATION_PROPERTY_NAME, null);
        this.observationRegistry = observationRegistry;
        this.quotaObservationConvention = DefaultQuotaObservationConvention.INSTANCE;
    }

    public SimpleRateLimiter(ObservationRegistry observationRegistry, QuotaObservationConvention quotaObservationConvention) {
        super(SimpleRateLimiter.Config.class, CONFIGURATION_PROPERTY_NAME, null);
        this.observationRegistry = observationRegistry;
        this.quotaObservationConvention = quotaObservationConvention;
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
       final QuotaObservationContext context = new QuotaObservationContext();
        Observation observation = Observation.start(quotaObservationConvention, DefaultQuotaObservationConvention.INSTANCE, () -> context, observationRegistry);
        if (!enabled.get()) {
            return Mono.just(observation).map(obs -> {
                context.setRequestAllowed(true);
                obs.stop();
                return new Response(true, new HashMap<>());
            });
        }
        return Mono.just(observation).map(obs -> {
            // set timestamp of first request
            firstRequestTimeStamp.compareAndSet(0, System.currentTimeMillis());
            long firstTimestamp = firstRequestTimeStamp.get();
            long fromMilis = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - firstTimestamp);
            long seconds = fromMilis == 0 ? 1 : fromMilis;
            context.setTotalSeconds(seconds);
            long noOfRequests = acceptedRequests.incrementAndGet();
            context.setTotalRequests(noOfRequests);
            double rate = (double) noOfRequests / seconds;
            context.setCurrentRatePerSeconds(rate);

            Config config = loadConfiguration(routeId);
            double maxReqPerSecond = config.getMaxReqPerSecond();
            context.setAllowedRatePerSeconds(maxReqPerSecond);

            boolean allowed = maxReqPerSecond >= rate;
            if (!allowed) {
                acceptedRequests.decrementAndGet();
            }
            context.setRequestAllowed(allowed);

            obs.scoped(() -> {
                log.debug("Rate limiter parameters: total seconds [{}], noOfRequests [{}], rate [{}], maxPerSecond [{}], allowed [{}]", seconds, noOfRequests, rate, maxReqPerSecond, allowed);
                if (!allowed) {
                    log.debug("WARNING! Request quota reached!");
                }
            });

            observation.stop();

            return new Response(allowed, new HashMap<>());
        });
    }

    private SimpleRateLimiter.Config loadConfiguration(String routeId) {
        SimpleRateLimiter.Config routeConfig = getConfig().getOrDefault(routeId, defaultConfig);

        if (routeConfig == null) {
            routeConfig = getConfig().get(RouteDefinitionRouteLocator.DEFAULT_FILTERS);
        }

        if (routeConfig == null) {
            throw new IllegalArgumentException("No Configuration found for route " + routeId + " or defaultFilters");
        }
        return routeConfig;
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public static class Config {

        private boolean enabled = false;

        private double maxRatePerSecond = 2;

        public boolean isEnabled() {
            return enabled;
        }

        public Config setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Config setMaxReqPerSecond(int maxReqPerSecond) {
            this.maxRatePerSecond = maxReqPerSecond;
            return this;
        }

        public double getMaxReqPerSecond() {
            return maxRatePerSecond;
        }

        @Override
        public String toString() {
            return new ToStringCreator(this).append("enabled", enabled).append("maxReqPerSecond", maxRatePerSecond).toString();
        }
    }

}
