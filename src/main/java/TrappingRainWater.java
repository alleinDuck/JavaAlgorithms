/**
 * Problem: Trapping Rain Water
 *
 * Description:
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it can trap after raining.
 *
 * Approach: Two Pointers
 * This approach efficiently calculates the trapped water in a single pass.
 * We use two pointers, `left` starting from the first bar and `right` from the last bar.
 * We also maintain `leftMax` (maximum height encountered from the left up to `left` pointer)
 * and `rightMax` (maximum height encountered from the right up to `right` pointer).
 * The amount of water trapped above any bar is determined by the minimum of `leftMax` and
 * `rightMax` minus the height of the current bar.
 *
 * Algorithm Steps (Two Pointers):
 * 1. Initialize `left = 0` and `right = height.length - 1`.
 * 2. Initialize `leftMax = 0` and `rightMax = 0`. These will store the maximum height
 *    of a bar encountered so far from the left and right ends, respectively.
 * 3. Initialize `waterTrapped = 0`. This will accumulate the total amount of trapped water.
 * 4. Iterate while `left < right`:
 *    a. Compare `height[left]` and `height[right]`:
 *       i. If `height[left] < height[right]`:
 *          - The water level at the `left` pointer is limited by `leftMax`.
 *          - If `height[left]` is greater than or equal to `leftMax`, it means this bar
 *            is higher than any bar encountered from the left so far. So, update
 *            `leftMax = height[left]`. No water can be trapped above this bar based
 *            on the current `leftMax`.
 *          - Else (`height[left] < leftMax`), this bar is lower than `leftMax`. The
 *            amount of water trapped above `height[left]` is `leftMax - height[left]`.
 *            Add this amount to `waterTrapped`.
 *          - Increment `left` pointer to move one step to the right.
 *       ii. Else (`height[right] <= height[left]`):
 *           - The water level at the `right` pointer is limited by `rightMax`.
 *           - If `height[right]` is greater than or equal to `rightMax`, it means this bar
 *             is higher than any bar encountered from the right so far. So, update
 *             `rightMax = height[right]`. No water can be trapped above this bar based
 *             on the current `rightMax`.
 *           - Else (`height[right] < rightMax`), this bar is lower than `rightMax`. The
 *             amount of water trapped above `height[right]` is `rightMax - height[right]`.
 *             Add this amount to `waterTrapped`.
 *           - Decrement `right` pointer to move one step to the left.
 * 5. After the loop finishes (`left` meets or crosses `right`), return `waterTrapped`.
 *
 * Time Complexity: O(N)
 * The algorithm involves a single pass with two pointers. Each pointer (`left` and `right`)
 * traverses the array at most once. So, the time complexity is linear with respect to
 * the number of bars N.
 *
 * Space Complexity: O(1)
 * The algorithm uses only a few extra variables (`left`, `right`, `leftMax`, `rightMax`,
 * `waterTrapped`), irrespective of the input size. Thus, the space complexity is constant.
 */
public class TrappingRainWater {

    public int trap(int[] height) {
        // Handle edge cases: if the array is null or has less than 3 bars,
        // no water can be trapped. (Need at least two walls and a space between them)
        if (height == null || height.length < 3) {
            return 0;
        }

        int n = height.length;
        int left = 0;               // Left pointer, starts at the beginning of the array
        int right = n - 1;          // Right pointer, starts at the end of the array
        int leftMax = 0;            // Maximum height encountered from the left
        int rightMax = 0;           // Maximum height encountered from the right
        int waterTrapped = 0;       // Total accumulated trapped water

        // Process the array until left and right pointers meet
        while (left < right) {
            // If the height at the left pointer is less than the height at the right pointer
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    // If current left bar is higher than or equal to leftMax, update leftMax
                    // This bar cannot trap water based on the previous leftMax
                    leftMax = height[left];
                } else {
                    // If current left bar is lower than leftMax, it can trap water
                    // Water trapped = leftMax - height[left]
                    waterTrapped += leftMax - height[left];
                }
                // Move left pointer to the right
                left++;
            } else { // height[right] <= height[left]
                // If the height at the right pointer is less than or equal to the height at the left pointer
                if (height[right] >= rightMax) {
                    // If current right bar is higher than or equal to rightMax, update rightMax
                    // This bar cannot trap water based on the previous rightMax
                    rightMax = height[right];
                } else {
                    // If current right bar is lower than rightMax, it can trap water
                    // Water trapped = rightMax - height[right]
                    waterTrapped += rightMax - height[right];
                }
                // Move right pointer to the left
                right--;
            }
        }
        // Return the total water trapped
        return waterTrapped;
    }
}
