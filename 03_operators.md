# ➕ Topic 03: The Magic Wands (Operators)

To play with our toys (variables), we need magic wands to change them, compare them, and combine them. In Java, these wands are called **Operators**.

---

## 🏠 The Big Picture & Real-Life Example

### ⚖️ The Seesaw Analogy
Imagine you and your friend are on a playground seesaw.
* If you want to know who is heavier, you compare your weights: `Is Vishal > Friend?`
* If you want to join forces and sit on one side together, you add your weights: `Vishal + Friend`.
* If you want to check if both of you are wearing sneakers before entering the sandbox, you use logic: `Is Vishal wearing sneakers? AND Is Friend wearing sneakers?`

In Java, we use operators to do math, compare values, and make logical decisions!

---

## 🔬 Let's Look Closer: Types of Operators

Java has different wands for different kinds of magic:

### 1. 🧮 Arithmetic Operators (The Math Wands)
Used to do basic math:
* `+` (Plus): Adds numbers.
* `-` (Minus): Subtracts numbers.
* `*` (Times): Multiplies numbers.
* `/` (Divide): Divides numbers.
* `%` (Modulo): Finds the **leftover** (remainder) after division.
  * *Example*: If you have 5 cookies and share them with 2 friends, you give each 2 cookies, and you have `5 % 2 = 1` cookie leftover!
* `++` (Increment): Adds `1` to a number.
  * `++x` (Prefix): Adds 1 first, then uses the value.
  * `x++` (Postfix): Uses the value first, then adds 1.
* `--` (Decrement): Subtracts `1` from a number.

### 2. 📝 Assignment Operators (The Hand-over Wands)
Used to put a value into a box:
* `=` (Equals): Puts the right-side value into the left-side box (e.g., `x = 5`).
* `+=` (Add and Assign): Adds and stores (e.g., `x += 2` is short for `x = x + 2`).
* `-=`, `*=`, `/=`, `%=`: Do the math and store the result.

### 3. ⚖️ Relational / Comparison Operators (The Measuring Wands)
Used to compare two boxes. The result is always a boolean (`true` or `false`):
* `==` (Is Equal to?): *Double equals checks if they are the same!*
* `!=` (Is NOT Equal to?): Checks if they are different.
* `>`, `<`: Greater than, Less than.
* `>=`, `<=`: Greater than or equal to, Less than or equal to.

### 4. 🧠 Logical Operators (The Decision Wands)
Used to combine multiple rules:
* `&&` (AND): **Both** rules must be true.
  * *Example*: `Do I have money? && Is the toy shop open?`
* `||` (OR): **At least one** rule must be true.
  * *Example*: `Do I have a car? || Can I walk there?`
* `!` (NOT): Flips the truth. `!true` becomes `false`.

### 🔌 5. Bitwise Operators (The Light Switch Wands)
Computers think in binary (zeros and ones), which are like light switches that are either **OFF (0)** or **ON (1)**. Bitwise operators flip these switches:
* `&` (Bitwise AND): Only ON if both switches are ON.
* `|` (Bitwise OR): ON if at least one switch is ON.
* `^` (Bitwise XOR): ON only if the switches are different (one ON, one OFF).
* `~` (Bitwise NOT): Flips all switches (ON becomes OFF, OFF becomes ON).
* `<<` (Left Shift): Moves switches to the left (multiplies by 2).
* `>>` (Right Shift): Moves switches to the right (divides by 2).

### ❓ 6. Ternary Operator (The Shortcut Wand)
A tiny, one-line decision maker:
`variable = (condition) ? value_if_true : value_if_false;`

---

## 📖 Key Definitions

* **Operator**: A special symbol (like `+` or `&&`) used to perform mathematical or logical calculations on values.
* **Operand**: The target values or variables that are being acted on by an operator (e.g., in `x + y`, `x` and `y` are the operands).
* **Modulo Operator (`%`)**: An arithmetic operator that divides one number by another and returns only the leftover remainder.
* **Logical Operators**: Operators (like `&&` and `||`) used to combine multiple true/false conditions and return a single true/false result.
* **Bitwise Operators**: Operators that manipulate the individual binary digits (0s and 1s) of a number.
* **Ternary Operator**: A short, one-line conditional operator `? :` that acts as a shorthand for a simple `if-else` statement.

---

## 💻 Code Sandbox: Magic in Action

Here is a simple playground script demonstrating all these operators:

```java
public class OperatorsDemo {
    public static void main(String[] args) {
        // --- 1. Math Magic ---
        int cookies = 7;
        int friends = 3;
        int leftover = cookies % friends; // Leftover of 7 / 3
        System.out.println("Leftover cookies: " + leftover); // Output: 1

        int health = 10;
        health++; // Adds 1. Same as health = health + 1;
        System.out.println("Health after power-up: " + health); // Output: 11

        // --- 2. Measuring Magic (Comparison) ---
        int myScore = 95;
        int passingScore = 50;
        boolean didIPass = myScore >= passingScore;
        System.out.println("Did I pass? " + didIPass); // Output: true

        // --- 3. Decision Magic (Logical) ---
        boolean isWeekend = true;
        boolean isRaining = false;
        // I can play outside if it's the weekend AND it is NOT raining!
        boolean canPlayOutside = isWeekend && !isRaining;
        System.out.println("Can I play outside? " + canPlayOutside); // Output: true

        // --- 4. Light Switch Magic (Bitwise) ---
        int a = 5; // Binary: 0101
        int b = 3; // Binary: 0011
        System.out.println("Bitwise AND (5 & 3): " + (a & b)); // Output: 1 (Binary: 0001)
        System.out.println("Bitwise OR (5 | 3): " + (a | b));  // Output: 7 (Binary: 0111)

        // --- 5. Shortcut Magic (Ternary) ---
        int energy = 20;
        String activity = (energy > 50) ? "Running" : "Sleeping";
        System.out.println("Activity: " + activity); // Output: Sleeping
    }
}
```

---

## 🧠 Points to Remember

> [!IMPORTANT]
> * `=` is for **assigning** a value (putting a toy in a box).
> * `==` is for **comparing** values (checking if two toys are the same).
> * Operators have precedence (order of operations), just like in math. Parentheses `()` always run first!

> [!TIP]
> **Modulo Riddle:**
> If `x % 2 == 0`, it means the number `x` has zero leftover when divided by 2. This is how programmers check if a number is **Even**!

---

## ❓ Interview Questions (Q1 - Q50)

### 🟢 Basic Questions (Q1 - Q20)
1. **What is an operator in Java?**
   * *Answer*: A symbol that performs a specific operation on one, two, or three operands and returns a result.
2. **What are the five main arithmetic operators?**
   * *Answer*: Addition (`+`), Subtraction (`-`), Multiplication (`*`), Division (`/`), and Modulo (`%`).
3. **What is the difference between the `=` and `==` operators?**
   * *Answer*: `=` is the assignment operator (stores a value in a variable); `==` is the comparison operator (checks if two values are equal).
4. **What does the Modulo (`%`) operator do?**
   * *Answer*: It returns the remainder of a division operation (e.g., `5 % 2` returns `1`).
5. **How do logical `&&` and `||` differ from relational comparison operators?**
   * *Answer*: Logical operators combine multiple boolean expressions; relational operators compare two values (e.g., numbers) to return a boolean.
6. **What is the decrement operator in Java?**
   * *Answer*: The `--` operator, which decreases the value of a variable by `1`.
7. **What is the ternary operator?**
   * *Answer*: An operator representing a shorthand `if-else` block. Its format is: `condition ? value_if_true : value_if_false`.
8. **What does the `!` (Logical NOT) operator do?**
   * *Answer*: It reverses the boolean value of an operand (e.g., `!true` becomes `false`).
9. **What are compound assignment operators?**
   * *Answer*: Shorthand operators combining math operations and assignment (e.g., `+=`, `-=`, `*=`, `/=`, `%=`).
10. **What is the result of `10 != 5`?**
    * *Answer*: `true`, because 10 is indeed not equal to 5.
11. **What are unary operators?**
    * *Answer*: Operators that require only one operand (e.g., `++`, `--`, `!`, `-` for negation).
12. **What are binary operators?**
    * *Answer*: Operators that require two operands (e.g., `+`, `-`, `*`, `/`, `&&`, `==`).
13. **What are ternary operators?**
    * *Answer*: Operators that require three operands (the only one in Java is `? :`).
14. **How do you check if a number is odd using an operator?**
    * *Answer*: By checking if the remainder of division by 2 is not zero (e.g., `num % 2 != 0`).
15. **What does `x += 5` mean?**
    * *Answer*: It is shorthand for `x = x + 5`.
16. **What are the relational operators in Java?**
    * *Answer*: `==`, `!=`, `>`, `<`, `>=`, and `<=`.
17. **What is the default order of operations for `*` and `+`?**
    * *Answer*: Multiplication (`*`) is evaluated first because it has higher precedence than addition (`+`).
18. **Can operators be overloaded by programmers in Java?**
    * *Answer*: No, Java does not support user-defined operator overloading.
19. **Which operator is used for string concatenation?**
    * *Answer*: The `+` operator (e.g., `"Hello " + "World"`).
20. **What is the value of `true || false`?**
    * *Answer*: `true`.

### 🟡 Intermediate Questions (Q21 - Q40)
21. **What is the difference between prefix (`++x`) and postfix (`x++`) increment operators?**
   * *Answer*: Prefix increment (`++x`) increases the value first and then returns the new value. Postfix increment (`x++`) returns the current value first, and then increases the variable.
22. **What is short-circuit evaluation in logical operators?**
   * *Answer*: It means logical operators (`&&`, `||`) skip evaluating the second expression if the final result is already determined by the first expression (e.g., if the first part of `&&` is `false`, the second part is ignored).
23. **What is the difference between logical AND (`&&`) and bitwise AND (`&`) when used with booleans?**
   * *Answer*: `&&` applies short-circuiting; `&` always evaluates both operands regardless of the first operand's value.
24. **What happens if you divide an integer by zero (`5 / 0`) in Java?**
   * *Answer*: Java throws an `ArithmeticException: / by zero` at runtime.
25. **What happens if you divide a double by zero (`5.0 / 0.0`) in Java?**
   * *Answer*: It does not throw an exception; it returns `Infinity` or `NaN` (if dividing `0.0 / 0.0`).
26. **What does the Bitwise XOR (`^`) operator do?**
   * *Answer*: It compares matching bits of two numbers, returning `1` if the bits are different, and `0` if they are identical.
27. **What is the shift operator `<<` (Left Shift)?**
   * *Answer*: It shifts the bits of a number to the left by a specified number of positions, filling empty spaces on the right with `0`s (equivalent to multiplying by $2^n$).
28. **What is the shift operator `>>` (Signed Right Shift)?**
   * *Answer*: It shifts bits to the right, filling empty spaces on the left with the sign bit (maintains positive or negative sign).
29. **What occurs during numeric promotion in operations like `byte a = 1; byte b = 2; byte c = a + b;`?**
   * *Answer*: It fails to compile because binary arithmetic operations promote operands of type `byte`, `short`, or `char` to `int` before evaluation, returning an `int` result.
30. **What is operator precedence?**
   * *Answer*: A set of rules defining the order in which operators are evaluated in a complex expression.
31. **What is operator associativity?**
   * *Answer*: The order of evaluation (left-to-right or right-to-left) when operators of the same precedence appear in an expression.
32. **What is the result of `10 + 20 + "30"` in Java?**
    * *Answer*: `"3030"`. First, `10 + 20` evaluates to `30` (math), then `30 + "30"` concatenates to the string `"3030"`.
33. **What is the result of `"10" + 20 + 30` in Java?**
    * *Answer*: `"102030"`. The first addition is string concatenation `"1020"`, which makes the entire expression evaluate as strings from left to right.
34. **What does the bitwise complement operator (`~`) do?**
    * *Answer*: It flips all bits of a number, changing `0` to `1` and `1` to `0` (yielding $-x - 1$ in signed decimal representation).
35. **What is the result of `5 & 1`?**
    * *Answer*: `1`. 5 in binary is `0101`, 1 is `0001`. Performing bitwise AND yields `0001` (1).
36. **Is `instanceof` an operator in Java?**
    * *Answer*: Yes, it is a binary operator used to test if an object is an instance of a specific class or interface.
37. **What is the associativity of the assignment operator (`=`)?**
    * *Answer*: Right-to-left (e.g., `a = b = c = 10;` assigns 10 to c, then b, then a).
38. **How does the relational check `==` compare reference types?**
    * *Answer*: It checks if both reference variables point to the exact same memory address (object identity), not whether their contents are identical.
39. **What is the result of `true ^ true`?**
    * *Answer*: `false`, because the operands are identical.
40. **How do parentheses `()` affect operator execution?**
    * *Answer*: They override default operator precedence rules, forcing the expression inside them to be evaluated first.

### 🔴 Advanced Questions (Q41 - Q50)
41. **What is the difference between `>>` (Signed Right Shift) and `>>>` (Unsigned Right Shift)?**
   * *Answer*: `>>` fills the left bits with the sign bit (preserving the sign). `>>>` always fills the left bits with `0`s regardless of whether the original number was positive or negative.
42. **Explain the short-circuit side-effect pitfall.**
   * *Answer*: In `if (a == 5 || ++b > 10)`, if `a == 5` is `true`, the second condition is skipped, meaning `++b` is never executed. This can cause bugs if code relies on `b` incrementing.
43. **How is the expression `a = a++` evaluated?**
   * *Answer*: The postfix `a++` evaluates to the current value of `a` first, then increments `a`. However, the assignment `=` assigns the evaluated original value back to `a`, overwriting the increment. Thus, `a`'s value remains unchanged.
44. **What is the numeric promotion type of the expression `double d = 1.0f + 2L + 3.0;`?**
   * *Answer*: The operands are `float`, `long`, and `double`. According to Java promotion rules, the expression is promoted to the widest type, which is `double`.
45. **What are the type compatibility rules of the ternary operator `? :`?**
   * *Answer*: The second and third operands must be compatible or convertible to a common type, which the compiler determines at compile-time (e.g., returning `int` and `double` promotes the result to `double`).
46. **What is the bitwise representation and decimal value of `~0`?**
   * *Answer*: `0` in binary is all `0`s. `~0` flips all bits to `1`s. In signed two's complement, this represents `-1`.
47. **What is the result of comparing `Double.NaN == Double.NaN`?**
   * *Answer*: `false`. The IEEE 754 standard defines that `NaN` (Not-a-Number) is never equal to any other value, including itself. To check for NaN, use `Double.isNaN()`.
48. **Why does `x = x + y` fail compilation while `x += y` succeeds when `x` is `short` and `y` is `int`?**
   * *Answer*: `x + y` promotes to `int` and cannot be implicitly converted to a smaller type `short`. However, `x += y` is equivalent to `x = (short)(x + y)` due to the implicit cast built into compound assignment.
49. **How do bitwise shifts handle shift counts larger than the bit size of the type (e.g., shifting an `int` by 33)?**
   * *Answer*: The JVM applies a bitmask to the shift count using modulo 32 for `int` (shifting by 33 is equivalent to shifting by $33 \pmod{32} = 1$) and modulo 64 for `long`.
50. **What is constant folding optimization in Java compilers?**
    * *Answer*: A compiler optimization technique where expressions containing only literal operands (e.g., `int x = 2 * 300;`) are evaluated at compile-time and replaced directly with their results (e.g., `int x = 600;`) in the bytecode.

