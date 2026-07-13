# 🚀 Topic 07: Performance & Memoization

Welcome, performance tuner! In this chapter, we will learn about **Performance Optimization and Memoization**. In React, components re-render automatically. While this guarantees the UI is always up to date, it can cause performance bottlenecks if expensive calculations or unnecessary child renders occur repeatedly. We will learn how to cache values (`useMemo`), cache function pointers (`useCallback`), and skip child updates (`React.memo`).

---

## 🏠 The Big Picture & Real-Life Example

### 📝 The Math Teacher's Cheat Sheet & The Call Center Memo
Imagine you are a busy math teacher grading papers:
* **The Normal Process (Re-rendering)**: A student asks: *"What is 342 multiplied by 879?"* You sit down, pull out a notepad, write out the steps, calculate the answer (300,618), and announce it. Two seconds later, another student asks the exact same question. If you repeat all calculation steps, you waste time!
* **`useMemo` (The Memoized Notepad)**: You calculate the result (300,618) once and write it on a sticky note (**`useMemo`**). When the next student asks, you look at the sticky note and answer instantly. You only recalculate if the numbers change.
* **`useCallback` (The Speed-Dial Button)**: You have a phone call speed-dial button to contact the principal (**`useCallback`**). Instead of printing a new phone number card and changing your speed-dial wires on every render, you lock the wire in place so the button points to the exact same principal phone line reference.
* **`React.memo` (The Smart Assistant)**: You have an assistant named Bob (**Child Component**). You ask him to print a report. If you render the parent page, but the report props did not change, you tell Bob: *"Don't print a new sheet, just show the last report you printed."*

---

## 🔬 Let's Look Closer

### 1. React.memo (Component Memoization)
`React.memo` is a higher-order component. It performs a shallow comparison of the component's props. If they are unchanged, React skips re-rendering it:
```jsx
const MyComponent = React.memo(function Child(props) {
    return <div>{props.value}</div>;
});
```

### 2. useMemo (Value Memoization)
`useMemo` caches the *result* of a calculation. It only recalculates the value when one of its dependencies changes:
```jsx
// Caches computed array filter output:
const filteredItems = useMemo(() => {
    return items.filter(item => item.price > minPrice);
}, [items, minPrice]);
```

### 3. useCallback (Function Memoization)
`useCallback` caches the *function definition* reference itself. It prevents child components that rely on referential equality from re-rendering:
```jsx
// Keeps function reference stable across renders:
const handleClick = useCallback(() => {
    console.log("Clicked!");
}, []); // Empty dependencies = function reference never changes
```

---

## 💻 Code Sandbox

Let's build a component demonstrating how `React.memo`, `useMemo`, and `useCallback` prevent unnecessary render iterations.

```jsx
import React, { useState, useMemo, useCallback } from 'react';

// 1. Child component wrapped in React.memo (Skips re-rendering if props are identical)
const HeavyChild = React.memo(function HeavyChild(props) {
    console.log("-> [Render] HeavyChild component!");
    return (
        <div style={{ border: '1px solid blue', padding: '10px', marginTop: '10px' }}>
            <h3>Heavy Child (Memoized)</h3>
            <button onClick={props.onAction}>Execute Custom Callback Action</button>
        </div>
    );
});

// 2. Parent Component
export default function PerformanceDashboard() {
    const [count, setCount] = useState(0);
    const [searchTerm, setSearchTerm] = useState("");

    // 3. useMemo: Caches expensive calculation results
    const expensiveCalculation = useMemo(() => {
        console.log("=> Running expensive calculation loop...");
        let sum = 0;
        for (let i = 0; i < 1000000; i++) {
            sum += i;
        }
        return sum;
    }, []); // Empty dependencies = calculate only once on mount

    // 4. useCallback: Caches function reference to prevent HeavyChild from rendering
    const handleChildAction = useCallback(() => {
        console.log("Child callback executed!");
    }, []); // Stable reference

    return (
        <div style={{ padding: '20px' }}>
            <h2>Performance Control Center</h2>
            <p>Calculated Sum: <strong>{expensiveCalculation}</strong></p>
            <p>Parent Counter: {count}</p>
            <button onClick={() => setCount(prev => prev + 1)}>Increment Counter</button>
            
            <div style={{ marginTop: '15px' }}>
                <label>Filter Search: </label>
                <input 
                    type="text" 
                    value={searchTerm} 
                    onChange={(e) => setSearchTerm(e.target.value)} 
                />
            </div>

            {/* HeavyChild only renders on mount because props (handleChildAction) are stable */}
            <HeavyChild onAction={handleChildAction} />
        </div>
    );
}
```

---

## 🧠 Points to Remember

* **Don't use memoization everywhere**. Caching has overhead. React must allocate memory for closures and run dependency comparison checks. For simple components and light calculations, memoization actually slows down the application.
* **Referential equality** is key. In JavaScript, `{}` !== `{}` and `() => {}` !== `() => {}`. If you wrap a child in `React.memo`, but pass an inline array or function prop, the child will re-render anyway. You must use `useCallback` or `useMemo` to keep references stable.
* Every variable used inside `useMemo` or `useCallback` must be listed in its dependency array.

---

## 📖 Key Definitions

* **React.memo**: A higher-order component used to skip re-rendering a component if its props have not changed.
* **useMemo Hook**: A built-in hook used to cache (memoize) the computed result of an expensive calculation across render cycles.
* **useCallback Hook**: A built-in hook used to cache the memory reference of a function definition across render cycles.
* **Referential Equality**: A comparison check in JavaScript that verifies if two variables point to the exact same location in memory.
* **Shallow Comparison**: A comparison check that only verifies equality of primitive values and object memory pointers, rather than deeply comparing keys.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is memoization in React?**
   * *Answer*: A performance optimization technique that stores computed results or function definitions in memory, returning the cached copy when inputs don't change.
2. **What does the `useMemo` hook do?**
   * *Answer*: It caches (memoizes) the returned value of a function, recalculating it only when dependencies change.
3. **What does the `useCallback` hook do?**
   * *Answer*: It caches the memory reference of a function definition, preventing it from being recreated on every render.
4. **What is `React.memo`?**
   * *Answer*: A higher-order component used to optimize functional components by skipping re-renders when props do not change.
5. **How does `useCallback` differ from `useMemo`?**
   * *Answer*: `useMemo` caches the **result** of calling a function. `useCallback` caches the **function definition** reference itself.
6. **What is a shallow comparison?**
   * *Answer*: A comparison that checks if primitive values are equal, and checks if reference variables point to the same object address in memory.
7. **What is referential equality in JavaScript?**
   * *Answer*: The rule that objects, arrays, and functions are compared by their memory reference pointers rather than their inner keys.
8. **Why do inline functions passed as props cause child re-renders?**
   * *Answer*: Because inline functions are recreated on every render, yielding a new memory pointer that fails props comparison checks.
9. **What happens if the dependency array of `useMemo` is empty `[]`?**
   * *Answer*: The calculation runs once on mount, and the cached value is returned for all future renders.
10. **What is the consequence of not using a dependency array in `useMemo`?**
    * *Answer*: The function will run and recalculate the value on every single render, rendering the hook useless.
11. **Does `React.memo` optimize state updates?**
    * *Answer*: No, `React.memo` only checks prop changes. If a component's internal state updates, it will always re-render.
12. **What does "expensive calculation" mean in React profiling?**
    * *Answer*: A CPU-heavy operation (like looping through millions of items, sorting large datasets, or generating cryptographic keys) that slows down rendering.
13. **Why shouldn't you wrap every component in `React.memo`?**
    * *Answer*: Because running comparison checks consumes CPU. If props change frequently, the comparison runs *plus* the render runs, degrading speed.
14. **How do you set dependencies for `useCallback`?**
    * *Answer*: By listing any variables from the component scope (state, props) that are referenced inside the callback function.
15. **What is the default behavior of React regarding component re-renders?**
    * *Answer*: By default, whenever a parent component updates, all of its child components re-render recursively.
16. **How does `React.memo` handle object props?**
    * *Answer*: It checks them using shallow comparison (`===`). If a new object reference is passed, it forces a re-render even if the keys match.
17. **What is the return value of `useCallback(fn, deps)`?**
    * *Answer*: It returns the memoized function reference `fn`.
18. **Can you use hooks inside `React.memo`?**
    * *Answer*: Yes, `React.memo` wraps the component definition. The component inside can still use hooks normally.
19. **What is the return value of `useMemo(fn, deps)`?**
    * *Answer*: It returns the memoized value returned by the execution of `fn`.
20. **Can you customize the props comparison logic in `React.memo`?**
    * *Answer*: Yes, by passing a custom comparison function as the second argument: `React.memo(Component, (prevProps, nextProps) => boolean)`.

### 🟡 Intermediate Questions (21-40)

21. **Explain the structural difference under the hood: `useCallback(fn, deps)` vs `useMemo(() => fn, deps)`.**
    * *Answer*: They are functionally identical. `useCallback` is syntactic sugar for returning a function from `useMemo`.
22. **Why does a component wrapped in `React.memo` still re-render when a context value it consumes updates?**
    * *Answer*: Because context consumption overrides `React.memo`. When context changes, React schedules renders for all consuming nodes regardless of prop states.
23. **What is the performance implication of declaring custom functions inside a parent component that renders a list?**
    * *Answer*: Every list item receives a new function instance, causing all list items to re-render. Resolved by wrapping the function in `useCallback`.
24. **Explain how `useMemo` can be used to prevent unnecessary API fetches or subscriptions.**
    * *Answer*: By memoizing config options objects: `const options = useMemo(() => ({ headers }), [headers])` to ensure stable references.
25. **What is "Over-optimization" in React, and how does it manifest?**
    * *Answer*: Applying memoization to simple components (like `<button>{label}</button>`). It increases memory usage and overhead without showing any profile speedups.
26. **How does the custom comparison function in `React.memo` compare to `shouldComponentUpdate` in class components?**
    * *Answer*: They check similarly. However, `shouldComponentUpdate` returns `true` to **allow** rendering; `React.memo`'s custom function returns `true` to **skip** rendering.
27. **Why does mutating a state object directly break `useMemo` tracking?**
    * *Answer*: Because the reference stays the same. `useMemo` checks dependencies by reference pointer. If you mutate a key, the pointer stays equal, skipping recalculation.
28. **Explain the relationship between garbage collection and memoization in SPA applications.**
    * *Answer*: Memoized objects and functions are held in memory. If not managed, large arrays cached in hooks prevent garbage collectors from reclaiming memory, causing bloat.
29. **How would you verify if a component re-render was caused by prop reference changes?**
    * *Answer*: Using the React DevTools Profiler, checking the "Why did this render?" audit tab, or writing a custom hook to log prop changes.
30. **Explain how child components can use stable state setters without `useCallback`.**
    * *Answer*: State setters returned by `useState` (e.g. `setCount`) are guaranteed by React to have stable references, so they don't need `useCallback` wraps.
31. **What is the difference between shallow equality and deep equality checks in custom `React.memo` wrappers?**
    * *Answer*: Shallow equality only checks top-level keys (`===`). Deep equality (like Lodash `isEqual`) compares nested arrays/objects, which is expensive on large objects.
32. **Can `useMemo` be used to run side effects?**
    * *Answer*: No. `useMemo` runs during the rendering phase. Running side effects there violates React's pure rendering constraints. Side effects belong in `useEffect`.
33. **Why does React not guarantee that memoized values are never discarded?**
    * *Answer*: In memory-deficient environments (like low-end mobile browsers), React may release cached memo values to free up RAM, recalculating them on next render.
34. **How does `useCallback` help resolve problems with `useEffect` dependency loops?**
    * *Answer*: If an effect calls a function, the function must be in dependencies. If the function is recreated on every render, it triggers the effect in a loop. Wrapping it in `useCallback` stabilizes the reference.
35. **What is the difference between `React.memo` and `React.PureComponent`?**
    * *Answer*: `React.PureComponent` is used for Class Components. `React.memo` is a Higher-Order Component used for Functional Components.
36. **Explain what happens to memoized values during a hot reload in development.**
    * *Answer*: Dev servers usually clear hook caches during Hot Module Replacement (HMR) to ensure code modifications are compiled and shown immediately.
37. **Can a component wrapped in `React.memo` receive a React Element as a prop without rendering issues?**
    * *Answer*: Yes, but if the React Element is constructed inline (e.g. `<Card icon={<Icon />} />`), it creates a new element object on every render, bypassing `React.memo` checks.
38. **How does the cost of Virtual DOM reconciliation compare to the cost of running prop checks?**
    * *Answer*: For light trees, reconciliation is faster. For massive component trees with nested DOM branches, running prop checks with `React.memo` is significantly faster.
39. **Explain how to optimize a component that calculates averages of a transaction history array.**
    * *Answer*: Wrap the math logic in `useMemo` dependent on the history array: `useMemo(() => calcAverage(history), [history])`.
40. **Does `React.memo` prevent child components from updating if they consume context variables directly?**
    * *Answer*: No, context consumption bypasses prop checks. The component will still update when context values change.

### 🔴 Advanced Questions (41-50)

41. **Explain the structural representation of memoized values inside the Fiber Node's hook list.**
    * *Answer*: For `useMemo`, the fiber's hook node stores a tuple: `[value, dependencies]` inside its `memoizedState` field, compared with incoming dependencies on render.
42. **Why does React's concurrent rendering architecture require functional updates to be idempotent when working with `useMemo`?**
    * *Answer*: Because Concurrent React can abort, pause, or recalculate render branches. If `useMemo` calculations modify external variables, double-rendering causes inconsistent state.
43. **How does `useCallback` interact with closures to capture variables in their lexical scope?**
    * *Answer*: When `useCallback` is declared, it captures variables from that specific render cycle. If dependency arrays are incorrect, the cached function will execute using stale variables from the captured closure.
44. **Explain how the virtual reconciler optimizes trees during static hoisting and how this interacts with `React.memo`.**
    * *Answer*: Compilers like Vite can hoist static JSX nodes outside the render loop. Since the node reference never changes, React skips diffing and prop checks automatically.
45. **What is the memory and CPU tradeoff of using a custom deep-comparison prop check inside `React.memo`?**
    * *Answer*: Deep checks are $O(N)$ based on object size and depth. For large objects, traversing keys consumes more CPU cycles than rebuilding the lightweight Virtual DOM nodes.
46. **How does the lane prioritization scheduler in React 18 manage rendering updates when memoized calculations take a long time?**
    * *Answer*: React can break rendering into frames. If a memoized calculation is marked as a low-priority transition, React pauses execution to handle urgent inputs before returning to the calculation.
47. **Explain how to design an immutable data architecture that maximizes referential stability for memoized React components.**
    * *Answer*: Use patterns like structural sharing. When updating a nested key, copy only parent references down to that key, keeping sibling references stable.
48. **Why does Concurrent React's double-buffering render pass make mutating refs inside `useMemo` a critical anti-pattern?**
    * *Answer*: Because if React pauses rendering a branch, it discards the `workInProgress` tree. If you mutated a ref inside the render phase, the change is written, but the UI is rolled back, causing desynchronization.
49. **How would you profile and locate a memory leak caused by a stale memoized cache inside a production React bundle?**
    * *Answer*: Using Chrome DevTools Memory profiling. Record heap snapshots, filter by Fiber hook lists, and look for large retained arrays mapped to `memoizedState` of unmounted nodes.
50. **Explain how Server Components bypass the need for client-side memoization hooks like `useMemo` and `useCallback`.**
    * *Answer*: Server Components render once on the server, outputting static HTML/JSON. Since they do not run update loops in the client browser, memoization hooks are useless.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 06: Forms & Event Handling (useRef)](06_forms_useref.md)
* **Next Chapter**: [👉 Topic 08: Context API](08_context_api.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
