# 🏆 Topic 10: Window Functions

Welcome to the final chapter of your SQL journey, data analyst! In this topic, we will learn about **Window Functions**. Standard SQL aggregates (`GROUP BY`) are useful, but they collapse your rows, losing individual record details. Window functions solve this limitation: they perform calculations across related rows *without* collapsing them. We will learn how to rank rows, find running totals, and access adjacent row values using `LEAD` and `LAG`.

---

## 🏠 The Big Picture & Real-Life Example

### 🏃 The Running Race Leaderboard
Imagine you are managing a marathon race with runners divided by gender (Male and Female groups):
* **Standard Group By**: You calculate: *"What is the fastest time for each gender?"* The database returns only two rows: Male = 2:05:00, Female = 2:15:00. All details about who ran what time are lost!
* **Window Functions (The Floating Magnifying Glass)**: You keep the complete list of all 1,000 runners. As you read down the list, you hold a sliding window over them:
  * **`PARTITION BY` (Dividing the tracks)**: You separate the runners into Male and Female tracks.
  * **`ORDER BY` (Sorting the track)**: You sort the runners in each track by their speed.
  * **`ROW_NUMBER()` (The Position)**: You assign a simple runner number: 1, 2, 3, 4.
  * **`RANK()` (The Medal)**: You assign ranks: 1st place, 2nd place. If there is a tie for 2nd place, the next runner gets 4th place (skipping 3rd).
  * **`DENSE_RANK()` (No skipped medals)**: You assign ranks: 1st, 2nd, 2nd, and the next runner gets 3rd place (no numbers skipped!).
  * **`LAG()` (Looking Behind)**: A runner looks backward to see: *"How far behind the runner in front of me am I?"*
  * **`LEAD()` (Looking Ahead)**: A runner looks forward: *"How far ahead of the runner behind me am I?"*

---

## 🔬 Let's Look Closer

### 1. The OVER Clause
The **`OVER`** clause is what turns a normal function into a window function. It defines the "window" of rows that the function operates on. It has two main sub-clauses:
* **`PARTITION BY`**: Divides the rows into groups (similar to `GROUP BY`, but keeps rows).
* **`ORDER BY`**: Sorts the rows inside each partition.

### 2. Ranking Functions
* **`ROW_NUMBER()`**: Assigns a unique sequential integer starting at 1.
* **`RANK()`**: Assigns ranks, creating gaps if there are ties (e.g. 1, 2, 2, 4).
* **`DENSE_RANK()`**: Assigns ranks, leaving no gaps on ties (e.g. 1, 2, 2, 3).

### 3. Value Functions
* **`LAG(column, offset)`**: Accesses data from a previous row.
* **`LEAD(column, offset)`**: Accesses data from a subsequent row.

---

## 💻 Code Sandbox

Let's write window calculations on an employee salary database.

### The Table: `employees`
| id | name | department | salary |
|---|---|---|---|
| 1 | Alice | IT | 90000.00 |
| 2 | Bob | IT | 80000.00 |
| 3 | Charlie | IT | 90000.00 |
| 4 | David | HR | 50000.00 |
| 5 | Emma | HR | 60000.00 |

### 1. Assigning Row Numbers
```sql
-- Numbers employees by salary within each department
SELECT department, name, salary,
       ROW_NUMBER() OVER (PARTITION BY department ORDER BY salary DESC) AS row_num
FROM employees;
```

### 2. Comparing RANK() vs. DENSE_RANK()
```sql
-- Alice and Charlie earn the same. Watch how ranks differ!
SELECT name, salary,
       RANK() OVER (ORDER BY salary DESC) AS rnk,
       DENSE_RANK() OVER (ORDER BY salary DESC) AS dense_rnk
FROM employees;
```

*Expected Output for Rank vs Dense Rank:*
| name | salary | RANK() | DENSE_RANK() |
|---|---|---|---|
| Alice | 90000.00 | 1 | 1 |
| Charlie | 90000.00 | 1 | 1 |
| Bob | 80000.00 | 3 (gaps!) | 2 (no gaps!) |

### 3. Accessing Adjacent Rows (LAG)
```sql
-- Shows the salary of the employee earning slightly more in the same department
SELECT department, name, salary,
       LAG(salary, 1) OVER (PARTITION BY department ORDER BY salary DESC) AS next_higher_salary
FROM employees;
```

### 4. Running Total (Cumulative Sum)
```sql
-- Calculates a running total of salaries across the company
SELECT name, salary,
       SUM(salary) OVER (ORDER BY salary ASC) AS running_total
FROM employees;
```

---

## 🧠 Points to Remember

* Window functions can only be placed inside the `SELECT` and `ORDER BY` clauses. You cannot use them inside `WHERE` (e.g. `WHERE ROW_NUMBER() = 1` is illegal!). To filter by window outputs, wrap the query in a CTE or subquery first.
* Unlike `GROUP BY`, window functions do not collapse rows. The output has the exact same number of rows as the input table.
* The `OVER (ORDER BY ...)` clause implicitly creates a default frame partition: `RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW`. This is what calculates cumulative sums.

---

## 📖 Key Definitions

* **Window Function**: An advanced SQL function that performs calculations across a set of table rows that are related to the current row, without collapsing them.
* **OVER Clause**: The SQL clause that defines the window partition (partition boundaries) and sorting order for window calculations.
* **PARTITION BY**: The sub-clause inside OVER that divides query results into separate groups/partitions.
* **DENSE_RANK()**: A window function that assigns a rank to each row in a partition, with no gaps in the rank sequence on duplicates.
* **LEAD() / LAG()**: Window functions used to access data from subsequent (LEAD) or preceding (LAG) rows in the partition without joining.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is a window function in SQL?**
   * *Answer*: A function that performs calculations across a set of table rows related to the current row, without collapsing the output rows.
2. **What does the `OVER` clause do?**
   * *Answer*: It defines the window partition (boundaries) and sorting order for window function calculations.
3. **What is the difference between `GROUP BY` and window functions?**
   * *Answer*: `GROUP BY` collapses multiple rows into a single summary row. Window functions perform calculations but retain all individual rows in the output.
4. **What does the `PARTITION BY` clause do?**
   * *Answer*: It divides query results into separate partitions (groups) for window calculations (e.g., partitioning by department).
5. **What does `ROW_NUMBER()` do?**
   * *Answer*: It assigns a unique, sequential integer starting at 1 to each row in a partition.
6. **What is the difference between `RANK()` and `DENSE_RANK()`?**
   * *Answer*: If there are ties, `RANK()` skips rank numbers, creating gaps in the sequence. `DENSE_RANK()` assigns the same rank to ties without skipping numbers.
7. **What does `LAG()` do?**
   * *Answer*: It accesses data from a preceding row in the same result set at a specified offset.
8. **What does `LEAD()` do?**
   * *Answer*: It accesses data from a subsequent row in the same result set at a specified offset.
9. **Where inside a SQL statement can you write window functions?**
   * *Answer*: Only inside the `SELECT` and `ORDER BY` clauses.
10. **Why can't we use window functions inside the `WHERE` clause?**
    * *Answer*: Because the `WHERE` clause filters rows *before* the select columns and window functions are computed.
11. **How do you filter query results based on a window function's output?**
    * *Answer*: By wrapping the query containing the window function inside a CTE or subquery, and applying the `WHERE` filter to the outer query.
12. **What is the default sorting order of `ORDER BY` inside `OVER`?**
    * *Answer*: Ascending (`ASC`).
13. **What does `FIRST_VALUE(column)` do?**
    * *Answer*: It returns the first value in the sorted window partition.
14. **What does `LAST_VALUE(column)` do?**
    * *Answer*: It returns the last value in the sorted window partition.
15. **What value is returned by `LAG()` if there is no previous row?**
    * *Answer*: It returns `NULL` by default.
16. **Can you customize the fallback value of `LAG()` on nulls?**
    * *Answer*: Yes, by passing a third parameter: `LAG(column, offset, default_value)`.
17. **How do you calculate a running total in SQL?**
    * *Answer*: By using `SUM(column) OVER (ORDER BY sort_column)`.
18. **Can you use multiple window functions in a single query?**
    * *Answer*: Yes, you can include as many window functions as needed in the SELECT list.
19. **What does `NTILE(N)` do?**
    * *Answer*: It divides the rows in a partition into `N` equal groups (buckets) and assigns a bucket number to each row.
20. **What is the difference between `ROW_NUMBER()` and `IDENTITY`?**
    * *Answer*: `IDENTITY` is a table schema property that generates permanent serial IDs on insert. `ROW_NUMBER()` is a dynamic query function computed on the fly.

### 🟡 Intermediate Questions (21-40)

21. **Explain the physical execution process of window functions in RDBMS.**
    * *Answer*: The database scans the table (`FROM`, `WHERE`), sorts the entire dataset in memory by the partition and order keys, and feeds the sorted stream into a Window Spool operator to calculate values sequentially.
22. **What is the difference in performance between `PARTITION BY` and `ORDER BY` in window functions?**
    * *Answer*: If an index matches both `PARTITION BY` and `ORDER BY` columns in sequence, the engine can execute calculations instantly. If not, it must run expensive in-memory sort operations to group keys.
23. **What is a Window Frame (ROWS/RANGE)?**
    * *Answer*: A sub-boundary inside a partition that defines exactly which rows around the current row are included in the calculation (e.g. `ROWS BETWEEN 1 PRECEDING AND 1 FOLLOWING`).
24. **Explain the difference: `ROWS` vs `RANGE` in window frame specifications.**
    * *Answer*: `ROWS` measures physical row counts (e.g., exactly 3 rows). `RANGE` measures logical offsets of values, grouping duplicate values together in calculations.
25. **What does the default frame `RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW` do?**
    * *Answer*: It includes all rows from the start of the partition up to the current row, which is used to compute running totals.
26. **Why does `SUM(val) OVER (ORDER BY date)` calculate a running total, whereas `SUM(val) OVER ()` calculates a grand total?**
    * *Answer*: Including `ORDER BY` triggers the default running frame (`RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW`). Excluding `ORDER BY` sets the default frame to the entire partition, returning the global sum.
27. **What is the performance cost of using `RANGE` instead of `ROWS` in running totals?**
    * *Answer*: `RANGE` forces the database engine to create temporary spool files on disk to evaluate duplicate values. `ROWS` runs in memory using a fast streaming accumulator, which is much faster.
28. **How do you find the top 3 highest earning employees in each department using window functions?**
    * *Answer*: Wrap a `DENSE_RANK()` query in a CTE, then filter:
      ```sql
      WITH Ranked AS (
          SELECT name, dept, salary, DENSE_RANK() OVER (PARTITION BY dept ORDER BY salary DESC) as rnk FROM employees
      ) SELECT * FROM Ranked WHERE rnk <= 3;
      ```
29. **What is the `CUME_DIST()` function?**
    * *Answer*: A window function that calculates the cumulative distribution (percentile rank) of a value in a partition, returning a number between 0 and 1.
30. **What is the `PERCENT_RANK()` function?**
    * *Answer*: It calculates the relative rank of a row inside a partition as a percentage: `(rank - 1) / (total_rows - 1)`.
31. **Explain the purpose of `WINDOW` clause aliases in SQL standard.**
    * *Answer*: It allows naming a window definition at the end of the query to avoid repeating the same `OVER` definition: `WINDOW w AS (PARTITION BY dept ORDER BY salary DESC)`.
32. **How does `NTH_VALUE(column, N)` work?**
    * *Answer*: It returns the column value of the N-th row in the sorted window frame.
33. **Can you combine window functions with `GROUP BY`?**
    * *Answer*: Yes, but the window function executes *after* the `GROUP BY` aggregation, meaning it can only partition or order over the aggregated group outputs.
34. **How do you compute month-over-month sales growth using window functions?**
    * *Answer*: By using `LAG` to fetch the previous month's revenue: `SELECT revenue - LAG(revenue) OVER (ORDER BY month) FROM monthly_sales;`.
35. **What is a "Window Spool" in query execution plans?**
    * *Answer*: An execution operator that caches rows of the active partition in memory, allowing value functions (like lag, lead) to peek at adjacent rows.
36. **Explain the behavior of `LAST_VALUE` with the default window frame.**
    * *Answer*: A common mistake. Because the default frame ends at `CURRENT ROW`, `LAST_VALUE` returns the value of the current row instead of the actual last row. You must change the frame to `ROWS BETWEEN CURRENT ROW AND UNBOUNDED FOLLOWING`.
37. **How does the cost-based optimizer optimize multiple window functions with different `PARTITION BY` keys?**
    * *Answer*: It must execute separate sort operators. If you have 3 window functions with different partitions, the engine will sort the dataset 3 separate times, causing high CPU load.
38. **Explain the purpose of `ROW_NUMBER()` in removing duplicate records.**
    * *Answer*: You assign `ROW_NUMBER() OVER (PARTITION BY duplicate_cols ORDER BY id)` inside a CTE, and then delete rows where `row_number > 1`.
39. **Does `PARTITION BY` use database indexes?**
    * *Answer*: Yes, if a B-Tree index matches the partition column, the database can read rows in pre-sorted group order, skipping the sorting phase.
40. **How do you calculate a moving average of the last 3 sales (current + 2 previous)?**
    * *Answer*: By defining the frame: `AVG(amount) OVER (ORDER BY date ROWS BETWEEN 2 PRECEDING AND CURRENT ROW)`.

### 🔴 Advanced Questions (41-50)

41. **Explain the sorting and paging mechanics of the Segment and Sequence Project operators during window function execution.**
    * *Answer*: In execution plans, the **Segment** operator monitors the sorted stream to detect when partition values change (boundaries). The **Sequence Project** operator then applies calculations (counting or ranking) to the rows, resetting its counter whenever the Segment operator signals a new partition.
42. **Why does executing `SUM(val) OVER (ORDER BY date)` on a column with non-unique dates trigger performance degradation under RANGE vs ROWS?**
    * *Answer*: Under `RANGE`, duplicate dates are treated as a single group. The engine must scan ahead to aggregate all matching dates before returning the sum for the current row. Under `ROWS`, the engine performs simple running accumulation row-by-row in memory, bypassing lookaheads.
43. **Explain how to optimize query memory grants when sorting millions of rows for window function execution.**
    * *Answer*: Ensure the database has a covering index matching `(partition_col, order_col)`. This allows the engine to perform **Stream Aggregation** using the pre-sorted index order, reducing the required memory grant to zero and avoiding spills to disk.
44. **What is the mathematical difference in rank sequences between `RANK()`, `DENSE_RANK()`, and `PERCENT_RANK()` on duplicate values?**
    * *Answer*:
      * `RANK()` computes: `1 + count of rows preceding current row with lesser values`.
      * `DENSE_RANK()` computes: `1 + count of distinct preceding values`.
      * `PERCENT_RANK()` computes: `(RANK() - 1) / (Total Rows - 1)`.
45. **How do distributed query engines (like Apache Spark SQL or Google BigQuery) compute Window Functions across network nodes?**
    * *Answer*: They use a **Shuffle** operation. The engine hashes the `PARTITION BY` keys and redistributes (shuffles) all rows across the network so that all records belonging to the same partition land on the same worker node to be sorted and calculated locally.
46. **What is a "sliding window frame" in stream processing databases (like Apache Flink or ksqlDB)?**
    * *Answer*: A time-based or row-based window that slides continuously over real-time events (e.g., checking averages of transactions in the last 10 seconds), outputting updates as events enter and exit the active frame.
47. **How would you write a query using window functions to identify session duration gaps (gaps-and-islands problem)?**
    * *Answer*: You use `LAG` to compare the current transaction timestamp with the previous one. If the difference exceeds a threshold (e.g. 30 minutes), you flag it as a new session start (`1`, else `0`). You then compute a running sum of these flags to assign unique session IDs to islands.
48. **Explain the behavior of the `NTILE(N)` function when the number of rows is not perfectly divisible by N.**
    * *Answer*: The engine distributes the remainder rows across the first few buckets, making them larger by 1 row. For example, dividing 8 rows into 3 buckets results in sizes of 3, 3, and 2.
49. **How would you optimize a query that calculates running totals on a table of 100 million rows where indexes cannot be used?**
    * *Answer*: I would pre-aggregate the data by larger buckets (e.g., daily sums) into a summary table, calculate running totals on the summary table, and join the detail rows back, minimizing the number of rows sorted by the window function.
50. **Explain how "Window Partition Clauses" can be rewritten into correlated subqueries, and why this is usually a poor optimization choice.**
    * *Answer*: `SUM(val) OVER (PARTITION BY group_col)` can be rewritten as `(SELECT SUM(val) FROM table WHERE group_col = outer.group_col)`. This is a poor choice because it forces a nested loop table scan ($O(N^2)$), whereas window functions use a single sort-merge pass ($O(N log N)$).

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 09: Views, Stored Procedures & Triggers](09_views_procedures_triggers.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
