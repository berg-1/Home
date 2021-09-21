package me.berg.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index.html", "index"})
    public String homePage() {
        return "index";
    }

    @GetMapping(value = "/switch")
    public String switchPage() {
        return "switch";
    }

    @GetMapping(value = "/upload")
    public String uploadPage() {
        return "upload";
    }

    @GetMapping(value = "/test1")
    public String testPage() {
        return "test";
    }

}
