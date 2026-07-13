# 🎬 Topic 01: Introduction to React & JSX

Welcome, web engineer! In this chapter, we will learn about **React & JSX**. In vanilla JavaScript, updating the browser UI is tedious and slow. React changes this by introducing a declarative paradigm. We will learn how React uses an in-memory duplicate of the browser (the Virtual DOM) to make lightning-fast updates, and how to write HTML inside JavaScript using JSX.

---

## 🏠 The Big Picture & Real-Life Example

### 📐 The Architect's Blueprint vs. The Bricklayer
Imagine you want to change a wall inside a house:
* **The Imperative Way (Vanilla JS)**: You hire a bricklayer and tell him: *"First, walk 5 steps. Pick up a hammer. Smash the 3rd brick from the left. Clean the dust. Lay a blue brick."* If you make a tiny mistake in instructions, the house collapses!
* **The Declarative Way (React)**: You draw a new blueprint of the house (**JSX**) and give it to the manager. You say: *"I want the wall to look like this blueprint."* The manager compares the new blueprint with the old house layout, finds exactly which bricks need changing (**Virtual DOM Diffing**), and swaps *only* those bricks. You don't direct the hammer; you just show the finished blueprint!

---

## 🔬 Let's Look Closer

### 1. Declarative vs. Imperative
* **Imperative (Vanilla JS)**: Step-by-step instructions. You manually find elements (`document.getElementById`), modify classes, and append nodes.
* **Declarative (React)**: You describe *what* the UI should look like based on the current state. React manages the underlying DOM changes.

### 2. The Virtual DOM & Reconciliation
The real browser DOM is slow to modify. React maintains a lightweight representation in memory called the **Virtual DOM**. When state changes:
1. React builds a new Virtual DOM tree representing the updated UI.
2. It compares this tree with the previous Virtual DOM tree (this comparison is called the **Diffing Algorithm**).
3. It calculates the minimum updates required and applies them to the real browser DOM in a single transaction (this is called **Reconciliation**).

### 3. JSX (JavaScript XML)
JSX is not valid HTML or JavaScript. It is a syntax extension that compiles down to standard JavaScript function calls. For example:
```jsx
// Write this in JSX:
const element = <h1 className="title">Hello World</h1>;

// Babel compiles it to this standard JavaScript:
const element = React.createElement('h1', { className: 'title' }, 'Hello World');
```

---

## 💻 Code Sandbox

Here is how a simple React application looks in a single file environment.

```jsx
import React from 'react';
import ReactDOM from 'react-dom/client';

// 1. Defining a declarative JSX element
const userName = "Alice";
const welcomeCard = (
    <div className="card">
        <h1>Welcome, {userName}!</h1>
        <p>This UI is generated declaratively using JSX.</p>
    </div>
);

// 2. Mounting and rendering the component to the real DOM
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(welcomeCard);
```

---

## 🧠 Points to Remember

* Browsers cannot understand JSX directly. Tools like **Babel** must compile JSX into `React.createElement()` calls before it reaches the browser.
* In JSX, you must use `className` instead of `class` and `htmlFor` instead of `for` because `class` and `for` are reserved keywords in JavaScript.
* Every JSX expression must return a single root element. If you don't want to add extra `<div>` wrappers to the DOM, you can group elements using a **Fragment** (`<React.Fragment>` or `<>...</>`).

---

## 📖 Key Definitions

* **Virtual DOM**: A lightweight, in-memory representation of the real DOM kept in sync by libraries like React.
* **JSX (JavaScript XML)**: A syntax extension for JavaScript that allows writing HTML-like structures directly inside JavaScript files.
* **Reconciliation**: The process through which React updates the real DOM by comparing the new Virtual DOM tree with the old one (diffing).
* **Declarative UI**: A programming paradigm where you describe *what* the UI should look like for a given state, rather than *how* to step-by-step change it.
* **Babel**: A JavaScript compiler used to transform modern JavaScript and JSX code into standard, backward-compatible JavaScript that browsers can execute.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is React?**
   * *Answer*: React is a declarative, component-based JavaScript library developed by Meta for building user interfaces.
2. **What is JSX?**
   * *Answer*: JSX stands for JavaScript XML. It is a syntax extension that allows developers to write HTML-like code inside JavaScript.
3. **What is the difference between React and vanilla JavaScript?**
   * *Answer*: Vanilla JS is imperative (you manually update the DOM). React is declarative (you declare the state, and React handles DOM updates automatically).
4. **What is the Virtual DOM?**
   * *Answer*: A lightweight, in-memory duplicate of the real browser DOM that React uses to compute and optimize UI updates.
5. **Why can't browsers read JSX directly?**
   * *Answer*: Because JSX is not standard JavaScript. It must be compiled by tools like Babel into standard ES5 JS calls.
6. **What is Babel's role in a React application?**
   * *Answer*: Babel compiles JSX code into standard `React.createElement()` function calls that browsers can parse and run.
7. **What is a React Fragment?**
   * *Answer*: A wrapper component (`<>...</>`) used to group multiple sibling JSX elements without adding extra nodes to the DOM.
8. **Why do we use `className` instead of `class` in JSX?**
   * *Answer*: Because JSX compiles to JavaScript, and `class` is a reserved keyword in ES6 JavaScript.
9. **Why must JSX expressions have a single parent root?**
   * *Answer*: Because JSX elements compile into a single function call (`React.createElement()`), which can only return a single JavaScript object.
10. **What is React's "reconciliation" process?**
    * *Answer*: The process of comparing the updated Virtual DOM with the old Virtual DOM, finding the differences, and patching the real DOM.
11. **Is React a framework or a library?**
    * *Answer*: React is a library because it only focuses on rendering the UI view layer, leaving routing, state management, and build setups to other tools.
12. **Who created React and when?**
    * *Answer*: React was created by Jordan Walke, a software engineer at Facebook (Meta), and was open-sourced in 2013.
13. **What is a single page application (SPA)?**
    * *Answer*: A web application that loads a single HTML page and dynamically updates content without reloading the page from the server.
14. **What does `ReactDOM.createRoot` do?**
    * *Answer*: It initializes a mount container point in the real HTML DOM where React will render its component tree.
15. **How do you write JavaScript comments inside JSX?**
    * *Answer*: By wrapping the comment inside curly braces: `{/* This is a comment */}`.
16. **How do you embed dynamic variables inside JSX?**
    * *Answer*: By enclosing the variable name inside curly braces: `<h1>Hello {name}</h1>`.
17. **What happens when the state of a React component changes?**
    * *Answer*: React automatically schedules a re-render of the component and its children, rebuilding the Virtual DOM.
18. **What does `React.createElement()` return?**
    * *Answer*: It returns a plain JavaScript object representing the DOM node type, properties, and child elements.
19. **What are components in React?**
    * *Answer*: Modular, reusable building blocks of code that return JSX to represent parts of the user interface.
20. **Can you write React without using JSX?**
    * *Answer*: Yes, by calling `React.createElement()` manually for every element, but it is verbose and hard to maintain.

### 🟡 Intermediate Questions (21-40)

21. **Explain the Diffing Algorithm used in React's Virtual DOM.**
    * *Answer*: A heuristic $O(N)$ complexity algorithm that compares trees. It assumes: (1) Two elements of different types will produce different trees, and (2) Developers can hint which child elements are stable using `key` properties.
22. **What is component co-location, and why is it a best practice in React?**
    * *Answer*: Keeping component styles, tests, and assets in the same folder as the component file, keeping code self-contained and easy to find.
23. **What is the difference between Element and Component in React?**
    * *Answer*: A React Element is a plain object describing a DOM node (e.g. `<h1>`). A React Component is a reusable function or class that returns Elements.
24. **Explain how Babel compiles `<Welcome name="Sara" />` under the hood.**
    * *Answer*: It compiles to: `React.createElement(Welcome, { name: "Sara" })`.
25. **Why is modifying the real DOM slow compared to the Virtual DOM?**
    * *Answer*: Modifying the real DOM triggers browser layout reflows and repaint operations. The Virtual DOM is a simple JS object, so operations run in memory without touching screen rendering.
26. **What is the "single directional data flow" in React?**
    * *Answer*: A design pattern where data flows only downward from parent components to child components via props, making data state tracking easy to debug.
27. **What is React StrictMode, and what is its purpose?**
    * *Answer*: A wrapper component used in development to detect unsafe lifecycles, unexpected side effects, and deprecated API usages by intentionally double-rendering components.
28. **How does React compile style attributes in JSX?**
    * *Answer*: Style attributes are passed as JavaScript objects rather than strings: `style={{ color: 'red', fontSize: '12px' }}` (camelCased properties).
29. **What is the difference between Declarative programming and Imperative programming?**
    * *Answer*: Imperative defines *how* to do things step-by-step. Declarative defines *what* the target result should be, hiding the step-by-step implementation.
30. **What is the "root node" in a React application?**
    * *Answer*: The primary HTML element (usually `<div id="root"></div>`) that wraps the entire React component hierarchy inside the browser.
31. **Explain how React handles event handling compared to standard HTML.**
    * *Answer*: React uses Synthetic Events, camelCase names (e.g. `onClick` instead of `onclick`), and passes function references instead of string functions.
32. **What is a compiler like Vite or Webpack doing in React?**
    * *Answer*: Bundling code, optimizing assets, transpile-matching JSX with Babel, and reloading the browser instantly during edits (Hot Module Replacement).
33. **Explain the rules of naming React components.**
    * *Answer*: React components must start with an uppercase letter (PascalCase). If a component name starts with lowercase, React treats it as a standard HTML element.
34. **Does React support Server-Side Rendering (SSR)?**
    * *Answer*: Yes, through frameworks like Next.js, or by converting components to HTML strings on the server using `ReactDOMServer`.
35. **What is the difference between client-side routing and server-side routing?**
    * *Answer*: Server-side loads a new HTML page on every URL request. Client-side routing intercepts requests and replaces page components in place without reloading the page.
36. **Explain what JSX attributes compile to.**
    * *Answer*: They compile to property keys (props) in the configuration object parameter of the `React.createElement(type, config, ...children)` call.
37. **Can you return multiple fragments in a single React component?**
    * *Answer*: No, a component must return exactly one root element or fragment, but that fragment can contain nested fragments inside it.
38. **Explain what a shadow DOM is and how it differs from a Virtual DOM.**
    * *Answer*: Shadow DOM is a browser feature used to isolate styles and components (Web Components). Virtual DOM is a JavaScript library implementation used for fast rendering changes.
39. **What happens if you define a component inside another component's render loop?**
    * *Answer*: A performance anti-pattern. Every time the parent renders, the inner component type is recreated from scratch, destroying its DOM state and inputs.
40. **How can you prevent HTML rendering from executing scripts (XSS protection) in React?**
    * *Answer*: React automatically escapes all string variables embedded inside JSX before rendering them, preventing malicious script injections.

### 🔴 Advanced Questions (41-50)

41. **Explain the internal structure of a Fiber Node in React 16+ architecture.**
    * *Answer*: A Fiber node represents a component and its state. It contains keys like `child` (points to first child), `sibling` (points to adjacent sibling), `return` (points to parent), and `alternate` (used during double-buffering updates).
42. **Why does React's diffing algorithm assume that comparing two elements of different HTML types yields completely different trees?**
    * *Answer*: For performance. Comparing different node structures is computationally expensive ($O(N^3)$). React cuts comparisons short: if a `<div>` changes to a `<span>`, it immediately tears down the old tree and builds a new one.
43. **How does React implement Batching under the hood to prevent multiple renders?**
    * *Answer*: React uses a scheduler queue. When multiple state updates occur inside an event handler, React postpones updating the DOM, merges all state changes, and executes a single render pass at the end of the microtask queue.
44. **Explain how Babel transforms modern React JSX starting from React 17+ (The New JSX Transform).**
    * *Answer*: React 17+ does not compile to `React.createElement`. It compiles to `_jsx('h1', { children: 'Hello' })` imported automatically from `react/jsx-runtime`, eliminating the need to import `React` manually in every file.
45. **What is "Double Buffering" in React's Fiber layout?**
    * *Answer*: A graphics rendering pattern. React maintains two trees concurrently: the `current` tree (displayed on screen) and the `workInProgress` tree (built quietly in memory). Once the new tree is complete, React swaps the root pointer, showing changes instantly.
46. **What is "Concurrent Mode" in React 18, and how does it change rendering?**
    * *Answer*: A mode that allows React to pause, discard, or resume rendering updates. Instead of blocking the browser thread during large computations, React yields control to browser animations periodically.
47. **How does the Virtual DOM handle changes to deeply nested structures without crashing performance?**
    * *Answer*: By dirty-marking component branches. When a state update is triggered, React only runs diffing algorithms on the sub-tree branch under the component that triggered the state update, leaving the rest of the tree untouched.
48. **Explain what Hydration means in Server-Side Rendered (SSR) React apps.**
    * *Answer*: The process where the client-side JavaScript loads, scans the pre-rendered HTML sent by the server, binds event listeners to it, and attaches the in-memory Virtual DOM state without re-rendering the elements.
49. **Why is the Virtual DOM considered an abstraction over the DOM rather than a direct replacement?**
    * *Answer*: Because it allows writing declarative code targeting any environment. The same React Virtual DOM tree can compile to browser HTML (via `react-dom`), mobile native components (via `react-native`), or server strings.
50. **What is the performance implication of compiling massive JSX structures with deep layouts?**
    * *Answer*: High memory allocations. Every nested JSX node translates to a JS object instance. For huge DOM trees, this creates garbage collection pressure on the JS runtime, which is why modular components and pagination are used.

---

## ⏭️ Next Steps

* **Next Chapter**: [👉 Topic 02: Components & Props](02_components_props.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
