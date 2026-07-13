# 🎬 Topic 01: Introduction to JavaScript & DOM Basics

Welcome, web developer! In this chapter, we will learn the foundations of **JavaScript and the DOM (Document Object Model)**. JavaScript is the programming language that makes websites alive. We will learn how a browser parses code using engines (like Chrome's V8), how to link scripts to HTML, and how to select and change HTML elements on the fly.

---

## 🏠 The Big Picture & Real-Life Example

### 📺 The Remote Control & The Television Screen
Imagine you buy a brand-new television:
* **The TV Cabinet & Glass Screen (HTML)**: This is the static structure. It has a power button slot and speaker grills. It stands there, looking nice but completely silent.
* **The Remote Control (JavaScript)**: This is the dynamic engine. When you press a button on the remote, it sends an electrical signal to the TV.
* **The Signal Receiver & DOM (Document Object Model)**: The TV has a sensor board (**DOM**) that translates the remote's signals. If the remote signal says: *"Set channel to 5,"* the sensor board changes the picture pixels on the screen. The remote doesn't physical touch the glass; it sends commands to the sensor board to update the view!
* **The V8 Engine (The Microprocessor)**: Inside the remote control is a tiny chip (**V8 engine**) that compiles your button press into electricity instantly.

---

## 🔬 Let's Look Closer

### 1. The V8 Engine Compiler
JavaScript is a high-level language. It runs inside a **JS Engine** (such as Chrome and Node's **V8**, Safari's **JavaScriptCore**, or Firefox's **Spidermonkey**).
* Legacies used simple interpreters (reading and executing line-by-line, which is slow).
* Modern engines use **JIT (Just-In-Time) Compilation**. They compile JavaScript directly into machine code at runtime, optimizing hot paths dynamically for speed.

### 2. The DOM (Document Object Model)
When a browser loads an HTML page, it parses the text and builds a tree structure of objects in memory representing the document. This is the **DOM tree**.
* Each tag (`<div>`, `<p>`) is a **Node object**.
* JavaScript does not modify raw HTML files on disk; it modifies these DOM node objects in memory, which triggers the browser to redraw the screen.

---

## 💻 Code Sandbox

Here is how a script targets and modifies HTML elements.

```html
<!DOCTYPE html>
<html>
<head>
    <title>JS DOM Playground</title>
</head>
<body>
    <h1 id="main-heading">Hello World</h1>
    <p class="description">This text is static HTML.</p>
    <button id="action-btn">Click Me!</button>

    <!-- Linking external script file -->
    <script>
        // 1. Selecting elements from the DOM
        const heading = document.getElementById('main-heading');
        const paragraph = document.querySelector('.description');
        const button = document.querySelector('#action-btn');

        // 2. Listening for user interactions
        button.addEventListener('click', () => {
            // 3. Modifying DOM attributes and content
            heading.textContent = "Welcome to JavaScript!";
            heading.style.color = "blue";
            paragraph.innerHTML = "This paragraph was updated <strong>dynamically</strong>!";
        });
    </script>
</body>
</html>
```

---

## 🧠 Points to Remember

* **Where to place `<script>` tags**. Standard script tags block HTML parsing. If you put a script in the `<head>`, and it tries to select a button in the `<body>`, it will fail because the button hasn't been parsed yet. Fix: Put scripts at the bottom of the `<body>`, or use `defer` / `async` attributes.
* **`defer` vs `async`**:
  * `defer`: Downloads the script in the background and executes it *after* HTML parsing is fully complete (preserves execution order).
  * `async`: Downloads the script in the background and executes it *immediately* when download finishes, pausing HTML parsing (does not guarantee order).

---

## 📖 Key Definitions

* **JavaScript Engine**: A program or interpreter that executes JavaScript code, such as Google's V8 engine used in Chrome and Node.js.
* **DOM (Document Object Model)**: A programming interface representing web documents as a tree structure of nodes that scripts can select and modify.
* **script tag**: An HTML tag used to embed or reference external executable JavaScript code files.
* **querySelector()**: A modern, versatile DOM selection method that returns the first child element matching a specified CSS selector string.
* **Dynamic Typing**: A language characteristic where variables are not bound to specific data types, allowing them to hold different types of values.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is JavaScript?**
   * *Answer*: JavaScript is a high-level, lightweight, interpreted or JIT-compiled programming language with first-class functions, primarily used to build interactive web pages.
2. **What is a JavaScript Engine?**
   * *Answer*: A program that compiles and executes JavaScript code. Examples include Google V8 (Chrome), SpiderMonkey (Firefox), and JavaScriptCore (Safari).
3. **What is the DOM (Document Object Model)?**
   * *Answer*: An API representation of HTML documents as a tree of objects, allowing languages like JavaScript to read and modify page structures.
4. **How do you link an external JavaScript file to HTML?**
   * *Answer*: By using a script tag with the src attribute pointing to the script path: `<script src="script.js"></script>`.
5. **What is the difference between `innerText` and `innerHTML`?**
   * *Answer*: `innerText` returns or sets only raw text, escaping tags. `innerHTML` retrieves or sets HTML tags, rendering them as child DOM nodes.
6. **What does `document.getElementById()` do?**
   * *Answer*: A DOM query method that returns the single element matching the specified `id` attribute string, or null if no match is found.
7. **What is the difference between `querySelector()` and `querySelectorAll()`?**
   * *Answer*: `querySelector()` returns the first child node matching a CSS selector. `querySelectorAll()` returns a static NodeList of all matching nodes.
8. **What does the `defer` attribute do in script tags?**
   * *Answer*: It tells the browser to download the script file in parallel and execute it only *after* the HTML parsing has fully finished.
9. **What does the `async` attribute do in script tags?**
   * *Answer*: It downloads the script in parallel and executes it immediately when the download finishes, pausing HTML parsing.
10. **Where is the best place to insert script tags if not using defer?**
    * *Answer*: At the very bottom of the HTML `<body>` container to ensure all elements are parsed and available for selection.
11. **Is JavaScript case-sensitive?**
    * *Answer*: Yes, JavaScript is case-sensitive (e.g., variables `myVar` and `myvar` are distinct).
12. **What is dynamic typing in JavaScript?**
    * *Answer*: A feature where variables are not bound to a specific data type and can hold strings, numbers, or objects dynamically.
13. **How do you write comments in JavaScript?**
    * *Answer*: Single-line comments start with `//`. Multi-line comments are wrapped inside `/* ... */`.
14. **What is an event listener?**
    * *Answer*: A method attached to a DOM node that waits for a specific user action (like click, keydown) and executes a callback function.
15. **What does `textContent` do?**
    * *Answer*: It reads or sets the text content of a node and all of its descendants, bypassing any styling layout checks.
16. **Is JavaScript single-threaded or multi-threaded?**
    * *Answer*: JavaScript is single-threaded, meaning it has a single call stack and executes one block of code at a time.
17. **What is the default global object in browser environments?**
    * *Answer*: The `window` object represents the browser window environment.
18. **How do you change the inline style of a DOM element using JS?**
    * *Answer*: By accessing the style property on the node: `element.style.color = "red"`.
19. **What is a NodeList in DOM manipulation?**
    * *Answer*: An array-like collection of DOM nodes returned by methods like `querySelectorAll()` or child nodes properties.
20. **What is the difference between HTML elements and DOM nodes?**
    * *Answer*: A DOM node is the generic base class object representing any element, text fragment, or comment in the tree. An HTML element is a specific node representing a tag.

### 🟡 Intermediate Questions (21-40)

21. **Explain the compilation differences between Just-In-Time (JIT) Compilation and Ahead-Of-Time (AOT) Compilation.**
    * *Answer*: AOT compiles code into binary machine instructions before execution (like C++). JIT compiles code on the fly during runtime, analyzing hot paths and recompiling them into highly optimized machine code dynamically.
22. **What is the difference between `NodeList` and an `HTMLCollection`?**
    * *Answer*: `HTMLCollection` is live (updates automatically when DOM changes) and contains only elements. `NodeList` is usually static and can contain text nodes and comment nodes.
23. **Why does modifying class names using `element.classList` perform better than using `element.className = "..."`?**
    * *Answer*: `className` overwrites the entire class string, forcing parsing. `classList` provides methods (`add`, `remove`, `toggle`) to modify individual classes efficiently without parsing the whole string.
24. **Explain the browser rendering pipeline steps when JavaScript updates a DOM node.**
    * *Answer*: (1) DOM tree updates. (2) Style Recalculation. (3) Layout (reflow calculations). (4) Paint (pixels rasterization). (5) Composite (compositing layers on GPU).
25. **What is the difference between Reflow (Layout) and Repaint, and how does JS trigger them?**
    * *Answer*: Reflow is calculating element geometry positions (triggered by size or margin changes). Repaint is drawing pixels (triggered by color or visibility changes). Reflows are much slower.
26. **Explain what the "DOMContentLoaded" event is and how it differs from the "load" event.**
    * *Answer*: `DOMContentLoaded` fires when the HTML document is parsed (scripts are safe to run). `load` fires later, once all resources (images, stylesheets) have finished loading.
27. **What is a Document Fragment (`document.createDocumentFragment()`), and why does it optimize DOM insertions?**
    * *Answer*: A lightweight, virtual DOM container. Appending nodes to a fragment and then appending the fragment to the DOM triggers exactly **one reflow**, instead of triggering a reflow on every individual append.
28. **How does Chrome's V8 engine optimize property lookups in dynamically typed JS objects?**
    * *Answer*: By using **Hidden Classes** (Shape maps). V8 creates hidden schemas internally as properties are added to objects, optimizing memory lookups to match static C++ structures.
29. **What is the difference between `src` script blocking and loading scripts dynamically?**
    * *Answer*: Standard script tags halt HTML parsing. Loading scripts dynamically via JS (`const s = document.createElement('script'); document.body.appendChild(s);`) behaves asynchronously by default.
30. **Explain how to select elements based on custom data attributes (e.g. `data-user-id="5"`).**
    * *Answer*: Using query selectors: `document.querySelector('[data-user-id="5"]')`, or accessing properties via `element.dataset.userId`.
31. **What is a "Live NodeList"? Name a method that returns one.**
    * *Answer*: A list that updates automatically when the DOM changes. `document.getElementsByTagName()` and `document.getElementsByClassName()` return live collections.
32. **Explain the security risks of utilizing `innerHTML` with unsanitized user inputs.**
    * *Answer*: It exposes applications to Cross-Site Scripting (XSS) attacks. Attackers can inject malicious scripts (e.g. `<img src=x onerror=stealCookies()>`) that execute in the victim's browser.
33. **How does the parser handle inline scripts compared to external scripts?**
    * *Answer*: Both block HTML parsing by default. However, external scripts require network downloads, meaning parsing halts for a longer period unless deferred.
34. **What is the difference between `window` and `document` objects?**
    * *Answer*: `window` represents the global browser window tab execution environment. `document` represents the active HTML DOM document loaded inside that window.
35. **What is the difference between querySelector and getElementById in terms of execution performance?**
    * *Answer*: `getElementById` is faster because it uses a direct lookup map. `querySelector` must parse a CSS selector string, which is slower but more versatile.
36. **Explain what strict mode does when compiling JavaScript.**
    * *Answer*: Declared via `"use strict"`. It throws errors on silent mistakes (like assigning values to undeclared variables) and blocks unsafe syntax.
37. **What is the difference between `element.getAttribute('href')` and `element.href`?**
    * *Answer*: `getAttribute` returns the literal string written in the HTML tag. `element.href` returns the fully resolved absolute URL.
38. **How do you determine if an element is visible in the viewport?**
    * *Answer*: By calling `element.getBoundingClientRect()`, which returns element coordinates, and checking if they fall within viewport dimensions.
39. **Explain the benefit of using CSS custom properties (variables) with JS DOM manipulation.**
    * *Answer*: You can modify styles globally with one JS call: `document.documentElement.style.setProperty('--main-color', 'blue')`, avoiding loops over individual elements.
40. **How does V8's Garbage Collector track variables in the DOM tree?**
    * *Answer*: It uses a mark-and-sweep algorithm. If a DOM node is removed from the page but a JS variable still references it, it is classified as a "Detached DOM node" and cannot be collected, causing memory leaks.

### 🔴 Advanced Questions (41-50)

41. **Explain the internal V8 compilation lifecycle (Ignition Interpreter to TurboFan Compiler).**
    * *Answer*: V8 parses code into an Abstract Syntax Tree (AST). The **Ignition** interpreter compiles the AST into bytecode. As code executes, V8 monitors execution stats. If a function is called frequently (hot path), V8 sends the bytecode to **TurboFan**, which compiles it into optimized machine code. If a type changes, V8 de-optimizes and falls back to bytecode.
42. **Why does executing inline style modifications in a tight animation loop trigger "Layout Thrashing" (Forced Synchronous Layout)?**
    * *Answer*: If you modify a style (write) and immediately read a geometric property (like `offsetHeight`) in the same loop cycle, JS forces the browser to calculate layout synchronously. If done repeatedly, it causes reflow bottlenecks and lags the frame rate.
43. **Explain how to optimize DOM insertions when rendering 10,000 items utilizing Virtual DOM concepts or DocumentFragments.**
    * *Answer*: Instead of inserting elements one-by-one, append all elements to a single virtual `DocumentFragment` first. Then, execute a single DOM append, causing only one reflow repaint cycle.
44. **What is the garbage collection risk of keeping references to Detached DOM Nodes in JavaScript arrays?**
    * *Answer*: Detached DOM nodes are nodes removed from the DOM tree, but still referenced by JS code. Because they are reachable, the garbage collector cannot free their memory, creating a memory leak.
45. **How does the parser handle `<script type="module">` tags by default in modern browsers?**
    * *Answer*: Module scripts are treated as if they have the `defer` attribute by default. They download in parallel, do not block HTML parsing, and execute in strict mode in order of declaration.
46. **Explain what the V8 Hidden Classes (Map transitions) are, and how adding properties in random order impacts object performance.**
    * *Answer*: V8 matches objects to internal shape maps. If two objects share the same properties in the same order, they share a map. If you add properties in random orders, V8 creates separate map transitions, degrading compiler optimizations.
47. **How does browser paint scheduling interact with the CPU requestAnimationFrame callback?**
    * *Answer*: `requestAnimationFrame` runs right before the layout and paint steps. This ensures styling updates run in sync with the screen refresh rate, preventing frame skips and flickering.
48. **Explain the differences in DOM tree reconciliation between standard browsers and Shadow DOM scopes.**
    * *Answer*: The standard DOM is open. The Shadow DOM creates a encapsulated root branch. Styles inside the shadow tree do not leak out, and queries from the outer scope do not select shadow elements unless explicitly queried through the shadow root interface.
49. **Why is reading geometric values like `getBoundingClientRect()` or `scrollTop` inside a scroll event handler a performance bottleneck?**
    * *Answer*: Because it queries the browser for updated layout geometries, forcing layout computations on every scroll event tick. It is optimized by throttling events and caching layouts in variables.
50. **Explain how Chrome's V8 engine implements Ignition bytecode optimizations to save mobile RAM.**
    * *Answer*: V8 Ignition compiles Javascript into compact bytecode instead of native machine code. Bytecode uses up to 4x less memory than machine code, allowing mobile devices to run complex scripts without running out of memory.

---

## ⏭️ Next Steps

* **Next Chapter**: [👉 Topic 02: Variables, Scope & Hoisting](02_variables_scope_hoisting.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
