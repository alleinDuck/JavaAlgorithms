import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;

/**
 * Problem: N-Queens
 *
 * Description:
 * The N-Queens puzzle is the problem of placing N chess queens on an N×N
 * chessboard such that no two queens threaten each other. This means no two queens
 * can be in the same row, column, or diagonal.
 * This solution finds all distinct solutions to the N-Queens puzzle.
 *
 * Approach: Backtracking
 * The problem is solved using a backtracking algorithm. We try to place queens
 * row by row. For each row, we iterate through all possible columns and check if
 * placing a queen in that cell `(row, col)` is safe. A placement is safe if the
 * cell is not under attack from any previously placed queens (i.e., no other queen
 * in the same column, main diagonal, or anti-diagonal).
 *
 * Algorithm Steps:
 * 1. Initialize an empty list `solutions` to store all valid board configurations.
 * 2. Initialize an `n x n` character array `board` with `.` representing empty squares.
 *    A queen is represented by `Q`.
 * 3. To efficiently check for attacks, maintain three sets:
 *    - `cols`: A set to store columns that are already occupied by a queen.
 *    - `diag1`: A set to store main diagonals (identified by `row - col`) occupied by a queen.
 *    - `diag2`: A set to store anti-diagonals (identified by `row + col`) occupied by a queen.
 * 4. Define a recursive helper function `backtrack(row, cols, diag1, diag2, n, solutions, board)`:
 *    a. **Base Case:** If `row == n`, it means we have successfully placed N queens, one in each
 *       row, without any attacking each other. A valid solution is found.
 *       Convert the current `board` (char[][]) configuration into a `List<String>`
 *       and add it to the `solutions` list. Then, return.
 *    b. **Recursive Step:** For the current `row`, iterate through each column `col` from `0` to `n-1`:
 *       i. Check if placing a queen at `(row, col)` is safe. This position is safe if:
 *          - The column `col` is not present in the `cols` set.
 *          - The main diagonal `row - col` is not present in the `diag1` set.
 *          - The anti-diagonal `row + col` is not present in the `diag2` set.
 *       ii. If the position `(row, col)` is safe:
 *           1. Place the queen: Set `board[row][col] = 'Q'`.
 *           2. Update the sets: Add `col` to `cols`, `row - col` to `diag1`, and `row + col` to `diag2`.
 *           3. Recursively call `backtrack(row + 1, cols, diag1, diag2, n, solutions, board)`
 *              to try placing a queen in the next row.
 *           4. **Backtrack**: After the recursive call returns (meaning all possibilities for
 *              the subsequent rows have been explored), undo the current placement to explore
 *              other possibilities for the current row.
 *              Set `board[row][col] = '.'`.
 *              Remove `col` from `cols`, `row - col` from `diag1`, and `row + col` from `diag2`.
 * 5. The initial call to start the backtracking process will be
 *    `backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>(), n, solutions, board)`.
 * 6. After the initial call completes, `solutions` will contain all distinct board configurations.
 *    Return `solutions`.
 *
 * Time Complexity: O(N!)
 * While it's hard to give an exact tight bound, the algorithm explores placements of N queens.
 * In the worst case, it resembles exploring permutations. Pruning invalid branches helps,
 * but the complexity is roughly factorial in N. For each valid placement, constructing the
 * board string representation takes O(N^2).
 *
 * Space Complexity: O(N^2)
 * - The `board` array takes O(N^2) space.
 * - The recursion stack can go up to N levels deep. Each level stores some information.
 * - The sets (`cols`, `diag1`, `diag2`) can store up to O(N) elements each.
 * - The `solutions` list can store multiple boards, each O(N^2). If K is the number of solutions,
 *   this could be O(K * N^2). Excluding the space for storing solutions, it's O(N^2).
 */
public class NQueens {

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> solutions = new ArrayList<>();
        // Handle edge case for n=0 or n<0 (though typical constraints are n>=1)
        // For n=0, it could be argued to return [[]] (one solution: empty board on empty grid)
        // or [] (no solutions as no queens can be placed). LeetCode expects [] for n=0.
        if (n <= 0) {
            return solutions; // Return empty list for n=0 or invalid n
        }

        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], '.'); // Initialize board with empty cells
        }

        // Sets to keep track of occupied columns and diagonals
        Set<Integer> cols = new HashSet<>();
        Set<Integer> diag1 = new HashSet<>(); // For (row - col)
        Set<Integer> diag2 = new HashSet<>(); // For (row + col)

        // Start backtracking from the first row (row 0)
        backtrack(0, cols, diag1, diag2, n, solutions, board);

        return solutions;
    }

    private void backtrack(int row, Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2,
                           int n, List<List<String>> solutions, char[][] board) {
        // Base case: If all queens are placed (i.e., current row is n)
        if (row == n) {
            solutions.add(constructBoard(board)); // Add the valid board configuration to solutions
            return;
        }

        // Iterate through each column in the current row
        for (int col = 0; col < n; col++) {
            // Check if placing a queen at (row, col) is safe
            if (cols.contains(col) ||              // Check column
                diag1.contains(row - col) ||      // Check main diagonal (r-c)
                diag2.contains(row + col)) {      // Check anti-diagonal (r+c)
                continue; // If not safe, skip this column
            }

            // Place the queen
            board[row][col] = 'Q';
            cols.add(col);
            diag1.add(row - col);
            diag2.add(row + col);

            // Recursively call backtrack for the next row
            backtrack(row + 1, cols, diag1, diag2, n, solutions, board);

            // Backtrack: Remove the queen and reset sets for this position
            board[row][col] = '.';
            cols.remove(col);
            diag1.remove(row - col);
            diag2.remove(row + col);
        }
    }

    // Helper function to convert the char[][] board to List<String>
    private List<String> constructBoard(char[][] boardArray) {
        List<String> boardRepresentation = new ArrayList<>();
        for (int i = 0; i < boardArray.length; i++) {
            boardRepresentation.add(new String(boardArray[i])); // Convert each row to a String
        }
        return boardRepresentation;
    }
}
