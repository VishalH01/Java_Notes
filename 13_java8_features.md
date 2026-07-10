# ⚡ Topic 13: Super Power Upgrades (Java 8 to 21)

Every few years, Java gets updates that give it new superpowers! Today we will learn about the biggest updates: **Java 8 (Lambdas and Streams)** and modern features up to **Java 21**.

---

## 🏠 The Big Picture & Real-Life Example

### 🍣 The Sushi Conveyor Belt (Streams API)
Imagine you are at a sushi restaurant with a conveyor belt.
1. A stream of plates moves past you (**Stream**).
2. **Filter**: You tell a helper to remove all plates that aren't Salmon sushi.
3. **Map**: You tell another helper to squeeze lemon juice on every remaining plate.
4. **Collect**: Finally, you stack the plates in a box to eat them (**Terminal Operation**).

Instead of writing complex loops with if-statements to check every plate manually, the conveyor belt does it for you in one clean line!

---

## 🔬 Let's Look Closer: Java 8 Features

### 1. Lambda Expressions (The Arrow Shortcut 🏹)
Instead of writing a full block of code to create a single action, you can use the lambda arrow `(parameters) -> { body }`.
* *Old Way*: Building a whole robot helper just to press a button.
* *New Way*: Just writing: `() -> pressButton()`.

### 2. Functional Interfaces (One-Button Machines)
An interface with exactly **one abstract method**. Java provides 4 classic ones:
* **`Predicate<T>`**: Checks a condition. Returns `true` or `false`. (The security guard checking tickets: `ticket -> isValid`).
* **`Consumer<T>`**: Eats an input, returns nothing. (The trash can: `trash -> throwAway(trash)`).
* **`Supplier<T>`**: Gives an output, takes no input. (The bubblegum machine: `() -> getGum()`).
* **`Function<T, R>`**: Takes one thing, turns it into another. (The magician's hat: `rabbit -> dove`).

### 3. The `Optional` Box (No More Missing Toys! 🎁)
If a method might return `null` (nothing), it can cause a `NullPointerException` (Java crashes when trying to play with an empty toy). 
An **`Optional<T>`** is a gift box. You check if there is a toy inside using `.isPresent()` or `.orElse()` before opening it!

---

## 🔬 Modern Upgrades (Java 9 to 21)

* **`var` (Java 10)**: Lets Java guess the type of variable automatically so you don't have to write it out!
  * *Example*: `var message = "Hello";` (Java automatically knows it's a `String`).
* **Text Blocks (Java 15)**: Write multi-line strings easily using triple quotes `"""` without messy `\n` tags!
* **Records (Java 16)**: A super quick way to create data classes. It automatically creates constructors, getters, `toString()`, and `equals()` for you!
* **Sealed Classes (Java 17)**: A parent class that controls exactly which child classes can inherit from it. It's like saying: *"Only Bob and Alice can be my children!"*

---

## 📖 Key Definitions

* **Lambda Expression**: An anonymous function (a method without a name) that can be passed around as a parameter.
* **Functional Interface**: An interface that contains exactly one abstract method, which can be implemented using a lambda.
* **Stream API**: A tool introduced in Java 8 used to process collections of elements step-by-step in a declarative way.
* **Optional**: A container class that may or may not hold a value, used to prevent NullPointerExceptions.
* **Local Variable Type Inference (`var`)**: A feature that lets the compiler guess the type of a local variable based on its value.
* **Record**: A read-only class introduced in Java 16 designed to act as an immutable data carrier without boilerplate code.
* **Sealed Class**: A class or interface that controls exactly which other classes are allowed to extend or implement it.

---

## 💻 Code Sandbox: Modern Conveyor Belt

Copy, play, and run this code:

```java
import java.util.*;
import java.util.stream.Collectors;

// --- 1. RECORD (Java 16+) ---
// A clean data carrier class. Java builds constructor, getters, toString for you!
record Toy(String name, int price) {}

public class ModernJavaDemo {
    public static void main(String[] args) {
        List<Toy> toyBox = Arrays.asList(
            new Toy("Robot", 30),
            new Toy("Teddy Bear", 15),
            new Toy("Lego Set", 50),
            new Toy("Toy Car", 10)
        );

        // --- 2. STREAM API (The Conveyor Belt) ---
        // Goal: Find toys cheaper than $40, sort them by price, and get their names.
        List<String> affordableToyNames = toyBox.stream()
            .filter(t -> t.price() < 40) // 1. Filter: price < 40
            .sorted(Comparator.comparingInt(Toy::price)) // 2. Sort by price
            .map(Toy::name) // 3. Map: Turn Toy object into just String name
            .collect(Collectors.toList()); // 4. Collect into a list!

        System.out.println("Affordable Sorted Toys: " + affordableToyNames);

        // --- 3. OPTIONAL BOX ---
        Optional<String> optionalToy = Optional.ofNullable(null);
        // Safely provide a default value if the box is empty!
        String toyName = optionalToy.orElse("No toy selected"); 
        System.out.println("Selected Toy: " + toyName);

        // --- 4. TEXT BLOCKS (Java 15+) ---
        String multilineText = """
                {
                   "name": "Vishal",
                   "age": 5,
                   "hobby": "Coding"
                }
                """;
        System.out.println("\nJSON Text Block:\n" + multilineText);

        // --- 5. VAR TYPE INFERENCE (Java 10+) ---
        var number = 100; // Java guesses 'int'
        var list = new ArrayList<String>(); // Java guesses 'ArrayList<String>'
        System.out.println("Var Number: " + number);
    }
}
```

---

> [!IMPORTANT]
> * Streams **do not change the original collection**! They only create a new, modified view of the data.
> * Streams are **lazy**. If you don't call a **Terminal Operation** (like `collect()`, `forEach()`, or `count()`) at the end, the stream will not run at all!
> * **Records** are immutable (their fields are final and cannot be changed after creation).

---

## ❓ Interview Questions (Q1 - Q50)

### 🟢 Basic Questions (Q1 - Q20)
1. **What is a Lambda Expression in Java?**
   * *Answer*: An anonymous function (without a name, return type, or class) that allows treating code as a method argument.
2. **What is a Functional Interface?**
   * *Answer*: An interface that contains exactly one abstract method (can contain multiple default or static methods).
3. **What is the purpose of the `@FunctionalInterface` annotation?**
   * *Answer*: It is an informative annotation that tells the compiler to check that the interface indeed has exactly one abstract method, preventing compilation if not.
4. **What are default methods in interfaces?**
   * *Answer*: Methods with implementations introduced in Java 8, marked with the `default` keyword, which implementing classes can use or override.
5. **What is the Stream API in Java?**
   * *Answer*: A feature introduced in Java 8 that allows processing sequences of elements (like collections) declaratively using functional operations.
6. **How do you obtain a stream from a List?**
   * *Answer*: By calling the `.stream()` method on the list.
7. **What is a Method Reference?**
   * *Answer*: A shorthand notation for writing lambdas that call an existing method, using double colons (e.g., `System.out::println`).
8. **What does the `Optional` class do?**
   * *Answer*: A wrapper class introduced in Java 8 that holds a value or `null`, designed to explicitly represent optional return values and prevent `NullPointerException`s.
9. **How does the `filter()` stream operation work?**
   * *Answer*: It is an intermediate operation that filters elements based on a given predicate (condition) returning a boolean.
10. **How does the `map()` stream operation work?**
    * *Answer*: It is an intermediate operation that transforms each element in the stream to another value or type using a mapping function.
11. **What is local variable type inference (`var`)?**
    * *Answer*: A Java 10 feature that allows omitting the explicit type in local variable declarations, letting the compiler infer the type based on the initialization value.
12. **What is a Text Block?**
    * *Answer*: A Java 15 feature that allows writing multi-line string literals enclosed in triple double-quotes `"""` without escaping newlines.
13. **What is a Record in Java?**
    * *Answer*: A special class type introduced in Java 16 designed to act as an immutable data carrier, automatically generating constructor, getters, `equals()`, `hashCode()`, and `toString()`.
14. **What are Sealed Classes?**
    * *Answer*: Classes introduced in Java 17 that restrict which other classes are permitted to extend them using the `permits` keyword.
15. **Can a Record class extend another class?**
    * *Answer*: No, all records implicitly extend `java.lang.Record` and Java does not support multiple class inheritance.
16. **How do you check if an `Optional` box has a value?**
    * *Answer*: By calling `.isPresent()` (or `.isEmpty()` since Java 11).
17. **What is the difference between `map()` and `flatMap()` in Streams?**
    * *Answer*: `map()` transforms elements one-to-one; `flatMap()` transforms each element into a stream and flattens multiple nested streams into a single stream.
18. **Name the four main functional interfaces in the `java.util.function` package.**
    * *Answer*: `Predicate`, `Consumer`, `Supplier`, and `Function`.
19. **What does the `Supplier` interface represent?**
    * *Answer*: A functional interface whose method `get()` takes no arguments and returns a value (lazy generator).
20. **What does the `Consumer` interface represent?**
    * *Answer*: A functional interface whose method `accept(T)` takes one argument and returns no result (has side effects).

### 🟡 Intermediate Questions (Q21 - Q40)
21. **What is the difference between Intermediate and Terminal operations in Streams?**
   * *Answer*: Intermediate operations (like `filter`, `map`) return a new Stream and are lazy (do not execute until a terminal operation is called). Terminal operations (like `collect`, `forEach`, `count`) process the stream, return a non-stream result, and close the stream.
22. **What does it mean that Streams are "lazy"?**
   * *Answer*: Intermediate operations do not perform any processing on the source elements. They are only queued up. The actual evaluation of elements occurs only when a terminal operation is invoked.
23. **What is the difference between `orElse()` and `orElseGet()` in `Optional`?**
   * *Answer*: 
     * `orElse(defaultValue)` evaluates the default value parameter eagerly, even if the Optional is not empty.
     * `orElseGet(Supplier)` evaluates the default value lazily (only if the Optional is empty) by executing the provided Supplier.
24. **How do you collect stream elements into a List?**
   * *Answer*: By calling `.collect(Collectors.toList())` as a terminal operation (or `.toList()` since Java 16).
25. **Can you modify the fields of a Record class after instantiation?**
   * *Answer*: No, all fields in a record class are implicitly `public final` and cannot be reassigned (records are immutable).
26. **What is the difference between `var` and dynamically typed languages like JavaScript's `let`?**
   * *Answer*: Java is still statically typed. `var` is compiled to the specific inferred type at compile-time. Once compiled, the variable type cannot change (e.g., you cannot assign a String to a `var` holding an integer).
27. **What is Pattern Matching for `instanceof`?**
   * *Answer*: A feature introduced in Java 16 that allows checking a type and casting it to a binding variable in a single statement (e.g., `if (obj instanceof String s)` where `s` is available inside the block without explicit casting).
28. **How does the arrow `->` syntax in modern switch expressions improve safety over traditional switch statements?**
   * *Answer*: It prevents accidental fall-through because only the statement on the right of the matching arrow is executed, eliminating the need for `break` statements.
29. **What is a method reference of type `ContainingClass::methodName`?**
   * *Answer*: It refers to an instance method of an arbitrary object of a particular type (e.g., `String::toLowerCase` is equivalent to `(String s) -> s.toLowerCase()`).
30. **What is the difference between `Optional.of()` and `Optional.ofNullable()`?**
   * *Answer*: `Optional.of(value)` throws a `NullPointerException` if the value is null; `Optional.ofNullable(value)` returns an empty Optional if the value is null.
31. **Can a class inherit from a sealed class if it is not listed in the `permits` clause?**
   * *Answer*: No, the compiler will reject the inheritance structure with a compilation error.
32. **What are the three modifiers permitted on subclasses extending a Sealed class?**
   * *Answer*: Subclasses must be declared as either `final` (cannot be extended), `sealed` (extends and declares its own permits), or `non-sealed` (open for inheritance by anyone).
33. **Can an interface be sealed?**
   * *Answer*: Yes, interfaces can be sealed to restrict which classes or interfaces can implement or extend them.
34. **What is a "compact constructor" in a Record class?**
   * *Answer*: A constructor declaration without parameter lists `public MyRecord { ... }` used to validate or normalize arguments before assigning them to record fields.
35. **What does the `distinct()` intermediate stream operation do?**
   * *Answer*: It filters out duplicate elements in the stream based on their `.equals()` method evaluation.
36. **What is a Parallel Stream? How do you create one?**
   * *Answer*: A stream that partitions elements across multiple threads to process them concurrently. Created by calling `.parallelStream()` or `.parallel()` on an existing stream.
37. **What is the difference between `peek()` and `forEach()` in Streams?**
   * *Answer*: `peek()` is an intermediate operation designed for debugging (takes a Consumer and returns the stream); `forEach()` is a terminal operation that consumes the elements, closing the stream.
38. **What does the `reduce()` terminal operation do?**
   * *Answer*: It combines the elements of a stream into a single summary value using an associative accumulation function (e.g. summing numbers).
39. **Why is it discouraged to return an `Optional` from a getter method?**
   * *Answer*: Optional was designed strictly as a method return type for API design to represent missing returns. Storing Optionals as fields or returning them from getters breaks serialization and increases heap memory overhead.
40. **How does the JVM implement lambda expressions internally?**
    * *Answer*: It uses the `invokedynamic` instruction (introduced in Java 7) to dynamically generate and bind a private class representing the lambda at runtime, avoiding the compile-time overhead of anonymous inner classes.

### 🔴 Advanced Questions (Q41 - Q50)
41. **Explain Loop Fusion and Lazy Evaluation optimizations in Streams.**
   * *Answer*: Since intermediate operations are lazy, the Stream pipeline evaluates elements in a single pass. For example, `filter().map()` does not iterate the collection twice; it merges the operations (fuses them) and processes each element through the filter and map sequentially in a single loop.
42. **What is the performance overhead of Streams compared to traditional `for` loops?**
   * *Answer*: Streams introduce overhead due to pipeline object allocations, lambda conversions, and indirect method dispatch. For simple arithmetic arrays, traditional loops are significantly faster; for complex collections or database processing, the readability benefits of streams outweigh the minor performance cost.
43. **What thread pool do parallel streams use by default? What is the risk?**
   * *Answer*: They share the common **ForkJoinPool.commonPool()**. The risk is thread starvation: if a parallel stream executes a blocking call (like an HTTP request), it blocks threads in the shared pool, stalling all other parallel streams running in the application.
44. **What are short-circuiting terminal operations in Streams? Give examples.**
   * *Answer*: Operations that do not require evaluating the entire stream to return a result (e.g., `findFirst()`, `findAny()`, `anyMatch()`, `allMatch()`). They terminate immediately once the result is determined.
45. **What is the difference between Virtual Threads (Java 21) and Platform Threads?**
   * *Answer*: Platform threads are thin wrappers around OS threads (heavyweight, blocked by blocking I/O). Virtual threads (Project Loom) are lightweight threads managed entirely by the JVM. Millions of virtual threads can run on a small number of carrier platform threads, automatically yielding during I/O blocking.
46. **What is Pattern Matching for Switch in Java 21?**
   * *Answer*: A feature that allows matching the switch variable type directly (e.g., `case Integer i -> ...`), applying conditional guards (`when`), and handling nulls safely.
47. **What is a Record Pattern in Java 21?**
   * *Answer*: A feature that allows deconstructing record variables directly inside pattern matching checks (e.g., `if (obj instanceof Toy(String name, int price))` to extract record variables directly without using getters).
48. **Explain the rules of exhaustiveness for pattern matching in switch.**
   * *Answer*: When switch is used as an expression with type patterns (or sealed classes), the compiler verifies that all possible subtypes are covered. If the compiler cannot prove coverage, it requires a `default` case to compile.
49. **How do you configure a custom thread pool for running a parallel stream?**
   * *Answer*: By submitting the parallel stream task inside a custom `ForkJoinPool` instance: `customPool.submit(() -> stream.parallel().forEach(...)).get();`.
50. **What is the difference between stateless and stateful intermediate operations in Streams?**
    * *Answer*: Stateless operations (like `filter`, `map`) process elements independently without memory of other elements. Stateful operations (like `sorted`, `distinct`, `limit`) require knowledge of all elements in the stream before producing results, requiring internal buffering and degrading parallel performance.

