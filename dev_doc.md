# 开发文档

## 添加个人的包

```java
@MapperScans(value = {
@MapperScan("com.guoran.server.weicustomer.repository"),
@MapperScan("com.guoran.server.test.repository")
})
```

以`weicustomer`为例，加入需要的目录（你的包）即可

## 实例

1. 加入`auth`包

```java
@MapperScans(value = {
@MapperScan("com.guoran.server.weicustomer.repository"),
@MapperScan("com.guoran.server.test.repository")
@MapperScan("com.guoran.server.auth.repository")
})
```

[GuoranServerApplication.java](src%2Fmain%2Fjava%2Fcom%2Fguoran%2Fserver%2FGuoranServerApplication.java)

# 统一返回结果

```json
{
    code:"200",
    msg:"成功",
    data:[
    {
        "id":1,
        "name":"张三",
        "age":18
    },
    {
        "id":2,
        "name":"李四",
        "age":19
    }
    ]
}
```

# 统一异常处理
