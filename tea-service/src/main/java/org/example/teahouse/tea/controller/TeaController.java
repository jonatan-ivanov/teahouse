package org.example.teahouse.tea.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tea.service.TeaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "Tea API")
public class TeaController {
    private final TeaService teaService;

    @GetMapping("/tea/{name}")
    @ApiOperation("Tells you how to make a cup of tea")
    public TeaResponse make(@PathVariable String name, @RequestParam("size") String size) {
        return teaService.make(name, size);
    }
}
