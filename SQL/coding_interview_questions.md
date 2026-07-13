# 📊 SQL Practical Coding Interview Guide

Welcome, database candidate! This guide compiles the **Top 25 most frequently asked SQL query coding questions** in technical interviews. Every question includes mock schemas, query solutions, and detailed execution breakdowns.

---

## 🗺️ Index of Questions

1. [Find the 2nd Highest Salary](#1-find-the-2nd-highest-salary)
2. [Find the N-th Highest Salary](#2-find-the-n-th-highest-salary)
3. [Find Duplicate Emails](#3-find-duplicate-emails)
4. [Delete Duplicate Rows from a Table](#4-delete-duplicate-rows-from-a-table)
5. [Find Employees who Earn More than Their Managers](#5-find-employees-who-earn-more-than-their-managers)
6. [Find Customers who Never Ordered](#6-find-customers-who-never-ordered)
7. [Find Departments with More than 5 Employees](#7-find-departments-with-more-than-5-employees)
8. [Calculate 3-Day Moving Average of Sales](#8-calculate-3-day-moving-average-of-sales)
9. [Find Consecutive Numbers appearing Three Times](#9-find-consecutive-numbers-appearing-three-times)
10. [Pivot Table (Rows to Columns)](#10-pivot-table-rows-to-columns)
11. [Find Cumulative Running Total of Transactions](#11-find-cumulative-running-total-of-transactions)
12. [Find Top 3 Highest Paid Employees per Department](#12-find-top-3-highest-paid-employees-per-department)
13. [Find Department with the Highest Total Salary](#13-find-department-with-the-highest-total-salary)
14. [Find Second Most Recent Order for each User](#14-find-second-most-recent-order-for-each-user)
15. [Calculate Difference between Current and Previous Row](#15-calculate-difference-between-current-and-previous-row)
16. [Find Users with Consecutive Logins](#16-find-users-with-consecutive-logins)
17. [Calculate Monthly Active Users (MAU)](#17-calculate-monthly-active-users-mau)
18. [Swap Salary Gender Values in one Update](#18-swap-salary-gender-values-in-one-update)
19. [Calculate Total Distance Traveled by Car](#19-calculate-total-distance-traveled-by-car)
20. [Join with Optional Fallbacks (COALESCE)](#20-join-with-optional-fallbacks-coalesce)
21. [Count Orders per Customer (including 0 orders)](#21-count-orders-per-customer-including-0-orders)
22. [Select Top 10% Highest Selling Products](#22-select-top-10-highest-selling-products)
23. [Find the Median of a Column](#23-find-the-median-of-a-column)
24. [Find Project with Maximum Employees](#24-find-project-with-maximum-employees)
25. [Fetch Employees Hired in Last 30 Days](#25-fetch-employees-hired-in-last-30-days)

---

## 💻 SQL Query Challenges & Solutions

### 1. Find the 2nd Highest Salary
**Problem**: Write a query to find the second highest salary from the `employees` table.
* **Schema**: `employees (id, name, salary)`
```sql
-- Solution 1: Subquery (Safe for all databases)
SELECT MAX(salary) AS second_highest 
FROM employees 
WHERE salary < (SELECT MAX(salary) FROM employees);

-- Solution 2: LIMIT / OFFSET (MySQL / PostgreSQL specific)
SELECT DISTINCT salary 
FROM employees 
ORDER BY salary DESC 
LIMIT 1 OFFSET 1;
```
* **Explanation**: Solution 1 finds the global maximum salary, then queries to find the maximum salary among all records *less than* that global maximum. Solution 2 sorts unique salaries descending and skips the first record (highest) to return the second.

---

### 2. Find the N-th Highest Salary
**Problem**: Find the N-th highest salary (e.g. 3rd highest) using Window functions.
* **Schema**: `employees (id, name, salary)`
```sql
WITH RankedSalaries AS (
    SELECT salary, 
           DENSE_RANK() OVER (ORDER BY salary DESC) as rnk 
    FROM employees
)
SELECT DISTINCT salary 
FROM RankedSalaries 
WHERE rnk = 3; -- Replace 3 with N
```
* **Explanation**: We use `DENSE_RANK()` because it handles duplicates correctly (if three people earn the highest salary, they are all rank 1, and the next highest is rank 2). We wrap it in a CTE and filter where `rnk = N`.

---

### 3. Find Duplicate Emails
**Problem**: Identify all duplicate emails present in the `users` table.
* **Schema**: `users (id, name, email)`
```sql
SELECT email 
FROM users 
GROUP BY email 
HAVING COUNT(email) > 1;
```
* **Explanation**: We group rows by their email value. The `HAVING` clause filters the grouped results, returning only those emails whose group row count is greater than 1.

---

### 4. Delete Duplicate Rows from a Table
**Problem**: Delete all duplicate email rows, keeping only the row with the lowest ID.
* **Schema**: `users (id, name, email)`
```sql
-- Solution using Self Join (MySQL/PostgreSQL)
DELETE u1 
FROM users u1
INNER JOIN users u2 ON u1.email = u2.email 
WHERE u1.id > u2.id;
```
* **Explanation**: We perform a self-join on `email`. If two rows share the same email, we delete the row `u1` if its `id` is larger than `u2.id`, leaving only the record with the minimum ID.

---

### 5. Find Employees who Earn More than Their Managers
**Problem**: List employees who earn more than their direct manager.
* **Schema**: `employees (id, name, salary, manager_id)`
```sql
SELECT e.name AS employee_name 
FROM employees e 
INNER JOIN employees m ON e.manager_id = m.id 
WHERE e.salary > m.salary;
```
* **Explanation**: We perform a Self Join. We treat alias `e` as the employee and alias `m` as the manager (linked by `e.manager_id = m.id`). We then filter where the employee's salary exceeds the manager's.

---

### 6. Find Customers who Never Ordered
**Problem**: List customers who have never placed an order.
* **Schema**: `customers (id, name)` and `orders (id, customer_id, amount)`
```sql
-- Solution 1: Left Join + NULL check (SARGable)
SELECT c.name 
FROM customers c 
LEFT JOIN orders o ON c.id = o.customer_id 
WHERE o.customer_id IS NULL;

-- Solution 2: NOT EXISTS
SELECT c.name 
FROM customers c 
WHERE NOT EXISTS (
    SELECT 1 FROM orders o WHERE o.customer_id = c.id
);
```
* **Explanation**: Solution 1 left joins customers to orders. Customers without orders will have NULL values in order columns, which we filter using `WHERE o.customer_id IS NULL`.

---

### 7. Find Departments with More than 5 Employees
**Problem**: List all departments that employ more than 5 workers.
* **Schema**: `employees (id, name, department)`
```sql
SELECT department, COUNT(*) AS employee_count 
FROM employees 
GROUP BY department 
HAVING COUNT(*) > 5;
```
* **Explanation**: We group employees by department, aggregate the counts using `COUNT(*)`, and filter groups using `HAVING COUNT(*) > 5`.

---

### 8. Calculate 3-Day Moving Average of Sales
**Problem**: Find the 3-day moving average of sales amount for each date (current day + 2 previous days).
* **Schema**: `daily_sales (sale_date, amount)`
```sql
SELECT sale_date, amount,
       AVG(amount) OVER (
           ORDER BY sale_date 
           ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
       ) AS moving_avg
FROM daily_sales;
```
* **Explanation**: We use the `AVG()` window function. The frame is defined inside `OVER` using `ROWS BETWEEN 2 PRECEDING AND CURRENT ROW`, forcing the engine to calculate the average over a sliding frame of exactly 3 records.

---

### 9. Find Consecutive Numbers appearing Three Times
**Problem**: Find all numbers that appear consecutively at least three times in a log table.
* **Schema**: `logs (id, num)`
```sql
SELECT DISTINCT l1.num 
FROM logs l1
INNER JOIN logs l2 ON l1.id = l2.id - 1
INNER JOIN logs l3 ON l1.id = l3.id - 2
WHERE l1.num = l2.num AND l2.num = l3.num;
```
* **Explanation**: We join the table to itself three times using offset IDs (`id - 1` and `id - 2`). We then filter where the values in all three joined columns are identical.

---

### 10. Pivot Table (Rows to Columns)
**Problem**: Rotate sales category amounts into columns for year 2026.
* **Schema**: `sales (year, category, amount)`
```sql
SELECT year,
       SUM(CASE WHEN category = 'Electronics' THEN amount ELSE 0 END) AS Electronics_Sales,
       SUM(CASE WHEN category = 'Clothing' THEN amount ELSE 0 END) AS Clothing_Sales
FROM sales
WHERE year = 2026
GROUP BY year;
```
* **Explanation**: We use **Conditional Aggregation**. For each row in the group, we check the category using a `CASE` statement. If it matches, we return the amount, otherwise 0, and sum the outputs.

---

### 11. Find Cumulative Running Total of Transactions
**Problem**: Calculate the running total (cumulative sum) of transaction amounts sorted by date.
* **Schema**: `transactions (id, transaction_date, amount)`
```sql
SELECT id, transaction_date, amount,
       SUM(amount) OVER (ORDER BY transaction_date, id) AS cumulative_sum
FROM transactions;
```
* **Explanation**: We use the `SUM()` window function. Ordering inside the `OVER` clause implicitly applies a default window frame from the start of the partition up to the current row, calculating a running sum.

---

### 12. Find Top 3 Highest Paid Employees per Department
**Problem**: List the top 3 earning employees within each department.
* **Schema**: `employees (id, name, salary, department)`
```sql
WITH RankedEmployees AS (
    SELECT name, department, salary,
           DENSE_RANK() OVER (PARTITION BY department ORDER BY salary DESC) as rnk
    FROM employees
)
SELECT department, name, salary 
FROM RankedEmployees 
WHERE rnk <= 3;
```
* **Explanation**: We partition employees by `department` and sort by `salary DESC` inside `DENSE_RANK()`. This ranks employees inside their respective departments. We then query the CTE to return ranks 1, 2, and 3.

---

### 13. Find Department with the Highest Total Salary
**Problem**: Find the department that has the highest cumulative salary expense.
* **Schema**: `employees (id, name, salary, department)`
```sql
SELECT department, SUM(salary) AS total_payroll 
FROM employees 
GROUP BY department 
ORDER BY total_payroll DESC 
LIMIT 1;
```
* **Explanation**: We group salaries by department, sum them, sort the resulting categories in descending order of payroll, and return the first row using `LIMIT 1`.

---

### 14. Find Second Most Recent Order for each User
**Problem**: Find the second most recent order details for every customer.
* **Schema**: `orders (order_id, user_id, order_date, amount)`
```sql
WITH SortedOrders AS (
    SELECT order_id, user_id, order_date, amount,
           ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY order_date DESC) as row_num
    FROM orders
)
SELECT user_id, order_id, order_date, amount 
FROM SortedOrders 
WHERE row_num = 2;
```
* **Explanation**: We partition orders by `user_id` and sort them by `order_date DESC`. We assign a sequential row number (`row_num`) starting at 1 (most recent). We filter for `row_num = 2` in the outer query.

---

### 15. Calculate Difference between Current and Previous Row
**Problem**: Find the difference in sales amount between the current day and the previous day.
* **Schema**: `sales (sale_date, amount)`
```sql
SELECT sale_date, amount,
       amount - LAG(amount, 1) OVER (ORDER BY sale_date) AS difference
FROM sales;
```
* **Explanation**: We use the `LAG(amount, 1)` window function to retrieve the `amount` value from exactly 1 row prior, and subtract it from the current row's `amount`.

---

### 16. Find Users with Consecutive Logins
**Problem**: Find users who logged in on at least two consecutive days.
* **Schema**: `logins (user_id, login_date)`
```sql
SELECT DISTINCT l1.user_id 
FROM logins l1 
INNER JOIN logins l2 ON l1.user_id = l2.user_id 
  AND l2.login_date = l1.login_date + INTERVAL '1 day';
```
* **Explanation**: We join the `logins` table to itself on `user_id`, matching rows where the second login date is exactly 1 day after the first login date.

---

### 17. Calculate Monthly Active Users (MAU)
**Problem**: Calculate the count of unique active users per month.
* **Schema**: `user_activities (user_id, activity_date)`
```sql
-- Using PostgreSQL DATE_TRUNC
SELECT DATE_TRUNC('month', activity_date) AS activity_month,
       COUNT(DISTINCT user_id) AS active_users
FROM user_activities 
GROUP BY activity_month 
ORDER BY activity_month;
```
* **Explanation**: We truncate the timestamp column to the month level using `DATE_TRUNC`, group by that month, and count unique users inside each month group using `COUNT(DISTINCT user_id)`.

---

### 18. Swap Salary Gender Values in one Update
**Problem**: Swap all 'm' values to 'f' and vice versa in the `sex` column of a table in a single update without using select.
* **Schema**: `salary (id, name, sex)`
```sql
UPDATE salary 
SET sex = CASE WHEN sex = 'm' THEN 'f' ELSE 'm' END;
```
* **Explanation**: We use an inline `CASE` statement inside the `SET` clause of the `UPDATE` query, switching values dynamically row-by-row.

---

### 19. Calculate Total Distance Traveled by Car
**Problem**: Find the cumulative distance traveled by each car.
* **Schema**: `car_trips (car_id, trip_date, odometer_reading)`
```sql
SELECT car_id,
       MAX(odometer_reading) - MIN(odometer_reading) AS total_distance
FROM car_trips 
GROUP BY car_id;
```
* **Explanation**: Assuming odometer readings only increase, the total distance is the maximum odometer reading minus the minimum odometer reading grouped for each car ID.

---

### 20. Join with Optional Fallbacks (COALESCE)
**Problem**: Combine employee contact details, falling back to phone if email is missing.
* **Schema**: `employees (id, name, email, phone)`
```sql
SELECT name, 
       COALESCE(email, phone, 'No Contact Details') AS primary_contact 
FROM employees;
```
* **Explanation**: `COALESCE` checks arguments from left to right. It returns the email if present; if email is NULL, it returns the phone; if phone is also NULL, it falls back to the literal string.

---

### 21. Count Orders per Customer (including 0 orders)
**Problem**: List all customers and the count of orders they placed, displaying 0 for inactive customers.
* **Schema**: `customers (id, name)` and `orders (id, customer_id)`
```sql
SELECT c.name, COUNT(o.id) AS order_count 
FROM customers c 
LEFT JOIN orders o ON c.id = o.customer_id 
GROUP BY c.id, c.name;
```
* **Explanation**: We use a `LEFT JOIN` to keep all customers. We group by customer columns and use `COUNT(o.id)` instead of `COUNT(*)`. `COUNT(o.id)` ignores NULLs, returning 0 for customers without orders.

---

### 22. Select Top 10% Highest Selling Products
**Problem**: Find products that belong to the top 10% highest selling brackets.
* **Schema**: `products (id, name, sales_count)`
```sql
-- Using PostgreSQL PERCENT_RANK
WITH RankedProducts AS (
    SELECT name, sales_count,
           PERCENT_RANK() OVER (ORDER BY sales_count DESC) as pct_rank
    FROM products
)
SELECT name, sales_count 
FROM RankedProducts 
WHERE pct_rank <= 0.10;
```
* **Explanation**: We calculate the percentile ranking using `PERCENT_RANK()` descending. Pervasive values <= 0.10 represent the top 10% selling products.

---

### 23. Find the Median of a Column
**Problem**: Find the median salary of all employees.
* **Schema**: `employees (id, salary)`
```sql
-- Solution using PERCENTILE_CONT (PostgreSQL/Oracle standard)
SELECT PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY salary) AS median_salary 
FROM employees;
```
* **Explanation**: `PERCENTILE_CONT(0.5)` is the standard SQL function used to calculate the 50th percentile (the median) of a sorted column group.

---

### 24. Find Project with Maximum Employees
**Problem**: Find the project name that has the largest number of employees.
* **Schema**: `projects (id, name)` and `employee_projects (emp_id, project_id)`
```sql
SELECT p.name, COUNT(ep.emp_id) AS employee_count 
FROM projects p 
INNER JOIN employee_projects ep ON p.id = ep.project_id 
GROUP BY p.id, p.name 
ORDER BY employee_count DESC 
LIMIT 1;
```
* **Explanation**: We join projects with employee associations, group by project name, count the linked employee IDs, sort descending, and return the first row.

---

### 25. Fetch Employees Hired in Last 30 Days
**Problem**: Find employees who were hired in the last 30 days.
* **Schema**: `employees (id, name, hire_date)`
```sql
-- Standard PostgreSQL / MySQL compatible format
SELECT name, hire_date 
FROM employees 
WHERE hire_date >= CURRENT_DATE - INTERVAL '30 day';
```
* **Explanation**: We filter `hire_date` against the current system date minus an interval of 30 days, which is sargable and runs efficiently on indexed date columns.

---

## ⏭️ Next Steps

* **Java Interview Guide**: [👈 Java Practical Coding Interview Guide](../Java/coding_interview_questions.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
