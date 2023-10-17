package com.robot.dict;


import com.alibaba.fastjson2.JSON;
import com.robot.dict.bean.Teacher;
import com.robot.dict.enums.Sex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DictTest {
    private List<Teacher> teachers;

    @BeforeEach
    void setUp() {
        // 三个老师测试数据
        Teacher teacher1 = new Teacher(1, "张三", "13311111111", "湖南长沙");
        Teacher teacher2 = new Teacher(2, "李四", "13322222222", "湖南长沙");
        Teacher teacher3 = new Teacher(3, "王五", "13333333333", "湖南长沙");
        teachers = Arrays.asList(teacher1, teacher2, teacher3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTextByCode() {
        String text = Dict.getTextByCode(Sex.class, 1);
        assertEquals("男", text);
    }

    @Test
    void getCodeByText() {
        Integer code = Dict.getCodeByText(Sex.class, "男");
        assertEquals(1, code);
    }

    @Test
    void getByCode() {
        Sex sex = Dict.getByCode(Sex.class, 1);
        assertEquals(Sex.MALE, sex);
    }

    @Test
    void getAll() {
        List<DictBean> all = Dict.getAll(Sex.class);
        System.out.println(JSON.toJSONString(all));
        assertEquals(3, all.size());
    }

    @Test
    void getItems() {
        List<DictBean> items = Dict.getItems(Sex.MALE, Sex.FEMALE);
        System.out.println(JSON.toJSONString(items));
        assertEquals(2, items.size());
    }

    @Test
    void getItemsExclude() {
        List<DictBean> items = Dict.getItemsExclude(Sex.MALE);
        System.out.println(JSON.toJSONString(items));
        assertEquals(2, items.size());
    }

    @Test
    void parse() {
        // 转换为字典集合
        List<DictBean> dictBeans = Dict.parseList(teachers, Teacher::getId, Teacher::getName);
        System.out.println(JSON.toJSONString(dictBeans));
        assertEquals(3, dictBeans.size());
    }
}