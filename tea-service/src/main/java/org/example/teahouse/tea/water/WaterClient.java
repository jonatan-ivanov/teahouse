package org.example.teahouse.tea.water;

import org.example.teahouse.water.api.WaterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "water", url = "${feign.client.config.water.url}")
public interface WaterClient {
    @GetMapping("/water/search/findBySize")
    WaterResponse findBySize(@RequestParam("size") String size);
}
