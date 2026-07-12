# 🧪 Topic 11: Testing in Spring Boot

Welcome back, quality assurance engineer! In this chapter, we will learn how to write automated tests for our Spring Boot applications. Writing tests ensures that your application behaves correctly, even when you change or add new features. We will master **Unit Testing** in isolation using **Mockito** and **Integration Testing** using **`@SpringBootTest`** and **`@WebMvcTest`**.

---

## 🏠 The Big Picture & Real-Life Example

### ✈️ The Flight Simulator and The Real Test Flight (Unit vs. Integration Testing)
Imagine you are building a commercial airplane:
* **Unit Testing (The Flight Simulator)**: You want to test if the steering wheel responds correctly when turned left. You don't want to buy fuel, hire a crew, and fly a real plane into a storm just to test a wheel! Instead, you wire the steering wheel to a **Flight Simulator (Mockito)**. You mock the engine, mock the wind, and mock the wings. You test the wheel in absolute isolation.
* **Integration Testing (The Test Flight)**: Once you confirm every single part (wheel, engine, wings) works in isolation, you assemble the entire plane, fill it with fuel, start the engine, and fly it on the runway (**`@SpringBootTest`**). This checks if the fuel line actually feeds the engine and the engine actually spins the wings.

---

## 🔬 Let's Look Closer

### 1. Mocking with Mockito
When unit-testing a Service class, we want to isolate it from the database (Repository). We do this by creating a **Mock** (a fake copy) of the repository.
* **`@Mock`**: Creates a fake mock instance of a class.
* **`@InjectMocks`**: Instantiates the test class and automatically injects all `@Mock` fields into its constructor.
* **`Mockito.when(...).thenReturn(...)`**: Configures the mock bean to return a pre-defined value when a specific method is called.

### 2. Integration Testing in Spring Boot
* **`@SpringBootTest`**: Starts the complete Spring container (`ApplicationContext`) and connects all beans, databases, and servers. Used for full-blown integration tests.
* **`@WebMvcTest(MyController.class)`**: A specialized annotation that starts *only* the web layer (DispatcherServlet, Controllers). It ignores databases and services, making controller unit-tests super fast.
* **`@MockBean`**: Used inside Spring integration tests to replace a real container bean with a Mockito mock.

---

## 💻 Code Sandbox

Let's write tests for a standard Service and Controller layer.

### 1. The Service Unit Test: `UserServiceTest.java`
```java
package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito annotation processing
public class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Faked dependency

    @InjectMocks
    private UserService userService; // Target test object

    @Test
    public void testGetUserGreeting_Success() {
        // 1. Arrange: Tell the fake repository what to return
        Mockito.when(userRepository.fetchUserEmail("alice"))
                .thenReturn(Optional.of("alice@gmail.com"));

        // 2. Act: Run the service method
        String greeting = userService.getUserGreeting("alice");

        // 3. Assert: Verify the output matches
        assertEquals("User email is: alice@gmail.com", greeting);
    }

    @Test
    public void testGetUserGreeting_NotFound() {
        // 1. Arrange: Return empty Optional
        Mockito.when(userRepository.fetchUserEmail("unknown"))
                .thenReturn(Optional.empty());

        // 2. Act & Assert: Check if it throws Exception
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserGreeting("unknown");
        });
    }
}
```

### 2. The Controller Web Layer Test: `UserControllerTest.java`
```java
package com.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // Boots only the MVC web filter layer
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simulates HTTP requests

    @MockBean
    private UserService userService; // Mock injected into active Spring Web Context

    @Test
    public void testGetGreetingEndpoint_Success() throws Exception {
        // Arrange
        Mockito.when(userService.getUserGreeting("alice"))
               .thenReturn("User email is: alice@gmail.com");

        // Act & Assert: Simulate GET request and verify response
        mockMvc.perform(get("/api/users/alice")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 OK
                .andExpect(content().string("User email is: alice@gmail.com"));
    }
}
```

---

## 🧠 Points to Remember

* **Unit Tests** do not boot the Spring container. They are plain Java classes powered by JUnit and Mockito, running in milliseconds.
* **Integration Tests** boot the container. They take longer to start but test real configurations, profiles, database links, and networking.
* Use **`MockMvc`** to test your web layer endpoints without launching a real Tomcat network server port. This speeds up API validation significantly.
* Test your code using the **AAA Pattern**: **A**rrange (prepare inputs/mocks), **A**ct (execute method), **A**ssert (verify results).

---

## 📖 Key Definitions

* **Unit Testing**: A testing level where individual, small units of code (typically single methods or classes) are verified in complete isolation from external resources.
* **Integration Testing**: A testing level designed to verify that different modules, databases, and configurations of an application work together correctly.
* **Mockito**: A popular open-source Java mocking framework used to create mock objects and stub responses in unit tests.
* **MockMvc**: A Spring testing class that enables testing controllers by simulating HTTP requests and validating response payloads without starting a servlet container.
* **MockBean**: A Spring Boot annotation used to declare a Mockito mock bean that automatically replaces any existing real bean of the same type in the ApplicationContext.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is the difference between Unit Testing and Integration Testing?**
   * *Answer*: Unit testing verifies a single class in isolation using mocks, running very fast. Integration testing verifies multiple modules and configurations together with a running Spring container.
2. **What is JUnit 5?**
   * *Answer*: It is the standard testing framework for Java applications, used to run test suites and execute assertions.
3. **What is Mockito?**
   * *Answer*: An open-source Java framework used to create mock (fake) objects to isolate classes under test.
4. **What does the `@Test` annotation do?**
   * *Answer*: It marks a method as a test method that should be run by the test runner.
5. **What does `@SpringBootTest` do?**
   * *Answer*: It is a class-level annotation that starts the complete Spring ApplicationContext for integration tests.
6. **What is the purpose of `@Mock`?**
   * *Answer*: It is a Mockito annotation used to create a fake mock instance of a class.
7. **What is `@InjectMocks`?**
   * *Answer*: It is a Mockito annotation that instantiates the test object and automatically injects all `@Mock` fields into it.
8. **What does `@MockBean` do in Spring Boot tests?**
   * *Answer*: It creates a Mockito mock and registers it inside Spring's active ApplicationContext, replacing any real bean of the same type.
9. **Explain `@WebMvcTest`.**
   * *Answer*: An annotation that boots only the Spring MVC web layer (controllers, filters, interceptors) for fast testing of API endpoints.
10. **What is MockMvc?**
    * *Answer*: A class that allows developers to simulate HTTP requests (GET, POST, etc.) and assert responses without booting Tomcat.
11. **What is the AAA testing pattern?**
    * *Answer*: **Arrange** (set up test data and mocks), **Act** (call the target method), and **Assert** (verify the outputs match expectations).
12. **How do you assert that a method call throws an exception?**
    * *Answer*: Using JUnit 5's `assertThrows(Exception.class, executable)` assertion.
13. **How do you assert that two values are equal?**
    * *Answer*: Using JUnit 5's `assertEquals(expected, actual)` assertion.
14. **What is the `@BeforeEach` annotation used for?**
    * *Answer*: It runs the annotated method before *each* individual test method in the class, commonly used to reset test data.
15. **What is the `@AfterEach` annotation used for?**
    * *Answer*: It runs the annotated method after *each* test completes, commonly used to clean up temp databases or files.
16. **How do you verify if a mock method was called a specific number of times?**
    * *Answer*: By calling Mockito's `verify(mock, times(n)).method()` checker.
17. **What is the purpose of `@ExtendWith(MockitoExtension.class)`?**
    * *Answer*: It integrates Mockito with JUnit 5, initializing all `@Mock` and `@InjectMocks` fields automatically.
18. **How do you temporarily disable or skip a test in JUnit 5?**
    * *Answer*: By annotating the test method or class with `@Disabled`.
19. **What does `status().isOk()` verify in MockMvc?**
    * *Answer*: It asserts that the controller returned an HTTP status code **200 OK**.
20. **What is the difference between `@Mock` and `@MockBean`?**
    * *Answer*: `@Mock` is a pure Mockito annotation running outside the Spring container. `@MockBean` is a Spring Boot annotation that registers the mock inside the Spring container context.

### 🟡 Intermediate Questions (21-40)

21. **Explain the difference between Mocking and Stubbing.**
    * *Answer*: Mocking is creating a fake class interface. Stubbing is defining the behavior of that fake class (e.g. telling it to return `"value"` when called).
22. **What does `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)` do?**
    * *Answer*: It boots the complete application container and starts a real Tomcat web server on a random available port, useful for testing HTTP clients.
23. **What is `TestRestTemplate`?**
    * *Answer*: A Spring helper class designed to make real HTTP REST calls to a running integration test server (used with `RANDOM_PORT`).
24. **Explain `@Spy` in Mockito.**
    * *Answer*: It creates a partial mock. It wraps a real object instance; by default, it calls real methods unless they are explicitly stubbed.
25. **What is the difference between `@Spy` and `@Mock`?**
    * *Answer*: `@Mock` creates a completely blank object where all methods return null/defaults. `@Spy` wraps a real object, executing real method code unless stubbed.
26. **How do you mock void methods in Mockito?**
    * *Answer*: By using the `doNothing()`, `doThrow()`, or `doAnswer()` syntax, e.g., `Mockito.doThrow(new Exception()).when(mock).voidMethod()`.
27. **What is the purpose of `ArgumentCaptor`?**
    * *Answer*: A Mockito helper class that allows you to capture and inspect the exact arguments passed to a mock method during execution.
28. **How do you run tests using specific profiles?**
    * *Answer*: By annotating the test class with `@ActiveProfiles("test")`.
29. **What does the `@BeforeAll` annotation do?**
    * *Answer*: It runs the annotated method exactly once before *all* tests in the class start. The method must be declared as `static`.
30. **What does the `@AfterAll` annotation do?**
    * *Answer*: It runs the annotated method once after *all* tests in the class complete. The method must be declared as `static`.
31. **What is the purpose of `@MockMvcTest` vs `@WebMvcTest`?**
    * *Answer*: `@WebMvcTest` is the standard annotation for web slice testing. `@MockMvcTest` is not a standard Spring annotation (often confused with `AutoConfigureMockMvc` which enables MockMvc injection in general tests).
32. **Explain `@AutoConfigureMockMvc`.**
    * *Answer*: An annotation placed on `@SpringBootTest` classes to automatically configure and inject a `MockMvc` bean into integration tests.
33. **What is H2 Database and why is it popular in Spring Boot integration testing?**
    * *Answer*: H2 is an in-memory SQL database. It runs inside the JVM memory, allowing fast, isolated integration tests without requiring an external MySQL or Postgres installation.
34. **What is the purpose of `@Sql` annotation?**
    * *Answer*: It allows executing SQL script files (e.g. `schema.sql`, `data.sql`) before running a test method to pre-populate database states.
35. **What does `Mockito.any()` do?**
    * *Answer*: A Mockito matcher that matches any argument value (null or non-null) passed to a stubbed method.
36. **Explain the rules of Argument Matchers in Mockito.**
    * *Answer*: If you use an argument matcher (like `any()`) for one parameter in a method stub, you *must* use argument matchers for all parameters in that method call.
37. **What is a Bean Mocking cycle?**
    * *Answer*: When a test boots a context, replaces a bean with `@MockBean`, and then cached context beans are polluted, forcing Spring to rebuild the context on subsequent test classes (reducing test speeds).
38. **What does the `@DirtiesContext` annotation do?**
    * *Answer*: It tells Spring that the test class modified the ApplicationContext (e.g., modified beans). Spring will close and rebuild the context before running the next test class.
39. **What is the difference between `@MockBean` and `@SpyBean`?**
    * *Answer*: `@MockBean` registers a complete mock in the container. `@SpyBean` wraps a real Spring container bean, allowing you to spy or stub specific methods while keeping the rest real.
40. **How do you verify if a mock was never called?**
    * *Answer*: By calling `verify(mock, never()).method()`.

### 🔴 Advanced Questions (41-50)

41. **Explain the Spring Test Context Caching mechanism.**
    * *Answer*: To speed up tests, Spring caches the booted `ApplicationContext` in memory. If subsequent test classes share the exact same configuration (annotations, active profiles), Spring reuses the cached context instead of rebuilding it.
42. **How does Escape Analysis in the JVM optimize mock execution speeds?**
    * *Answer*: JIT escape analysis detects that Mockito mock instances created inside unit tests do not escape the method scope, enabling scalar replacement and allocating mock fields directly to CPU registers instead of the heap.
43. **Explain how to write a custom mock check for multi-threaded async code.**
    * *Answer*: Standard Mockito assertions fail on separate threads. You must use utility libraries like **Awaitility** to poll and wait for async assertions to pass (`Awaitility.await().untilAsserted(...)`).
44. **What is the difference between `@SpringBootTest` and using Testcontainers?**
    * *Answer*: `@SpringBootTest` typically uses in-memory H2 databases (which might behave differently than real production databases). **Testcontainers** uses Docker to boot real database engines (like PostgreSQL or MySQL) on the fly during integration tests, ensuring absolute database consistency.
45. **What is the role of `ContextLoader` in Spring test runner architecture?**
    * *Answer*: An interface responsible for loading an `ApplicationContext` for integration tests based on configuration properties and classes.
46. **How would you test a Spring Boot security JWT filter?**
    * *Answer*: By using `mockMvc.perform(get("/api/secure").header("Authorization", "Bearer valid_jwt_token"))` and asserting `status().isOk()`, verifying that the filter chain successfully parsed the token.
47. **What is the purpose of `WebTestClient`?**
    * *Answer*: A non-blocking, reactive HTTP client designed for testing WebFlux reactive applications, but it can also be used to test standard Servlet MVC APIs.
48. **How does Mockito mock final classes or final methods in modern versions?**
    * *Answer*: Mockito uses a custom subclassing mechanism with Java Agents and dynamic class transformers to modify final class bytecode loading, bypassing final restrictions.
49. **How would you test a Scheduled task (`@Scheduled`) in Spring Boot?**
    * *Answer*: Since scheduled tasks run on background threads, you mock the executed service method and use `verify` along with Awaitility to check if the mock was executed within a specific time window.
50. **What is the role of `SpringBootContextLoader`?**
    * *Answer*: The default `ContextLoader` class used in Spring Boot tests that parses default configuration properties, active profiles, and bootstraps the context using `SpringApplication`.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 10: Spring Security & JWT Authentication](10_security_jwt.md)
* **Next Chapter**: [👉 Topic 12: Advanced Features & Caching](12_actuator_scheduling_async.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
