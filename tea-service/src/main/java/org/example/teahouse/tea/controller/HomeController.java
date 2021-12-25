package org.example.teahouse.tea.controller;

import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Hidden
@Controller
public class HomeController {
    @GetMapping("/")
    RedirectView redirectToSwaggerUi() {
        return new RedirectView("/swagger-ui.html");
    }
}
