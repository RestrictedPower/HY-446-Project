package implementation;

import java.util.*;

/**
 * Custom PriorityQueue Implementation that supports O(logn) removals.
 *
 * @param <E> the type of elements held in this queue
 * @author Konstantinos Anemozalis, Orfeas Xatzipanagiotis
 */
@SuppressWarnings("unchecked")
public class PriorityQueue<E> extends AbstractQueue<E> implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = -7720805057305804111L;
    private static final boolean TESTING = false;
    private int heapCap;
    private int currentSize;
    private final HashMap<Object, HashSet<Integer>> objectIndex;
    private transient Object[] heap;

    /**
     * The comparator, or null if priority queue uses elements'
     * natural ordering.
     */
    @SuppressWarnings("serial")
    private final Comparator<? super E> comparator;

    /**
     * Constructor with custom initial capacity and comparator
     *
     * @param initialCapacity The initial heap size
     * @param comparator      The comparator to use
     */
    public PriorityQueue(int initialCapacity, Comparator<? super E> comparator) {
        objectIndex = new HashMap<>();
        heapCap = initialCapacity;
        currentSize = 0;
        this.heap = new Object[heapCap];
        this.comparator = comparator;
    }

    /**
     * Constructor with custom comparator
     *
     * @param comparator The comparator to use
     */
    public PriorityQueue(Comparator<? super E> comparator) {
        this(8, comparator);
    }

    /**
     * Constructor with custom initial capacity
     *
     * @param initialCapacity The initial heap size
     */
    public PriorityQueue(int initialCapacity) {
        objectIndex = new HashMap<>();
        heapCap = initialCapacity;
        currentSize = 0;
        heap = (E[]) new Object[heapCap];
        this.comparator = null;
        if (TESTING) validateAll();
    }

    /**
     * Basic constructor
     */
    public PriorityQueue() {
        this(8);
    }

    /**
     * Returns the comparator used to order the elements in this
     * queue, or {@code null} if this queue is sorted according to
     * the {@linkplain Comparable natural ordering} of its elements.
     *
     * @return the comparator used to order this queue, or
     * {@code null} if this queue is sorted according to the
     * natural ordering of its elements
     */
    public Comparator<? super E> comparator() {
        return comparator;
    }

    public E poll() {
        if (currentSize == 0) throw new RuntimeException("empty!");
        E v = deleteAt(0);
        if (TESTING) validateAll();
        return v;
    }

    public E peek() {
        if (currentSize == 0) throw new RuntimeException("empty!");
        return (E) heap[0];
    }

    public boolean add(E v) {
        return offer(v);
    }

    public boolean offer(E v) {
        if (currentSize >= heapCap) expandSize();
        heap[currentSize] = v;
        insertIndex(currentSize);
        pushUp(currentSize);
        currentSize++;
        if (TESTING) validateAll();
        return true;
    }

    public int size() {
        return currentSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean remove(Object v) {
        if (!objectIndex.containsKey(v)) {
            return false;
        }
        int idx = objectIndex.get(v).iterator().next();
        deleteAt(idx);
        if (TESTING) validateAll();
        return true;
    }

    /************
     * Iterator *
     ************/
    public Iterator<E> iterator() {
        return new Itr();
    }

    private final class Itr implements Iterator<E> {
        private int cursor;

        Itr() {
            cursor = 0;
        }                        // prevent access constructor creation

        public boolean hasNext() {
            return cursor < currentSize;
        }

        public E next() {
            if (cursor < currentSize) return (E) heap[cursor++];
            throw new NoSuchElementException();
        }
    }

    /********************
     * Helper functions	*
     ********************/

    /*
     *	Push the value up, until it's parent is smaller, or it becomes root.
     */
    private void pushUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) >> 1;
            if (smaller(idx, parent)) {
                swap(idx, parent);
            }
            idx = parent;
        }
    }

    /*
     *	Push the value down, until it has no smaller child or becomes leaf.
     */
    private void pushDown(int idx) {
        while (true) {
            int mn = idx;
            int ch1 = (idx << 1) + 1, ch2 = (idx << 1) + 2;
            if (ch1 < currentSize && smaller(ch1, mn)) mn = ch1;
            if (ch2 < currentSize && smaller(ch2, mn)) mn = ch2;
            if (mn == idx) break;
            swap(idx, mn);
            idx = mn;
        }
    }

    /*
     * Compare values at given indexes.
     * Returns true only if the value at index i is strictly less than the value at index j.
     */
    private boolean smaller(int i, int j) {
        boolean smaller;
        if (comparator == null) {
            smaller = ((Comparable<? super E>) heap[i]).compareTo((E) heap[j]) < 0;
        } else {
            smaller = comparator.compare((E) heap[i], (E) heap[j]) < 0;
        }
        return smaller;
    }

    /*
     * Delete value at the given index in the heap.
     * Returns the deleted value.
     */
    private E deleteAt(int idx) {
        if (idx >= currentSize) throw new RuntimeException("Index out of bounds!");
        E valueToReturn = (E) heap[idx];
        currentSize--;
        removeIndex(idx);
        if (idx == currentSize) {
            if (TESTING) validateAll();
            return valueToReturn;
        }
        removeIndex(currentSize);
        heap[idx] = heap[currentSize];
        insertIndex(idx);
        pushDown(idx);
        pushUp(idx);
        if (TESTING) validateAll();
        return valueToReturn;
    }

    /*
     * Swap two indexes (also updates the indexes on the map).
     */
    private void swap(int i, int j) {
        removeIndex(i);
        removeIndex(j);
        E tmp = (E) heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
        insertIndex(i);
        insertIndex(j);
    }

    /*
     * Expand the size of the heap if the current capacity is reached.
     */
    private void expandSize() {
        int nextSize = heapCap << 1;
        heapCap = nextSize;
        heap = Arrays.copyOf(heap, nextSize);
        if (TESTING) validateAll();
    }

    /*
     * Insert the idx to the multiset.
     */
    private void insertIndex(int idx) {
        E data = (E) heap[idx];
        if (!objectIndex.containsKey(data)) {
            objectIndex.put(data, new HashSet<>());
        }
        objectIndex.get(data).add(idx);
    }

    /*
     * Insert the idx from the multiset.
     */
    private void removeIndex(int idx) {
        E data = (E) heap[idx];
        if (!objectIndex.containsKey(data)) return;
        if (objectIndex.get(data).size() == 1) {
            objectIndex.remove(data);
            return;
        }
        objectIndex.get(data).remove(idx);
    }


    /*********************
     * Testing functions *
     *********************/

    /*
     * Validates that the current state of the whole implementation is valid.
     */
    private boolean validateAll() {
        boolean indexes = validateIndexes();
        boolean state = validateHeapState();
        return state && indexes;
    }

    /*
     * Validates that all the indexes are valid in the objectIndex Map.
     */
    private boolean validateIndexes() {
        int cnt = 0;
        for (Object i : objectIndex.keySet()) {
            int sz = objectIndex.get(i).size();
            if (sz == 0) return false;
            cnt += sz;
        }
        if (cnt != currentSize) {
            System.err.println("Map doesn't have similar amount of entries as heap!");
            return false;
        }
        for (int i = 0; i < currentSize; i++) {
            E data = (E) heap[i];
            if (!objectIndex.containsKey(data) || !objectIndex.get(data).contains(i)) {
                System.err.println("Could not find index of object in heap!");
                return false;
            }
        }
        return true;
    }

    /*
     * Checks if all the parents are smaller than the children.
     */
    private boolean validateHeapState() {
        for (int i = 0; i < currentSize; i++) {
            int ch1 = (i << 1) + 1, ch2 = (i << 1) + 2;
            if (ch1 < currentSize && smaller(ch1, i)) {
                System.err.println("Smaller child found error!");
                return false;
            }
            if (ch2 < currentSize && smaller(ch2, i)) {
                System.err.println("Smaller child found error!");
                return false;
            }
        }
        return true;
    }
}
