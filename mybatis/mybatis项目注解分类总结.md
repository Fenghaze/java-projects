# 当前项目注解分类总结

## 一、组件注册（将类交给 Spring 管理）

| 注解 | 文件 | 作用 | 原理 |
|------|------|------|------|
| `@SpringBootApplication` | `MybatisApplication` | 启动类组合注解 | = `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`，Spring Boot 通过 `@EnableAutoConfiguration` 加载 `META-INF/spring.factories` 中的自动配置类 |
| `@Configuration` | `WebConfig` | 标记配置类 | Spring 对该类创建 CGLIB 代理，确保 `@Bean` 方法返回单例，用于声明配置 |
| `@Component` | `AuthCheckInterceptor`, `AuthFilter`, `TimeAspect`, `DataOperationLogAspect` | 通用组件 | Spring 扫描到后实例化为 Bean，放入 IoC 容器 |
| `@Service` | `OssService`, `AuthServiceImpl`, `DeptServiceImpl`, `EmpServiceImpl`, `DataOperationLogServiceImpl` | 业务层 | 语义化 `@Component`，功能相同，用于 Service 层 |
| `@RestController` | `UploadController`, `EmpController`, `authController`, `DeptControlller` | REST 控制器 | = `@Controller` + `@ResponseBody`，方法返回值自动序列化为 JSON |
| `@MapperScan` | `MybatisApplication` | Mapper 扫描 | 指定扫描 `@Mapper` 接口的包路径，MyBatis 会为每个接口生成代理实现 |

---

## 二、依赖注入

| 注解 | 作用 | 原理 |
|------|------|------|
| `@Autowired` | 自动注入依赖 | Spring 通过 BeanFactory 查找类型匹配的 Bean，按 **type → qualifier → name** 顺序匹配，注入到字段/构造器/方法 |
| `@Value` | 注入配置属性值 | `Environment` 抽象从 `application.yml/properties` 读取属性值，支持 SpEL 表达式 |

---

## 三、Web 请求映射（MVC）

| 注解 | 作用 | 文件示例 |
|------|------|----------|
| `@RequestMapping("/api")` | 类/方法级别基础路径映射 | `UploadController`, `EmpController` |
| `@GetMapping` | 处理 GET 请求 | 查询操作 |
| `@PostMapping` | 处理 POST 请求 | 新增操作 |
| `@PutMapping` | 处理 PUT 请求 | 更新操作 |
| `@DeleteMapping` | 处理 DELETE 请求 | 删除操作 |

**原理**：Spring 在 `RequestMappingHandlerMapping` 初始化时，扫描 `@RequestMapping` 及其派生注解，建立 **URL + HTTP 方法 → 处理器方法** 的映射表，请求到达时根据路径和方法匹配调用。

---

## 四、控制器参数绑定

| 注解 | 作用 |
|------|------|
| `@RequestParam` | 绑定查询参数到方法参数 |
| `@PathVariable` | 绑定 URL 路径模板变量（如 `/emps/{id}`） |
| `@RequestBody` | 绑定请求体 JSON/XML 到对象 |
| `@DateTimeFormat` | 格式化日期字符串为 `LocalDate`/`Date` |

**原理**：`RequestMappingHandlerAdapter` 执行目标方法前，注册的参数解析器（`HandlerMethodArgumentResolver`）根据注解从 `HttpServletRequest` 中提取数据并转换类型。

---

## 五、异常处理

| 注解 | 作用 | 原理 |
|------|------|------|
| `@RestControllerAdvice` | 全局异常拦截器 | = `@ControllerAdvice` + `@ResponseBody`，通过 AOP 在控制器执行前后切入 |
| `@ExceptionHandler(Exception.class)` | 处理指定异常 | Spring 根据异常类型匹配最近的 `@ExceptionHandler` 方法执行 |

---

## 六、AOP 切面编程

| 注解 | 文件 | 作用 |
|------|------|------|
| `@Aspect` | `TimeAspect`, `DataOperationLogAspect` | 标记切面类 |
| `@Around` | 同上 | 环绕通知，方法执行前后插入逻辑 |

**原理**：Spring AOP 基于**动态代理**——有接口用 JDK 动态代理，无接口用 CGLIB 代理。`@Around` 定义切点表达式（如 `execution(* service..*(..))`），匹配的方法调用会被代理拦截，在代理逻辑中执行切面代码。

---

## 七、自定义注解

| 注解 | 文件 | 作用 |
|------|------|------|
| `@LogDataOperation` | `LogDataOperation.java` | 自定义注解，标记需要记录操作日志的方法 |
| `@Target(METHOD)` | 元注解 | 限制注解只能用在方法上 |
| `@Retention(RUNTIME)` | 元注解 | 注解保留到运行时，支持反射读取 |

**原理**：`DataOperationLogAspect` 的 `@Around("@annotation(com.fenghaze.mybatis.aop.LogDataOperation)")` 匹配所有标注了 `@LogDataOperation` 的方法，在执行时通过反射读取注解信息，实现日志记录。

---

## 八、MyBatis 数据访问

| 注解 | 作用 | 原理 |
|------|------|------|
| `@Mapper` | 标记 MyBatis Mapper 接口 | MyBatis 为其创建 JDK 动态代理 |
| `@Select`/`@Insert`/`@Update`/`@Delete` | SQL 语句映射 | 代理方法执行时，从注解中读取 SQL 并交由 `SqlSession` 执行 |
| `@Param` | 绑定命名参数 | 解决多参数问题，将 Java 参数名映射到 SQL 中的 `#{name}` |
| `@Options(useGeneratedKeys=true)` | 获取自增主键 | 执行 INSERT 后通过 JDBC `getGeneratedKeys()` 回写主键 |

---

## 九、Lombok（编译期代码生成）

| 注解 | 作用 | 原理 |
|------|------|------|
| `@Data` | = `@Getter` + `@Setter` + `@ToString` + `@EqualsAndHashCode` + `@RequiredArgsConstructo` | 编译期通过注解处理器（APT）生成字节码 |
| `@AllArgsConstructor` | 生成全参构造器 | 同上 |
| `@NoArgsConstructor` | 生成无参构造器 | 同上 |
| `@Slf4j` | 生成 `log` 字段 | 自动添加 `private static final Logger log = LoggerFactory.getLogger(...)` |

---

## 十、其他

| 注解 | 文件 | 作用 |
|------|------|------|
| `@Transactional` | `DeptServiceImpl` | 声明式事务管理，Spring 通过 AOP 代理，在方法前后开启/提交/回滚事务 |
| `@PostConstruct` | `OssService` | Bean 初始化完成后执行，用于初始化 OSS 客户端 |
| `@PreDestroy` | `OssService` | Bean 销毁前执行，用于关闭资源 |
| `@ServletComponentScan` | 启动类 | 扫描 `@WebFilter`/`@WebServlet`/`@WebListener` 等 Servlet 原生组件 |
| `@Override` | 各处 | 编译期校验是否覆写父类方法 |
| `@SpringBootTest` | 测试类 | 加载完整 Spring 上下文进行集成测试 |
| `@Test` | 测试类 | JUnit 标记测试方法 |
| `@Nullable` | `AuthCheckInterceptor` | 标记参数/返回值允许为 null（编译期检查辅助） |