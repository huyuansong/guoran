# ‼️.gitignore勿动

# 项目介绍

这是一个企业管理项目
开发文档直达连接
[dev_doc.md](dev_doc.md)

# 后端项目结构

```java
-com.guoran.server
  |config //配置类
  | ---SwaggerConfig //swagger配置
  |common //工具类
  |test //个人名字或业务名
  |  ---controller //控制层
  |  ---model //实体类
  |  ---repository //dao层
  |    ---repository.xml //dao层实现
  |  ---service //业务层
  |    ---service.impl //业务层实现  
  |weicustomer //实例代码
  |  ---controller //控制层
  |  ---model //实体类
  |  ---repository //dao层
  |    ---repository.xml //dao层实现
  |  ---service //业务层
  |    ---service.impl //业务层实现 
  |springboot启动类
```

# 所需环境

前端：Node.js@16

后端: Java@8、Mysql@5.8、SpringBoot@2.2.4

运维: ngnix

# 安装教程

## 1. 导入Sql语句

新建数据库guoran

把sql导入到guoran数据库库

## 2. 启动后端

Jar包启动

```sh
java -jar guoran1.0.jar
```

项目启动
....

## 3. 启动前端

0. 克隆本仓库
1. 进入启动项目

```cmd
cd guoran/web
```

2. 安装依赖

```cmd
npm i
```

3. 运行

```cmd
npm run dev
```

# 开发进度

| 名称  | 功能模块 | 进度  |
|-----|------|-----|
| 魏梦  | 客户管理 | 5%  |
| 李少锋 | 仓库管理 | 0%  |
| 杨震乾 | 客户管理 | 0%  |
| 申优扬 | 销售管理 | 0%  |
| 裴圣洁 | 饮料车间 | 1%  |
| 卜春辉 | 设备管理 | 0%  |
| 何菲凡 | 生产管理 | 1%  |
| 刘朋飞 | 财务管理 | 0%  |
| 马亚鹏 | 零工信息 | 0%  |
| 姚梦珂 | 设备管理 | 0%  |
| 陈炳峰 | 饮料车间 | 0%  |

# 需求进度

- [ ] 开始
    - [X] 1.项目分工
    - [ ] 2.开始创建对应文件夹
