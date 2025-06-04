import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NQueensTest {

    private final NQueens solver = new NQueens();

    // Helper to compare lists of boards where the order of boards doesn't matter.
    private void assertBoardListsEqual(List<List<String>> expected, List<List<String>> actual) {
        // Convert each board (List<String>) to a canonical representation (e.g., sorted list of strings,
        // or rely on List<String>.hashCode() and .equals() if row order is fixed).
        // For N-Queens, the row order within a board is fixed.
        Set<List<String>> expectedSet = new HashSet<>(expected);
        Set<List<String>> actualSet = new HashSet<>(actual);
        assertEquals(expectedSet, actualSet, "The set of solved boards should match the expected set.");
    }

    @Test
    void testSolveNQueens_n0() {
        List<List<String>> expected = new ArrayList<>(); // Expect empty list for n=0
        List<List<String>> actual = solver.solveNQueens(0);
        assertBoardListsEqual(expected, actual);
    }

    @Test
    void testSolveNQueens_n1() {
        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList("Q"));
        List<List<String>> actual = solver.solveNQueens(1);
        assertBoardListsEqual(expected, actual);
    }

    @Test
    void testSolveNQueens_n2() {
        List<List<String>> expected = new ArrayList<>(); // No solutions for n=2
        List<List<String>> actual = solver.solveNQueens(2);
        assertBoardListsEqual(expected, actual);
    }

    @Test
    void testSolveNQueens_n3() {
        List<List<String>> expected = new ArrayList<>(); // No solutions for n=3
        List<List<String>> actual = solver.solveNQueens(3);
        assertBoardListsEqual(expected, actual);
    }

    @Test
    void testSolveNQueens_n4() {
        List<List<String>> expectedSolutions = new ArrayList<>();
        expectedSolutions.add(Arrays.asList(".Q..", "...Q", "Q...", "..Q."));
        expectedSolutions.add(Arrays.asList("..Q.", "Q...", "...Q", ".Q.."));

        List<List<String>> actualSolutions = solver.solveNQueens(4);

        assertEquals(expectedSolutions.size(), actualSolutions.size(), "Number of solutions should match.");
        assertBoardListsEqual(expectedSolutions, actualSolutions);
    }

    @Test
    void testSolveNQueens_negativeInput() {
        // Assuming n must be non-negative based on problem context (board size)
        // The implementation returns an empty list for n <= 0.
        List<List<String>> expected = new ArrayList<>();
        List<List<String>> actual = solver.solveNQueens(-1);
        assertBoardListsEqual(expected, actual);
    }

    // It might be good to test a slightly larger N if performance allows, but N=4 is standard.
    // For example, N=5 has 10 solutions. N=6 has 4 solutions.
    // Listing them all out is cumbersome and error-prone for manual test setup.
    // Checking the *count* of solutions for larger N can be a good smoke test.
    @Test
    void testSolveNQueens_solutionCounts() {
        assertEquals(1, solver.solveNQueens(1).size(), "N=1 should have 1 solution.");
        assertEquals(0, solver.solveNQueens(2).size(), "N=2 should have 0 solutions.");
        assertEquals(0, solver.solveNQueens(3).size(), "N=3 should have 0 solutions.");
        assertEquals(2, solver.solveNQueens(4).size(), "N=4 should have 2 solutions.");
        assertEquals(10, solver.solveNQueens(5).size(), "N=5 should have 10 solutions.");
        // N=6 has 4 solutions.
        // N=7 has 40 solutions.
        // N=8 has 92 solutions. (This is the classic 8-queens problem)
        // N=9 has 352 solutions.
        // These can be slow, so only test a few, or smaller N.
    }
}
