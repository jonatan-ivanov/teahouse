package org.example.teahouse.tea.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.tea.service.TeaService;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;

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
        // Adding a non existent tea leave to throw an exception
        List<SimpleTealeafModel> tealeaves = new ArrayList<>(teaService.tealeaves());
        tealeaves.add(new SimpleTealeafModel(UUID.randomUUID(), "english breakfast", "white", "10 g", "200 °C", "10 min"));

        model.addAttribute("tealeaves", tealeaves);
        model.addAttribute("waters", teaService.waters());

        return "steep";
    }
}
