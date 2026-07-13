# 🏗️ Topic 07: ES6+ Classes & Modules

Welcome back, class architect! In this chapter, we will learn about **ES6 Classes and Modules**. While JavaScript is a prototype-based language, ES6 introduced the `class` keyword to provide a clean, readable syntax for Object-Oriented Programming. We will learn how to write classes, inherit methods using `extends`, and organize code across multiple files using ES Modules (`import` and `export`).

---

## 🏠 The Big Picture & Real-Life Example

### 🏭 The Toy Factory Blueprint & The Cargo Shipping Container
Imagine you run a global toy company:
* **The Class (The Factory Blueprint)**: Instead of manually linking prototypes by hand for every plastic doll you make, you write a standardized blueprint called a **Class**:
  ```javascript
  class Doll { constructor(hairColor) { this.hairColor = hairColor; } }
  ```
  This blueprint has a setup machine called a **Constructor** that configures hair color when a new doll is made.
* **Inheritance (The Specialized Blueprint)**: You want to make a special crying doll. You don't redraw the hair or arms. You copy the Doll blueprint using **`extends Doll`** and add a crying speaker. The child constructor calls **`super()`** to run the parent doll assembly machine first.
* **Modules (The Cargo Containers)**: In the past, you threw all your factory tools, blueprints, and materials into one giant room (**One giant script file**). It was messy. Now, you package related tools into separate containers (**Modules**):
  * **Export**: The wheel factory packages wheels into a container and labels it: `export const wheels = [...]`.
  * **Import**: The main assembly factory requests that container from shipping: `import { wheels } from './wheel_factory.js'`.

---

## 🔬 Let's Look Closer

### 1. ES6 Classes (Syntactic Sugar)
ES6 classes do **not** introduce a new object-oriented inheritance model to JavaScript. They are syntactic sugar over JavaScript's existing **prototype-based inheritance**.
* Under the hood, class methods are attached to the prototype object (`ClassName.prototype`).
* Methods declared inside classes are non-enumerable by default.

### 2. ES Modules (ESM) vs. CommonJS
Modern JavaScript uses **ES Modules (ESM)** as the standard system:
* **ESM**: Uses `import` and `export` statements. It is static, meaning imports are resolved at compile-time before code executes, allowing tree-shaking (removing unused code).
* **CommonJS**: Uses `require()` and `module.exports`. It is dynamic and resolved at runtime (default in older Node.js).

---

## 💻 Code Sandbox

Let's build a class hierarchy and demonstrate module export syntax in a sandbox.

```javascript
// --- 1. OOP Class inheritance ---
class Vehicle {
    constructor(make, model) {
        this.make = make;
        this.model = model;
    }

    getDetails() {
        return `Vehicle: ${this.make} ${this.model}`;
    }
}

class Car extends Vehicle {
    constructor(make, model, doors) {
        // super() calls the parent constructor to initialize make and model
        super(make, model); 
        this.doors = doors;
    }

    // Overriding parent method
    getDetails() {
        return `${super.getDetails()} with ${this.doors} doors`;
    }
}

const myCar = new Car("Toyota", "Camry", 4);
console.log(myCar.getDetails()); // Output: "Vehicle: Toyota Camry with 4 doors"

// --- 2. ES Module Emulation (Exports/Imports) ---
// File: mathUtils.js
export const pi = 3.14159;
export function add(a, b) { return a + b; }
export default class Calculator {
    multiply(a, b) { return a * b; }
}

// File: app.js
// import Calculator, { pi, add } from './mathUtils.js';
```

---

## 🧠 Points to Remember

* **`super()` must be called first** in a child constructor. If you try to write `this.doors = doors` before calling `super()`, JavaScript will throw a ReferenceError because the parent constructor has not initialized the object context yet.
* **ES Modules run in strict mode by default**. When you load a script using `<script type="module">`, strict mode is enabled automatically.
* **ES Modules are evaluated once**. If multiple files import the same module, the module code is executed only once when first loaded, and subsequent imports receive the cached exports.

---

## 📖 Key Definitions

* **ES6 Class**: Syntactic sugar over JavaScript's prototype-based inheritance, providing a cleaner syntax to define object blueprints.
* **constructor method**: A special method in a class used to initialize and configure new instances of that class.
* **extends keyword**: A keyword used in class declarations to create a child class that inherits properties and methods from a parent class.
* **super()**: A function call used inside a child class constructor to call and initialize the parent class constructor.
* **ES Module (ESM)**: The official standardized module system in JavaScript, using `import` and `export` statements to share code between files.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is an ES6 class in JavaScript?**
   * *Answer*: Syntactic sugar over prototype-based inheritance, providing a clean, readable syntax to define object blueprints.
2. **What is the purpose of the `constructor` method in a class?**
   * *Answer*: A special method used to initialize and configure new class instances when created with the `new` keyword.
3. **How does a class inherit from another class?**
   * *Answer*: By using the `extends` keyword (e.g., `class Child extends Parent`).
4. **What does `super()` do?**
   * *Answer*: It invokes the constructor of the parent class. It must be called first in a child constructor before using `this`.
5. **What is the difference between Named Exports and Default Exports?**
   * *Answer*: You can have multiple Named Exports per module, imported with exact names in curly braces. You can only have one Default Export per module, imported without curly braces using any name.
6. **What is an ES Module (ESM)?**
   * *Answer*: The standardized module system in JavaScript, using `import` and `export` statements.
7. **Is class code executed in strict mode by default?**
   * *Answer*: Yes, all code written inside a class body runs in strict mode automatically.
8. **What are static methods in ES6 classes?**
   * *Answer*: Methods defined with the `static` keyword that belong to the class itself, not its instances. They are called directly on the class: `MathUtils.add()`.
9. **How do you define private properties in modern ES6 classes?**
   * *Answer*: By prefixing the property name with a hash `#` symbol (e.g. `#salary`).
10. **What is the difference between ES Modules (ESM) and CommonJS (CJS)?**
    * *Answer*: ESM uses `import`/`export` and is resolved statically. CommonJS uses `require()`/`module.exports` and is resolved dynamically at runtime.
11. **Do ES6 classes support multiple inheritance?**
    * *Answer*: No, a JavaScript class can only extend a single parent class.
12. **What happens if you omit the constructor method in a class?**
    * *Answer*: JavaScript inserts a default empty constructor automatically (which calls `super()` if it is a subclass).
13. **Can you call static methods from class instances?**
    * *Answer*: No, static methods are not available on instances. You must call them directly on the class name.
14. **How do you import everything from a module as an object?**
    * *Answer*: By using the wildcard syntax: `import * as Utils from './utils.js';`.
15. **What are Getters and Setters in ES6 classes?**
    * *Answer*: Methods prefixing `get` and `set` that bind object properties to functions, allowing you to run code when reading or writing properties.
16. **Are class declarations hoisted?**
    * *Answer*: Yes, but like `let` and `const`, they are hoisted without initialization, meaning they reside in the Temporal Dead Zone (TDZ) and cannot be accessed before declaration.
17. **What is the file extension commonly used to force ES modules in Node.js?**
    * *Answer*: `.mjs`.
18. **Can you export variables declared with `let` or `const`?**
    * *Answer*: Yes, named exports can export any variable declaration.
19. **How do you rename imports from a module?**
    * *Answer*: By using the `as` keyword: `import { add as sum } from './math.js';`.
20. **Do ES modules load synchronously or asynchronously in browsers?**
    * *Answer*: Asynchronously by default. They behave like deferred scripts, executing after the HTML document is fully parsed.

### 🟡 Intermediate Questions (21-40)

21. **Explain why classes are described as "syntactic sugar" in JavaScript.**
    * *Answer*: Because they don't introduce a new object-oriented inheritance model. Under the hood, they compile to constructor functions and prototype method links.
22. **What is the difference in prototype linkage between static methods and instance methods?**
    * *Answer*: Instance methods are attached to `Class.prototype`. Static methods are attached directly to the constructor function object itself.
23. **Why must `super()` be called before accessing `this` inside a child class constructor?**
    * *Answer*: Child class constructors do not have a default `this` binding. They rely on the parent constructor (`super()`) to instantiate the object and return the `this` context.
24. **Explain how private class properties (`#prop`) differ from standard properties.**
    * *Answer*: Standard properties are accessible outside the class. Private properties prefixing `#` are enforced by the engine, throwing errors if accessed outside class brackets.
25. **What is Module Tree Shaking, and why does ES Modules support it while CommonJS does not?**
    * *Answer*: Tree shaking removes unused code from bundles. ESM supports it because its imports are static and resolved at compile time. CommonJS imports are dynamic and resolved at runtime, making static analysis impossible.
26. **Explain what the `new.target` meta-property represents.**
    * *Answer*: An internal reference pointer. In constructors, it points to the class invoked with `new`. In child classes, it points to the subclass, useful for tracking instantiations.
27. **What is the difference between standard module imports and dynamic imports (`import()`)?**
    * *Answer*: Standard imports are static and block until loaded. Dynamic imports (`import('/path.js')`) are callable functions that return a Promise, allowing code-splitting at runtime.
28. **How does JavaScript handle circular dependencies in ES Modules?**
    * *Answer*: ESM handles circular dependencies by creating a module map before execution. Variables are bound as live references, allowing them to work if resolved before they are read.
29. **What is the benefit of using getters instead of standard methods?**
    * *Answer*: Getters allow you to read calculated values as if they were properties (e.g. `user.fullName`), simplifying property access.
30. **Explain how to extend built-in classes like `Array` or `Error`.**
    * *Answer*: By using `class MyArray extends Array` and calling `super()`. This creates a subclass that inherits all array methods while allowing you to add custom helpers.
31. **Are ES Modules evaluated multiple times if imported by different files?**
    * *Answer*: No, ES Modules are singletons. They execute only once when first loaded, and subsequent imports receive the cached exports.
32. **What does the `module` wrapper do in CommonJS behind the scenes?**
    * *Answer*: In CommonJS, Node wraps every file in an IIFE function passing `exports`, `require`, `module`, `__filename`, and `__dirname` to isolate scope.
33. **Can you use ES Modules inside a standard HTML script tag?**
    * *Answer*: Yes, but you must add the type attribute: `<script type="module" src="app.js"></script>`.
34. **Explain how class declarations differ from class expressions.**
    * *Answer*: Class declarations are written standalone: `class User {}`. Class expressions bind the class to a variable: `const User = class {};`, which can be anonymous.
35. **What is the purpose of the static initialization block (`static {}`) in modern classes?**
    * *Answer*: A block that executes once when the class is loaded, used to initialize complex static properties or handle errors.
36. **How do you export a default and named exports from the same file?**
    * *Answer*: Declare both:
      ```javascript
      export const name = "Alice";
      export default class User {}
      ```
37. **Why does ES Modules throw a CORS error when loaded directly from the local file system (`file://`) in browsers?**
    * *Answer*: For security reasons. Browsers block module scripts from reading local files via AJAX, requiring a local HTTP server to load modules.
38. **What does the `export { name }` syntax do?**
    * *Answer*: It exports a previously declared variable `name` as a named export.
39. **Explain how class fields are initialized relative to constructor executions.**
    * *Answer*: Class fields (properties declared outside methods) are initialized immediately before the constructor body runs.
40. **How does class inheritance in JavaScript differ from class inheritance in Java?**
    * *Answer*: Java uses class-based inheritance where types are fixed at compile time. JavaScript uses prototype-based inheritance, where objects inherit directly from other object instances at runtime.

### 🔴 Advanced Questions (41-50)

41. **Explain the compilation differences between ES Module linking and CommonJS evaluation in the V8 engine.**
    * *Answer*: CommonJS loads modules synchronously: when `require()` is called, V8 pauses, compiles, and runs the file to extract exports. ESM compiles modules statically in three phases: (1) **Construction** (parsing files into module records), (2) **Instantiation** (allocating memory slots for exports), and (3) **Evaluation** (executing code to fill slots).
42. **Why does calling `super()` in a subclass constructor trigger V8 to run the parent constructor on the child's `this` context, and how is this managed in the prototype chain?**
    * *Answer*: Child class constructors are flagged as uninitialized. Calling `super()` invokes the parent constructor, which binds `this` to a new object instance. The parent constructor passes this instantiated context back to the child constructor, which then assigns properties to it.
43. **How does tree-shaking analyze ES Module trees to remove dead code paths, and when does it fail?**
    * *Answer*: Bundlers build a static dependency graph. Since ESM imports are fixed, the bundler can trace which exports are never referenced and remove them. This fails if a module triggers side effects (like mutating global variables), because the compiler cannot safely remove it.
44. **What is the internal bytecode representation of Private Class Fields (`#field`) in V8, and how does it prevent external inspections?**
    * *Answer*: V8 implements private fields using **WeakMaps** internally. The compiler creates a private WeakMap key. Accessing `#field` compiles to a WeakMap lookup, preventing access from outside the class scope.
45. **Explain the performance cost of dynamic imports (`import()`) inside loops compared to static imports.**
    * *Answer*: Dynamic imports return a Promise and resolve asynchronously. Using them in loops causes network overhead and forces V8 to compile and link modules repeatedly, degrading performance.
46. **How does V8 optimize prototype property lookups when a class hierarchy extends deeply (e.g. 5 layers of subclasses)?**
    * *Answer*: V8 uses **Prototype Chains** optimized by **Inline Caching**. When a method is called, V8 caches the target method offset in memory. If a prototype along the chain changes, V8 invalidates the cache, falling back to slow tree traversal.
47. **What is the difference between the module graph lifecycle phases: Construction, Instantiation, and Evaluation?**
    * *Answer*: Construction fetches and parses files into module records. Instantiation links import/export pointers in memory without running code. Evaluation runs the code to assign actual values to the memory slots.
48. **Explain why static properties are not copied to instance prototypes during class inheritance, and how subclasses access parent static methods.**
    * *Answer*: Static properties are defined on the class constructor object itself, not its prototype. Subclasses access them because the subclass constructor's prototype link points to the parent constructor.
49. **How would you configure Node.js to support both CommonJS and ES Modules concurrently inside a single package (Dual Package Hazard)?**
    * *Answer*: By defining conditional exports in `package.json`:
      ```json
      "exports": { "import": "./index.mjs", "require": "./index.cjs" }
      ```
      Ensure modules do not duplicate state, as importing both versions allocates separate memory slots.
50. **Explain how class getters/setters affect the hidden class (Map) transitions in V8.**
    * *Answer*: Getters/setters do not transition properties like standard fields. V8 registers them as **Accessor Descriptors** on the Hidden Class, compiling property access to direct function calls.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 06: DOM Events & Propagation](06_dom_events_propagation.md)
* **Next Chapter**: [👉 Topic 08: Error Handling & Strict Mode](08_error_handling_strict_mode.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
