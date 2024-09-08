package multithreading.Semaphore_CyclicBarrier_CountdownLatch_3;

import java.util.concurrent.CountDownLatch;


public class CountdownLatchExample {

    // A CountDownLatch in Java is used to ensure that one or more threads wait until a
    // set of operations being performed in other threads completes.

    private static CountDownLatch firstLatch = null;
    private static CountDownLatch secondLatch = null;

    CountdownLatchExample() {
        firstLatch = new CountDownLatch(1);
        secondLatch = new CountDownLatch(2);
    }

    private static class First implements Runnable{

        @Override
        public void run() {
            System.out.println("First");
            firstLatch.countDown();
            // decrement count of firstLatch by 1;
            // once it reaches 0 all threads waiting on this latch become ready to execute
        }
    }

    private static class Second implements Runnable{

        @Override
        public void run() {
            try {
                firstLatch.await(); // second thread will wait until firstLatch has become 0
                System.out.println("Second");
                secondLatch.countDown(); // decrement count of second latch
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class Third implements Runnable{

        @Override
        public void run() {
            try {
                secondLatch.await(); // third thread is waiting on the secondLatch to become 0
                System.out.println("Third");
                secondLatch.countDown(); // decrement second latch
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) {
        CountdownLatchExample obj = new CountdownLatchExample();

        First o1 = new First();
        Second o2 = new Second();
        Third o3 = new Third();

        Thread t1 = new Thread(o1);
        Thread t2 = new Thread(o2);
        Thread t3 = new Thread(o3);

        t1.start();
        t2.start();
        t3.start();
    }
}
