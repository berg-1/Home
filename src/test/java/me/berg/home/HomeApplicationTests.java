package me.berg.home;

import me.berg.home.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HomeApplicationTests {

    @Autowired
    RedisService redisService;

    @Test
    void contextLoads() {
        String get = redisService.get("RedisTest");
        System.out.println(get);
    }

}
