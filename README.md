## 基于BS架构的K12在线教考平台

### 简介

该项目[exam](https://github.com/CNPolaris/exam)是一个在线教考平台的后端解决方案，基于Java语言编写，使用Spring-Boot、Mybatis-plus框架实现，包括了，动态路由，权限验证等典型功能，为绝大多数考试流程中的业务提供服务。

- [管理员前端](https://github.com/CNPolaris/exam-ui.git)
- [教师前端](https://github.com/CNPolaris/exam-ui-tch.git)
- [学生前端](https://github.com/CNPolaris/exam-ui-stu.git)

### 前序准备

本项目后端技术栈基于Spring-Boot、Redis、MyBatis-plus，前端技术栈基于ES6、vue、vuex、vue-router、vue-cli、axios、element-ui，可以提前了解和学习这些知识会对使用本项目有很大的帮助。

```
├─docs 文档
├─exam-common 通用模块
│  ├─src
│  │  └─main
│  │      └─java
│  │          └─com
│  │              └─polaris
│  │                  └─exam
│  │                      ├─config redis配置文件
│  │                      ├─enums 数据枚举
│  │                      ├─service redis实现类
│  │                      └─utils 一些再次封装的工具类
├─exam-mbg mybatis-plus生成模块
│  ├─src
│  │  └─main
│  │      ├─java
│  │      │  └─com
│  │      │      └─polaris
│  │      │          └─exam
│  │      │              ├─dto 参数
│  │      │              ├─mapper 持久层
│  │      │              ├─pojo 实体类
│  │      └─resources
│  │          └─mapper xml文件
├─exam-security 安全模块
│  ├─src
│  │  └─main
│  │      └─java
│  │          └─com
│  │              └─polaris
│  │                  └─exam
│  │                      └─security
│  │                          ├─annotation
│  │                          ├─aspect
│  │                          ├─component
│  │                          ├─config 配置文件
│  │                          └─util 工具类
└─exam-serve 具体业务模块
    ├─src
    │  └─main
    │      ├─java
    │      │  └─com
    │      │      └─polaris
    │      │          └─exam
    │      │              ├─bo 用户detail
    │      │              ├─config 配置文件
    │      │              ├─controller 控制层
    │      │              │  ├─admin 管理员
    │      │              │  ├─student 学生
    │      │              │  └─teacher 教师
    │      │              ├─dto 数据传输
    │      │              ├─event 事件
    │      │              ├─listener 监听器
    │      │              ├─service 业务层
    │      │              └─utils 工具类
    |      |─resources 配置文件
```

### 功能

```
- 登录 / 注销

- 权限验证
  - 页面权限
  - 路由权限
  - 权限配置

- 多环境发布
 - dev
 - stage
 - prod
 
- 题库功能
 - 单选题编辑
 - 多选题编辑
 - 填空题编辑
 - 判断题编辑
 - 简答题编辑
 - 错题集
 
- 卷库管理
 - 时段试卷
 - 任务试卷
 - 班级试卷
 - 在线阅卷
 - 在线考试
 - 考试记录
 - 成绩分析
 
- 系统管理
 - 性能监控
 - 日志管理
 - 角色管理
 - 资源管理
```

### 开发

```shell
# 克隆项目

# 后端
git clone https://github.com/CNPolaris/exam.git

# 配置数据库
# sql文件位于docs文件夹内

# 修改项目配置文件
# exam-server/src/main/resources/application.yml

# 运行项目
```

### 发布

```shell
# maven打包
mvn clean
mvn package
```

