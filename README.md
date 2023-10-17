# EasyEnum
[![coverage](https://img.shields.io/badge/coverage-100%25-red)]()
[![GitHub](https://img.shields.io/github/license/luo-zhan/EasyEnum)](http://opensource.org/licenses/apache-2-0)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/luo-zhan/EasyEnum)]()
[![GitHub last commit](https://img.shields.io/github/last-commit/luo-zhan/EasyEnum?label=Last%20commit)]()

只需体验3分钟，带你见识全新的枚举开发体验！

## 功能
- [x] 简化枚举类定义（省略属性定义&get方法，提供大量实用的枚举转换工具）
- [x] Spring开发支持（支持Post请求DTO/VO中使用枚举属性、支持Get请求方法出入参使用枚举、Feign调用支持枚举传输）
- [x] MyBatis开发支持（Entity中可直接用枚举属性在sql中自动转换成数值、MyBatisPlus中Wrapper支持直接使用枚举条件）
- [ ] Dubbo调用中使用枚举传输

## 快速上手

### 1. 创建枚举类

回想一下，以前我们每次创建枚举类都要定义属性、写get方法和一堆工具方法，代码非常重复。
#### Before：
```java
public enum Sex {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(3, "未知");
    // 1、属性定义
    private final Integer code;
    private final String text;
    // 2、构造方法
    Sex(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    // 3、get方法
    public Integer getCode() {
        return code;
    }
    public String getText() {
        return text;
    }
    // 4、工具方法
    /**
     * 根据code获取text
     */
    public String getTextByCode(Integer code){
        return Stream.of(Sex.class.getEnumConstants())
                .filter((e) -> (e.getCode().equals(code)))
                .map(Dict::getText)
                .findAny()
                .orElse(null);
    }

    /**
     * 根据code获取枚举
     */
    public Sex getFromCode(Integer code){
        return Stream.of(Sex.class.getEnumConstants())
                .filter((e) -> (e.getCode().equals(code)))
                .findAny()
                .orElse(null);
    }
    // 省略更多方法...
}
```

使用EasyEnum后，将枚举实现`Dict`接口，代码将简化成如下。
#### After：
```java
/**
 * 性别枚举示例
 */
public enum Sex implements Dict<Integer> {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(3, "未知");

    Sex(Integer code, String text) {
        // 一个init方法搞定，该方法来自Dict接口
        init(code, text);
    }
}
```
你没看错，原先的属性定义、get方法、工具方法全部省略，仅仅只需要在构造方法中调用`init()`方法即可。接着就能通过`getCode()`和`getText()`方法获取枚举的编码值和文本。
```java
Integer code = Sex.MALE.getCode(); // 1
String text = Sex.MALE.getText(); // "男"
```

> Dict的含义即为字典，拥有编码和文本两个固定属性，而在业务开发中定义的枚举大都是就是字典，所以抽象成接口，省略枚举类中重复代码。

### 2.使用枚举方法
Dict接口除了优化枚举申明，还提供了大量实用的api：
```java
// 1、通过code获取text
String text = Dict.getTextByCode(Sex.class, 1); // "男"
// 2、通过text获取code
Integer code = Dict.getCodeByText(Sex.class, "男"); // 1
// 3、通过code获取枚举
Sex sex = Dict.getByCode(Sex.class, 1); // Sex.MALE
```
转换成字典项集合，常用于给前端下拉框展示使用
```java
// 4、获取枚举的所有元素
List<DictBean> all = Dict.getAll(Sex.class); // [{code:1,text:"男"},{code:2,text:"女"},{code:3,text:"未知"}]
// 5、获取枚举的指定元素
List<DictBean> items = Dict.getItems(Sex.MALE, Sex.FEMALE); // [{code:1,text:"男"},{code:2,text:"女"}]
// 6、排除指定元素，获取其他元素
List<DictBean> items = Dict.getItemsExclude(Sex.UNKNOWN); // [{code:1,text:"男"},{code:2,text:"女"}]
```
有了这些工具方法，枚举类中再也不用写重复的方法代码了。

### 3.Spring开发支持
在以往的接口开发中，是不是当DTO中有枚举含义的字段时，都是用int类型来处理，然后代码中写逻辑时需要这样：
```java
// 实际上这段代码还会告警
if(student.getSex() == SexEnum.MALE.getCode()){
    ...
}
```
又比如当遇到switch-case时，代码也会相当不优雅：
```java
// 写法1，直接用魔法值
switch(studentDTO.getSex()){
    case 1: System.out.print("男");break;
    case 2: System.out.print("女");break;
}
// 写法2，先转换成枚举类
SexEnum sex = SexEnum.getByCode(studentDTO.getSex());
switch(sex){
    case MALE: System.out.print("男");break;
    case FEMAL: System.out.print("女");break;
}
```
那为什么不直接在Bean中使用枚举类型的属性呢？
```java
public class StudentDTO {
    private int id;
    private String name;
    private SexEnum sex; // 使用枚举类型
    
}

// 代码中：
switch(studentDTO.getSex()){
    case MALE:  System.out.print("男");break;
    case FEMAL: System.out.print("女");break;
}
```
> 这里DTO、VO都支持使用枚举属性，主要是源码中对枚举的json序列化和反序列化做了支持
## 依赖
   ```xml
    <!-- 核心模块 -->
    <dependency>
       <groupId>io.github.luo-zhan</groupId>
       <artifactId>easy-enum</artifactId>
       <version>1.2.0-RELEASE</version>
    </dependency>

    <!-- spring开发支持（Json、Mybatis、Converter），引入这个不需要再引入上面核心模块 -->
    <dependency>
        <groupId>io.github.luo-zhan</groupId>
        <artifactId>easy-enum-for-spring</artifactId>
        <version>1.2.0-RELEASE</version>
    </dependency>
   ```

## 交流

有任何问题或建议，欢迎提issues或者来讨论组内畅所欲言

[💬进入讨论组](https://github.com/luo-zhan/EasyEnum/discussions)
