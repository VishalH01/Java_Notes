# 🤖 Topic 09: Views, Stored Procedures & Triggers

Welcome back, database automation engineer! In this chapter, we will learn about **Views, Stored Procedures, and Triggers**. Databases can do more than just store records; they can automate tasks, secure data, and encapsulate logic. We will learn how to build secure virtual windows (Views), save reusable code blocks (Stored Procedures), and program automatic security traps (Triggers) that snap shut when data is modified.

---

## 🏠 The Big Picture & Real-Life Example

### 🏢 The High-Security Corporate Building
Imagine you are managing a secure bank office:
* **Views (The Security Window)**: You have a raw employee ledger table showing names, departments, and secret salaries.
  * You don't want the reception staff to see salaries.
  * You build a frosted-glass window (**View**) called `EmployeeDirectoryView`. It only displays Names and Departments, hiding the secret salary column. The staff query the View as if it were a real table, but their view is restricted!
* **Stored Procedures (The Keypad Macros)**: Instead of manually typing 10 steps to register a new employee (setting up payroll, badges, desks), you record all 10 steps into a single macro button called `RegisterNewEmployee(Name, Dept)`. You press the button, pass the name, and the database runs all steps automatically.
* **Triggers (The Mouse-Trap Alarm)**: You put an alarm sensor on the safe box door.
  * The moment someone edits the vault balance, the sensor automatically snaps shut and logs the action: *"Manager Bob edited vault balance at 12:00 PM"* (**Audit Trigger**). No one has to call the alarm code manually; it triggers itself on the action!

---

## 🔬 Let's Look Closer

### 1. Database Views
A View is a virtual table. It contains no physical data of its own. When you query a View, the database engine retrieves the saved query definition and merges it with your query, fetching data from the underlying physical tables. Used for security and simplification.

### 2. Stored Procedures
A Stored Procedure is a compiled block of SQL code saved in the database server. Unlike simple queries, procedures can:
* Accept input parameters (`IN`) and return output values (`OUT`).
* Perform logic using loops and `IF-ELSE` statements.
* Group multiple modifications (DML) into transactions.

### 3. Triggers
A Trigger is a database block of code that runs automatically when a DML event (`INSERT`, `UPDATE`, `DELETE`) occurs on a table:
* **BEFORE Trigger**: Runs *before* the modification is written to disk (used for validation).
* **AFTER Trigger**: Runs *after* the modification is written (used for audit logs).

---

## 💻 Code Sandbox

Let's write views, procedures, and triggers on an employee database.

### The Tables
**`employees`**
| emp_id | name | department | salary |
|---|---|---|---|
| 1 | Alice | HR | 50000.00 |

**`audit_logs`**
| log_id | action | change_date |
|---|---|---|
| (auto) | VARCHAR | TIMESTAMP |

### 1. Creating a View (Security Masking)
```sql
-- Creates a view that masks the secret salary column
CREATE VIEW employee_directory AS 
SELECT emp_id, name, department 
FROM employees;

-- Query the view just like a normal table!
SELECT * FROM employee_directory;
```

### 2. Creating a Stored Procedure
```sql
-- Creating a procedure to give a salary raise
CREATE PROCEDURE GiveRaise(IN employee_id INT, IN raise_amount DECIMAL(10,2))
BEGIN
    UPDATE employees 
    SET salary = salary + raise_amount 
    WHERE emp_id = employee_id;
END;

-- To execute this procedure:
CALL GiveRaise(1, 500.00);
```

### 3. Creating an AFTER UPDATE Trigger (Audit Logging)
```sql
-- Creates a trigger that logs every salary change automatically
CREATE TRIGGER log_salary_update
AFTER UPDATE ON employees
FOR EACH ROW
BEGIN
    -- We can check the OLD and NEW values of the columns!
    IF OLD.salary <> NEW.salary THEN
        INSERT INTO audit_logs (action, change_date) 
        VALUES (CONCAT('Employee ', NEW.name, ' salary changed from ', OLD.salary, ' to ', NEW.salary), NOW());
    END IF;
END;
```

---

## 🧠 Points to Remember

* Views contain no physical data on disk. When you query a View, the database must still scan the underlying physical tables.
* Triggers can cause hidden performance problems. If a trigger executes slow SQL queries, every single `INSERT` or `UPDATE` statement on that table will wait for the trigger to finish, slowing down the application.
* Standard stored procedures cannot be called inside a `SELECT` statement. If you want a database function that can be queried inside SELECT, you must create a **User Defined Function (UDF)**.

---

## 📖 Key Definitions

* **View**: A virtual table representing the result set of a saved SQL query, containing no physical data of its own.
* **Stored Procedure**: A precompiled collection of SQL statements saved in the database server that can be executed on command, supporting parameters and logic.
* **Trigger**: A database object that automatically executes a block of SQL code in response to a DML event (INSERT, UPDATE, or DELETE) on a table.
* **IN/OUT Parameters**: Parameters in a stored procedure used to pass input values in (IN) or return result values back out (OUT).
* **INSTEAD OF Trigger**: A special type of trigger used on Views that intercepts the DML request and executes custom statements instead.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is a View in SQL?**
   * *Answer*: A virtual table created by saving a SQL query definition. It contains no physical data of its own.
2. **What is a Stored Procedure?**
   * *Answer*: A compiled collection of SQL statements saved on the database server that can be called repeatedly.
3. **What is a database Trigger?**
   * *Answer*: A special block of SQL code that executes automatically in response to a specific database event (INSERT, UPDATE, or DELETE).
4. **What is the difference between a Stored Procedure and a Query?**
   * *Answer*: A query is sent and compiled every time it runs. A stored procedure is precompiled on the server, saving parsing overhead and network traffic.
5. **Name three events that can fire a Trigger.**
   * *Answer*: `INSERT`, `UPDATE`, and `DELETE`.
6. **What is the difference between a View and a Table?**
   * *Answer*: A table stores physical data on disk. A view is a virtual window that reads data from underlying tables dynamically.
7. **What does the `CALL` keyword do?**
   * *Answer*: It is used to execute a stored procedure.
8. **What are the differences between BEFORE and AFTER triggers?**
   * *Answer*: `BEFORE` triggers run before data changes are written to disk (good for validation). `AFTER` triggers run after data is written (good for logging).
9. **How do you delete a Stored Procedure?**
   * *Answer*: Using the `DROP PROCEDURE procedure_name;` command.
10. **How do you delete a View?**
    * *Answer*: Using the `DROP VIEW view_name;` command.
11. **Can a view read data from multiple tables?**
    * *Answer*: Yes, a view's definition query can contain `JOIN` statements to merge data from multiple tables.
12. **What is the difference between `IN` and `OUT` parameters in procedures?**
    * *Answer*: `IN` parameters pass values into the procedure. `OUT` parameters return values back to the caller.
13. **What are the `OLD` and `NEW` qualifiers in triggers?**
    * *Answer*: Special keywords used to reference column states: `OLD` contains the data *before* modification; `NEW` contains the data *after* modification.
14. **Does an `INSERT` trigger have access to `OLD` values?**
    * *Answer*: No, because there is no previous record. `OLD` references in an insert trigger will return `NULL`.
15. **Does a `DELETE` trigger have access to `NEW` values?**
    * *Answer*: No, because there is no new record. `NEW` references in a delete trigger will return `NULL`.
16. **What does the `CREATE OR REPLACE VIEW` command do?**
    * *Answer*: It creates a view if it doesn't exist, or modifies the query definition of an existing view without dropping it.
17. **Can a Stored Procedure call another Stored Procedure?**
    * *Answer*: Yes, a procedure can call other procedures sequentially.
18. **Can you modify data inside a View?**
    * *Answer*: Yes, if the view is "updatable" (references a single table without aggregation or grouping). Updating the view modifies the underlying table.
19. **What is a User Defined Function (UDF)?**
    * *Answer*: A database function that accepts parameters, runs logic, and returns a single value, and can be used directly inside `SELECT` clauses.
20. **Can a Trigger call a Stored Procedure?**
    * *Answer*: Yes, a trigger can execute a procedure call when fired.

### 🟡 Intermediate Questions (21-40)

21. **What is the difference between a Stored Procedure and a User Defined Function (UDF)?**
    * *Answer*: A stored procedure cannot be called inside a `SELECT` statement and can perform transactions (COMMIT/ROLLBACK). A UDF can be called inside `SELECT` but cannot perform transactions or modify database state.
22. **What is a Materialized View?**
    * *Answer*: A special view that physically computes and stores its query results on disk (like a physical table) to optimize performance, requiring manual or automated refreshes when the base tables change.
23. **What is the `WITH CHECK OPTION` clause in a View definition?**
    * *Answer*: A constraint on updatable views. It prevents you from inserting or updating rows via the view if the modified row would no longer meet the view's `WHERE` filter.
24. **How do you handle error conditions inside Stored Procedures?**
    * *Answer*: By declaring custom handlers using `DECLARE CONTINUE HANDLER` or `DECLARE EXIT HANDLER` to catch SQL errors and execute rollbacks.
25. **What is the difference between Statement-level Triggers and Row-level Triggers?**
    * *Answer*: A **Row-level Trigger** (`FOR EACH ROW`) fires once for every single row affected by a query. A **Statement-level Trigger** fires exactly once for the entire query statement, regardless of how many rows are modified.
26. **Explain the performance risk of Recursive Triggers.**
    * *Answer*: A recursive trigger occurs when a trigger on Table A updates Table B, which in turn has a trigger updating Table A. This creates an infinite loop that crashes the database session.
27. **What is an `INSTEAD OF` Trigger?**
    * *Answer*: A trigger used exclusively on Views. It intercepts DML modifications and runs custom SQL instructions instead, allowing developers to make complex joined views updatable.
28. **How does the cost-based optimizer execute queries on Views?**
    * *Answer*: It performs "View Merging". It merges the view's query definition with the outer query, compiling them into a single execution plan as if the view didn't exist.
29. **What is a "Deterministic Function"?**
    * *Answer*: A function that always returns the exact same output for the same input parameters (e.g. `ABS(x)`). Non-deterministic functions (like `NOW()`) return different values each time.
30. **Explain how stored procedures reduce network traffic in applications.**
    * *Answer*: Instead of sending 10 separate SQL queries back and forth over the network, the application sends a single lightweight call: `CALL MyProc(1)`. The database executes all 10 queries locally on the server.
31. **Explain the security benefits of using Stored Procedures.**
    * *Answer*: Developers can revoke direct `SELECT` or `UPDATE` permissions on raw tables from application users, granting them access *only* to call specific stored procedures, protecting tables from unauthorized access.
32. **What is the risk of using Triggers for critical business logic?**
    * *Answer*: Triggers are "invisible code". They run silently behind the scenes. If a trigger has a bug or introduces locking, it is hard to debug and locate because the application code only sees standard DML statements.
33. **What is a View Dependency conflict?**
    * *Answer*: A scenario where you modify or drop a column in a physical table, causing any views referencing that column to break and throw errors on query.
34. **How do you prevent view dependency breaks in SQL Server?**
    * *Answer*: By using the `WITH SCHEMABINDING` option, which blocks modifications to table columns that are referenced by active views.
35. **What is SQL injection protection in Stored Procedures?**
    * *Answer*: By using input parameters, the database engine treats parameter values as literals rather than executable SQL code, preventing SQL injection attacks naturally.
36. **Explain the purpose of the `PREPARE` statement.**
    * *Answer*: It allows compiling a dynamic SQL query string at runtime and executing it later inside a stored procedure.
37. **Can a Stored Procedure return a result set?**
    * *Answer*: Yes, a procedure can execute a `SELECT` statement, which outputs a cursor result set directly to the calling client.
38. **Explain the concept of cursor processing inside Stored Procedures.**
    * *Answer*: A cursor is a database object used to loop through query rows sequentially inside a stored procedure, processing rows one-by-one.
39. **What does the trigger keyword `REFERENCING` do?**
    * *Answer*: It allows renaming the transition tables containing `OLD` and `NEW` rows inside statement-level triggers.
40. **How do you view the SQL code of an existing stored procedure?**
    * *Answer*: By querying the system catalog schema (e.g. `SHOW CREATE PROCEDURE name;` or querying `information_schema.routines`).

### 🔴 Advanced Questions (41-50)

41. **Explain the internal execution and compilation difference between Stored Procedures, Functions, and Prepared Statements.**
    * *Answer*: **Stored Procedures** compile at creation and store their execution plan in the database cache. **Functions** compile similarly but must return a value. **Prepared Statements** compile dynamically during a database connection session and are discarded when the TCP socket closes.
42. **Why do massive AFTER INSERT triggers on high-frequency tables cause transaction log bloat (write-ahead log exhaustion)?**
    * *Answer*: Every row inserted triggers a second transaction branch. The trigger's DML statements are written sequentially to the write-ahead log (WAL). If the trigger has nested lookups, this multiplies the active log file size, causing I/O pauses.
43. **Explain how to make a complex View containing Joins and Group By columns updatable using INSTEAD OF Triggers.**
    * *Answer*: You create an `INSTEAD OF INSERT` trigger on the view. The trigger parses the inserted DTO columns, executes an INSERT into Table A, fetches the generated ID, and executes a second INSERT into Table B, masking the join complexity from the client.
44. **What is a Materialized View "Incremental Refresh" (Fast Refresh), and how does the engine track base table changes?**
    * *Answer*: Instead of recalculating the entire view, the engine creates a **Materialized View Log** on the base tables. The log tracks modified Row IDs. When a refresh occurs, the engine only applies the logged diff modifications, saving massive disk scans.
45. **Explain the difference in compilation between SQL Stored Procedures and Java/C Stored Procedures (External Routines).**
    * *Answer*: SQL procedures compile into database engine bytecode. External routines (Java classes run inside the DB) are loaded as dynamic libraries (JARs/DLLs) into an embedded JVM process inside the database space, communicating via internal memory sockets.
46. **What is "Definer Security" vs. "Invoker Security" in Stored Procedure execution privileges?**
    * *Answer*: **Definer (`SQL SECURITY DEFINER`)** executes the procedure using the security permissions of the user who created it. **Invoker (`SQL SECURITY INVOKER`)** executes using the permissions of the active user running the call.
47. **How does PostgreSQL handle trigger execution loops using Constraint Triggers?**
    * *Answer*: Constraint Triggers allow delaying trigger execution to the end of the transaction (`DEFERRABLE`), allowing all table DML edits to complete before validating constraints, breaking instant execution loops.
48. **Explain the internal locking behavior of an AFTER UPDATE trigger compared to the parent query transaction.**
    * *Answer*: The trigger executes *within the same transaction scope* as the parent DML query. Any rows updated by the trigger inherit the same exclusive write-locks, extending lock duration until the parent transaction commits or rolls back.
49. **How would you debug a Stored Procedure that runs quickly in development but causes CPU spikes on production due to stale statistics?**
    * *Answer*: I would use `EXPLAIN` on the procedure's queries, check if compilation caching is reusing a bad plan (Parameter Sniffing), run `ANALYZE` to update table stats, and add `WITH RECOMPILE` or `recompile` hints to force the engine to rebuild plans on production.
50. **What is a "Nested Transaction" inside Stored Procedures, and how do database engines physically implement them?**
    * *Answer*: Most engines do not support true physical nested transactions. They implement them using **Savepoints**. When a procedure starts a nested transaction, it writes a Savepoint. If the nested transaction fails, it executes a `ROLLBACK TO SAVEPOINT`, keeping the outer transaction active.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 08: Indexes & Query Optimization](08_indexes_optimization.md)
* **Next Chapter**: [👉 Topic 10: Window Functions](10_window_functions.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
