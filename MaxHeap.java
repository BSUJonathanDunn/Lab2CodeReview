import java.lang.reflect.Array;
/** 
 * A generic max heap that stores elements in an array-based 
 * binary heap structure. Elements are ordered according to 
 * their natural ordering through the Comparable interface. 
 * The element with the greatest value is placed at the root 
 * of the heap. 
 * 
 * @param <T> the type of elements stored in the heap 
 * @author Jonathan Dunn
 */
public class MaxHeap<T extends Comparable<T>> {
    private T[] heap;
    private int heapCapacity;
    private int heapSize;

    /**
     * Constructs empty heap with a capacity of 10.
     */
    @SuppressWarnings("unchecked")
    public MaxHeap() {
        heap = (T[]) new Comparable[10];
        heapCapacity = heap.length;
        heapSize = 0;
    }

    /**
     * Constructs a heap with the same capacity of the
     * passed array, populated with array's elements,
     * and ordered according to max heap properties.
     * 
     * @param arrayClass class type of the elements in the array.
     * @param passedArray array passed to constructor of any comparable element
     * @throws HeapException thrown if array passed has length of less than 1.
     */
    @SuppressWarnings("unchecked")
    public MaxHeap(Class<T> arrayClass, T[] passedArray) throws HeapException{
        heap = (T[]) Array.newInstance(arrayClass, passedArray.length);
        heapCapacity = passedArray.length;

        if(heapCapacity <= 0) {
            throw new HeapException("Heap array must have a length of at least 1.");
        }

        for (int i = 0; i < heapCapacity; i++) {
            heap[i] = passedArray[i];
        }

        for (int i = 0; i < heap.length; i++) {
            if (heap[i] != null) {
                heapSize++;
            }
        }

        buildMaxHeap();
    }

    /**
     * Gives parent of current element at given index.
     * 
     * @param index index of current element
     * @return index of parent to current element
     */
    public int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Find left child of current element at given index
     * 
     * @param index index of current element
     * @return index of the left child
     */
    public int left(int index) {
        return (2 * index) + 1;
    }

    /**
     * Find right child of current element at given index
     * 
     * @param index index of current element
     * @return index of right child
     */
    public int right(int index) {
        return (2 * index) + 2;
    }

    /**
     * Gives element in heap at given index
     * 
     * @param index index of desired element
     * @return element at given index
     * @throws ArrayIndexOutOfBoundsException thrown if attempting to
     *      to access heap at an illegal index.
     */
    public T getHeapElement(int index) throws ArrayIndexOutOfBoundsException{
        if(index < 0 || index >= this.heapSize) {
            throw new ArrayIndexOutOfBoundsException("Index accessed outside of Array range");
        }
        return this.heap[index];
    }

    /**
     * Helper method to get heap's capacity.
     * 
     * @return integer representing full capacity of the heap.
     */
    public int getHeapCapacity() {
        return this.heapCapacity;
    }

    /**
     * Helper method to get current size of heap.
     * 
     * @return integer representing current size of heap, not the
     *      same as the heap's capacity.
     */
    public int getHeapSize() {
        return this.heapSize;
    }

    /**
     * Increases size of heap by 1.
     */
    private void incrementHeapSize() {
        this.heapSize++;
    }

    /**
     * Decreses size of heap by 1.
     */
    private void decrementHeapSize() {
        this.heapSize--;
    }

    /**
     * Insures heap satisfies max heap properties beginning at given index.
     * 
     * @param index user given index to start insuring the heap keeps
     *      the max heap properties.
     */
    private void heapify(int index) {
        int leftIndex = left(index);
        int rightIndex = right(index);
        int largestIndex;

        if (leftIndex < this.heapSize && (this.heap[leftIndex].compareTo(this.heap[index]) > 0)) {
            largestIndex = leftIndex;
        }
        else {
            largestIndex = index;
        }

        if (rightIndex < this.heapSize && (this.heap[rightIndex].compareTo(this.heap[largestIndex]) > 0)) {
            largestIndex = rightIndex;
        }

        if (largestIndex != index) {
            T storeElement = this.heap[index];
            this.heap[index] = this.heap[largestIndex];
            this.heap[largestIndex] = storeElement;
            heapify(largestIndex);
        }
    }

    /**
     * Builds heap as a max heap using heapify to insure it
     * keeps to max heap properties.
     */
    private void buildMaxHeap() {
        int currentSize = this.heapSize;

        //starts at currentSize/2 -1 because anything above that should be a leaf.
        for (int i = (currentSize / 2) - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * Doubles capacity of the heap by making a new heap
     * with twice the heap capacity and copies elements of
     * current heap into new heap. Old heap overwritten with
     * new larger heap.
     * 
     * @throws HeapException thrown if heap as a capacity of 0
     *      or less.
     */
    @SuppressWarnings("unchecked") 
    private void doubleHeapSize() throws HeapException{
        if (this.heapCapacity <= 0) {
            throw new HeapException("Array must have capacity of at least 1.");
        }

        Class<?> arrayClass = this.heap.getClass().getComponentType();
        T[] tempHeap = (T[]) Array.newInstance(arrayClass, this.heapCapacity * 2);

        for (int i = 0; i < this.heapSize; i++) {
            tempHeap[i] = this.heap[i];
        }

        this.heap = tempHeap;
        this.heapCapacity = this.heap.length;
    }

    /**
     * Get's root of max heap.
     * 
     * @return element at root of the max heap
     * @throws HeapException thrown if heap has size of 0,
     *      no elements to return.
     */
    public T max() throws HeapException{
        if (this.heapSize < 1) {
            throw new HeapException("Heap Underflow");
        }
        return this.heap[0];
    }

    /**
     * Get's root of max heap and removes it from heap.
     * Re-heapify to make sure heap keeps max heap priorities.
     * 
     * @return element previously at the root of the heap
     * @throws HeapException thrown at max() if heapsize is less
     *      then 1.
     */
    public T extractMax() throws HeapException{
        T largestPriority = max();
        this.heap[0] = this.heap[this.heapSize - 1];
        decrementHeapSize();
        heapify(0);
        return largestPriority;
    }

    /**
     * Checks to see if new element has a larger key/value
     * than element at given index. If so, replaces element at
     * given index and restores max heap properties with new element.
     * 
     * @param currentIndex index of element to be replaced
     * @param newElement element to be compared to element at given index
     * @throws HeapException thrown if new element does not contain value/key higher than
     *      element at current index.
     * @throws ArrayIndexOutOfBoundsException thrown if given index is outside range of
     *      the heap size.
     */
    public void increaseKey(int currentIndex, T newElement) throws HeapException, ArrayIndexOutOfBoundsException {
        if (currentIndex < 0 || currentIndex >= this.heapSize) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (newElement.compareTo(this.heap[currentIndex]) < 0) {
            throw new HeapException("New key is smaller than current key");
        }

        //new element's key/value is larger than element currently
        //in heap, in practice new element is copy of old element with
        //different key.
        this.heap[currentIndex] = newElement;

        int i = currentIndex;

        while (i > 0 && this.heap[i].compareTo(this.heap[parent(i)]) > 0) {
            T storeElement = this.heap[i];
            this.heap[i] = this.heap[parent(i)];
            this.heap[parent(i)] = storeElement;
            i = parent(i);
        }
    }

    /**
     * Inserts new element into heap. If heap is full,
     * doubles heap size to make space for new element.
     * Calls increasekey to restory max heap properties.
     * 
     * @param elementToInsert new element to be placed in heap.
     * @throws HeapException thrown if element is null.
     */
    public void insert(T elementToInsert) throws HeapException{
        if (elementToInsert == null) {
            throw new HeapException("Cannot insert a null value");
        }

        if (this.heapSize == this.heapCapacity) {
            doubleHeapSize();
        }

        incrementHeapSize();

        this.heap[this.heapSize - 1] = elementToInsert;
        increaseKey(this.heapSize - 1, elementToInsert);
    }

    /**
     * Checks if heap has no elements
     * 
     * @return boolean true if empty, false if elements
     *      exist within the heap
     */
    public boolean isEmpty() {
        return this.heapSize == 0;
    }
}
