package com.robot.dict.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Teacher {
    private Integer id;
    private String name;
    private String phone;
    private String address;
}
