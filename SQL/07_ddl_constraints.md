# 🏗️ Topic 07: Table Design & Constraints (DDL)

Welcome back, database designer! In this chapter, we will learn about **DDL (Data Definition Language)** and **Constraints**. Before you can query or modify data, you must design the database tables. A good table design enforces rules (constraints) to prevent bad data from ever entering your database—like making sure emails are unique, ages are positive numbers, and order records point to valid customer IDs. We will master creating, altering, and dropping tables, and how to configure key constraints.

---

## 🏠 The Big Picture & Real-Life Example

### 📐 The Board Game Blueprint
Imagine you are designing a physical board game:
* **`CREATE TABLE` (Drawing the Board)**: You draw a grid box on a paper card. You label the grid columns: Player ID, Character Class, and Score.
* **Constraints (The Game Rules)**: You write strict rules on the board to prevent players from cheating:
  * **`NOT NULL`**: You cannot leave your name blank!
  * **`UNIQUE`**: No two players can choose the exact same username.
  * **`PRIMARY KEY`**: The Player ID must be a unique number to identify who is who.
  * **`FOREIGN KEY`**: If a player card points to a *Character Card*, that Character Card must actually exist in the Character deck! (No referencing made-up cards).
  * **`CHECK`**: Your starting level must be greater than or equal to 1.
  * **`DEFAULT`**: If you don't declare a starting score, you start with 0 points.
* **`ALTER TABLE` (Modifying the Board)**: Mid-game, you decide to add a new column to the grid: Player Status.
* **`DROP TABLE` (Burning the Board)**: You throw the paper board in the fireplace, destroying the game layout and all records!

---

## 🔬 Let's Look Closer

### 1. Table Schema Operations (DDL)
* **`CREATE TABLE`**: Builds a new table structure defining column names and their data types.
* **`ALTER TABLE`**: Modifies an existing table structure (adding columns, dropping columns, or modifying data types).
* **`DROP TABLE`**: Removes the table and all its data permanently.

### 2. Column Constraints
Constraints are rules enforced by the database engine at the column level:
* **`PRIMARY KEY`**: Combines `NOT NULL` and `UNIQUE` to uniquely identify rows.
* **`FOREIGN KEY`**: Enforces referential integrity. Ensures values in a column match values in another table's primary key.
* **`UNIQUE`**: Guarantees all values in a column are distinct.
* **`NOT NULL`**: Blocks `NULL` inputs.
* **`CHECK`**: Validates a condition (e.g. `CHECK (age >= 18)`).
* **`DEFAULT`**: Assigns a fallback value if none is specified during an insert.

---

## 💻 Code Sandbox

Let's design a clean table blueprint with multiple constraints.

### 1. Creating a Table with Constraints
```sql
-- Create a table for users
CREATE TABLE users (
    user_id INT PRIMARY KEY,                       -- Unique identifier
    username VARCHAR(50) NOT NULL UNIQUE,          -- Cannot be blank, must be unique
    email VARCHAR(100) NOT NULL,
    age INT CHECK (age >= 18),                     -- Must be 18 or older
    status VARCHAR(20) DEFAULT 'Pending',          -- Fallback status
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Current date/time
);
```

### 2. Creating a Child Table with a Foreign Key
```sql
-- Create a table for orders linked to users
CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    user_id INT,
    amount DECIMAL(10,2) NOT NULL,
    -- Foreign Key links orders to users table
    -- ON DELETE CASCADE deletes all user orders if the user record is deleted!
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

### 3. Modifying an Existing Table (ALTER)
```sql
-- Add a phone number column to the users table
ALTER TABLE users 
ADD phone_number VARCHAR(15);
```

### 4. Dropping a Table
```sql
-- Deletes the orders table and all its records permanently
DROP TABLE orders;
```

---

## 🧠 Points to Remember

* A table can have only **one** `PRIMARY KEY`, but it can have multiple `UNIQUE` constraints.
* The `ON DELETE CASCADE` rule is powerful but dangerous. If you delete a customer row, the database will silently delete millions of linked order rows automatically.
* Altering columns on tables containing millions of rows can block other queries and lock the table for hours, causing application downtime.

---

## 📖 Key Definitions

* **DDL (Data Definition Language)**: SQL commands used to define, modify, and drop database schemas and object structures (`CREATE`, `ALTER`, `DROP`).
* **Constraint**: A declarative rule enforced on a table column to restrict the type or values of data that can be inserted (e.g. NOT NULL, UNIQUE).
* **Primary Key**: A column constraint that uniquely identifies each record in a table, prohibiting null values.
* **Foreign Key**: A column constraint that establishes a link between tables by referencing the primary key of another table.
* **CHECK Constraint**: A validation constraint used to enforce that values inserted into a column must satisfy a boolean expression (e.g. age >= 18).

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is DDL?**
   * *Answer*: Data Definition Language, SQL commands used to define and modify database structures (e.g. CREATE, ALTER, DROP).
2. **What does the `CREATE TABLE` command do?**
   * *Answer*: It defines and creates a new table structure inside the database schema.
3. **What is a database constraint?**
   * *Answer*: A declarative rule placed on a table column to restrict the types of data values permitted during inserts or updates.
4. **What is the difference between a Primary Key and a Foreign Key?**
   * *Answer*: A primary key uniquely identifies rows inside a table. A foreign key references the primary key of another table, linking the records.
5. **What does the `NOT NULL` constraint do?**
   * *Answer*: It prevents a column from accepting NULL values.
6. **What does the `UNIQUE` constraint do?**
   * *Answer*: It guarantees that all values in a column (or combination of columns) are distinct.
7. **What does the `CHECK` constraint do?**
   * *Answer*: It enforces that values in a column must satisfy a specific logical condition (e.g., salary > 0).
8. **What does the `DEFAULT` constraint do?**
   * *Answer*: It defines a default value to be inserted into a column if no value is provided.
9. **How do you add a new column to an existing table?**
   * *Answer*: By using the `ALTER TABLE` command with the `ADD` keyword (e.g. `ALTER TABLE users ADD age INT;`).
10. **How do you delete a column from a table?**
    * *Answer*: By using the `ALTER TABLE` command with the `DROP COLUMN` keyword.
11. **What is the difference between `DROP TABLE` and `TRUNCATE TABLE`?**
    * *Answer*: `DROP TABLE` deletes both the table structure and its rows. `TRUNCATE TABLE` deletes only the rows, keeping the table structure intact.
12. **Can a table have multiple primary keys?**
    * *Answer*: No, a table can have only one primary key (though it can be a composite key consisting of multiple columns).
13. **Can a table have multiple unique keys?**
    * *Answer*: Yes, a table can have as many unique constraints as needed.
14. **What does `ON DELETE CASCADE` do?**
    * *Answer*: A foreign key setting that automatically deletes child table rows if the referenced parent table row is deleted.
15. **What does `ON DELETE SET NULL` do?**
    * *Answer*: A setting that sets the foreign key column to NULL in child rows if the referenced parent row is deleted.
16. **How do you rename a table in SQL?**
    * *Answer*: Using `ALTER TABLE table_name RENAME TO new_name;` (exact syntax varies by RDBMS).
17. **What is a composite primary key?**
    * *Answer*: A primary key made of two or more columns combined to guarantee row uniqueness.
18. **What data type would you use to store precise money values in SQL?**
    * *Answer*: **`DECIMAL`** (or `NUMERIC`), because floating-point types (`FLOAT`, `DOUBLE`) suffer from rounding errors.
19. **What does the `VARCHAR` data type represent?**
    * *Answer*: Variable-length character string. It allocates only the actual length of the text entered, up to a specified maximum.
20. **What is the difference between `CHAR` and `VARCHAR`?**
    * *Answer*: `CHAR` is fixed-length (pads empty spaces with blanks). `VARCHAR` is variable-length (dynamic storage, no padding).

### 🟡 Intermediate Questions (21-40)

21. **Explain the physical storage layout of a table created on a Heap vs a Clustered Index.**
    * *Answer*: A **Heap table** stores rows in no specific physical order; new records are placed in any empty page slot. A **Clustered Index table** physically organizes and stores rows sorted by the primary key column values on disk.
22. **What is Referential Integrity and how is it enforced?**
    * *Answer*: A database rule requiring that foreign key columns must always reference valid, existing primary key values in the target table, preventing orphan records.
23. **What is a "Self-Referencing" Foreign Key?**
    * *Answer*: A foreign key column that points to the primary key of the same table (e.g. `manager_id` referencing `employee_id` in an employees table).
24. **Why is it recommended to place indexes on Foreign Key columns?**
    * *Answer*: RDBMS engines do not index foreign keys automatically. Creating indexes on them speeds up JOIN queries and prevents full scans when verifying referential integrity constraints during DML edits.
25. **What is the difference between Table-level constraints and Column-level constraints?**
    * *Answer*: Column-level constraints are defined next to the column type during declaration. Table-level constraints are declared at the end of the table script, required when defining composite keys or multi-column constraints.
26. **Explain the behavior of a UNIQUE constraint on NULL values.**
    * *Answer*: The SQL standard allows multiple NULL values in a UNIQUE column because NULL represents an unknown value. However, SQL Server uniquely restricts it to a single NULL value unless filtered.
27. **What is the cost of running `ALTER TABLE ADD COLUMN` on a table with 50 million rows?**
    * *Answer*: The engine must rewrite every data page on disk to allocate space for the new column, locking the table and blocking application read/write traffic.
28. **How do you define a CHECK constraint that validates email formatting?**
    * *Answer*: Using pattern matching: `CHECK (email LIKE '%@%.%')`.
29. **What does the `ALTER TABLE` option `DROP CONSTRAINT` do?**
    * *Answer*: It removes a named constraint (like a check or foreign key rule) without deleting the table columns.
30. **Explain the difference between `DROP TABLE` and `DROP TABLE ... CASCADE`.**
    * *Answer*: `DROP TABLE` will fail if other tables reference it via foreign keys. `DROP TABLE ... CASCADE` automatically drops the referencing foreign key constraints in child tables first.
31. **What is the difference between `VARCHAR(50)` and `VARCHAR(50 CHAR)` in Oracle/PostgreSQL?**
    * *Answer*: `VARCHAR(50)` might limit length to 50 bytes (which is less than 50 characters in UTF-8 multi-byte characters). `VARCHAR(50 CHAR)` guarantees storage for exactly 50 characters regardless of byte sizes.
32. **Explain the purpose of the `IDENTITY` (or `AUTO_INCREMENT` / `SERIAL`) column.**
    * *Answer*: An auto-managed sequence column that automatically generates a unique sequential integer value for every new row inserted.
33. **What is a "Metadata Lock" (MDL)?**
    * *Answer*: An internal lock acquired when executing DDL commands. It blocks DML queries (`SELECT`, `INSERT`) to prevent queries from reading a table structure while it is being altered.
34. **How do you add a default constraint to an existing column?**
    * *Answer*: Using `ALTER TABLE table_name ALTER COLUMN column_name SET DEFAULT value;` (syntax varies slightly by engine).
35. **What is a Constraint Violation Error?**
    * *Answer*: A database error thrown when an application tries to write or modify data that breaks a constraint (e.g. duplicate key in a unique column).
36. **Can a Foreign Key point to a non-primary key column?**
    * *Answer*: Yes, but the referenced column must be marked with a `UNIQUE` constraint, otherwise the database will reject the foreign key.
37. **What does the `EXCLUDE` constraint in PostgreSQL do?**
    * *Answer*: An advanced constraint that generalizes UNIQUE. It prevents overlapping values (e.g. preventing two booking schedules from overlapping on the same room).
38. **Explain the difference between `TIMESTAMP` and `TIMESTAMP WITH TIME ZONE`.**
    * *Answer*: `TIMESTAMP` stores raw dates without timezone offsets. `TIMESTAMP WITH TIME ZONE` automatically normalizes dates to UTC when saved and converts them to the client's local timezone when retrieved.
39. **What is the purpose of the `DESCRIBE` (or `sp_help`) command?**
    * *Answer*: A command-line utility used to view a table's schema, displaying column types, lengths, and active constraints.
40. **How do you define a constraint that restricts a status column to only accept 'Active', 'Pending', or 'Closed'?**
    * *Answer*: By using a `CHECK` constraint with the `IN` operator: `CHECK (status IN ('Active', 'Pending', 'Closed'))`.

### 🔴 Advanced Questions (41-50)

41. **Explain the physical storage layout and page splitting mechanism when a row is inserted into a table with a Clustered Index.**
    * *Answer*: The table is physically structured as a B-Tree. When a new row is inserted, it must land on a specific leaf data page in sorted key order. If that page is full, a **Page Split** occurs: the database allocates a new page, moves half the rows to the new page, and updates index branch pointers, causing slow disk I/O.
42. **Why does PostgreSQL's MVCC architecture make DDL table modifications like `ALTER TABLE ALTER COLUMN TYPE` extremely slow on large tables?**
    * *Answer*: Because PostgreSQL is MVCC-based, it cannot modify values in place. It must write a completely new physical version of every single row in the table to disk with the new type layout, rewriting the entire heap file and rebuilding all indexes.
43. **How do database engines handle DDL migrations online without blocking traffic (e.g., Online DDL in MySQL or pg_repack in Postgres)?**
    * *Answer*: They use shadow copying: (1) Create a new empty table with the modified schema. (2) Apply a trigger to the original table that replicates all DML modifications to the new table. (3) Bulk copy rows from the old table to the new one. (4) Acquire a brief metadata lock, rename the tables, and drop the old one.
44. **What is the internal execution difference between a CHECK constraint and a database Trigger for data validation?**
    * *Answer*: **CHECK constraints** are evaluated inline during the DML page write operation inside the engine's kernel (extremely fast). **Triggers** are stored routines that require spawning a context loop, executing custom SQL procedures, and checking variables, consuming far more CPU and memory.
45. **Explain the difference in locking and index structures between an inline PRIMARY KEY and a manually declared UNIQUE constraint.**
    * *Answer*: A primary key automatically creates a Clustered Index on most engines (sorting the table data pages). A UNIQUE constraint creates a Non-Clustered Index containing pointers to the heap or clustered table, leaving physical data order unaffected.
46. **What is a "Schema Drift" and how do developers handle it in production?**
    * *Answer*: A scenario where database schemas deviate from the expected structure (e.g., test has different column types than production). Managed using Schema Migration tools (like Liquibase or Flyway) that run versioned DDL scripts sequentially.
47. **How does the database engine verify Foreign Key constraints during bulk insert operations?**
    * *Answer*: For every row inserted into the child table, the engine executes a B-Tree index seek on the parent table to verify key existence. To speed up imports, developers sometimes temporarily disable constraint checking (`ALTER TABLE ... DISABLE TRIGGER ALL` or similar).
48. **Explain the security and isolation difference between a Database Schema and a Database User.**
    * *Answer*: A **User** is an authentication identity (login credentials). A **Schema** is a logical namespace containing database objects (tables, views). A user owns a schema, but multiple users can be granted access permissions to objects inside that schema.
49. **How would you write a DDL migration script to add a NOT NULL column to a table with 10 million rows without throwing validation errors?**
    * *Answer*:
      1. Add the column as nullable: `ALTER TABLE users ADD temp_col INT;`.
      2. Run updates in small batches to populate the column: `UPDATE users SET temp_col = 0 WHERE temp_col IS NULL;`.
      3. Apply the constraint: `ALTER TABLE users ALTER COLUMN temp_col SET NOT NULL;`.
50. **What is the purpose of the SQL standard `ON UPDATE CASCADE` rule?**
    * *Answer*: If a primary key value in the parent table is updated (changed), the database automatically propagates the new key value to all referencing foreign key columns in child tables, maintaining referential integrity.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 06: Data Modification & Transactions](06_dml_transactions.md)
* **Next Chapter**: [👉 Topic 08: Indexes & Query Optimization](08_indexes_optimization.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
