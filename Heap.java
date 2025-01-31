// Heap class represents a generic heap data structure that can be used for both min and max heaps.
// It uses an ArrayList to maintain the heap structure and a HashMap to track the indices of elements in the heap.

import java.util.ArrayList;
import java.util.HashMap;

public class Heap<T extends Comparable<T>> {
    // ArrayList to store the elements of the heap.
    ArrayList<T> heapList = new ArrayList<>();

    // HashMap to store the indices of elements in the heap for efficient update operations.
    HashMap<T, Integer> indexMap = new HashMap<>();

    // Adds a new item to the heap and performs up-heapify to maintain the heap property.
    public void add(T item) {
        heapList.add(item);
        indexMap.put(item, this.heapList.size() - 1);
        upHeapify(heapList.size() - 1);
    }

    // Performs up-heapify operation starting from the given index to maintain the heap property.
    private void upHeapify(int currentIndex) {
        int parentIndex = (currentIndex - 1) / 2;
        if (isLarger(heapList.get(currentIndex), heapList.get(parentIndex)) > 0) {
            swap(parentIndex, currentIndex);
            upHeapify(parentIndex);
        }
    }

    // Swaps elements at two given indices in the heap.
    private void swap(int i, int j) {
        T ith = heapList.get(i);
        T jth = heapList.get(j);

        heapList.set(i, jth);
        heapList.set(j, ith);
        indexMap.put(ith, j);
        indexMap.put(jth, i);
    }

    // Displays the elements of the heap.
    public void display() {
        System.out.println(heapList);
    }

    // Returns the size of the heap.
    public int size() {
        return this.heapList.size();
    }

    // Checks if the heap is empty.
    public boolean isEmpty() {
        return this.size() == 0;
    }

    // Removes and returns the root of the heap, then performs down-heapify to maintain the heap property.
    public T remove() {
        swap(0, this.heapList.size() - 1);
        T removedValue = this.heapList.remove(this.heapList.size() - 1);
        downHeapify(0);

        indexMap.remove(removedValue);
        return removedValue;
    }

    // Performs down-heapify operation starting from the given index to maintain the heap property.
    private void downHeapify(int parentIndex) {
        int leftChildIndex = 2 * parentIndex + 1;
        int rightChildIndex = 2 * parentIndex + 2;
        int maxIndex = parentIndex;

        if (leftChildIndex < this.heapList.size() && isLarger(heapList.get(leftChildIndex), heapList.get(maxIndex)) > 0) {
            maxIndex = leftChildIndex;
        }

        if (rightChildIndex < this.heapList.size() && isLarger(heapList.get(rightChildIndex), heapList.get(maxIndex)) > 0) {
            maxIndex = rightChildIndex;
        }

        if (maxIndex != parentIndex) {
            swap(maxIndex, parentIndex);
            downHeapify(maxIndex);
        }
    }

    // Returns the root element of the heap without removing it.
    public T get() {
        return this.heapList.get(0);
    }

    // Compares two elements and returns a positive integer if the first is larger, negative if smaller, and zero if equal.
    public int isLarger(T t, T o) {
        return t.compareTo(o);
    }

    // Updates the priority of an item in the heap and performs up-heapify to maintain the heap property.
    public void updatePriority(T item) {
        int index = indexMap.get(item);
        upHeapify(index);
    }
}
