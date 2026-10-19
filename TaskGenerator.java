import java.util.Random;
/** 
 * Generates and manages tasks for the Stardew Simulation. 
 * The TaskGenerator is responsible for creating tasks, managing 
 * the current energy storage, determining whether a new task 
 * should be generated, and determining random task outcomes. 
 * Implements the TaskGeneratorInterface.
 * 
 * @author Jonathan Dunn 
 */
public class TaskGenerator implements TaskGeneratorInterface {
    private int totalEnergy;
    private double taskGenerationProbability;
    private long seed;
    private Random rand;

    /** 
     * Constructs a TaskGenerator with the specified probability 
     * of generating a new task. The energy storage is initialized 
     * to the default energy value and a randomly seeded Random 
     * object is created. 
     * 
     * @param taskGenerationProbability the probability that a new 
     *      task will be generated 
     */
    public TaskGenerator(double taskGenerationProbability) {
        this.totalEnergy = DEFAULT_ENERGY;
        this.taskGenerationProbability = taskGenerationProbability;
        rand = new Random();
    }

    /** 
     * Constructs a TaskGenerator with the specified probability 
     * of generating a new task and a seed for the random number 
     * generator. The energy storage is initialized to the default 
     * energy value. 
     * 
     * @param taskGenerationProbability the probability that a new 
     *      task will be generated 
     * @param seed the seed used to initialize the random number generator 
     */
    public TaskGenerator(double taskGenerationProbability, long seed) {
        this.totalEnergy = DEFAULT_ENERGY;
        this.taskGenerationProbability = taskGenerationProbability;
        this.seed = seed;
        rand = new Random(this.seed);
    }
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public void decrementEnergyStorage(Task.TaskType taskType) {
        totalEnergy -= taskType.getEnergyPerHour();
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public Task getNewTask(int hourCreated, TaskInterface.TaskType taskType, String taskDescription) {
        Task newTask = new Task(hourCreated, taskType, taskDescription);

        return newTask;
    }

    /**
     * {@inheritDoc} 
     */    
    @Override
    public void resetCurrentEnergyStorage() {
        this.totalEnergy = DEFAULT_ENERGY;
    }

    /**
     * {@inheritDoc} 
     */    
    @Override
    public int getCurrentEnergyStorage() {
        return this.totalEnergy;
    }

    /**
     * {@inheritDoc} 
     */    
    @Override
    public void setCurrentEnergyStorage(int newEnergyNum) {
        this.totalEnergy = newEnergyNum;
    }

    /**
     * {@inheritDoc} 
     */    
    @Override
    public boolean generateTask() {
        double generateCheck = rand.nextDouble();
        return generateCheck <= taskGenerationProbability;
    }

    /**
     * {@inheritDoc} 
     */    
    @Override
    public int getUnlucky(Task task, double unluckyProbability) {
        double taskPassoutProb = task.getTaskType().getPassingOutProbability();
        double taskDeathProb = task.getTaskType().getDyingProbability();

        if (unluckyProbability <= taskPassoutProb) {
            if (unluckyProbability <= taskDeathProb) {
                this.totalEnergy = (this.totalEnergy - (int)(this.totalEnergy * 0.75));
                task.setPriority(0);
                return DEATH;
            }
            else {
                this.totalEnergy = this.totalEnergy / 2;
                return PASSED_OUT;
            }
        }
        else {
            return SURVIVED;
        }
    }

    /**
     * {@inheritDoc} 
     */    
    @Override
    public String toString(Task task, Task.TaskType taskType) {
        if(taskType == Task.TaskType.MINING) {
            return "     Mining " + task.getTaskDescription() + " at " 
                + totalEnergy + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.FISHING) {
            return "     Fishing " + task.getTaskDescription() + " at " 
                + totalEnergy + " energy points (Priority:" + task.getPriority() +")" ;
        }
        if(taskType == Task.TaskType.FARM_MAINTENANCE) {
            return "     Farm Maintenance " + task.getTaskDescription() + " at " 
                + totalEnergy + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.FORAGING) {
            return "     Foraging " + task.getTaskDescription() + " at " 
                + totalEnergy + " energy points (Priority:" + task.getPriority() +")" ;
        }
        if(taskType == Task.TaskType.FEEDING) {
            return "     Feeding " + task.getTaskDescription() + " at " 
                + totalEnergy + " energy points (Priority:" + task.getPriority() +")";
        }
        if(taskType == Task.TaskType.SOCIALIZING) {
            return "     Socializing " + task.getTaskDescription() + " at " 
                + totalEnergy + " energy points (Priority:" + task.getPriority() +")";
        }
        else { return "nothing to see here..."; }

    }  
}
