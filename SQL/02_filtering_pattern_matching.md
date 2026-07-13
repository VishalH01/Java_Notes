# 🔍 Topic 02: Filtering & Pattern Matching

Welcome back, data detective! In this chapter, we will learn about advanced **Filtering & Pattern Matching** in SQL. Simply grabbing everything from a table is not enough. You need to search for specific records, like finding users whose names start with "J", finding products priced between $10 and $50, or locating orders that are missing email addresses.

---

## 🏠 The Big Picture & Real-Life Example

### 🛒 The Online Shopping Filters
Imagine you are shopping on a giant online clothing store:
* **The `AND` Filter**: You check the boxes for *"Color: Blue"* AND *"Size: Medium"*. The store only shows shirts matching both rules.
* **The `OR` Filter**: You check *"Size: Medium"* OR *"Size: Large"*. The store shows items matching either size.
* **The `LIKE` Wildcard Search**: You type *"boot"* in the search bar. The engine finds *"leather boots"*, *"ankle boots"*, and *"bootcuts"*.
  * The **`%`** wildcard is like typing: *"boot*"*. It matches any number of characters after the word.
  * The **`_`** wildcard matches exactly *one* missing character (e.g. searching for `"h_t"` matches `"hat"`, `"hot"`, and `"hut"`, but not `"heat"`).
* **The `IS NULL` Filter**: You click a box: *"Show items with free shipping (no shipping price set)"*.

---

## 🔬 Let's Look Closer

### 1. Pattern Matching with LIKE
The `LIKE` operator is used with string columns to execute pattern matching:
* **`%` (Percent)**: Represents zero, one, or multiple characters.
  * `WHERE name LIKE 'A%'` -> Matches names starting with "A" (Alice, Aaron).
  * `WHERE name LIKE '%son'` -> Matches names ending with "son" (Jackson, Wilson).
  * `WHERE name LIKE '%or%'` -> Matches names containing "or" (Jordan, Taylor).
* **`_` (Underscore)**: Represents exactly one character.
  * `WHERE code LIKE 'A_B'` -> Matches "A1B", "AXB", but not "A12B".

### 2. Matching Lists & Ranges
* **`IN`**: Used to match values against a specific list. It replaces multiple `OR` statements.
  * `WHERE city IN ('New York', 'London', 'Tokyo')`
* **`BETWEEN`**: Filters values within an inclusive range (including start and end points).
  * `WHERE age BETWEEN 18 AND 30` (equivalent to `age >= 18 AND age <= 30`).

---

## 💻 Code Sandbox

Let's use a mock customer table to write our filtering queries.

### The Table: `customers`
| id | name | email | age | country | membership_status |
|---|---|---|---|---|---|
| 1 | John Doe | john@gmail.com | 28 | USA | Active |
| 2 | Jane Smith | jane@yahoo.com | 34 | UK | Active |
| 3 | Bob Johnson | bob@outlook.com | 45 | Canada | NULL |
| 4 | Alice Brown | NULL | 22 | USA | Pending |

### 1. Simple Pattern Matching (LIKE)
```sql
-- Finds customers whose names start with 'J'
SELECT name, email 
FROM customers 
WHERE name LIKE 'J%';
```

### 2. Exact Length Pattern Matching
```sql
-- Finds emails ending with exactly 4 letters after the dot (e.g. '.com', '.info')
SELECT name, email 
FROM customers 
WHERE email LIKE '%.____';
```

### 3. Check Range & List Matching
```sql
-- Finds active customers aged 25 to 40 in USA or Canada
SELECT name, age, country 
FROM customers 
WHERE age BETWEEN 25 AND 40 
  AND country IN ('USA', 'Canada') 
  AND membership_status = 'Active';
```

### 4. Locate Missing Data (IS NULL)
```sql
-- Finds customers who did not provide an email address
SELECT name, country 
FROM customers 
WHERE email IS NULL;
```

---

## 🧠 Points to Remember

* The `BETWEEN` operator is inclusive. If you write `BETWEEN 10 AND 20`, numbers 10 and 20 will be included in the results.
* Standard SQL string comparisons are case-sensitive in some database engines (like Oracle and PostgreSQL) and case-insensitive in others (like MySQL). To force case-insensitive checks in PostgreSQL, use **`ILIKE`**.
* Never use `= NULL` to check for missing data. In database logic, `NULL = NULL` evaluates to `UNKNOWN` (neither true nor false), so you must use **`IS NULL`**.

---

## 📖 Key Definitions

* **LIKE**: A SQL comparison operator used to search for a specified pattern in a string column using wildcards.
* **Wildcard**: Special characters (`%` and `_`) used inside a LIKE pattern matching query.
* **BETWEEN**: A SQL comparison operator used to filter rows whose values fall within an inclusive range.
* **IN**: A SQL comparison operator used to match column values against a predefined list of literal values.
* **IS NULL**: A SQL operator used to locate records where a column's data is missing or empty.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is the difference between `AND` and `OR` operators?**
   * *Answer*: `AND` requires both conditions to be true for a row to be included. `OR` requires only one of the conditions to be true.
2. **What does the `NOT` operator do?**
   * *Answer*: It negates a boolean condition, returning rows where the condition is false (e.g. `WHERE NOT status = 'Active'`).
3. **What is the `LIKE` operator used for?**
   * *Answer*: It is used in the `WHERE` clause to search for a specified pattern inside a text column.
4. **What does the `%` wildcard represent?**
   * *Answer*: It represents zero, one, or multiple characters in a `LIKE` pattern.
5. **What does the `_` wildcard represent?**
   * *Answer*: It represents exactly one character in a `LIKE` pattern.
6. **How do you search for a name starting with "Z"?**
   * *Answer*: `WHERE name LIKE 'Z%'`.
7. **How do you search for a name ending with "y"?**
   * *Answer*: `WHERE name LIKE '%y'`.
8. **How do you search for a name containing "dan"?**
   * *Answer*: `WHERE name LIKE '%dan%'`.
9. **What does `WHERE code LIKE '___'` mean?**
   * *Answer*: It matches any value that is exactly three characters long.
10. **How do you filter values within a specific range?**
    * *Answer*: Using the `BETWEEN` operator (e.g. `WHERE price BETWEEN 10 AND 50`).
11. **Is the `BETWEEN` operator inclusive or exclusive?**
    * *Answer*: It is inclusive, meaning the start and end values are included in the filter.
12. **What does the `IN` operator do?**
    * *Answer*: It matches values against a list of options (e.g. `WHERE country IN ('USA', 'UK')`).
13. **Why can't we use `= NULL` to filter empty values?**
    * *Answer*: Because NULL represents an unknown value. Any direct comparison with NULL using `=` yields `UNKNOWN`, not true.
14. **What operator is used to find NULL values?**
    * *Answer*: **`IS NULL`**.
15. **What operator is used to exclude NULL values?**
    * *Answer*: **`IS NOT NULL`**.
16. **How do you combine `IN` with `NOT`?**
    * *Answer*: By writing `NOT IN` (e.g. `WHERE country NOT IN ('UK', 'USA')`).
17. **What is the equivalent of `age BETWEEN 20 AND 30` using comparison operators?**
    * *Answer*: `age >= 20 AND age <= 30`.
18. **Can you use `LIKE` on numeric columns?**
    * *Answer*: No, `LIKE` is designed for text-based string pattern matching. The database engine may fail or implicitly convert values.
19. **How do you search for names whose second letter is 'a'?**
    * *Answer*: `WHERE name LIKE '_a%'`.
20. **What does `NOT LIKE` do?**
    * *Answer*: It filters out rows that match the specified string pattern.

### 🟡 Intermediate Questions (21-40)

21. **How do you search for strings containing actual '%' or '_' characters without triggering wildcards?**
    * *Answer*: By using the `ESCAPE` clause to define an escape character (e.g. `WHERE discount LIKE '10\%' ESCAPE '\'`).
22. **What is the performance difference between `LIKE 'ABC%'` and `LIKE '%ABC%'`?**
    * *Answer*: `LIKE 'ABC%'` can use a B-Tree index scan (Index Seek) because the start string is known. `LIKE '%ABC%'` cannot use B-Tree indexes efficiently and forces a full table scan.
23. **What is `ILIKE` in PostgreSQL?**
    * *Answer*: A case-insensitive version of the `LIKE` operator.
24. **How do logical operator precedence rules affect expressions containing both AND and OR?**
    * *Answer*: `AND` has higher precedence than `OR`. An expression `A OR B AND C` is evaluated as `A OR (B AND C)`. You should use parentheses to override this order.
25. **What is Short-Circuit evaluation in SQL query filters?**
    * *Answer*: Modern SQL optimizers evaluate cheap filters (like integer checks) first before evaluating expensive ones (like text regex or subqueries) to save CPU cycles.
26. **Explain how `NOT IN` behaves if the subquery list contains a NULL value.**
    * *Answer*: If the list contains a NULL value, `NOT IN` will return **zero rows**. This is because `val NOT IN (1, NULL)` translates to `val != 1 AND val != NULL`. Since `val != NULL` is `UNKNOWN`, the whole AND condition fails.
27. **How does B-Tree indexing handle `IS NULL` queries?**
    * *Answer*: Most modern RDBMS engines (like Oracle, PostgreSQL, SQL Server) index NULL values in B-Trees, allowing `IS NULL` filters to execute index seeks instead of table scans.
28. **What is the `COALESCE` function?**
    * *Answer*: A function that evaluates arguments in order and returns the first non-null value (e.g. `COALESCE(phone, 'No Phone')`).
29. **What is the difference between `COALESCE` and `IFNULL` (or `NVL`)?**
    * *Answer*: `COALESCE` is the standard SQL specification function and accepts multiple arguments. `IFNULL` (MySQL) and `NVL` (Oracle) are engine-specific and only accept two arguments.
30. **How do you match a string against a regular expression in PostgreSQL?**
    * *Answer*: Using the `~` operator (case-sensitive) or `~*` operator (case-insensitive).
31. **What is collation and how does it affect pattern matching?**
    * *Answer*: Collation is the set of rules determining how strings are compared and sorted. A case-insensitive collation (e.g. `utf8_general_ci`) makes `LIKE` case-insensitive by default.
32. **Explain the behavior of the `BETWEEN` operator on DATE columns.**
    * *Answer*: It can be risky because dates contain times. If you filter `BETWEEN '2026-07-01' AND '2026-07-02'`, it matches up to `2026-07-02 00:00:00`. Any record created at `2026-07-02 12:00:00` is excluded.
33. **How do you safely query DATE ranges without using `BETWEEN`?**
    * *Answer*: By using comparison operators: `WHERE created_at >= '2026-07-01' AND created_at < '2026-07-03'`.
34. **What is the purpose of the `NULLIF` function?**
    * *Answer*: It compares two expressions. If they are equal, it returns `NULL`; otherwise, it returns the first expression. Useful to prevent divide-by-zero errors.
35. **How can you prevent a divide-by-zero error using `NULLIF`?**
    * *Answer*: By wrapping the divisor: `SELECT price / NULLIF(quantity, 0) FROM sales;`. If quantity is 0, the division returns NULL instead of throwing an error.
36. **Explain the behavior of logical `NOT` with `NULL`.**
    * *Answer*: `NOT UNKNOWN` remains `UNKNOWN`. Therefore, negating a condition on a NULL field does not return that row.
37. **How do you find records where a column is either NULL or has a specific value?**
    * *Answer*: By explicitly checking both conditions: `WHERE email IS NULL OR email = 'test@test.com'`.
38. **What does the SQL standard operator `IS DISTINCT FROM` do?**
    * *Answer*: A null-safe comparison operator. It returns true if values are different, treating NULL as a comparable value (e.g., `A IS DISTINCT FROM B` is true if A is NULL and B is 10).
39. **How do you check if a string does NOT start with a number?**
    * *Answer*: In engines supporting Regex: `WHERE name NOT SIMILAR TO '[0-9]%'` or `WHERE name !~ '^[0-9]'`.
40. **Can B-Tree indexes be used for `LIKE '%abc'`?**
    * *Answer*: No, because the leading characters are unknown, preventing B-Tree traversal from the root node.

### 🔴 Advanced Questions (41-50)

41. **Explain the internal evaluation of `IN (val1, val2, ...)` vs. a series of `OR` statements in query compilation.**
    * *Answer*: Many query optimizers compile `IN` lists into a balanced binary search tree or a hash lookup table in memory, which evaluates in $O(log N)$ or $O(1)$ time, whereas raw `OR` statements force linear evaluations of $O(N)$.
42. **Why is it dangerous to write `NOT IN` with a subquery that might return NULL values?**
    * *Answer*: Because SQL evaluates `NOT IN (A, B, NULL)` as `col != A AND col != B AND col != NULL`. Since any comparison with NULL returns `UNKNOWN`, the entire boolean statement evaluates to `UNKNOWN` or `FALSE`, filtering out all records.
43. **How does PostgreSQL optimize `LIKE '%search%'` using trigram indexes (pg_trgm)?**
    * *Answer*: Trigram indexing splits strings into 3-character slices (e.g. "search" into "sea", "ear", "arc", "rch"). The engine indexes these slices, enabling index lookups on leading wildcard queries.
44. **What is the difference between `IS NULL` and checking for an empty string (`col = ''`) on Oracle Database?**
    * *Answer*: Oracle is unique because it treats empty strings (`''`) as `NULL` values. In Oracle, checking `col = ''` will never return records, and you must use `col IS NULL`. Other engines (MySQL, PostgreSQL) treat them as distinct.
45. **Explain the physical disk scanning difference between Index Seek and Index Scan on wildcards.**
    * *Answer*: An **Index Seek** traverses B-Tree branch pointers directly to matching leaf keys (fast). An **Index Scan** traverses all leaf keys in index sorted order because it cannot isolate matching branch bounds (slower).
46. **How does the cost-based optimizer (CBO) estimate the selectivity of a LIKE query?**
    * *Answer*: It checks database statistics histograms. If you query `LIKE 'Smith%'`, the CBO looks up name distribution statistics. If you query `LIKE '%xyz%'`, it falls back to a default guess (usually 5% selectivity) which can lead to poor execution plans.
47. **What is the difference between `COALESCE` and `CASE WHEN` evaluations?**
    * *Answer*: `COALESCE` is syntactically a shortcut for a `CASE` expression. `COALESCE(a, b)` compiled represents `CASE WHEN a IS NOT NULL THEN a ELSE b END`.
48. **How does the database engine internally compute `NULLIF(expression1, expression2)`?**
    * *Answer*: It compiles to: `CASE WHEN expression1 = expression2 THEN NULL ELSE expression1 END`.
49. **How would you write a query to search for a string containing `_` in MySQL?**
    * *Answer*: By using a backslash to escape the character: `WHERE code LIKE '%\_%'`.
50. **What is the difference in memory utilization between `IN` with a static list and `IN` with a subquery?**
    * *Answer*: A static list has fixed parameters compiled in the plan. A subquery requires executing the nested query, loading its results into a temporary in-memory hash table, and performing hash semi-joins against the outer query.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 01: Introduction to Databases & SQL](01_intro_to_databases_sql.md)
* **Next Chapter**: [👉 Topic 03: Aggregation & Grouping](03_aggregation_grouping.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
