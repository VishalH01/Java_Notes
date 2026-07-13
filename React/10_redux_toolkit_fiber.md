# 🏆 Topic 10: Redux Toolkit & Fiber

Welcome to the final chapter of your React JS journey, enterprise developer! In this chapter, we will learn about **Redux Toolkit, React Fiber, and Error Boundaries**. As applications grow, managing state across hundreds of pages using Context becomes slow and hard to debug. Redux Toolkit solves this by establishing a central data warehouse. We will also look under the hood at how React schedules updates (Fiber) and how to catch runtime crashes using Error Boundaries.

---

## 🏠 The Big Picture & Real-Life Example

### 🏭 The Central Corporate Warehouse & The Shock Absorber
Imagine you are managing a global online shop:
* **Redux Store (The Central Warehouse)**: Instead of every local shop drawer storing its own products and money (local state) or sending couriers back and forth (Context), you build one massive central warehouse (**Redux Store**).
  * **Actions (The Delivery Orders)**: To update the inventory, you send a standardized written delivery slip called an **Action**: `[DELIVER_ITEM, ID: 5]`.
  * **Reducers (The Warehouse Robots)**: The robots receive the order slip, look at the shelves, and update the stock logs. Redux Toolkit lets robots edit virtual shelves easily using **Slices**.
  * **Selectors (The Video Cameras)**: Every shop department points a security camera (**Selector**) at a specific shelf to track inventory in real-time.
* **Error Boundaries (The Fire Sprinkler System)**: A small candle catches fire in the kitchen. Instead of letting the fire burn down the entire building (the app crashing to a blank white screen), a local sprinkler system (**Error Boundary**) activates. It shuts down the kitchen and displays a sign: *"Kitchen Closed for Maintenance,"* keeping the rest of the store running.
* **React Fiber (The Assembly Line Coordinator)**: The factory has a coordinator who schedules tasks. If a VIP order arrives, he pauses standard box packing, prints the VIP shipping label, and then returns to packing boxes (**Incremental Rendering**).

---

## 🔬 Let's Look Closer

### 1. Redux Toolkit (RTK)
Redux is a predictable state container. Redux Toolkit (RTK) simplifies Redux setups:
* **Store**: The single source of truth that holds the global application state.
* **Slices**: Functions that bundle action creators and reducers together for a specific feature (e.g. `cartSlice`). RTK uses the **Immer** library internally, allowing you to write "mutating" code (like `state.push()`) safely because Immer translates it into immutable copy operations.
* **Thunks**: Middleware used to write asynchronous logic (like API fetches) that dispatch standard actions on completion.

### 2. Error Boundaries
An Error Boundary is a class component that implements `static getDerivedStateFromError()` (to render a fallback UI) or `componentDidCatch()` (to log errors). It acts as a try-catch block for JSX.

### 3. React Fiber Reconciler
React Fiber is a complete rewrite of the reconciliation algorithm. It supports:
* **Incremental Rendering**: Breaking rendering work into smaller chunks and spreading it over multiple browser frames to keep animations smooth.
* **Priority Assignment**: Assigning lanes of priority to updates (e.g., user clicks get top priority; background queries get low priority).

---

## 💻 Code Sandbox

Let's look at how to declare a Redux Toolkit Slice, store config, and a standard Error Boundary.

### 1. Declaring a Redux Toolkit Slice
```javascript
import { createSlice, configureStore } from '@reduxjs/toolkit';

// Define a slice for counter state
const counterSlice = createSlice({
    name: 'counter',
    initialState: { value: 0 },
    reducers: {
        increment: (state) => {
            // Safe to "mutate" state directly! Immer handles it.
            state.value += 1;
        },
        decrement: (state) => {
            state.value -= 1;
        }
    }
});

// Export actions and reducer
export const { increment, decrement } = counterSlice.actions;

// Configure global store
export const store = configureStore({
    reducer: {
        counter: counterSlice.reducer
    }
});
```

### 2. Declaring a React Error Boundary
```jsx
import React from 'react';

export class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false };
    }

    // 1. Update state so next render shows fallback UI
    static getDerivedStateFromError(error) {
        return { hasError: true };
    }

    // 2. Log the error to a service
    componentDidCatch(error, errorInfo) {
        console.error("ErrorBoundary caught an error:", error, errorInfo);
    }

    render() {
        if (this.state.hasError) {
            // Render custom fallback UI
            return <h2>Something went wrong. Please refresh the page.</h2>;
        }
        return this.props.children;
    }
}
```

---

## 🧠 Points to Remember

* **Functional components cannot be Error Boundaries**. React still requires you to use Class Components to implement `getDerivedStateFromError` or `componentDidCatch`.
* **Redux state is immutable**. Never mutate state outside of Redux Toolkit Slices. Inside slices, Immer handles immutability for you.
* **Error Boundaries do not catch all errors**. They do not catch errors inside: async code (like setTimeout or fetch), event handlers (like onClick), or server-side rendering.

---

## 📖 Key Definitions

* **Redux Toolkit (RTK)**: The official, opinionated toolset for efficient Redux development, simplifying store setup and state mutation logic.
* **Redux Slice**: A collection of Redux reducer logic and actions defined together for a specific feature of your application state.
* **Redux Thunk**: A middleware that allows you to write action creators that return a function instead of an action, used for async logic.
* **Error Boundary**: A React component that catches JavaScript errors anywhere in its child component tree, logging them and displaying a fallback UI.
* **React Fiber**: The core reconciliation engine in React 16+ that supports incremental rendering of the virtual DOM.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is Redux?**
   * *Answer*: A predictable state container for JavaScript apps, used to manage global state centrally in large applications.
2. **What is Redux Toolkit (RTK)?**
   * *Answer*: The official, recommended opinionated toolset designed to simplify writing Redux code by removing boilerplate.
3. **What is a Redux Store?**
   * *Answer*: The single, centralized database object that holds the entire state tree of your application.
4. **What is an Action in Redux?**
   * *Answer*: A plain JavaScript object that describes a state mutation intent, containing a mandatory `type` property.
5. **What is a Reducer?**
   * *Answer*: A pure function that takes the current state and an action, and returns the next state.
6. **What is a Slice in Redux Toolkit?**
   * *Answer*: A collection of Redux reducer logic and actions defined together for a specific feature (like cart, user).
7. **What is the role of the Immer library inside RTK?**
   * *Answer*: It allows developers to write mutable-style state updates (e.g. `state.push()`), translating them behind the scenes into immutable copy operations.
8. **What are selectors in Redux?**
   * *Answer*: Functions used to extract and read specific slices of state from the global Redux store (e.g. `state => state.counter.value`).
9. **What is Redux Thunk?**
   * *Answer*: A middleware that allows you to write action creators that return a function (usually containing async fetches) instead of a simple action object.
10. **What is an Error Boundary in React?**
    * *Answer*: A class component that catches JavaScript errors in its child tree, prevents the app from crashing, and displays a fallback UI.
11. **Can functional components act as Error Boundaries?**
    * *Answer*: No, because functional hooks do not support lifecycle methods like `componentDidCatch` or `getDerivedStateFromError`.
12. **Name two lifecycle methods exclusive to Error Boundaries.**
    * *Answer*: `static getDerivedStateFromError(error)` and `componentDidCatch(error, info)`.
13. **Do Error Boundaries catch errors in event handlers?**
    * *Answer*: No. If an error occurs inside an `onClick` callback, you must use standard `try-catch` blocks; Error Boundaries only catch errors during rendering.
14. **What is React Fiber?**
    * *Answer*: The core reconciliation engine introduced in React 16 to support incremental rendering and update prioritization.
15. **What is the main benefit of React Fiber?**
    * *Answer*: It keeps web animations and input interfaces smooth by allowing React to pause and resume rendering tasks.
16. **How do you dispatch an action in Redux?**
    * *Answer*: By calling the `dispatch` function (e.g., `dispatch(increment())`) from the `useDispatch` hook.
17. **What is the difference between Redux and Context API?**
    * *Answer*: Context API is a transport mechanism that triggers updates globally. Redux is a full state framework featuring devtools, thunks, and optimized selector renders.
18. **What does the `configureStore` utility do in RTK?**
    * *Answer*: It sets up a Redux store with default middleware (including Redux Thunk and DevTools extension support) automatically.
19. **What is the `Provider` component in Redux?**
    * *Answer*: A wrapper component from the `react-redux` library that binds the Redux store to the React app tree, making state available to all components.
20. **How do you access the Redux store inside functional components?**
    * *Answer*: By calling the `useSelector` hook to select data, and the `useDispatch` hook to trigger actions.

### 🟡 Intermediate Questions (21-40)

21. **Explain the data flow cycle in Redux (Single-source data flow).**
    * *Answer*: (1) Component dispatches an Action. (2) Store sends the Action to the Redux Reducers. (3) Reducers compute the next state. (4) Component receives the state updates via Selectors and re-renders.
22. **Why must Redux reducers be pure functions?**
    * *Answer*: To ensure predictability. If reducers run side effects or change arguments, Redux cannot perform time-travel debugging, undo features, or track state history reliably.
23. **What is the difference between `getDerivedStateFromError` and `componentDidCatch`?**
    * *Answer*: `getDerivedStateFromError` is static and runs during the render phase to update state and show the fallback UI. `componentDidCatch` runs in the commit phase, used to log errors to external monitoring services.
24. **How does the `createAsyncThunk` utility work in Redux Toolkit?**
    * *Answer*: It accepts an action string prefix and an async callback. It returns a Thunk that automatically dispatches pending, fulfilled, or rejected actions based on the promise status.
25. **Explain the concept of "Time Travel Debugging" in Redux.**
    * *Answer*: Since Redux stores state as immutable logs triggered by actions, the Redux DevTools can replay, step backward, or skip actions to reconstruct any past UI state.
26. **What is "State Tearing" and how does React 18 prevent it?**
    * *Answer*: A visual inconsistency where different parts of the screen display conflicting values for the same state during concurrent renders. React 18 uses internal schedules and `useSyncExternalStore` to prevent it.
27. **What are the limits of Error Boundaries (which errors do they miss)?**
    * *Answer*: They do not catch errors inside: (1) event handlers, (2) setTimeout/setInterval, (3) asynchronous API requests, (4) server-side rendering, and (5) errors thrown in the boundary component itself.
28. **Explain what the "payload" property in a Redux Action represents.**
    * *Answer*: The payload holds the actual data variables needed by the reducer to perform the state transition (e.g. `payload: { id: 5 }`).
29. **What is the difference between `extraReducers` and `reducers` inside a Redux Toolkit Slice?**
    * *Answer*: `reducers` generates internal action creators automatically. `extraReducers` allows the slice to listen and respond to action creators declared in *other* slices or async thunks.
30. **Explain how React Fiber schedules rendering work using reconciliation phases (Render Phase vs Commit Phase).**
    * *Answer*: The **Render Phase** computes changes (can be paused, restarted, and is asynchronous). The **Commit Phase** writes changes to the real DOM (runs synchronously and cannot be interrupted).
31. **What is Middleware in Redux?**
    * *Answer*: A pipeline wrapper that intercepts dispatched actions before they reach the reducers, used for logging, analytics, or asynchronous thunks.
32. **How do you handle multiple reducers in Redux?**
    * *Answer*: RTK's `configureStore` merges them automatically. In legacy Redux, you combine them into a single root reducer using `combineReducers()`.
33. **Explain what "Rehydration" means in Redux state persistence.**
    * *Answer*: The process of reading cached state from local storage on app load and seeding it back into the Redux store to restore the user session.
34. **How does `useSelector` optimize rendering, and what is the role of `shallowEqual`?**
    * *Answer*: `useSelector` compares return values. If they are equal, it skips component rendering. If the selector returns a new object reference, you must pass `shallowEqual` to avoid unnecessary updates.
35. **What is the default middleware set loaded by Redux Toolkit?**
    * *Answer*: Immutability checks, serializability checks, and `redux-thunk` middleware.
36. **How do you handle error logging in production React applications?**
    * *Answer*: By setting up an Error Boundary that catches runtime exceptions and sends the stack trace details to monitoring platforms like Sentry or LogRocket.
37. **Can a component inside a Redux app still use local `useState`?**
    * *Answer*: Yes. Local state is preferred for UI toggles and form typing inputs. Redux should only store global, shared business data.
38. **Explain the concept of "Action bubbling" in Redux.**
    * *Answer*: Actions do not bubble. Redux has a single global dispatcher. Any action dispatched is sent directly to all reducers in the application simultaneously.
39. **What is the purpose of the `serializableCheck` middleware in Redux Toolkit?**
    * *Answer*: It logs warnings if non-serializable values (like functions, Promises, or class instances) are stored in the state, ensuring the state tree can be easily converted to JSON strings.
40. **How do you force an Error Boundary to catch event handler errors?**
    * *Answer*: By catching the error in a try-catch block and updating a dummy state variable, forcing React to render an exception during the next cycle:
      ```javascript
      try { ... } catch(e) { setError(() => { throw e; }); }
      ```

### 🔴 Advanced Questions (41-50)

41. **Explain the structural transformation of the reconciliation tree inside React Fiber (Singly Linked List Tree Traversal).**
    * *Answer*: Fiber discards standard recursive tree walks. It transforms the DOM tree into a linked list where each node has `child` (points to first child), `sibling` (points to next sibling), and `return` (points to parent) pointers, allowing React to stop and resume traversal.
42. **Why does Redux Toolkit warn against storing non-serializable objects like class instances in the state tree, and what is the architectural cost of disabling this rule?**
    * *Answer*: It breaks time-travel debugging and state rehydration. Class instances contain methods and mutable states. Disabling the rule allows mutations to occur without Redux knowing, breaking view synchronization.
43. **How does `useSyncExternalStore` coordinate state updates between React's Scheduler and third-party stores?**
    * *Answer*: It subscribes to the store. When updates occur, it runs selector checks. If values change, it forces a synchronous update pass on the subscribing React fiber, preventing visual tearing.
44. **Explain how the Work Tag and Effect Tag bitmasks inside a Fiber Node determine what DOM operations are executed in the Commit Phase.**
    * *Answer*: During the render phase, Fiber nodes are flagged with bitmasks (e.g. `Placement`, `Update`, `Deletion`). During the commit phase, the reconciler skips checking props, scanning the linear effect list to execute DOM additions or removals quickly.
45. **What is the difference in performance between Redux selectors created with `createSelector` (Reselect) and plain arrow function selectors?**
    * *Answer*: Plain selectors recalculate on every store change. `createSelector` creates memoized selectors. If input parameters don't change, it returns the cached state slice immediately, preventing re-renders.
46. **How does React Fiber's priority scheduling map updates to Lanes?**
    * *Answer*: Fiber divides work into 32 priority lanes. Discrete events (clicks, keypresses) get immediate lanes. Transitions get medium lanes. Network data streams get idle lanes. React can interleave and pause tasks depending on lane ratings.
47. **Why can't an Error Boundary catch errors thrown inside a nested component's `useEffect` hook synchronously during the commit phase?**
    * *Answer*: Because `useEffect` runs asynchronously *after* the render and commit phases have completed. The Error Boundary try-catch layout is only active during the synchronous rendering sweep.
48. **Explain the implementation of dynamic slice injection (code splitting Redux reducers) in microservices architecture.**
    * *Answer*: When a lazy-loaded route mounts, it calls a custom hook that injects its slice reducer dynamically into the active store using `store.replaceReducer()`, allowing code bundles to stay small.
49. **How would you debug a hard crash caused by an infinite loop between a Redux selector and an action dispatcher?**
    * *Answer*: Look for dispatcher loops in the Redux DevTools. Check if a component's render block or `useEffect` triggers an action that modifies a key, which in turn updates the selector, triggering renders.
50. **Explain what the "Double Render" phase of React 18 Concurrent rendering does when resolving state inconsistencies.**
    * *Answer*: If React detects a state mutation occurred during a paused rendering pass, it discards the workInProgress tree, reverts DOM states to the current tree values, and restarts the render loop from scratch.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 09: Routing & Custom Hooks](09_routing_custom_hooks.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
