# ⚙️ Topic 09: External Configuration & Profiles

Welcome back, settings master! In this chapter, we will learn how to configure our Spring Boot applications dynamically. In real-world software, you never hardcode settings like database passwords, API URLs, or server ports inside your Java classes. Instead, you manage them externally using `application.yml` or `application.properties` files. We will learn how to load these configurations using `@Value` and `@ConfigurationProperties`, and how to swap environments (development, testing, production) using **Profiles**.

---

## 🏠 The Big Picture & Real-Life Example

### 📺 The Smart TV and The Remote Control (Configurations & Profiles)
Imagine you purchase a Smart TV:
* **The TV Hardware/Software**: This is your compiled Java code. It is sealed in plastic and cannot be modified easily.
* **The Remote Control (Properties)**: Instead of opening the TV with a screwdriver to change the volume, you press buttons on the remote. This changes the TV's parameters externally!
* **The Mode Switch (Profiles)**: 
  * You set the mode to **Kids Mode (`dev` profile)**: The TV disables advanced channels and connects to a mock network.
  * You switch to **Parent Mode (`prod` profile)**: The TV unlocks all channels and connects to a high-speed network.

You change the active profile, and the TV's behavior changes completely without rebuilding the physical TV!

---

## 🔬 Let's Look Closer

### 1. YAML vs. Properties
* **Properties (`application.properties`)**: Uses flat key-value pairs. Can become repetitive:
  ```properties
  db.connection.url=jdbc:mysql://localhost
  db.connection.username=root
  ```
* **YAML (`application.yml`)**: Uses indentation and hierarchical blocks. It is much cleaner and easier for humans to read:
  ```yaml
  db:
    connection:
      url: jdbc:mysql://localhost
      username: root
  ```

### 2. Loading Settings into Java Beans
* **`@Value`**: Best for injecting individual simple settings (like a string or number).
* **`@ConfigurationProperties`**: Best for binding a nested group of configurations directly to a structured Java object with type-safety (e.g., binding all `db.connection` settings to a `DbConfig` object).

### 3. Profile-Specific Files
Spring Boot automatically searches for files matching `application-{profile}.properties` or `application-{profile}.yml`. If you activate the `prod` profile, Spring Boot loads all properties inside `application-prod.yml` and overrides any default properties in `application.yml`.

---

## 💻 Code Sandbox

Let's build a structured Config model that reads profile-specific email server properties.

### 1. The Configuration file: `application-dev.yml`
```yaml
# Settings for the development environment
app:
  mail:
    host: dev.smtp.mailtrap.io
    port: 2525
    username: dev-user
    password: dev-password-123
```

### 2. The Configuration file: `application-prod.yml`
```yaml
# Settings for the production environment
app:
  mail:
    host: smtp.sendgrid.net
    port: 587
    username: prod-api-key
    password: prod-secure-secret-token
```

### 3. The Properties Class: `MailProperties.java`
```java
package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.mail") // Automatically binds keys starting with "app.mail"
public class MailProperties {

    private String host;
    private int port;
    private String username;
    private String password;

    // Standard Getters and Setters (Required for binding)
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

### 4. Injecting Individual Configs: `NotificationService.java`
```java
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Injecting a single property value with a default fallback
    @Value("${app.system-name:AlertSystem}")
    private String systemName;

    private final MailProperties mailProperties;

    @Autowired
    public NotificationService(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    public void sendAlert() {
        System.out.println("System: " + systemName);
        System.out.println("Connecting to host: " + mailProperties.getHost());
        System.out.println("Using SMTP Port: " + mailProperties.getPort());
        System.out.println("Authentication User: " + mailProperties.getUsername());
    }
}
```

---

## 🧠 Points to Remember

* Enable `@ConfigurationProperties` by adding `@EnableConfigurationProperties(MailProperties.class)` in your config classes or simply annotating the class with `@Component`.
* To run your Spring Boot application with a specific profile activated, use this terminal command:
  `java -jar app.jar --spring.profiles.active=prod`
* Property keys are case-insensitive and support **relaxed binding** (e.g. `app.mail.host-name`, `app.mail.hostName`, and `app_mail_host_name` all bind successfully to `hostName` variables).

---

## 📖 Key Definitions

* **YAML**: A human-readable data serialization language commonly used for design configuration files due to its clean, indentation-based layout.
* **Relaxed Binding**: A feature in Spring Boot that maps property keys from configuration files (like kebab-case or snake_case) to camelCase variables in Java objects automatically.
* **ConfigurationProperties**: An annotation used to bind nested groups of configuration properties from files directly to typed Java objects.
* **Property Injection**: The process of reading external configuration values at runtime and injecting them into class fields.
* **Active Profile**: The configuration profile designated at startup that determines which environment-specific properties are loaded by Spring Boot.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is external configuration in Spring Boot?**
   * *Answer*: It is the practice of storing configuration values (like database URLs, passwords, ports) outside the compiled Java code inside files, environment variables, or command-line arguments.
2. **What is the difference between `.properties` and `.yml` files?**
   * *Answer*: `.properties` files use simple flat key-value pairs. `.yml` (YAML) files use nested blocks and indentations, making them cleaner and easier to read.
3. **How do you inject a single property value into a field?**
   * *Answer*: By using the `@Value` annotation: `@Value("${property.name}")`.
4. **What happens if a property key specified in `@Value` is missing?**
   * *Answer*: The application will fail to start, throwing an `IllegalArgumentException` during bean initialization.
5. **How do you define a default value fallback in `@Value`?**
   * *Answer*: By adding a colon separator: `@Value("${property.name:default_value}")`.
6. **What is the purpose of `@ConfigurationProperties`?**
   * *Answer*: It is used to bind groups of related properties starting with a shared prefix directly into a typed Java class.
7. **What annotation enables `@ConfigurationProperties` class scanning?**
   * *Answer*: `@EnableConfigurationProperties` declared in configuration classes, or annotating the target class with `@Component`.
8. **What are Spring Profiles?**
   * *Answer*: Profiles are environment groups (like `dev`, `test`, `prod`) that allow developers to load conditional configurations based on the active runtime environment.
9. **How do you name a profile-specific properties file in Spring Boot?**
   * *Answer*: Follow the format: `application-{profile}.properties` or `application-{profile}.yml` (e.g., `application-prod.yml`).
10. **How do you set the active profile in the command line?**
    * *Answer*: By passing the JVM argument: `java -jar app.jar --spring.profiles.active=prod` or `-Dspring.profiles.active=prod`.
11. **Where does Spring Boot search for `application.properties` files by default?**
    * *Answer*: In the current folder, a `/config` folder, the classpath root (`src/main/resources`), or a classpath `/config` folder.
12. **Can you define multiple active profiles at once?**
    * *Answer*: Yes, by passing a comma-separated list: `--spring.profiles.active=dev,db-local`.
13. **What is the default profile name if none is explicitly specified?**
    * *Answer*: The default profile name is `default`.
14. **What is Relaxed Binding?**
    * *Answer*: A Spring Boot feature where property keys like `app.mail-host`, `app.mailHost`, and `app.MAIL_HOST` all bind successfully to a Java field named `mailHost`.
15. **Do `@ConfigurationProperties` classes require setter methods?**
    * *Answer*: Yes, because the binding engine uses setter methods to inject values into the fields during startup.
16. **How do you inject list configurations in properties?**
    * *Answer*: By using comma-separated values: `my.list=one,two,three` or indexing them: `my.list[0]=one`.
17. **Can you validate `@ConfigurationProperties` classes using standard validations?**
    * *Answer*: Yes, by annotating the properties class with `@Validated` and adding constraints like `@NotNull` or `@Email` on the properties fields.
18. **How does Spring Boot resolve conflicts between multiple property files?**
    * *Answer*: Profile-specific files (e.g. `application-dev.yml`) override keys in generic files (`application.yml`). Environment variables override keys in properties files.
19. **What does the `@PropertySource` annotation do?**
    * *Answer*: It allows loading custom properties files (other than the default `application.properties`) into the environment.
20. **Does YAML support `@PropertySource` in standard Spring?**
    * *Answer*: No, `@PropertySource` does not support loading `.yml` files by default. It only supports `.properties` files.

### 🟡 Intermediate Questions (21-40)

21. **What is the Spring Boot Property Loading Order (Precedence)?**
    * *Answer*: The order (highest to lowest) is: Command-line arguments -> JVM arguments -> OS environment variables -> Profile-specific config files -> Application config files -> `@PropertySource` declarations.
22. **Explain the differences between `@Value` and `@ConfigurationProperties`.**
    * *Answer*: `@Value` supports SpEL expressions, is best for single values, and does not support relaxed binding or validation. `@ConfigurationProperties` binds structured hierarchies, supports relaxed binding, type-safe validation, but does not support SpEL.
23. **What is the purpose of `@Profile("!prod")`?**
    * *Answer*: It registers a bean only if the active profile is *not* "prod". The exclamation mark represents the logical NOT operator.
24. **How do you bind properties that contain maps (`Map<String, String>`)?**
    * *Answer*: By creating a `Map` field in a `@ConfigurationProperties` class, which automatically maps nested keys, like `app.my-map.key1=value1`.
25. **How does Spring Boot handle multiline strings in YAML?**
    * *Answer*: By using the pipe symbol `|` (preserves line breaks) or greater-than symbol `>` (collapses line breaks into spaces).
26. **What is the role of `Environment` interface in Spring?**
    * *Answer*: It is an abstraction of the application environment containing active profiles and all resolved property sources, allowing programmatic lookups using `env.getProperty("key")`.
27. **What is property placeholder resolution?**
    * *Answer*: Resolving placeholders like `${db.url}` inside annotation strings or config files by evaluating them against current environment variables.
28. **How can you reference one property value inside another property key?**
    * *Answer*: By using the placeholder syntax: `app.description=This is ${app.name}`. Spring resolves the nested key automatically.
29. **What is the purpose of `spring.config.import`?**
    * *Answer*: A feature introduced in Spring Boot 2.4 that allows importing external config files, directories, or cloud configuration servers directly inside your main properties file.
30. **Explain how to active a profile from within another properties file.**
    * *Answer*: By adding the key `spring.profiles.active=dev` or `spring.profiles.include=db-local` inside the default configuration file.
31. **What is a Configuration Processor (`spring-boot-configuration-processor`)?**
    * *Answer*: A dependency that scans `@ConfigurationProperties` classes and generates metadata JSON files. This enables IDE auto-complete tooltips when writing property keys.
32. **How does Spring Boot handle passwords and secrets securely in configurations?**
    * *Answer*: By using OS environment variables at deployment, or using third-party encryption libraries like **Jasypt** to decrypt encrypted property strings (e.g. `DEC(encrypted_string)`) at startup.
33. **Explain the purpose of `CommandLinePropertySource`.**
    * *Answer*: An internal Spring class that parses command-line arguments (like `--key=value`) and registers them as highest-priority properties in the environment.
34. **How do you inject a boolean value using `@Value`?**
    * *Answer*: Spring automatically converts the string "true" or "false" to a boolean field type: `@Value("${app.enabled:false}") private boolean enabled`.
35. **What is the difference between `@Value("#{...}")` and `@Value("${...}")`?**
    * *Answer*: `@Value("#{...}")` represents a **SpEL expression** (evaluated against Spring beans). `@Value("${...}")` represents a **property placeholder** (reads keys from environment properties).
36. **Can you use `@ConfigurationProperties` on class fields directly without getters/setters?**
    * *Answer*: No, setters are strictly required because Spring uses setter-based bean injection to populate the configuration properties.
37. **How does Spring Boot handle decimal and integer types in relaxed binding?**
    * *Answer*: It uses conversion service helpers (`ConversionService`) to automatically convert property string values into Java floats, doubles, integers, or longs.
38. **Explain the purpose of `spring.profiles.default`.**
    * *Answer*: It defines the default profile name to run if the developer does not configure `spring.profiles.active` at startup.
39. **What is the difference between configuration profiling and configuration injection?**
    * *Answer*: Injection is loading key-values. Profiling is selecting *which* files or beans are active based on the environment state.
40. **How can you print all loaded configurations during application startup?**
    * *Answer*: By autowiring the `Environment` object and extracting properties, or using the Spring Boot Actuator `/env` endpoint.

### 🔴 Advanced Questions (41-50)

41. **Explain the compilation optimization of relaxed binding in Spring Boot.**
    * *Answer*: Spring Boot builds a tree-like `ConfigurationPropertySource` map at startup, normalizing keys to standard formats, enabling constant time O(1) matching during binding loops.
42. **How would you dynamically refresh `@Value` fields at runtime without restarting the application?**
    * *Answer*: By annotating the bean with `@RefreshScope` (requires Spring Cloud Context) and triggering an endpoint refresh (`/actuator/refresh`) to reload properties.
43. **What is the role of `PropertySourcesPlaceholderConfigurer`?**
    * *Answer*: It is a bean post-processor that resolves `${...}` placeholders in bean definition values against the active environment property sources.
44. **How can you write a custom property source resolver for reading config from a database?**
    * *Answer*: By extending the `PropertySource` class and registering it in an `ApplicationContextInitializer` to insert it into the environment's property sources list during bootstrap.
45. **What is the difference between standard profile matching and Profile Expression syntax in Spring Boot 5.3+?**
    * *Answer*: Modern Spring supports logical expressions: `@Profile("prod & !cloud")` (active only in production and not in cloud), whereas older versions only supported flat comma-separated values.
46. **How does Spring Boot bind nested Lists of Custom Objects in `@ConfigurationProperties`?**
    * *Answer*: By defining a `List<CustomClass>` variable in the config class. YAML maps these using list indices or hyphens, matching constructor or setter parameters of the custom object.
47. **What is the purpose of `OriginTrackedValue`?**
    * *Answer*: It is an internal wrapper class that keeps track of the exact origin file name and line number where a property key was loaded, which is displayed on startup exceptions.
48. **Explain how `SnakeYAML` integration compiles YAML files.**
    * *Answer*: Spring Boot uses the SnakeYAML parser to read `.yml` files, converting the nested structure into a flat `Map<String, Object>` where keys are separated by periods.
49. **How would you encrypt properties using Jasypt in Spring Boot?**
    * *Answer*: (1) Add Jasypt dependency. (2) Define secret key. (3) Wrap secrets as `ENC(cipher_text)` in `application.yml`. Jasypt registers a property source post-processor to decrypt them automatically.
50. **What is the role of `EnvironmentPostProcessor`?**
    * *Answer*: An interface that allows developers to customize the application environment properties programmatically before the ApplicationContext starts loading bean definitions.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 08: Database Relationships & Transactions](08_relationships_transactions.md)
* **Next Chapter**: [👉 Topic 10: Spring Security & JWT Authentication](10_security_jwt.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
