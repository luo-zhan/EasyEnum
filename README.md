# EasyEnum
[![coverage](https://img.shields.io/badge/coverage-100%25-red)]()
[![GitHub](https://img.shields.io/github/license/luo-zhan/EasyEnum)](http://opensource.org/licenses/apache-2-0)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/luo-zhan/EasyEnum)]()
[![GitHub last commit](https://img.shields.io/github/last-commit/luo-zhan/EasyEnum?label=Last%20commit)]()

枚举也能这么简单！

## 功能
- [x] 省略属性定义、get方法
- [x] 提供枚举工具方法
- [x] MyBatis中支持枚举映射（Bean中用枚举属性）
- [x] MyBatisPlus中Wrapper支持枚举条件
- [x] SpringMVC中支持枚举传输（DTO、VO用枚举属性）
- [x] Feign调用中使用枚举传输
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
Dict接口除了优化枚举申明，还提供了大量工具api：
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

## 依赖
   ```xml
   <dependency>
       <groupId>io.github.luo-zhan</groupId>
       <artifactId>easy-enum</artifactId>
       <version>1.0.0-RELEASE</version>
   </dependency>
   ```

## 交流

有任何问题或建议，欢迎提issues或者来讨论组内畅所欲言

[💬进入讨论组](https://github.com/luo-zhan/EasyEnum/discussions)
