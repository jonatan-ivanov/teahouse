package org.example.teahouse.tea.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.tea.service.TeaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Hidden
@Controller
@RequiredArgsConstructor
public class SteepController {
    private final TeaService teaService;

    @GetMapping("/steep")
    public String steep(Model model) {
        model.addAttribute("tealeaves", teaService.tealeaves());
        model.addAttribute("waters", teaService.waters());

        return "steep";
    }
}
