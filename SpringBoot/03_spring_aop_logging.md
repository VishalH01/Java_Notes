# 🛡️ Topic 03: Aspect-Oriented Programming (AOP) & Logging

Welcome back! In this chapter, we will learn about **Aspect-Oriented Programming (AOP)** and **Logging**. AOP is a powerful technique that helps us separate secondary tasks (like logging, security checks, and timing metrics) from our main business logic. We will also learn how to log messages professionally using SLF4J and Logback.

---

## 🏠 The Big Picture & Real-Life Example

### 💂 The Bank Clerk and the Security Guard (AOP)
Imagine a bank clerk whose only job is to hand cash to customers.
* **Without AOP**: The clerk has to write down the customer's name in a diary, check if they have a passport, hand over the money, and then write down the time the transaction finished. The clerk spends more time doing security and diary entries than counting cash!
* **With AOP**: The clerk focusing 100% on counting cash. A **Security Guard** (the **Aspect**) stands at the counter. 
  * Before the clerk opens their drawer, the guard checks the customer's ID (**Before Advice**).
  * While the clerk counts the money, the guard starts a stopwatch and logs the duration (**Around Advice**).
  * After the transaction is finished, the guard signs the logbook (**After Advice**).

The clerk does not have to change how they count cash. The guard intercepts the work automatically!

---

## 🔬 Let's Look Closer

### 1. Core AOP Jargon
* **Aspect**: The class containing the secondary code (like the Security Guard).
* **Join Point**: A specific point during execution where we can plug in the aspect (in Spring, this is always a method call).
* **Pointcut**: A filter expression that tells Spring *which* specific methods the aspect should guard (e.g., "all methods in the service package").
* **Advice**: The actual code to execute (e.g., write a log). Types of Advice:
  * `@Before`: Runs *before* the target method execution.
  * `@AfterReturning`: Runs *after* the method successfully returns a value.
  * `@AfterThrowing`: Runs if the method throws an exception.
  * `@After`: Runs *after* the method finishes (regardless of success or failure).
  * `@Around` (Most Powerful): Surrounds the method call, allowing you to control whether to execute the method, edit the arguments, or return a custom value.

```mermaid
graph TD
    Client[Client Code] -->|Calls Method| Proxy[AOP Proxy Class]
    Proxy -->|@Before Advice| Aspect[Aspect Code]
    Proxy -->|Execute| Target[Real Service Method]
    Proxy -->|@After Advice| Aspect
```

### 2. Logging in Spring Boot
Instead of using `System.out.println()`, professional developers use a logging framework.
* **SLF4J (Simple Logging Facade for Java)**: A unified abstraction interface for logging.
* **Logback**: The default implementation framework used by Spring Boot to write logs to consoles or files.
* **Logging Levels**: `TRACE` < `DEBUG` < `INFO` < `WARN` < `ERROR`. By default, Spring Boot displays `INFO` and above.

---

## 💻 Code Sandbox

Let's build a service that handles bank transfers, and guard it using AOP to log execution times and security checks.

### 1. Enable AOP: `AppConfig.java`
```java
package com.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example")
@EnableAspectJAutoProxy // Enables Spring's Aspect helper
public class AppConfig {
}
```

### 2. The Core Service: `BankService.java`
```java
package com.example;

import org.springframework.stereotype.Service;

@Service
public class BankService {
    public void transferMoney(String sender, String receiver, double amount) {
        // Business logic only!
        System.out.println("Processing: Transferring $" + amount + " from " + sender + " to " + receiver);
    }
}
```

### 3. The Aspect: `LoggingAspect.java`
```java
package com.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component // Must be a Spring bean
public class LoggingAspect {

    // SLF4J Logger Instance
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // 1. Pointcut: Target all methods in BankService
    @Pointcut("execution(* com.example.BankService.*(..))")
    public void bankServiceMethods() {}

    // 2. Before Advice: Check user details
    @Before("bankServiceMethods()")
    public void checkSecurity(JoinPoint joinPoint) {
        logger.info("[AOP BEFORE] Verifying security credentials for method: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        logger.info("[AOP BEFORE] Sender: {}, Amount: {}", args[0], args[2]);
    }

    // 3. Around Advice: Measures execution speed
    @Around("bankServiceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("[AOP AROUND] Starting stopwatch for: {}", joinPoint.getSignature().getName());
        long start = System.currentTimeMillis();

        // Let the actual method run!
        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        logger.info("[AOP AROUND] Completed {} in {} ms", joinPoint.getSignature().getName(), duration);
        
        return result;
    }
}
```

### 4. Running the Application: `Main.java`
```java
package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class);

        BankService bank = context.getBean(BankService.class);
        
        // This call will trigger the logging and security aspects automatically!
        bank.transferMoney("Bob", "Charlie", 250.0);

        context.close();
    }
}
```

---

## 🧠 Points to Remember

* AOP separates **cross-cutting concerns** (concerns that affect multiple classes like transaction management, security, and logging) from the core business logic.
* Spring AOP is built on **runtime dynamic proxies**. It intercepts method invocations at runtime by wrapping your bean in a proxy.
* Use `@Around` advice carefully. You must remember to call `proceed()` inside the advice, otherwise the target method will never execute!
* Always prefer **SLF4J** interfaces over hardcoded logger implementations so you can switch logging frameworks without editing code.

---

## 📖 Key Definitions

* **Aspect-Oriented Programming (AOP)**: A programming paradigm that enables separating cross-cutting concerns (tasks like security or logging) from the core application logic by injecting code snippets into existing classes.
* **Pointcut**: An expression that filters and matches specific method execution points (Join Points) within the application code where an Aspect should be applied.
* **Advice**: The actual code block executed by an Aspect at a matching Join Point (can run before, after, or around the target method).
* **SLF4J**: An abstraction facade for logging frameworks that allows developers to write standard logging calls and swap backing engines (like Logback or Log4j) seamlessly.
* **AspectJ**: An advanced, full-featured AOP framework for Java that supports compile-time and class-load-time weaving (Spring AOP uses AspectJ annotations but executes via runtime proxies).

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is Aspect-Oriented Programming (AOP)?**
   * *Answer*: AOP is a programming style that allows separating cross-cutting concerns (like logging or security) from the main business logic using reusable blocks of code called Aspects.
2. **What is a cross-cutting concern?**
   * *Answer*: A feature or task that affects multiple layers of an application (e.g., logging, transaction management, security, caching).
3. **What is an Aspect?**
   * *Answer*: An Aspect is a modular class in AOP that encapsulates a cross-cutting concern (combining Pointcuts and Advices).
4. **What is a Join Point?**
   * *Answer*: A candidate point in the program execution where an Aspect can be plugged in (e.g., method calls, field access, exception handlers). In Spring AOP, it is always a method execution.
5. **What is a Pointcut?**
   * *Answer*: An expression that defines a subset of Join Points where the Advice code should execute.
6. **What is Advice in AOP?**
   * *Answer*: Advice is the action or code executed by an Aspect at a particular Join Point (e.g., logging a message before a method runs).
7. **Name the five types of Advice in Spring AOP.**
   * *Answer*: `@Before`, `@AfterReturning`, `@AfterThrowing`, `@After` (finally), and `@Around`.
8. **What does the `@Around` advice do?**
   * *Answer*: It surrounds the target method invocation. It has full control over when to run the target method, whether to run it at all, and what value to return.
9. **How does Spring AOP implement aspects under the hood?**
   * *Answer*: By using runtime **Dynamic Proxies** (JDK Dynamic proxies or CGLIB proxies) to intercept calls to target beans.
10. **What is Weaving in AOP?**
    * *Answer*: The process of linking Aspects with other application types to create an advised object (can happen at compile-time, load-time, or runtime).
11. **When does Weaving happen in Spring AOP?**
    * *Answer*: In Spring AOP, weaving happens at **Runtime** using dynamic proxy wrappers.
12. **What is SLF4J?**
    * *Answer*: Simple Logging Facade for Java. It is an interface facade that acts as a wrapper for concrete logging frameworks (like Logback or Log4j).
13. **Why should you use SLF4J instead of Logback directly?**
    * *Answer*: It decouples your code from the underlying logging library. You can swap the logging engine (e.g., from Logback to Log4j2) by editing dependencies without changing Java code.
14. **What is the default logging framework in Spring Boot?**
    * *Answer*: **Logback** is the default logging framework pre-configured in Spring Boot.
15. **Name the logging levels in order of severity (lowest to highest).**
    * *Answer*: `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`.
16. **What is the default logging level in Spring Boot?**
    * *Answer*: The default logging level is `INFO`. Lower logs (`DEBUG`, `TRACE`) are ignored unless explicitly enabled.
17. **How can you enable DEBUG logging in Spring Boot's application properties?**
    * *Answer*: By adding the property `logging.level.root=DEBUG` or `logging.level.com.example=DEBUG`.
18. **What annotation enables AOP proxy generation in a Spring config?**
    * *Answer*: The `@EnableAspectJAutoProxy` annotation.
19. **What is a ProceedingJoinPoint?**
    * *Answer*: A parameter passed to `@Around` advice containing the `proceed()` method, which is executed to invoke the target method.
20. **Can you execute AOP on static methods?**
    * *Answer*: No, Spring AOP only supports method intercepting on Spring beans and cannot intercept static methods because they belong to the class, not object instances.

### 🟡 Intermediate Questions (21-40)

21. **Compare Spring AOP and AspectJ.**
    * *Answer*: Spring AOP is simpler and uses runtime dynamic proxies, supporting only method execution join points. AspectJ is a full-featured AOP framework that uses compile-time or class-load-time weaving and supports all join points (field access, constructors, etc.).
22. **What is the difference between JDK Dynamic Proxy and CGLIB proxy?**
    * *Answer*: JDK Dynamic proxy is used when the target bean implements at least one interface (uses reflection). CGLIB proxy is used when the bean does not implement any interface (subclasses the bean at runtime).
23. **What is the difference between `@AfterReturning` and `@After`?**
    * *Answer*: `@AfterReturning` runs only when the target method exits successfully without throwing an exception. `@After` runs in a `finally` block, executing regardless of whether the method succeeded or failed.
24. **How do you access method arguments inside a `@Before` advice?**
    * *Answer*: By declaring a `JoinPoint` parameter in the advice method and calling `joinPoint.getArgs()`.
25. **What pointcut designator matches all methods in a specific package?**
    * *Answer*: `within(com.example.service..*)`.
26. **Explain the execution pointcut expression: `execution(public * com.example.service.*.*(..))`**
    * *Answer*: Matches any public method (`public *`), in any class inside `com.example.service` package, with any method name (`.*.*`), accepting any number of arguments (`(..)`).
27. **What happens if you do not call `proceed()` inside an `@Around` advice?**
    * *Answer*: The target method will be blocked and will never execute, which might cause the application flow to halt or return null.
28. **How can you order multiple Aspects guarding the same method?**
    * *Answer*: By annotating the Aspect classes with `@Order(value)` or implementing the `Ordered` interface. Lower order values run first.
29. **What is the purpose of the `@AfterThrowing` annotation?**
    * *Answer*: It executes code when a method throws an exception. You can access the thrown exception using the `throwing` attribute of the annotation.
30. **Explain how to log exceptions using `@AfterThrowing`.**
    * *Answer*: Use `@AfterThrowing(pointcut = "...", throwing = "ex")` and declare `Exception ex` in the advice method signature to log the stack trace.
31. **What is Self-Invocation in Spring AOP, and why does it bypass aspects?**
    * *Answer*: Self-invocation is when one method inside a bean calls another method in the same bean (e.g., `this.methodB()`). Since the call bypasses the external proxy wrapper, the aspect on `methodB` is not triggered.
32. **How can you solve the Self-Invocation problem?**
    * *Answer*: By refactoring the methods into separate beans, injecting the proxy bean into itself (Self-Injection) using `@Autowired`, or getting the current proxy using `AopContext.currentProxy()`.
33. **What is the difference between log formats: `logger.info("Value: " + val)` and `logger.info("Value: {}", val)`?**
    * *Answer*: The first version uses string concatenation, which creates a new string object in memory even if INFO logging is disabled. The second version uses parameterized placeholders and only formats the string if the log level is active, which is much faster.
34. **How do you write logs to a file in Spring Boot?**
    * *Answer*: By setting `logging.file.name=app.log` or `logging.file.path=/var/logs` in your `application.properties` file.
35. **What is MDC (Mapped Diagnostic Context) in logging?**
    * *Answer*: A feature in logging frameworks (like Logback) that allows storing contextual information (like User ID or Correlation ID) in a ThreadLocal map, making it easy to print tracking IDs in every log statement.
36. **Explain dynamic proxy target source in Spring.**
    * *Answer*: The backing bean instance that the proxy delegates target calls to. By default, it is a single singleton bean instance, but it can be custom pool sources.
37. **What is Load-Time Weaving (LTW) in Spring?**
    * *Answer*: The process of executing AspectJ weaving at class-loading time using a JVM agent, allowing aspects to be woven into objects that are not managed as Spring beans.
38. **What does the Pointcut designator `@annotation(...)` do?**
    * *Answer*: It filters and matches only those methods that are annotated with a specific custom annotation (e.g., `@annotation(com.example.LogExecutionTime)`).
39. **How does Spring AOP handle returned values in `@AfterReturning`?**
    * *Answer*: You can capture the return value using the `returning` attribute, e.g., `@AfterReturning(pointcut = "...", returning = "result")`, to log or inspect the result of the method.
40. **Does Spring AOP support pointcuts matching field modifications?**
    * *Answer*: No, Spring AOP only supports method execution join points. If you need to intercept variable updates, you must use the full AspectJ framework.

### 🔴 Advanced Questions (41-50)

41. **Explain the performance overhead of Spring AOP vs. AspectJ Weaving.**
    * *Answer*: Spring AOP creates proxy wrappers and uses reflection-based method invocations at runtime, adding small method-call overhead. AspectJ modifies the bytecode directly (compile-time or load-time), resulting in compile-time weaving and zero runtime proxy overhead, making it faster.
42. **How does CGLIB proxy handle `final` classes or `final` methods?**
    * *Answer*: CGLIB subclasses the target class to create the proxy. Since `final` classes cannot be subclassed and `final` methods cannot be overridden, CGLIB cannot apply aspects to them and will throw a warning or fail to proxy.
43. **How does `ProxyFactory` programmatically build aspects in Spring?**
    * *Answer*: `ProxyFactory` is Spring's programmatic API for proxy generation. You pass the target object, add Advisor implementations (Advice + Pointcut), and call `getProxy()` to create an advised object.
44. **What is an Advisor in Spring AOP?**
    * *Answer*: An Advisor is an internal Spring container abstraction that combines a single Pointcut with a single Advice, mapping them together.
45. **How does SLF4J handle multi-threaded log writing safely?**
    * *Answer*: SLF4J delegates thread-safety to the underlying framework (like Logback), which uses synchronized blocks, lock-free ring buffers (Logback AsyncAppender), or local threads to guarantee logs are written safely.
46. **How would you trace asynchronous thread logs using MDC?**
    * *Answer*: Since MDC uses `ThreadLocal` behind the scenes, properties do not copy automatically to new threads. You must capture the MDC context map before spawning a thread and copy it inside the new thread context using `MDC.setContextMap(context)`.
47. **What is the purpose of `org.springframework.aop.framework.Advised` interface?**
    * *Answer*: Every proxy created by Spring implements the `Advised` interface. You can cast any Spring proxy to `Advised` at runtime to query its active advisors or change the target source dynamically.
48. **Explain the role of `AnnotationAwareAspectJAutoProxyCreator`.**
    * *Answer*: It is a special bean post-processor registered by `@EnableAspectJAutoProxy` that automatically scans the context for `@Aspect` beans, matches them to candidate target beans, and wraps them in proxies.
49. **How would you bypass CGLIB and force JDK Dynamic Proxying in Spring AOP?**
    * *Answer*: By configuring `@EnableAspectJAutoProxy(proxyTargetClass = false)`. However, if your bean doesn't implement any interfaces, Spring will fallback to CGLIB anyway.
50. **How would you write a custom annotation to measure method execution time using AOP?**
    * *Answer*: (1) Create an interface `@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME) public @interface TimeIt {}`. (2) Write an Aspect with `@Around("@annotation(com.example.TimeIt)")` that measures and prints method execution duration.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 02: Spring Core Annotations & Configuration](02_spring_annotations_configuration.md)
* **Next Chapter**: [👉 Topic 04: Introduction to Spring Boot](04_intro_to_spring_boot.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
