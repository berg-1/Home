package me.berg.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.berg.home.service.ProjectService;
import me.berg.home.service.RedisService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class RedisApplicationRunner implements ApplicationRunner {

    private final RedisService redisService;
    private final ProjectService projectService;


    @Override
    public void run(ApplicationArguments args) {
        log.info("Redis Project Names - Initializing...");
        InitRedisProject();
        log.info("Redis Project Names - Initialized...");
    }

    /**
     * 提取数据库数据至Redis <br/>
     * 定时任务（两个小时一次）
     * corn表达式
     */
    @Scheduled(fixedRate = 7200000)
    private void InitRedisProject() {
        redisService.deleteAll("Projects");
        projectService.list()
                .forEach(project -> redisService.hSet("Projects", project.getProjectName(), project.getPid().toString()));
    }
}
