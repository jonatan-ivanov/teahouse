package org.example.teahouse.tea.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tea.service.TeaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Tea API")
public class TeaController {
    private final TeaService teaService;

    @GetMapping("/tea/{name}")
    @Operation(summary = "Tells you how to make a cup of tea")
    public TeaResponse make(@PathVariable String name, @RequestParam("size") String size) {
        return teaService.make(name, size);
    }
}
