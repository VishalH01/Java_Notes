# 💛 JavaScript Practical Coding Interview Guide

Welcome, JavaScript candidate! This guide compiles the **Top 50 most frequently asked JavaScript practical coding questions** in technical interviews. Every question includes a problem statement, clean JavaScript code, logical explanations, and performance/complexity notes.

---

## 🗺️ Index of Questions

1. [Implement custom Debounce](#1-implement-custom-debounce)
2. [Implement custom Throttle](#2-implement-custom-throttle)
3. [Deep Clone recursively](#3-deep-clone-recursively)
4. [Custom Promise Polyfill](#4-custom-promise-polyfill)
5. [Polyfill for Array.prototype.map](#5-polyfill-for-arrayprototypemap)
6. [Polyfill for Array.prototype.filter](#6-polyfill-for-arrayprototypefilter)
7. [Polyfill for Array.prototype.reduce](#7-polyfill-for-arrayprototypereduce)
8. [Polyfill for Function.prototype.bind](#8-polyfill-for-functionprototypebind)
9. [Custom Event Emitter Class](#9-custom-event-emitter-class)
10. [Implement Function Currying](#10-implement-function-currying)
11. [Flatten a Nested Array recursively](#11-flatten-a-nested-array-recursively)
12. [Deep Equality Comparison Helper](#12-deep-equality-comparison-helper)
13. [Memoize Function Helper](#13-memoize-function-helper)
14. [Implement custom setInterval using setTimeout](#14-implement-custom-setinterval-using-settimeout)
15. [Async Task Runner with Concurrency Limit K](#15-async-task-runner-with-concurrency-limit-k)
16. [Find the Intersection of Two Arrays](#16-find-the-intersection-of-two-arrays)
17. [Group By Array Helper](#17-group-by-array-helper)
18. [Check if String is Palindrome](#18-check-if-string-is-palindrome)
19. [Two Sum Index Finder](#19-two-sum-index-finder)
20. [Check for Balanced Parentheses](#20-check-for-balanced-parentheses)
21. [Generate All Permutations of an Array](#21-generate-all-permutations-of-an-array)
22. [Reverse a Singly Linked List](#22-reverse-a-singly-linked-list)
23. [Find First Non-Repeating Character](#23-find-first-non-repeating-character)
24. [Implement Custom Pipe / Compose Helper](#24-implement-custom-pipe--compose-helper)
25. [Nested Object Property Getter Safely](#25-nested-object-property-getter-safely)
26. [Binary Search Implementation](#26-binary-search-implementation)
27. [Bubble Sort Implementation](#27-bubble-sort-implementation)
28. [Quick Sort Implementation](#28-quick-sort-implementation)
29. [Merge Sort Implementation](#29-merge-sort-implementation)
30. [Detect Loop in a Linked List](#30-detect-loop-in-a-linked-list)
31. [Fibonacci Sequence Generation (Memoized)](#31-fibonacci-sequence-generation-memoized)
32. [Chunk Array into Sub-Arrays of Size S](#32-chunk-array-into-sub-arrays-of-size-s)
33. [Remove Duplicates from Array](#33-remove-duplicates-from-array)
34. [Find Longest Word in Sentence](#34-find-longest-word-in-sentence)
35. [Title Case a Sentence Converter](#35-title-case-a-sentence-converter)
36. [Check if Two Strings are Anagrams](#36-check-if-two-strings-are-anagrams)
37. [Sum of Numbers in Nested Array](#37-sum-of-numbers-in-nested-array)
38. [Custom Object Assign Polyfill](#38-custom-object-assign-polyfill)
39. [Transpose a 2D Matrix](#39-transpose-a-2d-matrix)
40. [Implement Custom JSON.stringify Parser](#40-implement-custom-jsonstringify-parser)
41. [Find Duplicate in N+1 Integer Array](#41-find-duplicate-in-n1-integer-array)
42. [Longest Common Prefix in String List](#42-longest-common-prefix-in-string-list)
43. [Find Missing Number in Array 1 to N](#43-find-missing-number-in-array-1-to-n)
44. [Valid IPv4 Address Validator](#44-valid-ipv4-address-validator)
45. [Custom URL Query String Parser](#45-custom-url-query-string-parser)
46. [Rotate Array by K Steps](#46-rotate-array-by-k-steps)
47. [Merge Two Sorted Arrays in Place](#47-merge-two-sorted-arrays-in-place)
48. [Max Sub-Array Sum (Kadane's Algorithm)](#48-max-sub-array-sum-kadanes-algorithm)
49. [Least Recently Used (LRU) Cache](#49-least-recently-used-lru-cache)
50. [Custom Promisify Converter Helper](#50-custom-promisify-converter-helper)

---

## 💻 JavaScript Coding Challenges

### 1. Implement custom Debounce
**Problem**: Write a function that delays invoking a callback until after `delay` ms have elapsed since the last time it was called.
```javascript
function debounce(fn, delay) {
    let timerId;

    return function(...args) {
        const context = this;
        clearTimeout(timerId); // Reset active timer on new execution call

        timerId = setTimeout(() => {
            fn.apply(context, args);
        }, delay);
    };
}
```
* **Explanation**: Returns a wrapper function that uses a closure to maintain the active timer ID. Every invocation clears the pending timer and schedules a new one.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 2. Implement custom Throttle
**Problem**: Create a function that restricts callback execution to run at most once every `limit` ms.
```javascript
function throttle(fn, limit) {
    let inThrottle = false;

    return function(...args) {
        const context = this;
        if (!inThrottle) {
            fn.apply(context, args);
            inThrottle = true;
            setTimeout(() => {
                inThrottle = false;
            }, limit);
        }
    };
}
```
* **Explanation**: Uses a boolean flag in a closure. When the throttled function runs, it executes the callback and sets the flag to true, ignoring subsequent calls until the timer resets the flag.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 3. Deep Clone recursively
**Problem**: Write a function that deep clones a JavaScript object, resolving nested structures, arrays, and circular references.
```javascript
function deepClone(obj, visited = new WeakMap()) {
    if (obj === null || typeof obj !== 'object') {
        return obj; // Return primitives directly
    }

    if (obj instanceof Date) return new Date(obj);
    if (obj instanceof RegExp) return new RegExp(obj);

    // Resolve circular references
    if (visited.has(obj)) {
        return visited.get(obj);
    }

    const clone = Array.isArray(obj) ? [] : {};
    visited.set(obj, clone);

    for (let key in obj) {
        if (obj.hasOwnProperty(key)) {
            clone[key] = deepClone(obj[key], visited);
        }
    }

    return clone;
}
```
* **Explanation**: We recursively clone object values. We pass a `WeakMap` to keep track of visited objects, resolving circular references to prevent infinite loops.
* **Complexity**: Time: $O(N)$ where $N$ is total properties count, Space: $O(N)$ memory depth.

---

### 4. Custom Promise Polyfill
**Problem**: Write a minimal Promise constructor that supports `resolve`, `reject`, and `.then()`.
```javascript
class MyPromise {
    constructor(executor) {
        this.state = 'pending';
        this.value = undefined;
        this.handlers = [];

        const resolve = (val) => {
            if (this.state !== 'pending') return;
            this.state = 'fulfilled';
            this.value = val;
            this.handlers.forEach(h => h(val));
        };

        const reject = (err) => {
            if (this.state !== 'pending') return;
            this.state = 'rejected';
            this.value = err;
        };

        try {
            executor(resolve, reject);
        } catch (e) {
            reject(e);
        }
    }

    then(callback) {
        if (this.state === 'fulfilled') {
            callback(this.value);
        } else {
            this.handlers.push(callback);
        }
        return this; // Return instance for chaining
    }
}
```
* **Explanation**: Implements a simple Promise structure. The constructor manages a `handlers` array, executing queued callbacks when `resolve` is called.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 5. Polyfill for Array.prototype.map
**Problem**: Implement `Array.prototype.map` manually without using the built-in map helper.
```javascript
Array.prototype.myMap = function(callback) {
    const output = [];
    
    for (let i = 0; i < this.length; i++) {
        if (i in this) { // Handle sparse arrays
            output.push(callback(this[i], i, this));
        }
    }
    
    return output;
};
```
* **Explanation**: We loop through the calling array, executing the callback function on each element and pushing the result to a new output array.
* **Complexity**: Time: $O(N)$ array elements, Space: $O(N)$.

---

### 6. Polyfill for Array.prototype.filter
**Problem**: Implement `Array.prototype.filter` manually.
```javascript
Array.prototype.myFilter = function(callback) {
    const output = [];
    
    for (let i = 0; i < this.length; i++) {
        if (i in this) {
            if (callback(this[i], i, this)) {
                output.push(this[i]);
            }
        }
    }
    
    return output;
};
```
* **Explanation**: We loop through the array, running the callback condition on each item and pushing matching elements to a new array.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 7. Polyfill for Array.prototype.reduce
**Problem**: Implement `Array.prototype.reduce` manually.
```javascript
Array.prototype.myReduce = function(callback, initialVal) {
    let accumulator = initialVal;
    let startIdx = 0;

    if (accumulator === undefined) {
        if (this.length === 0) throw new TypeError("Reduce of empty array with no initial value");
        accumulator = this[0];
        startIdx = 1;
    }

    for (let i = startIdx; i < this.length; i++) {
        if (i in this) {
            accumulator = callback(accumulator, this[i], i, this);
        }
    }

    return accumulator;
};
```
* **Explanation**: If no initial value is passed, the accumulator defaults to the first item and the loop starts at index 1.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 8. Polyfill for Function.prototype.bind
**Problem**: Implement `Function.prototype.bind` manually without using the native bind method.
```javascript
Function.prototype.myBind = function(context, ...args) {
    const targetFn = this;

    return function(...innerArgs) {
        return targetFn.apply(context, [...args, ...innerArgs]);
    };
};
```
* **Explanation**: Returns a wrapper function that uses `apply` to execute the target function with the bound context and merged arguments.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 9. Custom Event Emitter Class
**Problem**: Write an `EventEmitter` class with `on`, `off`, `emit`, and `once` methods.
```javascript
class EventEmitter {
    constructor() {
        this.events = {};
    }

    on(name, listener) {
        if (!this.events[name]) this.events[name] = [];
        this.events[name].push(listener);
    }

    off(name, listenerToRemove) {
        if (!this.events[name]) return;
        this.events[name] = this.events[name].filter(l => l !== listenerToRemove);
    }

    emit(name, ...args) {
        if (!this.events[name]) return;
        this.events[name].forEach(listener => listener(...args));
    }

    once(name, listener) {
        const wrapper = (...args) => {
            listener(...args);
            this.off(name, wrapper);
        };
        this.on(name, wrapper);
    }
}
```
* **Explanation**: Maps event names to arrays of listener functions, executing and managing the arrays when events are triggered or removed.
* **Complexity**: Time: $O(1)$ subscribe, $O(L)$ emit/unsubscribe where $L$ is listeners count.

---

### 10. Implement Function Currying
**Problem**: Write a curry helper that translates a function of arity $N$ into nested functions.
```javascript
function curry(fn) {
    return function curried(...args) {
        if (args.length >= fn.length) {
            return fn.apply(this, args); // Execute if we have all arguments
        } else {
            // Otherwise return a function to collect remaining arguments
            return function(...nextArgs) {
                return curried.apply(this, [...args, ...nextArgs]);
            };
        }
    };
}
```
* **Explanation**: Compares active argument size with the target function's expected argument count (`fn.length`).
* **Complexity**: Time: $O(1)$ nesting returns.

---

### 11. Flatten a Nested Array recursively
**Problem**: Flatten a nested array recursively up to a specified depth limit.
```javascript
function flattenArray(arr, depth = 1) {
    const result = [];

    for (let val of arr) {
        if (Array.isArray(val) && depth > 0) {
            result.push(...flattenArray(val, depth - 1));
        } else {
            result.push(val);
        }
    }

    return result;
}
```
* **Explanation**: Loops through items. If an item is an array and depth limit is not exceeded, we recursively flatten it, otherwise we push the item as-is.
* **Complexity**: Time: $O(N)$ total elements, Space: $O(N)$ output size.

---

### 12. Deep Equality Comparison Helper
**Problem**: Write an `isEqual(a, b)` function to deeply compare two values.
```javascript
function isEqual(a, b) {
    if (a === b) return true;

    if (typeof a !== 'object' || a === null || typeof b !== 'object' || b === null) {
        return false;
    }

    const keysA = Object.keys(a);
    const keysB = Object.keys(b);

    if (keysA.length !== keysB.length) return false;

    for (let key of keysA) {
        if (!keysB.includes(key) || !isEqual(a[key], b[key])) {
            return false;
        }
    }

    return true;
}
```
* **Explanation**: Recursively verifies objects. If lengths match, we recursively check if every key and nested property are equal.
* **Complexity**: Time: $O(N)$, Space: $O(H)$ recursion depth.

---

### 13. Memoize Function Helper
**Problem**: Create a function that caches the results of a callback by input parameters.
```javascript
function memoize(fn) {
    const cache = new Map();

    return function(...args) {
        const cacheKey = JSON.stringify(args);
        if (cache.has(cacheKey)) {
            return cache.get(cacheKey);
        }

        const output = fn.apply(this, args);
        cache.set(cacheKey, output);
        return output;
    };
}
```
* **Explanation**: Uses a Map to store results. Input arguments are serialized as keys to retrieve cached outputs.
* **Complexity**: Time: $O(1)$ cache hit, Space: $O(C)$ cache store.

---

### 14. Implement custom setInterval using setTimeout
**Problem**: Write a custom setInterval replacement using recursive setTimeout calls.
```javascript
function customInterval(callback, delay) {
    let active = true;

    function run() {
        if (!active) return;
        callback();
        setTimeout(run, delay); // Schedule next execution recursively
    }

    setTimeout(run, delay);

    return {
        clear: () => { active = false; }
    };
}
```
* **Explanation**: Recursively schedules the next run after the current callback finishes, preventing executions from overlapping on lag.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 15. Async Task Runner with Concurrency Limit K
**Problem**: Run an array of async tasks in parallel, limiting concurrent executions to at most $K$ tasks.
```javascript
function runConcurrentTasks(tasks, K) {
    return new Promise((resolve) => {
        let activeCount = 0;
        let index = 0;
        const results = [];

        function runNext() {
            if (index >= tasks.length && activeCount === 0) {
                resolve(results);
                return;
            }

            while (activeCount < K && index < tasks.length) {
                const currentIdx = index;
                const task = tasks[currentIdx];
                index++;
                activeCount++;

                task().then((res) => {
                    results[currentIdx] = res;
                    activeCount--;
                    runNext(); // Start next task when one completes
                });
            }
        }

        runNext();
    });
}
```
* **Explanation**: Spawns up to $K$ tasks simultaneously. When a task completes, we decrement the count and pull the next task from the queue.
* **Complexity**: Time: $O(N)$, Space: $O(N)$ results.

---

### 16. Find the Intersection of Two Arrays
**Problem**: Return an array of unique elements present in both input arrays.
```javascript
function getIntersection(arr1, arr2) {
    const set1 = new Set(arr1);
    const intersected = arr2.filter(item => set1.has(item));
    return [...new Set(intersected)]; // Remove duplicates
}
```
* **Explanation**: We store one array in a Set for $O(1)$ lookups, and filter the second array to keep matching items.
* **Complexity**: Time: $O(A + B)$ array sizes, Space: $O(A)$.

---

### 17. Group By Array Helper
**Problem**: Group elements of an array based on a category function key.
```javascript
function groupBy(array, keyGetter) {
    const output = {};

    for (let item of array) {
        const category = typeof keyGetter === 'function' ? keyGetter(item) : item[keyGetter];
        if (!output[category]) {
            output[category] = [];
        }
        output[category].push(item);
    }

    return output;
}
```
* **Explanation**: Iterates over items, calling the categorizer to extract keys and appending matching items to group arrays in an object.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 18. Check if String is Palindrome
**Problem**: Verify if a string is a palindrome, ignoring non-alphanumeric characters and casing.
```javascript
function isPalindrome(str) {
    const clean = str.toLowerCase().replace(/[^a-z0-9]/g, '');
    let left = 0;
    let right = clean.length - 1;

    while (left < right) {
        if (clean[left] !== clean[right]) return false;
        left++;
        right--;
    }
    return true;
}
```
* **Explanation**: Cleans the string and uses a two-pointer approach, comparing characters from both ends moving inward.
* **Complexity**: Time: $O(C)$ character size, Space: $O(1)$.

---

### 19. Two Sum Index Finder
**Problem**: Find the indices of two numbers in an array that add up to a target sum.
```javascript
function twoSum(nums, target) {
    const visitedMap = new Map(); // Store value -> index

    for (let i = 0; i < nums.length; i++) {
        const complement = target - nums[i];
        if (visitedMap.has(complement)) {
            return [visitedMap.get(complement), i];
        }
        visitedMap.set(nums[i], i);
    }
    return [];
}
```
* **Explanation**: We store visited numbers and their indices in a Map. For each item, we check if its complement is already in the map.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 20. Check for Balanced Parentheses
**Problem**: Verify if all brackets in a string are closed in the correct order: `(`, `[`, `{`.
```javascript
function isBalanced(str) {
    const stack = [];
    const bracketsMap = { ')': '(', ']': '[', '}': '{' };

    for (let char of str) {
        if (['(', '[', '{'].includes(char)) {
            stack.push(char); // Push opening brackets
        } else if ([')', ']', '}'].includes(char)) {
            if (stack.pop() !== bracketsMap[char]) {
                return false; // Mismatched bracket close!
            }
        }
    }
    return stack.length === 0;
}
```
* **Explanation**: Uses a stack. Opening brackets are pushed onto the stack. Closing brackets pop the top item and verify if it matches.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 21. Generate All Permutations of an Array
**Problem**: Return all possible permutations of an array.
```javascript
function getPermutations(arr) {
    const output = [];

    function permute(curr, remaining) {
        if (remaining.length === 0) {
            output.push(curr);
            return;
        }

        for (let i = 0; i < remaining.length; i++) {
            const next = [...curr, remaining[i]];
            const rest = remaining.filter((_, idx) => idx !== i);
            permute(next, rest);
        }
    }

    permute([], arr);
    return output;
}
```
* **Explanation**: Backtracking pattern. Recursively builds permutations by picking one item and passing the remaining items down.
* **Complexity**: Time: $O(N!)$, Space: $O(N!)$.

---

### 22. Reverse a Singly Linked List
**Problem**: Reverse a singly linked list in-place.
```javascript
function reverseLinkedList(head) {
    let prev = null;
    let curr = head;

    while (curr !== null) {
        let nextNode = curr.next; // Cache next node pointer
        curr.next = prev;         // Reverse the link
        prev = curr;              // Shift pointer variables forward
        curr = nextNode;
    }
    return prev; // Return new head pointer
}
```
* **Explanation**: Iterates through the list, caching the next node pointer and updating the current node's next pointer to point backward.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 23. Find First Non-Repeating Character
**Problem**: Find the first character in a string that does not repeat.
```javascript
function firstUniqueChar(str) {
    const charMap = new Map();

    for (let char of str) {
        charMap.set(char, (charMap.get(char) || 0) + 1);
    }

    for (let char of str) {
        if (charMap.get(char) === 1) return char;
    }
    return null;
}
```
* **Explanation**: We build a frequency map of characters, and then scan the string again to find the first character with a count of 1.
* **Complexity**: Time: $O(N)$, Space: $O(U)$ unique characters.

---

### 24. Implement Custom Pipe / Compose Helper
**Problem**: Write functions that compose other functions (left-to-right for `pipe`, right-to-left for `compose`).
```javascript
const pipe = (...fns) => (val) => fns.reduce((acc, fn) => fn(acc), val);

const compose = (...fns) => (val) => fns.reduceRight((acc, fn) => fn(acc), val);
```
* **Explanation**: Aggregates function calls. `pipe` passes values forward left-to-right; `compose` runs them right-to-left.
* **Complexity**: Time: $O(F)$ functions size, Space: $O(1)$.

---

### 25. Nested Object Property Getter Safely
**Problem**: Safely access a nested property using a path string (e.g. `'a.b.c'`).
```javascript
function getNestedValue(obj, path, defaultVal = undefined) {
    const keys = path.split('.');
    let current = obj;

    for (let key of keys) {
        if (current === null || current === undefined) {
            return defaultVal;
        }
        current = current[key];
    }

    return current === undefined ? defaultVal : current;
}
```
* **Explanation**: Splits the path by `.`. It traverses the object keys sequentially, returning the default value if it hits undefined.
* **Complexity**: Time: $O(P)$ path length, Space: $O(1)$.

---

### 26. Binary Search Implementation
**Problem**: Locate the index of a target value in a sorted array.
```javascript
function binarySearch(arr, target) {
    let low = 0;
    let high = arr.length - 1;

    while (low <= high) {
        const mid = Math.floor((low + high) / 2);
        if (arr[mid] === target) return mid;
        
        if (arr[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return -1;
}
```
* **Explanation**: Halves search space. We compare the middle element with the target, moving indices to search left or right halves.
* **Complexity**: Time: $O(\log N)$, Space: $O(1)$.

---

### 27. Bubble Sort Implementation
**Problem**: Sort an array using the Bubble Sort algorithm.
```javascript
function bubbleSort(arr) {
    const n = arr.length;
    for (let i = 0; i < n; i++) {
        let swapped = false;
        for (let j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                // Swap elements
                [arr[j], arr[j + 1]] = [arr[j + 1], arr[j]];
                swapped = true;
            }
        }
        if (!swapped) break; // Break early if already sorted
    }
    return arr;
}
```
* **Explanation**: Loops through the array, swapping adjacent elements if they are out of order, bubbling the largest value to the end.
* **Complexity**: Time: $O(N^2)$ average, Space: $O(1)$.

---

### 28. Quick Sort Implementation
**Problem**: Sort an array using the Quick Sort algorithm.
```javascript
function quickSort(arr) {
    if (arr.length <= 1) return arr;

    const pivot = arr[arr.length - 1]; // Pick last item as pivot
    const left = [];
    const right = [];

    for (let i = 0; i < arr.length - 1; i++) {
        if (arr[i] < pivot) {
            left.push(arr[i]);
        } else {
            right.push(arr[i]);
        }
    }

    return [...quickSort(left), pivot, ...quickSort(right)];
}
```
* **Explanation**: Divide and conquer pattern. Partitions array into items smaller than pivot and items larger, sorting them recursively.
* **Complexity**: Time: $O(N \log N)$ average, Space: $O(N)$ partitioning.

---

### 29. Merge Sort Implementation
**Problem**: Sort an array using the Merge Sort algorithm.
```javascript
function mergeSort(arr) {
    if (arr.length <= 1) return arr;

    const mid = Math.floor(arr.length / 2);
    const left = mergeSort(arr.slice(0, mid));
    const right = mergeSort(arr.slice(mid));

    return merge(left, right);
}

function merge(left, right) {
    const result = [];
    let l = 0, r = 0;

    while (l < left.length && r < right.length) {
        if (left[l] < right[r]) {
            result.push(left[l]);
            l++;
        } else {
            result.push(right[r]);
            r++;
        }
    }

    return [...result, ...left.slice(l), ...right.slice(r)];
}
```
* **Explanation**: Recursively splits arrays in halves until size is 1, and then merges the sorted halves back together.
* **Complexity**: Time: $O(N \log N)$, Space: $O(N)$.

---

### 30. Detect Loop in a Linked List
**Problem**: Verify if a linked list contains a cycle.
```javascript
function hasCycle(head) {
    let slow = head;
    let fast = head;

    while (fast !== null && fast.next !== null) {
        slow = slow.next;
        fast = fast.next.next;

        if (slow === fast) return true; // Cycle detected!
    }
    return false;
}
```
* **Explanation**: Floyd's Cycle-Finding Algorithm. We move a slow pointer by 1 step and a fast pointer by 2 steps. If they meet, there is a cycle.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 31. Fibonacci Sequence Generation (Memoized)
**Problem**: Return the $N$-th Fibonacci number efficiently.
```javascript
function fibonacci(n, cache = {}) {
    if (n <= 1) return n;
    if (cache[n]) return cache[n];

    cache[n] = fibonacci(n - 1, cache) + fibonacci(n - 2, cache);
    return cache[n];
}
```
* **Explanation**: Uses a memoization cache object to store already computed Fibonacci numbers, reducing the exponential time complexity.
* **Complexity**: Time: $O(N)$, Space: $O(N)$ recursion depth.

---

### 32. Chunk Array into Sub-Arrays of Size S
**Problem**: Split an array into chunk arrays of size `S`.
```javascript
function chunkArray(arr, size) {
    const chunked = [];
    
    for (let i = 0; i < arr.length; i += size) {
        chunked.push(arr.slice(i, i + size));
    }
    
    return chunked;
}
```
* **Explanation**: Iterates through the array by increments of `size`, pushing sliced sub-arrays into a container array.
* **Complexity**: Time: $O(N)$ array elements, Space: $O(N)$.

---

### 33. Remove Duplicates from Array
**Problem**: Remove duplicates from an array containing primitives.
```javascript
function removeDuplicates(arr) {
    return [...new Set(arr)];
}
```
* **Explanation**: Converts the array to a `Set` (which automatically ignores duplicate values) and spreads it back into an array.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 34. Find Longest Word in Sentence
**Problem**: Find the longest word inside a text string.
```javascript
function findLongestWord(sentence) {
    const words = sentence.split(/\s+/);
    let longest = "";

    for (let word of words) {
        // Strip punctuation
        const clean = word.replace(/[^a-zA-Z]/g, '');
        if (clean.length > longest.length) {
            longest = clean;
        }
    }
    return longest;
}
```
* **Explanation**: Splits the sentence by whitespace, strips non-alphabetic characters, and tracks the longest word found.
* **Complexity**: Time: $O(W)$ words count, Space: $O(W)$.

---

### 35. Title Case a Sentence Converter
**Problem**: Capitalize the first letter of each word in a string.
```javascript
function titleCase(str) {
    return str
        .toLowerCase()
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}
```
* **Explanation**: Splits the lowercase string by spaces, capitalizes the first character of each word, and joins them back together.
* **Complexity**: Time: $O(C)$ character size, Space: $O(W)$ words list.

---

### 36. Check if Two Strings are Anagrams
**Problem**: Check if two strings contain the exact same characters in different orders.
```javascript
function isAnagram(str1, str2) {
    const clean1 = str1.toLowerCase().replace(/[^a-z]/g, '').split('').sort().join('');
    const clean2 = str2.toLowerCase().replace(/[^a-z]/g, '').split('').sort().join('');
    return clean1 === clean2;
}
```
* **Explanation**: Cleans, sorts, and compares the character arrays of both strings.
* **Complexity**: Time: $O(N \log N)$ sorting time.

---

### 37. Sum of Numbers in Nested Array
**Problem**: Sum all numeric values in a nested array structure.
```javascript
function sumNestedArray(arr) {
    return arr.reduce((acc, curr) => {
        if (Array.isArray(curr)) {
            return acc + sumNestedArray(curr);
        }
        return acc + (typeof curr === 'number' ? curr : 0);
    }, 0);
}
```
* **Explanation**: Uses `.reduce()` to recursively sum elements if they are arrays, otherwise adding their numeric values.
* **Complexity**: Time: $O(N)$ elements, Space: $O(D)$ recursion depth.

---

### 38. Custom Object Assign Polyfill
**Problem**: Implement `Object.assign` manually.
```javascript
function myObjectAssign(target, ...sources) {
    if (target === null || target === undefined) {
        throw new TypeError("Cannot convert null or undefined to object");
    }

    const output = Object(target);

    for (let source of sources) {
        if (source !== null && source !== undefined) {
            for (let key in source) {
                if (Object.prototype.hasOwnProperty.call(source, key)) {
                    output[key] = source[key];
                }
            }
        }
    }
    return output;
}
```
* **Explanation**: Wraps target in an object, copies all own enumerable properties from source objects, and returns the modified target.
* **Complexity**: Time: $O(P)$ properties size, Space: $O(1)$.

---

### 39. Transpose a 2D Matrix
**Problem**: Rotate rows into columns for a 2D grid matrix.
```javascript
function transpose(matrix) {
    const rows = matrix.length;
    const cols = matrix[0].length;
    const output = Array.from({ length: cols }, () => []);

    for (let r = 0; r < rows; r++) {
        for (let c = 0; c < cols; c++) {
            output[c][r] = matrix[r][c]; // Swap coordinates
        }
    }

    return output;
}
```
* **Explanation**: Creates an empty array container, and loops through the matrix to swap coordinates: `output[c][r] = matrix[r][c]`.
* **Complexity**: Time: $O(R \times C)$, Space: $O(R \times C)$.

---

### 40. Implement Custom JSON.stringify Parser
**Problem**: Create a basic JSON stringifier supporting strings, numbers, booleans, arrays, and objects.
```javascript
function customJsonStringify(val) {
    if (typeof val === 'string') return `"${val}"`;
    if (typeof val === 'number' || typeof val === 'boolean') return String(val);
    if (val === null) return "null";

    if (Array.isArray(val)) {
        const arrItems = val.map(item => customJsonStringify(item));
        return `[${arrItems.join(',')}]`;
    }

    if (typeof val === 'object') {
        const objKeys = Object.keys(val).map(key => {
            return `"${key}":${customJsonStringify(val[key])}`;
        });
        return `{${objKeys.join(',')}}`;
    }
    return undefined;
}
```
* **Explanation**: Checks types. Primitives are wrapped in quotes, and arrays/objects are mapped recursively and wrapped in braces.
* **Complexity**: Time: $O(N)$ properties count, Space: $O(D)$ recursion depth.

---

### 41. Find Duplicate in N+1 Integer Array
**Problem**: Find the single duplicate number in an array of $N+1$ integers ranging from 1 to $N$.
```javascript
function findDuplicate(nums) {
    let slow = nums[0];
    let fast = nums[0];

    // Phase 1: Locate intersection point in loop
    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow !== fast);

    // Phase 2: Find entrance to cycle loop
    let pointer1 = nums[0];
    let pointer2 = slow;

    while (pointer1 !== pointer2) {
        pointer1 = nums[pointer1];
        pointer2 = nums[pointer2];
    }

    return pointer1;
}
```
* **Explanation**: Floyd's Cycle Detection. We treat array values as pointers to traverse a cycle, finding the entry point which represents the duplicate.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 42. Longest Common Prefix in String List
**Problem**: Find the longest common prefix string among an array of strings.
```javascript
function longestCommonPrefix(strs) {
    if (strs.length === 0) return "";
    let prefix = strs[0];

    for (let i = 1; i < strs.length; i++) {
        while (strs[i].indexOf(prefix) !== 0) {
            prefix = prefix.substring(0, prefix.length - 1); // Shorten prefix
            if (prefix === "") return "";
        }
    }
    return prefix;
}
```
* **Explanation**: Assumes the first string is the prefix, and iteratively shortens it until it matches the start of all other strings.
* **Complexity**: Time: $O(S)$ total characters, Space: $O(1)$.

---

### 43. Find Missing Number in Array 1 to N
**Problem**: Find the single missing number in an array containing numbers from 1 to $N$.
```javascript
function findMissingNumber(arr, N) {
    const expectedSum = (N * (N + 1)) / 2;
    const actualSum = arr.reduce((acc, curr) => acc + curr, 0);
    return expectedSum - actualSum;
}
```
* **Explanation**: Calculates the mathematical sum from 1 to $N$, and subtracts the actual sum of the array to find the missing number.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 44. Valid IPv4 Address Validator
**Problem**: Verify if a string is a valid IPv4 address.
```javascript
function isValidIPv4(ip) {
    const parts = ip.split('.');
    if (parts.length !== 4) return false;

    for (let part of parts) {
        if (!/^\d+$/.test(part)) return false; // Must contain digits only
        const val = Number(part);
        if (val < 0 || val > 255) return false;
        
        // Block leading zeros (e.g. "01" is invalid)
        if (part.length > 1 && part[0] === '0') return false;
    }
    return true;
}
```
* **Explanation**: Splits by `.`. It checks that there are 4 segments, each between 0 and 255, and blocks leading zeros.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 45. Custom URL Query String Parser
**Problem**: Parse a URL query string (e.g. `?name=alice&age=30`) into a key-value object.
```javascript
function parseQueryString(url) {
    const output = {};
    const queryIdx = url.indexOf('?');
    if (queryIdx === -1) return output;

    const queryString = url.slice(queryIdx + 1);
    const pairs = queryString.split('&');

    for (let pair of pairs) {
        const [key, val] = pair.split('=');
        if (key) {
            output[decodeURIComponent(key)] = decodeURIComponent(val || '');
        }
    }
    return output;
}
```
* **Explanation**: Extracts the query portion, splits it by `&` and `=`, and decodes keys and values into an object.
* **Complexity**: Time: $O(L)$ URL length, Space: $O(P)$ parameters.

---

### 46. Rotate Array by K Steps
**Problem**: Rotate an array to the right by $K$ steps.
```javascript
function rotateArray(arr, K) {
    const n = arr.length;
    const steps = K % n; // Resolve offset bounds

    // Helper to reverse sub-array in place
    const reverse = (start, end) => {
        while (start < end) {
            [arr[start], arr[end]] = [arr[end], arr[start]];
            start++;
            end--;
        }
    };

    reverse(0, n - 1);       // 1. Reverse entire array
    reverse(0, steps - 1);   // 2. Reverse first steps segment
    reverse(steps, n - 1);   // 3. Reverse remaining segment
    return arr;
}
```
* **Explanation**: We reverse the entire array, then reverse the first $K$ elements, and finally reverse the remaining elements in place.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 47. Merge Two Sorted Arrays in Place
**Problem**: Merge array 2 into array 1, assuming array 1 has enough space at the end.
```javascript
function mergeSortedArrays(nums1, m, nums2, n) {
    let p1 = m - 1; // End of active nums1 elements
    let p2 = n - 1; // End of nums2 elements
    let p = m + n - 1; // End of nums1 array space

    while (p1 >= 0 && p2 >= 0) {
        if (nums1[p1] > nums2[p2]) {
            nums1[p] = nums1[p1];
            p1--;
        } else {
            nums1[p] = nums2[p2];
            p2--;
        }
        p--;
    }

    // Copy any remaining elements from nums2
    while (p2 >= 0) {
        nums1[p] = nums2[p2];
        p2--;
        p--;
    }
}
```
* **Explanation**: Iterates from the back of both arrays, placing the larger elements at the end of the first array to avoid overwriting values.
* **Complexity**: Time: $O(M + N)$, Space: $O(1)$.

---

### 48. Max Sub-Array Sum (Kadane's Algorithm)
**Problem**: Find the contiguous sub-array within a numeric array that has the largest sum.
```javascript
function maxSubArray(nums) {
    let maxSoFar = nums[0];
    let currentMax = nums[0];

    for (let i = 1; i < nums.length; i++) {
        currentMax = Math.max(nums[i], currentMax + nums[i]);
        maxSoFar = Math.max(maxSoFar, currentMax);
    }
    return maxSoFar;
}
```
* **Explanation**: Kadane's Algorithm. At each step, we decide whether to add the current number to the running sum or start a new sub-array.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 49. Least Recently Used (LRU) Cache
**Problem**: Design an LRU Cache class with `get` and `put` operations and a capacity limit.
```javascript
class LRUCache {
    constructor(capacity) {
        this.capacity = capacity;
        this.cache = new Map(); // Map preserves insertion order!
    }

    get(key) {
        if (!this.cache.has(key)) return -1;

        // Refresh key to mark as most recently used
        const val = this.cache.get(key);
        this.cache.delete(key);
        this.cache.set(key, val);
        return val;
    }

    put(key, value) {
        if (this.cache.has(key)) {
            this.cache.delete(key);
        } else if (this.cache.size >= this.capacity) {
            // Delete least recently used item (first item in Map keys)
            const oldestKey = this.cache.keys().next().value;
            this.cache.delete(oldestKey);
        }
        this.cache.set(key, value);
    }
}
```
* **Explanation**: We use a `Map` since it maintains key insertion order. Reading or updating a key moves it to the end of the map.
* **Complexity**: Time: $O(1)$ for get/put, Space: $O(C)$ capacity limit.

---

### 50. Custom Promisify Converter Helper
**Problem**: Create a function that converts standard Node-style callback functions into Promise returns.
```javascript
function promisify(fn) {
    return function(...args) {
        return new Promise((resolve, reject) => {
            // Append the callback function as the last argument
            fn.apply(this, [...args, (err, result) => {
                if (err) {
                    reject(err);
                } else {
                    resolve(result);
                }
            }]);
        });
    };
}
```
* **Explanation**: Returns a wrapper function that returns a Promise, appending a custom error-first callback to the target function's arguments.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

## ⏭️ Next Steps

* **JavaScript Roadmap**: [🏠 Back to JavaScript Roadmap](README.md)
* **Master Roadmap**: [🏠 Back to Master Roadmap](../README.md)
