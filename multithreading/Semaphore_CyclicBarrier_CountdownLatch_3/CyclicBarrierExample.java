package multithreading.Semaphore_CyclicBarrier_CountdownLatch_3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    //A CyclicBarrier in Java is used when you want multiple threads to wait for each other
    // at a common barrier point before proceeding.
    // Unlike CountDownLatch, which is a one-time use, CyclicBarrier can be reset and reused.
    private static CyclicBarrier cyclicBarrier;

    CyclicBarrierExample(){
        this.cyclicBarrier = new CyclicBarrier(3);
    }

    private static class ExampleClass implements Runnable{

        @Override
        public void run() {

            try {
                System.out.println(Thread.currentThread().getName() + " in exampleClass doing phase 1 work.");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " in exampleClass finished doing phase 1 work.");

                cyclicBarrier.await(); // this acts as a barrier against proceeding forward for all 3 threads.
                // only after all 3 threads are done doing phase 1 work they start with the next statements.

                System.out.println(Thread.currentThread().getName() + " in exampleClass doing phase 2 work.");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " in exampleClass finished doing phase 2 work.");

                cyclicBarrier.await();
                // only after all 3 threads are done doing phase 2 work they start with the next statements.

                System.out.println(Thread.currentThread().getName() + " in exampleClass doing phase 3 work.");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " in exampleClass finished doing phase 3 work.");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public static void main(String[] args) {
        CyclicBarrierExample obj = new CyclicBarrierExample();

        ExampleClass o1 = new ExampleClass();
        ExampleClass o2 = new ExampleClass();
        ExampleClass o3 = new ExampleClass();

        Thread t1 = new Thread(o1); t1.setName("T1");
        Thread t2 = new Thread(o2); t2.setName("T2");
        Thread t3 = new Thread(o3); t3.setName("T3");

        t1.start();
        t2.start();
        t3.start();

    }
}
