package com.robot.dict.demo.controller;


import com.robot.dict.demo.bean.Teacher;
import com.robot.dict.demo.enums.Sex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/json/serialize")
    public Teacher getTeacher() {
        // 观察接口响应中的sex属性值
        return new Teacher(1, "张三", Sex.MALE, "长沙");
    }

    @GetMapping("/json/deserialize")
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
}
