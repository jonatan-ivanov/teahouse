package org.example.teahouse.tealeaf.controller;

import springfox.documentation.annotations.ApiIgnore;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    @ApiIgnore
    @GetMapping("/")
    RedirectView redirectWithUsingRedirectView() {
        return new RedirectView("/swagger-ui/");
    }
}
