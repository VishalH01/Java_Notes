# 🏷️ Topic 02: Spring Core Annotations & Configuration

Welcome back, explorer! In this chapter, we will learn about the **Spring Annotation System**. Think of annotations as metadata tags (like stickers) that you put on your Java classes. These stickers tell the Spring Container exactly what the class does and how to manage it. We will also learn about configuration profiles for separating developer and production configurations.

---

## 🏠 The Big Picture & Real-Life Example

### 🏷️ The Restaurant Badge System (Stereotype Annotations)
Imagine a busy restaurant where everyone wears a specialized name badge:
1. **Chef Badge (`@Service`)**: Worn by the kitchen staff who cook the food (business logic).
2. **Accountant Badge (`@Repository`)**: Worn by the money manager who accesses the cash register and safes (database access).
3. **Receptionist Badge (`@Controller`)**: Worn by the host at the front door who greets guests, takes their orders, and returns their food (handling web requests).
4. **General Employee Badge (`@Component`)**: Worn by general workers who do utility tasks like washing dishes or sweeping.

The Restaurant Owner (Spring Container) scans the room, spots the badges, and assigns tasks automatically: *"Ah, Chef! Here is the clean stove (`@Autowired`). Go cook!"*

---

## 🔬 Let's Look Closer

### 1. Stereotype Annotations
Instead of declaring every bean manually in a config class, you put annotations on top of your classes. During startup, Spring performs **Component Scanning** to find these classes and register them as beans:
* **`@Component`**: The basic generic tag for any Spring-managed class.
* **`@Service`**: A specialized tag for classes holding business logic.
* **`@Repository`**: A specialized tag for data-access classes. It also translates raw SQL exceptions into Spring's unified data exceptions.
* **`@Controller`**: Used for web controllers that handle user views (HTML).

### 2. Auto-Wiring (`@Autowired`)
This sticker tells Spring: *"Find a bean that matches this type and inject it here."* Spring will look through its registered beans and wire them together.

### 3. Bean Scopes (`@Scope`)
A scope defines the lifecycle and availability of a bean instance:
* **`singleton` (Default)**: Creates only one instance. Every class requesting this bean gets the exact same object.
* **`prototype`**: Creates a brand new object instance every single time the bean is requested.

### 4. Environments & Profiles (`@Profile`)
Imagine you want to use a mock database for local testing (`local` profile) and a real cloud database for production (`prod` profile). `@Profile("prod")` tells Spring: *"Only register this bean if the active profile is set to 'prod'."*

---

## 💻 Code Sandbox

Let's build a clean, multi-layered service with database connection switches.

### 1. The Repository Layer: `UserRepository.java`
```java
package com.example;

import org.springframework.stereotype.Repository;

@Repository // Tagged as Database Access
public class UserRepository {
    public String fetchUser() {
        return "Alice Green";
    }
}
```

### 2. The Service Layer: `UserService.java`
```java
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Tagged as Business Logic
public class UserService {
    
    private final UserRepository userRepository;

    // Autowired via Constructor Injection
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserGreeting() {
        String name = userRepository.fetchUser();
        return "Hello, " + name + "! Welcome to the Spring world.";
    }
}
```

### 3. Profile-Specific Beans: `DatabaseConnector.java`
```java
package com.example;

public interface DatabaseConnector {
    void connect();
}
```

```java
package com.example;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // Active only when profile is "dev"
public class DevDatabaseConnector implements DatabaseConnector {
    public void connect() {
        System.out.println("Connected to local in-memory H2 database (DEV).");
    }
}
```

```java
package com.example;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod") // Active only when profile is "prod"
public class ProdDatabaseConnector implements DatabaseConnector {
    public void connect() {
        System.out.println("Connected to cloud PostgreSQL database instance (PROD).");
    }
}
```

### 4. Main Controller: `UserController.java`
```java
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // A new controller is created per request
public class UserController {
    
    private final UserService userService;
    private final DatabaseConnector dbConnector;

    @Autowired
    public UserController(UserService userService, DatabaseConnector dbConnector) {
        this.userService = userService;
        this.dbConnector = dbConnector;
    }

    public void displayUserDashboard() {
        dbConnector.connect();
        System.out.println(userService.getUserGreeting());
    }
}
```

### 5. Running with Profiles: `Main.java`
```java
package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext();
        
        // Set active profile programmatically before scanning
        context.getEnvironment().setActiveProfiles("dev"); 
        
        // Scan the package for annotated beans
        context.scan("com.example");
        context.refresh();

        UserController controller = context.getBean(UserController.class);
        controller.displayUserDashboard();

        context.close();
    }
}
```

---

## 🧠 Points to Remember

* Use **stereotypes** (`@Service`, `@Repository`, `@Component`) to let Spring auto-detect your classes.
* Spring looks for these annotated classes inside packages configured by `@ComponentScan`.
* `@Repository` adds a built-in translator that turns low-level SQL database exceptions into Spring's clean data access exceptions.
* Use `@Profile` to isolate database properties, passwords, or mock components between development and live environments.

---

## 📖 Key Definitions

* **Stereotype Annotation**: Specialized metadata tags in Spring (like `@Service` or `@Repository`) used to declare a class's architectural role and register it as a bean.
* **Component Scanning**: The automated process where Spring parses application packages during startup to find annotated classes and register them in the container.
* **Bean Scope**: A configuration setting (like Singleton or Prototype) that defines the lifecycle, creation logic, and visibility of a bean in the container.
* **Profile**: A named environment configuration group in Spring that allows conditional activation of properties and beans based on runtime flags.
* **Autowired**: A Spring annotation used to perform automatic dependency injection by scanning matching bean types.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What are stereotype annotations in Spring?**
   * *Answer*: Stereotype annotations are core Spring annotations (like `@Component`, `@Service`, `@Repository`, and `@Controller`) that define the role of a class in the application architecture.
2. **What does the `@Component` annotation do?**
   * *Answer*: It marks a class as a generic Spring-managed bean, making it eligible for automatic component scanning.
3. **What is the difference between `@Component` and `@Service`?**
   * *Answer*: `@Component` is a generic annotation for any bean, while `@Service` is a specialized component used to represent business logic classes.
4. **Why is `@Repository` specifically used for data access classes?**
   * *Answer*: Because in addition to registering the bean, it automatically translates low-level database exceptions (like `SQLException`) into Spring's unified `DataAccessException` hierarchy.
5. **How does `@Autowired` find matching beans?**
   * *Answer*: By default, it searches the container **by type**. If multiple beans match, it tries to match by the bean name.
6. **What is Component Scanning, and how is it enabled?**
   * *Answer*: It is the process of locating classes with stereotype annotations. It is enabled using the `@ComponentScan` annotation on a configuration class.
7. **What is the `@Primary` annotation used for?**
   * *Answer*: It tells Spring to prioritize a specific bean for injection when multiple beans of the exact same type are available.
8. **What is the difference between `@Autowired` and `@Resource`?**
   * *Answer*: `@Autowired` is Spring-specific and resolves dependencies **by type** first; `@Resource` is a standard Java annotation (JSR-250) and resolves dependencies **by name** first.
9. **How do you inject a property value from a file using annotations?**
   * *Answer*: By using the `@Value("${property.name}")` annotation on a field, constructor parameter, or setter.
10. **What does the `@Scope` annotation do?**
    * *Answer*: It defines the lifecycle boundary of a bean, specifying whether it should be a `singleton`, `prototype`, `request`, or `session`.
11. **What is a Singleton Bean?**
    * *Answer*: A bean whose instance is created once per Spring container and shared across the entire application context.
    * **How do you make a bean Prototype scope?**
    * *Answer*: By annotating the class with `@Scope("prototype")` or `@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)`.
13. **What is the default scanning package for `@ComponentScan`?**
    * *Answer*: The package containing the configuration class that declares `@ComponentScan` (and all of its sub-packages).
14. **What is the `@Profile` annotation?**
    * *Answer*: An annotation used to register a bean conditionally based on which profile environment (like `dev` or `prod`) is currently active.
15. **Can you apply `@Profile` at the class level and method level?**
    * *Answer*: Yes, it can be applied to both `@Component` classes and `@Bean` methods inside configuration classes.
16. **How do you activate a specific profile at runtime?**
    * *Answer*: By passing the JVM argument `-Dspring.profiles.active=dev` or setting it in environment variables.
17. **What is the purpose of `@PropertySource`?**
    * *Answer*: It is used to declare external properties files (like `classpath:db.properties`) to be loaded into Spring's Environment registry.
18. **What is the difference between `@Controller` and `@RestController`?**
    * *Answer*: `@Controller` is used for traditional MVC views returning HTML pages, while `@RestController` is a shortcut combining `@Controller` and `@ResponseBody` to return raw data (JSON/XML).
19. **What does the `@Qualifier` annotation do?**
    * *Answer*: It is used alongside `@Autowired` to specify the exact name of the bean that should be injected to resolve ambiguity.
20. **Is `@Autowired` required on constructors in modern Spring?**
    * *Answer*: No, starting from Spring 4.3, if a class has only one constructor, Spring will automatically autowire it without needing the explicit annotation.

### 🟡 Intermediate Questions (21-40)

21. **What is the difference between `@Bean` method return types and stereotypes?**
    * *Answer*: Stereotypes are placed on class declarations where you own the source code. `@Bean` is used inside configuration classes to register third-party library classes where you cannot add annotations to the source.
22. **Explain the behavior of prototype-scoped beans in a singleton bean.**
    * *Answer*: Since the singleton bean is only instantiated once, the prototype bean is injected only once during that startup. The singleton will continue to reuse that same prototype instance, bypassing the prototype scope.
23. **How do you resolve the prototype-injection-in-singleton problem?**
    * *Answer*: You can use **Lookup Injection** (`@Lookup`), inject an `ObjectProvider<PrototypeBean>`, or inject the `ApplicationContext` directly (though that couples code to Spring).
24. **How does the `@Value` annotation handle default values?**
    * *Answer*: By using a colon separator: `@Value("${key:default_value}")`. If the key is missing in the environment, the default is used.
25. **What is the purpose of the `@ImportResource` annotation?**
    * *Answer*: It is used to load legacy XML-based Spring configurations into a modern `@Configuration` Java class.
26. **Explain how `@Profile("!prod")` works.**
    * *Answer*: The exclamation mark represents logical NOT. This profile annotation registers the bean in every environment *except* when the active profile is "prod".
27. **What happens if a class is annotated with both `@Component` and `@Scope("prototype")` and has a `@PostConstruct` method?**
    * *Answer*: The `@PostConstruct` method will execute every single time Spring instantiates a new instance of the prototype bean.
28. **Does Spring call `@PreDestroy` methods for prototype-scoped beans?**
    * *Answer*: No, Spring does not manage the complete lifecycle of prototype beans. It instantiates and wires them, but the client must handle their destruction and garbage collection.
29. **What is the difference between `@PostConstruct` and a bean initialization method defined in `@Bean(initMethod = "customInit")`?**
    * *Answer*: `@PostConstruct` is annotation-driven and runs first; `initMethod` is declared in the configuration metadata and runs after annotations are processed.
30. **How can you dynamically configure a Bean using SpEL?**
    * *Answer*: By writing expressions inside `@Value`, like `@Value("#{systemProperties['user.home']}")` or `@Value("#{dbConfig.url}")`.
31. **What is the `@AliasFor` annotation used for?**
    * *Answer*: It is used to create aliases for annotation attributes, commonly used to build custom annotations that override Spring attributes.
32. **What does the `@DependsOn` annotation do?**
    * *Answer*: It forces Spring to instantiate one or more specified beans before instantiating the bean annotated with `@DependsOn`.
33. **What is the difference between `@Autowired(required = false)` and java's `Optional`?**
    * *Answer*: Both allow optional injection. If the bean doesn't exist, `@Autowired(required = false)` sets the field to null, while `Optional<MyBean>` wraps it inside an empty Optional container.
34. **Can you Autowire a List of beans of the same interface?**
    * *Answer*: Yes, Spring will automatically search for all registered beans implementing that interface and inject them as a List or Set.
35. **What is the difference between `@Autowired` and `@Inject`?**
    * *Answer*: `@Autowired` is Spring's native annotation, while `@Inject` is part of standard JSR-330 annotations. They behave similarly, but `@Autowired` has a `required` attribute.
36. **Explain the purpose of `@Order` annotation when injecting lists.**
    * *Answer*: It specifies the order in which beans are sorted when injected into a collection (like a List). Lower values have higher priority.
37. **What is a stereotypic meta-annotation?**
    * *Answer*: An annotation used to build other annotations (e.g., `@Service` is meta-annotated with `@Component`).
38. **How does `@ComponentScan` exclude specific classes from being registered?**
    * *Answer*: By using the `excludeFilters` attribute of `@ComponentScan` with custom filter types (regex, annotations, or assignable classes).
39. **What is the default profile name in Spring if no profile is active?**
    * *Answer*: The default profile name is `default`. You can register fallback beans using `@Profile("default")`.
40. **How does `@Value` read list values?**
    * *Answer*: By parsing comma-separated strings into lists, like: `@Value("#{'${my.list}'.split(',')}")` or `@Value("${my.list}") List<String> list`.

### 🔴 Advanced Questions (41-50)

41. **How does Spring parse annotations at startup?**
    * *Answer*: Spring uses ASM (a bytecode manipulation framework) to read class files and check for annotations without actually loading the classes into the JVM ClassLoader, which keeps memory usage low.
42. **What is the difference between CGLIB proxies and JDK Dynamic proxies in bean configuration?**
    * *Answer*: JDK Dynamic proxies require the bean to implement an interface, and the proxy is created dynamically based on that interface. CGLIB proxies subclass the bean directly at runtime, which is used for classes without interfaces.
43. **Explain how `@Configuration(proxyBeanMethods = false)` optimizes performance.**
    * *Answer*: It disables the generation of CGLIB proxies for the configuration class (Lite Mode). Spring will not intercept method calls, making startup faster and reducing memory overhead, but you lose the guarantee that calling `@Bean` methods repeatedly returns the same singleton instance.
44. **How would you implement a custom scope in Spring?**
    * *Answer*: By implementing the `org.springframework.beans.factory.config.Scope` interface and registering it with the container via `ConfigurableBeanFactory.registerScope()`.
45. **What is the role of `AutowiredAnnotationBeanPostProcessor`?**
    * *Answer*: It is the internal post-processor responsible for scanning and injecting fields, methods, and constructors annotated with `@Autowired` or `@Value`.
46. **How does `@Profile` work under the hood?**
    * *Answer*: It is meta-annotated with `@Conditional(ProfileCondition.class)`. The `ProfileCondition` class reads the active profiles from the `Environment` and evaluates whether the profile expressions match.
47. **What is the difference between `@LocalVariableTypeInference` and injecting bean references?**
    * *Answer*: Local Variable Type Inference (`var`) is a Java language compile-time feature; injecting beans is a runtime container management feature.
48. **How does Spring prevent ClassNotFoundException during component scanning when optional classes are annotated?**
    * *Answer*: Spring uses metadata readers that check class headers instead of loading classes, allowing it to safely skip scanning classes whose dependencies are not present on the classpath.
49. **Can a constructor have multiple `@Autowired` annotations?**
    * *Answer*: No, a class can have only one constructor marked with `@Autowired(required = true)` to avoid container initialization ambiguity.
50. **How would you resolve the issue where a bean is injected before its properties are loaded via `@Value`?**
    * *Answer*: By injecting the values using Constructor Injection rather than Field Injection. This ensures values are present when the object is instantiated.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 01: Introduction to Spring Framework (IoC & DI)](01_intro_to_spring_framework.md)
* **Next Chapter**: [👉 Topic 03: Aspect-Oriented Programming (AOP) & Logging](03_spring_aop_logging.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
