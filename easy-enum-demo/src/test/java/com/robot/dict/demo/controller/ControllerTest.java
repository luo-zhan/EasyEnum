package com.robot.dict.demo.controller;


import com.robot.dict.demo.Application;
import com.robot.dict.demo.bean.Teacher;
import com.robot.dict.demo.enums.Sex;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ControllerTest {

    @Resource
    private Controller controller;
    @Resource
    private TestRestTemplate restTemplate;


    /**
     * 测试json序列化枚举（接口返回参数是DTO，且DTO中使用了枚举属性）
     */
    @Test
    void test_serialize_by_json() {
        Teacher response = restTemplate.getForObject("/getTeacher", Teacher.class);
        log.info(response.toString());
        assertEquals(response.getSex(), Sex.MALE);
    }

    /**
     * 测试json反序列化枚举（接口入参是DTO，且DTO中使用了枚举属性）
     */
    @Test
    void test_deserialize_by_json() {
        Teacher teacher = new Teacher();
        teacher.setSex(Sex.MALE);

        Teacher response = restTemplate.postForObject("/addTeacher", teacher, Teacher.class);
        log.info(response.toString());
        assertEquals(response, teacher);
    }

    /**
     * 测试spring-converter反序列化枚举（接口入参使用枚举）
     */
    @Test
    void test_deserialize_by_spring_converter() {
        Teacher response = restTemplate.getForObject("/findOneTeacherBySex?sex=1", Teacher.class);
        log.info(response.toString());
        assertEquals(response.getSex(), Sex.MALE);
    }

    /**
     * 测试spring-converter序列化枚举（接口出参使用枚举）
     */
    @Test
    void test_serialize_by_spring_converter() {
        String response = restTemplate.getForObject("/getSex", String.class);
        assertEquals("1", response);
    }

    /**
     * 测试feign调用时序列化枚举（接口入参使用枚举）
     */
    @Test
    void test_feign() {
        Sex sex = controller.testFeign(Sex.MALE);
        assertEquals(sex, Sex.FEMALE);
    }
}