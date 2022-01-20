package me.berg.home;

import me.berg.home.model.MyFile;
import me.berg.home.service.MyFileService;
import me.berg.home.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HomeApplicationTests {

    @Autowired
    RedisService redisService;

    @Autowired
    MyFileService myFileService;

    @Test
    void redisTest() {
        String get = redisService.get("RedisTest");
        System.out.println(get);
    }

    @Test
    void sqlTest(){
        MyFile myFile = myFileService.getById("06d2a898-ab2c-44fd-9009-5cc0ea318c68");
        assert myFile.getFilename().equals( "a.py");
    }

}
