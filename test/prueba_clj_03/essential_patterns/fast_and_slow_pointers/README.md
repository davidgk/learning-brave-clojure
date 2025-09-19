# Title

Detect cycle in a linked list.


## examples

```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}
public boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
````