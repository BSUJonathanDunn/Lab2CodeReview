/** 
 * A priority queue implementation that uses a MaxHeap to store 
 * and manage Task objects. Tasks are maintained according to their 
 * priority, with the highest-priority task positioned at the root 
 * of the underlying heap. 
 * 
 * The priority queue provides operations for adding and removing 
 * tasks, checking whether the queue is empty, and updating task 
 * priorities based on waiting time. 
 * 
 * @author Jonathan Dunn
*/
public class MyPriorityQueue extends MaxHeap<Task> implements PriorityQueueInterface {

    /**
     * {@inheritDoc}
     */
    @Override
    public void enqueue(Task task) {
        try {
            this.insert(task);
        }
        catch(HeapException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Task dequeue() {
        Task extractedTask = null;
        try {
            extractedTask = this.extractMax();
        }
        catch (HeapException e) {
            System.out.println(e.getMessage());
        }

        return extractedTask;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(int timeToIncrementPriority, int maxPriority) {
        Task currentTask;
        try {
            for (int i = 0; i < this.getHeapSize(); i++) {
                currentTask = getHeapElement(i);
                currentTask.incrementWaitingTime();

                if (currentTask.getWaitingTime() >= timeToIncrementPriority
                        && currentTask.getPriority() < maxPriority) {

                    currentTask.setPriority(currentTask.getPriority() + 1);
                    currentTask.resetWaitingTime();

                    increaseKey(i, currentTask);
                }
            }
        }
        catch (HeapException e) {
            System.out.println(e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
    
