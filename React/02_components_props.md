# 📦 Topic 02: Components & Props

Welcome back, component architect! In this chapter, we will learn about **Components and Props**. React builds websites like puzzles, breaking the UI into independent, reusable pieces called Components. We will learn how to write functional components and how to pass customization parameters (Props) from parent components down to child components.

---

## 🏠 The Big Picture & Real-Life Example

### 🧩 The Custom Lego Brick
Imagine you are building a Lego castle:
* **The Lego Brick (Component)**: You design a plastic brick template called `CastleWall`. Instead of manufacturing 100 different wall styles, you reuse this single template 100 times.
* **The Colors & Studs (Props)**: When you place a brick, you decide its settings: *"This wall should be red"* or *"This wall should have 4 studs."* These settings are **Props**! Props are read-only properties set from the outside. The brick cannot paint itself blue from within; it must accept whatever color you pass down to it.
* **The Special Display Case (Children Prop)**: You build a glass cabinet component called `DisplayBox`. You can place anything inside it—a toy car, a Lego figure, or a coin. The box renders its glass frame, and whatever you put inside is rendered in the center via `props.children`!

---

## 🔬 Let's Look Closer

### 1. Functional Components
In modern React, components are written as JavaScript functions that return JSX. They must start with a Capital letter:
```jsx
function WelcomeCard() {
    return <h1>Hello Reader!</h1>;
}
```

### 2. Props (Properties)
Props are read-only inputs passed down from parent components to child components. Props are immutable (a component must never modify its own props):
```jsx
// Parent component passes prop: "userName"
<UserProfile userName="Alice" />

// Child component receives prop:
function UserProfile(props) {
    return <p>Active User: {props.userName}</p>;
}
```

### 3. The `children` Prop
`children` is a special prop automatically populated by React. It contains whatever elements are nested inside the opening and closing tags of a custom component:
```jsx
// Passing child elements:
<Container>
    <p>This paragraph is passed as a child element.</p>
</Container>

// Accessing children:
function Container(props) {
    return <div className="card-border">{props.children}</div>;
}
```

---

## 💻 Code Sandbox

Let's write reusable user card components.

```jsx
import React from 'react';

// 1. Child Component: UserProfileCard (Accepts props)
function UserProfileCard(props) {
    return (
        <div style={{ border: '1px solid gray', padding: '10px', margin: '10px' }}>
            <h2>{props.name}</h2>
            <p>Role: {props.role}</p>
            {/* Displaying children elements if present */}
            <div>{props.children}</div>
        </div>
    );
}

// 2. Parent Component: App (Passes props to children)
export default function App() {
    return (
        <div>
            <h1>Team Directory</h1>
            
            {/* Reusing the same card component with different prop inputs */}
            <UserProfileCard name="Alice" role="Lead Architect" />
            
            <UserProfileCard name="Bob" role="Security Specialist">
                {/* Nesting children element inside Bob's card */}
                <span style={{ color: 'red' }}>⚠️ High Security Clearance</span>
            </UserProfileCard>
        </div>
    );
}
```

---

## 🧠 Points to Remember

* Props are **read-only** (immutable). If you attempt to change props inside a child component (e.g. `props.name = "New Name"`), React will throw an error.
* If you pass a prop without a value (e.g., `<Button disabled />`), React automatically evaluates that prop as a boolean `true` (equivalent to `disabled={true}`).
* You can set fallback values for props using JavaScript destructuring default values:
  ```jsx
  function Button({ label = "Click Me" }) {
      return <button>{label}</button>;
  }
  ```

---

## 📖 Key Definitions

* **Component**: A self-contained, reusable building block of UI that returns JSX.
* **Props**: Read-only inputs passed from a parent component to a child component to customize its look and behavior.
* **Prop Drilling**: The process of passing props down through multiple layers of nested components to reach a deeply nested child.
* **Children Prop**: A special built-in prop (`props.children`) that allows components to pass and render arbitrary nested JSX elements inside them.
* **Pure Component**: A component that renders the exact same output for the exact same props and does not modify its inputs.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What are Props in React?**
   * *Answer*: Props (Properties) are read-only configuration inputs passed from parent components to child components to customize their content.
2. **What is the difference between Components and Props?**
   * *Answer*: A Component is a reusable block of code (the function template). Props are the inputs passed *into* that component to configure it.
3. **Are Props mutable or immutable?**
   * *Answer*: Props are strictly immutable. A child component must never modify its received props.
4. **How do you pass props to a component?**
   * *Answer*: By writing custom attributes on the JSX tag, similar to HTML: `<Profile name="Bob" age={30} />`.
5. **What is the `children` prop?**
   * *Answer*: A special built-in prop that represents whatever elements are placed between the opening and closing tags of a component.
6. **Can a component have default props?**
   * *Answer*: Yes, by using JavaScript ES6 default parameters during props destructuring (e.g., `function User({ name = "Guest" })`).
7. **What is the difference between Class Components and Functional Components?**
   * *Answer*: Class components are ES6 classes extending `React.Component` and use lifecycle methods. Functional components are simple JS functions that return JSX and use Hooks.
8. **What is Prop Drilling?**
   * *Answer*: The practice of passing props down through multiple nested child components just to reach a deeply nested component that needs the data.
9. **How do you pass a number or a boolean as a prop?**
   * *Answer*: By wrapping the value in curly braces: `<Card count={5} active={true} />`.
10. **Why should component names start with a capital letter?**
    * *Answer*: So that React can distinguish them from standard HTML elements (e.g. `<div />` vs `<Card />`).
11. **Can you pass functions as props?**
    * *Answer*: Yes, functions can be passed as props to allow child components to communicate events back up to their parents.
12. **Can a component render another component?**
    * *Answer*: Yes, components can nest and compose other components inside their JSX output.
13. **What is component composition?**
    * *Answer*: The design pattern of building complex UIs by combining smaller, simple components together.
14. **How do you receive props in a functional component?**
    * *Answer*: As a single object argument passed to the component function: `function User(props)`.
15. **What is props destructuring?**
    * *Answer*: A clean ES6 syntax to extract properties directly from the props object: `function User({ name, age })`.
16. **What is a stateless component?**
    * *Answer*: A simple component that does not maintain internal state or fetch data; it only displays whatever is passed to it via props.
17. **Can a component return a fragment as props?**
    * *Answer*: Yes, props can accept any valid JavaScript type, including JSX elements and fragments.
18. **Can you pass a component as a prop to another component?**
    * *Answer*: Yes, you can pass components as props to implement layout templates (e.g. `<Layout sidebar={<Sidebar />} />`).
19. **What does `<Button />` render if no props are defined?**
    * *Answer*: It renders its base default layout. Props that are not passed default to `undefined` unless fallback values are specified.
20. **What is the benefit of component reusability?**
    * *Answer*: It reduces code duplication, simplifies testing, and ensures visual consistency across the application.

### 🟡 Intermediate Questions (21-40)

21. **Why is it important that React components behave as Pure Functions with respect to their props?**
    * *Answer*: Because React optimizes rendering based on prop equality. If components mutate their props, React cannot reliably detect updates, leading to buggy UIs.
22. **Explain how to implement component prop validation in React.**
    * *Answer*: By using the `prop-types` library to define schemas (e.g., `Card.propTypes = { name: PropTypes.string.isRequired }`), which logs warnings in development.
23. **What is the difference between container components and presentational components?**
    * *Answer*: Container components manage logic, state, and fetch APIs. Presentational components are stateless blocks that focus solely on rendering UI based on props.
24. **How does React handle child re-rendering when parent props do not change?**
    * *Answer*: By default, whenever a parent component re-renders, all of its child components re-render as well, regardless of whether their props changed.
25. **What is the performance implication of prop drilling, and how do you resolve it?**
    * *Answer*: It causes unnecessary re-renders in intermediate components. It is resolved using the Context API or global state managers like Redux.
26. **Explain what happens when you pass a new object literal as a prop (e.g., `<Card config={{ color: 'red' }} />`) on every render.**
    * *Answer*: It creates a new object memory reference on every render, which breaks optimizations like `React.memo` by forcing re-renders on shallow comparisons.
27. **Can a child component communicate with its parent? How?**
    * *Answer*: Yes, by executing a callback function passed down to it as a prop from the parent, passing data back up as arguments.
28. **What are render props in React?**
    * *Answer*: A design pattern where a component's prop accepts a function that returns a React element, allowing sharing of dynamic UI logic.
29. **What is the difference between props and state in React?**
    * *Answer*: Props are configuration parameters passed *into* a component from the outside (read-only). State is internal data managed *inside* the component (mutable via setState).
30. **Explain how the children prop can be manipulated using `React.Children` utilities.**
    * *Answer*: React provides helper utilities like `React.Children.map()`, `React.Children.count()`, and `React.Children.toArray()` to inspect and modify nested elements.
31. **What is the difference between `props.children` and a custom component prop?**
    * *Answer*: Functional equivalence is the same. `props.children` is syntactically cleaner for nesting layout elements, while custom props are better for named slot slots.
32. **Can you modify `props.children` directly?**
    * *Answer*: No, `props.children` elements are read-only descriptors. To modify them, you must clone them using `React.cloneElement()`.
33. **Explain how to set default values for destructured props.**
    * *Answer*: You assign default values directly inside the function signature: `function Card({ title = "No Title" })`.
34. **Does React merge props automatically?**
    * *Answer*: No. If a parent passes props, they are packed into a single new `props` object. If props share keys, the latest defined key overwrites previous ones.
35. **What is the default value of a boolean prop if you only specify its name (e.g. `<Input readOnly />`)?**
    * *Answer*: The default value is boolean `true`.
36. **How do you spread an entire object as props onto a component?**
    * *Answer*: By using the ES6 spread operator: `<Card {...userPropsObject} />`.
37. **What is the risk of using props spreading (`{...props}`) excessively?**
    * *Answer*: It can pass invalid HTML attributes to the underlying DOM elements, triggering console warnings, and makes it hard to track what data is being passed.
38. **Explain the concept of "lifting state up".**
    * *Answer*: The pattern of moving shared component state up to the closest common parent component, allowing sibling components to synchronize data via props.
39. **What are Higher-Order Components (HOCs)?**
    * *Answer*: A design pattern where a function takes a component as an argument and returns a new component wrapped with additional props or behavior.
40. **How does React identify elements returned by functional components under the hood?**
    * *Answer*: By checking the `type` field of the React element. For components, the `type` points to the actual JavaScript function reference rather than an HTML tag string.

### 🔴 Advanced Questions (41-50)

41. **Explain the performance cost of rendering deeply nested component trees and how React's Fiber reconciler schedules updates.**
    * *Answer*: Deep trees require large traversal times. Fiber resolves this by breaking reconciliation into units of work. It can pause tree rendering to yield the thread back to the browser for animations.
42. **Why does passing an inline arrow function as a prop (e.g. `<Button onClick={() => console.log('hi')} />`) trigger shallow comparison updates on memoized components?**
    * *Answer*: Because a new function instance with a different memory pointer is created on every parent render cycle, causing `React.memo`'s shallow equality check to return false.
43. **How does `React.cloneElement()` work, and what is its primary use case in component composition?**
    * *Answer*: It clones a React element and merges new props or children into it. Used for layout composition where parent containers inject state or behavior into their child elements.
44. **Explain how prop-types are checked in production builds compared to development environments.**
    * *Answer*: Prop-types checks are stripped from production bundles to save file size and runtime execution overhead, meaning checks only run in development.
45. **What is the difference in fiber node representation between a host component (e.g. `<div>`) and a composite component (e.g. `<Card>`)?**
    * *Answer*: A host component has `tag: HostComponent` and directly represents a physical DOM element. A composite component has `tag: ClassComponent` or `FunctionComponent` and must resolve its render method.
46. **How would you debug a performance issue caused by prop mutation inside a React application?**
    * *Answer*: By using the React Developer Tools profiler to track component renders, auditing props using `WhyDidYouRender`, and refactoring state updates to use spread operators.
47. **Explain the difference between shallow copy and deep copy comparisons in React's component update checks.**
    * *Answer*: Shallow copy checks compare memory references of root object keys (`obj1 === obj2`). Deep copy checks recursively compare nested keys, which is too slow for real-time rendering loops.
48. **How does React's virtual representation handle static props compared to dynamic props during reconciliation?**
    * *Answer*: During compilation, Babel can mark static JSX elements (hoisting) so that React skips diffing checks for static nodes entirely, focusing only on dynamic properties.
49. **Why is prop-drilling considered a architectural smell rather than a runtime bug?**
    * *Answer*: Because it does not crash the code, but it couples intermediate components to data they don't need, making refactoring and testing extremely difficult.
50. **Explain how Server Components in React 18+ handle props serialization across the network boundary.**
    * *Answer*: Server Components run on the server and pass props to Client Components. These props must be JSON-serializable (no functions or class instances can be passed over the wire).

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 01: Introduction to React & JSX](01_intro_to_react_jsx.md)
* **Next Chapter**: [👉 Topic 03: State Management (useState)](03_state_management.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
