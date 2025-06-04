/**
 * Problem: Merge k Sorted Lists
 *
 * Description:
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Approach:
 * The problem can be efficiently solved using a min-priority queue (MinHeap).
 * The MinHeap will store the head nodes of the k lists. We repeatedly extract
 * the minimum node from the heap, add it to our result list, and if the
 * extracted node has a next node, we add that next node to the heap.
 *
 * Algorithm Steps:
 * 1. Define a ListNode class with 'val' and 'next' pointers.
 * 2. Initialize a min-priority queue. The priority queue will store ListNode objects
 *    and order them by their 'val' attribute.
 * 3. Add the head of each non-empty input list to the priority queue.
 * 4. Create a dummy head for the result list (e.g., `dummy = new ListNode(-1)`)
 *    and a pointer for the current tail of the result list (e.g., `current = dummy`).
 * 5. While the priority queue is not empty:
 *    a. Extract the node with the smallest value from the priority queue.
 *       This node is the next smallest node in the overall sorted list.
 *    b. Append this extracted node to the `next` of the `current` tail of the result list.
 *    c. Move the `current` tail pointer to this newly added node (`current = current.next`).
 *    d. If the extracted node has a `next` node in its original list, add that
 *       `next` node to the priority queue.
 * 6. Return `dummy.next`, which is the actual head of the merged sorted list.
 *
 * Time Complexity:
 * - O(N log k), where N is the total number of nodes in all lists and k is the number of lists.
 * - Adding all k initial heads to the heap takes O(k log k).
 * - Each of the N nodes will be extracted from the heap once, and each extraction
 *   takes O(log k). So, N extractions take O(N log k).
 * - Adding the next nodes to the heap also takes O(log k) for each of the N nodes in the worst case.
 * - Thus, the dominant factor is O(N log k).
 *
 * Space Complexity:
 * - O(k) in the worst case for the priority queue, as it will store at most k nodes
 *   (one from each list).
 * - O(N) for the new merged list if we consider the output space. If we only consider
 *   auxiliary space, it's O(k).
 */

import java.util.PriorityQueue;
import java.util.Comparator;

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class MergeKSortedLists {

    /**
     * Merges k sorted linked lists into one sorted linked list.
     *
     * @param lists An array of ListNode objects, where each ListNode is the head of a sorted list.
     * @return The head of the merged sorted linked list.
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // Handle edge case: if the input array is null or empty, return null.
        if (lists == null || lists.length == 0) {
            return null;
        }

        // Initialize a min-priority queue.
        // The comparator ensures that ListNodes are ordered by their 'val' attribute.
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.val));

        // Add the head of each non-empty input list to the priority queue.
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.add(list);
            }
        }

        // Create a dummy head for the result list.
        ListNode dummyHead = new ListNode(-1);
        // Create a pointer for the current tail of the result list.
        ListNode current = dummyHead;

        // While the priority queue is not empty:
        while (!minHeap.isEmpty()) {
            // Extract the node with the smallest value from the priority queue.
            ListNode smallestNode = minHeap.poll();

            // Append this node to the result list.
            current.next = smallestNode;
            // Move the current pointer to the newly added node.
            current = current.next;

            // If the extracted node has a 'next' node, add that 'next' node to the priority queue.
            if (smallestNode.next != null) {
                minHeap.add(smallestNode.next);
            }
        }

        // Return the 'next' node of the dummy head, which is the actual head of the merged list.
        return dummyHead.next;
    }
}
