# 🍃 Spring & Spring Boot Learning Path: Basics to Advanced 🚀

Welcome to the Spring & Spring Boot learning dashboard! This path is designed to take you from a complete beginner to an advanced, enterprise-ready backend engineer. 

Every topic is fully self-contained, meaning you won't need to consult any other tutorials, blogs, or documentations. Each folder contains a detailed topic file featuring real-world analogies, deep-dives, copy-paste-ready code sandboxes, interview-ready definitions, and exactly **50 interview questions** (20 Basic, 20 Intermediate, 10 Advanced) to test your readiness!

---

## 🗺️ Spring & Spring Boot Road Map

Use this list to navigate through the curriculum and track your progress:

### 🟢 Phase 1: Core Spring Framework Foundations
- [ ] 🍃 **[Topic 01: Introduction to Spring Framework](01_intro_to_spring_framework.md)** — Understand Inversion of Control (IoC), Dependency Injection (DI), Bean Lifecycles, and ApplicationContext.
- [ ] 🏷️ **[Topic 02: Spring Core Annotations & Configuration](02_spring_annotations_configuration.md)** — Master annotations like `@Component`, `@Autowired`, `@Bean`, `@Configuration`, `@Scope`, and `@Profile`.
- [ ] 🛡️ **[Topic 03: Aspect-Oriented Programming (AOP) & Logging](03_spring_aop_logging.md)** — Learn how to separate cross-cutting concerns using AOP (Aspects, Advices, `@Around`) and set up logging (SLF4J/Logback).

### 🟡 Phase 2: Building Web APIs with Spring Boot
- [ ] ⚡ **[Topic 04: Introduction to Spring Boot](04_intro_to_spring_boot.md)** — Discover the magic under the hood: Auto-Configuration, Starter Dependencies, and the `@SpringBootApplication` bootstrap.
- [ ] 📡 **[Topic 05: Building REST APIs with MVC](05_rest_api_mvc.md)** — Create robust RESTful endpoints with Controllers, Request Mapping (`@GetMapping`, `@PostMapping`), and ResponseEntities.
- [ ] 🛑 **[Topic 06: Global Exception Handling & Validation](06_exception_handling_validation.md)** — Learn validation (`@Valid`, `@NotNull`) and set up clean, uniform API responses for errors using `@ControllerAdvice`.

### 🟠 Phase 3: Data Access & Enterprise Persistence
- [ ] 🗃️ **[Topic 07: Spring Data JPA & Hibernate](07_spring_data_jpa.md)** — Connect Java objects to relational databases using ORM, write Entities, and leverage JpaRepository for CRUD operations.
- [ ] 🔗 **[Topic 08: Database Relationships & Transactions](08_relationships_transactions.md)** — Configure `@OneToMany` and `@ManyToOne` mappings, understand Eager/Lazy loading, and protect operations using `@Transactional`.
- [ ] ⚙️ **[Topic 09: External Configuration & Profiles](09_external_configuration_profiles.md)** — Manage configurations for Dev, Test, and Prod using `application.yml`, `@Value`, and `@ConfigurationProperties`.

### 🔴 Phase 4: Production Security, Testing, & Operations
- [ ] 🔑 **[Topic 10: Spring Security & JWT Authentication](10_security_jwt.md)** — Secure your application. Set up UserDetailsService, BCrypt password hashing, and build a custom JWT Stateless Filter Chain.
- [ ] 🧪 **[Topic 11: Testing in Spring Boot](11_spring_boot_testing.md)** — Write clean tests using JUnit 5 and Mockito. Master `@SpringBootTest`, `@WebMvcTest`, and `@MockBean`.
- [ ] 🔋 **[Topic 12: Advanced Features & Caching](12_actuator_scheduling_async.md)** — Monitor your system with Actuator, schedule recurring tasks (`@Scheduled`), run async threads (`@Async`), and configure **Caching with Redis**.
- [ ] 🌐 **[Topic 13: Microservices Architecture & Resilience](13_microservices_basics.md)** — Transition to microservices. Connect services via Eureka Registry, route traffic with API Gateway, communicate with Feign, and implement **Resilience4j Circuit Breakers** and **Distributed Tracing**.
- [ ] 📦 **[Topic 14: Deployment, Containers, & Messaging](14_spring_boot_deployment.md)** — Package apps (executable JAR), containerize with **Docker**, deploy to production, and integrate **Apache Kafka** for event-driven messaging.

---

## 🎨 Layout of Each Chapter

1. 🏠 **Analogies**: Real-world scenarios (like a restaurant kitchen or airport security gate) representing technical flows.
2. 🔬 **Deep Dives**: Concrete explanations of framework classes, annotation behavior, and JVM internals.
3. 💻 **Code Sandbox**: Complete, copy-paste-ready, and heavily annotated Java classes.
4. 📖 **Interview-Ready Definitions**: Simple-English summaries of core jargon for interview preparation.
5. ❓ **50 Interview Questions**: Chronological, categorized lists of questions with complete answers.

Let's begin! Open **[Topic 01: Introduction to Spring Framework](01_intro_to_spring_framework.md)** to get started!
