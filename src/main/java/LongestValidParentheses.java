/**
 * Problem: Longest Valid Parentheses
 *
 * Description:
 * Given a string containing just the characters '(' and ')', find the length of the
 * longest valid (well-formed) parentheses substring.
 *
 * Approach: Dynamic Programming
 * We use a 1D DP array, `dp`, where `dp[i]` stores the length of the longest
 * valid parentheses substring ending at index `i` of the input string `s`.
 *
 * Algorithm Steps (DP):
 * 1. Initialize a `dp` array of the same length as the input string `s` with all zeros.
 *    `dp[i]` will store the length of the longest valid parentheses substring ending at index `i`.
 * 2. Initialize `maxLength = 0` to keep track of the maximum length found so far.
 * 3. Iterate through the string `s` from `i = 1` to `s.length() - 1`. (We start from 1
 *    because a valid substring of length at least 2 requires at least two characters).
 * 4. If `s.charAt(i) == ')'`:
 *    a. Case 1: `s.charAt(i-1) == '('`. This forms a pair `()`.
 *       The length of the valid substring ending at `i` is 2 plus the length of
 *       the valid substring ending at `i-2` (if `i-2` is a valid index).
 *       So, `dp[i] = (i >= 2 ? dp[i-2] : 0) + 2`.
 *    b. Case 2: `s.charAt(i-1) == ')'`. This means we have a structure like `...))`.
 *       If the substring ending at `i-1` is a valid parentheses substring of length `dp[i-1]`,
 *       we then look at the character before this substring, i.e., at index `i - dp[i-1] - 1`.
 *       If this character `s.charAt(i - dp[i-1] - 1)` is an opening parenthesis `'('`, then
 *       it forms a new valid pair with `s.charAt(i)`.
 *       The length of this new valid substring ending at `i` is:
 *       `dp[i-1]` (length of valid substring ending at `i-1`)
 *       `+ 2` (for the newly formed pair `s.charAt(i - dp[i-1] - 1)` and `s.charAt(i)`)
 *       `+ (i - dp[i-1] - 2 >= 0 ? dp[i - dp[i-1] - 2] : 0)` (length of any valid
 *         parentheses substring that might exist before this entire new block).
 * 5. After calculating `dp[i]` for each `i`, update `maxLength = Math.max(maxLength, dp[i])`.
 * 6. Return `maxLength`.
 *
 * Time Complexity: O(N)
 * The algorithm involves a single pass through the string of length N.
 * Each step inside the loop takes constant time.
 *
 * Space Complexity: O(N)
 * We use a DP array of size N to store intermediate results.
 */
public class LongestValidParentheses {

    public int longestValidParentheses(String s) {
        if (s == null || s.length() < 2) { // Basic check: need at least "()"
            return 0;
        }

        int n = s.length();
        int[] dp = new int[n]; // dp[i] = length of longest valid parentheses substring ending at i
        int maxLength = 0;     // Stores the maximum length found

        // Iterate from the second character
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') { // Only consider ')' as an ending character for a valid substring
                if (s.charAt(i - 1) == '(') {
                    // Case 1: Substring ends with "()"
                    // Examples: "()", "...()", "(())" -> "()" part contributes 2
                    // Add 2 to the length of valid parentheses ending at i-2 (if exists)
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else { // s.charAt(i - 1) == ')'
                    // Case 2: Substring ends with "))"
                    // Example: "()(())" -> ending at last ')'
                    // We need to check if there's a matching '(' for the current ')'
                    // The potential matching '(' would be at index i - dp[i-1] - 1
                    // dp[i-1] is the length of the valid substring ending at i-1 (e.g., for "(())", dp[i-1] for "()" is 2)
                    int prevValidLength = dp[i - 1];
                    int potentialOpenParenIndex = i - prevValidLength - 1;

                    if (potentialOpenParenIndex >= 0 && s.charAt(potentialOpenParenIndex) == '(') {
                        // We found a matching '(': s[potentialOpenParenIndex] ... s[i-1] s[i]
                        //                                (       valid_sub       )
                        // Length of this block is prevValidLength + 2
                        dp[i] = prevValidLength + 2;
                        // Now, check if there's another valid substring before this block
                        // Example: "() (())" -> when processing the last ')', potentialOpenParenIndex points to '(',
                        // prevValidLength is for "(())", and we add dp value for "()" before it.
                        // The index for that previous block's end would be potentialOpenParenIndex - 1
                        if (potentialOpenParenIndex - 1 >= 0) {
                            dp[i] += dp[potentialOpenParenIndex - 1];
                        }
                    }
                }
            }
            // Update the overall maximum length found
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }
}
