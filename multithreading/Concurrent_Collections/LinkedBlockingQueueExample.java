package multithreading.Concurrent_Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

class Producer implements Runnable{

    // private static final Logger log = LoggerFactory.getLogger(Producer.class);
    private LinkedBlockingQueue<Integer> prodQueue;

    Producer(LinkedBlockingQueue<Integer> queue){
        this.prodQueue = queue;
    }

    @Override
    public void run() {

        try {
            for (int i = 1; i <= 100; i++) {
               // log.info("Producer pushed into Queue = {}", i );
                this.prodQueue.put(i);
            }
        }catch (Exception ex){
           // log.error("Producer failed to push items into LinkedBlockingQueue");
        }
    }
}

class Consumer implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    private LinkedBlockingQueue<Integer> consQueue;
    Consumer(LinkedBlockingQueue<Integer> queue){
        this.consQueue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 100; i++) {
                log.info("Consumer consumed from Queue = {}", this.consQueue.take());
            }
        }catch (Exception ex){
            log.error("Producer failed to push items into LinkedBlockingQueue");
        }
    }
}

public class LinkedBlockingQueueExample {

    public static void main(String[] args) {

        // will automatically adjust size of queue if items added are more than given capacity
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10); // common queue shared by producer and consumer.

        Thread producer = new Thread(new Producer(queue)); // same queue is passed to both producer and consumer thread.
        Thread consumer = new Thread(new Consumer(queue));

        producer.start();
        consumer.start();
    }
}
