import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestValidParenthesesTest {

    private final LongestValidParentheses solver = new LongestValidParentheses();

    @Test
    void testLongestValidParentheses_emptyString() {
        assertEquals(0, solver.longestValidParentheses(""), "Empty string");
    }

    @Test
    void testLongestValidParentheses_singleOpen() {
        assertEquals(0, solver.longestValidParentheses("("), "Single open parenthesis");
    }

    @Test
    void testLongestValidParentheses_singleClose() {
        assertEquals(0, solver.longestValidParentheses(")"), "Single close parenthesis");
    }

    @Test
    void testLongestValidParentheses_simplePair() {
        assertEquals(2, solver.longestValidParentheses("()"), "Simple pair ()");
    }

    @Test
    void testLongestValidParentheses_concatenatedPairs() {
        assertEquals(4, solver.longestValidParentheses("()()"), "Concatenated pairs ()()");
    }

    @Test
    void testLongestValidParentheses_nestedPairs() {
        assertEquals(4, solver.longestValidParentheses("(())"), "Nested pairs (())");
    }

    @Test
    void testLongestValidParentheses_leadingInvalid() {
        // Valid part is "(()())" which is length 6
        assertEquals(6, solver.longestValidParentheses(")))(()())"), "Leading invalid characters");
    }

    @Test
    void testLongestValidParentheses_trailingInvalid() {
        // Valid part is "(()())" which is length 6
        assertEquals(6, solver.longestValidParentheses("(()())((("), "Trailing invalid characters");
    }

    @Test
    void testLongestValidParentheses_mixedValidAndInvalid() {
        // Valid part is "()()" which is length 4
        assertEquals(4, solver.longestValidParentheses(")()())"), "Mixed valid and invalid, example 1");
        // For "()(()((", the longest valid substrings are "()" at index 0 and "()" at index 3. Length is 2.
        assertEquals(2, solver.longestValidParentheses("()(()(("), "Mixed valid and invalid, example 2");
    }

    @Test
    void testLongestValidParentheses_noValidParentheses() {
        assertEquals(0, solver.longestValidParentheses("((("), "No valid parentheses sequence");
        assertEquals(0, solver.longestValidParentheses(")))"), "No valid parentheses sequence");
        // For ")(()(", the longest valid substring is "()" at index 2. Length is 2.
        assertEquals(2, solver.longestValidParentheses(")(()("), "No valid parentheses sequence, but contains ()");
    }

    @Test
    void testLongestValidParentheses_longValidString() {
        // The string "()(()())((()))" is itself a valid parentheses string of length 14.
        assertEquals(14, solver.longestValidParentheses("()(()())((()))"), "Long valid string");
    }

    @Test
    void testLongestValidParentheses_complexString1() {
        // The longest valid substring is "((()))" or "(()())", both length 6.
        assertEquals(6, solver.longestValidParentheses(")((())))(()())("), "Complex string example 1");
    }

    @Test
    void testLongestValidParentheses_complexString2() {
        // Longest is "()(())" -> length 6
        assertEquals(6, solver.longestValidParentheses("()(())"), "Complex string example 2");
    }

    @Test
    void testLongestValidParentheses_fromProblemStatement1() {
        assertEquals(2, solver.longestValidParentheses("(()"), "Problem statement example 1");
    }

    @Test
    void testLongestValidParentheses_fromProblemStatement2() {
        assertEquals(4, solver.longestValidParentheses(")()())"), "Problem statement example 2");
        // This is already covered by testLongestValidParentheses_mixedValidAndInvalid,
        // but good to have it explicitly if it was a direct example.
    }

    @Test
    void testLongestValidParentheses_anotherComplex() {
        // The string "((()()))(())" is itself a valid parentheses string of length 12.
        assertEquals(12, solver.longestValidParentheses("((()()))(())"), "Another complex case");
    }
}
