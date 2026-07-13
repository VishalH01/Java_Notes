# 🗃️ Topic 04: Objects, Arrays & Destructuring

Welcome back, data structurer! In this chapter, we will learn about **Objects, Arrays, Destructuring, and Prototype Chains**. Objects and arrays are the primary containers for data in JavaScript. We will learn how JavaScript implements inheritance without standard classes (using the Prototype Chain), how to use functional array loops (`map`, `filter`, `reduce`), and how to write clean code using destructuring and spread operators.

---

## 🏠 The Big Picture & Real-Life Example

### 📜 The Ancestral Blueprint Scroll & The Conveyor Belt
Imagine you are running a workshop:
* **The Prototype Chain (The Family Blueprint Scroll)**: You want to build a fancy gold watch. Instead of drawing 100 new dials, hands, and gears from scratch, you copy a generic watch blueprint scroll.
  * If someone asks for a dial, you look at your watch blueprint.
  * If they ask for a battery, and it's not on your watch blueprint, you look at the master clock blueprint scroll that your scroll refers to (**Prototype Link**).
  * You search up the family scrolls until you reach the origin blueprint scroll (**`Object.prototype`**). If it's not there, you give up (**`undefined`**).
* **Array Methods (The Conveyor Belt)**: You have a conveyor belt carrying toys:
  * **`map` (Painting)**: You paint every single toy red.
  * **`filter` (Quality Gate)**: You only let toys that pass validation slide through, dropping broken toys off the belt.
  * **`reduce` (The Packer)**: You combine all toys on the belt into a single big shipment box (**Accumulator**).
* **Destructuring (The Gift Unwrap)**: Instead of opening a package box, pulling out item 1, labeling it, and then doing it for item 2, you smash the box open and have all elements land directly on their labeled table slots in one move!

---

## 🔬 Let's Look Closer

### 1. The Prototype Chain
Every JavaScript object has a private link pointing to another object called its **Prototype** (`__proto__`). When you access a property:
1. The engine checks if the property exists directly on the object.
2. If not, it searches the object's prototype.
3. It keeps searching up the prototype chain until it reaches `Object.prototype` (which points to `null`).

### 2. Functional Array Methods
These are modern, pure array helper methods that do not mutate the original array:
* **`map()`**: Creates a new array by applying a callback to every element.
* **`filter()`**: Creates a new array with elements that pass a conditional test.
* **`reduce()`**: Runs a reducer function on each element, passing a running accumulator to output a single accumulated value.

---

## 💻 Code Sandbox

Let's test prototype links, array transformations, and destructuring in a sandbox.

```javascript
// 1. Prototype Chain Demonstration
const animal = {
    eats: true,
    walk: function() { console.log("Walking..."); }
};

// Creating a new object with animal as its prototype
const rabbit = Object.create(animal);
rabbit.jumps = true;

console.log("Rabbit eats:", rabbit.eats); // Output: true (Inherited!)
console.log("Rabbit jumps:", rabbit.jumps); // Output: true (Own property)
rabbit.walk(); // Output: "Walking..." (Inherited method called!)

// 2. Array Map, Filter, Reduce
const numbers = [1, 2, 3, 4, 5];

// Map: Double values
const doubled = numbers.map(x => x * 2); // [2, 4, 6, 8, 10]
// Filter: Get even numbers only
const evens = numbers.filter(x => x % 2 === 0); // [2, 4]
// Reduce: Calculate sum
const sum = numbers.reduce((acc, curr) => acc + curr, 0); // 15

console.log("Doubled:", doubled);
console.log("Evens:", evens);
console.log("Sum:", sum);

// 3. Destructuring & Spreads
const user = { username: "Alice", age: 30, location: "Paris" };
// Destructure with custom names and default values
const { username: name, age, gender = "F" } = user;
console.log(name, age, gender); // Output: "Alice" 30 "F"

// Array spread (merging)
const arr1 = [1, 2];
const arr2 = [...arr1, 3, 4]; // [1, 2, 3, 4]
console.log("Merged Array:", arr2);
```

---

## 🧠 Points to Remember

* **`map()` and `filter()` return new arrays**. They do not modify the original source array. Make sure to capture their return values in variables.
* **Avoid modifying `Object.prototype` directly**. Changing properties on the base object prototype affects every single object in your application runtime, which can cause security leaks and bugs.
* **`const` arrays can be mutated**. Declaring an array with `const` only locks the memory pointer. You can still call `arr.push()` or modify array items.

---

## 📖 Key Definitions

* **Prototype Chain**: An inheritance mechanism where JavaScript objects link to a parent prototype object, searching up the chain to resolve missing properties.
* **Array Map/Filter/Reduce**: Built-in functional array methods used to transform elements, filter elements by condition, or accumulate array values into a single output.
* **Destructuring**: A shorthand syntax introduced in ES6 that allows unpacking properties from objects or elements from arrays directly into variables.
* **Spread Operator**: The ES6 prefix `...` used to unpack elements of an array or properties of an object into a new container.
* **Rest Parameter**: The ES6 prefix `...` used in function signatures or destructuring to collect remaining items into a single array variable.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is the Prototype Chain in JavaScript?**
   * *Answer*: An inheritance mechanism where objects are linked to a parent prototype, allowing JS to look up properties dynamically along the chain.
2. **What does `Array.map()` do?**
   * *Answer*: It creates a new array by executing a callback function on every element of the calling array.
3. **What does `Array.filter()` do?**
   * *Answer*: It creates a new array containing only elements that pass a conditional test implemented by the callback function.
4. **What does `Array.reduce()` do?**
   * *Answer*: It runs an accumulator function on each element of the array, returning a single accumulated value.
5. **What is ES6 destructuring?**
   * *Answer*: A syntax that allows extracting properties from objects or values from arrays directly into independent variables.
6. **What is the difference between the Spread and Rest operators?**
   * *Answer*: Spread unpacks elements of an array or object properties. Rest packs remaining elements/arguments into a single array variable.
7. **Does `Array.forEach()` return a new array?**
   * *Answer*: No, `forEach()` executes a callback function for each element but returns `undefined`.
8. **How do you check if a property exists directly on an object (not inherited)?**
   * *Answer*: By calling `object.hasOwnProperty('property')` or `Object.hasOwn(object, 'property')`.
9. **How do you clone an object using the spread operator?**
   * *Answer*: By spreading it inside curly braces: `const clone = { ...original };`.
10. **What is the prototype of a plain object `{}`?**
    * *Answer*: `Object.prototype`.
11. **What does the prototype of `Object.prototype` point to?**
    * *Answer*: It points to `null` (representing the end of the prototype chain).
12. **How do you merge two arrays in ES6?**
    * *Answer*: Using the spread operator: `const merged = [...arr1, ...arr2];`.
13. **How do you skip elements during array destructuring?**
    * *Answer*: By leaving blank spaces separated by commas: `const [first, , third] = myArray;`.
14. **What is the difference between `__proto__` and `prototype`?**
   * *Answer*: `__proto__` is the link on an object instance pointing to its parent prototype. `prototype` is a property on constructor functions used to build new instances.
15. **Does `Array.map()` modify the original array?**
    * *Answer*: No, it returns a brand-new array, leaving the original array intact.
16. **How do you extract nested properties in object destructuring?**
    * *Answer*: By writing nested colon structures: `const { address: { city } } = user;`.
17. **What is the default value of the accumulator if not specified in `reduce()`?**
    * *Answer*: It defaults to the first element of the array, and the loop starts at the second element.
18. **How do you add a property to all instances of a constructor function?**
    * *Answer*: By writing it to the constructor's prototype: `User.prototype.role = "member";`.
19. **What does `Object.keys()` return?**
    * *Answer*: An array containing the names of all enumerable own properties of an object.
20. **What does `Object.create(proto)` do?**
    * *Answer*: It creates a new object and sets its internal prototype link (`__proto__`) to point directly to the passed `proto` object.

### 🟡 Intermediate Questions (21-40)

21. **Explain the performance difference between standard for-loops and functional array helpers (`map`, `filter`).**
    * *Answer*: Standard for-loops are faster because they don't have function call stack overhead. However, functional helpers are preferred because they are declarative and prevent state mutation bugs.
22. **What is prototypal inheritance?**
    * *Answer*: A design pattern where objects inherit properties directly from other objects via prototype links, bypassing the need for class templates.
23. **How do you create an object that has no prototype (points to no parent)?**
    * *Answer*: By calling: `const emptyObj = Object.create(null);`. This object will not inherit methods like `toString()` or `hasOwnProperty`.
24. **Explain how `Array.reduce()` can be used to flatten a nested array of arrays.**
    * *Answer*: By concatenating elements into a running accumulator array:
      ```javascript
      const flat = nested.reduce((acc, curr) => acc.concat(curr), []);
      ```
25. **Why does shallow copying using spread operators (e.g. `const clone = { ...user }`) fail to copy nested objects safely?**
    * *Answer*: Because it only copies primitive keys. For nested objects, it copies the memory reference pointer, meaning modifying a key inside `clone.address` mutates the original object.
26. **Explain what the constructor property represents on objects.**
    * *Answer*: A reference pointer stored on the prototype pointing back to the constructor function that instantiated the object.
27. **What is the difference between `Object.entries()` and `Object.values()`?**
    * *Answer*: `Object.values()` returns an array of the object's own property values. `Object.entries()` returns an array of `[key, value]` coordinate arrays.
28. **Explain the rules of object destructuring when variables are already declared.**
    * *Answer*: You must wrap the assignment statement in parentheses: `({ a, b } = obj);` to prevent the parser from mistaking the curly braces for a block statement.
29. **What is the difference between `Array.find()` and `Array.filter()`?**
    * *Answer*: `find()` returns the first element that matches the condition and halts execution. `filter()` loops through the entire array and returns a list of all matching elements.
30. **Explain how to set default values during destructuring.**
    * *Answer*: You assign defaults using the `=` operator: `const { name, role = "User" } = employee;`.
31. **What is the difference between standard property lookups and `in` operator checks?**
    * *Answer*: Standard lookups yield `undefined` if missing. The `in` operator checks own and inherited properties, returning a boolean: `'walk' in rabbit`.
32. **Explain the term "Prototype Pollution".**
    * *Answer*: A vulnerability where an attacker injects properties into `Object.prototype`, affecting all objects in the runtime, which can compromise security.
33. **Does modifying an array inside `Array.map()` callback mutate the original array?**
    * *Answer*: Only if you mutate elements explicitly (like `item.val = 5`). If you return new values without changing inputs, the original array is untouched.
34. **Explain how JavaScript implements class patterns in ES6.**
    * *Answer*: ES6 classes are syntactic sugar over prototype chains. Under the hood, declaring a class compiles to standard constructor functions and prototype method links.
35. **What does `Object.setPrototypeOf()` do, and why is it considered slow?**
    * *Answer*: It changes the prototype link of an existing object. It is slow because modifying prototypes dynamically forces V8 to invalidate hidden classes and recompile lookup paths.
36. **How does `Array.reduce()` behave on an empty array if no initial value is passed?**
    * *Answer*: It throws a TypeError: "Reduce of empty array with no initial value". Always provide an initial value for safety.
37. **What is the difference between array destructuring and object destructuring?**
    * *Answer*: Array destructuring extracts elements by index order. Object destructuring extracts properties by matching key names, ignoring order.
38. **Explain how the rest operator collects arguments inside function definitions.**
    * *Answer*: It must be the last parameter in the signature. It packs any remaining arguments passed to the function into a standard array.
39. **What does `Object.getOwnPropertyDescriptor()` do?**
    * *Answer*: It returns the configuration descriptors of a specific object property (e.g. `value`, `writable`, `enumerable`, `configurable`).
40. **How do you prevent properties from being iterated in a `for...in` loop?**
    * *Answer*: By defining the property descriptor `enumerable: false` using `Object.defineProperty()`.

### 🔴 Advanced Questions (41-50)

41. **Explain the V8 engine's internal lookup transition from Inline Caching (IC) to prototype lookups.**
    * *Answer*: When a property is queried, V8 checks the object's Hidden Class. If not found, it checks the prototype's Hidden Class, repeating up the chain. V8 caches this resolved offset (Inline Cache). If the prototype chain is mutated, V8 invalidates the cache, degrading lookup speed.
42. **Why does executing recursive deep copies using `JSON.parse(JSON.stringify(obj))` fail on objects containing functions, dates, or circular references?**
    * *Answer*: Because JSON serialization only supports basic types. Functions are completely removed; Date objects compile to ISO strings; and circular references cause the parser to throw a TypeError: "Converting circular structure to JSON".
43. **Explain the performance difference in the V8 garbage collector when allocating many small object literals versus reusing prototypes.**
    * *Answer*: Creating objects with inline methods duplicates function closures in the heap, causing garbage collection cycles. Reusing prototype methods stores only one function in the prototype node, saving memory and heap space.
44. **How does JavaScript's `Object.create(null)` differ at the bytecode level from `{}` during property lookups?**
    * *Answer*: `{}` compiles with an internal prototype link to `Object.prototype`. Lookups that miss in `{}` travel up the chain. `Object.create(null)` has no prototype link, so lookups bail out immediately, saving lookup cycles.
45. **What is the difference in V8 optimization between array lookups using contiguous arrays (Fast Elements) versus sparse arrays (Dictionary Elements)?**
    * *Answer*: Contiguous arrays allocate linear memory segments (Fast Elements). If you insert an item at index 1000 in a 3-item array, V8 converts it to a sparse hash dictionary (Dictionary Elements), which degrades lookup performance.
46. **Explain what the internal `[[Prototype]]` property represents and how it relates to standard browser `__proto__`.**
    * *Answer*: `[[Prototype]]` is the internal engine slot that stores the prototype reference. `__proto__` is a legacy getter/setter accessor property exposed on `Object.prototype` to read/write it.
47. **How does the `structuredClone()` utility handle circular references and typed arrays compared to custom copy functions?**
    * *Answer*: `structuredClone()` uses the HTML structured clone algorithm. It tracks visited objects to resolve circular loops, and supports copying RegExp, Date, Blob, and typed arrays.
48. **Explain the security risk of prototype pollution via query parameters or JSON payloads.**
    * *Answer*: If user input is processed recursively without sanitization (e.g. `obj[key][prop] = val`), an attacker can pass `__proto__` as the key and inject dangerous defaults, polluting all objects.
49. **Why does the V8 engine optimize array iteration methods (like `map`, `reduce`) using turbofan optimization, and when do they get de-optimized?**
    * *Answer*: V8 optimizes arrays that have uniform types (Monomorphic). If you change array element types dynamically (e.g. changing numbers to strings), V8 de-optimizes the loop, falling back to slow dynamic lookups.
50. **What is the difference between a constructor function's prototype and the prototype of the constructor function itself?**
    * *Answer*: A constructor function (e.g. `function User() {}`) has a `.prototype` property used to set the prototype of new instances. The constructor's *own* prototype (e.g. `User.__proto__`) points to `Function.prototype`, inheriting function methods.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 03: Functions & Execution Context](03_functions_execution_context.md)
* **Next Chapter**: [👉 Topic 05: Asynchronous JS & Promises](05_async_promises_async_await.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
