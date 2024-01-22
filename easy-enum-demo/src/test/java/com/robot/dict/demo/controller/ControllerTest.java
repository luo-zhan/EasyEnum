package com.robot.dict.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.robot.dict.demo.Application;
import com.robot.dict.demo.bean.Teacher;
import com.robot.dict.demo.enums.Sex;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Slf4j
class ControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTeacher() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/json/serialize");
        String result = request(request);
        assertEquals("{\"id\":1,\"name\":\"张三\",\"sex\":1,\"address\":\"长沙\"}", result);
    }

    @Test
    void addTeacher() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setSex(Sex.MALE);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/json/deserialize")
                .content(objectMapper.writeValueAsString(teacher))
                .contentType(MediaType.APPLICATION_JSON);
        String result = request(request);
        assertEquals("json反序列化性别枚举，结果：男", result);

    }

    @Test
    void listTeacher() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/deserialize").queryParam("sex", "1");
        String result = request(request);
        assertEquals("枚举可以直接作为接口参数，结果：男", result);
    }

    @Test
    void testListTeacher() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/serialize");
        String result = request(request);
        assertEquals("1", result);
    }

    @Test
    void testFeignClient() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/testFeign").queryParam("sex", "1");
        String result = request(request);
        assertEquals("1", result);
    }

    private String request(MockHttpServletRequestBuilder mockMvcRequestBuilder) throws Exception {
        MockHttpServletResponse response = mvc.perform(mockMvcRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        String result = response.getContentAsString();
        log.info(result);
        return result;
    }
}