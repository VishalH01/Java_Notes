# 🚦 Topic 03: State Management (useState)

Welcome, state manager! In this chapter, we will learn about **State Management using the `useState` hook**. While Props are read-only configuration inputs passed from the outside, State is a component's private, internal memory. We will learn how to declare state variables, trigger UI re-renders, and understand how React batches updates for speed.

---

## 🏠 The Big Picture & Real-Life Example

### 📝 The Magic Sketchpad
Imagine you have an interactive sketchpad hanging on your wall:
* **Props (Static frame)**: The physical wooden border of the sketchpad is brown. It was built this way, and you cannot change it from inside the sketchpad (**Props**).
* **State (The Drawing Canvas)**: The canvas has a counter display showing the number of drawings. When you click a button, the number changes from 1 to 2. This number is **State**—it is local, private memory.
* **The Render Loop (Clearing the screen)**: In vanilla JS, changing the number means taking an eraser, rubbing out the old digit, and drawing the new one. In React, when the counter state changes, the canvas automatically wipes itself clean and redraws the entire screen instantly using the updated state value!
* **Batching (The Delivery Boy)**: If you type 3 letters quickly on the sketchpad, the manager doesn't run to the store 3 times to buy new canvas papers. He waits until you finish typing the word, and then delivers one fresh sheet showing all 3 letters at once (**State Batching**).

---

## 🔬 Let's Look Closer

### 1. Declaring State with `useState`
The `useState` hook is a function that returns an array with exactly two elements:
1. The **current state value**.
2. A **setter function** used to update the state value and trigger a re-render.
```jsx
import { useState } from 'react';

const [count, setCount] = useState(0); // 0 is the initial state value
```

### 2. Updating State & The Async Nature
Updating state does not change the variable in the current code execution block immediately. It schedules a update for the next render.
```jsx
// Incorrect:
setCount(count + 1);
console.log(count); // Will still show the OLD value!
```

### 3. Functional Updates
If your next state depends on the previous state, you should pass a callback function to the setter to ensure you are reading the freshest state:
```jsx
// Correct:
setCount(prevCount => prevCount + 1);
```

### 4. State Batching
React groups multiple state updates inside the same event handler into a single re-render cycle. This prevents the browser from running unnecessary layout paints.

---

## 💻 Code Sandbox

Let's write an interactive counter component.

```jsx
import React, { useState } from 'react';

export default function Counter() {
    // 1. Declare state variable "count", initialized to 0
    const [count, setCount] = useState(0);

    // 2. Event handler functions
    const handleIncrement = () => {
        setCount(prev => prev + 1); // Using functional update
    };

    const handleDecrement = () => {
        if (count > 0) {
            setCount(prev => prev - 1);
        }
    };

    const handleReset = () => {
        setCount(0); // Simple state update
    };

    return (
        <div style={{ padding: '20px', textAlign: 'center' }}>
            <h1>Counter: {count}</h1>
            
            {/* Click handlers calling state setters */}
            <button onClick={handleDecrement} style={{ margin: '5px' }}>- Decrease</button>
            <button onClick={handleReset} style={{ margin: '5px' }}>Reset</button>
            <button onClick={handleIncrement} style={{ margin: '5px' }}>+ Increase</button>
        </div>
    );
}
```

---

## 🧠 Points to Remember

* **Never mutate state directly**. Writing `count = count + 1` or modifying an array `myArray.push(x)` directly does not notify React, meaning no re-render will occur. Always use the setter function and pass new object references.
* Hooks can only be called at the **top level** of your functional components. You cannot call `useState` inside loops, conditionals (`if` statements), or nested functions.
* When updating state with objects or arrays, you must spread the old state values to preserve them:
  ```jsx
  setUser(prev => ({ ...prev, name: "New Name" }));
  ```

---

## 📖 Key Definitions

* **Component State**: An object or variable managed internally within a component that stores local data that can change over time.
* **useState Hook**: A built-in React hook that allows functional components to declare and update local state variables.
* **State Batching**: A performance optimization where React groups multiple state updates into a single re-render cycle.
* **Functional Update**: An update pattern where you pass a function to the state setter that receives the previous state value to compute the next state.
* **Event Callback**: A function executed in response to a user action (e.g. click, input change) that usually modifies the component's state.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is State in React?**
   * *Answer*: State is a built-in React object used to store data local to a component that can change over time, triggering a UI re-render on update.
2. **What is the `useState` hook?**
   * *Answer*: A built-in hook that allows functional components to declare and manage local state variables.
3. **What does `useState` return?**
   * *Answer*: It returns an array of two elements: the current state value and a setter function to update that state.
4. **Why shouldn't we update state directly (e.g. `state = 'new'`)?**
   * *Answer*: Because mutating the state variable directly does not trigger a re-render. You must call the setter function to notify React to render changes.
5. **Are state updates synchronous or asynchronous?**
   * *Answer*: State updates are asynchronous. React schedules them to optimize rendering performance, so you cannot read the updated value immediately after setting it.
6. **What is the rules of hooks in React?**
   * *Answer*: Hooks must only be called: (1) at the top level of a component (not inside loops or ifs), and (2) only from React functional components or custom hooks.
7. **How do you initialize state in `useState`?**
   * *Answer*: By passing the initial value as the argument to the hook: `useState(initialValue)`.
8. **What happens to state when a component unmounts?**
   * *Answer*: The state of that component is destroyed and cleared from the browser's memory.
9. **Can a component have multiple `useState` hooks?**
   * *Answer*: Yes, you can declare as many state variables as needed inside a single component.
10. **What is the difference between props and state?**
    * *Answer*: Props are passed into a component from the outside (immutable). State is created and managed internally within the component (mutable).
11. **How do you update state based on its previous value?**
    * *Answer*: By passing a callback function to the state setter: `setCount(prev => prev + 1)`.
12. **Can you pass state as a prop to a child component?**
    * *Answer*: Yes, you can pass state variables (and state setters) as props to child components.
13. **What value does a state variable have if no initial value is passed to `useState`?**
    * *Answer*: It defaults to `undefined`.
14. **What is State Batching?**
    * *Answer*: React's performance optimization where multiple state updates within the same execution cycle are merged into a single re-render.
15. **How do you update an array in state?**
    * *Answer*: By creating a new array copy (using spread operators) and adding the new item: `setList(prev => [...prev, newItem])`.
16. **How do you update an object in state?**
    * *Answer*: By spreading the old object and overwriting the changed fields: `setUser(prev => ({ ...prev, age: 30 }))`.
17. **Can we use `useState` inside a Class Component?**
    * *Answer*: No, hooks are exclusive to functional components. Class components use `this.state` and `this.setState()`.
18. **Why does React re-render when state changes?**
    * *Answer*: Because state updates mark the component as "dirty", prompting React to rebuild its Virtual DOM and sync the changes with the browser.
19. **What is a controlled input?**
    * *Answer*: An input element whose value is driven by React state, and changes are caught via `onChange` events to update state.
20. **Can you set a function as the initial value in `useState`?**
    * *Answer*: Yes, this is called **Lazy Initialization**. React will execute the function only once during the initial mount.

### 🟡 Intermediate Questions (21-40)

21. **What is Lazy State Initialization, and when should you use it?**
    * *Answer*: Passing a function to `useState` (e.g. `useState(() => computeHeavyData())`). Used when calculating the initial state requires expensive operations (like reading local storage) to avoid running it on every render.
22. **Explain the behavior of state updates inside async blocks (like setTimeout or fetch calls) in React 18.**
    * *Answer*: In React 18, all state updates—including those inside setTimeout, promises, and native event listeners—are batched automatically (Automatic Batching).
23. **What is the consequence of updating state with the exact same value?**
    * *Answer*: React checks equality using `Object.is()`. If the new state value is identical to the current value, React skips (bails out of) rendering updates entirely.
24. **Why is mutating arrays directly (e.g., `list.push(5)`) followed by `setList(list)` a bad idea in React?**
    * *Answer*: Because `list` is a reference type. React compares references; since the array pointer did not change, React assumes the state is identical and skips rendering.
25. **How does React distinguish between different state variables declared via multiple `useState` calls?**
    * *Answer*: React relies on call order. It maintains an internal array of state hooks for the active component, mapping index slots to the exact sequence hooks are called.
26. **Explain what a functional update is and why it prevents stale closure bugs.**
    * *Answer*: Passing a function: `setCount(c => c + 1)`. Since it receives the fresh state parameter at execution time, it bypasses values captured during render closures.
27. **What is the difference between controlled and uncontrolled components?**
    * *Answer*: Controlled components have their data bound to React state. Uncontrolled components let the DOM handle form inputs directly, retrieved using `useRef`.
28. **How can you trigger a forced re-render of a component without changing its data?**
    * *Answer*: By introducing a toggle boolean or counter state variable that you increment solely to force a render: `const [, forceUpdate] = useState(0);`.
29. **What is the cost of storing excessive data in component state?**
    * *Answer*: High memory usage and slow rendering. If a large object changes, React diffs the entire layout. Non-UI values should be stored in refs instead.
30. **Explain how state updates are scheduled in React's event loop.**
    * *Answer*: State updates are batched as microtasks, resolving at the end of the current synchronous execution loop before the browser repaints the layout.
31. **What is the difference between `setState` in Class Components and the setter function in `useState`?**
    * *Answer*: Class `setState` merges object properties automatically. The `useState` setter function *replaces* the state value completely, requiring manual spreads.
32. **Can you call a state setter function inside the render block of a component?**
    * *Answer*: Yes, but only inside a conditional check. Otherwise, it triggers an infinite render loop that crashes the application with a "Too many re-renders" error.
33. **How does React handle state persistence across browser refreshes?**
    * *Answer*: It doesn't. Component state is stored in active JS memory. To persist data across refreshes, you must synchronize state manually with `localStorage`.
34. **Why can't we declare hooks conditionally?**
    * *Answer*: Because React relies on the exact execution sequence of hooks to match state indexes. Conditionals alter the call order, mismatching state values.
35. **What is the difference between `useState` and declaring a local variable inside the component function?**
    * *Answer*: Local variables are redeclared and reset on every single render cycle. State variables persist their data values across renders.
36. **Explain what is meant by "derived state" and why it is preferred over duplicate state variables.**
    * *Answer*: Derived state is calculated on the fly during render (e.g. `const doubleCount = count * 2`). It avoids duplicate state sync bugs.
37. **What happens if you trigger state updates in a child component during its mount phase?**
    * *Answer*: If done inside event handlers, it works. If triggered synchronously during rendering, it throws a warning: "Cannot update a component while rendering a different component."
38. **How does React handle state changes when a component key changes?**
    * *Answer*: Changing a component's `key` prop forces React to treat it as a brand-new component, destroying its old DOM nodes and resetting its state.
39. **Explain how to update nested state objects efficiently (e.g. 3 levels deep).**
    * *Answer*: By using nested spread operators: `setObj(prev => ({ ...prev, level1: { ...prev.level1, level2: newValue } }))`, or using libraries like Immer.
40. **How would you pass a state setter function to deeply nested children without prop drilling?**
    * *Answer*: By using the Context API to share the setter function globally, allowing any consumer child to call it.

### 🔴 Advanced Questions (41-50)

41. **Explain the internal implementation of hooks inside React's source code (The Linked List of Hooks).**
    * *Answer*: React stores hooks on the active Fiber node as a singly linked list. Each hook node has a `memoizedState` (stores value), `queue` (pending updates), and `next` pointer pointing to the next hook in sequence.
42. **Why does React StrictMode invoke state initializer functions twice during development, and does this happen in production?**
    * *Answer*: To detect side effects. Pure state initializers should be idempotent. Double-calling them exposes bugs early. This behavior is stripped in production.
43. **Explain how the scheduling queue transitions state from the render phase to the commit phase in React's Fiber architecture.**
    * *Answer*: When a setter is called, React appends an update object to the hook's queue. During the next render phase, React traverses this linked list of updates, calculates the final state value, and schedules a commit update.
44. **What is "State Splitting" and how does it affect component profiling during CPU-bound updates?**
    * *Answer*: The practice of breaking large state objects into individual primitives (`useState(a)`, `useState(b)`). It allows React to isolate updates, minimizing diffing scopes.
45. **How does Concurrent React prioritize state updates (Transition API / startTransition)?**
    * *Answer*: React splits updates into: (1) **Urgent** (typing, clicking), and (2) **Non-Urgent transitions** (filtering lists). Non-Urgent transitions are ran in the background, allowing user input to interrupt them.
46. **Explain the difference in update batching behavior between React 17 (Legacy) and React 18 (Concurrent).**
    * *Answer*: React 17 only batched updates inside React event handlers. Updates in promises or setTimeout ran independently. React 18 batches *all* updates automatically.
47. **What is the structural sharing optimization in React state updates, and how does it prevent garbage collection overhead?**
    * *Answer*: By spreading objects (`{...prev}`), unchanged nested references are reused. This minimizes the creation of new JS objects, reducing memory allocations and GC pauses.
48. **How does React's fiber scheduler handle conflicting state updates from multiple sources (e.g. user input vs Web Socket stream)?**
    * *Answer*: By assigning lane priorities. Each update is assigned a bitmask lane. User input gets high-priority lanes, while server data streams get lower-priority lanes, processed sequentially.
49. **Explain how the `useReducer` hook operates as an alternative to `useState` for complex state transitions.**
    * *Answer*: `useReducer` decouples state updates from components. It takes a state and an action, running them through a pure reducer function, which is cleaner for nested state.
50. **What is the risk of utilizing global state variables (e.g. window object) to bypass useState in concurrent React?**
    * *Answer*: **Tearing**. In concurrent rendering, React pauses rendering. If a global variable changes mid-render, different parts of the screen will show conflicting values. `useState` prevents this.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 02: Components & Props](02_components_props.md)
* **Next Chapter**: [👉 Topic 04: Side Effects & useEffect](04_useeffect_side_effects.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
