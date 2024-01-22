package com.robot.dict.demo.controller;

import com.robot.dict.demo.enums.Sex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="test",url = "127.0.0.1:8080")
public interface TestFeignClient {

    @GetMapping("/feignFunction")
    Sex feignFunction(@RequestParam("sex") Sex sex);
}
