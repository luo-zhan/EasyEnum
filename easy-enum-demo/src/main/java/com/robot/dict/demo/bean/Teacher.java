package com.robot.dict.demo.bean;

import com.robot.dict.demo.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    private Integer id;

    private String name;
    private Sex sex;

    private String address;
}
