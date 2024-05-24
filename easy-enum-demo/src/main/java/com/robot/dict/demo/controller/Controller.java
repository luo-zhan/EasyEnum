package com.robot.dict.demo.controller;


import com.robot.dict.demo.bean.Teacher;
import com.robot.dict.demo.enums.Sex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author robot.luo
 */
@Slf4j
@RestController
public class Controller implements TestFeignClient {

    @Resource
    private TestFeignClient testFeignClient;

    @GetMapping("/getTeacher")
    public Teacher getTeacher() {
        // 观察接口响应中的sex属性值
        return new Teacher(1, "张三", Sex.MALE, "长沙");
    }

    @PostMapping("/addTeacher")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        log.info("json反序列化性别枚举，结果：{}", teacher.getSex().getText());
        return teacher;
    }

    @GetMapping("/findOneTeacherBySex")
    public Teacher listTeacherBySex(Sex sex) {
        log.info("枚举可以直接作为接口参数，入参{}", sex.getText());
        Teacher teacher = new Teacher();
        teacher.setSex(sex);
        return teacher;
    }

    @GetMapping("/getSex")
    public Sex getSex() {
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
        log.info("feign调用后，转换性别{}", sex);
        return sex == Sex.MALE ? Sex.FEMALE : Sex.MALE;
    }
}
