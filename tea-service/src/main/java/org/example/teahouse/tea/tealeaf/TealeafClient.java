package org.example.teahouse.tea.tealeaf;

import org.example.teahouse.core.actuator.health.HealthClient;
import org.example.teahouse.tea.config.FeignClientConfig;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tea-leaf", configuration = FeignClientConfig.class, url = "${feign.client.config.tea-leaf.url}")
public interface TealeafClient extends HealthClient {
    @GetMapping("/tealeaves/search/findByName")
    SimpleTealeafModel findByName(@RequestParam("name") String name);
}
