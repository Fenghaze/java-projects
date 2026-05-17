# MyBatis 员工管理系统

基于 Spring Boot 4.0 + MyBatis 的企业级员工管理系统，提供员工信息管理、部门管理、文件上传、登录认证、操作日志审计等功能。

## 技术栈

| 层面 | 技术 | 版本 |
|------|------|------|
| 框架 | Spring Boot | 4.0.6 |
| ORM | MyBatis + mybatis-spring-boot-starter | 4.0.1 |
| 数据库 | MySQL + mysql-connector-j | 8.x |
| 认证 | JWT (jjwt) | 0.12.5 |
| 文件存储 | 阿里云 OSS SDK v2 | 0.4.0 |
| 代码简化 | Lombok | 最新 |
| AOP | Spring Aspects | 最新 |
| 构建工具 | Maven | 3.x+ |
| JDK | Java | 17+ |

## 功能列表

### 员工管理
- 分页查询员工列表，支持按员工号、时间范围筛选
- 新增员工（自动生成员工号 `EMP` + 时间戳）
- 修改员工信息（支持部分字段更新）
- 删除员工（支持批量删除）
- 查看员工详情（含部门信息）

### 部门管理
- 查询全部部门列表
- 按 ID 查询部门详情
- 新增部门
- 删除部门（级联删除该部门下所有员工）

### 登录认证
- 支持员工号、手机号、姓名三种方式登录
- JWT 签发与验证（24小时有效期）
- Filter 级别 Token 校验（除 `/login` 外全部拦截）

### 文件上传
- 员工头像上传至阿里云 OSS
- 支持单文件 10MB、总请求 100MB

### 操作审计
- 通过自定义 `@LogDataOperation` 注解标记需审计的方法
- AOP 自动记录操作人、方法参数、返回值、执行耗时
- 审计日志持久化到数据库

### 性能监控
- AOP 切面监控所有 Service 方法执行时间

## 项目结构

```
mybatis/
├── pom.xml                              # Maven 依赖配置
├── src/
│   ├── main/
│   │   ├── java/com/fenghaze/mybatis/
│   │   │   ├── MybatisApplication.java   # 启动类
│   │   │   ├── aop/
│   │   │   │   ├── LogDataOperation.java       # 自定义注解：操作日志标记
│   │   │   │   ├── TimeAspect.java             # AOP：Service 方法耗时统计
│   │   │   │   └── DataOperationLogAspect.java # AOP：操作日志自动记录
│   │   │   ├── config/
│   │   │   │   └── WebConfig.java              # 注册拦截器
│   │   │   ├── controller/
│   │   │   │   ├── authController.java         # 登录接口
│   │   │   │   ├── DeptControlller.java        # 部门 CRUD
│   │   │   │   ├── EmpController.java          # 员工 CRUD
│   │   │   │   └── UploadController.java       # 文件上传
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java # 全局异常处理
│   │   │   ├── filter/
│   │   │   │   └── AuthFilter.java             # JWT Token 校验过滤器
│   │   │   ├── interceptor/
│   │   │   │   └── AuthCheckInterceptor.java   # 请求日志拦截器
│   │   │   ├── mapper/
│   │   │   │   ├── DeptMapper.java             # 部门数据库操作
│   │   │   │   ├── EmpMapper.java              # 员工数据库操作
│   │   │   │   └── DataOperationLogMapper.java # 操作日志数据库操作
│   │   │   ├── oss/
│   │   │   │   └── OssService.java             # 阿里云 OSS 集成
│   │   │   ├── pojo/
│   │   │   │   ├── Dept.java                   # 部门实体
│   │   │   │   ├── Emp.java                    # 员工实体
│   │   │   │   └── DataOperationLog.java       # 操作日志实体
│   │   │   ├── response/
│   │   │   │   ├── Result.java                 # 统一响应封装
│   │   │   │   └── PageResult.java             # 分页结果封装
│   │   │   └── service/
│   │   │       ├── AuthService.java            # 认证接口
│   │   │       ├── DeptService.java            # 部门业务接口
│   │   │       ├── EmpService.java             # 员工业务接口
│   │   │       ├── DataOperationLogService.java # 操作日志接口
│   │   │       └── impl/
│   │   │           ├── AuthServiceImpl.java
│   │   │           ├── DeptServiceImpl.java
│   │   │           ├── EmpServiceImpl.java
│   │   │           └── DataOperationLogServiceImpl.java
│   │   └── resources/
│   │       ├── application.yml                          # 应用配置
│   │       ├── com/fenghaze/mybatis/mapper/EmpMapper.xml # MyBatis XML 映射
│   │       └── sql/data_operation_log.sql                # 操作日志建表 SQL
│   └── test/java/com/fenghaze/mybatis/
│       └── MybatisApplicationTests.java  # 集成测试
```

## 快速启动

### 前置条件

- JDK 17+
- Maven 3.x
- MySQL 8.x
- 阿里云 OSS 账号（可选，不上传头像可不配置）

### 1. 创建数据库

```sql
CREATE DATABASE IF NOT EXISTS my_test
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;
```

### 2. 导入表结构

```sql
-- 部门表
CREATE TABLE dept (
  dept_id    INT AUTO_INCREMENT PRIMARY KEY,
  dept_name  VARCHAR(50)  NOT NULL,
  dept_loc   VARCHAR(100),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 员工表
CREATE TABLE emp (
  emp_id     INT AUTO_INCREMENT PRIMARY KEY,
  emp_no     VARCHAR(32)  NOT NULL,
  password   VARCHAR(64)  DEFAULT '123456',
  avatar     VARCHAR(255),
  job        VARCHAR(50),
  hiredate   DATE,
  sal        DECIMAL(10,2),
  dept_id    INT,
  start_time DATETIME,
  end_time   DATETIME,
  name       VARCHAR(50),
  age        TINYINT,
  gender     TINYINT,
  phone      VARCHAR(20)
);

-- 操作日志表（见 src/main/resources/sql/data_operation_log.sql）
```

### 3. 修改数据库配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/my_test?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 4. 启动应用

```bash
# 克隆项目后
cd mybatis
mvn spring-boot:run
```

或打包后运行：

```bash
mvn package -DskipTests
java -jar target/mybatis-0.0.1-SNAPSHOT.jar
```

### 5. 访问接口

应用启动后默认运行在 `http://localhost:8080`。

## API 接口文档

### 登录认证

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/login` | 登录（支持员工号/手机号/姓名） |

请求示例：

```json
// POST /login?account=admin&password=123456
// 返回 JWT Token
```

### 部门管理

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/depts` | 查询全部部门 |
| GET | `/api/depts/{id}` | 查询部门详情 |
| POST | `/api/depts` | 新增部门 |
| DELETE | `/api/depts/{id}` | 删除部门（级联删除员工） |

### 员工管理

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/emps` | 分页查询员工列表 |
| GET | `/api/emps/{id}` | 查询员工详情 |
| POST | `/api/emps` | 新增员工 |
| PUT | `/api/emps/{id}` | 部分更新员工信息 |
| DELETE | `/api/emps/{ids}` | 批量删除员工 |

分页查询参数：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| pageNum | Integer | 否 | 页码，默认 1 |
| pageSize | Integer | 否 | 每页条数，默认 10 |
| empNo | String | 否 | 员工号筛选 |
| startTime | LocalDate | 否 | 开始时间 |
| endTime | LocalDate | 否 | 结束时间 |

### 文件上传

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/uploadAvatar` | 上传员工头像到 OSS |

### 通用响应格式

```json
// 成功
{"code": 0, "msg": "success", "data": {...}}

// 失败
{"code": -1, "msg": "错误信息", "data": null}
```

## 配置说明

### JWT 配置（application.yml）

```yaml
jwt:
  secret: LZ92GIRQdcTtxwQQ5zCnIXmaX7jVx5ygO9yfuC828wc=   # HMAC-SHA 密钥（Base64）
  expiration: 86400000                                      # Token 有效期（毫秒，默认24h）
```

### 阿里云 OSS 配置

```yaml
oss:
  region: cn-beijing                  # OSS 地域
  bucket-name: cloud-database-20260506 # 存储桶名称
```

OSS 访问凭证通过环境变量配置：

```bash
# Windows
set ALIBABA_CLOUD_ACCESS_KEY_ID=your_access_key
set ALIBABA_CLOUD_ACCESS_KEY_SECRET=your_access_secret

# Linux/Mac
export ALIBABA_CLOUD_ACCESS_KEY_ID=your_access_key
export ALIBABA_CLOUD_ACCESS_KEY_SECRET=your_access_secret
```

### MyBatis 配置

```yaml
mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # SQL 日志输出到控制台
    map-underscore-to-camel-case: true                     # 下划线转驼峰自动映射
```

## 架构设计要点

### 请求处理链路

```
客户端 → AuthFilter (JWT校验) → AuthCheckInterceptor (日志记录) → Controller → Service → Mapper → DB
                                                                       ↓
                                                              AOP (耗时统计 / 操作日志)
```

### 认证机制

- 登录成功返回 JWT Token，后续请求在 Header 中携带 `token` 字段
- `AuthFilter`（Servlet 过滤器）校验每个请求的 Token 有效性，失效返回 401
- 登录接口 `/login` 不校验 Token

### 事务管理

- 删除部门时同时删除该部门下的所有员工，使用 `@Transactional(rollbackFor = Exception.class)` 保证原子性

### 操作审计

- 在需要记录日志的方法上添加 `@LogDataOperation` 注解
- `DataOperationLogAspect` 通过 AOP 环绕通知自动采集方法执行信息并入库

## 开发指南

### 代码规范

- 控制层使用 `@RestController` + `@RequestMapping`
- 业务层接口与实现分离（`XxxService` + `XxxServiceImpl`）
- Mapper 支持注解和 XML 两种方式，复杂 SQL 使用 XML
- 统一响应使用 `Result` 和 `PageResult` 封装

### 添加新功能

1. 在 `pojo/` 中定义实体类
2. 在 `mapper/` 中创建 Mapper 接口
3. 在 `service/` 中定义接口和实现
4. 在 `controller/` 中创建 REST 接口
5. 如需要审计，在 Service 方法上添加 `@LogDataOperation`

## License

MIT
