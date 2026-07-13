# 🗂️ Topic 05: Lists, Keys & Conditionals

Welcome back, dynamic renderer! In this chapter, we will learn about **Lists, Keys, and Conditional Rendering**. In web applications, content is rarely static. We need to display lists of items from arrays and hide or show elements based on user settings. We will learn how to use JavaScript arrays in JSX and why React requires a unique `key` identifier for list items to avoid UI bugs.

---

## 🏠 The Big Picture & Real-Life Example

### 🧥 The Dry-Cleaning Coat Hanger Labels & The Entry Gate
Imagine you are managing a dry-cleaning shop:
* **List Rendering (The Hanger Racks)**: You have a list of 100 coats. Instead of writing 100 separate coat display cases in your store blueprint, you loop through your inventory logs and hang each coat on the rack sequentially.
* **The Key Prop (The Hanger Label)**: You attach a unique barcode tag (**`key`**) to every coat hanger: `"COAT-101"`, `"COAT-102"`.
  * If a customer removes a coat from the middle, you don't shift all coats and rename their hangers. You look at the barcode and know exactly which coat was removed.
  * If you don't use barcodes and just count hangers by index (Hanger 1, Hanger 2, Hanger 3), and you pull coat 2 out, Hanger 3 becomes Hanger 2. The database gets confused and hands the wrong coat to the next customer (**Index Render Bug**)!
* **Conditional Rendering (The VIP Gate)**: You have a gate at the entrance:
  * **The Ternary Switch (`? :`)**: If a customer has a VIP badge, the gate opens a velvet door; otherwise, it opens a standard turnstile.
  * **The Short-Circuit Gate (`&&`)**: If it starts raining, the gate opens the umbrella stand automatically. If it isn't raining, the stand is hidden.

---

## 🔬 Let's Look Closer

### 1. List Rendering with `.map()`
To render arrays inside JSX, we use the standard JavaScript `.map()` method to transform data arrays into arrays of JSX elements:
```jsx
const fruits = ['Apple', 'Banana', 'Orange'];

const fruitList = fruits.map((fruit, index) => (
    <li key={fruit}>{fruit}</li> // Always provide a unique key!
));
```

### 2. Why are Keys Important?
During reconciliation, React compares child elements. Keys tell React which elements match across render sweeps. Without keys, if you sort or prepend items in an array, React will re-evaluate and re-render every item in the list instead of simply moving the DOM nodes, degrading performance and breaking input state focus.

### 3. Conditional Rendering Patterns
* **Ternary Operator (`condition ? trueJSX : falseJSX`)**: Used when you want to render one of two options.
* **AND Operator (`condition && trueJSX`)**: Used when you want to render an element *only* if the condition is true.

---

## 💻 Code Sandbox

Let's write a dynamic list component with item deletions and conditional views.

```jsx
import React, { useState } from 'react';

export default function TaskList() {
    // 1. Array state
    const [tasks, setTasks] = useState([
        { id: 1, text: 'Study Java & Spring Boot', completed: true },
        { id: 2, text: 'Learn React Components', completed: false },
        { id: 3, text: 'Practice SQL Window Functions', completed: false },
    ]);

    const [showOnlyPending, setShowOnlyPending] = useState(false);

    // Delete handler
    const handleDelete = (idToDelete) => {
        setTasks(prev => prev.filter(task => task.id !== idToDelete));
    };

    // Filter list based on toggle state
    const displayedTasks = showOnlyPending 
        ? tasks.filter(t => !t.completed) 
        : tasks;

    return (
        <div style={{ padding: '20px' }}>
            <h2>My Tasks</h2>
            
            {/* Toggle button */}
            <button onClick={() => setShowOnlyPending(!showOnlyPending)}>
                {showOnlyPending ? "Show All Tasks" : "Filter: Show Pending Only"}
            </button>

            {/* Conditional view: Empty list check */}
            {displayedTasks.length === 0 ? (
                <p style={{ color: 'gray', marginTop: '10px' }}>No tasks found!</p>
            ) : (
                <ul>
                    {displayedTasks.map((task) => (
                        // unique ID used as key
                        <li key={task.id} style={{ margin: '8px 0' }}>
                            <span style={{ textDecoration: task.completed ? 'line-through' : 'none' }}>
                                {task.text}
                            </span>
                            
                            {/* Short-circuit conditional rendering: show badge if completed */}
                            {task.completed && <span style={{ color: 'green', marginLeft: '10px' }}>(Done!)</span>}
                            
                            <button onClick={() => handleDelete(task.id)} style={{ marginLeft: '15px' }}>
                                Delete
                            </button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
```

---

## 🧠 Points to Remember

* **Do not use `Math.random()` as a key**. Keys must be stable across renders. If keys change randomly on every render, React will recreate all list DOM nodes from scratch, degrading speed and wiping out text field cursor focuses.
* **Avoid using array index as keys** if the list can be sorted, filtered, or items can be prepended. Using index causes React to mismap inputs and styles if the item order changes.
* In JavaScript, the number `0` is falsy. If you write `{list.length && <List />}` and the list is empty, the browser will render the digit `0` on screen! To prevent this, convert it to a boolean: `{list.length > 0 && <List />}`.

---

## 📖 Key Definitions

* **Key Prop**: A unique string/number attribute passed to elements inside a rendered array that helps React identify which items have changed, been added, or been removed.
* **List Rendering**: The process of looping through an array of data inside JSX (typically using `.map()`) to return a list of sibling components.
* **Conditional Rendering**: The process of displaying different JSX elements based on dynamic conditions using logical operators (`&&`, `? :`).
* **Reconciliation Index Fallback**: A default behavior where React uses the array index as the key if no key is provided, which can lead to rendering bugs on sorting.
* **Short-Circuit Evaluation**: The JS logical operator `&&` pattern used to render an element only when a boolean condition is true.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **How do you render a list of elements in React?**
   * *Answer*: By using the JavaScript `.map()` array method inside JSX to loop through data and return elements.
2. **What is the purpose of the `key` prop in React lists?**
   * *Answer*: Keys help React identify which items have changed, been added, or been removed, optimizing Virtual DOM reconciliation.
3. **What is conditional rendering?**
   * *Answer*: The process of rendering different JSX elements based on boolean conditions using operators like `&&` or `? :`.
4. **Can you use a `for` loop inside JSX to render lists?**
   * *Answer*: No, because JSX is compiled to function arguments and only accepts expressions. You must use `.map()` or pre-build the list array outside JSX.
5. **What happens if you don't provide a `key` prop inside lists?**
   * *Answer*: React will show a console warning: "Each child in a list should have a unique 'key' prop" and fall back to using indices, which can cause bugs.
6. **Can two elements in the same list share the same key?**
   * *Answer*: No, keys must be unique among sibling elements. Shared keys cause React to mismap DOM updates.
7. **What is a Ternary Operator and how is it used in React?**
   * *Answer*: An inline conditional statement (`condition ? trueJSX : falseJSX`) used to render one of two layouts in JSX.
8. **What is the short-circuit (`&&`) operator used for?**
   * *Answer*: To render a JSX block only if a condition is true, bypassing rendering if the condition is false.
9. **Why is using array indexes as keys discouraged?**
   * *Answer*: Because if items are sorted, deleted, or prepended, their index changes. This causes React to map state to the wrong DOM elements.
10. **How do you render a default screen if an API returns an empty array?**
    * *Answer*: By checking array length: `{items.length === 0 ? <EmptyScreen /> : <List />}`.
11. **Can you use `if-else` blocks directly inside JSX?**
    * *Answer*: No, because `if-else` is a statement, not an expression. You must use ternary operators or move the logic outside the return block.
12. **How do you write a conditional style in React?**
    * *Answer*: By passing a template literal or ternary: `<div className={isActive ? "active" : "inactive"}>`.
13. **What value does React render if a conditional returns `null`?**
    * *Answer*: React renders nothing (null is treated as a blank space in the DOM).
14. **How do you hide a component entirely from rendering?**
    * *Answer*: By making the component return `null` from its render function.
15. **What happens if a key is a random number (e.g. `Math.random()`)?**
    * *Answer*: The key changes on every render. React will destroy the old DOM node and build a new one, breaking input focus and performance.
16. **Do keys need to be globally unique across the whole app?**
    * *Answer*: No, keys only need to be unique among their sibling elements inside the same array.
17. **How do you render a list of numbers from 1 to N dynamically?**
    * *Answer*: By creating an array first: `[...Array(N)].map((_, i) => <li key={i}>{i+1}</li>)`.
18. **Can you use a switch statement for conditional rendering?**
    * *Answer*: Yes, but you must write the switch block outside the JSX return statement or invoke it inside an immediately-invoked function (IIFE).
19. **What is the difference between `&&` and `||` in JSX?**
    * *Answer*: `&&` renders JSX if the condition is **true**. `||` renders JSX (or a fallback) if the condition is **false/falsy**.
20. **Can you pass a key prop down to a child component to read it?**
    * *Answer*: No, `key` is reserved by React. If you need the key value inside the child component, you must pass it under a different prop name (e.g. `id`).

### 🟡 Intermediate Questions (21-40)

21. **Explain the reconciliation bugs that arise when using array indices as keys for lists with inputs.**
    * *Answer*: If you delete index 0, the item at index 1 shifts to index 0. Because React relies on key matching, it retains the DOM elements at index 0—including user typed values—and updates only the labels, mismatching text inputs.
22. **Why does the logical expression `{count && <Label />}` render the number `0` if count is 0?**
    * *Answer*: In JS, `0 && expression` evaluates to `0` (the first falsy value). React doesn't print `false` or `null`, but it prints numbers, rendering `0`. Fix: `{count > 0 && <Label />}`.
23. **What is the difference between conditional rendering and changing element visibility via CSS (e.g. `display: none`)?**
    * *Answer*: Conditional rendering destroys and recreates the component and its DOM nodes (resets state). CSS visibility keeps nodes in the DOM, retaining their state and inputs in memory.
24. **How does React optimize list diffing when prepending an item using unique keys vs indices?**
    * *Answer*: With unique keys, React sees the old keys are intact, shifts the existing DOM nodes down, and inserts the new node at the top. With indices, React re-renders every single node because their index mapping changed.
25. **Can you use React Fragments as parent containers for list items, and do they accept keys?**
    * *Answer*: Yes, but you must use the full syntax `<React.Fragment key={item.id}>` since the short syntax `<>` does not support attributes or keys.
26. **Explain what an Immediately Invoked Function Expression (IIFE) is used for in conditional rendering.**
    * *Answer*: It allows running complex javascript statements (like `if-else` or `switch`) directly inside JSX: `{( () => { switch(status) { ... } })()}`.
27. **How would you sort a list in React state without causing infinite render loops?**
    * *Answer*: Sort the array during render based on a sorting state key (e.g., `const sorted = [...items].sort(...)`), rather than mutating the original state array directly.
28. **Why is it a bad practice to mutate the original array inside a render loop (e.g., `items.reverse()`)?**
    * *Answer*: Because `reverse()` mutates the array in-place, changing the component state directly during rendering, violating React's purity rules.
29. **What is the significance of the `type` field in the key reconciliation algorithm?**
    * *Answer*: If a child element has the same key but its `type` changes (e.g., changing from `<li>` to `<p>`), React immediately destroys the node and does not attempt to reuse it.
30. **Explain how to render nested lists (lists inside lists) in JSX.**
    * *Answer*: By nesting `.map()` calls:
      ```jsx
      categories.map(cat => (
          <div key={cat.id}>
              {cat.items.map(item => <p key={item.id}>{item.name}</p>)}
          </div>
      ))
      ```
31. **Does React keep state for a component if it is unmounted by a conditional rendering switch?**
    * *Answer*: No, unmounting deletes the component instance and clears its state. When remounted, its state resets to the initial value.
32. **How do you conditional render multiple elements using the short-circuit operator?**
    * *Answer*: Group them inside a parent Element or Fragment: `{condition && (<> <h2>Title</h2> <p>Text</p> </>)}`.
33. **What is the benefit of extracting list items into separate components?**
    * *Answer*: Performance and readability. You can wrap list item components in `React.memo` to prevent sibling elements from re-rendering when only one item changes.
34. **How does React's diffing algorithm match keys when list items are moved around?**
    * *Answer*: It stores sibling keys in a hash map, allowing it to quickly find nodes that moved index positions and reposition them in the DOM without recreating them.
35. **What is the nullish coalescing operator (`??`) used for in React?**
    * *Answer*: To provide a fallback value for missing data: `<p>{userData.nickname ?? userData.username}</p>`.
36. **Why should keys be string or integer data types?**
    * *Answer*: Because keys are internally stored as object properties in React's reconciliation dictionary, requiring serializable values.
37. **Can you define a key on a component itself, or must it be on the outer JSX wrapper element?**
    * *Answer*: The key must be defined on the top-level element returned inside the `.map()` loop, whether it is a HTML tag or a custom React component.
38. **How does conditional rendering affect the execution of hooks?**
    * *Answer*: If you wrap components inside conditionals, hooks inside those components mount and unmount safely. However, you must never wrap hook calls *themselves* inside conditionals.
39. **Explain the performance risk of list rendering with huge arrays (e.g., 10,000 items).**
    * *Answer*: The browser will lag attempting to create 10,000 DOM elements. Resolved using **Windowing** or **Virtualization** libraries (like `react-window`) to only render items visible on screen.
40. **How does React handle components that render `undefined` conditionally?**
    * *Answer*: React will throw a runtime error: "Nothing was returned from render". You must return `null` instead of `undefined` to render empty elements.

### 🔴 Advanced Questions (41-50)

41. **Explain the internal two-pass algorithm React uses to reconcile sibling lists (First pass vs Second pass).**
    * *Answer*: During reconciliation, React runs a first pass to match nodes at matching indices. If it encounters a key mismatch, it stops the linear scan and enters a second pass, building a map of remaining keys to look up and move nodes in $O(N)$ time.
42. **Why does reusing DOM nodes during key-matching reconciliation preserve CSS transition animations and input focuses?**
    * *Answer*: Because the physical browser DOM nodes are shifted instead of destroyed. The browser retains the active element focus pointer and layout frames, keeping CSS animations smooth.
43. **Explain how changing the key prop of a single component can be used to reset its internal state (The Key Reset Pattern).**
    * *Answer*: If a component's key changes (e.g. from `<Card key="user1" />` to `<Card key="user2" />`), React treats it as a new component type instance, unmounting the old one and initializing the new state.
44. **What is the performance difference in Virtual DOM diffing when rendering array items that have no key prop versus index keys?**
    * *Answer*: None. If no key is provided, React automatically uses the index as the fallback key, meaning both trigger the same index-based reconciliation loop.
45. **How does the reconciler handle sibling lists where some items have keys and some do not?**
    * *Answer*: React will output warnings and treat items without keys as having null keys. During map matching, it can confuse items, causing unpredictable DOM swaps.
46. **Explain what the "Single-Child Reconciler" does when a list transitions from multiple elements to a single element.**
    * *Answer*: It checks if the single element's key matches any key in the previous sibling list. If it finds a match, it retains that node and schedules deletion tasks for all other siblings.
47. **Why does the diffing algorithm perform a shallow comparison of keys rather than evaluating the entire component prop tree first?**
    * *Answer*: Performance. Keys act as a quick identity check. If keys mismatch, React knows the element is different, bypassing the expensive comparison of all props inside the subtree.
48. **How does Server-Side rendering handle dynamic key generations?**
    * *Answer*: Keys must be deterministic. If you use random keys during server rendering, the client-side hydration will fail because the client-compiled keys won't match the server HTML.
49. **Explain how to implement virtualized list rendering manually using scroll offsets and viewport calculations.**
    * *Answer*: You monitor container scroll events. Calculate the visible index boundaries: `Math.floor(scrollTop / rowHeight)`. Render only the slice of the array that falls within this boundary, positioning items using CSS transforms.
50. **What is the structural difference in Fiber nodes when rendering a list of fragments vs a list of host components?**
    * *Answer*: A list of fragments creates an intermediate fiber node for the fragment container, which has a `child` pointer to its elements but returns no host DOM instance, requiring React to traverse down to find the physical DOM node.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 04: Side Effects & useEffect](04_useeffect_side_effects.md)
* **Next Chapter**: [👉 Topic 06: Forms & Event Handling (useRef)](06_forms_useref.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
