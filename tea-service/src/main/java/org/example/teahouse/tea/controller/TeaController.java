package org.example.teahouse.tea.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tea.service.TeaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Tea API")
@Slf4j
public class TeaController {
    private final TeaService teaService;
    private final MeterRegistry registry;

    @GetMapping("/tea/{name}")
    @Operation(summary = "Tells you how to make a cup of tea")
    public TeaResponse make(@PathVariable String name, @RequestParam("size") String size) {
        Counter.builder("tea.make")
            .tags("name", name)
            .tags("size", size)
            .register(registry)
            .increment();
        log.info("Will make tea [{}] with size [{}]", name, size);
        return teaService.make(name, size);
    }
}
