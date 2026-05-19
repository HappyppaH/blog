# 📝 个人博客系统后端

基于 **Spring Boot 3** + **MyBatis-Plus** + **MySQL** + **Redis** + **Spring Security** + **JWT** 开发的个人博客后端系统。

## 🚀 技术栈

| 技术 | 说明 |
|------|------|
| Spring Boot 3.5 | 项目基础框架 |
| MyBatis-Plus | ORM 框架，简化数据库操作 |
| MySQL 8.0 | 关系型数据库 |
| Redis | 缓存中间件，提升接口响应速度 |
| Spring Security | 安全框架，实现认证与授权 |
| JWT | 无状态身份认证 |
| AOP | 面向切面编程，实现操作日志 |
| 自建文档 | 接口文档（/doc） |

## 📦 项目结构

- `annotation/` — 自定义注解（@Log）
- `aspect/` — AOP 切面（操作日志）
- `common/` — 通用类（Result、GlobalExceptionHandler）
- `config/` — 配置类（Security、MyBatis-Plus、Redis）
- `controller/` — 控制器层（接口）
- `entity/` — 实体类
- `filter/` — JWT 过滤器
- `handler/` — 时间填充处理器
- `mapper/` — 数据访问层
- `service/impl/` — 业务逻辑层与实现
- `utils/` — 工具类（JwtUtil）

## 🔧 功能模块

### 👤 用户管理
- 用户注册 / 登录（JWT 认证）
- 用户增删改查（管理员权限）
- 参数校验（@Valid）

### 📝 文章管理
- 文章增删改查
- 分页查询 + 按分类筛选 + 关键词搜索
- Redis 缓存文章列表和详情
- 自动记录作者（从 token 获取当前用户）

### 💬 评论管理
- 发表评论 / 删除评论
- 文章详情页展示该文章所有评论

### 🛡️ 权限控制
- 基于 RBAC 的角色权限模型
- 管理员（admin）：可管理所有用户
- 普通用户（user）：只能操作自己的文章和评论

### 📋 操作日志
- AOP 切面 + 自定义 @Log 注解
- 自动记录操作人、操作描述、请求参数、IP、时间

### 📖 接口文档
- 访问 `/doc` 查看所有接口

## 🛠️ 快速开始

### 环境要求
- JDK 17+
- MySQL 8.0+
- Redis
- Maven 3.6+

### 运行步骤

**1. 克隆项目**

git clone https://github.com/HappyppaH/blog.git

**2. 创建数据库**

CREATE DATABASE blog DEFAULT CHARACTER SET utf8mb4;

**3. 修改配置**

打开 src/main/resources/application.properties，修改数据库用户名和密码。

**4. 启动 Redis**

**5. 启动项目**

运行 BlogApplication.java 的 main 方法。

**6. 访问接口文档**

http://localhost:8080/doc


## 📡 接口概览

| 模块 | 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|------|
| 认证 | POST | /login | 登录获取 token | 公开 |
| 用户 | GET | /users | 用户列表 | 公开 |
| 用户 | POST | /users | 注册 | 公开 |
| 用户 | PUT | /users/{id} | 修改用户 | 管理员 |
| 用户 | DELETE | /users/{id} | 删除用户 | 管理员 |
| 文章 | GET | /articles | 分页查询文章 | 公开 |
| 文章 | GET | /articles/{id} | 文章详情 | 公开 |
| 文章 | GET | /articles/{id}/detail | 文章+评论 | 公开 |
| 文章 | POST | /articles | 发布文章 | 登录 |
| 文章 | PUT | /articles/{id} | 修改文章 | 登录 |
| 文章 | DELETE | /articles/{id} | 删除文章 | 登录 |
| 评论 | GET | /comments | 查询评论 | 公开 |
| 评论 | POST | /comments | 发表评论 | 登录 |
| 评论 | DELETE | /comments/{id} | 删除评论 | 登录 |


📌 项目亮点
✅ 三层架构（Controller → Service → Mapper）清晰分层

✅ 统一响应格式 Result + 全局异常处理

✅ 自定义 @Log 注解 + AOP 实现无侵入式操作日志

✅ Redis 缓存热点数据，降低数据库压力

✅ Spring Security + JWT 实现无状态认证

✅ RBAC 权限模型，区分管理员和普通用户

✅ 分页查询 + 多条件动态筛选

✅ 自动时间填充（createTime / updateTime）