import java.util.HashMap;
import java.util.Map;

/**
 * Problem: Minimum Window Substring
 *
 * Description:
 * Given two strings s and t, find the minimum (shortest) substring of s which
 * contains all the characters of t. If there is no such window in s that covers
 * all characters in t, return an empty string "". If there are multiple such
 * minimum-length windows, any one of them is an acceptable answer.
 *
 * Approach: Sliding Window with Frequency Maps
 * This technique involves maintaining a "window" within string `s` and adjusting
 * its boundaries (`left` and `right` pointers) to find the smallest substring
 * that satisfies the condition (contains all characters of `t`).
 * Frequency maps are used to keep track of character counts in `t` and in the current window.
 *
 * Algorithm Steps:
 * 1. Handle Edge Cases:
 *    - If `t` is empty, or `s` is empty, or `t` is longer than `s`, no valid window
 *      can be formed. Return `""`.
 *
 * 2. Initialize Frequency Map for `t`:
 *    - Create `tFreqMap` to store the frequency of each character in string `t`.
 *
 * 3. Initialize Pointers and Counters:
 *    - `left = 0`: The left boundary of the sliding window.
 *    - `right = 0`: The right boundary of the sliding window.
 *    - `requiredChars`: Set to `t.length()`. This counter tracks how many characters from `t`
 *      are still "needed" to form a valid window. When a character from `s` is included
 *      in the window, if it's a character required by `t` (and its count in the window
 *      does not yet exceed its count in `t`), `requiredChars` is decremented.
 *    - `minLength = Integer.MAX_VALUE`: Stores the length of the smallest valid window found so far.
 *    - `minStart = 0`: Stores the starting index of this minimum window.
 *    - `windowFreqMap`: A frequency map to store character counts within the current
 *      sliding window `s[left...right]`.
 *
 * 4. Expand and Shrink Window:
 *    - Iterate with the `right` pointer from `0` to `s.length() - 1` to expand the window:
 *      a. Get `charRight = s.charAt(right)`. Add it to `windowFreqMap` (increment its count).
 *      b. Check if `charRight` is a character present in `tFreqMap`. If it is, and its
 *         current count in `windowFreqMap` is less than or equal to its required count
 *         in `tFreqMap`, it means this instance of `charRight` helps satisfy the
 *         requirements. Decrement `requiredChars`.
 *
 *      c. Inner Loop: While `requiredChars == 0` (the current window `s[left...right]` is valid):
 *         i.  A valid window is found. Check if its length (`right - left + 1`) is smaller
 *             than `minLength`. If so, update `minLength = right - left + 1` and `minStart = left`.
 *         ii. Try to shrink the window from the left to find a potentially smaller valid window:
 *             - Get `charLeft = s.charAt(left)`.
 *             - Decrement its count in `windowFreqMap`.
 *             - If `charLeft` is in `tFreqMap` and its count in `windowFreqMap` (after decrementing)
 *               falls below its required count in `tFreqMap`, it means this character is now
 *               "needed" again to keep the window valid. Increment `requiredChars`.
 *             - Increment `left` pointer to effectively shrink the window.
 *
 *      d. Increment `right` pointer to continue expanding the window.
 *
 * 5. Return Result:
 *    - After the outer loop finishes, if `minLength` is still `Integer.MAX_VALUE`, it means
 *      no valid window was found. Return `""`.
 *    - Otherwise, a minimum window was found starting at `minStart` with length `minLength`.
 *      Return `s.substring(minStart, minStart + minLength)`.
 *
 * Time Complexity: O(|S| + |T|)
 * - Building `tFreqMap` takes O(|T|) time.
 * - The main loop involves two pointers, `left` and `right`. `right` iterates through `s` once (O(|S|)).
 *   `left` also iterates through `s` at most once. Each character processing step inside the
 *   loops (map lookups/updates) takes, on average, O(1) if the character set is limited (e.g., ASCII).
 *   So, the sliding window part is O(|S|).
 * - Total time complexity is O(|S| + |T|).
 *
 * Space Complexity: O(K)
 * - `tFreqMap` and `windowFreqMap` store character frequencies. In the worst case, they can store
 *   up to K distinct characters, where K is the size of the character set (e.g., 26 for lowercase
 *   English letters, 52 for mixed case, 128 for ASCII, etc.).
 * - So, space complexity is O(K).
 */
public class MinimumWindowSubstring {

    public String minWindow(String s, String t) {
        // Edge cases
        if (s == null || t == null || s.isEmpty() || t.isEmpty() || t.length() > s.length()) {
            return "";
        }

        // Frequency map for characters in t
        Map<Character, Integer> tFreqMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            tFreqMap.put(c, tFreqMap.getOrDefault(c, 0) + 1);
        }

        int left = 0;                       // Left pointer of the window
        int right = 0;                      // Right pointer of the window
        int requiredChars = t.length();     // Number of characters from t still needed in the window

        int minLength = Integer.MAX_VALUE;  // Length of the minimum window found
        int minStart = 0;                   // Starting index of the minimum window

        Map<Character, Integer> windowFreqMap = new HashMap<>(); // Frequency map for characters in the current window

        // Expand the window by moving the right pointer
        while (right < s.length()) {
            char charRight = s.charAt(right);
            // Add current character to the window map
            windowFreqMap.put(charRight, windowFreqMap.getOrDefault(charRight, 0) + 1);

            // If this character is in t and its count in window is not more than in t,
            // it's a character we needed.
            if (tFreqMap.containsKey(charRight) &&
                windowFreqMap.get(charRight) <= tFreqMap.get(charRight)) {
                requiredChars--;
            }

            // When window is valid (all required characters are found)
            while (requiredChars == 0) {
                int currentWindowLength = right - left + 1;
                // Update minimum window if current is smaller
                if (currentWindowLength < minLength) {
                    minLength = currentWindowLength;
                    minStart = left;
                }

                // Try to shrink the window from the left
                char charLeft = s.charAt(left);
                windowFreqMap.put(charLeft, windowFreqMap.get(charLeft) - 1); // Remove charLeft from window

                // If charLeft was a required character and its count in window drops below required count in t
                if (tFreqMap.containsKey(charLeft) &&
                    windowFreqMap.get(charLeft) < tFreqMap.get(charLeft)) {
                    requiredChars++; // We need this character again
                }
                // Move left pointer to shrink window
                left++;
            }
            // Expand window further
            right++;
        }

        // If no valid window was found, minLength will remain Integer.MAX_VALUE
        if (minLength == Integer.MAX_VALUE) {
            return "";
        }
        // Return the minimum window substring
        return s.substring(minStart, minStart + minLength);
    }
}
