package org.example.teahouse.core.actuator.info;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class RuntimeInfoContributor implements InfoContributor {
    private final Environment environment;

    @Override
    public void contribute(Builder builder) {
        builder.withDetail("spring", ImmutableMap.builder()
            .put("framework", ImmutableMap.builder().put("version", String.valueOf(SpringVersion.getVersion())).build())
            .put("boot", ImmutableMap.builder().put("version", SpringBootVersion.getVersion()).build())
            .build()
        );

        builder.withDetail("environment",
            ImmutableMap.of("activeProfiles", environment.getActiveProfiles())
        );

        builder.withDetail("network", networkInfo());
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
