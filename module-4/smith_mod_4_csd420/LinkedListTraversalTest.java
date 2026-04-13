import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListTraversalTest {

    public static void main(String[] args) {
        System.out.println("Testing with 50,000 integers");
        runTest(50000);

        System.out.println("\nTesting with 500,000 integers");
        runTest(500000);
    }

    public static void runTest(int size) {
        LinkedList<Integer> list = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        testListContents(list, size);

        long iteratorTime = traverseWithIterator(list);
        long getIndexTime = traverseWithGet(list);

        System.out.println("List size: " + size);
        System.out.println("Iterator traversal time: " + iteratorTime + " ns");
        System.out.println("get(index) traversal time: " + getIndexTime + " ns");
        System.out.println("Difference: " + (getIndexTime - iteratorTime) + " ns");
    }

    public static long traverseWithIterator(LinkedList<Integer> list) {
        long startTime = System.nanoTime();

        long sum = 0;
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            sum += iterator.next();
        }

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static long traverseWithGet(LinkedList<Integer> list) {
        long startTime = System.nanoTime();

        long sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void testListContents(LinkedList<Integer> list, int expectedSize) {
        if (list.size() != expectedSize) {
            throw new RuntimeException("Test failed: incorrect list size.");
        }

        if (!list.getFirst().equals(0)) {
            throw new RuntimeException("Test failed: first value should be 0.");
        }

        if (!list.getLast().equals(expectedSize - 1)) {
            throw new RuntimeException("Test failed: last value should be " + (expectedSize - 1) + ".");
        }

        System.out.println("Test passed: LinkedList contains " + expectedSize + " integers correctly.");
    }
}

/*
Results discussion:
The iterator traversal is much faster than using get(index) on a LinkedList.
This is because LinkedList stores data as nodes connected together. The iterator
moves through the list one element at a time efficiently. However, get(index)
must keep locating each position in the list, which takes much longer.

With 50,000 integers, the iterator is already faster. With 500,000 integers,
the difference becomes much larger because get(index) becomes increasingly inefficient
as the list grows. This shows that iterator traversal is the better method for
LinkedList, while get(index) is much slower for large lists.
*/