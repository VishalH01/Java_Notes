# 🧬 Topic 05: Subqueries & CTEs

Welcome back, query nested architect! In this chapter, we will learn about **Subqueries & Common Table Expressions (CTEs)**. In complex database work, you often need to solve problems in steps: *"Find the average price of products, then list all products that cost more than that average."* Or, you want to clean up massive queries by saving temporary steps. We will master nested queries, the mystery of Correlated Subqueries, and how to write elegant, readable queries using CTEs.

---

## 🏠 The Big Picture & Real-Life Example

### 🪆 The Nesting Dolls & The Scratchpad (Subqueries & CTEs)
Imagine you are a detective investigating a bank ledger:
* **Subquery (Russian Nesting Dolls)**: You open a doll, and inside is another smaller doll.
  * You ask: *"Who is the youngest student in the class?"*
  * **Step 1 (Inner Doll)**: You query the database: *"What is the minimum age?"* (Result: 18).
  * **Step 2 (Outer Doll)**: You query again: *"Who is the student with age = 18?"*
  * You pack both steps into one query: The inner query (the subquery) runs first to find the age 18, and hands it to the outer query.
* **Correlated Subquery (The Callback)**: The inner doll keeps whispering to the outer doll. For *every single* customer, the helper reads their country, then runs a subquery to compare their balance against the average of *their specific* country.
* **CTE (The Blackboard Scratchpad)**: Instead of nesting dolls, you write a temporary calculation on a blackboard, name it `AverageSalaryTable`, and reference it in your final query. Once you walk out of the room (query finishes), the blackboard is wiped clean!

---

## 🔬 Let's Look Closer

### 1. Subqueries
A subquery is a query nested inside a parent query. It can appear in `SELECT`, `FROM`, `WHERE`, or `HAVING`:
* **Scalar Subquery**: Returns exactly one row and one column (a single value).
* **Multi-row Subquery**: Returns a list of values (used with `IN`, `ANY`, `ALL`, or `EXISTS`).
* **Correlated Subquery**: References a column from the outer query. It is dependent on the outer row, running once for every row processed.

### 2. Common Table Expressions (CTEs)
A CTE is a temporary named result set. You define it at the very top of your query using the **`WITH`** keyword. It acts like a virtual table, making code clean, readable, and reusable:
```sql
WITH MyTemporaryTable AS (
    SELECT ...
)
SELECT * FROM MyTemporaryTable;
```

---

## 💻 Code Sandbox

Let's write subqueries and CTEs on a mock employee salaries database.

### The Table: `employees`
| id | name | department | salary |
|---|---|---|---|
| 1 | Alice | HR | 50000.00 |
| 2 | Bob | IT | 80000.00 |
| 3 | Charlie | IT | 95000.00 |
| 4 | David | Sales | 45000.00 |

### 1. Scalar Subquery in WHERE
```sql
-- Finds employees earning more than the company average
SELECT name, salary 
FROM employees 
WHERE salary > (SELECT AVG(salary) FROM employees);
```

### 2. Correlated Subquery
```sql
-- Finds employees who earn more than the average of THEIR SPECIFIC department
SELECT e.name, e.department, e.salary 
FROM employees e 
WHERE e.salary > (
    SELECT AVG(d.salary) 
    FROM employees d 
    WHERE d.department = e.department -- Reference to outer table 'e'
);
```

### 3. Subquery with EXISTS
```sql
-- Finds departments that have at least one employee earning > $90,000
SELECT DISTINCT department 
FROM employees e 
WHERE EXISTS (
    SELECT 1 
    FROM employees sub 
    WHERE sub.department = e.department AND sub.salary > 90000.00
);
```

### 4. Common Table Expression (CTE)
```sql
-- Computes average department salaries first, then joins it to list employees
WITH DeptAverages AS (
    SELECT department, AVG(salary) AS avg_salary 
    FROM employees 
    GROUP BY department
)
SELECT e.name, e.department, e.salary, da.avg_salary 
FROM employees e 
INNER JOIN DeptAverages da ON e.department = da.department 
WHERE e.salary > da.avg_salary;
```

---

## 🧠 Points to Remember

* A scalar subquery must return *exactly* one value. If it returns multiple rows (e.g. `(SELECT salary FROM employees)`), the database will crash with: *"Subquery returned more than 1 row"*.
* Correlated subqueries can be very slow. Because they run once for every row in the outer table, a table of 100,000 rows will force the database to execute the subquery 100,000 times!
* CTEs make your code self-documenting and easier to debug because you can test each temporary block independently before assembling the query.

---

## 📖 Key Definitions

* **Subquery**: A nested query statement compiled inside another parent query (SELECT, INSERT, UPDATE, or DELETE).
* **Scalar Subquery**: A subquery that returns exactly one value (one row and one column).
* **Correlated Subquery**: A subquery that references columns from the outer parent query, executing once for every row processed by the outer query.
* **Common Table Expression (CTE)**: A temporary named result set defined using the WITH clause, active only during the execution of a single query.
* **EXISTS**: A SQL operator that returns true if a subquery returns one or more matching rows, stopping search immediately upon finding a match.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is a subquery?**
   * *Answer*: A query nested inside another SQL statement (the parent outer query).
2. **What is a scalar subquery?**
   * *Answer*: A subquery that returns a single value (exactly one row and one column).
3. **What is a multi-row subquery?**
   * *Answer*: A subquery that returns a list of values (one column with multiple rows), used with operators like `IN` or `ANY`.
4. **What is a CTE?**
   * *Answer*: Common Table Expression, a temporary named result set declared using the `WITH` clause.
5. **What keyword is used to start a CTE?**
   * *Answer*: **`WITH`**.
6. **Where can a subquery be placed?**
   * *Answer*: Inside `SELECT`, `FROM`, `WHERE`, `HAVING`, `INSERT`, `UPDATE`, or `DELETE` statements.
7. **What happens if a scalar subquery returns 2 rows?**
   * *Answer*: The database throws a runtime error (e.g., "Subquery returned more than 1 row").
8. **What does the `EXISTS` operator do?**
   * *Answer*: It checks if a subquery returns any matching rows. It returns `TRUE` if the subquery returns at least one row, otherwise `FALSE`.
9. **What does `NOT EXISTS` do?**
   * *Answer*: It returns `TRUE` if the subquery returns zero rows.
10. **How do you name a CTE?**
    * *Answer*: `WITH cte_name AS (SELECT ... )`.
11. **Can a CTE be referenced multiple times in the same query?**
    * *Answer*: Yes, you can join or reference a defined CTE multiple times inside the main query.
12. **Is a CTE saved permanently in the database?**
    * *Answer*: No, a CTE is temporary and exists only during the execution duration of that specific query.
13. **What is a correlated subquery?**
    * *Answer*: A subquery that references columns from the outer query, making its execution dependent on the outer query's row processing loop.
14. **What operator is used to compare a value against a list returned by a subquery?**
    * *Answer*: The **`IN`** operator (or `ANY`/`ALL`).
15. **What is the difference between a subquery in the `WHERE` clause and a subquery in the `FROM` clause?**
    * *Answer*: A `WHERE` subquery acts as a search filter. A `FROM` subquery acts as a temporary virtual table that must be given an alias.
16. **What does `ANY` do?**
    * *Answer*: It returns true if the comparison is true for *at least one* value in the subquery list (equivalent to `OR`).
17. **What does `ALL` do?**
    * *Answer*: It returns true if the comparison is true for *every* value in the subquery list (equivalent to `AND`).
18. **Can you write a subquery inside a subquery?**
    * *Answer*: Yes, you can nest subqueries multiple levels deep, though this is hard to read and optimize.
19. **What is the purpose of naming a subquery in the `FROM` clause?**
    * *Answer*: To allow the outer query to reference its columns (e.g., `FROM (SELECT ...) AS temp`).
20. **Can you define multiple CTEs in a single query?**
    * *Answer*: Yes, by separating them with a comma under a single `WITH` keyword: `WITH cte1 AS (...), cte2 AS (...)`.

### 🟡 Intermediate Questions (21-40)

21. **What is the performance difference between a Correlated Subquery and a Non-Correlated Subquery?**
    * *Answer*: A non-correlated subquery runs exactly **once** first, caching its result. A correlated subquery runs **once for every row** in the outer table, leading to $O(N^2)$ execution costs if not optimized with indexes.
22. **How does `EXISTS` perform better than `IN` for subquery checks?**
    * *Answer*: `EXISTS` is a short-circuit operator. As soon as it finds a single matching row, it stops scanning and returns `TRUE`. `IN` must scan and load the entire list from the subquery into memory before filtering.
23. **What is the difference between a CTE and a View?**
    * *Answer*: A view is a permanent metadata definition saved in the database catalog. A CTE is temporary and discarded instantly after the query executes.
24. **What is the difference between a CTE and a Temporary Table?**
    * *Answer*: A temp table (e.g. `#temp`) is physically written to disk/memory (tempdb) and persists across multiple queries in the active session. A CTE is inline, does not create physical tables, and lasts for only one query statement.
25. **Can you write a Recursive CTE?**
    * *Answer*: Yes, by using `WITH RECURSIVE` (PostgreSQL/MySQL), which allows a CTE to reference itself, useful for traversing hierarchical tree structures (like hierarchies or bill of materials).
26. **Explain the two parts of a Recursive CTE.**
    * *Answer*: (1) The **Anchor Member**: A base query that returns the initial seed rows. (2) The **Recursive Member**: A query joined to the anchor using `UNION` or `UNION ALL` that executes repeatedly, referencing the CTE name until it returns empty.
27. **Why does `SELECT 1` or `SELECT *` inside an `EXISTS` subquery make no performance difference?**
    * *Answer*: The database engine ignores the `SELECT` column list inside `EXISTS` because it only cares if any row is returned. It evaluates only the query's `WHERE` filters.
28. **How does the cost-based optimizer optimize subqueries using "Subquery Unnesting"?**
    * *Answer*: The optimizer rewrites subqueries into joins (e.g. converting a `WHERE EXISTS` into a Hash Semi-Join), which allows it to rearrange the execution order and use indexes more effectively.
29. **What is a Inline View?**
    * *Answer*: Another name for a subquery placed inside the `FROM` clause (e.g. `FROM (SELECT ...) AS my_subquery`).
30. **Explain how `NOT EXISTS` differs from `LEFT JOIN ... WHERE right.key IS NULL` under the hood.**
    * *Answer*: Modern optimizers compile both to the same Anti-Semi-Join plan. Historically, `NOT EXISTS` was faster because it short-circuited, while the join created a wider intermediate table in memory.
31. **Can you perform `UPDATE` or `DELETE` using CTEs?**
    * *Answer*: Yes, you can define a CTE at the top and reference it inside the `UPDATE` or `DELETE` statement to target specific rows.
32. **Can you use `ORDER BY` inside a subquery?**
    * *Answer*: Standard SQL prohibits `ORDER BY` inside subqueries unless accompanied by `LIMIT` or `TOP`, because tables and subqueries represent unordered bags of data.
33. **Explain how the `WITH` clause is evaluated in PostgreSQL.**
    * *Answer*: Historically in PostgreSQL, CTEs acted as "optimization barriers", meaning the engine evaluated the CTE completely and wrote it to memory first. In recent versions, this is optimized, and filters can be pushed down into the CTE.
34. **What is a Lateral Subquery (or CROSS APPLY in SQL Server)?**
    * *Answer*: A correlated subquery in the `FROM` clause that can reference columns of preceding tables, acting like a loop to evaluate function results for every row.
35. **What is the difference between `IN` and `= ANY`?**
    * *Answer*: They are completely identical in SQL. `col IN (1, 2)` compiles to the same plan as `col = ANY(1, 2)`.
36. **Explain how `ALL` behaves if the subquery returns an empty set.**
    * *Answer*: If the subquery returns no rows, `col > ALL (subquery)` always evaluates to **`TRUE`**.
37. **Explain how `ANY` behaves if the subquery returns an empty set.**
    * *Answer*: If the subquery returns no rows, `col = ANY (subquery)` always evaluates to **`FALSE`**.
38. **Can you join two CTEs in a single query?**
    * *Answer*: Yes, you define both at the top and join them in your main SELECT block.
39. **What is a "Correlated Scalar Subquery" in the SELECT clause?**
    * *Answer*: A subquery placed in the SELECT list that runs for every row to pull a single value (e.g. `SELECT name, (SELECT SUM(amount) FROM orders WHERE user_id = u.id) FROM users u`).
40. **How does B-Tree indexing optimize correlated subqueries?**
    * *Answer*: It ensures that the filter keys in the subquery's `WHERE` clause are indexed. This converts the $O(N)$ nested loops into $O(log N)$ B-Tree seeks, preventing database hangs.

### 🔴 Advanced Questions (41-50)

41. **Explain the compilation and execution plan of a Correlated Subquery converted to a Hash Semi-Join.**
    * *Answer*: The optimizer unnests the subquery. It builds a hash table of the subquery table's keys. It then probes this hash table using keys from the outer table. As soon as a match is found, the outer row is immediately returned, achieving $O(N + M)$ time complexity.
42. **Why does the database engine sometimes fail to push a WHERE filter down into a CTE?**
    * *Answer*: If the CTE contains blocking operators (like `GROUP BY`, `DISTINCT`, or Window Functions), the engine cannot mathematically split the filter and push it inside. It must execute the CTE first, load the intermediate result set, and apply the filter afterward.
43. **How do Recursive CTEs protect themselves against infinite loops?**
    * *Answer*: Database engines enforce limits. In MySQL, the system variable `cte_max_recursion_depth` limits the iterations (default 1000). In SQL Server, the `MAXRECURSION` option is used. If exceeded, the engine crashes the query.
44. **What is the internal execution difference between Materialized CTEs and Inline CTEs?**
    * *Answer*: A **Materialized CTE** is executed once, and its output is written to a temporary storage buffer (spooled). An **Inline CTE** is treated as a macro; the optimizer pastes its SQL definition directly into the parent query, optimizing it as one unified syntax tree.
45. **Under what conditions will the optimizer choose a Merge Semi-Join over a Hash Semi-Join for an EXISTS subquery?**
    * *Answer*: If both the outer query table and the subquery table are sorted on the join key (due to clustered indexes or index seeks), it selects **Merge Semi-Join** because it can scan both tables in parallel and skip hashing.
46. **How does PostgreSQL's `AS NOT MATERIALIZED` hint modify CTE optimization barriers?**
    * *Answer*: It forces the optimizer to merge the CTE definition into the main query instead of spooling it, allowing outer `WHERE` conditions to be pushed down inside the CTE for faster execution.
47. **What is a "decorrelation" rewrite in query optimizers?**
    * *Answer*: An advanced optimization process where the optimizer rewrites a correlated subquery into a standard flat join using grouping or window aggregates, decoupling the inner query from the outer loop.
    * *Example*: Converting a loop checking `WHERE price > (avg price of category)` into a flat join on a pre-aggregated category averages table.
48. **Explain the impact of memory spill to tempdb during a massive recursive CTE run.**
    * *Answer*: If the recursive loop accumulates millions of records, the worktable size exceeds the memory grant. The engine writes temporary pages to disk (tempdb). This introduces massive disk I/O latency, causing CPU execution times to spike.
49. **How would you write a recursive CTE to build a calendar table containing every date of a year?**
    * *Answer*:
      ```sql
      WITH RECURSIVE Calendar AS (
          SELECT '2026-01-01'::DATE AS d
          UNION ALL
          SELECT (d + INTERVAL '1 day')::DATE FROM Calendar WHERE d < '2026-12-31'::DATE
      )
      SELECT d FROM Calendar;
      ```
50. **How does the cost-based optimizer estimate the cardinality of a query containing a subquery inside a HAVING clause?**
    * *Answer*: Since aggregate outputs are computed at runtime, the optimizer has no static statistics for the HAVING results. It falls back to default mathematical assumptions, which can lead to poor join order choices later in the plan.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 04: Joins (Combining Tables)](04_joins_combining_tables.md)
* **Next Chapter**: [👉 Topic 06: Data Modification & Transactions](06_dml_transactions.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
