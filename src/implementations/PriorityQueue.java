import java.util.*;

public class PriorityQueue<E extends Comparable<E>> {

	private static final boolean TESTING = false;
	private int heapCap;
	private int currentSize;
	private HashMap<E, HashSet<Integer>> objectIndex;
	private E[] heap;
	
	public PriorityQueue() {
		objectIndex = new HashMap<>();
		heapCap = 8;
		currentSize = 0;
		heap = (E[]) new Comparable[heapCap];
		if(TESTING) validateAll();
	}
	
	public E poll() {
		if(currentSize == 0) throw new RuntimeException("empty!");
		E v = getMin();
		currentSize--;
		removeIndex(currentSize);
		removeIndex(0);
		heap[0] = heap[currentSize];
		insertIndex(0);
		pushDown(0);
		if(TESTING) validateAll();
		return v;
	}
	
	public void add(E v) {
		if(currentSize >= heapCap) expandSize();
		heap[currentSize] = v;
		insertIndex(currentSize);
		pushUp(currentSize);
		currentSize++;
		if(TESTING) validateAll();
	}
	
	public E getMin() {
		return heap[0];
	}
	
	public int size() {
		return currentSize;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public void remove(E v) {
		if(!objectIndex.containsKey(v)) {
			throw new RuntimeException("Could not find the given value!");
		}
		int idx = objectIndex.get(v).iterator().next();
		deleteAt(idx);
		if(TESTING) validateAll();
	}
	
	
	/********************
	 * Helper functions	*
	 ********************/
	
	/*
	 *	Push the value up, until it's parent is smaller, or it becomes root.
	 */
	private void pushUp(int idx) {
		while(idx>0) {
			int parent = (idx-1)>>1;
			if(smaller(idx, parent)) {
				swap(idx, parent);
			}
			idx = parent;
		}
	}
	
	/*
	 *	Push the value down, until it has no smaller child or becomes leaf.
	 */
	private void pushDown(int idx) {
		while(true) {
			int mn = idx;
			int ch1 = (idx<<1) + 1, ch2 = (idx<<1) + 2;
			if(ch1<currentSize && smaller(ch1, mn)) mn = ch1;
			if(ch2<currentSize && smaller(ch2, mn)) mn = ch2;
			if(mn == idx) break;
			swap(idx, mn);
			idx = mn;
		}
	}
	
	/*
	 * Compare values at given indexes. 
	 * Returns true only if the value at index i is strictly less than the value at index j.
	 */
	private boolean smaller(int i, int j) {
		return heap[i].compareTo(heap[j]) < 0;
	}
	
	/*
	 * Delete value at the given index in the heap.
	 * Returns the deleted value.
	 */
	public E deleteAt(int idx) {
		if(idx >= currentSize) throw new RuntimeException("Index out of bounds!");
		E valueToReturn = heap[idx];
		currentSize--;
		removeIndex(idx);
		if(idx == currentSize) {
			if(TESTING) validateAll();
			return valueToReturn;
		}
		removeIndex(currentSize);
		heap[idx] = heap[currentSize];
		insertIndex(idx);
		pushDown(idx);
		pushUp(idx);
		if(TESTING) validateAll();
		return valueToReturn;
	}
	
	/*
	 * Swap two indexes (also updates the indexes on the map).
	 */
	private void swap(int i, int j) {
		removeIndex(i);
		removeIndex(j);
		E tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
		insertIndex(i);
		insertIndex(j);
	}
	
	/*
	 * Expand the size of the heap if the current capacity is reached.
	 */
	private void expandSize() {
		int nextSize = heapCap<<1;
		E[] newArray = (E[]) new Comparable[nextSize];
		for(int i = 0; i<heapCap; i++) newArray[i] = heap[i];
		heapCap = nextSize;
		heap = newArray;
		if(TESTING) validateAll();
	}
	
	/*
	 * Insert the idx to the multiset.
	 */
	private void insertIndex(int idx) {
		E data = heap[idx];
		if(!objectIndex.containsKey(data)) {
			objectIndex.put(data, new HashSet<>());
		}
		objectIndex.get(data).add(idx);
	}

	/*
	 * Insert the idx from the multiset.
	 */
	private void removeIndex(int idx) {
		E data = heap[idx];
		if(!objectIndex.containsKey(data)) return;
		if(objectIndex.get(data).size() == 1) {
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
	public boolean validateAll() {
		boolean indexes = validateIndexes();
		boolean state = validateHeapState();
		return state && indexes;
	}
	
	/*
	 * Validates that all the indexes are valid in the objectIndex Map.
	 */
	public boolean validateIndexes() {
		int cnt = 0;
		for(E i : objectIndex.keySet()) {
			int sz = objectIndex.get(i).size();
			if(sz ==0) return false;
			cnt += sz;
		}
		if(cnt != currentSize) {
			System.err.println("Map doesn't have similar amount of entries as heap!");
			return false;
		}
		for(int i = 0; i<currentSize; i++) {
			E data = heap[i];
			if(!objectIndex.containsKey(data) || !objectIndex.get(data).contains(i)) {
				System.err.println("Could not find index of object in heap!");
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Checks if all the parents are smaller than the children.
	 */
	public boolean validateHeapState() {
		for(int i = 0; i<currentSize; i++) {
			int ch1 = (i<<1) + 1, ch2 = (i<<1) + 2;
			if(ch1<currentSize && smaller(ch1, i)) {
				System.err.println("Smaller child found error!");
				return false;
			}
			if(ch2<currentSize && smaller(ch2, i)) {
				System.err.println("Smaller child found error!");
				return false;
			}
		}
		return true;
	}
}
