# 🔗 Topic 08: Database Relationships & Transactions

Welcome back, data architect! In this chapter, we will learn how to connect different database tables together using **JPA Relationships** (`@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`). We will understand the difference between **Lazy Loading** and **Eager Loading**, and how to protect our operations using **Transactions** (`@Transactional`) to ensure our data never gets corrupted.

---

## 🏠 The Big Picture & Real-Life Example

### 1. Database Relationships
Imagine a company database:
* **One-to-One (`@OneToOne`)**: A Employee has exactly one Workspace cubicle. The cubicle belongs to only one Employee.
* **Many-to-One / One-to-Many (`@ManyToOne` / `@OneToMany`)**: A Department (like HR) has many Employees. However, each Employee belongs to only one Department.
* **Many-to-Many (`@ManyToMany`)**: A Project can have many Employees working on it. At the same time, an Employee can be assigned to multiple Projects.

### 2. The Bank Transfer (Transactions)
Imagine you want to transfer $100 to your friend Bob:
1. The bank subtracts $100 from your balance.
2. Suddenly, the bank's power goes out!
3. The server restarts. The $100 is gone from your account, but Bob never received it! It vanished!

To prevent this, we use a **Transaction (`@Transactional`)**. A transaction makes an "All-or-Nothing" promise:
* If both steps succeed, the changes are saved (**Commit**).
* If any step fails, the system resets both accounts as if the transfer never started (**Rollback**).

---

## 🔬 Let's Look Closer

### 1. Eager vs. Lazy Fetching
* **Eager Fetching (`FetchType.EAGER`)**: When you load an entity (e.g., Department), JPA automatically loads all related data (e.g., all 500 Employees) in the same query. (Can cause slow queries).
* **Lazy Fetching (`FetchType.LAZY`)**: When you load a Department, JPA only loads the department details. It will only query the database for the Employees when you explicitly call `department.getEmployees()`.

### 2. Transaction Rollback Rules
By default, a `@Transactional` block will automatically roll back if a **Runtime Exception** (`RuntimeException` or `Error`) is thrown. It will **not** roll back on checked exceptions unless you configure it explicitly using `@Transactional(rollbackFor = Exception.class)`.

---

## 💻 Code Sandbox

Let's build a Department and Employee relational mapping with a transactional transfer logic.

### 1. The Parent Entity: `Department.java`
```java
package com.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One Department has Many Employees. mappedBy points to the variable name in Employee.java
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

    public Department() {}

    public Department(String name) { this.name = name; }

    // Helper method to add employees safely
    public void addEmployee(Employee emp) {
        employees.add(emp);
        emp.setDepartment(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Employee> getEmployees() { return employees; }
}
```

### 2. The Child Entity: `Employee.java`
```java
package com.example;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Many Employees belong to one Department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id") // Creates the foreign key column in database
    private Department department;

    public Employee() {}

    public Employee(String name) { this.name = name; }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setDepartment(Department department) { this.department = department; }
}
```

### 3. The Transactional Service: `CompanyService.java`
```java
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final DepartmentRepository deptRepo;
    private final EmployeeRepository empRepo;

    @Autowired
    public CompanyService(DepartmentRepository deptRepo, EmployeeRepository empRepo) {
        this.deptRepo = deptRepo;
        this.empRepo = empRepo;
    }

    // Protects this method with a database transaction
    @Transactional(rollbackFor = Exception.class) 
    public void setupCompanyStructure() {
        // 1. Create a Department
        Department hr = new Department("Human Resources");

        // 2. Create Employees and associate them
        hr.addEmployee(new Employee("Alice"));
        hr.addEmployee(new Employee("Bob"));

        // 3. Save parent (CascadeType.ALL will automatically save the employees too!)
        deptRepo.save(hr);

        // 4. Test Rollback: Throwing an exception to force rollback
        if (true) {
            throw new RuntimeException("Simulating system crash in middle of database operation!");
        }
        
        // If an exception occurs, neither the Department nor the Employees are saved.
    }
}
```

---

## 🧠 Points to Remember

* In bidirectional relationships (like `@OneToMany` and `@ManyToOne`), the child side containing the foreign key is the **owning side** (marked by `@ManyToOne`). The parent side uses the `mappedBy` attribute to point to the child.
* Always set `fetch = FetchType.LAZY` on relationships, especially on `@OneToMany` and `@ManyToMany`, to prevent loading too much data into memory.
* Mark read-only methods with `@Transactional(readOnly = true)`. This optimizes database locks and memory caching.

---

## 📖 Key Definitions

* **One-to-Many Relationship**: A database association where a single record in one table is linked to multiple records in another table.
* **Cascade Type**: A configuration setting (like `CascadeType.ALL`) that determines whether modifications (saves, updates, deletes) made to a parent entity should propagate to its children.
* **Lazy Loading**: A fetching strategy that defers the loading of related entities from the database until they are explicitly accessed by the application.
* **Database Transaction**: A sequence of database operations executed as a single logical unit of work that must succeed or fail together.
* **Transactional**: A Spring annotation used to wrap a method in a database transaction, guaranteeing ACID properties.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is a One-to-Many relationship?**
   * *Answer*: A relationship where a single record in Table A is associated with multiple records in Table B (e.g., one Department has many Employees).
2. **What does the `@ManyToOne` annotation do?**
   * *Answer*: It maps a relationship where multiple records in the current entity table point to a single record in another entity table.
3. **What is the purpose of `@JoinColumn`?**
   * *Answer*: It specifies the name of the foreign key column that connects the two tables in the database.
4. **What does `mappedBy` signify in JPA?**
   * *Answer*: It indicates that the relationship is bidirectional and that the other side is the owning side that manages the database foreign key column.
5. **Which side is the owning side in a `@OneToMany` / `@ManyToOne` relationship?**
   * *Answer*: The `@ManyToOne` side is always the owning side, as it holds the actual foreign key column in the database table.
6. **What is Eager Loading?**
   * *Answer*: A fetching strategy where related entities are queried and loaded from the database immediately when the parent entity is loaded.
7. **What is Lazy Loading?**
   * *Answer*: A fetching strategy where related entities are not queried from the database until they are explicitly accessed (e.g., calling `.getEmployees()`).
8. **What are the default fetch types for relationships in JPA?**
   * *Answer*: `@ManyToOne` and `@OneToOne` default to **EAGER**. `@OneToMany` and `@ManyToMany` default to **LAZY**.
9. **What is the `@Transactional` annotation?**
   * *Answer*: It is a Spring annotation used to define the transaction boundary around a method, ensuring all database operations within the method run inside a single transaction.
10. **What are the ACID properties in database transactions?**
    * *Answer*: Atomicity (All-or-Nothing), Consistency (Valid states), Isolation (Independent executions), and Durability (Permanent saves).
11. **What is a Transaction Rollback?**
    * *Answer*: The process of canceling and reversing all database changes made during the current failed transaction, restoring the database to its pre-transaction state.
12. **On which exception types does Spring execute a rollback by default?**
    * *Answer*: On **Runtime Exceptions** (unchecked exceptions extending `RuntimeException`) and JVM Errors.
13. **Does Spring roll back a transaction on checked exceptions by default?**
    * *Answer*: No. To roll back on checked exceptions, you must configure `@Transactional(rollbackFor = Exception.class)`.
14. **What is Cascade Type in JPA?**
    * *Answer*: It is an option that propagates state transitions (like PERSIST, MERGE, REMOVE) from a parent entity down to its related child entities.
15. **What does `CascadeType.ALL` mean?**
    * *Answer*: It applies all JPA cascade operations (saving, updating, deleting, refreshing) to the child entities automatically.
16. **What does `CascadeType.REMOVE` (or `@OneToMany(orphanRemoval = true)`) do?**
    * *Answer*: It deletes child entities from the database when they are deleted from the parent entity, or when the parent entity is deleted.
17. **What is a Joint Table mapping?**
    * *Answer*: A mapping strategy that uses an intermediary join table containing foreign keys to connect two entities, commonly used in `@ManyToMany` mappings.
18. **How do you define a Many-to-Many relationship in JPA?**
    * *Answer*: By using `@ManyToMany` annotations on both entities, and defining the join table structure using `@JoinTable` on the owning side.
19. **What is the purpose of `@Transactional(readOnly = true)`?**
    * *Answer*: It optimizes the transaction for reading. Hibernate disables dirty checking and lock management, improving performance and memory usage.
20. **Can you apply `@Transactional` at the class level?**
    * *Answer*: Yes, this applies the transaction configuration to all public methods declared inside the class.

### 🟡 Intermediate Questions (21-40)

21. **Explain the difference between `@OneToMany(orphanRemoval = true)` and `CascadeType.REMOVE`.**
    * *Answer*: `CascadeType.REMOVE` deletes children if the parent is deleted. `orphanRemoval = true` deletes children if the parent is deleted *and* if a child is removed from the parent's collection list.
22. **What are Transaction Propagation levels in Spring?**
    * *Answer*: Settings that define how transaction boundaries behave when one transactional method calls another. Common propagation settings include `REQUIRED`, `REQUIRES_NEW`, and `NESTED`.
23. **Explain the `REQUIRED` transaction propagation level (Default).**
    * *Answer*: If an active transaction exists, the method joins it. If no transaction exists, Spring creates a new one.
24. **Explain the `REQUIRES_NEW` propagation level.**
    * *Answer*: Spring always suspends any active transaction and creates a brand new independent transaction, ensuring it commits or rolls back independently of the caller.
25. **What are Transaction Isolation Levels?**
    * *Answer*: Settings that define the level of isolation between simultaneous transactions, protecting against anomalies like Dirty Reads, Non-Repeatable Reads, and Phantom Reads.
26. **What is a Dirty Read?**
    * *Answer*: An anomaly where Transaction A reads modified data from Transaction B before Transaction B commits. If Transaction B rolls back, Transaction A's read data is invalid.
27. **What is a LazyInitializationException and why does it happen?**
    * *Answer*: It occurs when you attempt to access a lazy-loaded relationship field outside of an active transaction session (after the EntityManager session has closed).
28. **How do you resolve a `LazyInitializationException`?**
    * *Answer*: By keeping the transaction open using `@Transactional`, eager fetching the relation using `JOIN FETCH` in the query, or loading the relationship fields before closing the session.
29. **What does `@Transactional(noRollbackFor = CustomException.class)` do?**
    * *Answer*: It prevents the transaction from executing a rollback if `CustomException` is thrown during the transaction flow.
30. **Explain how `@ManyToMany` relationships handle cascades.**
    * *Answer*: Cascade delete (`CascadeType.REMOVE`) should rarely be used on `@ManyToMany` because deleting a course should not automatically delete the students registered for it.
31. **What is the difference between `JoinColumn` and `mappedBy`?**
    * *Answer*: `JoinColumn` defines the foreign key column properties in the current entity. `mappedBy` delegates database column management to the opposite entity.
32. **Explain the `MANDATORY` propagation level.**
    * *Answer*: The method must be executed within an active transaction. If no transaction is active, it throws a `TransactionRequiredException`.
33. **Explain the `NEVER` propagation level.**
    * *Answer*: The method must run without any active transaction. If a transaction is active, it throws an exception.
34. **What is the difference between `@Transactional` rollback on checked vs unchecked exceptions?**
    * *Answer*: By default, Spring rolls back on unchecked (runtime) exceptions but commits on checked exceptions. You must explicitly configure `rollbackFor` to change this.
35. **What is Isolation Level `READ_COMMITTED`?**
    * *Answer*: An isolation level that prevents Dirty Reads. A transaction can only read data that has already been committed by other transactions.
36. **Explain the Phantom Read anomaly.**
    * *Answer*: It occurs when Transaction A executes a query matching a range of rows. Transaction B inserts a new row in that range and commits. When Transaction A re-runs the query, it sees "phantom" rows.
37. **What isolation level prevents all anomalies including Phantom Reads?**
    * *Answer*: **`SERIALIZABLE`** (the highest isolation level, which locks ranges of rows, making transactions execute sequentially).
38. **How do database locks relate to Isolation levels in Spring?**
    * *Answer*: Higher isolation levels require the database engine to acquire more locks on tables or rows, which increases consistency but reduces application performance.
39. **What is the difference between `@Transactional` from `org.springframework.transaction.annotation` and `javax.transaction.Transactional`?**
    * *Answer*: The Spring annotation has more features (like propagation and isolation settings), while the `javax` annotation is the standard Java specification.
40. **How does Spring AOP apply transactions to methods?**
    * *Answer*: Spring wraps the transactional bean in a proxy. When a method is called, the proxy intercepts the call, starts a database connection transaction, executes the method, and then commits or rolls back based on success.

### 🔴 Advanced Questions (41-50)

41. **Why does `@Transactional` fail to execute when a method calls another method inside the same class?**
    * *Answer*: Because transactions are applied using dynamic proxies. Internal method calls (self-invocation) bypass the proxy wrapper, meaning the transaction interception code is never executed.
42. **How do you solve the transaction self-invocation issue?**
    * *Answer*: By moving the transactional method into a separate Spring bean, or using programmatic transactions using `TransactionTemplate` inside the class.
43. **Explain the difference between physical transactions and logical transactions in Spring.**
    * *Answer*: A physical transaction is the actual connection socket lock on the database. A logical transaction is a Spring boundary. Multiple nested logical transactions (REQUIRED propagation) run inside a single physical transaction.
44. **What happens if a logical transaction rolls back inside a nested `REQUIRED` structure?**
    * *Answer*: The entire transaction is marked as rollback-only. When the outer transaction attempts to commit, Spring will throw an `UnexpectedRollbackException`.
45. **How does the `TransactionTemplate` facilitate programmatic transaction management?**
    * *Answer*: It wraps database calls inside a callback function: `transactionTemplate.execute(status -> { ... })`, allowing granular, runtime control over commits and rollbacks without annotations.
46. **Explain the performance impact of keeping a Transaction open during long external HTTP calls.**
    * *Answer*: The transaction holds a database connection pool socket open. If the HTTP call takes 10 seconds, the connection is blocked, starving other threads and potentially exhausting the connection pool.
47. **What is the role of `PlatformTransactionManager` in Spring?**
    * *Answer*: It is the central SPI (Service Provider Interface) that coordinates database transactions (e.g. `DataSourceTransactionManager` handles JDBC, `JpaTransactionManager` handles JPA).
48. **Explain the isolation level `REPEATABLE_READ`.**
    * *Answer*: It guarantees that if a transaction reads a row, re-reading the same row later will return the exact same data, preventing both Dirty Reads and Non-Repeatable Reads.
49. **How would you configure a transaction to time out after 5 seconds?**
    * *Answer*: By configuring the timeout attribute: `@Transactional(timeout = 5)`. If the transaction exceeds this duration, it throws a `TransactionTimedOutException`.
50. **Explain how JPA's Entity States translate to database queries when a transaction commits.**
    * *Answer*: During commit, the EntityManager flushes changes. Entities in the **Managed** state are analyzed using dirty checking, generating updates. **Removed** entities trigger DELETE statements, and **New** entities trigger INSERTs.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 07: Spring Data JPA & Hibernate](07_spring_data_jpa.md)
* **Next Chapter**: [👉 Topic 09: External Configuration & Profiles](09_external_configuration_profiles.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
