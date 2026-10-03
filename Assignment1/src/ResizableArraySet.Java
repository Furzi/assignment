import java.util.Arrays;
import java.util.Objects;



public class ResizableArraySet<T> implements SetInterface<T> {
    private T[] setArray;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_CAPACITY = 10000;

    
    //Creates an empty set with default capacity.
     
    public ResizableArraySet() {
        this(DEFAULT_CAPACITY);
    }
    //   Creates an empty set with a given initial capacity. 
    //  @param initialCapacity the initial capacity of the backing array
    @SuppressWarnings("unchecked")
    public ResizableArraySet(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = DEFAULT_CAPACITY;
        }
        // Generic array creation requires casting from Object[]
        this.setArray = (T[]) new Object[initialCapacity];
        this.numberOfEntries = 0;
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
        // Reject null entries consistently
        if (newEntry == null) {
            throw new IllegalArgumentException("Cannot add null to ResizableArraySet.");
        }

        // Prevent duplicates
        if (contains(newEntry)) {
            return false;
        }

        // Check capacity and double array size if full
        if (isArrayFull()) {
            doubleCapacity();
        }

        setArray[numberOfEntries] = newEntry;
        numberOfEntries++;
        return true;
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            return null;
        }

        // Remove the last active entry to avoid gaps
        T result = setArray[numberOfEntries - 1];
        setArray[numberOfEntries - 1] = null; // Prevent loitering
        numberOfEntries--;

        return result;
    }

    @Override
    public boolean remove(T anEntry) {
        if (anEntry == null || isEmpty()) {
            return false;
        }

        int index = getIndexOf(anEntry);
        if (index >= 0) {
            // Replace entry with last entry to keep array contiguous without shifting
            setArray[index] = setArray[numberOfEntries - 1];
            setArray[numberOfEntries - 1] = null; // Prevent loitering
            numberOfEntries--;
            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            remove();
        }
    }

    @Override
    public boolean contains(T anEntry) {
        if (anEntry == null) {
            return false;
        }
        return getIndexOf(anEntry) >= 0;
    }

    @Override
    public T[] toArray() {
        // Return a new snapshot array; modifying it will not affect the set
        return Arrays.copyOf(setArray, numberOfEntries);
    }

    @Override
    public SetInterface<T> union(SetInterface<T> otherSet) {
        SetInterface<T> result = new ResizableArraySet<>();
        
        // Add all elements from this set
        for (T item : this.toArray()) {
            result.add(item);
        }
        
        // Add all elements from otherSet
        if (otherSet != null) {
            for (T item : otherSet.toArray()) {
                result.add(item); // add() automatically ignores duplicates
            }
        }
        return result;
    }

    @Override
    public SetInterface<T> intersection(SetInterface<T> otherSet) {
        SetInterface<T> result = new ResizableArraySet<>();
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
        SetInterface<T> result = new ResizableArraySet<>();
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

    // --- Helper Methods ---

    private boolean isArrayFull() {
        return numberOfEntries >= setArray.length;
    }

    private void doubleCapacity() {
        int newLength = 2 * setArray.length;
        checkCapacity(newLength);
        setArray = Arrays.copyOf(setArray, newLength);
    }

    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY) {
            throw new IllegalStateException("Attempt to create a set whose capacity exceeds allowed maximum.");
        }
    }

    private int getIndexOf(T anEntry) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (Objects.equals(anEntry, setArray[i])) {
                return i;
            }
        }
        return -1;
    }
}