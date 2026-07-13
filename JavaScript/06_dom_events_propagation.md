# 📡 Topic 06: DOM Events & Propagation

Welcome back, event manager! In this chapter, we will learn about **DOM Events, Event Propagation (Bubbling and Capturing), and Event Delegation**. When you click a button on a web page, that click is not just felt by the button; it travels through the entire tree of parent elements. We will learn how to capture events, stop them from spreading, and use event delegation to manage dynamic list elements efficiently.

---

## 🏠 The Big Picture & Real-Life Example

### 🌊 The Dropped Stone in the Nested Lakes (Event Propagation)
Imagine you have three nested circular pools:
* **The Global Lake (The Window)**: The outermost pool.
* **The Middle Pond (The Card `<div>`)**: A pool inside the global lake.
* **The Inner Cup (The Button `<button>`)**: A small cup in the middle of the pond.

If you drop a stone directly into the **Inner Cup** (clicking the button):
* **Phase 1: Event Capturing (The Sink)**: The stone's splash signal enters from the sky. It passes through the Global Lake, then travels down into the Middle Pond, and finally hits the bottom of the Inner Cup (**Capturing Phase**).
* **Phase 2: The Hit (The Target)**: The stone strikes the target cup.
* **Phase 3: Event Bubbling (The Ripples)**: The splash creates water ripples. The ripples expand from the Inner Cup, move outward into the Middle Pond, and finally reach the edges of the Global Lake (**Bubbling Phase**).

### 🎫 The Ticket Gate Manager (Event Delegation)
Imagine you are running a cinema lobby with 100 theater doors.
* **Bad Setup**: You hire 100 ticket checkers and stand one checker at every single door. If you open 10 new doors, you must hire 10 new checkers (**Adding listeners to every list item**).
* **Good Setup (Event Delegation)**: You hire **one** gate manager and place him at the main lobby entrance (**Parent Element**). Every moviegoer must pass him. The manager checks their ticket stub, looks at the theater room number written on it (**`event.target`**), and directs them to the correct door. It works for 10 doors or 10,000 doors!

---

## 🔬 Let's Look Closer

### 1. Event Propagation Phases
An event travels through the DOM tree in three distinct phases:
1. **Capturing Phase**: The event travels down from the `window` root through ancestors to the target.
2. **Target Phase**: The event triggers on the element that was clicked (`event.target`).
3. **Bubbling Phase**: The event bubbles up from the target back to the `window` root. By default, most event listeners listen during this phase.

### 2. Event Delegation
Instead of binding listeners to many child elements, you bind a single listener to a parent container. When a child is clicked:
1. The click event bubbles up to the parent.
2. The parent reads `event.target` to identify which child was clicked.
3. The parent runs the action for that child. This works automatically for new children added to the DOM dynamically.

---

## 💻 Code Sandbox

Here is how to set up event propagation controls and event delegation.

```html
<!DOCTYPE html>
<html>
<body>
    <!-- 1. Event Delegation Container -->
    <ul id="todo-list">
        <li>Task 1 <button class="delete-btn">X</button></li>
        <li>Task 2 <button class="delete-btn">X</button></li>
    </ul>
    <button id="add-task-btn">Add New Task</button>

    <div id="parent-card" style="padding: 20px; background: #eee; margin-top: 15px;">
        Parent Card container
        <button id="child-action-btn">Click Card Action</button>
    </div>

    <script>
        // --- 1. Event Delegation ---
        const todoList = document.getElementById('todo-list');
        const addTaskBtn = document.getElementById('add-task-btn');

        // Single listener on the parent <ul> manages all button clicks!
        todoList.addEventListener('click', (event) => {
            // Check if the clicked target is a delete button
            if (event.target.classList.contains('delete-btn')) {
                const li = event.target.parentElement;
                console.log("Deleting item:", li.firstChild.textContent.trim());
                li.remove();
            }
        });

        // Adding items dynamically requires zero new listeners!
        addTaskBtn.addEventListener('click', () => {
            const newLi = document.createElement('li');
            newLi.innerHTML = `New Task <button class="delete-btn">X</button>`;
            todoList.appendChild(newLi);
        });

        // --- 2. Bubbling and stopPropagation ---
        const parent = document.getElementById('parent-card');
        const childButton = document.getElementById('child-action-btn');

        parent.addEventListener('click', () => {
            console.log("Parent Card Click Listener Fired!");
        });

        childButton.addEventListener('click', (event) => {
            console.log("Child Button Click Listener Fired!");
            // Prevent event from bubbling up to the parent card:
            event.stopPropagation();
        });
    </script>
</body>
</html>
```

---

## 🧠 Points to Remember

* **`event.target` vs `event.currentTarget`**:
  * `event.target`: The element that **originated** the event (the actual item you clicked).
  * `event.currentTarget`: The element that **currently owns** the active event listener (the parent).
* **Use `stopPropagation()` carefully**. Stopping propagation prevents parent components from listening to clicks, which can break global features like analytics tracking, dropdown close-on-click helpers, and menus.

---

## 📖 Key Definitions

* **Event Bubbling**: A propagation mechanism where an event triggers on a target element and bubbles up through its ancestors in the DOM tree.
* **Event Capturing**: A propagation phase where an event travels down from the window root element to the target element before bubbling begins.
* **Event Delegation**: A design pattern where a single event listener is attached to a parent element to manage events for all current and future child elements.
* **stopPropagation()**: A method on the DOM Event interface that prevents further propagation of the current event in the capturing and bubbling phases.
* **preventDefault()**: A method that tells the user agent that if the event goes unhandled, its default action (like submitting a form or navigating a link) should not run.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is Event Bubbling in JavaScript?**
   * *Answer*: A propagation phase where an event starts on the target element and bubbles up through its ancestors in the DOM tree.
2. **What is Event Capturing?**
   * *Answer*: A propagation phase where an event travels down from the top level (window) to the target element before bubbling begins.
3. **What is Event Delegation?**
   * *Answer*: A design pattern where a single event listener is attached to a parent element to manage events for all current and future child elements.
4. **How do you stop event propagation?**
   * *Answer*: By calling `event.stopPropagation()` inside the event handler callback.
5. **What is the difference between `event.target` and `event.currentTarget`?**
   * *Answer*: `event.target` is the element that triggered the event. `event.currentTarget` is the element that owns the active event listener.
6. **What does `event.preventDefault()` do?**
   * *Answer*: It prevents the browser's default behavior for an event (e.g. preventing form submits from reloading pages, or links from navigating).
7. **By default, do event listeners execute in the capturing or bubbling phase?**
   * *Answer*: In the bubbling phase.
8. **How do you configure an event listener to run during the capturing phase?**
   * *Answer*: By passing `true` or `{ capture: true }` as the third parameter to `addEventListener`.
9. **Name three common browser mouse events.**
    * *Answer*: `click`, `dblclick`, and `mousemove`.
10. **What is event bubbling's main benefit?**
    * *Answer*: It enables Event Delegation, allowing developers to manage many elements with a single listener.
11. **Do all DOM events bubble?**
    * *Answer*: No, some events like `focus`, `blur`, `mouseenter`, and `mouseleave` do not bubble.
12. **What is the difference between `element.onclick = fn` and `element.addEventListener('click', fn)`?**
    * *Answer*: `onclick` only allows binding a single handler and can be overwritten. `addEventListener` allows binding multiple listeners to the same event.
13. **How do you remove an event listener from a DOM element?**
    * *Answer*: By calling `element.removeEventListener('click', handlerName)` with the exact same function reference used to bind it.
14. **What is an event object?**
    * *Answer*: An object automatically passed to event handler callbacks containing details about the event (e.g., coordinates, target element, keys pressed).
15. **What does `event.stopImmediatePropagation()` do?**
    * *Answer*: It stops the event from propagating to parent elements *and* prevents any other listeners bound to the same element from running.
16. **What is event capturing also known as?**
    * *Answer*: Trickling.
17. **Can you delegate focus events?**
    * *Answer*: No, because `focus` does not bubble. However, you can listen during the capturing phase, or use `focusin` which does bubble.
18. **How do you pass options to an event listener (like making it run only once)?**
    * *Answer*: By passing an options object as the third argument: `element.addEventListener('click', handler, { once: true })`.
19. **What does `event.isTrusted` tell you?**
    * *Answer*: A boolean indicating if the event was generated by a real user action (`true`) or created programmatically by a script (`false`).
20. **What is a custom event in JavaScript?**
    * *Answer*: An event created programmatically using `new CustomEvent('eventName', { detail: data })` and triggered using `element.dispatchEvent()`.

### 🟡 Intermediate Questions (21-40)

21. **Explain the lifecycle flow of an event in detail.**
    * *Answer*: (1) **Capturing Phase**: Event moves down from window to parent nodes. (2) **Target Phase**: Event reaches the target element. (3) **Bubbling Phase**: Event bubbles back up to window.
22. **Why does using event delegation improve memory usage?**
    * *Answer*: It reduces the number of event listeners in memory. Binding one listener to a parent container uses less memory than binding individual listeners to thousands of list items.
23. **What is the consequence of calling `event.stopPropagation()` on analytics trackers?**
    * *Answer*: It blocks global click trackers bound to `document` or `body`, preventing user actions from being logged.
24. **How do you remove an anonymous function listener?**
    * *Answer*: You cannot. `removeEventListener` requires the exact same function reference pointer used during registration.
25. **Explain the difference between `mouseenter`/`mouseleave` and `mouseover`/`mouseout`.**
    * *Answer*: `mouseover`/`mouseout` bubble. `mouseenter`/`mouseleave` do not bubble and do not trigger when moving between nested child elements.
26. **What is the purpose of the `{ passive: true }` option in event listeners?**
    * *Answer*: It tells the browser the listener will not call `preventDefault()`, allowing the browser to scroll smoothly without waiting for JS execution.
27. **How does event delegation handle dynamic DOM updates?**
    * *Answer*: Because the listener is bound to the parent, any new children added to that parent automatically participate in bubbling, requiring no new listeners.
28. **What does the method `dispatchEvent()` return?**
    * *Answer*: A boolean that is `false` if the event is cancelable and at least one handler called `preventDefault()`, otherwise it returns `true`.
29. **How would you implement a simple click-outside utility using event delegation?**
    * *Answer*: Bind a click listener to `document.body` and check if the clicked target is contained within the element you want to protect:
      ```javascript
      document.body.addEventListener('click', (e) => {
          if (!myMenu.contains(e.target)) closeMenu();
      });
      ```
30. **Explain how to pass custom data payload objects inside custom events.**
    * *Answer*: Pass the data inside the `detail` property of the CustomEvent configuration object:
      ```javascript
      const event = new CustomEvent('userLogin', { detail: { username: 'john' } });
      ```
31. **What is the difference between `element.dispatchEvent()` and calling a method directly?**
    * *Answer*: `dispatchEvent` triggers the full browser event propagation lifecycle (capturing and bubbling) and executes all handlers bound to that event.
32. **Why does calling `event.preventDefault()` inside a passive listener throw an error?**
    * *Answer*: Because passive listeners promise they won't block scrolling by calling `preventDefault()`. Violating this contract triggers a console warning or error.
33. **Explain what the pointer events API solves.**
    * *Answer*: It consolidates mouse, touch, and stylus inputs into a single API (`pointerdown`, `pointerup`), eliminating the need to write separate handlers for mobile and desktop.
34. **Does `event.preventDefault()` stop event bubbling?**
    * *Answer*: No. It only cancels the browser's default behavior. The event will still bubble up the DOM tree unless `stopPropagation()` is called.
35. **What is the default behavior of the submit event?**
    * *Answer*: It attempts to serialize form inputs and send an HTTP request to the page URL or `action` attribute, reloading the page.
36. **Explain what keyboard event properties `key` and `code` represent.**
    * *Answer*: `key` returns the literal character value (e.g. `"A"` or `"a"` depending on Shift). `code` returns the physical key pressed (e.g. `"KeyA"`), ignoring modifiers.
37. **What is event hijacking?**
    * *Answer*: A vulnerability where malicious scripts intercept event bubblings to block user actions or steal inputs before they reach intended elements.
38. **How does V8 manage event listener callback references internally?**
    * *Answer*: V8 stores listeners in an internal array attached to the fiber or DOM node wrapper. It calls these callbacks in order when events trigger.
39. **Explain how to write a throttle utility for scrolling events.**
    * *Answer*: Use a timeout flag. Ignore scroll events if the flag is active, and reset the flag after a delay:
      ```javascript
      function throttle(fn, limit) {
          let inThrottle;
          return function(...args) {
              if (!inThrottle) { fn(...args); inThrottle = true; setTimeout(() => inThrottle = false, limit); }
          }
      }
      ```
40. **How do we listen to events globally on the browser tab window?**
    * *Answer*: By calling `window.addEventListener('eventName', callback)`.

### 🔴 Advanced Questions (41-50)

41. **Explain the browser event dispatcher's internal call stack and queue management during a mouse click.**
    * *Answer*: When a click occurs, the OS sends an event to the browser's UI thread. The browser creates an event task and pushes it to the Macrotask Queue. When the Event Loop picks it, the browser initiates the DOM propagation path, executing capturing and bubbling callbacks synchronously on the Call Stack.
42. **Why does executing heavy DOM calculations inside a scroll event handler cause "Layout Thrashing" (Jank)?**
    * *Answer*: Scroll events fire at high frequencies. If you query layout properties (like `offsetHeight`) inside the handler, the browser is forced to compute layout repeatedly, which blocks the rendering thread and drops frames.
43. **How does event delegation perform when dealing with thousands of deeply nested list elements?**
    * *Answer*: The parent listener is efficient, but the Event target check requires walking up the path (`event.target.closest('li')`). If elements are nested deeply, this traversal can degrade performance, requiring debouncing.
44. **What is the difference in browser scheduling between `requestAnimationFrame` and touch/scroll passive event listeners?**
    * *Answer*: `requestAnimationFrame` runs before layout and paint. Passive event listeners run immediately on touch inputs, allowing the compositor thread to scroll the page smoothly without waiting for JS execution.
45. **Explain how the browser resolves conflicts when capturing and bubbling listeners are bound to the same element.**
    * *Answer*: If capturing and bubbling listeners are bound to the same element, the capturing listeners execute first. For the target element itself, listeners execute in order of registration, ignoring the capture flag.
46. **How would you implement a custom Event Emitter class in vanilla JavaScript?**
    * *Answer*:
      ```javascript
      class EventEmitter {
          constructor() { this.events = {}; }
          on(name, fn) { (this.events[name] = this.events[name] || []).push(fn); }
          emit(name, ...args) { if (this.events[name]) this.events[name].forEach(fn => fn(...args)); }
      }
      ```
47. **Why does `event.stopPropagation()` block web component shadow boundaries unless the event is configured to compose?**
    * *Answer*: Shadow DOM encapsulates components. An event bubbles up the shadow tree; it will not cross the shadow boundary unless configured with `bubbles: true` and `composed: true`.
48. **Explain the garbage collection risk of failing to call `removeEventListener` on unmounted DOM nodes.**
    * *Answer*: If a listener references a parent closure (like a React component state), the DOM node's listener array retains a reference to that function, preventing the component from being garbage collected.
49. **How does V8 optimize event listener execution paths inside its JIT compiler?**
    * *Answer*: V8 optimizes event callbacks using **Monomorphic Inline Caches**. If the callback signature and arguments object shapes are consistent, V8 compiles them into optimized machine code, skipping dynamic checks.
50. **Explain how to write a debounced event handler that delays executions until after a scroll pause.**
    * *Answer*: Use a timer. Clear the timer on every scroll tick and restart it, executing the handler only when scrolling stops for `delay` ms:
      ```javascript
      function debounce(fn, delay) {
          let timer;
          return function(...args) {
              clearTimeout(timer);
              timer = setTimeout(() => fn(...args), delay);
          }
      }
      ```

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 05: Asynchronous JS & Promises](05_async_promises_async_await.md)
* **Next Chapter**: [👉 Topic 07: ES6+ Classes & Modules](07_es6_classes_modules.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
