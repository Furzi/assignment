public interface SetInterface<T> {
    int getCurrentSize();
    boolean isEmpty();
    boolean add(T newEntry);
    T remove();
    boolean remove(T anEntry);
    void clear();
    boolean contains(T anEntry);
    T[] toArray();
    SetInterface<T> union(SetInterface<T> otherSet);
    SetInterface<T> intersection(SetInterface<T> otherSet);
    SetInterface<T> difference(SetInterface<T> otherSet);
}