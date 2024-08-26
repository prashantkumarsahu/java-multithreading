package multithreading.Concurrent_Collections;

import java.util.concurrent.PriorityBlockingQueue;

class Task implements Comparable<Task>{

    private int priority;
    private String name;


    public Task(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Task{" +
                "priority=" + priority +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Task task2) {
        return Integer.compare(this.priority, task2.priority); // (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

}

public class PriorityBlockingQueueExample {

    public static void main(String[] args) {

        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        // queue has 3 diff priority tasks
        queue.put(new Task(3,"low"));
        queue.put(new Task(2,"medium"));
        queue.put(new Task(1,"high"));


        Runnable runnable = () -> {
            try{
                while(true){
                    Task task = queue.take(); // queue.take() will pick the highest priority task first,
                                             // even though it is added at last in queue, violating the FIFO principle.
                    System.out.println(task.getName() + task.getPriority());
                    Thread.sleep(100);
                }
            }catch (InterruptedException ex){
                System.out.println(ex.toString());
            }
        };

        Thread worker = new Thread(runnable);
        worker.start();
    }
}
