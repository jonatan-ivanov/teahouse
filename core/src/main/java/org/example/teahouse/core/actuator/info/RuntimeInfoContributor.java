package org.example.teahouse.core.actuator.info;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import static java.time.Duration.between;

@Slf4j
@RequiredArgsConstructor
public class RuntimeInfoContributor implements InfoContributor {
    private final Environment environment;
    private final Instant startTime = Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().getStartTime());

    @Override
    public void contribute(Builder builder) {
        builder.withDetail("spring", ImmutableMap.builder()
            .put("framework", ImmutableMap.builder().put("version", SpringVersion.getVersion()).build())
            .put("boot", ImmutableMap.builder().put("version", SpringBootVersion.getVersion()).build())
            .build()
        );

        builder.withDetail("environment",
            ImmutableMap.of("activeProfiles", environment.getActiveProfiles())
        );

        builder.withDetail("runtime", ImmutableMap.builder()
            .put("memory", memoryInfo())
            .put("gcs", gcInfo())
            .put("user", userInfo())
            .put("network", networkInfo())
            .put("startTime", startTime)
            .put("uptime", between(startTime, Instant.now()))
            .put("heartbeat", Instant.now())
            .build()
        );
    }

    private Map<String, Object> memoryInfo() {
        return ImmutableMap.<String, Object>builder()
            .put("total", Runtime.getRuntime().totalMemory())
            .put("max", Runtime.getRuntime().maxMemory())
            .put("free", Runtime.getRuntime().freeMemory())
            .build();
    }

    private List<Map<String, Object>> gcInfo() {
        return ManagementFactory.getGarbageCollectorMXBeans().stream()
            .map(this::toInfoObject).toList();
    }

    private Map<String, Object> toInfoObject(GarbageCollectorMXBean gcMXBean) {
        return ImmutableMap.<String, Object>builder()
            .put("name", gcMXBean.getName())
            .put("objectName", String.valueOf(gcMXBean.getObjectName()))
            .put("collectionCount", gcMXBean.getCollectionCount())
            .put("collectionTime", gcMXBean.getCollectionTime())
            .put("memoryPoolNames", gcMXBean.getMemoryPoolNames())
            .build();
    }

    private Map<String, Object> userInfo() {
        return ImmutableMap.<String, Object>builder()
            .put("timezone", System.getProperty("user.timezone"))
            .put("country", System.getProperty("user.country"))
            .put("language", System.getProperty("user.language"))
            .put("dir", System.getProperty("user.dir"))
            .build();
    }

    private Map<String, Object> networkInfo() {
        return ImmutableMap.<String, Object>builder()
            .put("host", hostName())
            .put("ip", ipAddress())
            .build();
    }

    private String hostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (Exception exception) {
            log.error("Unable to fetch hostname", exception);
            return "n/a";
        }
    }

    private String ipAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (Exception exception) {
            log.error("Unable to fetch IP", exception);
            return "n/a";
        }
    }
}
