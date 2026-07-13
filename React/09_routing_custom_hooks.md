# 🌐 Topic 09: Routing & Custom Hooks

Welcome, navigation architect! In this chapter, we will learn about **Client-Side Routing and Custom Hooks**. A real website needs multiple pages (e.g. Home, Profile, Settings) and reusable logic. We will learn how to set up routing using `react-router-dom` without triggering slow page reloads. We will also learn how to package React state and effects into reusable custom hooks, creating clean, modular code.

---

## 🏠 The Big Picture & Real-Life Example

### 🗺️ The Theme Park Map & The Electric Drill
Imagine you are visiting a massive tech theme park:
* **Client-Side Routing (The Theme Park Sections)**: The park has different areas: FutureLand, AquaPark, DinoLand. Instead of driving out of the park and buying a new entry ticket to visit a different section (Server-Side page reload), you walk through gates inside the park (**Client Routing**). The scenery changes instantly, but you stay inside the same main park gates (**SPA**).
* **The Route Guard (The VIP Gate)**: DinoLand is dangerous. A guard stands at the gate check: *"Do you have a VIP helmet?"* If yes, you enter; if not, he redirects you to the gift shop (**Route Guard Redirect**).
* **Custom Hooks (The Portable Power Tool)**: You notice that in every park section, engineers use the exact same motor-and-battery assembly to run water pumps, gates, and fans. Instead of rebuilding the motor from raw wires in every single spot, you package the motor parts into a portable power tool (**Custom Hook**). You carry it around, plug it in, and use it anywhere!

---

## 🔬 Let's Look Closer

### 1. Client-Side Routing (react-router-dom)
Unlike server routing (where the browser asks the server for a new HTML page on URL changes), client-side routing intercepts URL edits, reads the path, and replaces components inside the page DOM dynamically:
* **`BrowserRouter`**: The main wrapper that enables history tracking.
* **`Routes` & `Route`**: Defines which component renders for a specific URL path.
* **`Link`**: Intercepts clicks and routes the browser locally (replaces `<a>` tags).

```jsx
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';

function App() {
    return (
        <BrowserRouter>
            <nav>
                <Link to="/">Home</Link>
                <Link to="/about">About</Link>
            </nav>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/about" element={<About />} />
            </Routes>
        </BrowserRouter>
    );
}
```

### 2. Custom Hooks
A custom hook is a standard JavaScript function whose name **must** start with `use`. It can call other hooks (like `useState`, `useEffect`), allowing you to share stateful logic between components.
```jsx
// Custom Hook to fetch data:
function useFetch(url) {
    const [data, setData] = useState(null);
    useEffect(() => {
        fetch(url).then(res => res.json()).then(d => setData(d));
    }, [url]);
    return data;
}
```

---

## 💻 Code Sandbox

Let's write a custom hook that checks window resize dimensions and use it in a mock routing setup.

```jsx
import React, { useState, useEffect } from 'react';

// 1. CUSTOM HOOK: useWindowSize (Encapsulates resize listener state and effect)
function useWindowSize() {
    const [size, setSize] = useState({
        width: window.innerWidth,
        height: window.innerHeight
    });

    useEffect(() => {
        const handleResize = () => {
            setSize({ width: window.innerWidth, height: window.innerHeight });
        };

        window.addEventListener('resize', handleResize);
        
        // Cleanup listener on unmount
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    return size;
}

// 2. Component using the Custom Hook
export default function WindowDashboard() {
    // Calling our custom hook just like a standard hook!
    const { width, height } = useWindowSize();

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
            <h2>Screen Dimension Monitor</h2>
            <p>Responsive Layout helper utilizing a Custom Hook.</p>
            
            <div style={{
                padding: '10px',
                border: '1px solid black',
                backgroundColor: width < 600 ? '#ffe6e6' : '#e6ff99'
            }}>
                <p>Width: <strong>{width}px</strong></p>
                <p>Height: <strong>{height}px</strong></p>
                <p>Layout Mode: <strong>{width < 600 ? "Mobile View" : "Desktop View"}</strong></p>
            </div>
        </div>
    );
}
```

---

## 🧠 Points to Remember

* **Custom Hooks names must start with `use`**. If you name a custom hook `fetchData` instead of `useFetchData`, React's lint tools will not verify Hook Rules on it, which can lead to bugs.
* **State is not shared across Custom Hook instances**. If two components call `useWindowSize()`, they each receive their own private, independent state variables. The logic is shared, not the actual state data.
* **Always use `<Link>` instead of `<a href>`** when routing using `react-router-dom`. Standard `<a>` tags force the browser to request a fresh page from the server, wiping out the client React state.

---

## 📖 Key Definitions

* **Client-Side Routing**: A routing mechanism where the browser URL changes without triggering a full page reload from the server, handled by JS.
* **BrowserRouter**: A wrapper component from `react-router-dom` that uses the HTML5 history API to keep your UI in sync with the URL.
* **Custom Hook**: A JavaScript function whose name starts with "use" that can call other React hooks, encapsulating reusable stateful logic.
* **Route Guards**: A design pattern used to restrict access to certain client routes based on conditions (like user authentication status).
* **Dynamic Routing**: Routing where path matching parameters (like `/user/:id`) are captured dynamically to fetch and display specific records.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is client-side routing?**
   * *Answer*: A technique where page transitions are handled entirely in the browser by modifying the DOM and URL dynamically, preventing full page reloads.
2. **What package is commonly used for routing in React?**
   * *Answer*: `react-router-dom`.
3. **What is a Custom Hook in React?**
   * *Answer*: A JavaScript function whose name starts with `use` that can call other React hooks to extract and reuse stateful logic.
4. **Why must custom hooks start with the word `use`?**
   * *Answer*: To notify React's compiler and lint tools to check and enforce the Rules of Hooks inside the function.
5. **Do custom hooks share state between components using them?**
   * *Answer*: No, each component call to a custom hook creates an isolated, independent state instance.
6. **What does the `<Link>` component do in React Router?**
   * *Answer*: It renders an anchor tag but intercepts browser click events, performing a client-side route change without reloading the page.
7. **What does the `<Routes>` wrapper do?**
   * *Answer*: It acts as a switch, scanning its child `<Route>` elements to find and render the first route that matches the current URL.
8. **How do you define a fallback route for 404 pages?**
   * *Answer*: By writing a wildcard path: `<Route path="*" element={<NotFound />} />`.
9. **How do you read dynamic URL parameters (e.g. `/user/:id`)?**
   * *Answer*: By calling the `useParams()` hook from `react-router-dom` (e.g., `const { id } = useParams()`).
10. **How do you navigate programmatically in React Router?**
    * *Answer*: By calling the `useNavigate()` hook to get a navigation trigger function: `const navigate = useNavigate(); navigate('/home');`.
11. **Can custom hooks return arrays or objects?**
    * *Answer*: Yes, custom hooks are standard functions and can return any JavaScript type, including arrays, objects, values, or functions.
12. **What is the purpose of `<BrowserRouter>`?**
    * *Answer*: It is the top-level wrapper that uses the browser's HTML5 History API to synchronize the UI with URL modifications.
13. **How do you pass state query variables in a URL (e.g., `?search=react`)?**
    * *Answer*: By calling the `useSearchParams()` hook from `react-router-dom`.
14. **What is the difference between `useNavigate` and `Link`?**
    * *Answer*: `Link` is a JSX component for user clicks. `useNavigate` is a function hook used to navigate programmatically inside JS functions.
15. **Can custom hooks perform side effects?**
    * *Answer*: Yes, custom hooks can call `useEffect` to perform fetches, set timers, or bind event listeners.
16. **How do you write a custom hook that reads local storage?**
    * *Answer*: Define a state variable initialized by reading local storage, and use `useEffect` to write state updates back to local storage.
17. **Can a custom hook call another custom hook?**
    * *Answer*: Yes, custom hooks can compose and invoke other custom hooks.
18. **What does the `useLocation()` hook return?**
    * *Answer*: It returns the current URL location object containing details like `pathname`, `search`, and `hash`.
19. **What is an active link style helper in React Router?**
    * *Answer*: The `<NavLink>` component, which automatically adds an `active` class to the tag if its path matches the current URL.
20. **Can you write React routing using native browser window location events?**
    * *Answer*: Yes, by listening to `popstate` events, but using React Router is preferred because it handles nesting and parameter parsing.

### 🟡 Intermediate Questions (21-40)

21. **How do you implement Route Guarding (Protected Routes) in React Router?**
    * *Answer*: By creating a wrapper component that checks authentication status. If authenticated, it renders child elements using `<Outlet />`; otherwise, it redirects using `<Navigate to="/login" />`.
22. **What is the difference between client-side routing history modes (HashRouter vs BrowserRouter)?**
    * *Answer*: `BrowserRouter` uses standard URLs (e.g., `/profile`) but requires server configuration to redirect all requests to `index.html`. `HashRouter` uses hash anchors (e.g., `/#/profile`) which do not require server configuration because hashes aren't sent to servers.
23. **Explain how to build a custom hook `useDebounce` to delay search inputs.**
    * *Answer*:
      ```jsx
      function useDebounce(value, delay) {
          const [debouncedValue, setDebouncedValue] = useState(value);
          useEffect(() => {
              const handler = setTimeout(() => setDebouncedValue(value), delay);
              return () => clearTimeout(handler);
          }, [value, delay]);
          return debouncedValue;
      }
      ```
24. **What is the purpose of the `<Outlet />` component in React Router?**
    * *Answer*: It acts as a placeholder that renders the child routes of a nested parent route layout.
25. **How does lazy loading of routes work in React?**
    * *Answer*: By using `React.lazy()` and wrapping the `<Routes>` in `<Suspense fallback={<Loader />}>` to download page bundles only when a user navigates to them.
26. **Explain how custom hooks allow separating UI logic from rendering code.**
    * *Answer*: Components only handle layout JSX. Data fetching, inputs, and validation are delegated to custom hooks, keeping components clean and testable.
27. **What is the difference between `useParams` and `useSearchParams`?**
    * *Answer*: `useParams` reads path parameters `/user/:id` (returns object). `useSearchParams` reads query parameters `?search=test` (returns search object and setter).
28. **How does React Router prevent browser reloads when using nested links?**
    * *Answer*: It calls `event.preventDefault()` on anchor clicks, modifies the browser history using `window.history.pushState()`, and tells React to re-render.
29. **How would you write a custom hook `useToggle` that manages a boolean switch?**
    * *Answer*:
      ```jsx
      function useToggle(initial = false) {
          const [state, setState] = useState(initial);
          const toggle = () => setState(prev => !prev);
          return [state, toggle];
      }
      ```
30. **Explain the benefits of custom hooks in unit testing.**
    * *Answer*: You can test stateful logic independently from UI layouts using testing tools like `@testing-library/react-hooks` without mounting UI trees.
31. **What is dynamic routing in React Router?**
    * *Answer*: The process of defining routes with variables (e.g. `path="/post/:slug"`) so that a single component can render dynamic content based on parameters.
32. **Can a custom hook return JSX elements?**
    * *Answer*: Yes, but it is generally discouraged. Custom hooks should focus on data/logic, while components handle visual elements.
33. **Explain how the `useMatch()` hook works in React Router.**
    * *Answer*: It checks if a given URL path matches a specific pattern, returning detailed match information or `null` if it doesn't match.
34. **How do you handle scroll restoration on route changes in React Router?**
    * *Answer*: By adding a component that listens to route changes via `useLocation` and calls `window.scrollTo(0, 0)` inside a `useEffect` on change.
35. **What is the risk of using non-serializable data inside URL states?**
    * *Answer*: Functions or class instances passed in route states are lost when pages are refreshed or links shared because URL histories are serialized as JSON strings.
36. **How would you build a custom hook `useFetch` that supports request cancellation?**
    * *Answer*: Use an `AbortController` inside a `useEffect`. In the cleanup function, call `controller.abort()` to cancel pending fetches.
37. **What is the difference between `navigate('/path', { replace: true })` and standard push navigation?**
    * *Answer*: Standard push adds a new entry to the browser history stack. `replace: true` overwrites the current entry, preventing users from clicking back to it.
38. **Can you declare a custom hook that doesn't use any React hooks internally?**
    * *Answer*: Yes, but it is just a standard JavaScript helper function. Custom hooks are defined only when you need to encapsulate React hooks.
39. **How does React Router handle form actions starting from version 6.4+?**
    * *Answer*: By introducing loaders and actions, allowing components to declare data dependencies and write mutations directly within route configurations.
40. **How would you handle page redirection inside custom hooks?**
    * *Answer*: By importing `useNavigate` from React Router inside the custom hook and calling it when specific conditions are met.

### 🔴 Advanced Questions (41-50)

41. **Explain how React Router uses the browser's PopStateEvent to handle client-side back and forward navigation.**
    * *Answer*: React Router binds a listener to the window `popstate` event. When the user clicks the back button, the browser changes the history cursor. The listener fires, parses the new URL, and triggers a React state update to re-render the matching page component.
42. **Why does Server-Side rendering (SSR) of a React Router application require using `StaticRouter` instead of `BrowserRouter`?**
    * *Answer*: Because `BrowserRouter` relies on window APIs (like `history` and `location`) which do not exist on the server (Node.js). `StaticRouter` accepts a static URL string prop and computes matching paths in-memory on the server.
43. **Explain the closure capture bugs that occur when custom hooks return state updater callbacks that are not stabilized via `useCallback`.**
    * *Answer*: If a hook returns an unstabilized function, child components will recreate listeners on every render, causing memory leaks and executing effects in loop cycles.
44. **How does React Router handle route matching prioritization internally?**
    * *Answer*: React Router v6 uses a ranked routing algorithm. It scores routes based on specificity (e.g., static paths score higher than dynamic parameters, which score higher than wildcards), rendering the highest-ranked match regardless of declaration order.
45. **Explain how to design a custom state sync hook utilizing `useSyncExternalStore` for sharing state across browser tabs (via BroadcastChannel).**
    * *Answer*: Subscribe to a `BroadcastChannel`. Use `useSyncExternalStore` to connect React to the channel store, updating local tab states automatically when message events occur.
46. **What is the transition performance cost of loading lazy components on slow networks, and how does the `useTransition` hook mitigate it in React Router v6?**
    * *Answer*: Dynamic imports take time, causing Suspense to unmount page views and show loaders. `useTransition` allows React to build the next page in memory, keeping the current page active and responsive until the lazy chunk is ready.
47. **How does the reconciler handle the fiber tree when route parameters change on a dynamic route (e.g., `/user/1` to `/user/2`)?**
    * *Answer*: Since the component type matches, React does not unmount the route component. It retains the fiber node, updates props and URL states, and triggers an update pass, requiring the component to watch `useParams` for changes.
48. **Explain how to implement custom middleware or route guards using high-order routing wrappers in modern React Router layouts.**
    * *Answer*: Nest routes under a layout route. The layout route component checks state; if valid, it renders `<Outlet />`. Otherwise, it returns a redirect component, blocking child route executions.
49. **Why is it essential to unsubscribe from event emitters inside custom hooks returned cleanups, and what happens to listeners if they are omitted during tab switches?**
    * *Answer*: Omitted cleanups leave event listeners bound to active window scopes. The closures retain references to unmounted component states, creating memory leaks and throwing errors on trigger.
50. **How does React 19's experimental `use` hook change the design of custom data fetching hooks compared to traditional useEffect approaches?**
    * *Answer*: The `use` hook resolves promises inline. It allows writing custom hooks that call `use(promise)`. If the promise is pending, React automatically suspends rendering, eliminating the need to write `useState` or `useEffect` states for loaders.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 08: Context API](08_context_api.md)
* **Next Chapter**: [👉 Topic 10: Redux Toolkit & Fiber](10_redux_toolkit_fiber.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
