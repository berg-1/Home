package me.berg.home.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.berg.home.model.MyFile;
import me.berg.home.service.MyFileService;
import me.berg.home.service.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MyFileService myFileService;
    private final RedisService redisService;

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

    @GetMapping(value = "/test1")
    public String testPage() {
        return "test";
    }

    /**
     * For Testing.
     *
     * @param info user info
     */
    @GetMapping(value = "/userInfo")
    @CrossOrigin
    @ResponseStatus(value = HttpStatus.OK)
    public void getInfo(@RequestParam(value = "info") String info) {
        log.info("New Cookie: {}", info);
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
