# ⚡ Topic 04: Side Effects & useEffect

Welcome, side effect controller! In this chapter, we will learn about **Side Effects and the `useEffect` hook**. A pure React component only translates data into HTML. When a component needs to interact with the outside world (like fetching data, setting timers, or subscribing to sockets), it must perform a "Side Effect". We will learn how to schedule these actions and clean up resources to prevent memory leaks.

---

## 🏠 The Big Picture & Real-Life Example

### ⏰ The Hotel Butler & The Wake-Up Alarm
Imagine you are staying in a high-tech hotel room:
* **The Normal View (Rendering)**: The room has a bed, a TV, and lights. You change the TV volume using the remote (**State/Props**). This stays inside the room.
* **The Alarm (Side Effect)**: You want to coordinate with the outside world: *"Ring a bell when it is 7:00 AM"* or *"Fetch morning newspapers from the lobby."* You hire a butler (**`useEffect`**) to do this.
* **The Dependency Array (When to act)**: You tell the butler when to perform tasks:
  * **No schedule list (No array)**: The butler runs into the room every single time you change the TV volume or sit on the bed (**Runs on every render**). Too noisy!
  * **Empty list (`[]`)**: The butler only enters once when you check in, sets the alarm, and stays quiet (**Runs only once on mount**).
  * **Specific list (`[activeChannel]`)**: The butler only runs in when you change the active channel (**Runs only when dependency changes**).
* **The Cleanup Function (Tidying up)**: Before you check out of the hotel (**Unmount**), the butler must turn off the alarm clock so it doesn't wake up the next guest (**Cleanup Function**). If he forgets, the alarm rings forever (**Memory Leak**)!

---

## 🔬 Let's Look Closer

### 1. The Anatomy of `useEffect`
The `useEffect` hook accepts two arguments:
1. A **callback function** containing the side effect code.
2. An optional **dependency array**.

```jsx
import { useEffect } from 'react';

useEffect(() => {
    // Side effect logic goes here
    
    return () => {
        // Optional cleanup logic goes here
    };
}, [dependency1, dependency2]);
```

### 2. Dependency Array Rules

| Dependency Array | When it Runs |
|---|---|
| **`useEffect(() => {})`** (None) | Runs after **every single render** of the component. |
| **`useEffect(() => {}, [])`** (Empty) | Runs **once** after the initial render (Mount). |
| **`useEffect(() => {}, [count])`** (Variables) | Runs on mount, and then **only when `count` changes**. |

### 3. The Cleanup Function
If your effect sets up a timer or subscription, you must return a cleanup function. React executes this function:
1. Immediately before running the effect code again (to clean up the previous run).
2. When the component unmounts (destroys).

---

## 💻 Code Sandbox

Let's write a component that fetches data from an API and sets up a timer.

```jsx
import React, { useState, useEffect } from 'react';

export default function UserStatusTimer() {
    const [seconds, setSeconds] = useState(0);
    const [userData, setUserData] = useState(null);

    // Effect 1: Fetching API data (Runs ONLY on mount)
    useEffect(() => {
        console.log("Fetching user data...");
        fetch("https://jsonplaceholder.typicode.com/users/1")
            .then(res => res.json())
            .then(data => setUserData(data));
    }, []); // Empty array = mount only

    // Effect 2: Timer (Runs on mount, cleans up on unmount)
    useEffect(() => {
        console.log("Setting up interval...");
        const intervalId = setInterval(() => {
            setSeconds(prev => prev + 1);
        }, 1000);

        // Cleanup: Clear the interval to prevent memory leaks!
        return () => {
            console.log("Clearing interval!");
            clearInterval(intervalId);
        };
    }, []); // Runs once, returns cleanup

    return (
        <div style={{ padding: '20px', border: '1px solid black' }}>
            <h2>Activity Tracker</h2>
            <p>Time spent on page: {seconds} seconds</p>
            
            {userData ? (
                <div>
                    <h3>Logged In User:</h3>
                    <p>Name: {userData.name}</p>
                    <p>Email: {userData.email}</p>
                </div>
            ) : (
                <p>Loading user details...</p>
            )}
        </div>
    );
}
```

---

## 🧠 Points to Remember

* **Don't skip cleanup**. If you use `addEventListener` or `setInterval` inside `useEffect`, always clean them up in the return callback, otherwise they remain active in browser memory.
* **Don't lie about dependencies**. If your effect uses a state or prop variable, it **must** be listed inside the dependency array. If you omit it, the effect will read stale values from old renders.
* **Avoid infinite loops**. If your effect updates state `setX(newX)`, and `x` is in the dependency array, it triggers a loop: render -> effect -> state change -> render -> effect.

---

## 📖 Key Definitions

* **Side Effect**: Any operation that affects something outside the scope of the React component rendering loop (e.g. data fetching, direct DOM manipulation, timers).
* **useEffect Hook**: A built-in React hook used to perform side effects in functional components.
* **Dependency Array**: The second argument of `useEffect` that contains variables the effect relies on, determining when the effect should rerun.
* **Cleanup Function**: A function returned inside the `useEffect` callback used to tidy up resources (like clearing intervals or unsubscribing) before the component unmounts or reruns.
* **Infinite Loop (Effect)**: A bug where an effect updates a state variable that is also listed in its dependency array, causing continuous re-renders and re-executions.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is a Side Effect in React?**
   * *Answer*: Any operation that interacts with or modifies things outside the component rendering scope, such as API fetching, setting timers, or altering the page title.
2. **What is the `useEffect` hook?**
   * *Answer*: A built-in hook used to perform side effects in functional React components.
3. **What is the dependency array in `useEffect`?**
   * *Answer*: An array passed as the second argument that lists variables React monitors to determine when to rerun the effect.
4. **How do you make an effect run only once when the component mounts?**
   * *Answer*: By passing an empty dependency array `[]`.
5. **What happens if you do not pass a dependency array to `useEffect`?**
   * *Answer*: The effect will execute after every single render cycle of the component.
6. **What is a cleanup function in `useEffect`?**
   * *Answer*: A function returned by the effect callback used to dismantle subscriptions, intervals, or event listeners.
7. **When does the cleanup function execute?**
   * *Answer*: Immediately before the effect reruns with new dependencies, and when the component unmounts.
8. **How do you clear a timer inside `useEffect`?**
   * *Answer*: By returning a function that calls `clearTimeout(timerId)` or `clearInterval(intervalId)`.
9. **Can you return anything other than a function from `useEffect`?**
   * *Answer*: No, the effect callback must return either a cleanup function or `undefined`. Returning objects or strings triggers errors.
10. **Why shouldn't you write side effects directly inside the component body?**
    * *Answer*: Because the component body runs on every render. Executing API calls or timers there will lock the browser thread and cause loops.
11. **How do you synchronize a state variable with browser local storage?**
    * *Answer*: By writing a `useEffect` that calls `localStorage.setItem('key', state)` whenever the state variable changes.
12. **Can you call async functions directly inside `useEffect`?**
    * *Answer*: No, because async functions return a Promise, whereas React expects the callback to return a cleanup function. You must write an inner async function.
13. **What is a memory leak in React?**
    * *Answer*: A scenario where unmounted components leave active timers, listeners, or loops running in the browser, wasting CPU and RAM.
14. **How do you update the browser tab title in React?**
    * *Answer*: By running `document.title = newTitle` inside a `useEffect` that has `newTitle` in its dependency array.
15. **What does the error "Can't perform a React state update on an unmounted component" mean?**
    * *Answer*: It means an async operation (like an API call) completed *after* the component was destroyed, and attempted to update its local state.
16. **How do you track state changes inside `useEffect`?**
    * *Answer*: By listing the state variable inside the dependency array. React will trigger the effect whenever that variable changes.
17. **Can a single component have multiple `useEffect` hooks?**
    * *Answer*: Yes, this is recommended to separate unrelated side-effect concerns (e.g., one effect for timers, one for data fetching).
18. **Why are props and state listed in dependencies?**
    * *Answer*: To prevent closures from capturing old values, ensuring the effect always works with the latest render data.
19. **What is the difference between `componentDidMount` and `useEffect`?**
    * *Answer*: `componentDidMount` is a class lifecycle method. `useEffect` is a functional hook that covers mounting, updating, and unmounting in one API.
20. **Is `useEffect` executed before or after the DOM updates?**
    * *Answer*: `useEffect` executes asynchronously *after* the render is committed and the browser has painted the DOM updates to the screen.

### 🟡 Intermediate Questions (21-40)

21. **How do you fetch data asynchronously inside `useEffect` without triggering console warnings?**
    * *Answer*: Declare an inner async function inside the effect and invoke it immediately:
      ```jsx
      useEffect(() => {
          async function fetchData() {
              const data = await api.get();
              setData(data);
          }
          fetchData();
      }, []);
      ```
22. **What is the difference between `useEffect` and `useLayoutEffect`?**
    * *Answer*: `useEffect` runs asynchronously *after* the browser paints the screen. `useLayoutEffect` runs synchronously *before* the browser paints (used to measure DOM layouts to prevent visual flickers).
23. **Explain how stale closures affect variables referenced inside `useEffect` callbacks.**
    * *Answer*: If a variable is used in the effect but omitted from the dependency array, the effect will continuously read the variable's value from the render cycle when the effect was first created.
24. **How do you prevent an API request from updating state if the component unmounts mid-flight?**
    * *Answer*: By using an active flag or an `AbortController` inside the cleanup function to cancel the promise resolution:
      ```jsx
      useEffect(() => {
          let active = true;
          fetchData().then(res => { if (active) setData(res); });
          return () => { active = false; };
      }, []);
      ```
25. **Why does React StrictMode execute `useEffect` twice on mount during development?**
    * *Answer*: To ensure that developers have written correct cleanup functions. If an effect sets up a subscription, double-firing exposes memory leaks early if cleanups are missing.
26. **Explain what happens if you omit the dependency array entirely versus passing an empty array `[]`.**
    * *Answer*: Omitting the array (`useEffect(fn)`) runs the effect on **every** single render. An empty array (`useEffect(fn, [])`) runs the effect **only once** on mount.
27. **What is the performance cost of having a massive list of variables in the dependency array?**
    * *Answer*: Shallow comparison checks. React must loop and compare every variable pointer using `Object.is()`. While fast, massive dependency lists can slow down execution on hot paths.
28. **How would you create a custom auto-save feature using `useEffect`?**
    * *Answer*: Set up a debounce timer:
      ```jsx
      useEffect(() => {
          const delay = setTimeout(() => save(data), 1000);
          return () => clearTimeout(delay);
      }, [data]);
      ```
29. **What is the difference between referencing an object in the dependency array versus a primitive value?**
    * *Answer*: Objects are compared by reference. If the parent passes a new object literal on every render, the effect will execute on every render even if the keys are identical.
30. **How can you optimize an effect that relies on a function passed down as a prop?**
    * *Answer*: Ensure the parent wraps the function in `useCallback` to maintain a stable reference, preventing the child's effect from executing unnecessarily.
31. **Explain the execution sequence of cleanups and effects when a dependency changes.**
    * *Answer*: (1) The dependency changes. (2) React runs the cleanup function of the **previous** effect. (3) React runs the **new** effect callback.
32. **Can you conditionally call `useEffect` (e.g. inside an if block)?**
    * *Answer*: No. This breaks the Rules of Hooks. Hooks must be called in the exact same order on every render. Instead, place the conditional check *inside* the effect callback.
33. **What is the impact of updating a state variable inside `useEffect` without dependencies?**
    * *Answer*: It triggers an infinite render loop. The effect runs on render, changes state, which triggers a render, which triggers the effect, repeating indefinitely.
34. **How do you implement a fetch query that retries on failure using `useEffect`?**
    * *Answer*: You set up a counter state `retries`. The effect fetches data; if it fails, it increments `retries`. Listing `retries` in the dependency array triggers a re-fetch.
35. **Why does using a state updater callback (e.g. `setCount(c => c + 1)`) help reduce `useEffect` dependencies?**
    * *Answer*: Because you no longer need to reference `count` inside the effect, allowing you to remove it from the dependency array, preventing unnecessary effect runs.
36. **Explain what the ESLint rule `exhaustive-deps` does.**
    * *Answer*: It scans your effect code and warns you if you have used variables from the component scope without declaring them in the dependency array.
37. **Can we return a Promise from the cleanup function?**
    * *Answer*: Yes, but React will not await it. The cleanup function should execute synchronously to release resources instantly.
38. **How do you set up a Web Socket connection in a component using `useEffect`?**
    * *Answer*: Create the connection in the effect, and return a cleanup function that invokes `socket.close()`.
39. **What is the risk of using external variables (defined outside the component file) inside `useEffect` without listing them in dependencies?**
    * *Answer*: Zero risk. External variables are static with respect to React render scopes, so they don't need to be in dependencies.
40. **Explain how to throttle scroll events captured inside a React component.**
    * *Answer*: Inside `useEffect`, bind the scroll listener wrapped in a throttle/debounce wrapper, and remove it in the cleanup function.

### 🔴 Advanced Questions (41-50)

41. **Explain the internal scheduling priority of `useEffect` compared to browser paint ticks (requestAnimationFrame).**
    * *Answer*: `useEffect` is scheduled as a low-priority task using the Scheduler package. React commits the DOM, yields control to the browser paint cycle, and then executes the effect, avoiding blocking layouts.
42. **Why does `useLayoutEffect` block visual paints, and how can it cause page loading lags if it contains slow calculations?**
    * *Answer*: `useLayoutEffect` runs synchronously within the same tick as the DOM calculations. The browser cannot draw to screen until the JS thread completes, meaning slow tasks freeze UI updates.
43. **How does Concurrent React handle a dependency change that is interrupted by a high-priority user input before the commit phase?**
    * *Answer*: If a render is discarded, the new Virtual DOM is abandoned. Because the commit phase never occurs, React does not execute the corresponding `useEffect` or its cleanup function.
44. **Explain how to create a custom hooks wrapper that executes an effect only when a variable transitions from a specific value to another.**
    * *Answer*: You use a `useRef` to store the previous value of the variable. Inside the effect, check if `ref.current === valueA` and the new value is `valueB`, then update the ref.
45. **What is the consequence of utilizing `useEffectEvent` (experimental feature in React) in dependency management?**
    * *Answer*: It decouples reactive logic. It allows extracting non-reactive code (like logging functions) out of the effect, so they can access fresh state without being listed as dependencies.
46. **How does React prevent race conditions when executing multiple concurrent asynchronous requests within `useEffect`?**
    * *Answer*: React doesn't prevent them automatically. You must implement a cancellation flag in the cleanup function. When a new render occurs, the old cleanup runs and sets `active = false`, ignoring the outdated promise.
67. **Explain the memory footprint differences between creating new functions inside `useEffect` versus hoisting them outside the component.**
    * *Answer*: Functions inside the effect are recreated on every execution loop. Hoisting functions outside ensures only one function instance exists in memory, saving allocation cycles.
48. **How does React's Fiber node keep track of active cleanups for each hook?**
    * *Answer*: The fiber node's hook object contains an `updateQueue` containing effect descriptors. Each descriptor stores a `destroy` field pointing to the cleanup function, executed during the commit phase's deletion loops.
49. **Why is it discouraged to synchronize state from Props using `useEffect` (e.g. updating local state when prop changes)?**
    * *Answer*: It causes double rendering. The child renders with the new prop, fires the effect, updates state, and renders *again*. It is better to use key resets or calculate values during render.
50. **Explain how the `useInsertionEffect` hook works and why it is reserved exclusively for CSS-in-JS libraries.**
    * *Answer*: It fires *before* `useLayoutEffect` and before DOM mutations. It allows CSS-in-JS libraries to inject style tags dynamically into the DOM without triggering browser layout calculations during rendering.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 03: State Management (useState)](03_state_management.md)
* **Next Chapter**: [👉 Topic 05: Lists, Keys & Conditionals](05_lists_keys_conditionals.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
