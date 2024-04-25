package com.example.sd_41.controller.redis;

import com.example.sd_41.service.redis.BaseRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    //Test dữ liệu xem hàm chạy có oke không
    private final BaseRedisService baseRedisService;

    @PostMapping("/create")
    public void set(){
        baseRedisService.set("key1", "value1");
    }

    @DeleteMapping("/delete/{key}")
    public void delete(String key){
        baseRedisService.delete(key);
    }
}
