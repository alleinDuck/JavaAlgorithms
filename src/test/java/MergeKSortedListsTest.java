import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MergeKSortedListsTest {

    private final MergeKSortedLists merger = new MergeKSortedLists();

    // Helper method to construct a linked list from an array of integers
    private ListNode arrayToList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }

    // Helper method to convert a linked list to a List of integers
    private List<Integer> listToArray(ListNode head) {
        if (head == null) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            result.add(current.val);
            current = current.next;
        }
        return result;
    }

    @Test
    void testMergeKLists_emptyInput() {
        // Test with an empty array of lists.
        ListNode[] lists = {};
        ListNode result = merger.mergeKLists(lists);
        assertNull(result, "Merging an empty array of lists should return null.");

        // Test with a null array of lists.
        ListNode resultNull = merger.mergeKLists(null);
        assertNull(resultNull, "Merging a null array of lists should return null.");
    }

    @Test
    void testMergeKLists_listOfEmptyLists() {
        // Test with a list containing multiple empty lists.
        ListNode[] lists = {null, null, null};
        ListNode result = merger.mergeKLists(lists);
        assertNull(result, "Merging a list of null lists should return null.");
    }

    @Test
    void testMergeKLists_singleList() {
        // Test with a single list in the input.
        ListNode[] lists = {arrayToList(new int[]{1, 4, 5})};
        ListNode result = merger.mergeKLists(lists);
        assertEquals(Arrays.asList(1, 4, 5), listToArray(result), "Merging a single list.");
    }

    @Test
    void testMergeKLists_multipleNonEmptyLists() {
        // Test with several non-empty sorted lists.
        ListNode[] lists = {
            arrayToList(new int[]{1, 4, 5}),
            arrayToList(new int[]{1, 3, 4}),
            arrayToList(new int[]{2, 6})
        };
        ListNode result = merger.mergeKLists(lists);
        List<Integer> expected = Arrays.asList(1, 1, 2, 3, 4, 4, 5, 6);
        assertEquals(expected, listToArray(result), "Merging multiple non-empty lists.");
    }

    @Test
    void testMergeKLists_listsWithDuplicateValues() {
        // Test with lists that contain duplicate values.
        ListNode[] lists = {
            arrayToList(new int[]{1, 1, 2, 3}),
            arrayToList(new int[]{1, 2, 2, 4}),
            arrayToList(new int[]{3, 3, 5})
        };
        ListNode result = merger.mergeKLists(lists);
        List<Integer> expected = Arrays.asList(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 5);
        assertEquals(expected, listToArray(result), "Merging lists with duplicate values.");
    }

    @Test
    void testMergeKLists_oneListIsEmpty() {
        // Test with a mix of empty and non-empty lists.
        ListNode[] lists = {
            arrayToList(new int[]{1, 4, 5}),
            null,
            arrayToList(new int[]{2, 6}),
            null
        };
        ListNode result = merger.mergeKLists(lists);
        List<Integer> expected = Arrays.asList(1, 2, 4, 5, 6);
        assertEquals(expected, listToArray(result), "Merging a mix of empty and non-empty lists.");
    }

    @Test
    void testMergeKLists_allNodesInOneList() {
        // Test where one list contains all nodes and others are empty.
        ListNode[] lists = {
            null,
            arrayToList(new int[]{1, 2, 3, 4, 5}),
            null,
            null
        };
        ListNode result = merger.mergeKLists(lists);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(expected, listToArray(result), "Merging when one list has all nodes and others are empty.");

        ListNode[] lists2 = {
            arrayToList(new int[]{10, 20, 30}),
            null,
            null
        };
        ListNode result2 = merger.mergeKLists(lists2);
        List<Integer> expected2 = Arrays.asList(10, 20, 30);
        assertEquals(expected2, listToArray(result2), "Merging when first list has all nodes and others are empty.");
    }

    @Test
    void testMergeKLists_veryLargeValues() {
        // Test with large integer values.
        ListNode[] lists = {
            arrayToList(new int[]{1000000, 2000000}),
            arrayToList(new int[]{500000, 1500000})
        };
        ListNode result = merger.mergeKLists(lists);
        List<Integer> expected = Arrays.asList(500000, 1000000, 1500000, 2000000);
        assertEquals(expected, listToArray(result), "Merging lists with very large values.");
    }

    @Test
    void testMergeKLists_singleElementLists() {
        // Test with lists containing single elements.
        ListNode[] lists = {
            arrayToList(new int[]{1}),
            arrayToList(new int[]{5}),
            arrayToList(new int[]{2}),
            arrayToList(new int[]{0})
        };
        ListNode result = merger.mergeKLists(lists);
        List<Integer> expected = Arrays.asList(0, 1, 2, 5);
        assertEquals(expected, listToArray(result), "Merging lists with single elements.");
    }
}
