package multithreading.Semaphore_CyclicBarrier_CountdownLatch_3;

import java.util.concurrent.Semaphore;

class SemaphoreExample implements Runnable{

    private Semaphore semaphore;
    private int id;

    public SemaphoreExample(Semaphore s, int id) {
        this.semaphore = s;
        this.id = id;
    }


    @Override
    public void run() {

        try{
            for (int i = 0; i < 5; i++) {
                this.semaphore.acquire();
                // critical section
                System.out.println(Thread.currentThread().getName());
                this.semaphore.release();
            }
        }catch (InterruptedException ex){
            System.out.println(ex.toString());
        }
    }
}

public class SemaphoreExampleDriver {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);  // Binary semaphore aka Mutex, shared by both threads

        Thread t1 = new Thread(new SemaphoreExample(semaphore,0));
        Thread t2 = new Thread(new SemaphoreExample(semaphore, 1));

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        t2.start();
    }
}
