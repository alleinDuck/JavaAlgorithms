import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrappingRainWaterTest {

    private final TrappingRainWater solver = new TrappingRainWater();

    @Test
    void testTrap_emptyArray() {
        assertEquals(0, solver.trap(new int[]{}), "Empty array should trap 0 water.");
    }

    @Test
    void testTrap_singleBar() {
        assertEquals(0, solver.trap(new int[]{5}), "Single bar should trap 0 water.");
    }

    @Test
    void testTrap_twoBars() {
        assertEquals(0, solver.trap(new int[]{5, 3}), "Two bars should trap 0 water.");
        assertEquals(0, solver.trap(new int[]{3, 5}), "Two bars (ascending) should trap 0 water.");
    }

    @Test
    void testTrap_flatSurface() {
        assertEquals(0, solver.trap(new int[]{3, 3, 3, 3}), "Flat surface should trap 0 water.");
    }

    @Test
    void testTrap_peakInMiddle() {
        // Standard example from LeetCode
        assertEquals(6, solver.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}), "Peak in middle");
    }

    @Test
    void testTrap_valley() {
        // Water: (3-0) + (3-1) + (3-0) = 3 + 2 + 3 = 8
        assertEquals(8, solver.trap(new int[]{3, 0, 1, 0, 3}), "Simple valley");
    }

    @Test
    void testTrap_descendingHeights() {
        assertEquals(0, solver.trap(new int[]{5, 4, 3, 2, 1}), "Descending heights should trap 0 water.");
    }

    @Test
    void testTrap_ascendingHeights() {
        assertEquals(0, solver.trap(new int[]{1, 2, 3, 4, 5}), "Ascending heights should trap 0 water.");
    }

    @Test
    void testTrap_complexCase1() {
        // Detailed trace in prompt:
        // L=0,R=5, h[0]=4,h[5]=5. leftMax=0,rightMax=0,water=0
        // h[L]<h[R] (4<5): h[L]>=leftMax (4>=0) -> leftMax=4. L=1.
        // h[L]<h[R] (2<5): h[L]<leftMax (2<4) -> water+=4-2=2. L=2.
        // h[L]<h[R] (0<5): h[L]<leftMax (0<4) -> water+=4-0=4. L=3. (Total water=2+4=6)
        // h[L]<h[R] (3<5): h[L]<leftMax (3<4) -> water+=4-3=1. L=4. (Total water=6+1=7)
        // h[L]<h[R] (2<5): h[L]<leftMax (2<4) -> water+=4-2=2. L=5. (Total water=7+2=9)
        // Loop ends. Expected 9.
        assertEquals(9, solver.trap(new int[]{4, 2, 0, 3, 2, 5}), "Complex case 1");
    }

    @Test
    void testTrap_sawtoothPattern() {
        // Water at index 2 (0): min(1,1)-0 = 1
        // Water at index 4 (0): min(1,1)-0 = 1
        // Water at index 6 (0): min(1,1)-0 = 1 (No, this is last bar, right pointer will be there)
        // Let's trace: [0, 1, 0, 1, 0, 1, 0] -> length 7
        // L=0,R=6 | hL=0,hR=0. leftMax=0,rightMax=0. water=0
        // hL<hR is false (0<=0). hR>=rightMax (0>=0) -> rightMax=0. R=5. hR=1
        // hL<hR (0<1). hL>=leftMax (0>=0) -> leftMax=0. L=1. hL=1
        // hL<hR is false (1<=1). hR>=rightMax (1>=0) -> rightMax=1. R=4. hR=0
        // hL<hR is false (1<=0) is false. hL>=leftMax (1>=0) -> leftMax=1. L=2. hL=0
        // hL<hR (0<0) is false. hR<rightMax (0<1). water+=1-0=1. R=3. hR=1. (water=1)
        // hL<hR (0<1). hL<leftMax (0<1). water+=1-0=1. L=3. hL=1. (water=2)
        // L=3,R=3. Loop ends.
        // This trace is wrong.
        // Correct trace for [0, 1, 0, 1, 0, 1, 0]
        // L=0, R=6, hL=0, hR=0. lM=0, rM=0, W=0
        // hL(0) <= hR(0). Side: R. hR(0) >= rM(0) -> rM=0. R=5 (hR=1)
        // hL(0) < hR(1). Side: L. hL(0) >= lM(0) -> lM=0. L=1 (hL=1)
        // hL(1) == hR(1). Side: R. hR(1) >= rM(0) -> rM=1. R=4 (hR=0)
        // hL(1) > hR(0). Side: R. hR(0) < rM(1) -> W+=1-0=1. R=3 (hR=1). W=1
        // hL(1) == hR(1). Side: R. hR(1) >= rM(1) -> rM=1. R=2 (hR=0)
        // hL(1) > hR(0). Side: R. hR(0) < rM(1) -> W+=1-0=1. R=1 (hL=1). W=2
        // L=1, R=1. Loop ends.
        // Expected is 3. My trace gives 2.
        // The problem is that the first leftMax / rightMax should be initialized to the first / last element.
        // No, the logic is: if height[left] >= leftMax, update leftMax. This means leftMax starts at 0, and is updated by the first element it processes.
        // Let's use example [0,1,0,1,0] -> expected 1
        // L=0,R=4. hL=0,hR=0. lM=0,rM=0. W=0
        // hL(0) <= hR(0). Side:R. hR(0)>=rM(0) -> rM=0. R=3 (hR=1)
        // hL(0) < hR(1). Side:L. hL(0)>=lM(0) -> lM=0. L=1 (hL=1)
        // hL(1) == hR(1). Side:R. hR(1)>=rM(0) -> rM=1. R=2 (hR=0)
        // hL(1) > hR(0). Side:R. hR(0)<rM(1) -> W+=1-0=1. R=1 (hR=1). W=1
        // L=1,R=1. Loop ends. Returns 1. This is correct.

        // For [0, 1, 0, 1, 0, 1, 0], expected 2.
        // L=0,R=6 (0,0) lM=0,rM=0, W=0
        // R: hR(0)>=rM(0) -> rM=0. R=5 (hR=1)
        // L: hL(0)>=lM(0) -> lM=0. L=1 (hL=1)
        // R: hR(1)>=rM(0) -> rM=1. R=4 (hR=0)
        // R: hR(0)<rM(1) -> W+=1-0=1. R=3 (hR=1). W=1
        // R: hR(1)>=rM(1) -> rM=1. R=2 (hR=0)  <-- Error in my prev trace, rM was 1, hR(1) is not > rM(1)
        // L: hL(1)>=lM(0) -> lM=1. L=2 (hL=0) <-- Error here, when L moved from 0 to 1, lM became 1.
        // Let's re-trace [0, 1, 0, 1, 0, 1, 0] carefully:
        // L=0, R=6, height={0,1,0,1,0,1,0}
        // lM=0, rM=0, W=0
        // 1. L=0,R=6. h[L]=0,h[R]=0. h[L]<h[R] (0<0) is false. (Side R)
        //    h[R](0) >= rM(0) is true. rM=0. R=5. (h[R]=1)
        // 2. L=0,R=5. h[L]=0,h[R]=1. h[L]<h[R] (0<1) is true. (Side L)
        //    h[L](0) >= lM(0) is true. lM=0. L=1. (h[L]=1)
        // 3. L=1,R=5. h[L]=1,h[R]=1. h[L]<h[R] (1<1) is false. (Side R)
        //    h[R](1) >= rM(0) is true. rM=1. R=4. (h[R]=0)
        // 4. L=1,R=4. h[L]=1,h[R]=0. h[L]<h[R] (1<0) is false. (Side R)
        //    h[R](0) >= rM(1) is false. W += rM(1)-h[R](0) = 1-0=1. W=1. R=3. (h[R]=1)
        // 5. L=1,R=3. h[L]=1,h[R]=1. h[L]<h[R] (1<1) is false. (Side R)
        //    h[R](1) >= rM(1) is true. rM=1. R=2. (h[R]=0)
        // 6. L=1,R=2. h[L]=1,h[R]=0. h[L]<h[R] (1<0) is false. (Side R)
        //    h[R](0) >= rM(1) is false. W += rM(1)-h[R](0) = 1-0=1. W=2. R=1. (h[R]=1)
        // 7. L=1,R=1. Loop terminates.
        // Result is 2. The prompt example says expected 3.
        // The example `[0,1,0,1,0,1,0]` has water trapped at indices 2, 4.
        // Index 2 is between 1 (idx 1) and 1 (idx 3). Traps 1 unit.
        // Index 4 is between 1 (idx 3) and 1 (idx 5). Traps 1 unit.
        // Total = 2.
        // So the example in the prompt for sawtooth `[0, 1, 0, 1, 0, 1, 0]` expecting 3 is likely wrong. It should be 2.
        assertEquals(2, solver.trap(new int[]{0, 1, 0, 1, 0, 1, 0}), "Sawtooth pattern");
    }

    @Test
    void testTrap_withZeroHeightBars() {
        assertEquals(2, solver.trap(new int[]{2, 0, 2}), "Valley of zeros");
    }

    @Test
    void testTrap_anotherValley() {
        // Water: (5-1)+(5-2)+(5-0)+(5-3) = 4+3+5+2 = 14
        assertEquals(14, solver.trap(new int[]{5, 1, 2, 0, 3, 5}), "Another valley example");
    }

    @Test
    void testTrap_complexCaseFromLeetcode() {
        assertEquals(23, solver.trap(new int[]{5,5,1,7,1,1,5,2,7,6}), "Complex case from Leetcode discussion");
        // lM=0, rM=0, W=0
        // L=0,R=9 (5,6) -> L: lM=5. L=1
        // L=1,R=9 (5,6) -> L: lM=5 (no change as h[1]=5==lM). L=2
        // L=2,R=9 (1,6) -> L: h[2](1)<lM(5). W+=5-1=4. L=3. W=4
        // L=3,R=9 (7,6) -> R: h[R](6)>=rM(0). rM=6. R=8. (h[R]=7)
        // L=3,R=8 (7,7) -> R: h[R](7)>=rM(6). rM=7. R=7. (h[R]=2)
        // L=3,R=7 (7,2) -> R: h[R](2)<rM(7). W+=7-2=5. R=6. (h[R]=5). W=4+5=9
        // L=3,R=6 (7,5) -> R: h[R](5)<rM(7). W+=7-5=2. R=5. (h[R]=1). W=9+2=11
        // L=3,R=5 (7,1) -> R: h[R](1)<rM(7). W+=7-1=6. R=4. (h[R]=1). W=11+6=17
        // L=3,R=4 (7,1) -> R: h[R](1)<rM(7). W+=7-1=6. R=3. (h[R]=7). W=17+6=23
        // L=3,R=3. Loop ends. W=23. Correct.
    }
}
