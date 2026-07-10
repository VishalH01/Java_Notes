# 📦 Topic 02: Toy Boxes (Variables & Data Types)

To write programs, our computer needs to remember information. In Java, we do this using **Variables** and **Data Types**.

---

## 🏠 The Big Picture & Real-Life Example

### 🏷️ The Labeled Storage Box
Imagine you are cleaning your bedroom. You have a bunch of plastic boxes:
* One box is tiny and labeled **"Coins"** 🪙. You can only put coins in it.
* One box is huge and labeled **"Stuffed Animals"** 🧸.
* One box is labeled **"Is Raining?"** and only has room for a card that says either **Yes** or **No** 🌧️.

If you try to put a giant teddy bear into the tiny coin box, it won't fit! And if you try to put a shoe in a box labeled "Books," you will get confused later. 

In Java, **Variables** are these plastic boxes. **Data Types** are the labels that tell Java what kind of toys can go inside the boxes!

---

## 🔬 Let's Look Closer: Primitive Data Types

Java is very strict. You *must* tell it exactly what kind of box you want to make before you put anything inside it. 

Java has **8 basic types of boxes** (called **Primitive Data Types**):

### 🔢 1. The Integer Family (Whole Numbers only - no decimals!)
* **`byte`**: A tiny box. Fits numbers from `-128` to `127` (e.g., your age).
* **`short`**: A small box. Fits numbers from `-32,768` to `32,767` (e.g., the number of toys in a store).
* **`int`**: The **standard** box. Fits numbers up to 2 billion (e.g., your phone number without the country code). *Usually, we just use this!*
* **`long`**: A giant box. Fits massive numbers (e.g., the number of stars in the galaxy). You must put an `L` at the end of the number so Java knows it's a `long`.

### 🍕 2. The Decimal Family (Numbers with fractional parts!)
* **`float`**: A medium box for decimals. You must put an `f` at the end of the number (e.g., `3.14f`).
* **`double`**: The **standard** big box for decimal numbers (e.g., `99.99`). It's twice as precise as `float`!

### 🎭 3. The Character & Yes/No Family
* **`char`**: A box that holds exactly **one** letter, number, or symbol. Must be wrapped in single quotes, like `'A'` or `'7'`.
* **`boolean`**: A box that holds only two things: `true` (Yes) or `false` (No).

---

## 📦 Reference Data Types (The Big Bags)

While primitive types store simple values (like the number `5`), **Reference types** point to more complex things (like a whole robot or a book). 

The most common reference type is **`String`**. It holds text (a chain of characters). We write it inside double quotes, like `"Hello!"`.

---

## 🔄 Type Casting (Moving toys between boxes)

Sometimes you want to take a toy out of one box and put it into another. This is called **Type Casting**.

### 1. Widening Casting (Automatic 🟢)
Taking a toy from a small box and putting it into a big box. Java does this automatically because there is plenty of room!
* *Example*: Putting a `byte` toy into an `int` box.

### 2. Narrowing Casting (Manual 🔴)
Trying to put a toy from a big box into a smaller box. You have to do this manually by writing the small type in parentheses `(type)`. 
> [!WARNING]
> This is like using a funnel or a saw to make a big toy fit. You might cut off or lose some of the toy (data loss)!
* *Example*: Casting a double `5.75` to an integer makes it `5`. You lose the `.75`!

---

## 🛑 Constants: The Super-Glued Lid (`final`)

If you build a box and never want anyone to change the toy inside it, use the word **`final`**. It's like gluing the lid shut!
```java
final int BIRTH_YEAR = 2021; // This box can NEVER be changed!
```

---

## 📖 Key Definitions

* **Variable**: A designated storage location in the computer's memory mapped to a user-defined name (identifier) that holds a value during program execution.
* **Primitive Data Types**: The eight basic built-in data types in Java (`byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`) that represent raw, predefined values directly in memory rather than complex object references.
* **Reference Data Types**: Types that store the memory address of an object (like `String` or array references) rather than the actual values themselves.
* **Type Casting**: The process of converting a variable from one data type to another, either implicitly (widening) or explicitly (narrowing).
* **Constant**: A variable declared with the `final` keyword whose value cannot be reassigned or modified once initialized.

---

## 💻 Code Sandbox: Toy Boxes in Action

Copy and run this code to see how different boxes work:

```java
public class VariablesDemo {
    public static void main(String[] args) {
        // 1. Creating (Declaring) and filling (Initializing) our boxes!
        int age = 5; // An 'int' box named 'age' holding the number 5
        double price = 10.99; // A 'double' box for decimal numbers
        char grade = 'A'; // A 'char' box for a single character
        boolean isHappy = true; // A 'boolean' box for true/false

        System.out.println("Age: " + age);
        System.out.println("Price: " + price);
        System.out.println("Grade: " + grade);
        System.out.println("Is Happy? " + isHappy);

        // 2. Widening Casting (Automatic)
        int smallNumber = 100;
        double bigNumber = smallNumber; // Automatically turns 100 into 100.0
        System.out.println("Automatic casting: " + bigNumber);

        // 3. Narrowing Casting (Manual)
        double originalDecimal = 9.99;
        int cutDecimal = (int) originalDecimal; // Manually chop off the .99!
        System.out.println("Manual casting (9.99 -> int): " + cutDecimal); // Prints 9
        
        // 4. Using Constants
        final int MAX_LIVES = 3;
        // MAX_LIVES = 4; // ❌ UNCOMMENTING THIS WILL CAUSE AN ERROR! Java won't let you open a glued box.
        System.out.println("Max Lives: " + MAX_LIVES);
    }
}
```

---

## 🧠 Points to Remember

> [!IMPORTANT]
> * Variables must have a type (like `int`, `double`, `boolean`) and a unique name.
> * Always use **CamelCase** for variable names (e.g., `myFavoriteToy`), starting with a lowercase letter.
> * Put `L` after long numbers (e.g., `12345678900L`) and `f` after float numbers (e.g., `5.5f`).
> * `final` variables must be capitalized (e.g., `MAX_SPEED`) by convention.

---

## ❓ Interview Questions (Q1 - Q50)

### 🟢 Basic Questions (Q1 - Q20)
1. **What is a variable?**
   * *Answer*: A named storage location in the computer's memory that holds a value during program execution.
2. **What are the eight primitive data types in Java?**
   * *Answer*: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, and `boolean`.
3. **What is the size of `byte`, `short`, `int`, and `long` in bytes?**
   * *Answer*: `byte`: 1 byte, `short`: 2 bytes, `int`: 4 bytes, `long`: 8 bytes.
4. **What is the default value of numeric primitive types when declared as class variables?**
   * *Answer*: `byte`, `short`, `int`: `0`; `long`: `0L`; `float`: `0.0f`; `double`: `0.0d`.
5. **What is the default value of the `boolean` primitive type?**
   * *Answer*: `false`.
6. **What is the default value of the `char` primitive type?**
   * *Answer*: The null character `\u0000` (value 0).
7. **What is the difference between Primitive and Reference types?**
   * *Answer*: Primitives hold raw values directly in memory; reference types hold the memory address (reference) of an object stored in the Heap.
8. **What is widening type casting?**
   * *Answer*: Converting a smaller primitive type to a larger primitive type (e.g., `int` to `double`). It is done automatically.
9. **What is narrowing type casting?**
   * *Answer*: Converting a larger primitive type to a smaller primitive type (e.g., `double` to `int`). It must be done explicitly using `(type)` notation.
10. **How do you declare a constant in Java?**
    * *Answer*: By using the `final` keyword (e.g., `final int LIMIT = 100;`).
11. **Can you reassign a value to a `final` variable?**
    * *Answer*: No, once initialized, the value of a `final` variable cannot be altered.
12. **Why do we need to append `L` to long literals (e.g., `long val = 5000000000L;`)?**
    * *Answer*: By default, integer literals are treated as `int` (4 bytes). Appending `L` informs the compiler that the literal is a `long` value.
13. **Why do we need to append `f` to float literals (e.g., `float fVal = 5.5f;`)?**
    * *Answer*: By default, decimal literals are treated as `double` (8 bytes). Appending `f` informs the compiler to treat it as a `float` (4 bytes).
14. **What is the size of a `boolean` variable in memory?**
    * *Answer*: The Java Virtual Machine specification does not define a precise size, but it is typically represented using 1 byte at runtime.
15. **What is a literal?**
    * *Answer*: A constant value written directly in the source code (e.g., `100`, `'A'`, `true`).
16. **How many characters can a `char` variable hold?**
    * *Answer*: Exactly one character, enclosed in single quotes.
17. **What character encoding does Java use for `char`?**
    * *Answer*: 16-bit Unicode character encoding (UTF-16).
18. **Can you cast a `boolean` to an `int` in Java?**
    * *Answer*: No, `boolean` is incompatible with numeric types in Java.
19. **What is variable declaration?**
    * *Answer*: Telling the compiler the name and data type of a variable (e.g., `int age;`).
20. **What is variable initialization?**
    * *Answer*: Assigning a starting value to a declared variable (e.g., `age = 5;`).

### 🟡 Intermediate Questions (Q21 - Q40)
21. **What is the difference between local variables, instance variables, and class variables?**
   * *Answer*: 
     * Local variables are declared inside a method and exist only during method execution.
     * Instance variables are declared in a class and belong to individual objects.
     * Class variables are declared with the `static` keyword and are shared by all objects of the class.
22. **What occurs during numeric overflow and underflow?**
   * *Answer*: When a numeric type goes beyond its maximum range, it wraps around to its minimum value (overflow). Underflow wraps around from the minimum to the maximum value.
23. **What is numeric promotion in Java?**
   * *Answer*: The process of automatically converting smaller operands to match the size of the largest operand in an expression (e.g., `byte + int` promotes the `byte` to `int` before addition).
24. **Why does compiling `byte b = 5; b = b + 1;` fail?**
   * *Answer*: The literal `1` is an `int`. Adding `b + 1` promotes the expression to an `int`. Assigning it back to a `byte` requires explicit casting: `b = (byte)(b + 1)`.
25. **Why does `byte b = 5; b += 1;` compile successfully?**
   * *Answer*: Compound assignment operators (like `+=`) automatically apply an implicit cast under the hood (equivalent to `b = (byte)(b + 1)`).
26. **What is the precision difference between `float` and `double`?**
   * *Answer*: `float` provides single-precision (approx. 7 decimal digits); `double` provides double-precision (approx. 15 decimal digits).
27. **What is the range of a `byte` variable?**
   * *Answer*: `-128` to `127` (since it is a signed 8-bit integer, calculated as $-2^7$ to $2^7-1$).
28. **How does casting a larger float to a smaller float type behave?**
   * *Answer*: It rounds the value or loses precision, and can result in `Infinity` or `NaN` (Not a Number) if the value is too large for the destination type.
29. **What happens if a local variable is used without initialization?**
   * *Answer*: The compiler will reject the code with a compilation error: *"variable x might not have been initialized."*
30. **Can instance variables be accessed without initialization?**
   * *Answer*: Yes, instance variables are automatically initialized to their default values when the object is instantiated.
31. **What is a Reference Variable?**
   * *Answer*: A variable that stores the memory address (pointer) of an object located in the heap, rather than storing the object data itself.
32. **Can you declare multiple variables in a single line?**
   * *Answer*: Yes, if they share the same data type (e.g., `int x = 1, y = 2, z = 3;`).
33. **What is the difference between `double d = 1.0;` and `double d = 1;`?**
   * *Answer*: `1.0` is a double literal; `1` is an integer literal that gets automatically cast (widened) to `1.0` double at compile time.
34. **Can we declare a variable with the name of a Java keyword?**
   * *Answer*: No, keywords (like `class`, `void`, `int`) are reserved by Java and cannot be used as identifiers.
35. **What is the default value of reference variables?**
   * *Answer*: `null` (pointing to no memory location).
36. **What is the scope of a local variable?**
   * *Answer*: It is limited to the block of code `{ ... }` in which it was declared.
37. **Can we reuse a variable name in nested blocks?**
   * *Answer*: No, you cannot declare a local variable with the same name as another local variable in the same scope or its nested scopes.
38. **What is a binary literal in Java?**
   * *Answer*: An integer literal prefixed with `0b` or `0B` (e.g., `int binary = 0b1010;` which is 10 in decimal).
39. **What is a hexadecimal literal in Java?**
   * *Answer*: An integer literal prefixed with `0x` or `0X` (e.g., `int hex = 0xFF;` which is 255 in decimal).
40. **How does underscore `_` in numeric literals help?**
    * *Answer*: It serves as a visual separator to improve code readability (e.g., `int card = 1_000_000;` is treated as `1000000` by the compiler).

### 🔴 Advanced Questions (Q41 - Q50)
41. **Explain the IEEE 754 standard for floating-point representation.**
   * *Answer*: Floating-point numbers (`float` and `double`) are represented in memory using three components: a sign bit, exponent bits, and mantissa (fraction) bits, which can result in rounding anomalies due to binary representation limits.
42. **Why is `0.1 + 0.2` not equal to `0.3` in double precision?**
   * *Answer*: Because `0.1` and `0.2` cannot be represented precisely in binary fractional format. The rounding of these values leads to a tiny precision discrepancy: `0.30000000000000004`.
43. **Where are primitive variables stored in JVM memory?**
   * *Answer*: If they are local variables, they are stored on the **Stack Frame** of their executing thread. If they are instance variables of an object, they are stored in the **Heap** as part of that object.
44. **What are the limitations of local variable type inference (`var`)?**
   * *Answer*: `var` can only be used for local variables initialized at declaration. It cannot be used for class fields, method parameters, return types, or uninitialized variables.
45. **Does the JVM allocate memory for reference variables on the stack or heap?**
   * *Answer*: The reference variable itself (holding the address) is stored on the Stack Frame, but the object it points to is allocated on the Heap.
46. **What is the difference between `final` and `effectively final` variables?**
   * *Answer*: A `final` variable is explicitly declared with the `final` keyword. An `effectively final` variable is not declared final, but its value is never modified after initialization, allowing it to be used in anonymous classes or lambdas.
47. **What is dynamic range truncation (precision loss) during cast operations?**
   * *Answer*: Converting a double to float or int chops off fractional data, and casting an int to a byte chops off the higher order bits, retaining only the lowest 8 bits.
48. **Explain what happens when casting `int a = 130;` to `byte b = (byte) a;`.**
   * *Answer*: 130 in binary is `00000000 00000000 00000000 10000010`. Truncating it to 8 bits yields `10000010`. In signed two's complement, `10000010` represents `-126`. Thus, `b` will hold the value `-126`.
49. **Can a constant expression undergo compiler optimization?**
   * *Answer*: Yes, compile-time constants (e.g., `final int a = 5; final int b = 10; int c = a + b;`) are optimized by the compiler using **constant folding**, replacing `a + b` with `15` directly in bytecode.
50. **What is the OutOfMemoryError in relation to variable allocation?**
    * *Answer*: It occurs when the JVM Heap space runs out of memory because references to objects are continuously stored in variables without being dereferenced, leaving the Garbage Collector unable to free memory.

