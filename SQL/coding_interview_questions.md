# 📊 SQL Practical Coding Interview Guide

Welcome, database candidate! This guide compiles the **Top 50 most frequently asked SQL query coding questions** in technical interviews. Every question includes mock schemas, query solutions, and detailed execution breakdowns.

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
26. [Find the Employee with the Second Lowest Salary](#26-find-the-employee-with-the-second-lowest-salary)
27. [Count Logins per User within a 7-Day Window](#27-count-logins-per-user-within-a-7-day-window)
28. [Retrieve Users who Purchased on Their Registration Date](#28-retrieve-users-who-purchased-on-their-registration-date)
29. [Find the Top-Selling Product in each Category](#29-find-the-top-selling-product-in-each-category)
30. [Select Employees who Do Not Report to Anyone](#30-select-employees-who-do-not-report-to-anyone)
31. [Calculate Cumulative Percentage of Sales Contribution](#31-calculate-cumulative-percentage-of-sales-contribution)
32. [Find Duplicate Rows Based on Multiple Columns](#32-find-duplicate-rows-based-on-multiple-columns)
33. [Retrieve Rows from Table A that Do Not Exist in Table B](#33-retrieve-rows-from-table-a-that-do-not-exist-in-table-b)
34. [Find Users who Ordered Both Product A and Product B](#34-find-users-who-ordered-both-product-a-and-product-b)
35. [Calculate Average Time Gap between Consecutive Orders](#35-calculate-average-time-gap-between-consecutive-orders)
36. [Classify Employees into Salary Brackets Using CASE](#36-classify-employees-into-salary-brackets-using-case)
37. [Find the Date When Total Sales Exceeded $10,000 First](#37-find-the-date-when-total-sales-exceeded-10000-first)
38. [Report Products That Haven't Received Orders in 6 Months](#38-report-products-that-havent-received-orders-in-6-months)
39. [Report Monthly Revenue Including Months with 0 Revenue](#39-report-monthly-revenue-including-months-with-0-revenue)
40. [Select the First Order Placed by each Customer](#40-select-the-first-order-placed-by-each-customer)
41. [Find the Running Average of Transaction Amounts per User](#41-find-the-running-average-of-transaction-amounts-per-user)
42. [Select Top 5 Departments by Average Salary](#42-select-top-5-departments-by-average-salary)
43. [Pivot Table Showing Orders Count per Status per Month](#43-pivot-table-showing-orders-count-per-status-per-month)
44. [Find the Most Expensive Order and the User Who Placed It](#44-find-the-most-expensive-order-and-the-user-who-placed-it)
45. [Find the Employee who Has the Longest Tenure](#45-find-the-employee-who-has-the-longest-tenure)
46. [Report Departments whose Total Salary Exceeds the Average](#46-report-departments-whose-total-salary-exceeds-the-average)
47. [Find Date with the Maximum Transactions Count](#47-find-date-with-the-maximum-transactions-count)
48. [Swap Names of Two Users in a Table](#48-swap-names-of-two-users-in-a-table)
49. [Find the Customer who Has Spent the Most Money Overall](#49-find-the-customer-who-has-spent-the-most-money-overall)
50. [Retrieve Records Above the 90th Percentile](#50-retrieve-records-above-the-90th-percentile)

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

### 26. Find the Employee with the Second Lowest Salary
**Problem**: Write a query to find the employee(s) earning the second lowest salary.
* **Schema**: `employees (id, name, salary)`
```sql
SELECT id, name, salary 
FROM employees 
WHERE salary = (
    SELECT MIN(salary) 
    FROM employees 
    WHERE salary > (SELECT MIN(salary) FROM employees)
);
```
* **Explanation**: The innermost subquery finds the absolute minimum salary. The middle subquery finds the minimum salary that is strictly greater than that absolute minimum (which is the second lowest salary). The outer query returns all employee details matching this value.

---

### 27. Count Logins per User within a 7-Day Window
**Problem**: Find users who logged in at least 3 times within any rolling 7-day period.
* **Schema**: `logins (id, user_id, login_date)`
```sql
SELECT DISTINCT l1.user_id 
FROM logins l1
INNER JOIN logins l2 ON l1.user_id = l2.user_id 
  AND l2.login_date BETWEEN l1.login_date AND l1.login_date + INTERVAL '6 day'
GROUP BY l1.user_id, l1.login_date
HAVING COUNT(DISTINCT l2.id) >= 3;
```
* **Explanation**: We perform a self-join on `user_id`. For each login record `l1`, we pull all other logins `l2` for that same user that occurred within the next 6 days (a 7-day window). We group by the starting login and filter groups containing 3 or more unique login IDs.

---

### 28. Retrieve Users who Purchased on Their Registration Date
**Problem**: Find users who placed an order on the exact same calendar date they registered.
* **Schema**: `users (id, name, register_date)` and `orders (id, user_id, order_date)`
```sql
SELECT DISTINCT u.id, u.name 
FROM users u 
INNER JOIN orders o ON u.id = o.user_id 
  AND DATE(u.register_date) = DATE(o.order_date);
```
* **Explanation**: We inner join the tables on user ID and match only when the truncated date values of the registration timestamp and order timestamp are identical.

---

### 29. Find the Top-Selling Product in each Category
**Problem**: Retrieve the product name that has the highest sales count in each category.
* **Schema**: `products (id, name, category, sales_count)`
```sql
WITH RankedProducts AS (
    SELECT name, category, sales_count,
           ROW_NUMBER() OVER (PARTITION BY category ORDER BY sales_count DESC) as rnk
    FROM products
)
SELECT category, name, sales_count 
FROM RankedProducts 
WHERE rnk = 1;
```
* **Explanation**: We partition our products by `category` and sort by `sales_count DESC`. We assign a sequential row number (`rnk`) to each category partition. The product with `rnk = 1` is the top-seller in that category.

---

### 30. Select Employees who Do Not Report to Anyone
**Problem**: Find all employees who do not have a supervisor (manager_id is NULL).
* **Schema**: `employees (id, name, manager_id)`
```sql
SELECT id, name 
FROM employees 
WHERE manager_id IS NULL;
```
* **Explanation**: In SQL, comparisons to `NULL` must use the `IS NULL` operator because `NULL = NULL` evaluates to Unknown rather than True.

---

### 31. Calculate Cumulative Percentage of Sales Contribution
**Problem**: Calculate the cumulative percentage contribution of sales for each product category.
* **Schema**: `category_sales (category, sales_amount)`
```sql
WITH TotalSales AS (
    SELECT SUM(sales_amount) AS grand_total FROM category_sales
)
SELECT category, sales_amount,
       SUM(sales_amount) OVER (ORDER BY sales_amount DESC) / (SELECT grand_total FROM TotalSales) * 100 AS cumulative_pct
FROM category_sales;
```
* **Explanation**: We calculate the global total sales in a CTE. In our select list, we use a running sum of sales (`SUM(sales_amount) OVER (...)`) divided by the global total, multiplying by 100 to get a running percentage contribution.

---

### 32. Find Duplicate Rows Based on Multiple Columns
**Problem**: Find duplicate rows in a table where names and departments match.
* **Schema**: `employees (id, name, department)`
```sql
SELECT name, department, COUNT(*) AS occurrences 
FROM employees 
GROUP BY name, department 
HAVING COUNT(*) > 1;
```
* **Explanation**: We group by both `name` and `department` columns simultaneously. The `HAVING` clause filters the groups, returning only combinations that appear more than once.

---

### 33. Retrieve Rows from Table A that Do Not Exist in Table B
**Problem**: Retrieve all records from `table_a` that are missing in `table_b` without using any type of JOIN.
* **Schema**: `table_a (id, name)` and `table_b (id, name)`
```sql
SELECT id, name 
FROM table_a 
WHERE id NOT IN (
    SELECT id FROM table_b WHERE id IS NOT NULL
);
```
* **Explanation**: We use the `NOT IN` subquery filter. We explicitly filter out NULL values in the subquery because if any value in a `NOT IN` set is NULL, the entire comparison evaluates to Unknown, returning zero rows.

---

### 34. Find Users who Ordered Both Product A and Product B
**Problem**: Identify users who placed orders for both 'Product A' and 'Product B'.
* **Schema**: `orders (user_id, product_name)`
```sql
SELECT user_id 
FROM orders 
WHERE product_name IN ('Product A', 'Product B') 
GROUP BY user_id 
HAVING COUNT(DISTINCT product_name) = 2;
```
* **Explanation**: We filter for rows matching either product. We group by `user_id` and check if the count of distinct product names in the group equals 2, guaranteeing both products were ordered by that user.

---

### 35. Calculate Average Time Gap between Consecutive Orders
**Problem**: Calculate the average number of days between consecutive orders placed by each user.
* **Schema**: `orders (order_id, user_id, order_date)`
```sql
WITH OrderGaps AS (
    SELECT user_id,
           order_date - LAG(order_date) OVER (PARTITION BY user_id ORDER BY order_date) AS gap_days
    FROM orders
)
SELECT user_id, AVG(gap_days) AS avg_days_between_orders 
FROM OrderGaps 
GROUP BY user_id;
```
* **Explanation**: We use `LAG` partitioned by `user_id` to compute the date difference between the current order and the previous order. We then average these gaps in the outer query grouped by user ID.

---

### 36. Classify Employees into Salary Brackets Using CASE
**Problem**: Categorize employees into 'High' (>80k), 'Medium' (50k-80k), or 'Low' (<50k) salary tiers.
* **Schema**: `employees (id, name, salary)`
```sql
SELECT name, salary,
       CASE 
           WHEN salary > 80000 THEN 'High'
           WHEN salary BETWEEN 50000 AND 80000 THEN 'Medium'
           ELSE 'Low'
       END AS salary_bracket
FROM employees;
```
* **Explanation**: The `CASE` expression acts as a conditional switch, evaluating clauses sequentially from top to bottom and returning the matching string.

---

### 37. Find the Date When Total Sales Exceeded $10,000 First
**Problem**: Identify the date when the company's cumulative sales exceeded $10,000 for the first time.
* **Schema**: `daily_sales (sale_date, amount)`
```sql
WITH CumulativeSales AS (
    SELECT sale_date,
           SUM(amount) OVER (ORDER BY sale_date) AS running_total
    FROM daily_sales
)
SELECT MIN(sale_date) AS breakthrough_date 
FROM CumulativeSales 
WHERE running_total > 10000;
```
* **Explanation**: We calculate a cumulative sum of sales sorted by date inside a CTE. In the outer query, we filter for rows where `running_total > 10000` and pick the earliest date using `MIN(sale_date)`.

---

### 38. Report Products That Haven't Received Orders in 6 Months
**Problem**: Identify products that have not been ordered in the last 6 months.
* **Schema**: `products (id, name)` and `orders (id, product_id, order_date)`
```sql
SELECT p.id, p.name 
FROM products p
WHERE p.id NOT IN (
    SELECT DISTINCT product_id 
    FROM orders 
    WHERE order_date >= CURRENT_DATE - INTERVAL '6 month'
);
```
* **Explanation**: The subquery fetches all product IDs that were ordered in the last 6 months. We filter the main products list to return only those IDs not present in that subquery set.

---

### 39. Report Monthly Revenue Including Months with 0 Revenue
**Problem**: Display total revenue for each month of year 2026, showing 0 for months without any sales.
* **Schema**: `orders (order_date, amount)`
```sql
-- Generate mock months calendar table dynamically (PostgreSQL syntax)
WITH RECURSIVE Months AS (
    SELECT DATE '2026-01-01' AS month_start
    UNION ALL
    SELECT month_start + INTERVAL '1 month'
    FROM Months
    WHERE month_start < DATE '2026-12-01'
)
SELECT m.month_start,
       COALESCE(SUM(o.amount), 0) AS total_revenue
FROM Months m
LEFT JOIN orders o ON DATE_TRUNC('month', o.order_date) = m.month_start
GROUP BY m.month_start
ORDER BY m.month_start;
```
* **Explanation**: We use a recursive CTE to generate all 12 calendar months of 2026. We LEFT JOIN this generated calendar to the `orders` table, grouping by month and using `COALESCE(SUM(o.amount), 0)` to display 0 instead of NULLs for inactive months.

---

### 40. Select the First Order Placed by each Customer
**Problem**: Find the order details for the very first order placed by each customer.
* **Schema**: `orders (order_id, customer_id, order_date, amount)`
```sql
WITH FirstOrders AS (
    SELECT order_id, customer_id, order_date, amount,
           ROW_NUMBER() OVER (PARTITION BY customer_id ORDER BY order_date ASC) as rn
    FROM orders
)
SELECT customer_id, order_id, order_date, amount 
FROM FirstOrders 
WHERE rn = 1;
```
* **Explanation**: We partition orders by `customer_id` and sort them ascending by date (`order_date ASC`). The row with `rn = 1` represents the earliest order for each customer.

---

### 41. Find the Running Average of Transaction Amounts per User
**Problem**: Calculate the running average of transaction amounts for each user sorted by date.
* **Schema**: `transactions (id, user_id, transaction_date, amount)`
```sql
SELECT user_id, transaction_date, amount,
       AVG(amount) OVER (
           PARTITION BY user_id 
           ORDER BY transaction_date, id
       ) AS running_avg
FROM transactions;
```
* **Explanation**: We partition by `user_id` and sort by date. The window frame defaults to everything from the start of the partition up to the current row, calculating a rolling average per user.

---

### 42. Select Top 5 Departments by Average Salary
**Problem**: Find the top 5 departments by average salary, excluding departments with fewer than 3 employees.
* **Schema**: `employees (id, salary, department)`
```sql
SELECT department, AVG(salary) AS avg_salary 
FROM employees 
GROUP BY department 
HAVING COUNT(*) >= 3 
ORDER BY avg_salary DESC 
LIMIT 5;
```
* **Explanation**: We group employees by department, filter out groups with fewer than 3 rows using `HAVING COUNT(*) >= 3`, sort descending by average salary, and limit results to the top 5.

---

### 43. Pivot Table Showing Orders Count per Status per Month
**Problem**: Create a report displaying order counts per status (Pending, Completed) for each month.
* **Schema**: `orders (order_date, status)`
```sql
SELECT DATE_TRUNC('month', order_date) AS order_month,
       SUM(CASE WHEN status = 'Pending' THEN 1 ELSE 0 END) AS Pending_Count,
       SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) AS Completed_Count
FROM orders
GROUP BY order_month
ORDER BY order_month;
```
* **Explanation**: We aggregate counts using conditional CASE evaluations inside `SUM()`, grouping by the truncated order date month.

---

### 44. Find the Most Expensive Order and the User Who Placed It
**Problem**: Retrieve the details of the single most expensive order ever placed.
* **Schema**: `orders (order_id, user_id, amount)` and `users (id, name)`
```sql
SELECT u.name, o.order_id, o.amount 
FROM orders o 
INNER JOIN users u ON o.user_id = u.id 
WHERE o.amount = (SELECT MAX(amount) FROM orders);
```
* **Explanation**: The subquery finds the maximum order amount. The outer query joins orders and users and returns the records matching that maximum value.

---

### 45. Find the Employee who Has the Longest Tenure
**Problem**: Find the employee who has been working at the company the longest.
* **Schema**: `employees (id, name, hire_date)`
```sql
SELECT name, hire_date 
FROM employees 
ORDER BY hire_date ASC 
LIMIT 1;
```
* **Explanation**: We sort all employees in ascending chronological order of their `hire_date` (oldest hires first) and return the first record using `LIMIT 1`.

---

### 46. Report Departments whose Total Salary Exceeds the Average
**Problem**: Find departments whose combined salary cost is greater than the average combined salary cost of all departments.
* **Schema**: `employees (id, salary, department)`
```sql
WITH DeptSalaries AS (
    SELECT department, SUM(salary) AS total_salary 
    FROM employees 
    GROUP BY department
)
SELECT department, total_salary 
FROM DeptSalaries 
WHERE total_salary > (SELECT AVG(total_salary) FROM DeptSalaries);
```
* **Explanation**: The CTE aggregates total salary costs per department. The outer query filters for departments whose aggregated total is greater than the average of all aggregated category totals.

---

### 47. Find Date with the Maximum Transactions Count
**Problem**: Find the date on which the database logged the highest number of transactions.
* **Schema**: `transactions (id, transaction_date)`
```sql
SELECT DATE(transaction_date) AS tx_date,
       COUNT(*) AS tx_count
FROM transactions 
GROUP BY tx_date 
ORDER BY tx_count DESC 
LIMIT 1;
```
* **Explanation**: We group transaction records by their truncated date values, count occurrences, sort descending, and return the top date using `LIMIT 1`.

---

### 48. Swap Names of Two Users in a Table
**Problem**: Swap the names of users with IDs 1 and 2 in a single update query.
* **Schema**: `users (id, name)`
```sql
UPDATE users 
SET name = CASE 
               WHEN id = 1 THEN (SELECT name FROM (SELECT name FROM users WHERE id = 2) AS tmp)
               WHEN id = 2 THEN (SELECT name FROM (SELECT name FROM users WHERE id = 1) AS tmp)
           END
WHERE id IN (1, 2);
```
* **Explanation**: We update only rows with ID 1 and 2. We use a conditional CASE statement. Since subqueries in updates cannot reference the modified table directly in some SQL engines, we wrap the lookup query in a temporary table alias (`AS tmp`) to fetch values safely.

---

### 49. Find the Customer who Has Spent the Most Money Overall
**Problem**: Retrieve the customer details for the highest spending customer.
* **Schema**: `customers (id, name)` and `orders (id, customer_id, amount)`
```sql
SELECT c.name, SUM(o.amount) AS total_spent 
FROM customers c 
INNER JOIN orders o ON c.id = o.customer_id 
GROUP BY c.id, c.name 
ORDER BY total_spent DESC 
LIMIT 1;
```
* **Explanation**: We join customers with orders, group by customer identity, sum up the order amounts, sort the groups in descending order of total spend, and pick the first row.

---

### 50. Retrieve Records Above the 90th Percentile
**Problem**: Retrieve all product records whose sales count falls inside the top 10% bracket of sales.
* **Schema**: `products (id, name, sales_count)`
```sql
WITH Ranked AS (
    SELECT name, sales_count,
           PERCENT_RANK() OVER (ORDER BY sales_count ASC) as pct_rank
    FROM products
)
SELECT name, sales_count 
FROM Ranked 
WHERE pct_rank >= 0.90;
```
* **Explanation**: We calculate the percentile rank sorting ascending. Values matching `pct_rank >= 0.90` represent records that are in the 90th percentile or above (top 10% values).

---

## ⏭️ Next Steps

* **Java Interview Guide**: [👈 Java Practical Coding Interview Guide](../Java/coding_interview_questions.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)

