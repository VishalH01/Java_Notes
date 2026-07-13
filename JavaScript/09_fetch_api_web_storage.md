# 🌐 Topic 09: Fetch API & Web Storage

Welcome back, network communicator! In this chapter, we will learn about the **Fetch API and Web Storage (localStorage, sessionStorage, and cookies)**. Modern web apps need to load data from servers dynamically and store settings locally. We will learn how to make HTTP requests, handle JSON responses, and choose the right storage tool to save session data in the browser.

---

## 🏠 The Big Picture & Real-Life Example

### 📬 The Mail Courier & The Three Office Desks
Imagine you are running a business in a small office:
* **The Fetch API (The Mail Courier)**: You need new document logs from the corporate headquarters. You write an order slip, hand it to a mail courier (**`fetch(url)`**), and tell him to deliver it. You keep working. Later, the courier knocks on your door with a package (**Promise resolved to Response**). You open the box, compile the paper into reading files (**`response.json()`**), and use the data.
* **CORS (The Gate Security Check)**: The courier arrives at the headquarters. The security guard checks his origin ID: *"You are from LocalOffice.com, but you are trying to fetch from PrivateServer.com! Denied!"* (**CORS block**).
* **The Three Storage Options**:
  * **sessionStorage (The Scratchpad)**: You write your active phone call notes on a sticky note. When the call ends and you exit your desk (**Tab closed**), you throw the note in the bin.
  * **localStorage (The Safe)**: You write your theme preferences and login name in a sturdy metal file cabinet. You lock it. If you go home, sleep, and return tomorrow (**Browser restarted**), the files are still there.
  * **Cookies (The ID Badge)**: You print a tiny name tag and pin it to your shirt. Every time you walk to any department desk, the staff looks at your badge automatically (**Sent with every HTTP request**).

---

## 🔬 Let's Look Closer

### 1. Fetch API
The `fetch()` method starts a network request and returns a Promise.
* It only rejects if there is a network failure (like no internet connection).
* If the server responds with a `404 Not Found` or `500 Server Error`, the Promise still resolves. You must verify response status using `response.ok` (which is true for statuses between 200 and 299).

### 2. Web Storage Options

| Storage Type | Capacity | Expiration | Scope | Automatic HTTP Sent? |
|---|---|---|---|---|
| **localStorage** | ~5MB - 10MB | Never | Same Origin | No |
| **sessionStorage** | ~5MB | On Tab Close | Same Tab | No |
| **Cookies** | ~4KB | Manual / Max-Age | Set Domain | Yes (Sent with every request) |

---

## 💻 Code Sandbox

Let's write a data-fetching script and store the result in `localStorage`.

```javascript
// --- 1. Fetch API with Error Verification ---
async function loadUserData(userId) {
    const url = `https://jsonplaceholder.typicode.com/users/${userId}`;
    try {
        const response = await fetch(url);
        
        // Fetch resolves even on 404/500, so we must check response.ok
        if (!response.ok) {
            throw new Error(`HTTP Error! Status: ${response.status}`);
        }
        
        const userData = await response.json();
        console.log("Fetch success user:", userData.name);
        
        // Save user to LocalStorage
        saveUserLocal(userData);
    } catch (error) {
        console.log("Network / HTTP Fetch Error caught:", error.message);
    }
}

// --- 2. Web Storage operations ---
function saveUserLocal(userObj) {
    // LocalStorage only stores strings, so we must serialize objects
    localStorage.setItem("cached_user", JSON.stringify(userObj));
    console.log("User saved to LocalStorage.");
}

function readUserLocal() {
    const dataString = localStorage.getItem("cached_user");
    if (dataString) {
        const user = JSON.parse(dataString);
        console.log("Read user from LocalStorage:", user.name);
        return user;
    }
    console.log("No user found in LocalStorage.");
    return null;
}

// Running flow
async function main() {
    await loadUserData(1);
    readUserLocal();
}

main();
```

---

## 🧠 Points to Remember

* **`localStorage` is synchronous**. Accessing `localStorage.getItem()` blocks the main thread. Avoid reading or writing large data blocks repeatedly, as it can cause UI lag.
* **CORS is a browser security feature**. CORS is enforced by the browser, not the server. If the server does not return the correct `Access-Control-Allow-Origin` headers, the browser will block JavaScript from reading the response.

---

## 📖 Key Definitions

* **Fetch API**: A modern interface used to make asynchronous network requests, returning a Promise that resolves to a Response object.
* **localStorage**: A web storage mechanism that stores key-value pairs in the browser with no expiration date, surviving browser restarts.
* **sessionStorage**: A web storage mechanism that stores key-value pairs for the duration of the page session, cleared when the tab is closed.
* **Cookies**: Small text data fragments stored in the browser, sent automatically with every HTTP request to the server, often used for session tokens.
* **CORS (Cross-Origin Resource Sharing)**: A security mechanism that uses HTTP headers to restrict which domains can request resources from a web server.

---

## ❓ Interview Questions

### 🟢 Basic Questions (1-20)

1. **What is the Fetch API?**
   * *Answer*: A modern interface used to make asynchronous network requests, returning a Promise that resolves to a Response object.
2. **What does `response.json()` do?**
   * *Answer*: It reads the body of a Response stream and parses it as JSON, returning a Promise that resolves to the parsed object.
3. **What is the difference between `localStorage` and `sessionStorage`?**
   * *Answer*: `localStorage` persists data indefinitely. `sessionStorage` clears data automatically when the browser tab is closed.
4. **How do you save a key-value pair in `localStorage`?**
   * *Answer*: By calling `localStorage.setItem('key', 'value')`.
5. **How do you read a key-value pair from `localStorage`?**
   * *Answer*: By calling `localStorage.getItem('key')`.
6. **How do you delete a specific key from `localStorage`?**
   * *Answer*: By calling `localStorage.removeItem('key')`.
7. **What is a Cookie?**
   * *Answer*: A small fragment of text data stored in the browser that is sent automatically to the server with every HTTP request.
8. **What are the storage capacity limits of LocalStorage vs Cookies?**
   * *Answer*: LocalStorage supports around 5MB-10MB of data. Cookies only support around 4KB.
9. **Does a `fetch()` Promise reject on 404 or 500 HTTP status codes?**
   * *Answer*: No, it only rejects on network failures. To catch HTTP errors, you must check if `response.ok` is false.
10. **What is CORS?**
    * *Answer*: Cross-Origin Resource Sharing. A browser security mechanism that restricts cross-origin network requests.
11. **How do you clear all items stored in `localStorage`?**
    * *Answer*: By calling `localStorage.clear()`.
12. **Can you store JavaScript objects directly inside `localStorage`?**
    * *Answer*: No, `localStorage` only stores strings. You must serialize objects using `JSON.stringify()` before saving them.
13. **What is the purpose of `response.ok`?**
    * *Answer*: A boolean property indicating if the response HTTP status code is a success (between 200 and 299).
14. **How do you configure a `fetch()` request to perform a POST action?**
    * *Answer*: By passing an options object: `fetch(url, { method: 'POST', body: JSON.stringify(data) })`.
15. **What is the difference between Cookies and Web Storage in terms of network transmission?**
    * *Answer*: Cookies are sent to the server automatically with every HTTP request. Web Storage data is purely local and never sent automatically.
16. **How do you set a Cookie's expiration date?**
    * *Answer*: By appending a `max-age` (in seconds) or `expires` attribute to the cookie string.
17. **What is same-origin policy?**
    * *Answer*: A security policy that permits scripts running on a page to access data on another page only if both pages share the same protocol, host, and port.
18. **What does `response.text()` do?**
    * *Answer*: It reads the body of a Response stream and returns a Promise that resolves to the raw text string.
19. **How do you read cookies in JavaScript?**
    * *Answer*: By reading the `document.cookie` property, which returns a semicolon-separated string of all active cookies.
20. **Can you access LocalStorage across different browser types (e.g. Chrome to Safari)?**
    * *Answer*: No, Web Storage is isolated per browser installation and per domain origin.

### 🟡 Intermediate Questions (21-40)

21. **Why is `localStorage` considered synchronous, and how can it impact UI performance?**
    * *Answer*: It reads/writes data directly to the disk on the main execution thread. Reading large strings blocks execution, causing UI stuttering during scrolling or animations.
22. **Explain the purpose of the `HttpOnly` attribute in cookie configurations.**
    * *Answer*: It prevents client-side scripts from reading the cookie via `document.cookie`, protecting session tokens from Cross-Site Scripting (XSS) attacks.
23. **What is the difference between `Access-Control-Allow-Origin` and `Access-Control-Allow-Credentials` headers in CORS?**
    * *Answer*: `Allow-Origin` lists which domains can access the resource. `Allow-Credentials` is a boolean indicating if the browser should send cookies and credentials with the request.
24. **How do you send authorization headers using the Fetch API?**
    * *Answer*: Pass them inside the headers property of the fetch options object:
      ```javascript
      fetch(url, { headers: { 'Authorization': 'Bearer TOKEN_STRING' } });
      ```
25. **What is a CORS Preflight request, and when does the browser trigger it?**
    * *Answer*: An OPTIONS request sent by the browser to verify if the server approves the request method and headers. It triggers for requests using custom headers or methods other than GET, POST, or HEAD.
26. **Explain what the `Secure` attribute does when configuring cookies.**
    * *Answer*: It ensures the cookie is only transmitted over secure HTTPS connections, preventing interception on unencrypted HTTP paths.
27. **What is the `SameSite` cookie attribute, and what are its three options?**
    * *Answer*: A cookie protection attribute. Options: (1) `Strict` (sent only on same-site requests), (2) `Lax` (sent on cross-site navigations), and (3) `None` (sent on all cross-site requests; requires `Secure`).
28. **How does JavaScript handle streaming responses using Fetch?**
    * *Answer*: By accessing `response.body`, which returns a `ReadableStream` reader, allowing developers to process chunks of data as they arrive.
29. **What does the `storage` event do, and when is it triggered?**
    * *Answer*: An event triggered on `window` when `localStorage` or `sessionStorage` is updated in a different tab or window of the same origin.
30. **Explain how to set a request timeout using `AbortController` in Fetch.**
    * *Answer*:
      ```javascript
      const controller = new AbortController();
      setTimeout(() => controller.abort(), 5000); // 5-second timeout
      fetch(url, { signal: controller.signal });
      ```
31. **What is the difference between Fetch and Axios?**
    * *Answer*: Fetch is built-in and returns raw responses. Axios is an external library that automatically converts JSON, handles interceptors, and supports request cancellation by default.
32. **Can you save binary files (like image blobs) directly in LocalStorage?**
    * *Answer*: No, you must convert the binary file to a Base64 string first, which increases the data size by 33%.
33. **Explain what JSON serialization and deserialization are.**
    * *Answer*: Serialization converts a JavaScript object into a JSON string (`JSON.stringify`). Deserialization parses a JSON string back into an object (`JSON.parse`).
34. **What is the purpose of the `referrerPolicy` option in Fetch?**
    * *Answer*: It configures the browser to include or omit the Referer header (the URL of the origin page) during outgoing HTTP requests.
35. **Why should you avoid using `localStorage` to store sensitive data like JWTs?**
    * *Answer*: Because `localStorage` is accessible to client-side scripts. If an attacker executes malicious code on the page (XSS), they can easily read and steal the token.
36. **Explain the behavior of sessionStorage when duplicating browser tabs.**
    * *Answer*: Duplicating a tab copies the sessionStorage context to the new tab, but the two tabs remain isolated; updates in one tab do not affect the other.
37. **What happens when you exceed the storage limit of `localStorage`?**
    * *Answer*: The browser throws a `QuotaExceededError` exception, blocking further writes until space is cleared.
38. **What does `response.clone()` do, and why is it necessary?**
    * *Answer*: It duplicates the response object. It is necessary because the response body is a stream that can only be read once; cloning it allows reading it multiple times.
39. **What is the difference between a GET request and a POST request?**
    * *Answer*: GET requests retrieve data from a resource. POST requests send data to a server to create or update a resource.
40. **How does JavaScript read HTTP headers from a Fetch response?**
    * *Answer*: By calling getter methods on `response.headers` (e.g. `response.headers.get('content-type')`).

### 🔴 Advanced Questions (41-50)

41. **Explain the binary parsing and stream buffering operations inside V8 when executing `response.json()`.**
    * *Answer*: When `response.json()` is called, V8 accesses the stream reader. As binary packets arrive, V8 buffers them in memory. Once the stream ends, the engine parses the UTF-8 bytes and compiles them into a JavaScript object in the heap.
42. **Why is `localStorage` synchronous design a blocking bottleneck for the browser's Event Loop, and how does IndexedDB resolve this?**
    * *Answer*: `localStorage` blocks the main thread during disk read/write operations. IndexedDB runs operations asynchronously, using callbacks or promises to avoid blocking the main thread.
43. **Explain the security threat of Cross-Site Scripting (XSS) on LocalStorage tokens, and how HTTP-Only cookies mitigate this hazard.**
    * *Answer*: XSS allows attackers to execute scripts on your page. If tokens are in LocalStorage, they can call `localStorage.getItem()` and send the token to their server. HTTP-Only cookies block script access, preventing theft via XSS.
44. **How does the browser handle CORS credentials matching when a wildcard origin `Access-Control-Allow-Origin: *` is set?**
    * *Answer*: If credentials are enabled (`credentials: 'include'`), the browser rejects wildcard origins. The server must return the exact origin domain string in the `Access-Control-Allow-Origin` header instead.
45. **What is the purpose of the `keepalive` flag in the Fetch API, and when is it useful?**
    * *Answer*: It allows the request to continue in the background even if the page closes, which is useful for sending analytics or diagnostics when a user navigates away.
46. **Explain how to read and process partial file chunks using the Fetch API's `ReadableStream` reader.**
    * *Answer*: Call `response.body.getReader()`. Use a loop to call `reader.read()`, which returns `{ done, value }` chunks as typed Uint8Arrays, and decode them using `TextDecoder`.
47. **How does the browser manage cookie storage internally to match path and domain scopes?**
    * *Answer*: The browser stores cookies in an internal SQLite database. When making a request, the browser queries the database, filters cookies that match the request domain and path, and appends them to the Cookie header.
48. **Explain the difference between `navigator.sendBeacon()` and Fetch with `keepalive: true` for page unload telemetry.**
    * *Answer*: `sendBeacon` sends asynchronous POST requests of small payloads quickly. Fetch with `keepalive` is more flexible: it supports custom headers, response reading, and all HTTP methods.
49. **Why does CORS require an OPTIONS preflight request for custom Content-Types, and how does the server declare authorization?**
    * *Answer*: To prevent unauthorized operations on servers. The server approves the request by returning CORS headers (e.g. `Access-Control-Allow-Methods`) in the OPTIONS response.
50. **How does the V8 engine optimize JSON serialization for large objects?**
    * *Answer*: V8 uses optimized C++ parsers. If the object layout matches a known hidden class, V8 stringifies properties directly, bypassing dynamic checks to speed up serialization.

---

## ⏭️ Next Steps

* **Previous Chapter**: [👈 Topic 08: Error Handling & Strict Mode](08_error_handling_strict_mode.md)
* **Next Chapter**: [👉 Topic 10: Event Loop & Memory Management](10_memory_event_loop_advanced.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
