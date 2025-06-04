import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstringTest {

    private final MinimumWindowSubstring solver = new MinimumWindowSubstring();

    // Helper to check if a substring is a valid window (contains all chars of t)
    // and to handle cases where multiple valid windows of the same minimum length exist.
    private void assertMinWindow(String expected, String actual, String s, String t) {
        if (expected.isEmpty()) {
            assertEquals("", actual, "Expected empty string if no valid window exists.");
            return;
        }
        assertEquals(expected.length(), actual.length(), "Minimum window length should match.");

        Map<Character, Integer> tFreq = new HashMap<>();
        for (char c : t.toCharArray()) {
            tFreq.put(c, tFreq.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> actualWindowFreq = new HashMap<>();
        for (char c : actual.toCharArray()) {
            actualWindowFreq.put(c, actualWindowFreq.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : tFreq.entrySet()) {
            char tChar = entry.getKey();
            int tCount = entry.getValue();
            assertTrue(actualWindowFreq.getOrDefault(tChar, 0) >= tCount,
                    "Actual window must contain all characters of t with required frequencies. Missing/insufficient: " + tChar);
        }
        // Note: The problem allows any of the shortest windows if multiple exist.
        // The primary check is length and validity. Specific string match is only if one option.
        // For cases like s = "ABCAACXYZ", t = "AAC", expected could be "CAAC" or "AAC".
        // The current tests will expect the specific output from the reference algorithm.
        // If the problem guarantees a unique shortest window OR any is fine, this is okay.
        // The implementation should yield a consistent one.
         if (!expected.equals(actual)) {
            // This check is mainly for when specific output is expected like "BANC"
            // If multiple are possible, this might fail if a different valid one is returned.
            // However, for standard test cases, the expected output is often tied to a common algorithm's behavior.
            System.out.println("Note: Expected string was '" + expected + "' but got '" + actual + "'. Both might be valid minimum windows.");
        }

    }


    @Test
    void testMinWindow_emptyS() {
        assertMinWindow("", solver.minWindow("", "A"), "", "A");
    }

    @Test
    void testMinWindow_emptyT() {
        // As per problem: "If t is empty, return an empty string"
        assertMinWindow("", solver.minWindow("ABC", ""), "ABC", "");
    }

    @Test
    void testMinWindow_tLongerThanS() {
        assertMinWindow("", solver.minWindow("A", "AA"), "A", "AA");
    }

    @Test
    void testMinWindow_noSolution() {
        assertMinWindow("", solver.minWindow("ABC", "D"), "ABC", "D");
    }

    @Test
    void testMinWindow_exactMatch() {
        assertMinWindow("ABC", solver.minWindow("ABC", "ABC"), "ABC", "ABC");
    }

    @Test
    void testMinWindow_basicCase1() {
        // s = "ADOBECODEBANC", t = "ABC", expected "BANC"
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String result = solver.minWindow(s, t);
        assertMinWindow("BANC", result, s, t);
    }

    @Test
    void testMinWindow_basicCase2() {
        assertMinWindow("a", solver.minWindow("a", "a"), "a", "a");
    }

    @Test
    void testMinWindow_basicCase3() {
        assertMinWindow("", solver.minWindow("a", "aa"), "a", "aa");
    }

    @Test
    void testMinWindow_tHasDuplicates() {
        // s = "ABCAACXYZ", t = "AAC"
        // Valid windows: "ABCAA", "BCAAC", "CAAC", "AAC"
        // Shortest: "AAC" (length 3)
        String s = "ABCAACXYZ";
        String t = "AAC";
        String result = solver.minWindow(s,t);
        assertMinWindow("AAC", result, s, t);
    }

    @Test
    void testMinWindow_sHasDuplicates() {
        // s = "AAABBC", t = "ABC"
        // Windows: "AAABBC" (contains A,B,C)
        // "AABBC" (contains A,B,C)
        // "ABBC" (contains A,B,C)
        // Shortest is "ABBC"
        String s = "AAABBC";
        String t = "ABC";
        String result = solver.minWindow(s,t);
        assertMinWindow("ABBC", result, s, t);
    }

    @Test
    void testMinWindow_windowAtStart() {
        // s = "ABCCXYZ", t = "ABC"
        // Shortest "ABC"
        String s = "ABCCXYZ";
        String t = "ABC";
        String result = solver.minWindow(s,t);
        assertMinWindow("ABC", result, s, t);
    }

    @Test
    void testMinWindow_windowAtEnd() {
        // s = "XYZABCC", t = "ABC"
        // Shortest "ABC"
        String s = "XYZABCC";
        String t = "ABC";
        String result = solver.minWindow(s,t);
        assertMinWindow("ABC", result, s, t);
    }

    @Test
    void testMinWindow_allSameCharsInS() {
        // s = "AAAAA", t = "AA"
        // Shortest "AA"
        String s = "AAAAA";
        String t = "AA";
        String result = solver.minWindow(s,t);
        assertMinWindow("AA", result, s, t);
    }

    @Test
    void testMinWindow_allSameCharsInTButSDoesNotHaveEnough() { // Renamed for clarity
        // s = "AB", t = "AAA"
        assertMinWindow("", solver.minWindow("AB", "AAA"), "AB", "AAA");
    }

    @Test
    void testMinWindow_anotherBasic() {
        String s = "bbaac";
        String t = "aba";
        // t has two 'a' and one 'b'.
        // s has "bbaa" contains "aba"
        // s has "baac" contains "aba"
        // Shortest should be "baa" from "bbaa" or "aac" from "baac" if 'a' is first.
        // Expected: "baac" (length 4) if order matters.
        // My code's logic:
        // tFreq: {a:2, b:1}, requiredChars=3
        // s="bbaac"
        // R=0, s[0]=b. win{b:1}. tF{b} && win{b}<=tF{b} (1<=1). reqC=2.
        // R=1, s[1]=b. win{b:2}. tF{b} && win{b}<=tF{b} (2<=1) is false.
        // R=2, s[2]=a. win{b:2,a:1}. tF{a} && win{a}<=tF{a} (1<=2). reqC=1.
        // R=3, s[3]=a. win{b:2,a:2}. tF{a} && win{a}<=tF{a} (2<=2). reqC=0.
        //   VALID: s[0..3]="bbaa". len=4. minL=4, minS=0.
        //   Shrink L=0 (b): win{b:1,a:2}. tF{b} && win{b}<tF{b} (1<1) false. No, (1<1) is false. (1<1) is not true.
        //                   Is it win{b}(1) < tF{b}(1)? No.
        //                   Ah, my `requiredChars` logic: if map.get(char) < tmap.get(char) -> required++.
        //                   `windowFreqMap.get(charLeft) < tFreqMap.get(charLeft)`
        //                   For 'b': `windowFreqMap.get('b')` is now 1. `tFreqMap.get('b')` is 1. `1 < 1` is false. So `requiredChars` not incremented.
        //                   This is correct. We removed an excess 'b'.
        //   L=1. Window s[1..3]="baa". Still valid (reqC=0). len=3. minL=3, minS=1.
        //   Shrink L=1 (b): win{b:0,a:2}. tF{b} && win{b}<tF{b} (0<1). reqC=1. Break from while.
        // R=4, s[4]=c. win{b:0,a:2,c:1}. (no change to reqC).
        // Loop ends. minStart=1, minLength=3. s.substring(1, 1+3) = "baa".
        String result = solver.minWindow(s,t);
        assertMinWindow("baa", result, s, t);
    }
}
