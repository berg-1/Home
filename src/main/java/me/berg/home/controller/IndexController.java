package me.berg.home.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import me.berg.home.model.MyFile;
import me.berg.home.service.MyFileService;
import me.berg.home.service.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class IndexController {

    private final MyFileService myFileService;
    private final RedisService redisService;

    public IndexController(MyFileService myFileService, RedisService redisService) {
        this.myFileService = myFileService;
        this.redisService = redisService;
    }

    @GetMapping(value = {"/", "/index.html", "index"})
    public String homePage(Model model) {
        List<MyFile> titles = getTitles();
        model.addAttribute("titles", titles);
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

    /**
     * 获取文件标题、描述和所属项目
     *
     * @return MyFile 集合
     */
    private List<MyFile> getTitles() {
        List<MyFile> myFiles = myFileService.list(new QueryWrapper<MyFile>().orderByDesc("modify_time").eq("typename", "application/octet-stream").last("limit 5"));
        myFiles.forEach((file) ->
        {
            if (file.getFilename().length() > 10) {
                file.setFilename(file.getFilename().substring(0, 10) + "...");
            }
            file.setTypename(getProjectName(file.getPid()));
        });
        return myFiles;
    }

    private String getProjectName(Short pid) {
        return redisService.hGet("Projects", pid.toString());
    }

}
