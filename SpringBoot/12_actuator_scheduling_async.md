# 🔋 Topic 12: Advanced Features & Caching

Welcome back, advanced developer! In this chapter, we will learn about four powerful enterprise features in Spring Boot: monitoring our system health with **Actuator**, scheduling recurring background tasks with **Scheduler**, running non-blocking tasks asynchronously with **Async**, and boosting database performance using **Redis Caching**.

---

## 🏠 The Big Picture & Real-Life Example

### 🩺 Advanced Features in Action
Imagine you run a massive department store:
1. **Actuator (The Security Control Room)**: You have a dashboard displaying security camera feeds, thermostat temperatures, and electrical grid usages to monitor store health. (Actuator endpoints).
2. **Scheduler (The Automated Cleaning Robot)**: You program a robot to sweep the floors automatically every night at 11:00 PM when the store is closed. (`@Scheduled` cron tasks).
3. **Async (The Shipping Clerk)**: When a customer orders 50 boxes, the cashier doesn't make the customer stand in line for 2 hours while they package them. The cashier hands the request to a Shipping Clerk (**`@Async`**) and immediately checks out the next customer!
4. **Caching (The Sticky Note)**: Customers keep asking: *"Where is the exit?"* Instead of walking to the back of the store to consult the structural blueprints (Database query), you write *"Exit is next to Section A"* on a **Sticky Note (Cache)**. You answer instantly from the note!

---

## 🔬 Let's Look Closer

### 1. Spring Boot Actuator
Actuator exposes production-ready endpoints over HTTP to monitor your application:
* `/actuator/health`: Checks if the app and its database connections are alive.
* `/actuator/metrics`: Shows CPU, memory usage, and garbage collection statistics.
* `/actuator/env`: Displays active properties and environment configurations.

### 2. Task Scheduling (`@Scheduled`)
You can trigger background methods automatically using:
* `fixedDelay = 5000`: Runs 5 seconds after the previous run completes.
* `fixedRate = 5000`: Runs every 5 seconds from the start of the previous run.
* `cron = "0 0 12 * * *"`: Standard 6-field cron pattern (runs every day at 12:00 PM).

### 3. Asynchronous Execution (`@Async`)
Normally, code executes synchronously (blocking the caller thread). Annotating a method with `@Async` runs it on a separate thread from a pool, allowing the caller to continue immediately without waiting.

### 4. Caching with Redis
Caching stores hot database query results in memory (like a Redis server) for fast retrieval:
* **`@Cacheable(value = "users", key = "#id")`**: Before running the method, Spring checks if the result is in Redis. If found, it returns the cached value, bypassing the database entirely.
* **`@CachePut`**: Executes the method and updates the Redis cache with the new result.
* **`@CacheEvict`**: Deletes the specified data from the Redis cache (used when data is deleted).

---

## 💻 Code Sandbox

Let's build an email system containing Scheduled tasks, Async emails, and Redis caching.

### 1. Enable Features: `AppConfig.java`
```java
package com.example;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling // Enables `@Scheduled`
@EnableAsync      // Enables `@Async`
@EnableCaching    // Enables `@Cacheable`
public class AppConfig {
}
```

### 2. The Service: `CustomerService.java`
```java
package com.example;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService {

    private final Map<Long, String> database = new HashMap<>();

    public CustomerService() {
        database.put(1L, "Alice");
        database.put(2L, "Bob");
    }

    // 1. Caching: Results are saved in Redis/Cache memory
    @Cacheable(value = "customers", key = "#id")
    public String getCustomerName(Long id) {
        // Simulate slow database read
        try { Thread.sleep(3000); } catch (InterruptedException e) {}
        System.out.println("--- Querying real database for ID: " + id + " ---");
        return database.get(id);
    }

    // 2. Cache Eviction: Clears cache when data changes
    @CacheEvict(value = "customers", key = "#id")
    public void deleteCustomer(Long id) {
        database.remove(id);
        System.out.println("Deleted customer ID: " + id + ". Cache evicted.");
    }

    // 3. Asynchronous: Method runs on a separate worker thread
    @Async
    public void sendWelcomeEmail(String name) {
        System.out.println("Async thread [" + Thread.currentThread().getName() + "] started sending email...");
        try { Thread.sleep(5000); } catch (InterruptedException e) {} // Simulate delay
        System.out.println("Email sent to: " + name + "!");
    }

    // 4. Scheduling: Background task runs every 10 seconds
    @Scheduled(fixedRate = 10000)
    public void cleanTempFiles() {
        System.out.println("Cron Job: Cleaning temporary system logs...");
    }
}
```

### 3. Controller to trigger: `CustomerController.java`
```java
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public String getCustomer(@PathVariable("id") Long id) {
        long start = System.currentTimeMillis();
        String name = customerService.getCustomerName(id);
        
        // Trigger Async email instantly
        customerService.sendWelcomeEmail(name);
        
        long duration = System.currentTimeMillis() - start;
        return "Customer: " + name + " (Fetched in " + duration + " ms)";
    }
}
```

---

## 🧠 Points to Remember

* Methods annotated with `@Async` must return `void` or `Future<T>` (like `CompletableFuture<T>`).
* To make `@Async` work, you must call the method from a different bean. Internal self-invocation within the same class bypasses the AOP proxy thread wrapper!
* Use `@Cacheable` only for data that is read frequently but rarely updated. Caching highly volatile data wastes memory and leads to stale read bugs.

---

## 📖 Key Definitions

* **Spring Boot Actuator**: A sub-project of Spring Boot that exposes REST endpoints to monitor, audit, and collect metrics about the running application.
* **Task Scheduling**: The automation of running background methods at specific intervals or defined dates.
* **Asynchronous Execution**: A programming model that delegates method executions to separate worker threads, allowing the main program execution to run concurrently without blocking.
* **Caching**: The process of storing copies of database data in temporary memory (like Redis) to accelerate future request speeds.
* **Cache Eviction**: The process of deleting outdated or deleted entries from the cache memory to maintain data synchronization.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is Spring Boot Actuator?**
   * *Answer*: It is a sub-framework that provides built-in HTTP endpoints to monitor and interact with a running Spring Boot application in production.
2. **Name three common Actuator endpoints.**
   * *Answer*: `/actuator/health` (health check), `/actuator/metrics` (CPU/Memory details), and `/actuator/env` (active configurations).
3. **How do you enable Actuator in Spring Boot?**
   * *Answer*: By adding the `spring-boot-starter-actuator` dependency.
4. **Why are most Actuator endpoints disabled by default?**
   * *Answer*: For security reasons, as endpoints like `/env` contain sensitive configurations and passwords.
5. **How do you expose all Actuator endpoints via HTTP properties?**
   * *Answer*: By adding the property `management.endpoints.web.exposure.include=*`.
6. **What is task scheduling in Spring Boot?**
   * *Answer*: It is the ability to automatically execute background methods at set intervals using the `@Scheduled` annotation.
7. **What annotation enables scheduling?**
   * *Answer*: The `@EnableScheduling` annotation placed on a configuration class.
8. **What is the difference between `fixedRate` and `fixedDelay` in `@Scheduled`?**
   * *Answer*: `fixedRate` runs the task at a set interval from the *start* of the previous execution. `fixedDelay` runs the task after a set duration from the *completion* of the previous execution.
9. **What is a Cron Expression?**
    * *Answer*: A string pattern of six or seven fields that defines exact schedules (minute, hour, day, month, etc.) to trigger a task.
10. **What does `@Scheduled(cron = "0 0 * * * *")` mean?**
    * *Answer*: It triggers the task exactly at the start of every hour.
11. **What is the purpose of `@Async`?**
    * *Answer*: It runs the annotated method on a separate thread from a thread pool, allowing the caller to proceed without blocking.
12. **What annotation enables asynchronous method support?**
    * *Answer*: The `@EnableAsync` annotation.
13. **What must an `@Async` method return?**
    * *Answer*: It must return `void` or a wrapper class implementing `Future` (like `CompletableFuture<T>`).
14. **What is Caching?**
    * *Answer*: Storing frequently queried data in memory so subsequent reads are returned instantly without querying the primary database.
15. **What annotation enables caching?**
    * *Answer*: The `@EnableCaching` annotation.
16. **What does the `@Cacheable` annotation do?**
    * *Answer*: It checks the cache memory first. If the key exists, it returns the cached value. If not, it runs the method and saves the output in the cache.
17. **What does `@CachePut` do?**
    * *Answer*: It always runs the method and updates the cache with the new return value.
18. **What is `@CacheEvict` used for?**
    * *Answer*: It removes (evicts) specific key entries from the cache, commonly used when data is deleted or updated.
19. **Name a popular external cache provider that integrates with Spring Boot.**
    * *Answer*: **Redis** (along with Ehcache, Caffeine, and Hazelcast).
20. **What happens if you don't configure an external cache provider?**
    * *Answer*: Spring Boot defaults to `ConcurrentMapCacheManager`, which stores cache in-memory using concurrent hash maps inside JVM memory.

### 🟡 Intermediate Questions (21-40)

21. **Explain the self-invocation issue with `@Async` and `@Scheduled`.**
    * *Answer*: Since Spring uses AOP proxy wrappers to intercept methods and run them asynchronously, calling an `@Async` method from inside the same class bypasses the proxy, running it synchronously instead.
22. **How does `@Scheduled` execution behave if you have multiple tasks but only one thread?**
    * *Answer*: By default, all `@Scheduled` tasks share a single-threaded task scheduler pool. If one task hangs or takes long, it blocks all other scheduled tasks.
23. **How do you configure a thread pool for `@Scheduled` tasks?**
    * *Answer*: By implementing the `SchedulingConfigurer` interface and configuring a custom `ThreadPoolTaskScheduler`.
24. **How do you configure a custom Thread Pool executor for `@Async` tasks?**
    * *Answer*: By defining a bean of type `Executor` (e.g. `ThreadPoolTaskExecutor`) and naming it, then referencing it in `@Async("myExecutor")`.
25. **Explain the purpose of `CompletableFuture.completedFuture(value)`.**
    * *Answer*: It wraps a value in a completed future container, allowing an `@Async` method to return values conforming to the `Future` interface contract.
26. **What is the difference between `/actuator/health` Liveness and Readiness state?**
    * *Answer*: Liveness state checks if the JVM is running. Readiness state checks if the application is fully booted and ready to process incoming traffic.
27. **How do you secure Actuator endpoints?**
    * *Answer*: By configuring Spring Security rules to restrict `/actuator/**` access to users containing specific roles (e.g. `ROLE_ADMIN`).
28. **What is the purpose of `@CacheConfig`?**
    * *Answer*: A class-level annotation used to share common cache configurations, like the cache name, across all methods in that class.
29. **How do you configure Cache Time-to-Live (TTL) globally?**
    * *Answer*: By configuring properties specific to your cache provider, e.g. `spring.data.redis.time-to-live=600000` (10 minutes) for Redis.
30. **Explain `@CacheEvict(allEntries = true)`.**
    * *Answer*: It clears and deletes *all* entries inside the specified cache name, rather than deleting a single key.
31. **What is the cache key generator, and how does it generate keys by default?**
    * *Answer*: A component that generates cache keys. By default, it uses the method parameters. If a method has no params, it returns a constant key.
32. **How do you write conditional caching using `@Cacheable`?**
    * *Answer*: By using the `condition` attribute (runs before execution) or `unless` attribute (checks the returned result): `@Cacheable(value = "users", unless = "#result == null")`.
33. **What is the purpose of `/actuator/loggers`?**
    * *Answer*: It allows developers to view and dynamically modify logging levels (e.g., changing a package log level from INFO to DEBUG) at runtime without restarting.
34. **Explain how `@Scheduled(cron = "0 */5 * * * *")` behaves.**
    * *Answer*: The slash symbol represents increments. This expression triggers the task every 5 minutes.
35. **What is a Cron zone, and how do you specify it?**
    * *Answer*: It specifies the timezone for the cron trigger, e.g., `@Scheduled(cron = "...", zone = "GMT")`.
36. **Explain what happens during a Cache Stampede (Thundering Herd) problem.**
    * *Answer*: It occurs when a hot cache key expires. Multiple client threads detect the cache miss simultaneously and all query the database at once, potentially crashing the database.
37. **How do you prevent cache stampedes in Spring Boot?**
    * *Answer*: By enabling the `sync = true` attribute inside `@Cacheable`. This locks the cache key, forcing only one thread to load from the database while others wait for the cache update.
38. **What is the difference between `@Async` and using Java's Parallel Streams?**
    * *Answer*: `@Async` delegates tasks to a dedicated pool for separate execution. Parallel Streams use the shared JVM-wide `ForkJoinPool.commonPool()`, which can block other parts of the application if starved.
39. **How can you check if a bean method is running asynchronously in a log file?**
    * *Answer*: By printing the thread name in the log pattern. Async methods will display thread pool names (e.g. `task-1`) instead of Tomcat thread names (e.g. `http-nio-8080-exec-1`).
40. **How does Spring Boot resolve the active cache manager if multiple cache libraries are on the classpath?**
    * *Answer*: Spring Boot uses a pre-defined order to auto-configure the CacheManager. It checks for Redis, then EhCache, then Hazelcast, and falls back to Simple Map caching.

### 🔴 Advanced Questions (41-50)

41. **Explain how `@Cacheable` works under the hood using AOP.**
    * *Answer*: Spring creates a proxy for the cache-annotated bean. The proxy intercepts the method call, computes the cache key, queries the `CacheManager`, returns the cached value if present, or delegates to the real method and caches the result.
42. **What is the role of `AsyncExecutionInterceptor` in Spring?**
    * *Answer*: It is the internal interceptor class that intercepts calls to `@Async` methods and submits the target execution task to a `TaskExecutor` thread pool.
43. **How does the JIT compiler optimize recurring `@Scheduled` executions?**
    * *Answer*: The JIT compiler compiles the internal task scheduler invocation loops and method pointers into direct machine code, minimizing scheduling overhead and latency.
44. **What is Micrometer, and how does it relate to Spring Boot Actuator?**
    * *Answer*: Micrometer is an application metrics facade (like SLF4J for logging). Actuator uses Micrometer to collect metrics and export them to monitoring systems like Prometheus or Datadog.
45. **Explain the purpose of custom Actuator endpoints.**
    * *Answer*: You can create custom endpoints by annotating a class with `@Endpoint(id = "myEndpoint")` and marking methods with `@ReadOperation` (GET) or `@WriteOperation` (POST) to expose internal configs.
46. **What is the difference between JDK Serializer and Jackson Serializer for Redis caching?**
    * *Answer*: JDK Serializer converts objects into binary format (hard for humans to read, Java-dependent). Jackson Serializer converts objects into standard JSON string representation, which is readable and cross-platform.
47. **How would you configure a fallback CacheManager if your primary Redis server goes down?**
    * *Answer*: By configuring a `CompositeCacheManager` that groups multiple managers (like RedisCacheManager and SimpleCacheManager) together, resolving cache misses sequentially.
48. **Explain the purpose of `ShedLock` library in clustered environments.**
    * *Answer*: In a clustered environment (multiple servers running the same JAR), a `@Scheduled` task would run on every server simultaneously. `ShedLock` uses a shared database table to lock the task, ensuring it runs on only one node at a time.
49. **How does Spring handle transaction synchronization with Caching?**
    * *Answer*: If a transactional method writes data and evicts cache, the cache eviction should run only *after* the transaction successfully commits. This is solved by using `TransactionSynchronizationManager` or a custom transaction-aware cache manager.
50. **What is the role of `DelegatingSecurityContextExecutor` in asynchronous security?**
    * *Answer*: It is a wrapper executor that automatically propagates Spring Security's `SecurityContext` (user authentication) from the parent thread to the asynchronous thread, enabling `@Async` methods to perform role validation.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 11: Testing in Spring Boot](11_spring_boot_testing.md)
* **Next Chapter**: [👉 Topic 13: Microservices Architecture & Resilience](13_microservices_basics.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
