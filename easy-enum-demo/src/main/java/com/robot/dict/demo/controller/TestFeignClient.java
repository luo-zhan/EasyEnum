package com.robot.dict.demo.controller;

import com.robot.dict.demo.enums.Sex;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author robot.luo
 */
@FeignClient(name = "test", url = "127.0.0.1:8080", configuration = TestFeignClient.Config.class)
public interface TestFeignClient {

    /**
     * feign调用测试方法
     * 测试入参直接使用枚举类型
     */
    @GetMapping("/feignFunction")
    Sex feignFunction(@RequestParam("sex") Sex sex);

    class Config {
        @Bean
        public RequestInterceptor apiInterceptor() {
            return template -> System.out.println("Feign调用生成的URL（枚举已经序列化成枚举值)）:" + template.request());
        }


    }
}
