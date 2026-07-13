# 📦 Topic 02: Variables, Scope & Hoisting

Welcome back, scope manager! In this chapter, we will learn about **Variables, Data Types, Scope, and Hoisting**. How JavaScript stores variables depends heavily on how you declare them. We will compare `var`, `let`, and `const`, look at the data types available in JS, and understand why accessing variables before declaring them behaves differently depending on the keyword you use.

---

## 🏠 The Big Picture & Real-Life Example

### 🏷️ The Toy Box Label Tags & Locked Rooms
Imagine you are storing toys inside a nursery:
* **`var` (The Sticky Note Tag - Global Scope)**: You write a label called `car` and stick it anywhere. Sticky notes are loose; if you place it inside a playhouse tent (**Block Scope**), the sticker is still readable from outside the tent. If you ask for the toy before placing it, the assistant says: *"I heard of the tag car, but I don't know what it is yet"* (**Hoisting -> `undefined`**).
* **`let` (The Lockable Drawer - Block Scope)**: You put the toy in a dedicated drawer inside a specific room (**Block Scope**). The drawer is locked until you walk in and declare it. If you try to open the drawer *before* you reach the declaration line, the assistant yells: *"You cannot touch this area yet!"* (**Temporal Dead Zone**).
* **`const` (The Superglue Tag)**: You glue the label to a plastic block. You cannot peel it off and stick it to a toy train (**Reassignment Blocked**). However, if the block has a drawer, you *can* still put different Lego pieces inside it (**Mutating Object Keys is allowed**).

---

## 🔬 Let's Look Closer

### 1. var vs. let vs. const

| Property | `var` | `let` | `const` |
|---|---|---|---|
| **Scope** | Function / Global | Block Scope `{}` | Block Scope `{}` |
| **Hoisting** | Hoisted, initialized as `undefined` | Hoisted, uninitialized (TDZ) | Hoisted, uninitialized (TDZ) |
| **Reassignable** | Yes | Yes | No |
| **Redeclarable** | Yes | No | No |

### 2. Hoisting & The Temporal Dead Zone (TDZ)
During compilation, JavaScript scans the code and registers variable declarations.
* Variables declared with `var` are allocated memory and automatically initialized to `undefined`.
* Variables declared with `let` and `const` are registered in memory but left **uninitialized**. The period between entering their scope and the declaration line is the **Temporal Dead Zone (TDZ)**. Accessing them inside this window throws a `ReferenceError`.

### 3. JavaScript Data Types
JavaScript divides values into:
1. **Primitive Types** (Stored by value in stack memory): `string`, `number`, `boolean`, `null`, `undefined`, `symbol`, `bigint`.
2. **Reference Types** (Stored by pointer in heap memory): `object`, `array`, `function`.

---

## 💻 Code Sandbox

Let's test scoping and hoisting behavior in a sandbox.

```javascript
// 1. Hoisting demonstration
console.log("var value:", myVar); // Output: undefined (hoisted!)
var myVar = 10;

try {
    console.log("let value:", myLet); // Throws ReferenceError (TDZ block!)
} catch (e) {
    console.log("Let Error caught:", e.message);
}
let myLet = 20;

// 2. Scoping demonstration
{
    var blockVar = "I leak out of blocks!";
    let blockLet = "I am locked inside this block!";
}
console.log(blockVar); // Output: "I leak out of blocks!"
try {
    console.log(blockLet); // Throws ReferenceError!
} catch (e) {
    console.log("Block Let Error caught:", e.message);
}

// 3. Const behavior (Reassignment vs Mutation)
const user = { name: "Alice" };
user.name = "Bob"; // Mutation is ALLOWED!
console.log("User mutated name:", user.name);

try {
    user = { name: "Charlie" }; // Throws TypeError (Reassignment BLOCKED!)
} catch (e) {
    console.log("Const Reassign Error caught:", e.message);
}
```

---

## 🧠 Points to Remember

* **`const` prevents reassignment, not mutation**. If you declare an array with `const`, you can still do `arr.push(5)`. To make an object completely immutable, call `Object.freeze(obj)`.
* **Avoid `var`**. It has no block scope, which leads to bugs where loop variables leak and overwrite outer variables.
* **`null` vs `undefined`**:
  * `undefined`: A variable has been declared but has not been assigned a value yet.
  * `null`: An intentional assignment representing the complete absence of value.

---

## 📖 Key Definitions

* **Hoisting**: A JavaScript mechanism where variable and function declarations are moved to the top of their containing scope during compilation.
* **Scope**: The context or region of code in which variables are visible and can be accessed (global, function, or block scope).
* **Temporal Dead Zone (TDZ)**: The period between variable entry into scope and its actual initialization, during which accessing it throws a ReferenceError.
* **Primitive Types**: Immutable, basic data types stored by value in JavaScript (string, number, boolean, null, undefined, symbol, bigint).
* **Reference Types**: Complex data structures stored by memory reference pointer (objects, arrays, functions).

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is variable hoisting in JavaScript?**
   * *Answer*: A compilation phase behavior where variable and function declarations are moved to the top of their containing scope before execution.
2. **What is the difference between `var`, `let`, and `const`?**
   * *Answer*: `var` is function-scoped and hoisted with `undefined`. `let` and `const` are block-scoped and hoisted without initialization (TDZ). `const` prevents value reassignment.
3. **What is block scope?**
   * *Answer*: A scope boundary defined by curly braces `{}` (such as inside if statements or loops). Variables declared with `let`/`const` inside a block cannot be accessed outside.
4. **What is the Temporal Dead Zone (TDZ)?**
   * *Answer*: The state region between entering a scope and the variable declaration line, during which accessing a `let` or `const` variable throws a ReferenceError.
5. **What are the primitive data types in JavaScript?**
   * *Answer*: There are 7 primitive types: `string`, `number`, `boolean`, `null`, `undefined`, `symbol`, and `bigint`.
6. **What is the difference between null and undefined?**
   * *Answer*: `undefined` represents a declared variable that lacks value. `null` is an assigned value representing the intentional absence of any object value.
7. **Can you reassign a value to a `const` variable?**
   * *Answer*: No, `const` creates a read-only reference, so reassigning a new value throws a TypeError.
8. **Can you mutate an object declared with `const`?**
   * *Answer*: Yes, `const` only prevents changing the memory pointer reference. You can modify, add, or delete object keys.
9. **What does `typeof null` return, and why?**
    * *Answer*: It returns `"object"`. This is a legacy bug in JavaScript's original implementation that was left unchanged to prevent breaking existing web code.
10. **How do you make an object completely immutable?**
    * *Answer*: By calling the built-in utility method: `Object.freeze(object)`.
11. **What is function scope?**
    * *Answer*: The scope boundary created inside a function. Variables declared inside a function (including `var`) cannot be accessed outside.
12. **What happens if you assign a value to an undeclared variable (e.g., `x = 5`)?**
    * *Answer*: In non-strict mode, JS creates a global property `x` on the window object. In strict mode, it throws a ReferenceError.
13. **Is JavaScript pass-by-value or pass-by-reference?**
    * *Answer*: JavaScript is always pass-by-value. However, for reference types, the value passed is the memory reference pointer.
14. **What does `typeof undefined` return?**
    * *Answer*: It returns the string `"undefined"`.
15. **What is the difference between stack and heap memory?**
    * *Answer*: Stack stores static data, primitive values, and pointers in a LIFO order. Heap stores dynamic, large complex data structures (objects, arrays).
16. **What is a global variable?**
    * *Answer*: A variable declared outside of any function or block, making it accessible from anywhere in the script.
17. **What is the scope chain?**
    * *Answer*: The lookup path JavaScript uses to resolve variable values, scanning upward from the local scope to parent scopes and finally the global scope.
18. **Can you redeclare a `let` variable in the same scope?**
    * *Answer*: No, redeclaring a variable using `let` or `const` in the same scope throws a SyntaxError.
19. **What is the `bigint` data type used for?**
    * *Answer*: To store and perform operations on integer numbers larger than JavaScript's standard limit (`Number.MAX_SAFE_INTEGER`).
20. **What does the `Symbol` data type do?**
    * *Answer*: It generates a guaranteed unique, immutable identifier value, primarily used to create private object keys.

### 🟡 Intermediate Questions (21-40)

21. **Explain the execution contexts phases: Creation phase vs Execution phase.**
    * *Answer*: In the **Creation phase**, the compiler registers variables and functions in memory (hoisting). In the **Execution phase**, the engine steps line-by-line, assigning values and running logic.
22. **Why does `var` support redeclaration without throwing errors?**
    * *Answer*: Because of the compiler's creation pass. If a `var` variable is redeclared, the compiler registers it once, silently ignoring subsequent `var` declarations and converting them to simple assignments.
23. **What is the difference between `Object.freeze()` and `Object.seal()`?**
    * *Answer*: `freeze()` blocks additions, deletions, and updates to properties. `seal()` blocks additions and deletions, but allows updates to existing keys.
24. **Explain how hoisting affects function declarations vs function expressions.**
    * *Answer*: Function declarations are fully hoisted (the entire function body is loaded in memory). Function expressions (e.g. `const fn = () => {}`) are bound to variables, so they follow variable hoisting rules (remain uninitialized in TDZ).
25. **Why do variables declared with `let` and `const` reside in the Temporal Dead Zone?**
    * *Answer*: To enforce better coding practices. It prevents developers from reading variables before they are initialized, making code easier to read.
26. **Explain the memory leak risk of using global variables extensively.**
    * *Answer*: Global variables are attached to the `window` object and are never garbage collected as long as the page is open, wasting RAM.
27. **What is the difference between shallow freeze and deep freeze?**
    * *Answer*: `Object.freeze()` is shallow (it only freezes root keys). If the object contains nested objects, those nested objects can still be mutated unless you recursively freeze them (deep freeze).
28. **How does JavaScript handle passing objects to functions?**
    * *Answer*: The memory pointer reference is copied and passed by value. Modifying keys affects the original object. Reassigning the parameter variable inside the function does not affect the original reference.
29. **What does `Object.is()` do and how does it differ from `===`?**
    * *Answer*: It checks value equality. Unlike `===`, `Object.is(NaN, NaN)` evaluates to `true`, and `Object.is(+0, -0)` evaluates to `false`.
30. **Explain what the lexical environment represents.**
    * *Answer*: An internal engine structure that maps identifiers to specific variables and functions based on where the code was written in the text editor.
31. **What is the difference between function-scoped variables and block-scoped variables in loops?**
    * *Answer*: If you use `var i` in a loop, only one `i` variable exists, leaking its final value. If you use `let i`, a brand-new `i` binding is created for every loop iteration.
32. **Explain the behavior of variables initialized without var, let, or const inside functions.**
    * *Answer*: The engine scans local scopes. Finding no variable, it travels up to the global scope. If it doesn't exist, it creates the variable globally, attaching it to `window`.
33. **What is shadowing in JavaScript scope?**
    * *Answer*: A scenario where a variable declared inside a local scope shares the same name as a variable in the outer scope, temporarily masking access to the outer variable.
34. **Does hoisting happen inside nested scopes?**
    * *Answer*: Yes, hoisting occurs independently at the top of every scope block (global, function, or block `{}`) whenever compilation begins.
35. **What is a wrapper object for primitives?**
    * *Answer*: When you call a method on a primitive (e.g. `"hello".toUpperCase()`), JS temporarily wraps the string in a `String` object, calls the method, and discards the object.
36. **Explain how `typeof` handles functions compared to arrays.**
    * *Answer*: `typeof []` returns `"object"`. `typeof (() => {})` returns `"function"`. Even though functions are objects under the hood, the engine reports them as functions because they implement the `[[Call]]` internal method.
37. **How do you copy a nested object reference safely?**
    * *Answer*: By using deep cloning utilities: `structuredClone(obj)`, parsing JSON string equivalents (`JSON.parse(JSON.stringify(obj))`), or using libraries like Lodash.
38. **Explain the behavior of the `const` keyword in loop declarations.**
    * *Answer*: In `for (const i = 0; i < 5; i++)`, it throws an error after iteration 1 because it tries to modify `i`. In `for (const key in obj)`, it works because a new `key` variable is created for each loop loop.
39. **What is the scope of parameters passed to a function?**
    * *Answer*: Parameters are treated as local variables initialized with the passed arguments, scoped strictly to the function body.
40. **How does JavaScript handle variables declared on the window object in HTML?**
    * *Answer*: Any variable declared with `var` or initialized without keywords in the global scope becomes a property of the global `window` object, while `let` and `const` globals do not.

### 🔴 Advanced Questions (41-50)

41. **Explain the internal memory layout differences between primitives and objects in the V8 engine.**
    * *Answer*: Primitives are immutable and stored directly in the Stack memory for fast access (numbers, booleans, small strings). Objects are stored in the Heap memory. The Stack holds only the 32-bit or 64-bit reference address pointer pointing to the heap memory slot.
42. **Why does JavaScript's execution context initialize block scopes differently than function scopes during the compile pass?**
    * *Answer*: Function scopes have dedicated environment records created at compile time. Block scopes (`let`/`const` inside blocks) are evaluated dynamically. V8 creates temporary declarative environment records inside the running execution context to handle block lifetimes.
43. **Explain the mechanics of the Temporal Dead Zone (TDZ) at the bytecode level.**
    * *Answer*: When a scope is entered, the engine assigns a special internal token `uninitialized` to the `let`/`const` identifiers. If a bytecode instruction tries to read an identifier mapped to `uninitialized` before the initialization expression runs, it triggers a ReferenceError.
44. **What is the difference between a Lexical Environment and a Variable Environment inside an Execution Context?**
    * *Answer*: Inside an execution context, the **Variable Environment** handles `var` declarations. The **Lexical Environment** handles block-scoped `let`/`const` and function declarations, updating as blocks `{}` are entered and exited.
45. **How does V8's optimization compiler optimize access to primitive variables compared to object key lookups?**
    * *Answer*: Primitives are accessed via fixed stack offsets (extremely fast). Object keys require looking up the hidden class (Map), calculating the offset of the key, and fetching the value from the heap, which has compilation overhead.
46. **Explain why `Object.freeze()` is shallow and how to implement a deep freeze algorithm recursively.**
    * *Answer*: `freeze()` only applies to the root references. To deep freeze: get all property keys, check if `typeof obj[key] === 'object'`, and recursively call `Object.freeze(obj[key])` on them.
47. **What is the difference in memory retention between standard variables and Closures?**
    * *Answer*: Standard local variables are popped off the stack when the function exits. Closures store referenced variables in a special heap-allocated scope object, preventing garbage collection as long as the closure function exists.
48. **How does JS garbage collection handle cyclic references (e.g. Object A references B, and B references A)?**
    * *Answer*: Using **Reachability Analysis**. Older browsers used reference counting (which failed on cyclic loops). Modern browsers trace roots: if the cycle cannot be reached from the global object, it is collected anyway.
49. **Why does referencing `this` behave differently inside `var` scopes compared to `let` scopes at the global level?**
    * *Answer*: Because `var` registers properties on the global object wrapper. Calling `this.x` finds `x` if declared with `var`. `let` variables reside in a separate declarative environment record, so they cannot be accessed via `this.x` globally.
50. **What is the performance implication of utilizing the `with` statement in JavaScript scopes?**
    * *Answer*: The `with` statement extends the scope chain dynamically at runtime. This prevents the compiler from optimizing variable lookups during the compilation pass, forcing slow runtime checks, which is why it is banned in strict mode.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 01: Introduction to JavaScript & DOM Basics](01_intro_dom_basics.md)
* **Next Chapter**: [👉 Topic 03: Functions & Execution Context](03_functions_execution_context.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
