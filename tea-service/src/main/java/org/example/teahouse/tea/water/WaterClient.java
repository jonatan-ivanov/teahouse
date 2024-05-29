package org.example.teahouse.tea.water;

import org.example.teahouse.core.actuator.health.HealthClient;
import org.example.teahouse.tea.config.FeignClientConfig;
import org.example.teahouse.water.api.SimpleWaterModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "proxy-service", configuration = FeignClientConfig.class)
public interface WaterClient extends HealthClient {
    @GetMapping("/water-service/waters/search/findBySize")
    SimpleWaterModel findBySize(@RequestParam("size") String size);

    @GetMapping("/water-service/waters")
    PagedModel<SimpleWaterModel> waters(Pageable pageable);
}
