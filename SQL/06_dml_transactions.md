# ✍️ Topic 06: Data Modification & Transactions

Welcome back, data modifier! In this chapter, we will learn about **DML (Data Manipulation Language)** and **Transactions**. Databases are not static read-only files; applications must constantly add new users (`INSERT`), update passwords (`UPDATE`), and delete accounts (`DELETE`). We will learn how to write safe modification statements, understand why `DELETE` differs from `TRUNCATE`, and master Transactions, which prevent system crashes from corrupting your data.

---

## 🏠 The Big Picture & Real-Life Example

### 📝 The Pencil Draft vs. Permanent Ink
Imagine you are a bank clerk recording transactions in a ledger book:
* **`INSERT` (Adding a Line)**: You write a new customer's deposit on a line.
* **`UPDATE` (Fixing a Line)**: You change a customer's address details.
* **`DELETE` vs. `TRUNCATE` (Erasing lines vs. Replacing the Notebook)**:
  * **`DELETE`**: You take an eraser and rub out specific rows one-by-one. It takes time, and you can see the faint pencil marks (records are logged and can be recovered).
  * **`TRUNCATE`**: You rip out all the pages of the notebook and throw them in the trash! The notebook is instantly empty (extremely fast, cannot be easily undone).
* **Transactions (The Pencil Draft)**: A client wants to transfer $100 from Account A to Account B.
  * You write in pencil: *Subtract $100 from Account A*.
  * You write in pencil: *Add $100 to Account B*.
  * If the pen runs out of ink or the power goes out halfway, you erase the drafts (**`ROLLBACK`**). The bank balance remains safe.
  * If both steps are completed perfectly, you trace over them in permanent ink (**`COMMIT`**). The transfer is now permanent!

---

## 🔬 Let's Look Closer

### 1. Data Modification (DML)
* **`INSERT`**: Adds new rows to a table.
* **`UPDATE`**: Modifies existing columns. **Warning**: Always use a `WHERE` clause, or you will update every single row in the table!
* **`DELETE`**: Removes rows. Always use `WHERE` to target specific records.

### 2. DELETE vs. TRUNCATE
This is a standard database interview question:
* **`DELETE`**: A DML command. It deletes rows one-by-one, triggers delete events (triggers), and writes every deletion to the log file (slow).
* **`TRUNCATE`**: A DDL command. It empties the table instantly by deallocating the table's data pages from disk. It bypasses row logs and triggers (extremely fast).

### 3. Transaction Control (TCL)
A transaction is bounded by:
* **`BEGIN TRANSACTION`** (or `START TRANSACTION`): Starts the pencil draft.
* **`COMMIT`**: Saves changes permanently.
* **`ROLLBACK`**: Cancels and undoes all changes.

---

## 💻 Code Sandbox

Let's write safe data modification queries and transaction blocks.

### The Table: `accounts`
| account_id | owner | balance |
|---|---|---|
| 1 | Alice | 500.00 |
| 2 | Bob | 300.00 |

### 1. Inserting Data
```sql
-- Inserts a new record into the accounts table
INSERT INTO accounts (account_id, owner, balance) 
VALUES (3, 'Charlie', 1000.00);
```

### 2. Updating Data
```sql
-- Adds interest to Alice's account (Crucial: WHERE clause limits it to Alice!)
UPDATE accounts 
SET balance = balance + 50.00 
WHERE owner = 'Alice';
```

### 3. Deleting Data
```sql
-- Removes Charlie's account record
DELETE FROM accounts 
WHERE owner = 'Charlie';
```

### 4. Transaction Block (Safe Money Transfer)
```sql
-- Starts a protected transaction
BEGIN TRANSACTION;

-- Step 1: Deduct from Alice
UPDATE accounts 
SET balance = balance - 100.00 
WHERE owner = 'Alice';

-- Step 2: Add to Bob
UPDATE accounts 
SET balance = balance + 100.00 
WHERE owner = 'Bob';

-- Check: If anything went wrong (e.g. negative balances), we run ROLLBACK.
-- If everything is correct, we commit changes to disk:
COMMIT;
```

---

## 🧠 Points to Remember

* Running `UPDATE table SET column = value` without a `WHERE` clause is a catastrophic mistake. It will change that column for every row in the database.
* `TRUNCATE` cannot be rolled back in some database systems (like MySQL) because it executes DDL commands which commit transactions automatically.
* Transactions guarantee **Consistency** (C in ACID). If a database server loses power halfway through a transaction, it automatically rolls back the partial changes when it reboots.

---

## 📖 Key Definitions

* **DML (Data Manipulation Language)**: SQL commands used to manage and modify database records (`SELECT`, `INSERT`, `UPDATE`, `DELETE`).
* **Transaction**: A single logical unit of database work that contains one or more SQL commands and must succeed or fail as a whole.
* **COMMIT**: A transaction command that saves all changes made during the transaction permanently to the database disk.
* **ROLLBACK**: A transaction command that cancels and undoes all changes made during the active transaction, restoring the database to its pre-transaction state.
* **TRUNCATE**: A DDL command that removes all rows from a table quickly by deallocating its storage pages, bypassing individual row deletion logging.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is DML?**
   * *Answer*: Data Manipulation Language, SQL commands used to insert, update, delete, and retrieve data records.
2. **What does the `INSERT` command do?**
   * *Answer*: It adds one or more new rows of data into a database table.
3. **What is the risk of running an `UPDATE` query without a `WHERE` clause?**
   * *Answer*: It will modify the target columns for **every single row** in the table.
4. **What is the risk of running a `DELETE` query without a `WHERE` clause?**
   * *Answer*: It will delete all records inside the table.
5. **What is the difference between `DELETE` and `TRUNCATE`?**
   * *Answer*: `DELETE` removes rows one-by-one and logs each deletion (slow, DML). `TRUNCATE` empties the table instantly by deallocating storage pages (fast, DDL).
6. **What is a database transaction?**
   * *Answer*: A group of SQL queries executed as a single logical unit of work that must succeed completely or fail completely.
7. **What does the `COMMIT` command do?**
   * *Answer*: It saves all modifications made during the active transaction permanently to the database.
8. **What does the `ROLLBACK` command do?**
   * *Answer*: It cancels and undoes all modifications made during the current transaction, returning the database to its previous state.
9. **Name the four ACID properties of transactions.**
   * *Answer*: Atomicity, Consistency, Isolation, and Durability.
10. **What does "Atomicity" mean?**
    * *Answer*: The "all-or-nothing" rule. Either all queries inside a transaction succeed, or the entire transaction fails and is rolled back.
11. **What does "Durability" mean?**
    * *Answer*: Once a transaction is committed, its changes are written permanently to disk and will not be lost even during system crashes.
12. **How do you start a transaction in SQL?**
    * *Answer*: By executing `BEGIN TRANSACTION` or `START TRANSACTION`.
13. **Can you insert multiple rows in a single `INSERT` statement?**
    * *Answer*: Yes, by separating the value sets with commas: `INSERT INTO table VALUES (val1), (val2), (val3)`.
14. **What is the difference between `TRUNCATE` and `DROP TABLE`?**
    * *Answer*: `TRUNCATE` deletes all rows inside the table but keeps the table structure. `DROP TABLE` deletes both the rows and the table structure entirely from the database schema.
15. **What is Autocommit mode?**
    * *Answer*: A database mode where every single SQL command is treated as an individual transaction and committed automatically.
16. **How do you temporarily disable autocommit?**
    * *Answer*: By starting a transaction block (`START TRANSACTION;`).
17. **What happens if a database transaction is interrupted by a server crash?**
    * *Answer*: The database engine automatically executes a `ROLLBACK` when it restarts to clean up uncommitted changes.
18. **Can you update a column using its current value?**
    * *Answer*: Yes (e.g. `SET score = score + 1`).
19. **How do you copy all rows from one table into a new table in one query?**
    * *Answer*: Using `INSERT INTO table2 SELECT * FROM table1;`.
20. **What is a Savepoint?**
    * *Answer*: A marker within a transaction that allows you to roll back only a portion of the transaction rather than the whole thing.

### 🟡 Intermediate Questions (21-40)

21. **Explain the physical storage process of a DELETE command in an MVCC engine like PostgreSQL.**
    * *Answer*: The engine does not immediately erase the row from the disk page. It updates a metadata column (xmax) to mark the row as deleted. The space is only reclaimed later when the VACUUM process cleans up dead row versions.
22. **Why does TRUNCATE execute so much faster than DELETE on large tables?**
    * *Answer*: `DELETE` scans the table, writes every deleted row to the transaction log, and checks constraint locks for every row. `TRUNCATE` bypasses row checks and simply updates the database system catalog pointers, deallocating the table's disk extents instantly.
23. **What is the difference in logging overhead between DELETE and TRUNCATE?**
    * *Answer*: `DELETE` is a fully logged operation (logs every modified row byte). `TRUNCATE` is a minimally logged operation (logs only the page deallocation metadata).
24. **Can you rollback a TRUNCATE command?**
    * *Answer*: In PostgreSQL, SQL Server, and Oracle (inside transactions), yes, because page deallocations are logged. In MySQL, DDL statements like `TRUNCATE` trigger an implicit commit, making rollbacks impossible.
25. **What is "Dirty Read" in transaction isolation?**
    * *Answer*: A concurrency issue where Transaction A reads modifications made by Transaction B before Transaction B has committed. If Transaction B rolls back, Transaction A's data becomes invalid.
26. **What is "Non-Repeatable Read"?**
    * *Answer*: An isolation issue where Transaction A reads a row value, Transaction B updates that row and commits, and Transaction A reads the same row again, getting a different value.
27. **What is "Phantom Read"?**
    * *Answer*: An isolation issue where Transaction A runs a query returning a count of rows, Transaction B inserts new rows and commits, and Transaction A runs the same query again, getting a different count.
28. **Name the four SQL standard transaction isolation levels in order of increasing isolation.**
    * *Answer*: Read Uncommitted, Read Committed, Repeatable Read, and Serializable.
29. **What is the default isolation level in MySQL InnoDB?**
    * *Answer*: **Repeatable Read**.
30. **What is the default isolation level in PostgreSQL and Oracle?**
    * *Answer*: **Read Committed**.
31. **What is a Write Lock (Exclusive Lock) vs a Read Lock (Shared Lock)?**
    * *Answer*: A **Shared Lock** allows multiple transactions to read a row but blocks writes. An **Exclusive Lock** allows one transaction to write a row, blocking all other reads and writes.
32. **What is a Deadlock?**
    * *Answer*: A concurrency crash where Transaction A holds Lock 1 and waits for Lock 2, while Transaction B holds Lock 2 and waits for Lock 1. Both wait forever until the database engine terminates one.
33. **How does a database engine detect and resolve deadlocks?**
    * *Answer*: It runs a background detection thread checking lock graphs for cycles. If a cycle (deadlock) is found, it terminates one of the transactions (the "victim"), rolls back its changes, and throws an error.
34. **What is the purpose of the `RETURNING` clause in DML?**
    * *Answer*: It allows a query that inserts, updates, or deletes rows to return the modified column values directly to the application in the same execution (e.g. `INSERT ... RETURNING id`).
35. **What is the difference between `DELETE` and `TRUNCATE` regarding identity columns (auto-increments)?**
    * *Answer*: `TRUNCATE` resets the auto-increment seed back to its initial value (usually 1). `DELETE` keeps the current seed state (e.g., if you delete row 10, the next insert is still 11).
36. **Explain the behavior of DML commands on tables with Foreign Key constraints.**
    * *Answer*: If a child table has a foreign key pointing to a parent row, deleting the parent row will fail unless the foreign key is configured with `ON DELETE CASCADE` (automatically deletes child rows) or `ON DELETE SET NULL`.
37. **What is a "Lost Update" concurrency conflict?**
    * *Answer*: A conflict where Transaction A and Transaction B read the same row, make modifications, and Transaction B commits, overwriting and erasing the updates committed by Transaction A.
38. **Explain the purpose of Pessimistic Locking.**
    * *Answer*: A lock strategy where you actively lock rows during read queries using `FOR UPDATE`, blocking other transactions from modifying them until your transaction completes.
39. **Explain the purpose of Optimistic Locking.**
    * *Answer*: A strategy where you do not lock rows. Instead, you add a `version` column. When updating, you verify the version: `WHERE id = 1 AND version = current_version`. If another transaction modified it first, the version check fails, and the application retries.
40. **What does the SQL command `MERGE` (or UPSERT) do?**
    * *Answer*: It combines `INSERT` and `UPDATE` into one statement, checking if a record exists. If yes, it updates it; if no, it inserts a new row.

### 🔴 Advanced Questions (41-50)

41. **Explain the physical logging architecture difference between Undo Logs and Redo Logs in transaction recovery.**
    * *Answer*: **Redo Logs (Write-Ahead Logs)** record every change committed, used to replay changes to database files during startup (roll-forward recovery). **Undo Logs** store copies of the original data before modifications, used to revert uncommitted changes during rollbacks or provide older read versions in MVCC (roll-back recovery).
42. **How does Multi-Version Concurrency Control (MVCC) eliminate read-write blocking?**
    * *Answer*: Instead of lock tables during reads, MVCC preserves older versions of modified rows in the Undo log or table heap. When Transaction A writes, it creates a new version. If Transaction B reads, it reads the older committed version, ensuring **readers never block writers, and writers never block readers**.
43. **Explain how "Phantom Reads" are prevented in Repeatable Read isolation level in MySQL InnoDB vs PostgreSQL.**
    * *Answer*: In PostgreSQL, MVCC naturally captures a snapshot at the start of the query transaction, ignoring new inserts. In MySQL, InnoDB uses **Next-Key Locking** (combining index locks and gap locks) to physically block inserts into the empty spaces between index keys, preventing phantom inserts.
44. **What is the performance and locking difference between row-level locks and table-level locks?**
    * *Answer*: **Row-level locks** provide high concurrency (other threads access other rows) but require high memory overhead to track millions of locks. **Table-level locks** use minimal memory but block all other threads, reducing concurrency to zero.
45. **What is Lock Escalation?**
    * *Answer*: An optimization process where the engine converts thousands of individual row-level locks into a single table-level lock when a transaction modifies a large percentage of a table, saving memory but reducing concurrency.
46. **Explain the two-phase commit (2PC) protocol used in distributed transactions.**
    * *Answer*:
      * **Phase 1: Prepare**: The coordinator node asks all database nodes if they are ready to commit. Nodes write to log files and vote "Yes".
      * **Phase 2: Commit**: If all vote yes, the coordinator sends the commit signal. If any vote no, it sends the rollback signal to all nodes.
47. **How does the cost-based optimizer decide whether to use a partition truncate over a standard table truncate?**
    * *Answer*: If the table is partitioned (split into files by date or range) and the query targets a specific partition, the engine can deallocate only that partition's disk pages, keeping other partitions completely untouched.
48. **Explain the difference between Optimistic Concurrency Control (OCC) and Multi-Version Concurrency Control (MVCC) in high-write databases.**
    * *Answer*: OCC assumes conflicts are rare, allowing writes in memory and verifying at commit time (restarting on conflicts). MVCC manages multiple physical row versions and uses write-locks to serialize concurrent updates to the same row immediately.
49. **How would you debug a database that hangs during peak hours due to "Lock Wait Timeout" errors?**
    * *Answer*: I would check active sessions (`pg_stat_activity` or `SHOW ENGINE INNODB STATUS`) to locate long-running transactions that are holding lock tables, verify if missing indexes are causing updates to perform table scans (locking every row in the table), and optimize application code to commit transactions faster.
50. **What is the purpose of the Transaction Isolation Level "Read Uncommitted" in high-throughput analytical databases?**
    * *Answer*: It bypasses all read locks, allowing queries to read uncommitted "dirty" data. This eliminates read blocking completely and maximizes scan speeds, acceptable for analytical aggregations where absolute precision is secondary.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 05: Subqueries & CTEs](05_subqueries_ctes.md)
* **Next Chapter**: [👉 Topic 07: Table Design & Constraints (DDL)](07_ddl_constraints.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
