package org.example.teahouse.tealeaf.controller;

import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    @Hidden
    @GetMapping("/")
    RedirectView redirectWithUsingRedirectView() {
        return new RedirectView("/swagger-ui.html");
    }
}
