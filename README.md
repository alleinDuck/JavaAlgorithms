# Java Algorithm Problem Solutions

This repository contains Java implementations for solutions to several common algorithm problems. Each solution includes detailed line-by-line comments, an explanation of the approach, and comprehensive JUnit tests.

## Algorithms Included:

1.  **Merge K Sorted Lists**
    *   **Problem:** Merge k sorted linked lists into one sorted linked list.
    *   **Solution:** [`src/main/java/MergeKSortedLists.java`](./src/main/java/MergeKSortedLists.java)
    *   **Tests:** [`src/test/java/MergeKSortedListsTest.java`](./src/test/java/MergeKSortedListsTest.java)
    *   **Approach:** Uses a min-priority queue (MinHeap) to efficiently manage the heads of the k lists.
    *   **Time Complexity:** O(N log k), where N is the total number of nodes and k is the number of lists.
    *   **Space Complexity:** O(k) for the priority queue.

2.  **Longest Valid Parentheses**
    *   **Problem:** Find the length of the longest valid (well-formed) parentheses substring.
    *   **Solution:** [`src/main/java/LongestValidParentheses.java`](./src/main/java/LongestValidParentheses.java)
    *   **Tests:** [`src/test/java/LongestValidParenthesesTest.java`](./src/test/java/LongestValidParenthesesTest.java)
    *   **Approach:** Dynamic Programming. `dp[i]` stores the length of the longest valid parentheses substring ending at index `i`.
    *   **Time Complexity:** O(N), where N is the length of the string.
    *   **Space Complexity:** O(N) for the DP array.

3.  **Trapping Rain Water**
    *   **Problem:** Given n non-negative integers representing an elevation map, compute how much water it can trap after raining.
    *   **Solution:** [`src/main/java/TrappingRainWater.java`](./src/main/java/TrappingRainWater.java)
    *   **Tests:** [`src/test/java/TrappingRainWaterTest.java`](./src/test/java/TrappingRainWaterTest.java)
    *   **Approach:** Two Pointers. Efficiently calculates trapped water by maintaining left and right maximum heights.
    *   **Time Complexity:** O(N), where N is the number of bars.
    *   **Space Complexity:** O(1).

4.  **N-Queens**
    *   **Problem:** Place N chess queens on an N×N chessboard such that no two queens threaten each other.
    *   **Solution:** [`src/main/java/NQueens.java`](./src/main/java/NQueens.java)
    *   **Tests:** [`src/test/java/NQueensTest.java`](./src/test/java/NQueensTest.java)
    *   **Approach:** Backtracking. Explores all possible placements of queens recursively, pruning invalid branches.
    *   **Time Complexity:** O(N!), as it explores permutations.
    *   **Space Complexity:** O(N^2) for the board and recursion stack.

5.  **Minimum Window Substring**
    *   **Problem:** Find the minimum (shortest) substring of `s` which contains all the characters of `t`.
    *   **Solution:** [`src/main/java/MinimumWindowSubstring.java`](./src/main/java/MinimumWindowSubstring.java)
    *   **Tests:** [`src/test/java/MinimumWindowSubstringTest.java`](./src/test/java/MinimumWindowSubstringTest.java)
    *   **Approach:** Sliding Window with Frequency Maps. Efficiently finds the substring by expanding and contracting a window.
    *   **Time Complexity:** O(|S| + |T|), where |S| and |T| are the lengths of the strings.
    *   **Space Complexity:** O(K), where K is the size of the character set.

## How to Run Tests

This project uses Maven. To compile the code and run all JUnit tests, navigate to the project root directory and execute:

```bash
mvn clean test
```
