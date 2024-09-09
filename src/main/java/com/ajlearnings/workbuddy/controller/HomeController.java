package com.ajlearnings.workbuddy.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.ServletContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Hidden
@RestController
@RequestMapping("/")
public class HomeController {

    private final ServletContext servletContext;

    public HomeController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping
    public RedirectView redirectToSwaggerUI() {
        String contextPath = servletContext.getContextPath();
        String swaggerUrl = contextPath + "/swagger-ui/index.html";
        return new RedirectView(swaggerUrl);
    }
}
