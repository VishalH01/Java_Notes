# ⚛️ React Practical Coding Interview Guide

Welcome, React candidate! This guide compiles the **Top 50 most frequently asked React practical coding questions** in technical interviews. Every question includes a problem statement, clean JSX code, logical explanations, and performance/complexity notes.

---

## 🗺️ Index of Questions

1. [Simple Todo List](#1-simple-todo-list)
2. [Custom Hook: useToggle](#2-custom-hook-usetoggle)
3. [Search Filter Search Bar](#3-search-filter-search-bar)
4. [Custom Hook: useWindowSize](#4-custom-hook-usewindowsize)
5. [Pagination Component](#5-pagination-component)
6. [Custom Hook: useFetch](#6-custom-hook-usefetch)
7. [Custom Hook: useLocalStorage](#7-custom-hook-uselocalstorage)
8. [Validated Login Form](#8-validated-login-form)
9. [Stopwatch Component](#9-stopwatch-component)
10. [Accordion UI Component](#10-accordion-ui-component)
11. [Dynamic Tab Switcher](#11-dynamic-tab-switcher)
12. [Context-Based Theme Switcher](#12-context-based-theme-switcher)
13. [OTP (One-Time Password) 4-Digit Input](#13-otp-one-time-password-4-digit-input)
14. [Star Rating Component](#14-star-rating-component)
15. [Dynamic Table Column Sorter](#15-dynamic-table-column-sorter)
16. [Custom Hook: useClickOutside](#16-custom-hook-useclickoutside)
17. [Reusable Modal using React Portal](#17-reusable-modal-using-react-portal)
18. [Custom Hook: useDebounce](#18-custom-hook-usedebounce)
19. [Auto-Complete Search Input](#19-auto-complete-search-input)
20. [Image Carousel Slider](#20-image-carousel-slider)
21. [Multi-Step Registration Form](#21-multi-step-registration-form)
22. [Multi-Select Checkbox Filters](#22-multi-select-checkbox-filters)
23. [Custom Hook: usePrevious](#23-custom-hook-useprevious)
24. [Custom Hook: useInterval](#24-custom-hook-useinterval)
25. [Breadcrumbs Navigation Helper](#25-breadcrumbs-navigation-helper)
26. [Dynamic Form Builder from JSON Schema](#26-dynamic-form-builder-from-json-schema)
27. [OTP 6-Digit Auto-Tabber](#27-otp-6-digit-auto-tabber)
28. [Context Authentication State Simulator](#28-context-authentication-state-simulator)
29. [Custom Hook: useMediaQuery](#29-custom-hook-usemediaquery)
30. [Collapsible Tree View (Folder/Files)](#30-collapsible-tree-view-folderfiles)
31. [Shopping Cart Item Calculator](#31-shopping-cart-item-calculator)
32. [Typewriter Animation Component](#32-typewriter-animation-component)
33. [Quiz Application with Score count](#33-quiz-application-with-score-count)
34. [Custom Hook: useAsync](#34-custom-hook-useasync)
35. [Drag-to-Resize Panel Layout](#35-drag-to-resize-panel-layout)
36. [Custom Hook: useHistoryState (Undo/Redo)](#36-custom-hook-usehistorystate-undoredo)
37. [Countdown Timer with Finish Callback](#37-countdown-timer-with-finish-callback)
38. [Custom Hook: useEventListener](#38-custom-hook-useeventlistener)
39. [Search Query Highlight Helper](#39-search-query-highlight-helper)
40. [Form Input Mask (Phone Number Layout)](#40-form-input-mask-phone-number-layout)
41. [Custom Hook: useLockBodyScroll](#41-custom-hook-uselockbodyscroll)
42. [Tooltip Hover Trigger Layout](#42-tooltip-hover-trigger-layout)
43. [Multi-Select Tag Input Checklist](#43-multi-select-tag-input-checklist)
44. [Infinite Scroll Simulation Component](#44-infinite-scroll-simulation-component)
45. [Infinite Scrolling Select Dropdown](#45-infinite-scrolling-select-dropdown)
46. [Simple Kanban Board columns simulator](#46-simple-kanban-board-columns-simulator)
47. [Custom Hook: useScrollPosition](#47-custom-hook-usescrollposition)
48. [File Explorer Mock Collapsible Tree](#48-file-explorer-mock-collapsible-tree)
49. [Dynamic class styling switcher](#49-dynamic-class-styling-switcher)
50. [Star Rating Hover Selection Component](#50-star-rating-hover-selection-component)

---

## 💻 React Coding Challenges

### 1. Simple Todo List
**Problem**: Create a Todo list where users can add items, toggle completion, and delete items.
```jsx
import React, { useState } from 'react';

export default function TodoApp() {
    const [todos, setTodos] = useState([]);
    const [input, setInput] = useState("");

    const addTodo = () => {
        if (!input.trim()) return;
        setTodos(prev => [...prev, { id: Date.now(), text: input, done: false }]);
        setInput("");
    };

    const toggleTodo = (id) => {
        setTodos(prev => prev.map(t => t.id === id ? { ...t, done: !t.done } : t));
    };

    const deleteTodo = (id) => {
        setTodos(prev => prev.filter(t => t.id !== id));
    };

    return (
        <div>
            <input value={input} onChange={(e) => setInput(e.target.value)} />
            <button onClick={addTodo}>Add</button>
            <ul>
                {todos.map(t => (
                    <li key={t.id}>
                        <span onClick={() => toggleTodo(t.id)} style={{ textDecoration: t.done ? 'line-through' : 'none' }}>
                            {t.text}
                        </span>
                        <button onClick={() => deleteTodo(t.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
```
* **Explanation**: We manage a `todos` state array. We append new task objects, use `.map()` to update the `done` state property of matching IDs, and use `.filter()` to delete.
* **Complexity**: Add: $O(1)$, Toggle/Delete: $O(N)$ due to mapping/filtering.

---

### 2. Custom Hook: useToggle
**Problem**: Write a custom hook to manage a boolean state switch.
```jsx
import { useState } from 'react';

export function useToggle(initialVal = false) {
    const [state, setState] = useState(initialVal);
    const toggle = () => setState(prev => !prev);
    return [state, toggle];
}
```
* **Explanation**: Simplifies toggle actions (like open/close switches). It encapsulates the boolean state setter in a callback `toggle()`.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 3. Search Filter Search Bar
**Problem**: Filter a list of names dynamically as the user types.
```jsx
import React, { useState } from 'react';

export default function SearchFilter({ namesList }) {
    const [query, setQuery] = useState("");

    const filtered = namesList.filter(name => 
        name.toLowerCase().includes(query.toLowerCase())
    );

    return (
        <div>
            <input value={query} onChange={(e) => setQuery(e.target.value)} placeholder="Search..." />
            {filtered.map(name => <p key={name}>{name}</p>)}
        </div>
    );
}
```
* **Explanation**: The search query is controlled in state. We filter the original list on the fly during render to prevent state sync lag.
* **Complexity**: Time: $O(N)$ where $N$ is names list size.

---

### 4. Custom Hook: useWindowSize
**Problem**: Create a custom hook that monitors browser window dimensions.
```jsx
import { useState, useEffect } from 'react';

export function useWindowSize() {
    const [size, setSize] = useState({ width: window.innerWidth, height: window.innerHeight });

    useEffect(() => {
        const handleResize = () => setSize({ width: window.innerWidth, height: window.innerHeight });
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    return size;
}
```
* **Explanation**: Registers a window resize event listener on mount, updates state on resize, and removes the listener on unmount.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 5. Pagination Component
**Problem**: Page through an array of items, showing only a small page slice.
```jsx
import React, { useState } from 'react';

export default function PaginatedList({ items, itemsPerPage }) {
    const [page, setPage] = useState(1);
    const totalPages = Math.ceil(items.length / itemsPerPage);

    const startIndex = (page - 1) * itemsPerPage;
    const displayedItems = items.slice(startIndex, startIndex + itemsPerPage);

    return (
        <div>
            {displayedItems.map(item => <p key={item.id}>{item.name}</p>)}
            <button disabled={page === 1} onClick={() => setPage(p => p - 1)}>Prev</button>
            <span>Page {page} of {totalPages}</span>
            <button disabled={page === totalPages} onClick={() => setPage(p => p + 1)}>Next</button>
        </div>
    );
}
```
* **Explanation**: We compute the slice indices dynamically: `(page - 1) * count`. We call `items.slice()` during rendering to show the page slice, providing Prev/Next buttons.
* **Complexity**: Time: $O(K)$ where $K$ is items per page.

---

### 6. Custom Hook: useFetch
**Problem**: Create a custom hook to handle API requests with loading and error states.
```jsx
import { useState, useEffect } from 'react';

export function useFetch(url) {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        let active = true;
        setLoading(true);
        fetch(url)
            .then(res => res.json())
            .then(d => { if (active) { setData(d); setLoading(false); } })
            .catch(err => { if (active) { setError(err); setLoading(false); } });
            
        return () => { active = false; };
    }, [url]);

    return { data, loading, error };
}
```
* **Explanation**: Handles fetches using lifecycle updates. An active flag inside the cleanup function stops updates if the component unmounts.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 7. Custom Hook: useLocalStorage
**Problem**: Synchronize state automatically with localStorage.
```jsx
import { useState, useEffect } from 'react';

export function useLocalStorage(key, defaultVal) {
    const [value, setValue] = useState(() => {
        const cached = localStorage.getItem(key);
        return cached ? JSON.parse(cached) : defaultVal;
    });

    useEffect(() => {
        localStorage.setItem(key, JSON.stringify(value));
    }, [key, value]);

    return [value, setValue];
}
```
* **Explanation**: Reads initial state dynamically once using a lazy function. The `useEffect` keeps state changes aligned with localStorage.
* **Complexity**: Time: $O(1)$ read/write, Space: $O(1)$.

---

### 8. Validated Login Form
**Problem**: Form validation requiring password length > 6 and a valid email format.
```jsx
import React, { useState } from 'react';

export default function LoginForm() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleFormSubmit = (e) => {
        e.preventDefault();
        if (!email.includes("@")) {
            setError("Invalid Email!");
            return;
        }
        if (password.length < 6) {
            setError("Password must be at least 6 characters.");
            return;
        }
        setError("");
        alert("Valid Login!");
    };

    return (
        <form onSubmit={handleFormSubmit}>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <input value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
            <button type="submit">Login</button>
        </form>
    );
}
```
* **Explanation**: The form submission checks credentials against schemas and handles display messages.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 9. Stopwatch Component
**Problem**: Implement a stopwatch with Start, Stop, and Reset buttons.
```jsx
import React, { useState, useRef } from 'react';

export default function Stopwatch() {
    const [time, setTime] = useState(0);
    const timerRef = useRef(null);

    const startTimer = () => {
        if (timerRef.current) return;
        timerRef.current = setInterval(() => setTime(t => t + 10), 10);
    };

    const stopTimer = () => {
        clearInterval(timerRef.current);
        timerRef.current = null;
    };

    const resetTimer = () => {
        stopTimer();
        setTime(0);
    };

    return (
        <div>
            <h2>Stopwatch: {(time / 1000).toFixed(2)}s</h2>
            <button onClick={startTimer}>Start</button>
            <button onClick={stopTimer}>Stop</button>
            <button onClick={resetTimer}>Reset</button>
        </div>
    );
}
```
* **Explanation**: We use a `useRef` to store the interval ID. Since writing to a Ref doesn't trigger re-renders, it stores the timer reference safely.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 10. Accordion UI Component
**Problem**: Create a collapsible accordion layout.
```jsx
import React, { useState } from 'react';

export default function Accordion({ items }) {
    const [openIndex, setOpenIndex] = useState(null);

    const toggleIndex = (index) => {
        setOpenIndex(openIndex === index ? null : index);
    };

    return (
        <div>
            {items.map((item, idx) => (
                <div key={idx}>
                    <h3 onClick={() => toggleIndex(idx)} style={{ cursor: 'pointer' }}>
                        {item.title}
                    </h3>
                    {openIndex === idx && <p>{item.content}</p>}
                </div>
            ))}
        </div>
    );
}
```
* **Explanation**: We save the open index in state. The section displays content only if the item index matches `openIndex`.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 11. Dynamic Tab Switcher
**Problem**: Create tabbed screens where clicking tab headers switches the displayed content.
```jsx
import React, { useState } from 'react';

export default function TabSwitcher({ tabs }) {
    const [activeTab, setActiveTab] = useState(0);

    return (
        <div>
            <div>
                {tabs.map((tab, index) => (
                    <button 
                        key={index} 
                        onClick={() => setActiveTab(index)}
                        style={{ fontWeight: activeTab === index ? 'bold' : 'normal' }}
                    >
                        {tab.label}
                    </button>
                ))}
            </div>
            <div style={{ marginTop: '10px' }}>
                {tabs[activeTab].content}
            </div>
        </div>
    );
}
```
* **Explanation**: The active tab index is stored in state. The content is selected dynamically during rendering: `tabs[activeTab].content`.
* **Complexity**: Time: $O(T)$ where $T$ is the number of tabs.

---

### 12. Context-Based Theme Switcher
**Problem**: Build a theme provider to switch between light and dark backgrounds.
```jsx
import React, { createContext, useContext, useState } from 'react';

const ThemeContext = createContext();

export function ThemeProvider({ children }) {
    const [theme, setTheme] = useState("light");
    const toggleTheme = () => setTheme(t => t === "light" ? "dark" : "light");

    return (
        <ThemeContext.Provider value={{ theme, toggleTheme }}>
            {children}
        </ThemeContext.Provider>
    );
}

export function ThemeButton() {
    const { theme, toggleTheme } = useContext(ThemeContext);
    return <button onClick={toggleTheme}>Theme: {theme}</button>;
}
```
* **Explanation**: Standard Context API pattern. The provider holds state, and consumer components access it via `useContext(ThemeContext)`.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 13. OTP (One-Time Password) 4-Digit Input
**Problem**: OTP fields that focus the next box automatically when a digit is entered.
```jsx
import React, { useRef } from 'react';

export default function OtpInput() {
    const refs = [useRef(null), useRef(null), useRef(null), useRef(null)];

    const handleInput = (index, value) => {
        if (value.length > 0 && index < 3) {
            refs[index + 1].current.focus(); // Focus next input box
        }
    };

    const handleKeyDown = (index, event) => {
        if (event.key === 'Backspace' && index > 0 && !event.target.value) {
            refs[index - 1].current.focus(); // Focus previous input box
        }
    };

    return (
        <div>
            {refs.map((ref, idx) => (
                <input 
                    key={idx}
                    ref={ref}
                    maxLength="1"
                    onChange={(e) => handleInput(idx, e.target.value)}
                    onKeyDown={(e) => handleKeyDown(idx, e)}
                    style={{ width: '40px', marginRight: '5px', textAlign: 'center' }}
                />
            ))}
        </div>
    );
}
```
* **Explanation**: We use an array of refs to target adjacent DOM elements, shifting active focus programmatically based on user typing keys.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 14. Star Rating Component
**Problem**: Create a star rating selector (1-5 stars) that highlights stars on click.
```jsx
import React, { useState } from 'react';

export default function StarRating({ totalStars = 5 }) {
    const [rating, setRating] = useState(0);

    return (
        <div>
            {[...Array(totalStars)].map((_, index) => {
                const starVal = index + 1;
                return (
                    <span 
                        key={index} 
                        onClick={() => setRating(starVal)}
                        style={{ cursor: 'pointer', fontSize: '24px', color: starVal <= rating ? 'gold' : 'gray' }}
                    >
                        ★
                    </span>
                );
            })}
        </div>
    );
}
```
* **Explanation**: We loop `totalStars` times, checking if the current index is less than or equal to `rating` to color it gold.
* **Complexity**: Time: $O(S)$ where $S$ is stars count.

---

### 15. Dynamic Table Column Sorter
**Problem**: A table displaying user details that sorts rows alphabetically by column headings.
```jsx
import React, { useState } from 'react';

export default function SortableTable({ data }) {
    const [sortConfig, setSortConfig] = useState({ key: null, direction: 'asc' });

    const handleSort = (key) => {
        let direction = 'asc';
        if (sortConfig.key === key && sortConfig.direction === 'asc') {
            direction = 'desc';
        }
        setSortConfig({ key, direction });
    };

    const sortedData = React.useMemo(() => {
        if (!sortConfig.key) return data;
        const sorted = [...data].sort((a, b) => {
            if (a[sortConfig.key] < b[sortConfig.key]) return sortConfig.direction === 'asc' ? -1 : 1;
            if (a[sortConfig.key] > b[sortConfig.key]) return sortConfig.direction === 'asc' ? 1 : -1;
            return 0;
        });
        return sorted;
    }, [data, sortConfig]);

    return (
        <table>
            <thead>
                <tr>
                    <th onClick={() => handleSort('name')}>Name</th>
                    <th onClick={() => handleSort('age')}>Age</th>
                </tr>
            </thead>
            <tbody>
                {sortedData.map((user, idx) => (
                    <tr key={idx}>
                        <td>{user.name}</td>
                        <td>{user.age}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
}
```
* **Explanation**: We use `useMemo` to sort the table dataset based on column key and direction variables, avoiding sorting updates on unrelated page renders.
* **Complexity**: Time: $O(N \log N)$ sorting time.

---

### 16. Custom Hook: useClickOutside
**Problem**: Create a hook that executes a callback when the user clicks outside a targeted dropdown/modal component.
```jsx
import { useEffect } from 'react';

export function useClickOutside(ref, callback) {
    useEffect(() => {
        const listener = (event) => {
            if (!ref.current || ref.current.contains(event.target)) {
                return; // Click occurred inside the element
            }
            callback(); // Click occurred outside!
        };
        
        document.addEventListener('mousedown', listener);
        return () => document.removeEventListener('mousedown', listener);
    }, [ref, callback]);
}
```
* **Explanation**: Binds a click listener to the global document. We check if the click target falls outside the element reference using `ref.current.contains(event.target)`.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 17. Reusable Modal using React Portal
**Problem**: A modal component that mounts to a separate body node to bypass parent CSS overflows.
```jsx
import React from 'react';
import ReactDOM from 'react-dom';

export default function Modal({ isOpen, onClose, children }) {
    if (!isOpen) return null;

    // Renders the modal directly as a child of document.body
    return ReactDOM.createPortal(
        <div style={{
            position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
            background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center'
        }}>
            <div style={{ background: 'white', padding: '20px', borderRadius: '5px' }}>
                <button onClick={onClose} style={{ float: 'right' }}>Close</button>
                <div>{children}</div>
            </div>
        </div>,
        document.body
    );
}
```
* **Explanation**: Using `ReactDOM.createPortal` splits the DOM render point out of the component hierarchy, avoiding parent layout constraints.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 18. Custom Hook: useDebounce
**Problem**: Debounce a state value (like search queries) to delay API updates.
```jsx
import { useState, useEffect } from 'react';

export function useDebounce(value, delay) {
    const [debouncedVal, setDebouncedVal] = useState(value);

    useEffect(() => {
        const handler = setTimeout(() => {
            setDebouncedVal(value);
        }, delay);

        return () => clearTimeout(handler);
    }, [value, delay]);

    return debouncedVal;
}
```
* **Explanation**: Returns a delayed state. Changing inputs resets the timer via the cleanup function, executing updates only after the user stops typing for `delay` ms.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 19. Auto-Complete Search Input
**Problem**: Auto-complete search box showing suggestions as the user types.
```jsx
import React, { useState } from 'react';

export default function Autocomplete({ suggestions }) {
    const [filtered, setFiltered] = useState([]);
    const [input, setInput] = useState("");

    const handleChange = (e) => {
        const query = e.target.value;
        setInput(query);
        if (query.trim() === "") {
            setFiltered([]);
        } else {
            setFiltered(suggestions.filter(s => 
                s.toLowerCase().startsWith(query.toLowerCase())
            ));
        }
    };

    return (
        <div>
            <input value={input} onChange={handleChange} />
            {filtered.length > 0 && (
                <ul>
                    {filtered.map(s => (
                        <li key={s} onClick={() => { setInput(s); setFiltered([]); }}>
                            {s}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
```
* **Explanation**: Updates dynamic suggestion options using string checks. Selecting a suggestion populates the input and hides the lists.
* **Complexity**: Time: $O(N)$ suggestions count.

---

### 20. Image Carousel Slider
**Problem**: Reusable slider widget with Prev and Next buttons to rotate through images.
```jsx
import React, { useState } from 'react';

export default function ImageCarousel({ images }) {
    const [index, setIndex] = useState(0);

    const nextImage = () => {
        setIndex(prev => (prev === images.length - 1 ? 0 : prev + 1));
    };

    const prevImage = () => {
        setIndex(prev => (prev === 0 ? images.length - 1 : prev - 1));
    };

    return (
        <div style={{ textAlign: 'center' }}>
            <button onClick={prevImage}>Prev</button>
            <img src={images[index]} alt="Carousel" style={{ width: '200px', margin: '0 10px' }} />
            <button onClick={nextImage}>Next</button>
        </div>
    );
}
```
* **Explanation**: Indexes are rotated. Reaching bounds loops back to the start or end using ternary checks.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 21. Multi-Step Registration Form
**Problem**: Build a multi-step form wizard (Step 1 -> Step 2 -> Submit).
```jsx
import React, { useState } from 'react';

export default function MultiStepForm() {
    const [step, setStep] = useState(1);
    const [formData, setFormData] = useState({ name: "", email: "" });

    const handleNext = () => setStep(s => s + 1);
    const handlePrev = () => setStep(s => s - 1);

    return (
        <div>
            {step === 1 && (
                <div>
                    <h3>Step 1: Profile</h3>
                    <input 
                        value={formData.name} 
                        onChange={(e) => setFormData({ ...formData, name: e.target.value })} 
                        placeholder="Name" 
                    />
                    <button onClick={handleNext}>Next</button>
                </div>
            )}
            {step === 2 && (
                <div>
                    <h3>Step 2: Contact</h3>
                    <input 
                        value={formData.email} 
                        onChange={(e) => setFormData({ ...formData, email: e.target.value })} 
                        placeholder="Email" 
                    />
                    <button onClick={handlePrev}>Back</button>
                    <button onClick={() => alert(JSON.stringify(formData))}>Submit</button>
                </div>
            )}
        </div>
    );
}
```
* **Explanation**: We render form steps conditionally using a `step` state counter. The form data object persists across step changes.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 22. Multi-Select Checkbox Filters
**Problem**: Filter products based on multiple active category checkbox filters.
```jsx
import React, { useState } from 'react';

export default function CheckboxFilter({ products }) {
    const [selectedFilters, setSelectedFilters] = useState([]);

    const handleCheckboxChange = (category) => {
        setSelectedFilters(prev => 
            prev.includes(category) 
                ? prev.filter(c => c !== category) 
                : [...prev, category]
        );
    };

    const filteredProducts = products.filter(p => 
        selectedFilters.length === 0 || selectedFilters.includes(p.category)
    );

    return (
        <div>
            <div>
                {['Electronics', 'Clothing'].map(cat => (
                    <label key={cat}>
                        <input 
                            type="checkbox" 
                            onChange={() => handleCheckboxChange(cat)} 
                        />
                        {cat}
                    </label>
                ))}
            </div>
            <div>
                {filteredProducts.map(p => <p key={p.id}>{p.name} ({p.category})</p>)}
            </div>
        </div>
    );
}
```
* **Explanation**: Checked values are toggled in a filter array. If empty, all products render; otherwise, we filter matching items.
* **Complexity**: Time: $O(N \times F)$ where $F$ is active filters.

---

### 23. Custom Hook: usePrevious
**Problem**: Create a hook that returns the previous value of a state variable from the prior render.
```jsx
import { useRef, useEffect } from 'react';

export function usePrevious(value) {
    const ref = useRef(null);

    useEffect(() => {
        ref.current = value; // Update ref only AFTER render executes
    }, [value]);

    return ref.current; // Returns the old value captured during render phase
}
```
* **Explanation**: Refs update inside `useEffect` (commit phase). Returning `ref.current` during render returns the value *before* the update occurred.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 24. Custom Hook: useInterval
**Problem**: Write a declarative `setInterval` hook that handles dynamic delays.
```jsx
import { useEffect, useRef } from 'react';

export function useInterval(callback, delay) {
    const savedCallback = useRef();

    useEffect(() => {
        savedCallback.current = callback;
    }, [callback]);

    useEffect(() => {
        if (delay !== null) {
            const id = setInterval(() => savedCallback.current(), delay);
            return () => clearInterval(id);
        }
    }, [delay]);
}
```
* **Explanation**: Prevents closure stalls. We store the callback function in a ref. The interval executes the ref's current function, avoiding interval resets when callbacks update.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 25. Breadcrumbs Navigation Helper
**Problem**: Create breadcrumb trails based on the current active URL path (e.g. Home / Blog / Post).
```jsx
import React from 'react';
import { useLocation, Link } from 'react-router-dom';

export default function Breadcrumbs() {
    const { pathname } = useLocation();
    const parts = pathname.split('/').filter(x => x);

    return (
        <nav>
            <Link to="/">Home</Link>
            {parts.map((part, index) => {
                const routeTo = `/${parts.slice(0, index + 1).join('/')}`;
                const isLast = index === parts.length - 1;
                return (
                    <span key={index}>
                         / {isLast ? <span>{part}</span> : <Link to={routeTo}>{part}</Link>}
                    </span>
                );
            })}
        </nav>
    );
}
```
* **Explanation**: Splitting pathname by `/` creates array components. We compile links dynamically using slice offsets.
* **Complexity**: Time: $O(P)$ where $P$ is route segments.

---

### 26. Dynamic Form Builder from JSON Schema
**Problem**: Build a form dynamically based on a JSON schema describing fields and types.
```jsx
import React, { useState } from 'react';

export default function DynamicForm({ schema }) {
    const [formData, setFormData] = useState({});

    const handleChange = (name, value) => {
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        alert(JSON.stringify(formData));
    };

    return (
        <form onSubmit={handleSubmit}>
            {schema.map(field => (
                <div key={field.name} style={{ marginBottom: '10px' }}>
                    <label>{field.label}: </label>
                    <input 
                        type={field.type} 
                        onChange={(e) => handleChange(field.name, e.target.value)} 
                    />
                </div>
            ))}
            <button type="submit">Submit</button>
        </form>
    );
}
```
* **Explanation**: We map the fields array schema into input elements, updating a dynamic state object using computed keys.
* **Complexity**: Time: $O(F)$ where $F$ is fields count.

---

### 27. OTP 6-Digit Auto-Tabber
**Problem**: Similar to 4-digit OTP, auto-tabs focus across 6 fields and supports copy-paste pasting.
```jsx
import React, { useRef } from 'react';

export default function OtpSixInput() {
    const refs = Array(6).fill().map(() => useRef(null));

    const handlePaste = (e) => {
        e.preventDefault();
        const text = e.clipboardData.getData('text').slice(0, 6);
        text.split('').forEach((char, i) => {
            if (refs[i].current) {
                refs[i].current.value = char;
            }
        });
        refs[Math.min(text.length - 1, 5)].current.focus();
    };

    return (
        <div onPaste={handlePaste}>
            {refs.map((ref, idx) => (
                <input 
                    key={idx}
                    ref={ref}
                    maxLength="1"
                    onChange={(e) => {
                        if (e.target.value && idx < 5) refs[idx + 1].current.focus();
                    }}
                    style={{ width: '30px', marginRight: '5px', textAlign: 'center' }}
                />
            ))}
        </div>
    );
}
```
* **Explanation**: The `onPaste` event handler captures pasted text, splits it into characters, populates the input values, and focuses the final populated field.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 28. Context Authentication State Simulator
**Problem**: Create a global authentication context tracking active login status and user profiles.
```jsx
import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const login = (username) => setUser({ name: username });
    const logout = () => setUser(null);

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
```
* **Explanation**: Simplifies authentication access. Components call the custom `useAuth()` hook to fetch credentials or trigger actions.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 29. Custom Hook: useMediaQuery
**Problem**: Write a hook that checks CSS Media queries (e.g. checking if screen is `<768px`).
```jsx
import { useState, useEffect } from 'react';

export function useMediaQuery(query) {
    const [matches, setMatches] = useState(window.matchMedia(query).matches);

    useEffect(() => {
        const media = window.matchMedia(query);
        const listener = () => setMatches(media.matches);
        media.addEventListener('change', listener);
        return () => media.removeEventListener('change', listener);
    }, [query]);

    return matches;
}
```
* **Explanation**: Binds to native window `matchMedia` changes, returning boolean states to update responsive JSX components.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 30. Collapsible Tree View (Folder/Files)
**Problem**: Display a nested folder-file explorer recursively.
```jsx
import React, { useState } from 'react';

export default function TreeNode({ node }) {
    const [isOpen, setIsOpen] = useState(false);
    const hasChildren = node.children && node.children.length > 0;

    return (
        <div style={{ marginLeft: '15px' }}>
            <span 
                onClick={() => setIsOpen(!isOpen)} 
                style={{ cursor: hasChildren ? 'pointer' : 'default', fontWeight: hasChildren ? 'bold' : 'normal' }}
            >
                {hasChildren ? (isOpen ? '📂 ' : '📁 ') : '📄 '}
                {node.name}
            </span>
            {isOpen && hasChildren && (
                <div>
                    {node.children.map(child => (
                        <TreeNode key={child.id} node={child} />
                    ))}
                </div>
            )}
        </div>
    );
}
```
* **Explanation**: Component recursion pattern. A component renders instances of itself inside its tree layout if `children` exist, managing private collapse states per folder.
* **Complexity**: Time: $O(N)$ total files count, Space: $O(H)$ max tree depth.

---

### 31. Shopping Cart Item Calculator
**Problem**: Add, remove, and calculate total price for items in a shopping cart.
```jsx
import React, { useState } from 'react';

export default function ShoppingCart() {
    const [cart, setCart] = useState([]);

    const addToCart = (product) => {
        setCart(prev => {
            const existing = prev.find(item => item.id === product.id);
            if (existing) {
                return prev.map(item => item.id === product.id ? { ...item, qty: item.qty + 1 } : item);
            }
            return [...prev, { ...product, qty: 1 }];
        });
    };

    const updateQty = (id, delta) => {
        setCart(prev => prev.map(item => {
            if (item.id === id) {
                const newQty = item.qty + delta;
                return newQty > 0 ? { ...item, qty: newQty } : item;
            }
            return item;
        }));
    };

    const total = cart.reduce((sum, item) => sum + (item.price * item.qty), 0);

    return (
        <div>
            <button onClick={() => addToCart({ id: 1, name: 'Book', price: 10 })}>Add Book ($10)</button>
            {cart.map(item => (
                <div key={item.id}>
                    <span>{item.name} | Qty: {item.qty} | Price: ${item.price * item.qty}</span>
                    <button onClick={() => updateQty(item.id, 1)}>+</button>
                    <button onClick={() => updateQty(item.id, -1)}>-</button>
                </div>
            ))}
            <h3>Total: ${total}</h3>
        </div>
    );
}
```
* **Explanation**: We search if an item is already present in state. If yes, we map to increment its quantity; otherwise, we append the item. We aggregate the total price using `.reduce()`.
* **Complexity**: Time: $O(C)$ where $C$ is cart size.

---

### 32. Typewriter Animation Component
**Problem**: Animate text to print character-by-character.
```jsx
import React, { useState, useEffect } from 'react';

export default function Typewriter({ text, speed = 100 }) {
    const [displayed, setDisplayed] = useState("");

    useEffect(() => {
        setDisplayed("");
        let idx = 0;
        const timer = setInterval(() => {
            if (idx < text.length) {
                setDisplayed(prev => prev + text.charAt(idx));
                idx++;
            } else {
                clearInterval(timer);
            }
        }, speed);

        return () => clearInterval(timer);
    }, [text, speed]);

    return <span>{displayed}</span>;
}
```
* **Explanation**: Updates text length incrementally. A `setInterval` appends characters sequentially from the target string based on an index counter.
* **Complexity**: Time: $O(N)$ text characters count.

---

### 33. Quiz Application with Score count
**Problem**: Quiz app that switches questions, tracks correct choices, and displays the final score.
```jsx
import React, { useState } from 'react';

export default function QuizApp({ questions }) {
    const [currentIdx, setCurrentIdx] = useState(0);
    const [score, setScore] = useState(0);
    const [showScore, setShowScore] = useState(false);

    const handleAnswer = (isCorrect) => {
        if (isCorrect) setScore(s => s + 1);
        const next = currentIdx + 1;
        if (next < questions.length) {
            setCurrentIdx(next);
        } else {
            setShowScore(true);
        }
    };

    if (showScore) return <h2>Score: {score} / {questions.length}</h2>;

    const q = questions[currentIdx];
    return (
        <div>
            <h3>{q.questionText}</h3>
            {q.options.map(opt => (
                <button key={opt.text} onClick={() => handleAnswer(opt.isCorrect)} style={{ display: 'block', margin: '5px 0' }}>
                    {opt.text}
                </button>
            ))}
        </div>
    );
}
```
* **Explanation**: We display active questions using index states, transitioning to the end dashboard when the index exceeds limits.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 34. Custom Hook: useAsync
**Problem**: Reusable hook that handles loading, errors, and data updates for any async function.
```jsx
import { useState, useCallback } from 'react';

export function useAsync(asyncFunction) {
    const [status, setStatus] = useState("idle");
    const [value, setValue] = useState(null);
    const [error, setError] = useState(null);

    const execute = useCallback(() => {
        setStatus("pending");
        setValue(null);
        setError(null);
        return asyncFunction()
            .then(res => { setValue(res); setStatus("success"); })
            .catch(err => { setError(err); setStatus("error"); });
    }, [asyncFunction]);

    return { execute, status, value, error };
}
```
* **Explanation**: Standard hook for managing api actions, returning execution methods and status descriptors for dynamic UIs.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 35. Drag-to-Resize Panel Layout
**Problem**: Dynamic page splitter component where dragging the middle line resizes panels.
```jsx
import React, { useState, useRef } from 'react';

export default function ResizablePanels() {
    const [width, setWidth] = useState(200);
    const isDragging = useRef(false);

    const startDrag = () => { isDragging.current = true; };
    const stopDrag = () => { isDragging.current = false; };

    const doDrag = (e) => {
        if (!isDragging.current) return;
        setWidth(e.clientX); // Set panel width based on mouse coordinate pointer
    };

    return (
        <div onMouseMove={doDrag} onMouseUp={stopDrag} style={{ display: 'flex', height: '300px', width: '100%' }}>
            <div style={{ width: `${width}px`, background: '#ccc' }}>Left Panel</div>
            <div onMouseDown={startDrag} style={{ width: '10px', cursor: 'col-resize', background: '#333' }} />
            <div style={{ flexGrow: 1, background: '#eee' }}>Right Panel</div>
        </div>
    );
}
```
* **Explanation**: Resizing is driven by mouse coordinates. Panel sizes are updated based on mouse movement positions when the drag flag is active.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 36. Custom Hook: useHistoryState (Undo/Redo)
**Problem**: Create a hook that supports undo/redo history states for an input value.
```jsx
import { useState } from 'react';

export function useHistoryState(initialVal) {
    const [state, setState] = useState(initialVal);
    const [history, setHistory] = useState([initialVal]);
    const [pointer, setPointer] = useState(0);

    const set = (newVal) => {
        const updated = history.slice(0, pointer + 1);
        setHistory([...updated, newVal]);
        setPointer(updated.length);
        setState(newVal);
    };

    const undo = () => {
        if (pointer > 0) {
            setPointer(p => p - 1);
            setState(history[pointer - 1]);
        }
    };

    const redo = () => {
        if (pointer < history.length - 1) {
            setPointer(p => p + 1);
            setState(history[pointer + 1]);
        }
    };

    return { state, set, undo, redo, canUndo: pointer > 0, canRedo: pointer < history.length - 1 };
}
```
* **Explanation**: Implements undo/redo capability. We store state history in a list and manage a index pointer. Navigating history moves the pointer and updates the active state.
* **Complexity**: Set: $O(H)$ due to slice history size, Undo/Redo: $O(1)$.

---

### 37. Countdown Timer with Finish Callback
**Problem**: A countdown timer (seconds) that calls a custom callback function when it reaches 0.
```jsx
import React, { useState, useEffect } from 'react';

export default function CountdownTimer({ startSeconds, onFinish }) {
    const [timeLeft, setTimeLeft] = useState(startSeconds);

    useEffect(() => {
        if (timeLeft <= 0) {
            if (onFinish) onFinish();
            return;
        }

        const timer = setTimeout(() => {
            setTimeLeft(prev => prev - 1);
        }, 1000);

        return () => clearTimeout(timer);
    }, [timeLeft, onFinish]);

    return <h2>Time Remaining: {timeLeft}s</h2>;
}
```
* **Explanation**: The countdown decreases by 1 every second. When `timeLeft` hits 0, it calls `onFinish()` and returns early to prevent setting more timeouts.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 38. Custom Hook: useEventListener
**Problem**: Create a hook to bind and unbind window listeners cleanly.
```jsx
import { useEffect, useRef } from 'react';

export function useEventListener(eventName, handler, element = window) {
    const savedHandler = useRef();

    useEffect(() => {
        savedHandler.current = handler;
    }, [handler]);

    useEffect(() => {
        const isSupported = element && element.addEventListener;
        if (!isSupported) return;

        const eventListener = (event) => savedHandler.current(event);
        element.addEventListener(eventName, eventListener);
        
        return () => element.removeEventListener(eventName, eventListener);
    }, [eventName, element]);
}
```
* **Explanation**: Prevents listener rebinding cycles. The listener is bound once and triggers whatever function is currently saved in the ref.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 39. Search Query Highlight Helper
**Problem**: Highlight text matching a search query in a paragraph.
```jsx
import React from 'react';

export default function HighlightText({ text, highlight }) {
    if (!highlight.trim()) return <span>{text}</span>;

    const regex = new RegExp(`(${highlight})`, 'gi');
    const parts = text.split(regex);

    return (
        <span>
            {parts.map((part, index) => 
                regex.test(part) 
                    ? <mark key={index} style={{ backgroundColor: 'yellow' }}>{part}</mark> 
                    : <span key={index}>{part}</span>
            )}
        </span>
    );
}
```
* **Explanation**: We use a case-insensitive Regex to split text by the search key. Matching parts are wrapped inside `<mark>` tags during rendering.
* **Complexity**: Time: $O(C)$ where $C$ is text character size.

---

### 40. Form Input Mask (Phone Number Layout)
**Problem**: Automatically format input values as phone numbers: `(123) 456-7890`.
```jsx
import React, { useState } from 'react';

export default function PhoneInput() {
    const [value, setValue] = useState("");

    const formatPhone = (val) => {
        const numbers = val.replace(/\D/g, '').slice(0, 10);
        if (numbers.length <= 3) return numbers;
        if (numbers.length <= 6) return `(${numbers.slice(0, 3)}) ${numbers.slice(3)}`;
        return `(${numbers.slice(0, 3)}) ${numbers.slice(3, 6)}-${numbers.slice(6)}`;
    };

    const handleChange = (e) => {
        setValue(formatPhone(e.target.value));
    };

    return <input value={value} onChange={handleChange} placeholder="(XXX) XXX-XXXX" />;
}
```
* **Explanation**: Removes non-digit characters. It formats numbers into sections based on string length on every keystroke.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 41. Custom Hook: useLockBodyScroll
**Problem**: Lock page scrolling (for open modals/drawers).
```jsx
import { useLayoutEffect } from 'react';

export function useLockBodyScroll() {
    useLayoutEffect(() => {
        const originalStyle = window.getComputedStyle(document.body).overflow;
        document.body.style.overflow = "hidden"; // Disable scroll
        
        // Restore style on unmount
        return () => { document.body.style.overflow = originalStyle; };
    }, []);
}
```
* **Explanation**: We set `overflow: hidden` on the document body during mount to disable scrolling, restoring the original style on unmount.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 42. Tooltip Hover Trigger Layout
**Problem**: Create a custom tooltip widget that renders on hover.
```jsx
import React, { useState } from 'react';

export default function Tooltip({ text, children }) {
    const [show, setShow] = useState(false);

    return (
        <div 
            onMouseEnter={() => setShow(true)} 
            onMouseLeave={() => setShow(false)} 
            style={{ position: 'relative', display: 'inline-block' }}
        >
            {children}
            {show && (
                <div style={{
                    position: 'absolute', bottom: '100%', left: '50%', transform: 'translateX(-50%)',
                    background: 'black', color: 'white', padding: '5px', borderRadius: '4px', whiteSpace: 'nowrap'
                }}>
                    {text}
                </div>
            )}
        </div>
    );
}
```
* **Explanation**: We monitor mouse hover states. Setting absolute coordinate wrappers positions the tooltip box directly above children.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 43. Multi-Select Tag Input Checklist
**Problem**: Reusable tag input field where pressing Enter adds a tag pill.
```jsx
import React, { useState } from 'react';

export default function TagInput() {
    const [tags, setTags] = useState([]);
    const [input, setInput] = useState("");

    const handleKeyDown = (e) => {
        if (e.key === 'Enter' && input.trim() && !tags.includes(input.trim())) {
            setTags([...tags, input.trim()]);
            setInput("");
        }
    };

    const removeTag = (idx) => {
        setTags(prev => prev.filter((_, i) => i !== idx));
    };

    return (
        <div>
            <div>
                {tags.map((tag, i) => (
                    <span key={i} style={{ background: '#ddd', padding: '3px 6px', margin: '3px', borderRadius: '4px' }}>
                        {tag} <span onClick={() => removeTag(i)} style={{ cursor: 'pointer' }}>x</span>
                    </span>
                ))}
            </div>
            <input value={input} onChange={e => setInput(e.target.value)} onKeyDown={handleKeyDown} placeholder="Press Enter..." />
        </div>
    );
}
```
* **Explanation**: Keydowns are intercepted. Pressing Enter appends the text to the `tags` array and clears the input field.
* **Complexity**: Time: $O(T)$ where $T$ is tags count.

---

### 44. Infinite Scroll Simulation Component
**Problem**: Simulate infinite scroll by loading more items as the user scrolls to the bottom of the page.
```jsx
import React, { useState, useEffect } from 'react';

export default function InfiniteScrollList() {
    const [items, setItems] = useState(Array.from({ length: 20 }, (_, i) => `Item ${i + 1}`));
    const [isFetching, setIsFetching] = useState(false);

    useEffect(() => {
        const handleScroll = () => {
            if (window.innerHeight + window.scrollY >= document.documentElement.scrollHeight - 50) {
                setIsFetching(true);
            }
        };
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    useEffect(() => {
        if (!isFetching) return;
        setTimeout(() => {
            setItems(prev => [...prev, ...Array.from({ length: 10 }, (_, i) => `Item ${prev.length + i + 1}`)]);
            setIsFetching(false);
        }, 500); // Simulate API latency
    }, [isFetching]);

    return (
        <div>
            {items.map(item => <p key={item} style={{ padding: '20px', borderBottom: '1px solid #ccc' }}>{item}</p>)}
            {isFetching && <p>Loading more items...</p>}
        </div>
    );
}
```
* **Explanation**: We calculate if scroll matches screen limits (`window.innerHeight + window.scrollY >= document.documentElement.scrollHeight - 50`). If yes, we toggle `isFetching` to load more items.
* **Complexity**: Time: $O(L)$ where $L$ is list size.

---

### 45. Infinite Scrolling Select Dropdown
**Problem**: A dropdown select input that virtualizes and page-loads options as the user scrolls down the list.
```jsx
import React, { useState } from 'react';

export default function InfiniteSelect({ options }) {
    const [itemsCount, setItemsCount] = useState(10);

    const handleScroll = (e) => {
        const target = e.target;
        if (target.scrollHeight - target.scrollTop <= target.clientHeight + 10) {
            setItemsCount(prev => Math.min(prev + 10, options.length));
        }
    };

    return (
        <select onScroll={handleScroll} multiple style={{ height: '150px', overflowY: 'auto' }}>
            {options.slice(0, itemsCount).map((opt, idx) => (
                <option key={idx} value={opt}>{opt}</option>
            ))}
        </select>
    );
}
```
* **Explanation**: We monitor scroll position. If scroll position is near the bottom of the select list, we increment the count of visible items.
* **Complexity**: Time: $O(V)$ where $V$ is visible items.

---

### 46. Simple Kanban Board Columns Simulator
**Problem**: Build a basic Kanban board containing 'To Do' and 'Done' columns, letting users shift cards.
```jsx
import React, { useState } from 'react';

export default function KanbanBoard() {
    const [tasks, setTasks] = useState([
        { id: 1, text: 'Study Spring Security', status: 'todo' },
        { id: 2, text: 'Master React Reconciliation', status: 'todo' },
    ]);

    const moveTask = (id, newStatus) => {
        setTasks(prev => prev.map(t => t.id === id ? { ...t, status: newStatus } : t));
    };

    return (
        <div style={{ display: 'flex', gap: '20px' }}>
            {['todo', 'done'].map(col => (
                <div key={col} style={{ width: '200px', background: '#f0f0f0', padding: '10px' }}>
                    <h3>{col.toUpperCase()}</h3>
                    {tasks.filter(t => t.status === col).map(t => (
                        <div key={t.id} style={{ background: 'white', padding: '8px', marginBottom: '8px', border: '1px solid #ccc' }}>
                            <p>{t.text}</p>
                            <button onClick={() => moveTask(t.id, col === 'todo' ? 'done' : 'todo')}>
                                Move {col === 'todo' ? '👉' : '👈'}
                            </button>
                        </div>
                    ))}
                </div>
            ))}
        </div>
    );
}
```
* **Explanation**: Column grouping is computed. Clicking the shift button maps the status state of target items to switch their column category.
* **Complexity**: Time: $O(N)$ total tasks.

---

### 47. Custom Hook: useScrollPosition
**Problem**: Create a hook that tracks the vertical scroll offset `window.scrollY`.
```jsx
import { useState, useEffect } from 'react';

export function useScrollPosition() {
    const [scrollPos, setScrollPos] = useState(window.scrollY);

    useEffect(() => {
        const listener = () => setScrollPos(window.scrollY);
        window.addEventListener('scroll', listener);
        return () => window.removeEventListener('scroll', listener);
    }, []);

    return scrollPos;
}
```
* **Explanation**: Attaches to window scroll events on mount, updating state with the scroll coordinate and cleaning up on unmount.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 48. File Explorer Mock Collapsible Tree
**Problem**: Render collapsible items from a mock folder tree nested data structure.
```jsx
import React, { useState } from 'react';

const mockFileSystem = {
    name: 'root',
    isFolder: true,
    children: [
        { name: 'src', isFolder: true, children: [{ name: 'App.jsx', isFolder: false }] },
        { name: 'package.json', isFolder: false }
    ]
};

function FileExplorerNode({ item }) {
    const [expanded, setExpanded] = useState(false);
    return (
        <div style={{ marginLeft: '10px' }}>
            <div onClick={() => setExpanded(!expanded)} style={{ cursor: 'pointer' }}>
                {item.isFolder ? (expanded ? '📂 ' : '📁 ') : '📄 '}
                {item.name}
            </div>
            {expanded && item.children && (
                <div>
                    {item.children.map((child, idx) => (
                        <FileExplorerNode key={idx} item={child} />
                    ))}
                </div>
            )}
        </div>
    );
}

export default function FileExplorer() {
    return <FileExplorerNode item={mockFileSystem} />;
}
```
* **Explanation**: Recursive rendering matches node types, conditionalizing rendering of nested components based on expand toggles.
* **Complexity**: Time: $O(N)$ files count, Space: $O(H)$ height tree.

---

### 49. Dynamic Class Styling Switcher
**Problem**: Dynamically update a component's styling classes based on active state buttons.
```jsx
import React, { useState } from 'react';

export default function ClassStyleSwitcher() {
    const [styleMode, setStyleMode] = useState("normal");

    const getStyle = () => {
        switch (styleMode) {
            case 'highlight': return { border: '2px solid red', color: 'red', padding: '10px' };
            case 'compact': return { padding: '2px', fontSize: '10px', color: 'gray' };
            default: return { border: '1px solid gray', padding: '10px' };
        }
    };

    return (
        <div>
            <div style={getStyle()}>Dynamic Styling Box</div>
            <button onClick={() => setStyleMode("normal")}>Normal</button>
            <button onClick={() => setStyleMode("highlight")}>Highlight</button>
            <button onClick={() => setStyleMode("compact")}>Compact</button>
        </div>
    );
}
```
* **Explanation**: Returns style configuration mappings depending on the active state selection index.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 50. Star Rating Hover Selection Component
**Problem**: Star selector that dynamically highlights stars on hover, returning values to final states on click.
```jsx
import React, { useState } from 'react';

export default function StarHoverRating() {
    const [rating, setRating] = useState(0);
    const [hoverRating, setHoverRating] = useState(0);

    return (
        <div>
            {[...Array(5)].map((_, idx) => {
                const starVal = idx + 1;
                const activeStarLimit = hoverRating || rating;
                return (
                    <span 
                        key={idx}
                        onMouseEnter={() => setHoverRating(starVal)}
                        onMouseLeave={() => setHoverRating(0)}
                        onClick={() => setRating(starVal)}
                        style={{ cursor: 'pointer', fontSize: '30px', color: starVal <= activeStarLimit ? 'gold' : 'gray' }}
                    >
                        ★
                    </span>
                );
            })}
        </div>
    );
}
```
* **Explanation**: We combine mouse hover and click events. The active star limit is computed dynamically: `hoverRating || rating`. Hover states override click ratings temporarily.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 10: Redux Toolkit & Fiber](10_redux_toolkit_fiber.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
