import java.lang.reflect.Array;
import java.util.Objects;

/**
 * A singly linked list-based implementation of the Set ADT.
 * Maintains an unordered collection of unique elements with pointer references.
 *
 * @param <T> the type of elements held in this set
 */
public class LinkedSet<T> implements SetInterface<T> {
    private Node firstNode;
    private int numberOfEntries;

    public LinkedSet() {
        this.firstNode = null;
        this.numberOfEntries = 0;
    }

    private class Node {
        private T data;
        private Node next;

        private Node(T dataPortion) {
            this(dataPortion, null);
        }

        private Node(T dataPortion, Node nextNode) {
            this.data = dataPortion;
            this.next = nextNode;
        }
    }

    @Override
    public int getCurrentSize() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean add(T newEntry) {
        if (newEntry == null) {
            throw new IllegalArgumentException("Cannot add null to LinkedSet.");
        }

        // Prevent duplicates
        if (contains(newEntry)) {
            return false;
        }

        // Insert at the head O(1)
        Node newNode = new Node(newEntry, firstNode);
        firstNode = newNode;
        numberOfEntries++;
        return true;
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            return null;
        }

        // Remove from the head
        T result = firstNode.data;
        firstNode = firstNode.next;
        numberOfEntries--;
        return result;
    }

    @Override
    public boolean remove(T anEntry) {
        if (anEntry == null || isEmpty()) {
            return false;
        }

        Node currentNode = firstNode;
        Node previousNode = null;

        while (currentNode != null) {
            if (Objects.equals(anEntry, currentNode.data)) {
                if (previousNode == null) {
                    // Removing head node
                    firstNode = currentNode.next;
                } else {
                    // Removing middle or tail node
                    previousNode.next = currentNode.next;
                }
                currentNode.next = null; // Disconnect reference
                numberOfEntries--;
                return true;
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }

        return false;
    }

    @Override
    public void clear() {
        firstNode = null; // Unlinks entire chain for garbage collection
        numberOfEntries = 0;
    }

    @Override
    public boolean contains(T anEntry) {
        if (anEntry == null) {
            return false;
        }

        Node currentNode = firstNode;
        while (currentNode != null) {
            if (Objects.equals(anEntry, currentNode.data)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[numberOfEntries];
        Node currentNode = firstNode;
        int index = 0;
        while (currentNode != null) {
            result[index++] = currentNode.data;
            currentNode = currentNode.next;
        }
        return result;
    }

    @Override
    public SetInterface<T> union(SetInterface<T> otherSet) {
        SetInterface<T> result = new LinkedSet<>();
        
        for (T item : this.toArray()) {
            result.add(item);
        }
        
        if (otherSet != null) {
            for (T item : otherSet.toArray()) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public SetInterface<T> intersection(SetInterface<T> otherSet) {
        SetInterface<T> result = new LinkedSet<>();
        if (otherSet == null) {
            return result;
        }

        for (T item : this.toArray()) {
            if (otherSet.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public SetInterface<T> difference(SetInterface<T> otherSet) {
        SetInterface<T> result = new LinkedSet<>();
        if (otherSet == null) {
            for (T item : this.toArray()) {
                result.add(item);
            }
            return result;
        }

        for (T item : this.toArray()) {
            if (!otherSet.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }
}