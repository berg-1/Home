package me.berg.home;

import lombok.extern.slf4j.Slf4j;
import me.berg.home.service.ProjectService;
import me.berg.home.service.RedisService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Slf4j
public class RedisApplicationRunner implements ApplicationRunner {

    final RedisService redisService;
    final ProjectService projectService;

    public RedisApplicationRunner(RedisService redisService, ProjectService projectService) {
        this.redisService = redisService;
        this.projectService = projectService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Redis Project Names - Initializing...");
//        InitRedisProject();
        log.info("Redis Project Names - Initialized...");
    }

    private void InitRedisProject() {
        projectService.list()
                .forEach(project -> redisService.hSet("Projects", project.getPid().toString(), project.getProjectName()));
    }
}
