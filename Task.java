/** 
 * Represents a task in the Stardew Simulation. A Task stores 
 * information about its priority, creation hour, waiting time, 
 * task type, and task description. Tasks can be compared based 
 * on their priority and, when priorities are equal, their creation 
 * time. 
 * 
 * @author Jonathan Dunn 
 */
public class Task implements TaskInterface, Comparable<Task> {
    private int priorityLevel;
    private int hourCreated;
    private int waitingTime;
    private String taskDescription;
    private TaskType taskType;

    /** 
     * Constructs a default Task with a priority level, creation hour, 
     * and waiting time of 0. The task type is set to MINING and the 
     * task description is set to null. 
     */
    public Task() {
        this.priorityLevel = 0;
        this.hourCreated = 0;
        this.waitingTime = 0;
        this.taskDescription = null;
        this.taskType = Task.TaskType.MINING;
    }

    /** 
     * Constructs a Task using the specified creation hour, task type, 
     * and task description. The priority level and waiting time are 
     * initialized to 0. 
     * 
     * @param passedHourCreated the simulation hour when the task was created 
     * @param passedType the type of task 
     * @param passedtaskDescription the description of the task 
     */
    public Task(int passedHourCreated, TaskInterface.TaskType passedType, String passedtaskDescription) {
        this.priorityLevel = 0;
        this.waitingTime = 0;
        this.hourCreated = passedHourCreated;
        this.taskType = passedType;
        this.taskDescription = passedtaskDescription;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return this.priorityLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPriority(int priority) {
        this.priorityLevel = priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskInterface.TaskType getTaskType() {
        return this.taskType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTaskDescription() {
        return this.taskDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementWaitingTime() {
        this.waitingTime++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetWaitingTime() {
        this.waitingTime = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWaitingTime() {
        return this.waitingTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Task o) {
        //If same priority, who has been waiting longer?
        if(this.getPriority() == o.getPriority()) {
            return (this.hourCreated - o.hourCreated) * -1;
        }
        else {
            return this.getPriority() - o.getPriority();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public String toString() {
        if (this.getTaskType() == Task.TaskType.MINING) {
            return "MINING " + this.getTaskDescription() + " at Hour: " + this.hourCreated + ":00";
        }
        else if (this.getTaskType() == Task.TaskType.FISHING) {
            return "FISHING " + this.getTaskDescription() + " at Hour: " + this.hourCreated + ":00";
        }
        else if (this.getTaskType() == Task.TaskType.FARM_MAINTENANCE) {
            return "FARM_MAINTENANCE " + this.getTaskDescription() + " at Hour: " + this.hourCreated + ":00";
        }
        else if (this.getTaskType() == Task.TaskType.FEEDING) {
            return "FEEDING " + this.getTaskDescription() + " at Hour: " + this.hourCreated + ":00";
        }
        else if (this.getTaskType() == Task.TaskType.FORAGING) {
            return "FORAGING " + this.getTaskDescription() + " at Hour: " + this.hourCreated + ":00";
        }
        else if (this.getTaskType() == Task.TaskType.SOCIALIZING) {
            return "SOCIALIZING" + this.getTaskDescription() + " at Hour: " + this.hourCreated + ":00";
        }
        else {
            return "nothing to see here...";
        }

    }
    
}
