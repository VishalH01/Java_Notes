# ⚡ Topic 05: Asynchronous JS & Promises

Welcome back, time coordinator! In this chapter, we will learn about **Asynchronous JavaScript, Callback Hell, Promises, and `async`/`await`**. JavaScript is single-threaded, but it needs to perform slow tasks (like fetching data from database servers or loading files) without freezing the browser screen. We will learn how JS uses the browser environment to run tasks in the background, and how to write clean asynchronous code.

---

## 🏠 The Big Picture & Real-Life Example

### 🍔 The Fast Food Counter & The Pager
Imagine you walk into a busy burger restaurant:
* **Synchronous (The Single Line)**: The cashier takes your order, walks to the back, cooks the beef, toasts the bun, wraps the burger, and hands it to you. The next customer waits 15 minutes in line just to order (**Blocking**).
* **Asynchronous (The Restaurant Pager)**:
  * The cashier takes your order and hands you a wireless pager (**A Promise**).
  * You walk away from the register, sit at a table, and chat with friends (**Non-blocking execution**).
  * The pager status is **Pending** while the food is cooking.
  * When the burger is ready, the kitchen activates the pager (**Fulfilled**).
  * If the kitchen runs out of beef, they ring the pager with a red warning light (**Rejected**).
  * You return the pager to the counter and collect your food (**`then`** or **`await`** callback).
* **The Call Stack (The Cashier Register)**: The cashier only speaks to one customer at a time.
* **The Web API (The Kitchen Crew)**: The kitchen crew cooks the burger in the background while the cashier keeps taking new orders.
* **The Event Loop (The Alert Bell)**: A bell rings when a pager vibrates. The Event Loop waits until the cashier is free, then tells the customer to step up to the counter.

---

## 🔬 Let's Look Closer

### 1. The Asynchronous Architecture
Because the JavaScript engine is single-threaded, it delegates slow tasks to the hosting environment (the browser's **Web APIs** or Node's **C++ APIs**):
* **Web APIs**: Handle features like `setTimeout`, DOM events, and HTTP requests (Fetch).
* **Callback Queue (Task Queue)**: When an async task completes in the Web API container, its callback function is sent to this queue.
* **Microtask Queue**: A high-priority queue specifically for Promise resolvers (`.then`, `.catch`, `.finally`). It executes *before* the standard Task Queue.
* **Event Loop**: Monitors the Call Stack. If the stack is empty, it processes all tasks in the Microtask Queue first, then moves to the Task Queue.

### 2. Promises
A Promise is a JavaScript object that can be in one of three states:
* **Pending**: Initial state; the async operation is still running.
* **Fulfilled**: The operation completed successfully, yielding a result value.
* **Rejected**: The operation failed, throwing an error reason.

---

## 💻 Code Sandbox

Let's write a mock API fetch using Promises and clean `async`/`await` wrappers.

```javascript
// 1. Simulating an asynchronous database fetch using Promises
function fetchUserData(userId) {
    return new Promise((resolve, reject) => {
        console.log("Database query started for user:", userId);
        
        // Simulating 2 seconds of database network latency
        setTimeout(() => {
            if (userId === 99) {
                reject(new Error("User not found in system!"));
            } else {
                resolve({ id: userId, username: "dev_john", role: "admin" });
            }
        }, 2000);
    });
}

// 2. Consuming the Promise using standard .then() / .catch()
fetchUserData(1)
    .then(user => console.log("Promise Success callback:", user))
    .catch(err => console.log("Promise Error callback:", err.message));

// 3. Consuming the Promise using modern async/await with try-catch
async function runDashboard() {
    try {
        console.log("Dashboard starting...");
        // Execution pauses here until the promise resolves
        const user = await fetchUserData(5); 
        console.log("Dashboard loaded user data:", user.username);
        
        // Testing error rejection
        await fetchUserData(99);
    } catch (error) {
        console.log("Async/Await Error caught:", error.message);
    } finally {
        console.log("Dashboard operations completed.");
    }
}

runDashboard();
```

---

## 🧠 Points to Remember

* **`await` can only be used inside `async` functions**. (Note: Modern environments support top-level await in modules, but standard functions must be marked `async`).
* **Microtasks always execute before Macrotasks**. If you queue a Promise resolve and a `setTimeout(fn, 0)` at the same time, the Promise callback (`.then`) will run first.
* **Do not forget to handle Rejections**. An unhandled promise rejection can cause app crashes in environments like Node.js. Always use `try-catch` blocks or append `.catch()` handlers.

---

## 📖 Key Definitions

* **Synchronous Execution**: A program execution model where tasks are executed sequentially in order of declaration, blocking subsequent actions until the current task completes.
* **Asynchronous Execution**: An execution model where tasks can start now and finish later, letting the program continue running other tasks in the meantime.
* **Promise**: An object representing the eventual completion (or failure) of an asynchronous operation and its resulting value.
* **Event Loop**: A browser or engine manager that continuously monitors the Call Stack and Task Queue, pushing queued tasks to the stack when it is empty.
* **Async/Await**: Syntactic sugar built on top of Promises that allows writing asynchronous code that looks and behaves like synchronous code.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is asynchronous programming in JavaScript?**
   * *Answer*: A technique where tasks can start now and complete later, allowing the program to continue running other tasks without freezing.
2. **What is a Promise?**
   * *Answer*: An object representing the eventual completion (or failure) of an asynchronous operation and its resulting value.
3. **What are the three states of a Promise?**
   * *Answer*: (1) Pending, (2) Fulfilled, and (3) Rejected.
4. **How does `async/await` relate to Promises?**
   * *Answer*: `async/await` is syntactic sugar built on top of Promises, allowing developers to write asynchronous code that reads like synchronous code.
5. **What is Callback Hell?**
   * *Answer*: A scenario where nesting multiple callbacks inside each other makes code hard to read, maintain, and debug.
6. **How do you catch errors when using `async/await`?**
   * *Answer*: By wrapping the asynchronous code blocks inside standard `try-catch` blocks.
7. **What is the Event Loop?**
   * *Answer*: A process that coordinates task queues and the Call Stack, pushing queued tasks to the stack once the stack is empty.
8. **What does the `async` keyword do when placed before a function?**
   * *Answer*: It ensures the function always returns a Promise. If the function returns a raw value, it is wrapped in a resolved Promise.
9. **What does the `await` keyword do?**
   * *Answer*: It pauses code execution inside an async function until the targeted Promise settles (resolves or rejects).
10. **What is the difference between `setTimeout(fn, 1000)` and `setInterval(fn, 1000)`?**
    * *Answer*: `setTimeout` executes the callback once after 1000ms. `setInterval` executes the callback repeatedly every 1000ms.
11. **How do you resolve a Promise immediately?**
    * *Answer*: By calling the static method: `Promise.resolve(value)`.
12. **How do you reject a Promise immediately?**
    * *Answer*: By calling the static method: `Promise.reject(reason)`.
13. **What does `Promise.all()` do?**
    * *Answer*: It accepts an array of Promises and returns a single Promise that resolves when all input Promises resolve, or rejects if *any* Promise fails.
14. **What is the Callback Queue?**
    * *Answer*: The queue where callbacks from asynchronous tasks (like `setTimeout` or DOM clicks) wait to be pushed onto the Call Stack.
15. **Does JavaScript run asynchronous code on multiple threads?**
    * *Answer*: No, the JS engine is single-threaded. Asynchronous operations are handled by the browser (Web APIs) or Node C++ background threads.
16. **What is the difference between a resolved state and a settled state?**
    * *Answer*: A Promise is settled when it is no longer pending (i.e. it has transitioned to either fulfilled or rejected).
17. **What is the purpose of `.finally()` in Promises?**
    * *Answer*: To register a callback that executes once a Promise is settled, regardless of whether it succeeded or failed, commonly used for cleanups.
18. **Can you use `await` outside of an async function?**
    * *Answer*: Generally no. However, modern runtimes support top-level await inside ES Modules.
19. **How do you cancel a pending `setTimeout` timer?**
    * *Answer*: By passing the timer ID returned by `setTimeout` to `clearTimeout(timerId)`.
20. **What is an unhandled rejection error?**
    * *Answer*: An error that occurs when a Promise is rejected but no catch handler is defined to handle it.

### 🟡 Intermediate Questions (21-40)

21. **What is the difference between the Microtask Queue and the Macrotask (Task) Queue?**
    * *Answer*: The Microtask Queue holds high-priority tasks (Promise callbacks). The Macrotask Queue holds lower-priority tasks (`setTimeout`, intervals). The Event Loop executes *all* pending microtasks before moving to the next macrotask.
22. **Explain the execution outcome of `setTimeout(fn, 0)`. Does it run immediately?**
    * *Answer*: No, it does not run immediately. It registers the timer in the Web API container, which immediately queues the callback in the Macrotask Queue. It runs only after the current call stack is empty.
23. **What is the difference between `Promise.all()` and `Promise.allSettled()`?**
    * *Answer*: `Promise.all()` rejects immediately if *any* Promise fails. `Promise.allSettled()` waits for all Promises to settle (succeed or fail) and returns a list of results and statuses.
24. **Explain how `Promise.race()` works.**
    * *Answer*: It returns a Promise that settles as soon as the first Promise in the input array settles (either resolves or rejects).
25. **Explain the difference between `Promise.race()` and `Promise.any()`.**
    * *Answer*: `Promise.race()` settles when the first Promise settles (even if it rejects). `Promise.any()` resolves when the first Promise *resolves* successfully, ignoring rejections unless all fail.
26. **What happens if you throw an error inside an `async` function?**
    * *Answer*: The error is caught by the engine, which automatically rejects the Promise returned by the async function with that error as the reason.
27. **Why does calling `await` in a loop sequentially degrade performance, and how do you fix it?**
    * *Answer*: Because each iteration blocks until its promise completes, serializing operations. Fix: Collect the promises in an array and run them in parallel using `Promise.all(promises)`.
28. **How does JavaScript coordinate Web API callbacks with the Single-Threaded Call Stack?**
    * *Answer*: Web APIs execute tasks in the background. Once finished, they place the callback in the Task Queue. The Event Loop monitors the Call Stack; when the stack is empty, it pushes the callback onto the stack.
29. **What does the constructor parameter `executor` function of a Promise accept?**
    * *Answer*: It accepts two callback functions: `resolve(value)` (to fulfill the promise) and `reject(error)` (to reject it).
30. **Explain how Promise Chaining works.**
    * *Answer*: Calling `.then()` returns a new Promise. If the callback inside `.then()` returns a value, it resolves the new Promise; if it returns another Promise, subsequent `.then()` calls chain to it.
31. **What is the value of `this` inside the Promise executor function?**
    * *Answer*: In the executor function, `this` is resolved lexically depending on where the Promise was created, defaulting to `window` or `undefined` (in strict mode) for standard functions.
32. **Does a Promise execute synchronously or asynchronously?**
    * *Answer*: The **executor function** runs synchronously immediately when the Promise is constructed. Only the resolver callbacks (`.then`, `.catch`) run asynchronously.
33. **Explain how to convert a callback-based function (e.g. Node `fs.readFile`) into a Promise.**
    * *Answer*: Wrap it in a Promise constructor, calling `resolve` on success and `reject` on error:
      ```javascript
      const readFileOpt = (path) => new Promise((res, rej) => {
          fs.readFile(path, (err, data) => err ? rej(err) : res(data));
      });
      ```
34. **What is a "promisified" API?**
    * *Answer*: An API wrapper that replaces standard callback parameters with returned Promises, allowing developers to use `async/await` syntax.
35. **Explain the behavior of Promise execution order inside a nested code block containing `process.nextTick()` in Node.js.**
    * *Answer*: In Node.js, `process.nextTick()` has higher priority than Promise microtasks. It runs immediately after the current operation, before the Microtask Queue executes.
36. **How does the Event Loop check for page rendering frames?**
    * *Answer*: Rendering is scheduled by the browser. The Event Loop prioritizes microtasks first, executes rendering tasks (like `requestAnimationFrame`), and then processes macrotasks.
37. **What is the risk of executing infinite loops inside a Promise `.then()` callback?**
    * *Answer*: It blocks the Event Loop. Because microtasks run continuously until the queue is empty, an infinite loop in a microtask prevents the browser from rendering or processing clicks.
38. **Explain what the `AbortController` API does in asynchronous operations.**
    * *Answer*: An API that provides an `AbortSignal` object. You can pass this signal to async operations (like Fetch) and call `controller.abort()` to cancel the operation midway.
39. **Why does using `await` on a non-Promise value not throw an error?**
    * *Answer*: Because `await` automatically wraps non-promise values in a resolved Promise using `Promise.resolve(value)`.
40. **How does the Event Loop check for macro task completion thresholds?**
    * *Answer*: The Event Loop processes exactly **one macrotask** from the queue, then runs all pending microtasks before returning to check the next macrotask.

### 🔴 Advanced Questions (41-50)

41. **Explain the step-by-step memory allocation and thread safety of the V8 Event Loop when bridging task queues to C++ libuv layers.**
    * *Answer*: V8 executes on a single thread. For async I/O, V8 delegates tasks to the browser or Node's C++ **libuv thread pool**. Once libuv worker threads complete a task, they signal the main thread. The main thread pushes the callback to the Task Queue, ensuring V8 runs callbacks thread-safely on the main thread.
42. **Why does executing 1000 microtasks back-to-back starve the Macrotask Queue, and how does this affect browser UI rendering?**
    * *Answer*: The Event Loop executes microtasks until the queue is empty. If microtasks continuously queue more microtasks, the Event Loop stays in the Microtask Queue, blocking the Macrotask Queue and rendering tasks, which freezes the UI.
43. **Explain the internal implementation differences between `Promise.all()` and `Promise.any()` at the array tracking level.**
    * *Answer*: `Promise.all()` tracks successes using an output array and a counter. If the counter matches the input length, it resolves. If any promise fails, it rejects immediately. `Promise.any()` tracks failures in an array; it resolves when the first success occurs, and rejects only when the failure count matches the input length.
44. **How does the V8 engine optimize async stack traces in modern Chrome, preventing parent stack frames from losing references on pop?**
    * *Answer*: In older engines, stack frames were lost when functions popped off the stack. Modern V8 uses **Zero-Cost Async Stack Traces**. When a Promise suspends, V8 stores the parent stack frames in the heap, rebuilding the full stack trace if the Promise throws an error.
45. **What is the structural difference between Generator yields and Promise resolves inside V8 engine registers?**
    * *Answer*: Generator yields are handled by V8 registers directly, suspending the generator's execution frame in memory. Promise resolves require scheduling callbacks on the Microtask Queue, which involves the Event Loop.
46. **How does the runtime coordinate requestAnimationFrame (rAF) callbacks compared to standard macrotasks during frame updates?**
    * *Answer*: Standard macrotasks run in order on Event Loop ticks. `requestAnimationFrame` callbacks run as a batch right before the browser's layout and paint phases, ensuring style changes align with screen refresh rates.
47. **Explain the thread scheduling mechanism of the Libuv Event Loop inside Node.js (Timers, Poll, Check, Close phases).**
    * *Answer*: Libuv runs in phases: (1) **Timers** (setTimeout), (2) **Pending Callbacks** (I/O errors), (3) **Idle/Prepare** (internals), (4) **Poll** (incoming I/O connections), (5) **Check** (setImmediate), and (6) **Close Callbacks** (socket cleanups).
48. **Why does wrapping multiple database updates inside a single `Promise.all` call consume excessive database connection pools?**
    * *Answer*: Because `Promise.all` starts all asynchronous operations in parallel. If you pass 100 queries, it opens 100 database connections simultaneously, which can exhaust the connection pool.
49. **How would you write a custom async task scheduler that limits concurrent executions to exactly $K$ tasks?**
    * *Answer*: Maintain a running task counter and a queue of pending tasks. When launching a task, increment the counter. When a task resolves, decrement the counter and pull the next task from the queue if the counter is below $K$.
50. **Explain how V8 handles the compilation of `async/await` bytecode using internal Promise resolve hooks.**
    * *Answer*: V8 compiles `async/await` into generators under the hood. The `await` keyword compiles to a `yield` instruction. When the yielded Promise resolves, V8's internal hooks resume the generator, passing the resolved value back.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 04: Objects, Arrays & Destructuring](04_objects_arrays_destructuring.md)
* **Next Chapter**: [👉 Topic 06: DOM Events & Propagation](06_dom_events_propagation.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
