# ☕ Java Practical Coding Interview Guide

Welcome, interview candidate! This guide compiles the **Top 50 most frequently asked Java coding questions** in technical interviews. Every question is solved using standard Java, accompanied by a line-by-line explanation in simple English and a Big-O complexity analysis.

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
26. [Count Vowels and Consonants in a String](#26-count-vowels-and-consonants-in-a-string)
27. [Check if a String Contains Only Digits](#27-check-if-a-string-contains-only-digits)
28. [Find all Subsets of a Set (Power Set)](#28-find-all-subsets-of-a-set-power-set)
29. [Find the First Repeating Element in an Array](#29-find-the-first-repeating-element-in-an-array)
30. [Find the Intersection of Two Arrays](#30-find-the-intersection-of-two-arrays)
31. [Move All Zeros to the End of an Array](#31-move-all-zeros-to-the-end-of-an-array)
32. [Rotate an Array to the Right by K Steps](#32-rotate-an-array-to-the-right-by-k-steps)
33. [Find the Majority Element in an Array (Boyer-Moore)](#33-find-the-majority-element-in-an-array-boyer-moore)
34. [Container with Most Water](#34-container-with-most-water)
35. [Valid Parentheses (Bracket Matching)](#35-valid-parentheses-bracket-matching)
36. [Implement a Queue using Two Stacks](#36-implement-a-queue-using-two-stacks)
37. [Merge Two Sorted Linked Lists](#37-merge-two-sorted-linked-lists)
38. [Remove N-th Node from End of a Linked List](#38-remove-n-th-node-from-end-of-a-linked-list)
39. [Find the Starting Node of a Cycle in a Linked List](#39-find-the-starting-node-of-a-cycle-in-a-linked-list)
40. [Check if a Binary Tree is Balanced](#40-check-if-a-binary-tree-is-balanced)
41. [Binary Tree Inorder Traversal (Iterative & Recursive)](#41-binary-tree-inorder-traversal-iterative-recursive)
42. [Maximum Depth of a Binary Tree](#42-maximum-depth-of-a-binary-tree)
43. [Lowest Common Ancestor (LCA) in a BST](#43-lowest-common-ancestor-lca-in-a-bst)
44. [Dynamic Programming: Climbing Stairs](#44-dynamic-programming-climbing-stairs)
45. [Implement a Custom Thread Pool Executor](#45-implement-a-custom-thread-pool-executor)
46. [Java 8: Find the First Element of a Stream](#46-java-8-find-the-first-element-of-a-stream)
47. [Java 8: Count Empty Strings in a List](#47-java-8-count-empty-strings-in-a-list)
48. [Java 8: Convert Strings to Uppercase and Join with Commas](#48-java-8-convert-strings-to-uppercase-and-join-with-commas)
49. [Java 8: Find all Numbers Starting with '1'](#49-java-8-find-all-numbers-starting-with-1)
50. [Java 8: Find the Second Highest Number in a List](#50-java-8-find-the-second-highest-number-in-a-list)

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

### 26. Count Vowels and Consonants in a String
**Problem**: Count the number of vowels and consonants in a given string.
```java
public class CountVowelsConsonants {
    public static void count(String str) {
        if (str == null) return;
        int vowels = 0, consonants = 0;
        String temp = str.toLowerCase();
        
        for (int i = 0; i < temp.length(); i++) {
            char ch = temp.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }
        System.out.println("Vowels: " + vowels + ", Consonants: " + consonants);
    }
}
```
* **Explanation**: We normalize the string to lowercase. We loop through all characters, verifying they fall inside standard alphabetical bounds (`a` to `z`). If yes, we check if the character matches `a, e, i, o, u` to count vowels; otherwise, we count it as a consonant.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 27. Check if a String Contains Only Digits
**Problem**: Verify if a given string contains only numeric digits.
```java
public class OnlyDigits {
    public static boolean check(String str) {
        if (str == null || str.isEmpty()) return false;
        
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false; // Found a non-digit character!
            }
        }
        return true;
    }
}
```
* **Explanation**: We loop through each character in the string. We check if the character is a digit using Java's built-in `Character.isDigit()`. If any character fails this check, we return false immediately.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 28. Find all Subsets of a Set (Power Set)
**Problem**: Generate all possible subsets (the Power Set) of a given set of integers.
```java
import java.util.ArrayList;
import java.util.List;

public class PowerSet {
    public static List<List<Integer>> getSubsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> temp, int[] nums, int start) {
        result.add(new ArrayList<>(temp)); // Add current subset copy
        
        for (int i = start; i < nums.length; i++) {
            temp.add(nums[i]);                     // Choose element
            backtrack(result, temp, nums, i + 1);  // Recurse forward
            temp.remove(temp.size() - 1);          // Undo choice (backtrack)
        }
    }
}
```
* **Explanation**: We use Backtracking. We recursively construct subsets. In each step, we add the current selection list to the result, then loop from `start` to select the next element, call the function forward, and remove the last element to backtrack.
* **Complexity**: Time: $O(2^N)$ (since a set of size $N$ has $2^N$ subsets), Space: $O(N)$ (recursion stack space).

---

### 29. Find the First Repeating Element in an Array
**Problem**: Find the first element in an array that repeats, returning its value.
```java
import java.util.HashSet;
import java.util.Set;

public class FirstRepeating {
    public static int find(int[] nums) {
        int minIndex = -1;
        Set<Integer> visited = new HashSet<>();
        
        // Loop backward to find the earliest repeating occurrence!
        for (int i = nums.length - 1; i >= 0; i--) {
            if (visited.contains(nums[i])) {
                minIndex = i; // Save index
            } else {
                visited.add(nums[i]);
            }
        }
        return minIndex != -1 ? nums[minIndex] : -1;
    }
}
```
* **Explanation**: We loop through the array backward. We keep track of visited numbers in a `HashSet`. If we scan a number that is already in the set, we update `minIndex` with the current index. Scanning backward ensures that the earliest repeating element in the array is saved last.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 30. Find the Intersection of Two Arrays
**Problem**: Find common elements present in two arrays, returning only unique values.
```java
import java.util.HashSet;
import java.util.Set;

public class Intersection {
    public static int[] getIntersection(int[] a, int[] b) {
        Set<Integer> setA = new HashSet<>();
        for (int num : a) setA.add(num);
        
        Set<Integer> intersection = new HashSet<>();
        for (int num : b) {
            if (setA.contains(num)) {
                intersection.add(num); // Found common key
            }
        }
        
        // Convert Set back to array
        int[] result = new int[intersection.size()];
        int idx = 0;
        for (int num : intersection) result[idx++] = num;
        return result;
    }
}
```
* **Explanation**: We load all elements of the first array `a` into a `HashSet` named `setA`. We loop through the second array `b`, checking if the elements are present in `setA`. If yes, we write them into a second set (`intersection`) to prevent duplicate listings.
* **Complexity**: Time: $O(N + M)$, Space: $O(N + K)$ (where $K$ is the size of the intersection).

---

### 31. Move All Zeros to the End of an Array
**Problem**: Move all zeros present in an integer array to the end in-place, preserving non-zero order.
```java
public class MoveZeros {
    public static void move(int[] nums) {
        if (nums == null) return;
        int insertPos = 0;
        
        // 1. Move all non-zero elements forward
        for (int num : nums) {
            if (num != 0) {
                nums[insertPos++] = num;
            }
        }
        
        // 2. Fill the remaining array slots with zeros
        while (insertPos < nums.length) {
            nums[insertPos++] = 0;
        }
    }
}
```
* **Explanation**: We use a pointer `insertPos` initialized to 0. We iterate through the array. Whenever we find a non-zero number, we write it to index `insertPos` and increment `insertPos`. Finally, we fill all index slots from `insertPos` to the end of the array with 0.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 32. Rotate an Array to the Right by K Steps
**Problem**: Rotate an array to the right by $K$ steps in-place.
```java
public class RotateArray {
    public static void rotate(int[] nums, int k) {
        if (nums == null || nums.length == 0) return;
        k = k % nums.length; // Handle rotation values larger than array size
        
        // 1. Reverse the entire array
        reverse(nums, 0, nums.length - 1);
        // 2. Reverse the first k elements
        reverse(nums, 0, k - 1);
        // 3. Reverse the remaining elements
        reverse(nums, k, nums.length - 1);
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
```
* **Explanation**: An elegant three-step reversal algorithm. E.g. rotating `[1,2,3,4,5]` by `k=2` steps: (1) Reverse all -> `[5,4,3,2,1]`. (2) Reverse first `k` -> `[4,5,3,2,1]`. (3) Reverse rest -> `[4,5,1,2,3]`.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 33. Find the Majority Element in an Array (Boyer-Moore)
**Problem**: Find the element that appears more than $N/2$ times in an array (Boyer-Moore Voting).
```java
public class MajorityElement {
    public static int getMajority(int[] nums) {
        int count = 0;
        Integer candidate = null;
        
        for (int num : nums) {
            if (count == 0) {
                candidate = num; // Select new candidate
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
}
```
* **Explanation**: The Boyer-Moore Voting Algorithm. We maintain a `candidate` and a `count`. For every number, if `count` is 0, we set `candidate = num`. If the current number matches `candidate`, we increment `count`; otherwise, we decrement `count`. The majority element will always survive.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 34. Container with Most Water
**Problem**: Given $N$ non-negative integers representing line heights, find two lines that form a container holding the maximum water volume.
```java
public class ContainerWithMostWater {
    public static int maxArea(int[] height) {
        int max = 0;
        int left = 0;
        int right = height.length - 1;
        
        while (left < right) {
            // Volume = width * height limit
            int currentHeight = Math.min(height[left], height[right]);
            int currentWidth = right - left;
            max = Math.max(max, currentHeight * currentWidth);
            
            // Move pointer pointing to the shorter line
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }
}
```
* **Explanation**: We place pointers at boundaries `left` and `right`. We calculate water volume: `width * min(height[left], height[right])`. To find a larger volume, we must shift the pointer that points to the shorter line inward.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 35. Valid Parentheses (Bracket Matching)
**Problem**: Determine if a string containing characters `(`, `)`, `{`, `}`, `[` and `]` is valid.
```java
import java.util.Stack;

public class ValidParentheses {
    public static boolean isValid(String s) {
        if (s == null) return false;
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c); // Save open brackets
            } else {
                if (stack.isEmpty()) return false; // Closing bracket without open
                
                char open = stack.pop();
                if (c == ')' && open != '(') return false;
                if (c == '}' && open != '{') return false;
                if (c == ']' && open != '[') return false;
            }
        }
        return stack.isEmpty(); // Ensure all brackets are closed
    }
}
```
* **Explanation**: We use a `Stack`. When we scan an opening bracket, we push it onto the stack. When we find a closing bracket, we pop the top element from the stack and verify that it matches. If the stack is empty or the brackets mismatch, we return false.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 36. Implement a Queue using Two Stacks
**Problem**: Design a FIFO (First-In-First-Out) queue class using only two standard LIFO stacks.
```java
import java.util.Stack;

public class QueueUsingStacks {
    private Stack<Integer> s1 = new Stack<>(); // Input stack
    private Stack<Integer> s2 = new Stack<>(); // Output stack

    public void enqueue(int x) {
        s1.push(x);
    }

    public int dequeue() {
        if (s2.isEmpty()) {
            // Shift all elements from s1 to s2 to reverse LIFO order into FIFO!
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
        if (s2.isEmpty()) {
            throw new RuntimeException("Queue Empty!");
        }
        return s2.pop();
    }
}
```
* **Explanation**: When adding elements, we push to `s1`. When removing, if `s2` is empty, we pop all elements from `s1` and push them into `s2`. This reverses their order, making the oldest element land at the top of `s2`.
* **Complexity**: Enqueue: $O(1)$. Dequeue: Amortized $O(1)$ (elements are shifted at most once). Space: $O(N)$.

---

### 37. Merge Two Sorted Linked Lists
**Problem**: Combine two pre-sorted linked lists into one sorted linked list.
```java
public class MergeLinkedLists {
    public static ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0); // Scratchpad head pointer
        ListNode current = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        // Link remaining nodes
        if (l1 != null) current.next = l1;
        if (l2 != null) current.next = l2;
        
        return dummy.next;
    }
}
```
* **Explanation**: We initialize a `dummy` node to act as the head of the new list. We traverse both lists using pointers. We link the smaller value node to `current.next`, shifting pointers forward, and append leftover nodes at the end.
* **Complexity**: Time: $O(N + M)$, Space: $O(1)$ (reuse existing nodes).

---

### 38. Remove N-th Node from End of a Linked List
**Problem**: Remove the N-th node counting backward from the end of a linked list.
```java
public class RemoveNthFromEnd {
    public static ListNode remove(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;
        
        // 1. Move fast pointer N steps ahead
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        // 2. Move both pointers together until fast reaches the end
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // 3. Remove the node
        slow.next = slow.next.next;
        
        return dummy.next;
    }
}
```
* **Explanation**: We move pointer `fast` exactly `N + 1` nodes ahead. We then advance both `slow` and `fast` pointers at the same speed. When `fast` reaches `null`, the `slow` pointer will be positioned exactly *before* the node to be deleted.
* **Complexity**: Time: $O(N)$ (single pass), Space: $O(1)$.

---

### 39. Find the Starting Node of a Cycle in a Linked List
**Problem**: If a linked list contains a loop cycle, find the node where the cycle begins.
```java
public class CycleStart {
    public static ListNode findStart(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        if (!hasCycle) return null;
        
        // Reset slow to head and move both pointers one step at a time
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow; // Cycle start node
    }
}
```
* **Explanation**: Floyd's Cycle Detection. Once the pointers collide, we reset `slow` to the head of the list. We then advance both `slow` and `fast` pointers one step at a time. The node where they collide again is mathematically guaranteed to be the cycle start.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 40. Check if a Binary Tree is Balanced
**Problem**: Verify if a binary tree's height difference between left and right subtrees is at most 1 for all nodes.
```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
}

public class BalancedTree {
    public static boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }

    private static int getHeight(TreeNode node) {
        if (node == null) return 0;
        
        int leftHeight = getHeight(node.left);
        if (leftHeight == -1) return -1; // Left subtree is unbalanced
        
        int rightHeight = getHeight(node.right);
        if (rightHeight == -1) return -1; // Right subtree is unbalanced
        
        // Height difference check
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // Unbalanced
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
```
* **Explanation**: We calculate height recursively. If a subtree is unbalanced, we return `-1` to propagate the failure upward immediately. At each node, we check if the difference in heights between `leftHeight` and `rightHeight` exceeds 1.
* **Complexity**: Time: $O(N)$ (visits every node once), Space: $O(H)$ (where $H$ is the tree height, stack space).

---

### 41. Binary Tree Inorder Traversal (Iterative & Recursive)
**Problem**: Return the inorder traversal (Left, Root, Right) values of a binary tree.
```java
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeInorder {
    // 1. Recursive
    public static List<Integer> inorderRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        helper(root, result);
        return result;
    }
    private static void helper(TreeNode node, List<Integer> res) {
        if (node == null) return;
        helper(node.left, res);
        res.add(node.val);
        helper(node.right, res);
    }

    // 2. Iterative (Using Stack)
    public static List<Integer> inorderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Walk to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.add(current.val); // Visit node
            current = current.right;  // Switch to right subtree
        }
        return result;
    }
}
```
* **Explanation**: The recursive method traverses by calling left, printing, then calling right. The iterative method uses a `Stack` to record visited nodes as it walks down to the leftmost leaf, pops, records the value, and switches to the right node.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 42. Maximum Depth of a Binary Tree
**Problem**: Find the maximum number of nodes along the longest path from the root node down to the farthest leaf node.
```java
public class MaxDepthTree {
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
```
* **Explanation**: A simple post-order recursion. The maximum depth of a node is $1 +$ the maximum of the depths of its left and right child subtrees.
* **Complexity**: Time: $O(N)$, Space: $O(H)$ (recursive call stack).

---

### 43. Lowest Common Ancestor (LCA) in a BST
**Problem**: Find the lowest shared ancestor node of two given nodes in a Binary Search Tree (BST).
```java
public class LowestCommonAncestor {
    public static TreeNode findLCA(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        
        // 1. Both nodes are on the right side
        if (p.val > root.val && q.val > root.val) {
            return findLCA(root.right, p, q);
        }
        // 2. Both nodes are on the left side
        if (p.val < root.val && q.val < root.val) {
            return findLCA(root.left, p, q);
        }
        // 3. Split point found (one node left, one right, or one is root)
        return root;
    }
}
```
* **Explanation**: In a BST, keys are sorted. If both target nodes `p` and `q` are larger than the current node, their LCA must reside in the right subtree. If both are smaller, it is in the left. If they lie on opposite sides, the current node is the LCA.
* **Complexity**: Time: $O(H)$ (travels down a single path), Space: $O(H)$ stack space.

---

### 44. Dynamic Programming: Climbing Stairs
**Problem**: You are climbing a staircase of $N$ steps. You can climb 1 or 2 steps each time. How many distinct ways can you reach the top?
```java
public class ClimbingStairs {
    public static int countWays(int n) {
        if (n <= 2) return n;
        
        // This is mathematically equivalent to the Fibonacci sequence!
        int prev2 = 1; // Ways to reach step 1
        int prev1 = 2; // Ways to reach step 2
        int current = 0;
        
        for (int i = 3; i <= n; i++) {
            current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        return current;
    }
}
```
* **Explanation**: To reach step `i`, you must make a 1-step leap from step `i-1` or a 2-step leap from step `i-2`. Therefore, the total ways to reach `i` is the sum of the ways to reach `i-1` and `i-2`, matching the Fibonacci sequence.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 45. Implement a Custom Thread Pool Executor
**Problem**: Design a simple working thread pool executor class using Java threads.
```java
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] workerThreads;

    public CustomThreadPool(int threadCount) {
        taskQueue = new LinkedBlockingQueue<>();
        workerThreads = new Thread[threadCount];
        
        for (int i = 0; i < threadCount; i++) {
            workerThreads[i] = new Worker("ThreadPool-Worker-" + i);
            workerThreads[i].start();
        }
    }

    public void execute(Runnable task) {
        taskQueue.add(task); // Thread-safe queue add
    }

    private class Worker extends Thread {
        public Worker(String name) { super(name); }
        public void run() {
            while (true) {
                try {
                    // take() blocks worker thread if no tasks are available!
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
```
* **Explanation**: We initialize a thread-safe `BlockingQueue` and spin up $N$ worker threads. Each thread runs a continuous loop. Inside the loop, it calls `taskQueue.take()`. If the queue is empty, the thread goes to sleep automatically (blocks) until a new task is submitted via `execute()`.
* **Complexity**: Time: $O(1)$ task queue scheduling, Space: $O(M)$ task capacity.

---

### 46. Java 8: Find the First Element of a Stream
**Problem**: Find the first element of an integer stream.
```java
import java.util.List;
import java.util.Optional;

public class StreamFirstElement {
    public static Optional<Integer> getFirst(List<Integer> numbers) {
        return numbers.stream()
                      .findFirst();
    }
}
```
* **Explanation**: We call `.findFirst()`, which returns an `Optional` containing the first element of the stream (if present), preventing null exceptions on empty streams.
* **Complexity**: Time: $O(1)$, Space: $O(1)$.

---

### 47. Java 8: Count Empty Strings in a List
**Problem**: Count how many empty strings are present in a list of text names.
```java
import java.util.List;

public class StreamCountEmpty {
    public static long countEmpty(List<String> strings) {
        return strings.stream()
                      .filter(String::isEmpty)
                      .count(); // Terminal count operation
    }
}
```
* **Explanation**: We filter the stream using the method reference `String::isEmpty` and call the terminal operation `.count()` which returns the size of the filtered stream as a `long`.
* **Complexity**: Time: $O(N)$, Space: $O(1)$.

---

### 48. Java 8: Convert Strings to Uppercase and Join with Commas
**Problem**: Given a list of strings, convert them all to uppercase and join them into a single comma-separated string.
```java
import java.util.List;
import java.util.stream.Collectors;

public class StreamJoinUpper {
    public static String convertAndJoin(List<String> input) {
        return input.stream()
                    .map(String::toUpperCase) // Transform to upper
                    .collect(Collectors.joining(", ")); // Join elements
    }
}
```
* **Explanation**: We map each string to uppercase using `.map(String::toUpperCase)` and use `Collectors.joining(", ")` to concatenate them together with a separator string.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 49. Java 8: Find all Numbers Starting with '1' from a list of integers
**Problem**: Filter a list of integers returning only those that start with the digit 1.
```java
import java.util.List;
import java.util.stream.Collectors;

public class StreamStartsWithOne {
    public static List<Integer> filter(List<Integer> numbers) {
        return numbers.stream()
                      .map(String::valueOf) // Convert to String
                      .filter(s -> s.startsWith("1")) // Check prefix
                      .map(Integer::parseInt) // Convert back to Integer
                      .collect(Collectors.toList());
    }
}
```
* **Explanation**: We convert the integers to strings, check if they start with `"1"` using `.startsWith("1")`, parse them back to integers, and collect the outputs into a list.
* **Complexity**: Time: $O(N)$, Space: $O(N)$.

---

### 50. Java 8: Find the Second Highest Number in a List
**Problem**: Find the second largest integer in a list of numbers.
```java
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StreamSecondHighest {
    public static Optional<Integer> find(List<Integer> numbers) {
        return numbers.stream()
                      .distinct()                     // Remove duplicates
                      .sorted(Comparator.reverseOrder()) // Sort descending
                      .skip(1)                        // Skip highest value
                      .findFirst();                   // Return next value
    }
}
```
* **Explanation**: We remove duplicate values, sort the stream descending, skip the first record (the maximum), and call `.findFirst()` to retrieve the second largest value safely.
* **Complexity**: Time: $O(N \log N)$ (due to sorting), Space: $O(N)$.

---

## ⏭️ Next Steps

* **SQL Interview Guide**: [👉 SQL Practical Coding Interview Guide](../SQL/coding_interview_questions.md)
* **Roadmap Index**: [🏠 Back to Roadmap](README.md)

