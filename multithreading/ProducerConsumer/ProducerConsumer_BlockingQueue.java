package multithreading.ProducerConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
    BlockingQueue<Integer> obj;

    public Producer(BlockingQueue<Integer> obj)
    {
        // accept an ArrayBlockingQueue object from constructor
        this.obj = obj;
    }

    @Override
    public void run() {
        // Produce numbers in the range [1,4]
        // and put them in the BQ
        for (int i = 1; i <= 4; i++) {
            try {
                obj.put(i);
                System.out.println("Produced " + i);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {

    BlockingQueue<Integer> obj;
    // -1 indicates that no number has been taken so far.
    int taken = -1;

    public Consumer(BlockingQueue<Integer> obj)
    {
        // accept an ArrayBlockingQueue object from
        // constructor
        this.obj = obj;
    }

    @Override
    public void run() {
        while (taken != 4) {
            try {
                taken = obj.take();
                System.out.println("Consumed " + taken);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ProducerConsumer_BlockingQueue {

    public static void main(String[] args) {

        // create a blocking queue of size 4
        BlockingQueue<Integer> bqueue
                = new ArrayBlockingQueue<Integer>(4);

        // both Producer and Consumer use the same BlockingQueue
        // producer adds item to the BQ
        // consumer removes item from the BQ
        Producer p1 = new Producer(bqueue);
        Consumer c1 = new Consumer(bqueue);

        // Create one thread each for Producer and Consumer.
        Thread pThread = new Thread(p1);
        Thread cThread = new Thread(c1);

        pThread.start();
        cThread.start();
    }

}
