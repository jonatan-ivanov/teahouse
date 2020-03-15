package org.example.teahouse.tea.tealeaf;

import org.example.teahouse.tealeaf.api.TealeafResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tea-leaf", url = "${feign.client.config.tea-leaf.url}")
public interface TealeafClient {
    @GetMapping("/tealeaf/search/findByName")
    TealeafResponse findByName(@RequestParam("name") String name);
}
