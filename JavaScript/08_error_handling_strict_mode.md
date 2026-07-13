# 🛡️ Topic 08: Error Handling & Strict Mode

Welcome back, safety coordinator! In this chapter, we will learn about **Error Handling and Strict Mode**. Even the best programmers write bugs or face network failures. We will learn how to write safety nets using `try-catch-finally` blocks, create custom exceptions using `throw`, and enable `"use strict"` to prevent silent bugs.

---

## 🏠 The Big Picture & Real-Life Example

### 🎪 The Trapeze Safety Net & The Strict Referee
Imagine you are managing a circus performance:
* **No Error Handling**: A trapeze artist swings through the air. If he misses his partner's hands (**Exception / Bug**), he falls straight to the concrete floor. The entire show halts, the audience runs away screaming, and the lights turn off (**Application Crash to Blank Screen**).
* **The Safety Net (`try-catch`)**: You install a thick safety net (**`try`** block). If the artist falls, the net catches him safely (**`catch`** block). The show doesn't stop; the announcer says: *"Wow! What a stunt! Let's check on the actor"* (**Handling the error**). Finally, the clean-up crew sweeps the stage no matter what (**`finally`** block).
* **The Custom Flag (`throw`)**: If the artist sees that the partner is holding a dangerous prop, he yells: *"Stop! Danger!"* and refuses to jump (**`throw new Error("Dangerous Prop!")`**).
* **Strict Mode (The Strict Referee)**: In a casual practice, players can step slightly out of bounds, wear messy clothes, or throw balls out of turn. But when the referee blows the whistle and declares **Strict Rules** (**`"use strict"`**), the game throws immediate penalty calls on every tiny mistake!

---

## 🔬 Let's Look Closer

### 1. Try-Catch-Finally
* **`try`**: Wraps the code that might throw an error (e.g. fetching network data, parsing JSON).
* **`catch`**: Executes only if an error is thrown inside the `try` block. It receives the `Error` object as an argument.
* **`finally`**: Executes always, regardless of whether an error occurred or was caught, commonly used to close file streams or reset loading flags.

### 2. Strict Mode (`"use strict"`)
Enabling strict mode changes silent errors into throw exceptions:
* Prevents creating global variables accidentally (e.g., typing `x = 5` without `let`/`const` throws an error).
* Throws errors when deleting non-deletable properties.
* Disables duplicate parameter names in functions.
* Secures `this` (evaluates to `undefined` instead of `window` in global functions).

---

## 💻 Code Sandbox

Let's write an exception-throwing JSON parser and a strict mode demonstration script.

```javascript
// --- 1. Custom Error Handling ---
function parseConfig(jsonString) {
    try {
        const config = JSON.parse(jsonString);
        
        // Custom verification
        if (!config.theme) {
            // Throwing a custom error
            throw new Error("Missing theme parameter in configuration!");
        }
        
        return config;
    } catch (error) {
        console.log("Error Caught: " + error.name);
        console.log("Error Message: " + error.message);
        
        // Return a default configuration on failure
        return { theme: "light", debug: false };
    } finally {
        console.log("Configuration parser run completed.");
    }
}

const badJson = '{ "debug": true }'; // Missing "theme" key
const activeConfig = parseConfig(badJson);
console.log("Active UI Theme:", activeConfig.theme);

// --- 2. Strict Mode demonstration ---
function testStrictMode() {
    // Declaring strict mode inside a function block
    "use strict";
    try {
        // Without strict mode, this creates a global variable.
        // With strict mode, it throws a ReferenceError.
        undeclaredVar = 100; 
    } catch (e) {
        console.log("Strict Mode Error caught:", e.message);
    }
}

testStrictMode();
```

---

## 🧠 Points to Remember

* **`try-catch` only catches synchronous errors**. If you place an asynchronous operation like `setTimeout` inside a `try` block, and the timeout throws an error, the `try-catch` will not catch it. Fix: Use async/await or handle the error using `.catch()`.
* **The `finally` block runs even if you return**. If you write a `return` statement inside the `try` block, JavaScript will execute the `finally` block *before* actually returning the value to the caller.

---

## 📖 Key Definitions

* **Error Handling**: A programming mechanism used to handle runtime exceptions, preventing applications from crashing using try-catch blocks.
* **throw statement**: A keyword used to generate user-defined custom exceptions and halt execution flow.
* **strict mode**: A feature declared via `"use strict"` that restricts JavaScript's syntax, throwing errors on silent bugs and optimizing execution.
* **try-catch-finally**: A control flow block where dangerous code runs in `try`, errors are handled in `catch`, and cleanup code runs in `finally`.
* **Error Object**: A built-in JavaScript object containing exception details, including a `name`, `message`, and `stack` trace.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is Error Handling in JavaScript?**
   * *Answer*: A mechanism used to catch and handle runtime exceptions using `try-catch` blocks, preventing the application from crashing.
2. **What does the `throw` statement do?**
   * *Answer*: It allows developers to generate custom errors programmatically, halting code execution and passing control to the closest catch block.
3. **What is strict mode in JavaScript?**
   * *Answer*: A feature declared via `"use strict"` that restricts syntax, converting silent mistakes into explicit runtime errors.
4. **How do you declare strict mode?**
   * *Answer*: By writing the string literal `"use strict";` at the very top of a script file or function body.
5. **Name three properties of the built-in `Error` object.**
   * *Answer*: `name` (the type of error), `message` (a description of the error), and `stack` (the stack trace of the error).
6. **What does the `finally` block do?**
   * *Answer*: It specifies a block of code that always executes after `try` and `catch`, regardless of whether an error was thrown or caught.
7. **Does `try-catch` catch compilation syntax errors?**
   * *Answer*: No, syntax errors (like missing brackets) are caught during the compilation phase before the script executes, so they cannot be caught at runtime.
8. **What happens if you assign a value to an undeclared variable in strict mode?**
   * *Answer*: JavaScript throws a ReferenceError.
9. **Can you delete a variable using the `delete` keyword in strict mode?**
   * *Answer*: No, using the `delete` keyword on variables or functions throws a SyntaxError in strict mode.
10. **What is the default value of `this` inside global functions in strict mode?**
    * *Answer*: It evaluates to `undefined`, preventing accidental modifications to the global `window` object.
11. **Name three built-in error subclasses in JavaScript.**
    * *Answer*: `ReferenceError`, `TypeError`, and `RangeError`.
12. **What does a `TypeError` represent?**
    * *Answer*: An error that occurs when a value is not of the expected type (e.g. attempting to call a number as a function: `const x = 5; x();`).
13. **What does a `ReferenceError` represent?**
    * *Answer*: An error that occurs when attempting to read or modify a variable that has not been declared.
14. **What does a `RangeError` represent?**
    * *Answer*: An error that occurs when a numeric value falls outside its allowed range (e.g. setting an array length to a negative number).
15. **Does the presence of a `finally` block block a `return` statement in a `try` block?**
    * *Answer*: No, but the `finally` block executes first, and then the return statement completes.
16. **How do you define a custom Error class?**
    * *Answer*: By extending the built-in `Error` class and calling `super(message)` in the constructor.
17. **Can you throw non-error values like strings or numbers?**
    * *Answer*: Yes, you can throw any value (e.g. `throw "Error!"`), but throwing a true `Error` object is preferred because it captures the stack trace.
18. **Does strict mode block duplicate property names in objects?**
    * *Answer*: In early ES5 strict mode it did, but modern ES6 allows duplicate keys, with the last duplicate overwriting previous values.
19. **What does the `debugger` keyword do?**
    * *Answer*: It halts code execution and opens the browser's developer tools debugger panel at that line automatically.
20. **Is strict mode enabled automatically inside ES Modules?**
    * *Answer*: Yes, all code written inside ES Modules runs in strict mode by default.

### 🟡 Intermediate Questions (21-40)

21. **Why does `try-catch` fail to catch errors thrown inside asynchronous `setTimeout` callbacks?**
    * *Answer*: Because `setTimeout` registers the callback and completes immediately. The `try` block exits. Later, the callback runs on a separate execution context, outside the active try-catch block.
22. **Explain the behavior of strict mode on function parameters.**
    * *Answer*: Strict mode forbids duplicate parameter names (e.g. `function add(a, a)` throws a SyntaxError).
23. **What is the difference between `Error.prototype.stack` and other error properties?**
    * *Answer*: `stack` is a non-standard but widely supported property containing a trace of the active function stack frames at the moment the error was thrown.
24. **How do you write a global unhandled exception catcher in browser environments?**
    * *Answer*: By listening to the window error event:
      ```javascript
      window.addEventListener('error', (event) => { console.log(event.error); });
      ```
25. **Explain what silent errors strict mode converts to runtime crashes.**
    * *Answer*: (1) Writing to read-only properties, (2) writing to getter-only properties, (3) creating properties on non-extensible objects, and (4) deleting undeletable properties.
26. **What is the consequence of throwing a non-Error primitive (like `throw 404`)?**
    * *Answer*: You lose the stack trace. Catch blocks cannot inspect call paths, making debugging difficult.
27. **How does JavaScript handle nested `try-catch` blocks?**
    * *Answer*: If an error occurs inside a nested `try` block, it is caught by the inner `catch` block. If the inner catch does not re-throw the error, the outer catch is ignored.
28. **Explain the benefit of subclassing the `Error` object for API responses.**
    * *Answer*: It allows categorizing errors (e.g., `class ValidationError extends Error`). Catch blocks can check errors using `instanceof` to handle them differently.
29. **Does strict mode prevent octane engine optimization de-optimizations?**
    * *Answer*: Yes, by blocking dynamic changes like modifying `arguments` parameters or using `eval()`, which helps the engine compile optimized code.
30. **What is the purpose of `Error.captureStackTrace()` in custom errors?**
    * *Answer*: A Node.js-specific method that creates a stack property on an error object, allowing developers to hide internal library frames from the stack trace.
31. **Explain the behavior of the `arguments` object in strict mode.**
    * *Answer*: In strict mode, the `arguments` object is static. Modifying function parameters does not update the values inside the `arguments` object, and vice versa.
32. **Why does strict mode forbid the use of octal literal numbers (e.g., `012`)?**
    * *Answer*: Because octal numbers are easily confused with standard decimals, leading to silent calculation bugs.
33. **Explain how `finally` blocks handle return values if the catch block also has a return statement.**
    * *Answer*: The return statement in the `finally` block overrides any return statement in `try` or `catch`.
34. **Does strict mode block the use of the `with` statement?**
    * *Answer*: Yes, it throws a SyntaxError because the `with` statement makes variable lookups dynamic and impossible to optimize at compile-time.
35. **What is an unhandled promise rejection, and how do you catch it globally in browsers?**
    * *Answer*: An error that occurs when a Promise rejects without a catch block. Catch it globally by listening to `unhandledrejection` events:
      ```javascript
      window.addEventListener('unhandledrejection', (e) => { e.preventDefault(); });
      ```
36. **Explain the difference between throwing an error (`throw new Error()`) and returning an error (`return new Error()`).**
    * *Answer*: Throwing halts execution and triggers propagation up the call stack. Returning treats the error object as a standard data value, continuing execution.
37. **Can you catch errors thrown inside a promise constructor using try-catch?**
    * *Answer*: No, errors inside a promise constructor trigger a promise rejection, which must be caught using `.catch()` or `await`.
38. **Explain what the `SyntaxError` constructor does.**
    * *Answer*: It creates an error object representing a syntax issue in the code, thrown by methods like `eval()` or `JSON.parse()`.
39. **What does strict mode do to the `caller` and `callee` properties of functions?**
    * *Answer*: It bans accessing `arguments.callee` and `function.caller` for security reasons, throwing a TypeError.
40. **How does JavaScript handle errors thrown inside catch blocks?**
    * *Answer*: The error halts execution of the catch block and propagates up to the next outer `try-catch` wrapper.

### 🔴 Advanced Questions (41-50)

41. **Explain how V8 constructs the stack trace array inside the Error prototype at the engine level.**
    * *Answer*: When `new Error()` is called, V8 captures the active stack frames. To save memory, it does not build the string representation immediately. Instead, it stores an array of internal frame pointers. The stack trace string is constructed lazily only when the `.stack` property is accessed.
42. **Why does executing code inside `try-catch` blocks historically limit V8's JIT compiler optimizations, and how is this handled in modern engines?**
    * *Answer*: In older V8 engines, functions containing a `try-catch` block could not be optimized by the compiler. Modern V8 uses the **Crankshaft/TurboFan** architecture, which handles `try-catch` natively, compiling and optimizing code paths while preserving de-optimization hooks on error.
43. **Explain how the V8 engine implements strict mode check assertions at compile time.**
    * *Answer*: During the parsing phase, if V8 detects the `"use strict"` token, it sets a strict-mode flag on the AST node. This flag changes compiler rules: it turns off automatic variable bindings and changes assignment bytecodes to check variable existence.
44. **What is the execution order difference when a finally block, a catch block, and an async promise rejection resolve concurrently?**
    * *Answer*: The `finally` block runs synchronously immediately after `catch` finishes its execution. Any asynchronous microtasks queued in the catch block (like promise resolves) execute later via the Event Loop, after the synchronous stack clears.
45. **How does strict mode modify the behavior of parameter scoping when using default parameters?**
    * *Answer*: If a function uses default parameters, it cannot declare `"use strict"` inside its body. This prevents scope mismatches, as default parameters are evaluated in an intermediate scope before entering the function body.
46. **Explain the security vulnerability that strict mode resolves by blocking access to `arguments.callee` and `caller`.**
    * *Answer*: Blocking these properties prevents functions from inspecting the call stack. Without this check, third-party libraries could read parent caller functions and access sensitive arguments or variables.
47. **Why does V8 bypass the creation of error stack traces for standard runtime errors under extreme memory pressure?**
    * *Answer*: Capturing stack traces requires allocating heap memory for frame pointers. Under extreme memory pressure, V8 may disable trace allocations to prevent Out-Of-Memory crashes, returning only basic error messages.
48. **Explain the difference in error catching capability between synchronous `try-catch` and the `Promise.prototype.catch` microtask scheduler.**
    * *Answer*: Synchronous `try-catch` runs on the active call stack frame. `Promise.prototype.catch` is registered in the Microtask Queue, allowing it to catch errors thrown asynchronously when the promise settles, long after the original call stack has cleared.
49. **How would you implement a custom error parser that formats V8 stack trace strings into structured JSON objects?**
    * *Answer*: Read the `.stack` property, split the string by newlines, use a regular expression to match function names, file paths, and line/column numbers, and map them to a JSON array.
50. **What is the performance cost of throwing and catching exceptions in high-frequency performance-critical loops?**
    * *Answer*: Throwing exceptions is expensive because capturing the stack trace requires walking the call stack and resolving frame symbols. Avoid using `try-catch` for standard control flow in loops, using simple return codes instead.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 07: ES6+ Classes & Modules](07_es6_classes_modules.md)
* **Next Chapter**: [👉 Topic 09: Fetch API & Web Storage](09_fetch_api_web_storage.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
