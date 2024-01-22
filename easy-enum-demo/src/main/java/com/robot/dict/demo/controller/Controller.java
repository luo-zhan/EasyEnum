package com.robot.dict.demo.controller;


import com.robot.dict.demo.bean.Teacher;
import com.robot.dict.demo.enums.Sex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class Controller implements TestFeignClient {

    @Resource
    private TestFeignClient testFeignClient;

    @GetMapping("/json/serialize")
    public Teacher getTeacher() {
        // 观察接口响应中的sex属性值
        return new Teacher(1, "张三", Sex.MALE, "长沙");
    }

    @PostMapping("/json/deserialize")
    public String addTeacher(@RequestBody Teacher teacher) {
        return "json反序列化性别枚举，结果：" + teacher.getSex().getText();
    }

    @GetMapping("/deserialize")
    public String listTeacher(Sex sex) {
        return "枚举可以直接作为接口参数，结果：" + sex.getText();
    }

    @GetMapping("/serialize")
    public Sex listTeacher() {
        // 观察接口响应
        return Sex.MALE;
    }

    @GetMapping("/testFeign")
    public Sex testFeign(Sex sex) {
        log.info("feign调用前{}", sex);
        return testFeignClient.feignFunction(sex);
    }

    @Override
    public Sex feignFunction(Sex sex) {
        return sex;
    }
}
