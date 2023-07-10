# EasyEnum
[![coverage](https://img.shields.io/badge/coverage-100%25-red)]()
[![GitHub](https://img.shields.io/github/license/luo-zhan/EasyEnum)](http://opensource.org/licenses/apache-2-0)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/luo-zhan/EasyEnum)]()
[![GitHub last commit](https://img.shields.io/github/last-commit/luo-zhan/EasyEnum?label=Last%20commit)]()

枚举也能这么简单！

## 功能
- [x] 省略属性定义
- [x] 省略工具方法定义
- [x] MyBatis中支持枚举映射（Bean中用枚举属性）
- [x] MyBatisPlus中Wrapper支持枚举条件
- [x] SpringMVC中支持枚举传输（DTO、VO用枚举属性）
- [x] Feign调用中使用枚举传输
- [ ] Dubbo调用中使用枚举传输

## 快速上手

### 1. 定义枚举类
#### Before
回想一下，以往每次使用枚举都要写一堆重复的字段申明和工具方法
```java
public enum Sex {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(3, "未知");

    private Integer code;
    private String text;
    
    Sex(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

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
#### After
实现工具包提供的`Dict`接口（字典），只需要写一行代码即可
```java
/**
 * 性别枚举示例
 */
public enum Sex implements Dict<Integer> {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(3, "未知");

    Sex(Integer code, String text) {
        // 一个init方法搞定
        init(code, text);
    }
}
```
> Dict的含义即为字典，拥有code和text两个固定属性，其实绝大多数枚举都符合该性质
### 2.使用Dict提供的工具方法
基础的转换方法：
```java

// 通过code获取text
String text = Dict.getTextByCode(Sex.class, 1); // "男"
// 通过text获取code
Integer code = Dict.getCodeByText(Sex.class, "男"); // 1
// 通过code获取枚举
Sex sex = Dict.getByCode(Sex.class, 1); // Sex.MALE
       

```
转换成字典项集合，常用于给前端下拉框展示使用
```java
// 获取枚举的所有键值对，
List<DictBean> all = Dict.getAll(Sex.class); // [{code:1,text:"男"},{code:2,text:"女"},{code:3,text:"未知"}]
// 获取枚举的指定元素
List<DictBean> items = Dict.getItems(Sex.MALE, Sex.FEMALE); // [{code:1,text:"男"},{code:2,text:"女"}]
// 获取枚举的部分元素，排除指定枚举项
List<DictBean> items = Dict.getItemsExclude(Sex.UNKNOWN); // [{code:1,text:"男"},{code:2,text:"女"}]
```

## 依赖
   ```xml
   <dependency>
       <groupId>io.github.luo-zhan</groupId>
       <artifactId>easy-enum</artifactId>
       <version>1.0.0-RELEASE</version>
   </dependency>
   ```

## 交流

有任何问题或想说的，欢迎提issues或者来讨论组内畅所欲言

[💬进入讨论组](https://github.com/luo-zhan/EasyEnum/discussions)
