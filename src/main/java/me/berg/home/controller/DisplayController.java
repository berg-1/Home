package me.berg.home.controller;

import lombok.extern.slf4j.Slf4j;
import me.berg.home.service.FileDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class DisplayController {

    FileDataService fileDataService;

    public DisplayController(FileDataService fileDataService) {
        this.fileDataService = fileDataService;
    }

    @GetMapping(value = "/display")
    public String displayPage() {
        return "display";
    }

    /**
     * 将img/下的图片请求跳转值downloadFile@FileController方法
     * @param id 图片id
     * @return redirect
     */
    @GetMapping(value = "/img/{id}")
    public String getImage(@PathVariable String id) {
        return "redirect:/downloadFile?id=" + id;
    }

}
