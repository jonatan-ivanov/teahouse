package org.example.teahouse.core.actuator.info;

import static java.time.Duration.between;

import com.google.common.collect.ImmutableMap;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;

@Slf4j
@RequiredArgsConstructor
public class RuntimeInfoContributor implements InfoContributor {
    private final Environment environment;
    private final Instant startTime = Instant.ofEpochMilli(ManagementFactory.getRuntimeMXBean().getStartTime());

    @Override
    public void contribute(Builder builder) {
        builder.withDetail("environment",
            ImmutableMap.of("activeProfiles", environment.getActiveProfiles())
        );

        builder.withDetail("runtime", ImmutableMap.builder()
            .put("memory", memoryInfo())
            .put("cpu", cpuInfo())
            .put("java", javaInfo())
            .put("gcs", gcInfo())
            .put("user", userInfo())
            .put("os", osInfo())
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

    private Map<String, Object> cpuInfo() {
        return ImmutableMap.<String, Object>builder()
            .put("availableProcessors", Runtime.getRuntime().availableProcessors())
            .build();
    }

    private Map<String, Object> javaInfo() {
        return ImmutableMap.<String, Object>builder()
            .put("class.version", System.getProperty("java.class.version"))
            .put("version", System.getProperty("java.version"))
            .put("version.date", System.getProperty("java.version.date"))
            .put("compilation.name", ManagementFactory.getCompilationMXBean().getName())
            .put("file.encoding", System.getProperty("file.encoding"))
            .build();
    }

    private List<Map<String, Object>> gcInfo() {
        return ManagementFactory.getGarbageCollectorMXBeans().stream()
            .map(this::toInfoObject)
            .collect(Collectors.toUnmodifiableList());
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

    private Map<String, Object> osInfo() {
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        return ImmutableMap.<String, Object>builder()
            .put("arch", osMXBean.getArch())
            .put("name", osMXBean.getName())
            .put("version", osMXBean.getVersion())
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
