package com.project.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    @GetMapping("api/swagger")
    public String redirect(){
        return "redirect:/swagger-ui/index.html";
    }
}
