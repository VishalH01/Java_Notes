# ➕ Topic 03: Aggregation & Grouping

Welcome back, data summarizer! In this chapter, we will learn about **Aggregation & Grouping** in SQL. Real-world applications rarely fetch raw tables. Instead, managers want summary reports: *"What is the total revenue for this month?"*, *"What is the average salary of each department?"*, or *"Which products have sold more than 100 units?"* We will master aggregate functions, the power of `GROUP BY`, and how to filter groups using `HAVING`.

---

## 🏠 The Big Picture & Real-Life Example

### 🧸 Sorting the Toy Chest
Imagine you are sorting a massive chest of toys to organize them:
* **Aggregate Functions (The Counting Scale)**: Instead of listing every toy, you want quick totals:
  * **`COUNT`**: How many toys do we have?
  * **`SUM`**: What is the total weight of all toys combined?
  * **`AVG`**: What is the average price of a toy?
  * **`MIN` / `MAX`**: What is the cheapest and most expensive toy?
* **`GROUP BY` (Dividing into Boxes)**: You place the toys into separate boxes based on their color: Red Box, Blue Box, and Green Box.
* **`WHERE` vs. `HAVING` (Filtering toys vs. Filtering boxes)**:
  * **`WHERE` (Before grouping)**: You pick up toys and throw away any broken ones *before* putting them in boxes.
  * **`HAVING` (After grouping)**: You review the completed boxes. You say: *"Only keep boxes that have more than 5 toys in them"* (You throw away whole boxes, not individual toys!).

---

## 🔬 Let's Look Closer

### 1. Aggregate Functions
These functions collapse multiple rows into a single summary value:
* **`COUNT(column)`**: Counts non-null entries. `COUNT(*)` counts all rows.
* **`SUM(column)`**: Calculates total sum of numeric values.
* **`AVG(column)`**: Calculates average value.
* **`MIN(column)` / `MAX(column)`**: Returns lowest/highest values.

### 2. GROUP BY Clause
When you use aggregate functions, you cannot list normal columns unless they are declared in the **`GROUP BY`** clause. This clause tells the database engine to group rows sharing the same values into summary rows.

### 3. WHERE vs. HAVING
This is a classic interview trap:
* **`WHERE`**: Filters rows *before* grouping occurs. It cannot contain aggregate functions (e.g. `WHERE SUM(salary) > 5000` is illegal!).
* **`HAVING`**: Filters groups *after* `GROUP BY` is executed. It is used to filter aggregate values.

---

## 💻 Code Sandbox

Let's write summary queries on a mock sales transaction table.

### The Table: `sales`
| id | product | category | amount | sale_date |
|---|---|---|---|---|
| 1 | Laptop | Electronics | 1200.00 | 2026-07-01 |
| 2 | Phone | Electronics | 800.00 | 2026-07-02 |
| 3 | Shirt | Clothing | 25.00 | 2026-07-02 |
| 4 | Pants | Clothing | 45.00 | 2026-07-03 |
| 5 | Laptop | Electronics | 1200.00 | 2026-07-03 |

### 1. General Aggregates
```sql
-- Finds total sales revenue, average transaction, and count of sales
SELECT SUM(amount) AS total_revenue, 
       AVG(amount) AS avg_sale, 
       COUNT(*) AS transaction_count 
FROM sales;
```

### 2. Grouping by Category
```sql
-- Groups sales by category, calculating sums and averages per group
SELECT category, 
       COUNT(*) AS items_sold, 
       SUM(amount) AS category_revenue 
FROM sales 
GROUP BY category;
```

### 3. Filtering Groups with HAVING
```sql
-- Finds categories that made more than $100.00 in revenue
SELECT category, SUM(amount) AS category_revenue 
FROM sales 
GROUP BY category 
HAVING SUM(amount) > 100.00;
```

### 4. Combining WHERE and HAVING
```sql
-- Calculates sales averages excluding cheap transactions (< $30), 
-- only returning groups with average sales above $50.
SELECT category, AVG(amount) AS avg_amount 
FROM sales 
WHERE amount >= 30.00 
GROUP BY category 
HAVING AVG(amount) > 50.00;
```

---

## 🧠 Points to Remember

* Aggregate functions ignore `NULL` values (except `COUNT(*)`). If a column has values `[10, 20, NULL]`, `AVG` will calculate `(10+20)/2 = 15`, ignoring the NULL row entirely.
* If you write `SELECT category, SUM(amount)` without a `GROUP BY category` clause, the database will throw a syntax error: *"Expression is not in GROUP BY clause"*.
* Under the hood, `GROUP BY` sorts or hashes the table records by the grouping keys, which can be computationally expensive on large datasets.

---

## 📖 Key Definitions

* **Aggregate Function**: A SQL function (like COUNT, SUM, AVG) that performs a calculation on a set of values and returns a single summary value.
* **GROUP BY**: A SQL clause used to divide query records into groups based on matching values in one or more columns.
* **HAVING**: A SQL clause used to filter groups returned by the GROUP BY clause, operating on aggregate calculations.
* **WHERE vs HAVING**: WHERE filters individual rows before grouping occurs; HAVING filters summarized groups after grouping occurs.
* **COUNT(*)**: An aggregate function that counts all rows in a group, including NULL values.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is an aggregate function?**
   * *Answer*: A function that processes a collection of values from multiple rows and returns a single summary value (e.g. SUM, COUNT).
2. **Name five common aggregate functions in SQL.**
   * *Answer*: `COUNT()`, `SUM()`, `AVG()`, `MIN()`, and `MAX()`.
3. **What is the difference between `COUNT(column)` and `COUNT(*)`?**
   * *Answer*: `COUNT(column)` counts only non-null values in that column. `COUNT(*)` counts all rows in the table, including NULL rows.
4. **What is the purpose of the `GROUP BY` clause?**
   * *Answer*: To group rows sharing the same values in specified columns into single summary rows.
5. **Can you use aggregate functions without `GROUP BY`?**
   * *Answer*: Yes, but it will collapse the entire table into a single summary row (e.g. finding the total sum of all salaries).
6. **What is the difference between `WHERE` and `HAVING`?**
   * *Answer*: `WHERE` filters rows before grouping; `HAVING` filters groups after `GROUP BY` runs.
7. **Can you use aggregate functions inside the `WHERE` clause?**
   * *Answer*: No, because the `WHERE` clause filters individual rows before aggregates are calculated.
8. **Why does `SELECT name, SUM(salary) FROM employees;` throw an error?**
   * *Answer*: Because `name` is a normal column and `SUM(salary)` is an aggregate. Without a `GROUP BY name` clause, the engine doesn't know how to pair individual names with the single total sum.
9. **How do aggregate functions handle `NULL` values?**
   * *Answer*: They ignore `NULL` values completely during calculations (except for `COUNT(*)`).
10. **What does `MIN(date)` return?**
    * *Answer*: It returns the oldest date in the column.
11. **What does `MAX(name)` return on a text column?**
    * *Answer*: It returns the name that comes last alphabetically (e.g. "Zachary").
12. **Can you group by multiple columns?**
    * *Answer*: Yes, by separating them with commas (e.g. `GROUP BY department, job_title`).
13. **What happens to NULL values in a `GROUP BY` column?**
    * *Answer*: All rows with NULL values in that column are grouped together into a single summary row.
14. **How do you find the number of unique departments in an employee table?**
    * *Answer*: `SELECT COUNT(DISTINCT department) FROM employees;`.
15. **Can you use aliases in the `HAVING` clause?**
   * *Answer*: Standard SQL does not allow using SELECT aliases in `HAVING` because `HAVING` runs before `SELECT` in execution order. However, some engines (like MySQL) allow it.
20. **Can you use `ORDER BY` with aggregate functions?**
    * *Answer*: Yes, you can sort groups by their aggregated values (e.g., `ORDER BY SUM(amount) DESC`).

### 🟡 Intermediate Questions (21-40)

21. **Explain the physical database execution steps for a GROUP BY query.**
    * *Answer*: The database first reads table pages (`FROM`), filters matching rows (`WHERE`), sorts or builds an in-memory Hash Table of the grouping keys (`GROUP BY`), computes aggregate metrics, filters groups (`HAVING`), and outputs columns (`SELECT`).
22. **What is Hash Aggregation vs. Sort Aggregation in GROUP BY execution?**
    * *Answer*: **Hash Aggregation** builds a temporary hash map of groups in memory (fast, fits unsorted data). **Sort Aggregation** sorts the dataset by the grouping keys first, then sums values sequentially (efficient for large, pre-sorted data).
23. **What happens if you run `SUM` or `AVG` on a column containing only NULL values?**
    * *Answer*: The function returns `NULL`.
24. **How does the presence of an index on a GROUP BY column optimize aggregation speed?**
    * *Answer*: It allows the database to perform Sort Aggregation directly without scanning the whole table, skipping the expensive in-memory hashing or sorting steps.
25. **Is it valid to write `HAVING` without a `GROUP BY` clause?**
    * *Answer*: Yes. If there is no `GROUP BY`, the entire table acts as a single group. `HAVING` will evaluate the aggregate conditions against the entire table.
26. **Explain the difference: `SELECT COUNT(id)` vs `SELECT COUNT(1)`.**
    * *Answer*: Performance-wise, modern database optimizers treat them identically. `COUNT(1)` evaluates the constant "1" for every row, while `COUNT(id)` checks if `id` is not null. Both return the same counts if `id` is a primary key.
27. **What is the difference between `COUNT(DISTINCT column)` and `DISTINCT COUNT(column)`?**
    * *Answer*: `COUNT(DISTINCT column)` is correct syntax. `DISTINCT COUNT(column)` is syntax error.
28. **How does the `AVG` function calculate averages when NULL values are present?**
    * *Answer*: It divides the sum of non-null values by the count of non-null values. It does *not* count NULL rows in the denominator (e.g., `[10, 20, NULL]` averages to `30 / 2 = 15`, not `30 / 3 = 10`).
29. **What does the SQL standard `GROUPING SETS` feature do?**
    * *Answer*: An advanced aggregation feature that allows performing multiple different `GROUP BY` configurations in a single query (e.g. grouping by department AND grouping by job_title in one output).
30. **Explain how `ROLLUP` works in a `GROUP BY` clause.**
    * *Answer*: It generates hierarchical subtotal rows along with the standard grouped rows, ending with a grand total row.
31. **Explain how `CUBE` works in a `GROUP BY` clause.**
    * *Answer*: It generates subtotals for all possible combinations of the specified grouping columns.
32. **Can you filter a query using `WHERE` after `GROUP BY` has executed?**
    * *Answer*: No, syntactically the `WHERE` clause must always appear *before* `GROUP BY` in the query statement.
33. **Explain the behavior of string aggregation (e.g. `GROUP_CONCAT` in MySQL or `STRING_AGG` in PostgreSQL).**
    * *Answer*: An aggregate function that concatenates text strings from multiple rows in a group into a single comma-separated text string.
34. **How do you write a query to find departments whose average salary is higher than the overall company average?**
    * *Answer*: By using a subquery in the `HAVING` clause: `GROUP BY department HAVING AVG(salary) > (SELECT AVG(salary) FROM employees)`.
35. **What is the difference in output between `SELECT category, amount FROM sales GROUP BY category` in strict vs. non-strict SQL mode?**
    * *Answer*: In strict SQL (SQL standard), this query throws an error because `amount` is not aggregated nor grouped. In legacy MySQL non-strict mode, it returns a random `amount` value from the group, which is highly misleading.
36. **Explain the impact of collation on a `GROUP BY` text column query.**
    * *Answer*: If collation is case-insensitive, rows containing "apple" and "Apple" will be grouped together into a single group. If case-sensitive, they form two distinct groups.
37. **Why does `AVG(salary)` on an integer column sometimes perform integer division?**
    * *Answer*: In some database engines (like SQL Server), performing `AVG` on integer columns returns an integer. To get precise decimals, you must cast the column to a decimal type first: `AVG(CAST(salary AS DECIMAL))`.
38. **How does the cost-based optimizer choose between a Hash Match Aggregate and Stream Aggregate?**
    * *Answer*: It estimates sorting costs. If the index keeps data sorted, it chooses **Stream Aggregate** (low memory). If sorting is too expensive, it allocates memory for a **Hash Match Aggregate**.
39. **What is a "blocking operator" in SQL query execution, and is GROUP BY one?**
    * *Answer*: Yes. A blocking operator (like sort or group by) must consume and process *all* incoming rows before it can output its first result row to the next execution step.
40. **How do you find the second highest salary using aggregate functions?**
    * *Answer*: `SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees);`.

### 🔴 Advanced Questions (41-50)

41. **Explain the difference in execution resource costs between Group By Hashing and Group By Sorting.**
    * *Answer*: **Hashing** requires allocating memory buffer pools (hash tables). If the hash table size exceeds available RAM, the engine spills data pages to disk (tempdb), causing slow I/O. **Sorting** avoids high memory buffers if indexes are present, but sorting raw heap tables requires CPU-heavy merge-sort runs.
42. **How does the database engine maintain ACID isolation constraints when executing aggregate calculations on a table undergoing concurrent inserts?**
    * *Answer*: The engine utilizes lock isolation levels. Under standard Read Committed, it locks data pages temporarily as it reads them. Under Serializable, it uses range locks or table locks to block new row inserts until the aggregate scan completes, preventing "phantom reads".
43. **Why do aggregate functions like `SUM` or `AVG` trigger overflow errors, and how does the engine handle them?**
    * *Answer*: Overflow occurs when the accumulated sum exceeds the maximum storage capacity of the column's data type (e.g. summing values into a smallint). The engine throws a runtime crash. Developers prevent this by casting columns to BIGINT or DECIMAL before summing.
44. **Explain how PostgreSQL's Filter clause works with aggregates (e.g. `SUM(amount) FILTER (WHERE category = 'A')`).**
    * *Answer*: A SQL standard feature that filters which rows are fed into the aggregate function calculation, allowing you to compute multiple conditional aggregates in a single scan without subqueries.
45. **What is a Parallel Group By execution plan?**
    * *Answer*: The optimizer splits the table scan across multiple CPU threads. Each thread aggregates its section of data (partial aggregation), and then a coordinator thread merges the results (final aggregation) to produce the output.
46. **How do database engines handle memory allocations for GROUP BY queries using "Memory Grants"?**
    * *Answer*: The optimizer estimates the number of groups. It requests a specific RAM allocation (Memory Grant) before the query runs. If the guess is too low, the query spills to disk. If too high, it wastes database RAM, blocking other queries.
47. **Explain the internal difference between `COUNT(*)` and `COUNT(primary_key)` in query optimization.**
    * *Answer*: `COUNT(*)` is optimized to find the fastest path to count rows, often scanning the smallest non-clustered index tree. `COUNT(primary_key)` explicitly forces checking if the primary key is null, which is slightly more restrictive.
48. **What is the mathematical definition of standard deviation aggregates (`STDDEV`, `VAR`) and how do engines compute them in a single table scan?**
    * *Answer*: They compute variance using Welford's algorithm or the parallel single-pass formula, storing running counts, running means, and running sums of squares in memory without reading table pages twice.
49. **How would you aggregate historical stock price data to return only the price of the last date for each stock symbol using Group By?**
    * *Answer*: You find the max date per symbol in a subquery, then join the main table back: `SELECT s.symbol, s.price FROM stock s JOIN (SELECT symbol, MAX(date) AS max_d FROM stock GROUP BY symbol) g ON s.symbol = g.symbol AND s.date = g.max_d`.
50. **How does the database engine handle `HAVING` clauses that contain normal columns not present in the `GROUP BY`?**
    * *Answer*: Standard SQL prohibits this. However, if the grouping column is a primary key, modern engines resolve the functional dependency and permit accessing other table columns in `HAVING`.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 02: Filtering & Pattern Matching](02_filtering_pattern_matching.md)
* **Next Chapter**: [👉 Topic 04: Joins (Combining Tables)](04_joins_combining_tables.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
