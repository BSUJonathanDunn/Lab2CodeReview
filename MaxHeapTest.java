import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/** 
 * Tests the functionality and behavior of the MaxHeap class. 
 * Tests include heap construction, insertion, extraction, 
 * key increases, heap expansion, empty heap behavior, invalid 
 * input, and support for different Comparable data types. 
 */
public class MaxHeapTest {
    private Task[] testTasks;
    private MaxHeap<Task> testHeap;

    /** 
     * Determines whether the provided heap satisfies the max heap property. 
     * Each element in the heap must be less than or equal to its parent. 
     * 
     * @param heapArray the heap to check 
     * @return true if the heap satisfies the max heap property; 
     *      false otherwise 
     */
    public boolean checkIfMaxHeap(MaxHeap<Task> heapArray) {
        if (heapArray.getHeapSize() < 2) {
            return true;
        }

        for (int i = 1; i < heapArray.getHeapSize(); i++) {
            int parentIndex = heapArray.parent(i);

            if (heapArray.getHeapElement(parentIndex)
                    .compareTo(heapArray.getHeapElement(i)) < 0) {
                return false;
            }
        }

        return true;
    }

    /** 
     * Determines whether the provided array is sorted in descending 
     * order according to the natural ordering of its elements. 
     * 
     * @param userArray the array to check 
     * @param <T> the type of elements in the array 
     * @return true if the array is sorted in descending order; 
     *      false otherwise 
     */
    public <T extends Comparable<T>> boolean checkIfReverseSorted(T[] userArray) {
        int arraySize = userArray.length;
        if (arraySize < 2) {
            return true;
        }
        for (int i = 1; i < arraySize; i++) {
            if (userArray[i - 1].compareTo(userArray[i]) < 0) {
                return false;
            }
        }
        return true;
    }

    /** 
     * Tests constructing a MaxHeap from an existing array and verifies 
     * that the resulting heap satisfies the max heap property and that 
     * extracting all elements produces a descending sequence. 
     */
    @Test 
    void arrayToHeapTest() {
        Task[] filledArray = new Task[5];
        Integer[] testArray;

        Task taskA = new Task();
        Task taskB = new Task();
        Task taskC = new Task();
        Task taskD = new Task();
        Task taskE = new Task();

        taskA.setPriority(4);
        taskB.setPriority(2);
        taskC.setPriority(1);
        taskD.setPriority(5);
        taskE.setPriority(3);

        filledArray[0] = taskA;
        filledArray[1] = taskB;
        filledArray[2] = taskC;
        filledArray[3] = taskD;
        filledArray[4] = taskE;

        try {
            testHeap = new MaxHeap<Task>(Task.class, filledArray);
        }
        catch (HeapException e) {
            fail("HeapException thrown upon MaxHeap construction");
        }

        assertTrue(checkIfMaxHeap(testHeap));

        testArray = new Integer[testHeap.getHeapSize()];
        try {
            for (int i = 0; i < testArray.length; i++) {
                testArray[i] = (testHeap.extractMax()).getPriority();
            }
        }
        catch (HeapException e) {
            fail("HeapException thrown during extractMax");
        }

        assertTrue(checkIfReverseSorted(testArray));
    }

    /** 
     * Tests that the heap automatically increases its capacity when 
     * the number of inserted elements exceeds its initial capacity. 
     */
    @Test
    void heapExpansionTest() {
        testHeap = new MaxHeap<Task>();

        try {
            for (int i = 0; i < 21; i++) { 
                Task task = new Task();
                task.setPriority(i);
                testHeap.insert(task);
            }
        }
        
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        assertEquals(21, testHeap.getHeapSize());
        assertEquals(40, testHeap.getHeapCapacity());
        assertTrue(checkIfMaxHeap(testHeap));
    }

    /** 
     * Tests that a heap is correctly identified as empty when it is 
     * constructed without elements or contains only null elements. 
     */
    @Test
    void emptyTestTrue() {

        testTasks = new Task[10];
        MaxHeap<Task> testHeap;
        try {
            testHeap = new MaxHeap<Task>(Task.class, testTasks);
            assertTrue(testHeap.isEmpty());
        }
        catch (HeapException e) {
            fail("HeapException thrown upon MaxHeap construction");
        }

        testTasks = new Task[0];  
        assertThrows(HeapException.class, () -> {
            MaxHeap<Task> testHeapFail = new MaxHeap<Task>(Task.class, testTasks);
        });
 
        testTasks = new Task[100];
        try {
            testHeap = new MaxHeap<Task>(Task.class, testTasks);
            assertTrue(testHeap.isEmpty());
        }
        catch (HeapException e) {
            fail("HeapException thrown upon MaxHeap construction");
        }

        testHeap = new MaxHeap<Task>();
        assertTrue(testHeap.isEmpty());
    }

    /** 
     * Tests that a heap containing an element is correctly identified 
     * as non-empty. 
     */
    @Test
    void emptyTestFalse() {
        Task taskOne = new Task();
        Task[] testTasks = new Task[]{taskOne};
        try {
            testHeap = new MaxHeap<Task>(Task.class, testTasks);
            assertFalse(testHeap.isEmpty());
        }
        catch (HeapException e) {
            fail("HeapException thrown upon MaxHeap construction");
        }
    }

    /** 
     * Tests inserting multiple elements into a heap and verifies that 
     * the element with the greatest priority is returned by max(). 
     */
    @Test
    void insertTest() {
        testHeap = new MaxHeap<Task>();

        Task taskOne = new Task();
        Task taskTwo = new Task();

        taskOne.setPriority(10);
        taskTwo.setPriority(20);

        try {
            testHeap.insert(taskOne);
            testHeap.insert(taskTwo);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        assertEquals(2, testHeap.getHeapSize());

        try {
            assertEquals(taskTwo, testHeap.max());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }
    }

    /** 
     * Tests extracting all elements from a two-element heap and verifies 
     * that elements are returned in descending priority order. 
     */
    @Test 
    void extractMaxOneTest() {
        testHeap = new MaxHeap<Task>();

        Task taskOne = new Task();
        Task taskTwo = new Task();

        taskOne.setPriority(10);
        taskTwo.setPriority(20);

        try {
            testHeap.insert(taskOne);
            testHeap.insert(taskTwo);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        assertEquals(2, testHeap.getHeapSize());

        try {
            assertEquals(taskTwo, testHeap.extractMax());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during extractMax");
        }

        assertEquals(1, testHeap.getHeapSize());

        try {
            assertEquals(taskOne, testHeap.extractMax());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during extractMax");
        }

        assertEquals(0, testHeap.getHeapSize());
    }

    /** 
     * Tests extracting all elements from a three-element heap and verifies 
     * that elements are returned in descending priority order. 
     */
    @Test 
    void extractMaxTwoTest() {
        testHeap = new MaxHeap<Task>();

        Task taskOne = new Task();
        Task taskTwo = new Task();
        Task taskThree = new Task();

        taskOne.setPriority(45);
        taskTwo.setPriority(10);
        taskThree.setPriority(30);

        try {
            testHeap.insert(taskOne);
            testHeap.insert(taskTwo);
            testHeap.insert(taskThree);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        assertEquals(3, testHeap.getHeapSize());

        try {
            assertEquals(taskOne, testHeap.extractMax());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during extractMax");
        }

        assertEquals(2, testHeap.getHeapSize());

        try {
            assertEquals(taskThree, testHeap.extractMax());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during extractMax");
        }

        assertEquals(1, testHeap.getHeapSize());

        try {
            assertEquals(taskTwo, testHeap.extractMax());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during extractMax");
        }

        assertEquals(0, testHeap.getHeapSize());
    }

    /** 
     * Tests increasing the key of an element and verifies that the element 
     * moves to the root when its new priority becomes the greatest. 
     */
    @Test 
    void increaseKeyOneTest() {
        testHeap = new MaxHeap<Task>();

        Task taskOne = new Task();
        Task taskTwo = new Task();

        taskOne.setPriority(45);
        taskTwo.setPriority(10);

        try {
            testHeap.insert(taskOne);
            testHeap.insert(taskTwo);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        try {
            assertEquals(taskOne, testHeap.max());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during extractMax");
        }

        try {
            taskTwo.setPriority(55);
            testHeap.increaseKey(1, taskTwo);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during increaseKey");
        }

        try {
            assertEquals(taskTwo, testHeap.max());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during max()");
        }

        assertEquals(2, testHeap.getHeapSize());
    }

    /** 
     * Tests increasing the key of an element and verifies that the max heap 
     * property is restored after the element moves toward the root. 
     */
    @Test 
    void increaseKeyTwoTest() {
        testHeap = new MaxHeap<Task>();

        Task taskOne = new Task();
        Task taskTwo = new Task();
        Task taskThree = new Task();

        taskOne.setPriority(40);
        taskTwo.setPriority(50);
        taskThree.setPriority(30);

        try {
            testHeap.insert(taskOne);
            testHeap.insert(taskTwo);
            testHeap.insert(taskThree);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        try {
            assertEquals(taskTwo, testHeap.max());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during extractMax");
        }

        try {
            taskThree.setPriority(60);
            testHeap.increaseKey(2, taskThree);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during increaseKey");
        }

        assertTrue(checkIfMaxHeap(testHeap));

        try{
            assertEquals(taskThree, testHeap.max());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during max()");
        }
        assertEquals(3, testHeap.getHeapSize());
    }

    /** 
     * Tests inserting elements in ascending priority order and verifies 
     * that the heap maintains the max heap property and extracts elements 
     * in descending order. 
     */
    @Test 
    void insertAscendingTest() {
        testHeap = new MaxHeap<Task>();
        Integer[] testArray;

        Task taskOne = new Task();
        Task taskTwo = new Task();
        Task taskThree = new Task();
        Task taskFour = new Task();
        Task taskFive = new Task();

        taskOne.setPriority(1);
        taskTwo.setPriority(2);
        taskThree.setPriority(3);
        taskFour.setPriority(4);
        taskFive.setPriority(5);

        try{
            testHeap.insert(taskOne);
            testHeap.insert(taskTwo);
            testHeap.insert(taskThree);
            testHeap.insert(taskFour);
            testHeap.insert(taskFive);
        }
        catch (HeapException e) {
            fail("HeapException threw exception during insert");
        }

        assertTrue(checkIfMaxHeap(testHeap));

        testArray = new Integer[testHeap.getHeapSize()];
       try {
            for (int i = 0; i < testArray.length; i++) {
                testArray[i] = (testHeap.extractMax()).getPriority();
            }
        }
        catch (HeapException e) {
            fail("HeapException thrown during extractMax");
        }

        assertTrue(checkIfReverseSorted(testArray));
    }

    /** 
     * Tests inserting elements in descending priority order and verifies 
     * that the heap maintains the max heap property and extracts elements 
     * in descending order. 
     */
    @Test 
    void insertDescendingTest() {
        testHeap = new MaxHeap<Task>();
        Integer[] testArray;

        Task taskA = new Task();
        Task taskB = new Task();
        Task taskC = new Task();
        Task taskD = new Task();
        Task taskE = new Task();

        taskA.setPriority(5);
        taskB.setPriority(4);
        taskC.setPriority(3);
        taskD.setPriority(2);
        taskE.setPriority(1);

        try{
            testHeap.insert(taskA);
            testHeap.insert(taskB);
            testHeap.insert(taskC);
            testHeap.insert(taskD);
            testHeap.insert(taskE);
        }
        catch (HeapException e) {
            fail("HeapException threw exception during insert");
        }

        assertTrue(checkIfMaxHeap(testHeap));

        testArray = new Integer[testHeap.getHeapSize()];
       try {
            for (int i = 0; i < testArray.length; i++) {
                testArray[i] = (testHeap.extractMax()).getPriority();
            }
        }
        catch (HeapException e) {
            fail("HeapException thrown during extractMax");
        }

        assertTrue(checkIfReverseSorted(testArray));
    }

    /** 
     * Tests inserting elements in a non-sequential order and verifies 
     * that the heap maintains the max heap property and extracts elements 
     * in descending order. 
     */
    @Test 
    void insertRandomTest() {
        testHeap = new MaxHeap<Task>();
        Integer[] testArray;

        Task taskA = new Task();
        Task taskB = new Task();
        Task taskC = new Task();
        Task taskD = new Task();
        Task taskE = new Task();

        taskA.setPriority(4);
        taskB.setPriority(2);
        taskC.setPriority(1);
        taskD.setPriority(5);
        taskE.setPriority(3);

        try{
            testHeap.insert(taskA);
            testHeap.insert(taskB);
            testHeap.insert(taskC);
            testHeap.insert(taskD);
            testHeap.insert(taskE);
        }
        catch (HeapException e) {
            fail("HeapException threw exception during insert");
        }

        assertTrue(checkIfMaxHeap(testHeap));

        testArray = new Integer[testHeap.getHeapSize()];
       try {
            for (int i = 0; i < testArray.length; i++) {
                testArray[i] = (testHeap.extractMax()).getPriority();
            }
        }
        catch (HeapException e) {
            fail("HeapException thrown during extractMax");
        }

        assertTrue(checkIfReverseSorted(testArray));
    }

    /** 
     * Tests that inserting a null element throws a HeapException and 
     * does not increase the size of the heap. 
     */
    @Test
    public void insertNullTest() {
        MaxHeap<Integer> heap = new MaxHeap<>();

        assertThrows(HeapException.class, () -> {
            heap.insert(null);
        });

        assertEquals(0, heap.getHeapSize());
    }

    /** 
     * Tests that attempting to retrieve the maximum element from an 
     * empty heap throws a HeapException. 
     */
    @Test
    void maxEmptyTest() {
        testHeap = new MaxHeap<Task>();

        assertThrows(HeapException.class, () -> {
            testHeap.max();
        });
    }

    /** 
     * Tests that attempting to extract the maximum element from an 
     * empty heap throws a HeapException. 
     */
    @Test
    void extractMaxEmptyTest() {
        testHeap = new MaxHeap<Task>();

        assertThrows(HeapException.class, () -> {
            testHeap.extractMax();
        });
    }

    /** 
     * Tests that increaseKey throws a HeapException when the new 
     * element has a lower priority than the current element. 
     */
    @Test
    void increaseKeyDecreaseTest() {
        testHeap = new MaxHeap<Task>();

        Task taskOne = new Task();
        Task taskTwo = new Task();
        Task lowerTask = new Task();

        taskOne.setPriority(45);
        taskTwo.setPriority(30);
        lowerTask.setPriority(20);

        try {
            testHeap.insert(taskOne);
            testHeap.insert(taskTwo);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        assertThrows(HeapException.class, () -> {
            testHeap.increaseKey(1, lowerTask);
        });
    }

    /** 
     * Tests that increaseKey throws an ArrayIndexOutOfBoundsException 
     * when provided with a negative index. 
     */
    @Test
    void increaseKeyNegativeIndexTest() {
        testHeap = new MaxHeap<Task>();

        Task task = new Task();
        task.setPriority(10);

        try {
            testHeap.insert(task);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testHeap.increaseKey(-1, task);
        });
    }

    /** 
     * Tests that increaseKey throws an ArrayIndexOutOfBoundsException 
     * when the provided index is outside the current heap size. 
     */
    @Test
    void increaseKeyPastHeapTest() {
        testHeap = new MaxHeap<Task>();

        Task task = new Task();
        task.setPriority(10);

        try {
            testHeap.insert(task);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            testHeap.increaseKey(1, task);
        });
    }

    /** 
     * Tests that MaxHeap supports Integer elements and correctly 
     * maintains and extracts elements according to their natural ordering. 
     */
    @Test
    void integerHeapTest() {
        MaxHeap<Integer> heap = new MaxHeap<>();

        try {
            heap.insert(10);
            heap.insert(50);
            heap.insert(30);
            heap.insert(20);
            heap.insert(40);
        }
        catch (HeapException e) {
            fail("HeapException was thrown during insert");
        }

        try {
            assertEquals(50, heap.max());
            assertEquals(50, heap.extractMax());
            assertEquals(40, heap.extractMax());
            assertEquals(30, heap.extractMax());
            assertEquals(20, heap.extractMax());
            assertEquals(10, heap.extractMax());
        }
        catch (HeapException e) {
            fail("HeapException was thrown during heap operations");
        }

        assertTrue(heap.isEmpty());
    }
}

