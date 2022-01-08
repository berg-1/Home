package me.berg.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.berg.home.service.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UploadController {

    private final RedisService redisService;

    @GetMapping(value = "/upload")
    public String uploadPage(Model model) {
        Map<String, String> projectTypes = redisService.hGetAll("Projects");
        model.addAttribute("types", projectTypes);
        return "upload";
    }


}
