# ✍️ Topic 06: Forms & Event Handling (useRef)

Welcome back, form designer! In this chapter, we will learn about **Forms, Event Handling, and the `useRef` hook**. Collecting user input is the core of interactive websites. We will compare two ways of managing forms: using React state to control every keystroke (Controlled Components) and letting the browser DOM store the input value (Uncontrolled Components) using the `useRef` hook.

---

## 🏠 The Big Picture & Real-Life Example

### 🏎️ The Remote-Controlled Car vs. The Wind-Up Toy Car
Imagine you are building a toy car interface:
* **Controlled Components (The Remote-Controlled Car)**: The car has a radio link to your hand transmitter (**React State**). Every millimeter you turn the remote wheel, the car wheels turn instantly. React knows the exact position of the tires at every fraction of a second. If you type a character in a controlled input, state updates, the component re-renders, and the input shows the state value.
* **Uncontrolled Components (The Wind-Up Toy Car)**: You pull the car backward and let it run. It rolls on its own and holds its own kinetic energy (**The browser DOM holds the value**). You don't track its gears in real-time. If you want to check where it stopped, you walk over, hold it, and inspect its position (**`useRef`**).
* **`useRef` (The Laser Pointer)**: You want to point at a specific Lego block in your tower without rebuilding the tower. You use a laser pointer (**Ref**) to target it. Updating the laser pointer dot doesn't shake the tower (**Does not trigger re-renders**), but gives you direct access to that block!

---

## 🔬 Let's Look Closer

### 1. Controlled vs. Uncontrolled Components

| Feature | Controlled (State) | Uncontrolled (Ref) |
|---|---|---|
| **Data Storage** | Stored in React State | Stored in the Browser DOM |
| **Real-time Validation** | Easy (validates on every keystroke) | Hard (must fetch value first) |
| **Re-renders** | Renders on every keystroke | Only renders on form submit |
| **Setup** | Uses `value` and `onChange` | Uses `ref` and `.current.value` |

### 2. The `useRef` Hook
`useRef` returns a mutable object with a `.current` property. Unlike state variables, modifying `.current` **does not** trigger a component re-render. It is used to:
1. Store values that persist across renders without triggering updates.
2. Directly access and manipulate browser DOM elements.

```jsx
import { useRef } from 'react';

const inputRef = useRef(null);
// Focus input DOM element directly:
inputRef.current.focus();
```

---

## 💻 Code Sandbox

Let's build a form demonstrating both Controlled inputs and Uncontrolled DOM references.

```jsx
import React, { useState, useRef } from 'react';

export default function UserRegistrationForm() {
    // 1. Controlled State for Username
    const [username, setUsername] = useState("");
    
    // 2. Uncontrolled Ref for Password (DOM reference)
    const passwordRef = useRef(null);

    const handleSubmit = (event) => {
        // Prevent page reload on submit!
        event.preventDefault();

        // Retrieve uncontrolled value from DOM node directly:
        const passwordValue = passwordRef.current.value;

        alert(`Submitted!\nUsername (Controlled): ${username}\nPassword (Uncontrolled): ${passwordValue}`);
    };

    return (
        <div style={{ padding: '20px', maxWidth: '300px' }}>
            <h2>Sign Up</h2>
            <form onSubmit={handleSubmit}>
                {/* CONTROLLED INPUT */}
                <div style={{ marginBottom: '10px' }}>
                    <label>Username: </label>
                    <input 
                        type="text" 
                        value={username}
                        onChange={(e) => setUsername(e.target.value)} 
                    />
                    <p style={{ fontSize: '12px', color: 'gray' }}>
                        Typing status: {username.length} characters
                    </p>
                </div>

                {/* UNCONTROLLED INPUT USING REF */}
                <div style={{ marginBottom: '15px' }}>
                    <label>Password: </label>
                    <input 
                        type="password" 
                        ref={passwordRef} // Attaching Ref to DOM element
                    />
                </div>

                <button type="submit">Register</button>
            </form>
        </div>
    );
}
```

---

## 🧠 Points to Remember

* **`useRef` modifications do not trigger renders**. If you write `myRef.current = 5`, the component does not re-render. You will not see this change on the screen until a state update or prop change forces a render.
* **`event.preventDefault()` is mandatory** inside submit callbacks. Without it, the browser executes its default action, reloading the whole page and resetting all React states.
* React event parameters are **Synthetic Events**, not native browser events. They are pooled for performance (in React 16 and below) and act as a unified, cross-browser wrapper.

---

## 📖 Key Definitions

* **Controlled Component**: An input element whose value is bound to and controlled by React state, updated via event handler callbacks.
* **Uncontrolled Component**: An input element whose value is managed directly by the browser DOM, retrieved using a reference hook on demand.
* **useRef Hook**: A React hook that returns a mutable ref object whose `.current` property persists across renders and does not trigger re-renders on update.
* **SyntheticEvent**: React's cross-browser wrapper around the browser's native event object, ensuring consistent event properties across all browsers.
* **preventDefault()**: A method called on event objects to prevent the browser's default action (like reloading the page on form submission).

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is a Controlled Component in React?**
   * *Answer*: An input element whose value is driven by React state and updated via an `onChange` handler callback.
2. **What is an Uncontrolled Component?**
   * *Answer*: An input element that stores its value directly in the browser DOM, accessed on demand using a Ref.
3. **What is the `useRef` hook used for?**
   * *Answer*: To persist mutable values across render cycles without triggering re-renders, and to reference DOM nodes directly.
4. **How does `useRef` differ from `useState`?**
   * *Answer*: Updating state triggers a re-render; updating the `.current` property of a Ref does not trigger a re-render.
5. **What is the purpose of `event.preventDefault()` in form submission?**
   * *Answer*: It stops the browser's default form submit behavior (which reloads the whole page), allowing React to process the inputs via JavaScript.
6. **How do you access the value of an uncontrolled input?**
   * *Answer*: By referencing the input element using a Ref and reading its value: `inputRef.current.value`.
7. **What is a Synthetic Event in React?**
   * *Answer*: A cross-browser wrapper around the browser's native event object, ensuring consistent event behaviors across all browsers.
8. **How do you programmatically focus an input field in React?**
   * *Answer*: By using a Ref attached to the input and calling: `inputRef.current.focus()`.
9. **Which attribute is used to link a Ref to a DOM element?**
   * *Answer*: The `ref` attribute (e.g. `<input ref={myRef} />`).
10. **What is the initial value of `ref.current` if no argument is passed to `useRef()`?**
    * *Answer*: It defaults to `undefined`.
11. **Can you use `useRef` to store values other than DOM elements?**
    * *Answer*: Yes, you can store any mutable value (numbers, timer IDs, objects) that you want to persist across renders.
12. **Why do controlled inputs require an `onChange` handler?**
    * *Answer*: Because if you set a `value` without an `onChange` handler, the input becomes read-only and users cannot type inside it.
13. **What is the default behavior of a button inside a `<form>` element?**
    * *Answer*: The button defaults to `type="submit"`, meaning clicking it triggers the form's submit action.
14. **How do you handle select dropdowns in a controlled form?**
    * *Answer*: By binding the `value` attribute of the `<select>` tag to a state variable and updating it via `onChange`.
15. **What is the difference between `onInput` and `onChange` in React?**
    * *Answer*: In standard HTML, `onChange` only fires when an input loses focus. React normalizes `onChange` to fire on every keypress, making it equivalent to `onInput`.
16. **Can a component have both controlled and uncontrolled inputs?**
    * *Answer*: Yes, you can mix both patterns in the same form depending on validation requirements.
17. **What happens if you pass `null` to `useRef(null)`?**
    * *Answer*: The `current` property of the returned object is initialized to `null`.
18. **How do you reset a controlled form?**
    * *Answer*: By setting all state variables back to their empty initial values (e.g. `setUsername("")`).
19. **How do you reset an uncontrolled form using a ref?**
    * *Answer*: By calling the native form reset method: `formRef.current.reset()`.
20. **Can you read a ref's value during the rendering phase?**
    * *Answer*: No, refs should not be read or written to during rendering because React has not committed the DOM changes yet.

### 🟡 Intermediate Questions (21-40)

21. **Why is it considered a best practice to avoid reading/writing `ref.current` during the render phase?**
    * *Answer*: Because React rendering must be pure. Reading/writing refs during render introduces side effects and can cause visual inconsistencies in concurrent rendering.
22. **What is Event Pooling, and how did React 17 change it?**
    * *Answer*: In React 16 and below, SyntheticEvent objects were pooled and reused to save memory. In React 17+, event pooling was removed because modern browsers handle garbage collection efficiently, making async event access safe.
23. **How do you create a custom Hook that tracks the previous value of a state variable using `useRef`?**
    * *Answer*: By storing the state value in a ref inside an effect that executes *after* rendering:
      ```jsx
      function usePrevious(value) {
          const ref = useRef();
          useEffect(() => { ref.current = value; }, [value]);
          return ref.current;
      }
      ```
24. **What is `forwardRef` in React, and when should you use it?**
    * *Answer*: A utility wrapper that allows a parent component to pass a ref down through a custom child component to access its internal DOM node (e.g. custom input).
25. **Explain the difference between `useRef` and declaring a mutable global variable outside the component file.**
    * *Answer*: A global variable is shared across all instances of the component. `useRef` creates a private, isolated instance of the ref for each mounted component.
26. **What is the `useImperativeHandle` hook, and how does it relate to `forwardRef`?**
    * *Answer*: A hook used to customize the instance value that is exposed to parent components when using refs, allowing you to expose custom functions instead of the raw DOM node.
27. **How do you handle file uploads in React forms?**
    * *Answer*: File inputs are always uncontrolled. You must use `useRef` to access the file array from the input element: `fileRef.current.files[0]`.
28. **Explain how to debounce a search input using a controlled component.**
    * *Answer*: Bind the input to a state variable. Inside a `useEffect` watching that state, start a timer that executes the search, and return a cleanup function to clear it.
29. **What is the risk of using Uncontrolled components for real-time password strength validation?**
    * *Answer*: Uncontrolled inputs do not trigger re-renders. You cannot display real-time feedback (like character counts or validation badges) because the UI doesn't update on type.
30. **How do you bind multiple inputs to a single state handler object in a controlled form?**
    * *Answer*: By using computed property names based on the input's `name` attribute:
      ```jsx
      const handleChange = (e) => {
          const { name, value } = e.target;
          setForm(prev => ({ ...prev, [name]: value }));
      };
      ```
31. **What is the difference between `event.target` and `event.currentTarget` in React event handlers?**
    * *Answer*: `event.target` is the element that triggered the event (the origin). `event.currentTarget` is the element that the event listener is attached to.
32. **Can you attach a Ref to a functional component without using `forwardRef`?**
    * *Answer*: No, attaching a ref to a functional component directly will fail and throw a warning because functional components do not have instances.
33. **Explain how React implements Event Delegation.**
    * *Answer*: React does not attach event listeners to individual DOM nodes. It attaches a single listener to the root container node (`#root`), capturing events as they bubble up.
34. **How do you access the native browser event from a React SyntheticEvent?**
    * *Answer*: By referencing the `event.nativeEvent` property on the SyntheticEvent object.
35. **Why does a ref returned by `useRef` persist across renders?**
    * *Answer*: Because React stores the ref object on the component's Fiber node, preserving the same object instance throughout the component's lifecycle.
36. **What is the difference between passing a ref object and a callback ref to the `ref` attribute?**
    * *Answer*: A ref object is a simple mutable container. A callback ref is a function `(el) => {}` that React executes whenever the DOM element mounts or unmounts.
37. **How do you clear a text input programmatically using a Ref?**
    * *Answer*: By modifying its value directly: `inputRef.current.value = ""`.
38. **Explain what the `useRef` callback pattern is used for.**
    * *Answer*: It is used to perform side effects (like measuring layout sizes or binding external libraries) the moment a DOM element is mounted.
39. **What is the benefit of using React event handlers instead of adding native event listeners (`addEventListener`) inside effects?**
    * *Answer*: React event handlers automatically handle cleanups on unmount, implement event delegation to save memory, and integrate with the SyntheticEvent wrapper.
40. **How does React handle focus tracking on inputs during re-renders?**
    * *Answer*: React matches the active element before and after reconciliation. If the node is preserved (matching keys/types), the browser retains active focus.

### 🔴 Advanced Questions (41-50)

41. **Explain why React does not use event delegation at the `document` level anymore starting from React 17.**
    * *Answer*: In React 17, event delegation was moved to the root container (`#root`). This prevents event propagation conflicts when nesting multiple React apps (e.g. legacy app inside a modern app wrapper).
42. **How does `useImperativeHandle` shield child components from parent exposure?**
    * *Answer*: By intercepting the parent's ref. Instead of returning the raw DOM node, you define a custom object (e.g. containing only a `.focus()` method), hiding the rest of the child's DOM properties.
43. **Explain the relationship between the Fiber Node's `memoizedState` and the ref object returned by `useRef`.**
    * *Answer*: For a ref hook, React stores a descriptor object in `memoizedState` whose `current` property points to the mutable reference, keeping it stable across renders.
44. **What is the performance benefit of using Uncontrolled components in massive forms with hundreds of input fields?**
    * *Answer*: It avoids rendering bottlenecks. Controlled components trigger a full re-render of the form on every keystroke. Uncontrolled components let the browser handle typing, executing zero React renders until submission.
45. **How does React handle input state restoration during Concurrent rendering pauses?**
    * *Answer*: If React discards a render branch, the state updates are rolled back. However, if the input is uncontrolled, the DOM already contains the typed keys, which can lead to UI desynchronization.
46. **Explain what "Passive Events" are and how React configures them for touch and wheel handlers.**
    * *Answer*: Passive events tell the browser that the event handler will not call `preventDefault()`, allowing the browser to scroll smoothly without waiting for JS, configured internally by React.
47. **How would you implement a custom virtual keyboard in React utilizing controlled inputs?**
    * *Answer*: Bind the inputs to state. The virtual keyboard triggers click handlers that append string characters to the state variables, triggering re-renders to display updates.
48. **Explain what a Callback Ref is and why it runs with `null` when a component unmounts.**
    * *Answer*: A callback ref is a function `el => {}`. When the DOM element mounts, React calls it with the DOM node. When it unmounts, React calls it with `null` to allow you to release references.
49. **How does React map form submission events when a form contains nested custom submit buttons?**
    * *Answer*: React listens for bubbling submit events. Clicking any button of type `submit` bubbles up to the parent `<form>`, triggering its Synthetic `onSubmit` handler.
50. **What is the internal design of the SyntheticEvent pooling mechanism that was deprecated in React 17?**
    * *Answer*: React kept an array of event objects. When an event fired, it populated a pooled object. Once the handler finished, all properties were wiped. This caused async callbacks to read null properties unless `event.persist()` was called.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 05: Lists, Keys & Conditionals](05_lists_keys_conditionals.md)
* **Next Chapter**: [👉 Topic 07: Performance & Memoization](07_performance_memoization.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
