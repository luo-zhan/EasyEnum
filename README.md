# EasyEnum
[![coverage](https://img.shields.io/badge/coverage-100%25-red)]()
[![GitHub](https://img.shields.io/github/license/luo-zhan/EasyEnum)](http://opensource.org/licenses/apache-2-0)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/luo-zhan/EasyEnum)]()
[![GitHub last commit](https://img.shields.io/github/last-commit/luo-zhan/EasyEnum?label=Last%20commit)]()

重铸枚举荣光！！

## 功能
- [x] 简化枚举类定义（省略属性定义&get方法，提供大量实用的枚举转换工具）
- [x] Json序列化、反序列支持（Redis/MQ等需要json序列化的场景）
- [x] Spring开发支持（支持Post请求DTO/VO中使用枚举属性、支持Get请求方法出入参使用枚举、Feign调用支持枚举传输）
- [x] MyBatis开发支持（Entity中可直接用枚举属性在sql中自动转换成数值、MyBatisPlus中Wrapper支持直接使用枚举条件）
- [ ] Dubbo调用中使用枚举传输

## 快速上手

### 1. 创建枚举类

回想一下，以前我们每次创建枚举类都要定义属性、写get方法和一堆工具方法，代码非常重复。

而使用EasyEnum后，将枚举实现`Dict`接口，代码将简化成如下。
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
上例中的DictBean是Dict的一个实现类，用于存储和展示真实的枚举数据，DictBean申明如下：
```java
public class DictBean implements Dict<Object> {
    /**
     * 字典code
     */
    private final Object code;
    /**
     * 字典text
     */
    private final String text;
    /**
     * 是否过期
     * 一般来说，过期的字典项不能用于新增，而查询时需要作文本翻译
     */
    private final boolean isDeprecated;
    
}
```
其中`isDeprecated`属性的作用是如果我们在枚举项上使用`@Deprecated`注解时，这个属性的值就会为true
```java
public enum Sex implements Dict<Integer> {
    MALE(1, "男"),
    FEMALE(2, "女"),
    @Deprecated
    UNKNOWN(3, "未知");
    ...
}
// UNKOWN对应的数据体中isDeprecated为true
List<DictBean> all = Dict.getAll(Sex.class); // [{code:1,text:"男"},{code:2,text:"女"},{code:3,text:"未知",isDeprecated:true}]
```

有了这些工具方法，枚举类中再也不用写重复的方法代码了。

### 3.Spring开发支持
#### 3.1 Post请求DTO/VO中使用枚举属性
在以往的接口开发中，是不是当DTO中有枚举含义的字段时，都是用int类型来处理，然后代码中写逻辑时需要这样：
```java
// 实际上这段代码还会告警，提示应使用equals
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
    private Integer id;
    private String name;
    private SexEnum sex; // 使用枚举类型
    
}

// 代码中：
switch(studentDTO.getSex()){
    case MALE:  System.out.print("男");break;
    case FEMAL: System.out.print("女");break;
}
```
> 这里DTO、VO都支持使用枚举属性，因为对枚举的json序列化和反序列化做了支持
> 
> 源码参考：DictJacksonConfiguration
#### 3.2 支持Get请求方法出入参使用枚举
接口参数除了Json序列化方式，也有可能是Get请求参数方式，也做了支持
```java
@GetMapping("/listStudentBySex")
public List<Student> listStudent(Sex sex){
    ...   
}
```
> 这里无论是入参还是出参都可以使用枚举，因为对枚举对Spring的converter做了支持
> 
> 源码参考：DictSpringConvertConfiguration

#### 3.3 Feign调用支持枚举参数传输
Feign调用时，方法参数会先在调用方序列化成url请求参数进行传输，到达被调用方后再反序列化成方法入参，这里也对枚举做了支持

```java

// 被调用方接口申明
@FeignClient(url = "", path = "/student")
public interface StudentFeignClient {

    @GetMapping("/listStudentBySex")
    List<Student> listStudent(Sex sex); //直接使用枚举入参
}

// 调用方：
@Resource
private StudentFeignClient studentFeignClient;
...
List<Student> studentList = studentFeignClient.listStudent(Sex.MALE);
```
> 源码参考：DictSpringConvertConfiguration

### 4.Mybatis开发支持
可以在实体中直接使用枚举来充当属性，sql查询后会自动进行类型转换：
```java
public class Student{
    @TableId
    private Integer id;
    private String name;
    private SexEnum sex; // 使用枚举类型
    
}
```
另外，如果你使用了MybatisPlus，那么也可以简化MybatisPlus的枚举条件查询代码：
```java
Wrapper<Student> wrapper = new LambdaQueryWrapper<>(); // QueryWrapper同理
// 之前
wrapper.eq(Student::sex, Sex.MALE.getCode());
// 之后
wrapper.eq(Student::sex, Sex.MALE);
// 注意：这里的代码简化不是必须要求Student类中的sex属性类型是枚举，定义为Integer同样有效
```
> 源码参考：DictMybatisConfiguration
## 使用方式
导入依赖即自动生效（项目中必须已经依赖SpringBoot，但没有使用Mybatis不会有影响）
   ```xml
    <!-- Spring开发支持（含核心功能、Json、Mybatis、SpringConverter） -->
    <dependency>
        <groupId>io.github.luo-zhan</groupId>
        <artifactId>easy-enum-for-spring</artifactId>
        <version>1.2.0-RELEASE</version>
    </dependency>
   ```
如果你不需要Spring和Mybatis的支持，只需要Dict枚举的工具方法，可以只引入以下依赖
```xml
<!-- 核心模块 -->
<dependency>
   <groupId>io.github.luo-zhan</groupId>
   <artifactId>easy-enum</artifactId>
   <version>1.2.0-RELEASE</version>
</dependency>
```
## 交流

有任何问题或建议，欢迎提issues或者来讨论组内畅所欲言

[💬进入讨论组](https://github.com/luo-zhan/EasyEnum/discussions)
