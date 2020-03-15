package org.example.teahouse.tea.controller;

import lombok.RequiredArgsConstructor;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tea.service.TeaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeaController {
    private final TeaService teaService;

    @GetMapping("/tea/{name}")
    public TeaResponse make(@PathVariable String name, @RequestParam("size") String size) {
        return teaService.make(name, size);
    }
}
