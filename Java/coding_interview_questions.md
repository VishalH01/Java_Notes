# ☕ Java Practical Coding Interview Guide

Welcome, interview candidate! This guide compiles the **Top 25 most frequently asked Java coding questions** in technical interviews. Every question is solved using standard Java, accompanied by a line-by-line explanation in simple English and a Big-O complexity analysis.

---

## 🗺️ Index of Questions

1. [Reverse a String](#1-reverse-a-string)
2. [Check Palindrome String](#2-check-palindrome-string)
3. [Find Duplicate Characters in a String](#3-find-duplicate-characters-in-a-string)
4. [Check if Two Strings are Anagrams](#4-check-if-two-strings-are-anagrams)
5. [First Non-Repeated Character in a String](#5-first-non-repeated-character-in-a-string)
6. [Reverse an Array in Place](#6-reverse-an-array-in-place)
7. [Find the Missing Number in an Array](#7-find-the-missing-number-in-an-array)
8. [Find the Duplicate Number in an Array](#8-find-the-duplicate-number-in-an-array)
9. [Two Sum (Find indices matching a target)](#9-two-sum-find-indices-matching-a-target)
10. [Binary Search Implementation](#10-binary-search-implementation)
11. [Merge Two Sorted Arrays](#11-merge-two-sorted-arrays)
12. [FizzBuzz Implementation](#12-fizzbuzz-implementation)
13. [Fibonacci Series (Recursive vs Iterative)](#13-fibonacci-series-recursive-vs-iterative)
14. [Calculate Factorial (Recursive vs Iterative)](#14-calculate-factorial-recursive-vs-iterative)
15. [Check if a Number is Prime](#15-check-if-a-number-is-prime)
16. [Implement a Stack using an Array](#16-implement-a-stack-using-an-array)
17. [Reverse a Singly Linked List](#17-reverse-a-singly-linked-list)
18. [Find Middle Element of a Linked List](#18-find-middle-element-of-a-linked-list)
19. [Detect a Cycle in a Linked List](#19-detect-a-cycle-in-a-linked-list)
20. [Thread-Safe Singleton Design Pattern](#20-thread-safe-singleton-design-pattern)
21. [Java 8: Filter Even Numbers using Streams](#21-java-8-filter-even-numbers-using-streams)
22. [Java 8: Sum all Elements in a List](#22-java-8-sum-all-elements-in-a-list)
23. [Java 8: Group names by length](#23-java-8-group-names-by-length)
24. [Java 8: Find Maximum Value in a List](#24-java-8-find-maximum-value-in-a-list)
25. [Java 8: Find Duplicate Numbers in a List](#25-java-8-find-duplicate-numbers-in-a-list)

---

## 💻 Coding Challenges & Solutions

### 1. Reverse a String
**Problem**: Write a method to reverse a given string.
```java
public class StringReverse {
    public static String reverse(String input) {
        if (input == null) return null;
        
        StringBuilder reversed = new StringBuilder();
        // Loop backward from the last character to the first
        for (int i = input.length() - 1; i >= 0; i--) {
            reversed.append(input.charAt(i));
        }
        return reversed.toString();
    }
}
```
* **Explanation**: We initialize a `StringBuilder` scratchpad. We loop through the input string backward starting from `length() - 1` to `0`, grabbing characters using `charAt(i)` and appending them to our scratchpad.
* **Complexity**: Time: $O(N)$ (one loop pass), Space: $O(N)$ (to store the reversed string).

---

### 2. Check Palindrome String
**Problem**: Verify if a string reads the same backward as forward (e.g. `"radar"`).
```java
public class PalindromeCheck {
    public static boolean isPalindrome(String input) {
        if (input == null) return false;
        
        int left = 0;
        int right = input.length() - 1;
        
        // Two-pointer collision check
        while (left < right) {
            if (input.charAt(left) != input.charAt(right)) {
                return false; // Characters mismatch
            }
            left++;
            right--;
        }
        return true;
    }
}
```
* **Explanation**: We place two pointers: one at the start (`left`) and one at the end (`right`). We compare characters. If they match, we move pointers closer to the center. If they mismatch, we stop and return false immediately.
* **Complexity**: Time: $O(N)$ (scans half the string), Space: $O(1)$ (no extra memory).

---

### 3. Find Duplicate Characters in a String
**Problem**: Identify and print characters that appear more than once in a string.
```java
import java.util.HashMap;
import java.util.Map;

public class DuplicateChars {
    public static void findDuplicates(String input) {
        if (input == null) return;
        
        Map<Character, Integer> charMap = new HashMap<>();
        // Count character occurrences
        for (char c : input.toCharArray()) {
            charMap.put(c, charMap.getOrDefault(c, 0) + 1);
        }
        
        // Print entries with count > 1
        for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println(entry.getKey() + " appears " + entry.getValue() + " times.");
            }
        }
    }
}
```
* **Explanation**: We build a frequency map. We convert the string to a character array, loop through it, and increment counts inside a `HashMap`. Finally, we loop through the map entries and print those with counts greater than 1.
* **Complexity**: Time: $O(N)$, Space: $O(K)$ (where $K$ is the count of unique characters in the alphabet).

---

### 4. Check if Two Strings are Anagrams
**Problem**: Check if two strings contain the same characters in different orders (e.g. `"silent"`, `"listen"`).
```java
import java.util.Arrays;

public class AnagramCheck {
    public static boolean isAnagram(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        
        char[] array1 = s1.toCharArray();
        char[] array2 = s2.toCharArray();
        
        // Sort both character arrays
        Arrays.sort(array1);
        Arrays.sort(array2);
        
        // Compare sorted arrays
        return Arrays.equals(array1, array2);
    }
}
```
* **Explanation**: First, we check if lengths differ; if yes, they can't be anagrams. We convert both strings to character arrays, sort them alphabetically using `Arrays.sort()`, and check if the sorted arrays are identical.
* **Complexity**: Time: $O(N \log N)$ (due to sorting), Space: $O(N)$ (to store character arrays).

---

### 5. First Non-Repeated Character in a String
**Problem**: Find the first character in a string that does not repeat.
```java
import java.util.LinkedHashMap;
import java.util.Map;

public class FirstUniqueChar {
    public static Character getFirstUnique(String input) {
        if (input == null || input.isEmpty()) return null;
        
        // LinkedHashMap preserves insertion order!
        Map<Character, Integer> countMap = new LinkedHashMap<>();
        
        for (char c : input.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey(); // Return first unique
            }
        }
        return null;
    }
}
```
* **Explanation**: We use a `LinkedHashMap` because it retains the order in which characters were read. We count character frequencies, then loop through the ordered map keys, returning the first one with a count of 1.
* **Complexity**: Time: $O(N)$, Space: $O(K)$.

---

### 6. Reverse an Array in Place
**Problem**: Reverse an array of integers without allocating extra array memory.
```java
public class ReverseArray {
    public static void reverse(int[] nums) {
        if (nums == null) return;
        
        int start = 0;
        int end = nums.length - 1;
        
        while (start < end) {
            // Swap values using a temp variable
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            
            start++;
            end--;
        }
    }
}
```
* **Explanation**: We place a `start` index pointer at the beginning and `end` index at the last index. In a loop, we swap their values, increment `start`, and decrement `end` until they meet in the center.
* **Complexity**: Time: $O(N)$, Space: $O(1)$ (in-place swap).

---

### 7. Find the Missing Number in an Array
**Problem**: Given an array containing $N-1$ unique numbers in range $1$ to $N$, find the missing one.
```java
public class MissingNumber {
    public static int findMissing(int[] nums, int n) {
        // 1. Calculate sum of numbers from 1 to N mathematically: N * (N + 1) / 2
        int expectedSum = n * (n + 1) / 2;
        
        // 2. Sum the actual numbers present in the array
        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        
        // 3. The difference is the missing number!
        return expectedSum - actualSum;
    }
}
```
* **Explanation**: We calculate what the sum of numbers from 1 to $N$ should be using Gauss's formula: $S = N \times (N+1)/2$. We calculate the sum of the array's elements. Subtracting the actual sum from the expected sum yields the missing number.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 8. Find the Duplicate Number in an Array
**Problem**: Identify the duplicate number in an array of $N+1$ integers where numbers are in range $1$ to $N$.
```java
import java.util.HashSet;
import java.util.Set;

public class FindDuplicate {
    public static int getDuplicate(int[] nums) {
        Set<Integer> visited = new HashSet<>();
        for (int num : nums) {
            // checkIf already visited
            if (visited.contains(num)) {
                return num; // Found duplicate!
            }
            visited.add(num);
        }
        return -1;
    }
}
```
* **Explanation**: We loop through the array and store each element in a `HashSet`. Set lookups take $O(1)$ time. If we encounter a number that is already present in the set, we return it immediately as the duplicate.
* **Complexity**: Time: $O(N)$, Space: $O(N)$ (for the Hash Set).

---

### 9. Two Sum (Find indices matching a target)
**Problem**: Given an array of integers and a target sum, return indices of the two numbers that add up to target.
```java
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static int[] findTwoSum(int[] nums, int target) {
        // Map stores: Key = Number needed, Value = Index of current number
        Map<Integer, Integer> complementMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (complementMap.containsKey(complement)) {
                return new int[] { complementMap.get(complement), i };
            }
            complementMap.put(nums[i], i);
        }
        return new int[] {}; // No pair found
    }
}
```
* **Explanation**: For every number `x`, we calculate its complement (`target - x`). We check a `HashMap` to see if we have already scanned that complement. If yes, we return their indices. If not, we store `x` and its index in the map.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 10. Binary Search Implementation
**Problem**: Search a sorted array for a target key, returning its index.
```java
public class BinarySearch {
    public static int search(int[] sortedNums, int target) {
        int low = 0;
        int high = sortedNums.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // Prevents integer overflow
            
            if (sortedNums[mid] == target) {
                return mid; // Key found!
            } else if (sortedNums[mid] < target) {
                low = mid + 1; // Search right half
            } else {
                high = mid - 1; // Search left half
            }
        }
        return -1; // Key missing
    }
}
```
* **Explanation**: We initialize boundaries `low` and `high`. We calculate the midpoint `mid` index. If the mid value matches the key, we return it. If it is smaller, we shift `low` to `mid + 1`. If larger, we shift `high` to `mid - 1`.
* **Complexity**: Time: $O(\log N)$, Space: $O(1)$.

---

### 11. Merge Two Sorted Arrays
**Problem**: Merge two pre-sorted integer arrays into one unified sorted array.
```java
public class MergeSortedArrays {
    public static int[] merge(int[] a, int[] b) {
        int[] merged = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        
        // Step through both arrays comparing values
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                merged[k++] = a[i++];
            } else {
                merged[k++] = b[j++];
            }
        }
        
        // Copy remaining elements of A
        while (i < a.length) {
            merged[k++] = a[i++];
        }
        
        // Copy remaining elements of B
        while (j < b.length) {
            merged[k++] = b[j++];
        }
        
        return merged;
    }
}
```
* **Explanation**: We initialize pointers `i` and `j` for both input arrays. We compare values at pointers and insert the smaller value into the result array `merged`, incrementing the respective index pointers until one array is exhausted. Finally, we append any leftover elements.
* **Complexity**: Time: $O(N + M)$, Space: $O(N + M)$ (to store the merged array).

---

### 12. FizzBuzz Implementation
**Problem**: Print numbers 1 to N, replacing multiples of 3 with "Fizz", multiples of 5 with "Buzz", and multiples of both with "FizzBuzz".
```java
public class FizzBuzz {
    public static void runFizzBuzz(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }
}
```
* **Explanation**: We loop from 1 to N. We check conditions in order of specificity: check divisibility by both 3 and 5 first (`i % 15 == 0`), then check 3, then check 5, and fall back to printing the number itself.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 13. Fibonacci Series (Recursive vs. Iterative)
**Problem**: Calculate the N-th Fibonacci number.
```java
public class Fibonacci {
    // 1. Recursive approach (Simple but slow: O(2^N))
    public static int getFibRecursive(int n) {
        if (n <= 1) return n;
        return getFibRecursive(n - 1) + getFibRecursive(n - 2);
    }

    // 2. Iterative approach (Fast: O(N))
    public static int getFibIterative(int n) {
        if (n <= 1) return n;
        int prev2 = 0, prev1 = 1;
        int current = 1;
        
        for (int i = 2; i <= n; i++) {
            current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        return current;
    }
}
```
* **Explanation**: The recursive method branches outward twice per step, repeating calculations. The iterative method stores only the previous two numbers in variables (`prev1`, `prev2`) and loops once, computing the sum sequentially.
* **Complexity**: Recursive: Time $O(2^N)$, Space $O(N)$ (call stack). Iterative: Time $O(N)$, Space $O(1)$.

---

### 14. Calculate Factorial (Recursive vs. Iterative)
**Problem**: Compute the factorial of a number $N! = N \times (N-1) \times \dots \times 1$.
```java
public class Factorial {
    // 1. Recursive
    public static long getFactorialRecursive(int n) {
        if (n <= 1) return 1;
        return n * getFactorialRecursive(n - 1);
    }

    // 2. Iterative
    public static long getFactorialIterative(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
```
* **Explanation**: The recursive approach computes by calling itself down to base case `n <= 1`. The iterative approach uses a simple running multiplication loop, avoiding method call stack overheads.
* **Complexity**: Time: $O(N)$, Space: Recursive is $O(N)$ (call stack), Iterative is $O(1)$.

---

### 15. Check if a Number is Prime
**Problem**: Determine if a number is divisible only by 1 and itself.
```java
public class PrimeCheck {
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false; // Exclude even numbers
        
        // Loop up to the square root of N (mathematical boundary limit)
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false; // Found divisor
            }
        }
        return true;
    }
}
```
* **Explanation**: Numbers greater than $\sqrt{N}$ cannot have unique integer factors below $\sqrt{N}$. We check divisibility starting from 3 up to `i * i <= n`, skipping even numbers (`i += 2`) to speed up execution.
* **Complexity**: Time: $O(\sqrt{N})$, Space: $O(1)$.

---

### 16. Implement a Stack using an Array
**Problem**: Write a custom LIFO (Last-In-First-Out) stack class using an integer array.
```java
public class CustomStack {
    private int[] arr;
    private int top;
    private int capacity;

    public CustomStack(int size) {
        capacity = size;
        arr = new int[capacity];
        top = -1; // Stack is empty
    }

    public void push(int x) {
        if (top == capacity - 1) {
            throw new StackOverflowError("Stack Full!");
        }
        arr[++top] = x;
    }

    public int pop() {
        if (top == -1) {
            throw new RuntimeException("Stack Empty!");
        }
        return arr[top--];
    }

    public int peek() {
        if (top == -1) {
            throw new RuntimeException("Stack Empty!");
        }
        return arr[top];
    }
}
```
* **Explanation**: We manage a pointer index `top` initialized to `-1`. When we `push(x)`, we increment `top` and write to the array slot. When we `pop()`, we return the value at `top` and decrement `top`.
* **Complexity**: Time: $O(1)$ for all operations, Space: $O(N)$ capacity space.

---

### 17. Reverse a Singly Linked List
**Problem**: Reverse a linked list of nodes in-place.
```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

public class ReverseLinkedList {
    public static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        ListNode nextNode = null;
        
        while (current != null) {
            nextNode = current.next; // Store next node
            current.next = prev;     // Reverse the pointer direction
            prev = current;          // Move prev pointer forward
            current = nextNode;      // Move current pointer forward
        }
        return prev; // New head of reversed list
    }
}
```
* **Explanation**: We iterate through the list. For each node, we temporarily save its original `next` reference. We then point its `next` pointer backward to the `prev` node. Finally, we move our `prev` and `current` pointers one step forward.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 18. Find Middle Element of a Linked List
**Problem**: Find the middle node of a linked list in one pass.
```java
public class MiddleOfLinkedList {
    public static ListNode findMiddle(ListNode head) {
        if (head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Fast pointer moves twice as fast as slow pointer
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow; // Points to middle node
    }
}
```
* **Explanation**: We use the Two-Pointer (tortoise and hare) strategy. The `fast` pointer steps forward by two nodes, while the `slow` pointer steps forward by one. When `fast` reaches the end of the list, `slow` will be exactly at the midpoint.
* **Complexity**: Time: $O(N)$ (single pass), Space: $O(1)$.

---

### 19. Detect a Cycle in a Linked List
**Problem**: Determine if a linked list contains a loop cycle.
```java
public class DetectCycle {
    public static boolean hasCycle(ListNode head) {
        if (head == null) return false;
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return true; // Fast pointer caught up! (Loop detected)
            }
        }
        return false; // Reached end of list without loops
    }
}
```
* **Explanation**: Using Floyd's Cycle Finding Algorithm. We move a `slow` pointer (1 step) and a `fast` pointer (2 steps). If there is a loop cycle, the fast pointer will eventually loop around and collide with the slow pointer. If there is no loop, the fast pointer will reach `null`.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 20. Thread-Safe Singleton Design Pattern
**Problem**: Write a Singleton class in Java that guarantees only one instance is created, safe for multithreaded environments.
```java
public class ThreadSafeSingleton {
    // volatile ensures changes are visible across JVM threads instantly
    private static volatile ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {} // Prevent direct instantiation

    public static ThreadSafeSingleton getInstance() {
        // First check (no lock overhead)
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                // Second check (inside synchronized lock block)
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}
```
* **Explanation**: We use **Double-Checked Locking**. The first check avoids expensive synchronization lock checks once the instance is initialized. The `synchronized` block ensures only one thread can instantiate the class early. `volatile` prevents JVM thread memory caching issues.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 21. Java 8: Filter Even Numbers using Streams
**Problem**: Given a list of integers, return a new list containing only the even numbers.
```java
import java.util.List;
import java.util.stream.Collectors;

public class StreamFilterEvens {
    public static List<Integer> filterEvens(List<Integer> numbers) {
        return numbers.stream()
                      .filter(n -> n % 2 == 0) // Lambda checks divisibility
                      .collect(Collectors.toList());
    }
}
```
* **Explanation**: We convert the list to a `Stream`, apply the `.filter()` intermediate operation with a lambda expression checking even values, and collect the outputs back into a list.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 22. Java 8: Sum all Elements in a List
**Problem**: Calculate the sum of all integers in a list using Streams.
```java
import java.util.List;

public class StreamSum {
    public static int calculateSum(List<Integer> numbers) {
        return numbers.stream()
                      .mapToInt(Integer::intValue) // Convert to IntStream
                      .sum();                     // Aggregate sum
    }
}
```
* **Explanation**: We convert the stream to a primitive `IntStream` using `.mapToInt(Integer::intValue)` to avoid boxing/unboxing overhead, and call the aggregate method `.sum()`.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 23. Java 8: Group names by length
**Problem**: Group a list of names into a Map where key is name length, and value is a list of names.
```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamGrouping {
    public static Map<Integer, List<String>> groupNames(List<String> names) {
        return names.stream()
                    .collect(Collectors.groupingBy(String::length));
    }
}
```
* **Explanation**: We use the collector `.collect(Collectors.groupingBy(String::length))`. This automatically groups elements in the stream using the name length as the key.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 24. Java 8: Find Maximum Value in a List
**Problem**: Find the maximum integer in a list using Streams.
```java
import java.util.List;
import java.util.Optional;

public class StreamMax {
    public static Optional<Integer> getMax(List<Integer> numbers) {
        return numbers.stream()
                      .max(Integer::compareTo); // Compare elements
    }
}
```
* **Explanation**: We call `.max()` passing the standard comparator `Integer::compareTo`. Since the list could be empty, the method returns an `Optional` wrapper to prevent null pointer exceptions.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 25. Java 8: Find Duplicate Numbers in a List
**Problem**: Find all duplicate integers in a list using Streams and a Set.
```java
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamDuplicates {
    public static Set<Integer> getDuplicates(List<Integer> numbers) {
        Set<Integer> visited = new HashSet<>();
        return numbers.stream()
                      // Set.add(n) returns false if the item is already in the set!
                      .filter(n -> !visited.add(n)) 
                      .collect(Collectors.toSet());
    }
}
```
* **Explanation**: We declare a helper `HashSet`. Inside the stream `.filter()`, we execute `!visited.add(n)`. Since `visited.add(n)` returns `false` if `n` already exists, negating it with `!` returns `true` only for duplicate numbers, which are collected into a result set.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

## ⏭️ Next Steps

* **SQL Interview Guide**: [👉 SQL Practical Coding Interview Guide](../SQL/coding_interview_questions.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)
