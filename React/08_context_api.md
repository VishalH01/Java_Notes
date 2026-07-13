# 🔌 Topic 08: Context API

Welcome back, state broadcaster! In this chapter, we will learn about the **Context API**. Passing props down through multiple layers of components (Prop Drilling) is tedious and clutters your codebase. The Context API solves this: it acts as a global radio transmitter. We will learn how to initialize context, broadcast messages using Providers, and tune in using the `useContext` hook from any deeply nested child component.

---

## 🏠 The Big Picture & Real-Life Example

### 📻 The City Radio Station
Imagine you are building a city communication system:
* **Prop Drilling (The Bucket Brigade)**: You want to send a news alert from the Mayor (**Parent Component**) to a citizen inside a house (**Deep Child Component**). You stand 10 security guards in a line and pass the message notebook guard-to-guard. The guards in the middle don't care about the news, but they must keep holding and passing the notebook (**Prop Drilling**).
* **Context API (The Radio Station)**: The Mayor builds a radio tower (**`createContext`**).
  * **The Broadcaster (Provider)**: The Mayor sets the broadcast frequency to 98.1 FM and speaks into the microphone (**`<Provider value={news} />`**).
  * **The Listeners (Consumers)**: Any citizen inside any house can turn on a radio receiver, tune in to 98.1 FM (**`useContext`**), and listen to the news instantly. The intermediate guards are bypassed entirely!

---

## 🔬 Let's Look Closer

### 1. Creating Context
First, initialize a context object using `createContext()`. You can pass a default value to be used if a component is rendered outside a Provider.
```jsx
import { createContext } from 'react';

const ThemeContext = createContext('light'); // 'light' is the fallback value
```

### 2. The Provider
The Provider component wraps your component tree and supplies the shared value to all children using the `value` prop:
```jsx
<ThemeContext.Provider value="dark">
    <AppLayout /> {/* All components inside can access "dark" */}
</ThemeContext.Provider>
```

### 3. Consuming Context with `useContext`
In functional components, we read the active context value using the `useContext` hook:
```jsx
import { useContext } from 'react';

const activeTheme = useContext(ThemeContext); // Returns "dark"
```

---

## 💻 Code Sandbox

Let's build a Theme Switcher application utilizing a Custom Context Provider.

```jsx
import React, { createContext, useState, useContext } from 'react';

// 1. Create the Theme Context
const ThemeContext = createContext(null);

// 2. Custom Provider Component to encapsulate state and logic
export function ThemeProvider({ children }) {
    const [theme, setTheme] = useState("light");

    const toggleTheme = () => {
        setTheme(prev => prev === "light" ? "dark" : "light");
    };

    return (
        // Broadcasting theme state and toggle callback function
        <ThemeContext.Provider value={{ theme, toggleTheme }}>
            {children}
        </ThemeContext.Provider>
    );
}

// 3. Child Component: Header (Tunes in to Context)
function Header() {
    const { theme } = useContext(ThemeContext);
    return (
        <header style={{
            padding: '10px',
            backgroundColor: theme === "light" ? "#eee" : "#333",
            color: theme === "light" ? "#000" : "#fff"
        }}>
            <h2>Dynamic App Header</h2>
            <p>Active Theme: {theme}</p>
        </header>
    );
}

// 4. Child Component: SettingsPanel (Toggles Context State)
function SettingsPanel() {
    const { theme, toggleTheme } = useContext(ThemeContext);
    return (
        <div style={{ marginTop: '15px' }}>
            <p>Modify UI Theme:</p>
            <button onClick={toggleTheme}>
                Switch to {theme === "light" ? "Dark Mode" : "Light Mode"}
            </button>
        </div>
    );
}

// 5. App Component wrapping layout inside ThemeProvider
export default function App() {
    return (
        <ThemeProvider>
            <div style={{ fontFamily: 'sans-serif', padding: '20px' }}>
                <Header />
                <SettingsPanel />
            </div>
        </ThemeProvider>
    );
}
```

---

## 🧠 Points to Remember

* **Unnecessary re-renders**. By default, whenever the `value` of a Context Provider changes, **every single component** that consumes that context will re-render immediately. To prevent performance lag, split large contexts into separate providers (e.g. `UserProvider`, `ThemeProvider`).
* **Context is not a replacement for Redux or state managers**. Context does not store data; it only transports state. It is best used for static or low-frequency updates (like theme settings, localizations, or user logins).
* You can only consume context inside components that are rendered *under* the Provider in the tree. If you call `useContext` above or outside the Provider, it returns the fallback default value.

---

## 📖 Key Definitions

* **Context API**: A built-in React feature used to share data globally across the component tree without manually passing props down.
* **createContext**: A React function used to initialize a new Context object containing a Provider and a Consumer.
* **Provider Component**: A component that wraps a section of the component tree and supplies a `value` prop to all nested consumers.
* **useContext Hook**: A React hook used inside functional components to read the current value of a specified Context.
* **Consumer Component**: A legacy wrapper component that uses a render prop callback to access context values inside class components.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is the Context API in React?**
   * *Answer*: A built-in React feature used to share data globally across the component tree without having to pass props down through intermediate components.
2. **What is the difference between Context API and Prop Drilling?**
   * *Answer*: Prop drilling passes data down manually through every parent-child link. The Context API broadcasts data globally so any child can read it directly.
3. **Name the three main parts of the Context API.**
   * *Answer*: (1) Creating context (`createContext`), (2) Broadcasting context (`Provider`), and (3) Consuming context (`useContext`).
4. **How do you create a new Context object?**
   * *Answer*: By calling the React function: `const MyContext = React.createContext(defaultValue);`.
5. **What is the role of the Context Provider?**
   * *Answer*: It wraps a component tree branch and supplies the shared value to all children using the `value` prop.
6. **What does the `useContext` hook do?**
   * *Answer*: It reads the current value of a specified Context object inside functional components.
7. **What is the fallback default value of a Context?**
   * *Answer*: The value passed to `createContext(defaultValue)`, used if a component consumes context without being wrapped in a Provider.
8. **Can a component consume multiple contexts?**
   * *Answer*: Yes, a component can call `useContext` multiple times to consume different context sources.
9. **Is Context API a replacement for State?**
   * *Answer*: No. Context only acts as a transport channel. The data itself must still be stored in React state (e.g. `useState`) somewhere in the parent tree.
10. **Where in the component tree can you consume context?**
    * *Answer*: Only inside components that are descendants (rendered under) the matching Context Provider.
11. **What is the legacy `Consumer` component?**
    * *Answer*: A wrapper component (`<Context.Consumer>`) used in class components before hooks were introduced to read context values via render props.
12. **Can you pass objects as context values?**
    * *Answer*: Yes, context values can accept any JavaScript type, including objects, arrays, and functions.
13. **How do you update context values from a child component?**
    * *Answer*: By passing a state updater function (like `setTheme`) inside the context value object, allowing children to call it.
14. **What is a custom context hook?**
    * *Answer*: A custom hook (e.g. `useTheme()`) that wraps `useContext` and does error checks to simplify consumer imports.
15. **Does context persist data across page reloads?**
    * *Answer*: No, context value data is stored in JS memory and resets on reload unless synchronized with local storage.
16. **Why should you write default values in `createContext`?**
    * *Answer*: To prevent runtime crashes and simplify component testing by letting you render children independently without mock Providers.
17. **Can a Provider render another Provider of the same type?**
    * *Answer*: Yes. If nested, the consumer component will read the value of the closest matching Provider above it in the tree.
18. **Is Context API built into React?**
    * *Answer*: Yes, it is a core feature of the React library and does not require installing any external NPM packages.
19. **What is the main drawback of using a single global Context for all app state?**
    * *Answer*: Performance. A single change to any global key triggers re-renders across all components consuming that context.
20. **Can you define context outside of components?**
    * *Answer*: Yes, Context objects must be defined in module scope (usually exported from a separate file) so multiple components can import them.

### 🟡 Intermediate Questions (21-40)

21. **Explain the performance bottleneck associated with Context Provider value changes.**
    * *Answer*: Whenever a Provider's value changes, React triggers re-renders for all components consuming that context, bypassing `React.memo` prop optimizations.
22. **How do you prevent unnecessary context re-renders when passing an object value (e.g., `value={{ user, login }}`)?**
    * *Answer*: Memoize the object value using `useMemo`:
      ```jsx
      const value = useMemo(() => ({ user, login }), [user]);
      ```
23. **What is the difference between Context API and state managers like Redux?**
    * *Answer*: Context API simply transports state down the tree. Redux is a full state management framework featuring middleware, action dispatchers, and optimized state diffing selectors.
24. **Can you use Context to share state between sibling components?**
    * *Answer*: Yes, by lifting the state to their common parent, wrapping the parent in a Context Provider, and letting siblings consume it.
25. **Explain how to build a Custom Provider component in React.**
    * *Answer*: Create a component that maintains its own `useState` state, wraps its children in a Provider, and passes the state and setter inside the `value` prop:
      ```jsx
      function UserProvider({ children }) {
          const [user, setUser] = useState(null);
          return <UserContext.Provider value={{ user, setUser }}>{children}</UserContext.Provider>;
      }
      ```
26. **What is the risk of nesting too many Context Providers in a single application (Provider Soup)?**
    * *Answer*: It makes code hard to read and debug. It can be resolved by combining providers into a single wrapper component.
27. **Why does calling `useContext` outside of a Provider render block fail to retrieve active state?**
    * *Answer*: Because `useContext` scans up the component hierarchy. If it reaches the root without finding a matching Provider, it falls back to the default value.
28. **How would you write a custom context hook that throws an error if consumed outside its Provider?**
    * *Answer*:
      ```jsx
      function useTheme() {
          const context = useContext(ThemeContext);
          if (!context) throw new Error("useTheme must be used within a ThemeProvider");
          return context;
      }
      ```
29. **What is the benefit of splitting Context into separate State and Dispatch Providers?**
    * *Answer*: Performance. Components that only trigger actions consume the Dispatch context and skip re-renders when the state context value updates.
30. **Explain how Context API interacts with React's Concurrent rendering mode.**
    * *Answer*: Context updates are handled within React's render lanes, ensuring value changes propagate synchronously to prevent "tearing" during render pauses.
31. **Can a Context Provider value accept a ref object?**
    * *Answer*: Yes, but since ref updates do not trigger re-renders, components consuming the context will not update when the ref's `.current` changes.
32. **Does Context API support selectors to consume only a specific key of the value object?**
    * *Answer*: No. If a component consumes context, it will re-render if *any* property inside the context value object changes.
33. **How does the reconciler determine if a context value has changed?**
    * *Answer*: By executing an `Object.is()` comparison on the old and new `value` props passed to the Provider component.
34. **What is the difference between class component Context consumption (`static contextType`) and `useContext`?**
    * *Answer*: `contextType` only allows a class component to consume a **single** context. Functional components using `useContext` can consume as many contexts as needed.
35. **Why should we avoid passing inline arrow functions directly to context values?**
    * *Answer*: Because inline functions are recreated on every render, yielding new references that force consumers to re-render.
36. **Explain what the React dev tools show for Context components.**
    * *Answer*: They display the Context name (e.g. `MyContext.Provider` or `MyContext.Consumer`) and show the active values being broadcast.
37. **Can you modify context values directly from a testing environment?**
    * *Answer*: Yes, by wrapping the component under test inside a mock Provider, supplying custom test values in the `value` prop.
38. **Explain how to combine Context API with `useReducer` to create a lightweight Redux clone.**
    * *Answer*: Declare `const [state, dispatch] = useReducer(reducer, init)` inside a custom Provider, and pass both `state` and `dispatch` as the context value.
39. **Does Context API work across portals (`ReactDOM.createPortal`)?**
    * *Answer*: Yes. Even though portals render DOM nodes in different parts of the HTML body, they remain part of the React component tree and can consume context.
40. **How does context value propagation bypass intermediate components wrapped in `React.memo`?**
    * *Answer*: React reconciles context consumers directly. If a parent re-renders and the context value changes, React forces updates on all consumer nodes, ignoring `React.memo` checks on intermediate containers.

### 🔴 Advanced Questions (41-50)

41. **Explain the internal data representation of Context inside a Fiber Node's update queues.**
    * *Answer*: A context consumer creates a dependency object on its fiber. React links these dependencies in a list. When a Provider updates, React scans the fiber tree to locate nodes with matching dependencies and schedules updates on them.
42. **Why does the lack of a selector mechanism in the Context API make it suboptimal for highly dynamic, high-frequency state updates?**
    * *Answer*: Because a change to any property in a large context value object forces every consuming component to re-render, creating CPU bottlenecks that state selectors prevent.
43. **How does the `useSyncExternalStore` hook solve performance issues that the Context API cannot address?**
    * *Answer*: It subscribes directly to external data stores, using selectors to only trigger re-renders when the selected slice of data changes, bypassing context propagation overhead.
44. **Explain how React schedules context propagation in Concurrent Mode when multiple updates are queued.**
    * *Answer*: React prioritizes context propagation using bitmask lanes. High-priority updates propagate immediately; low-priority updates are deferred, allowing user input to run first.
45. **What is the behavior of the fiber reconciler when a Context Provider component is unmounted and remounted elsewhere in the tree?**
    * *Answer*: React deletes the old Provider fiber. All descendants lose access to the provider's value, falling back to the default context value until they are mounted under the new Provider.
46. **How would you debug a performance issue where intermediate components are re-rendering due to context object reference changes?**
    * *Answer*: Use React DevTools Profiler to trace renders. Wrap the context value object in `useMemo` with primitive dependency arrays to stabilize the object reference.
47. **Explain the structural difference between `createContext` and the experimental `use` hook in React 19 for reading context.**
    * *Answer*: The `use` hook allows reading context conditionally (e.g. inside `if` blocks or loops), whereas `useContext` is a standard hook and must follow strict top-level rules.
48. **How does context propagation behave when a component uses a custom `shouldComponentUpdate` class lifecycle method?**
    * *Answer*: Context propagation ignores `shouldComponentUpdate`. If the context value changes, the component will re-render even if `shouldComponentUpdate` returns `false`.
49. **Why is it recommended to place Context Providers as close as possible to the consuming components rather than at the root of the application?**
    * *Answer*: To limit re-rendering scopes. Placing Providers at the root forces larger sub-trees to participate in reconciliation checks when the provider value updates.
50. **What is the internal mechanism React uses to merge default values when nested contexts share the same schema structure?**
    * *Answer*: React does not merge them. Contexts are looked up by their unique JavaScript object reference. The engine resolves the closest provider matching that specific reference pointer.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 07: Performance & Memoization](07_performance_memoization.md)
* **Next Chapter**: [👉 Topic 09: Routing & Custom Hooks](09_routing_custom_hooks.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
