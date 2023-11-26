package org.example.teahouse.tea.tealeaf;

import org.example.teahouse.core.actuator.health.HealthClient;
import org.example.teahouse.tea.config.FeignClientConfig;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tealeaf", configuration = FeignClientConfig.class)
public interface TealeafClient extends HealthClient {
    @GetMapping("/tealeaves/search/findByName")
    SimpleTealeafModel findByName(@RequestParam("name") String name);

    @GetMapping("/tealeaves")
    PagedModel<SimpleTealeafModel> tealeaves(Pageable pageable);
}
