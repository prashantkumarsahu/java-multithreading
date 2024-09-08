package multithreading.Concurrent_Collections_8;


import java.util.concurrent.ArrayBlockingQueue;


class ProducerA implements Runnable{

    private ArrayBlockingQueue<Integer> prodQueue;

    ProducerA(ArrayBlockingQueue<Integer> queue){
        this.prodQueue = queue;
    }

    @Override
    public void run() {

        try {
            for (int i = 1; i <= 10; i++) {
                prodQueue.put(i);
                System.out.println("Producer put item into queue = " + i);
            }
        }catch (InterruptedException ex){
            System.out.println("Exception in producer.");
        }
    }
}

class ConsumerA implements Runnable{

    private ArrayBlockingQueue<Integer> consQueue;

    ConsumerA(ArrayBlockingQueue<Integer> queue){
        this.consQueue = queue;
    }
    @Override
    public void run() {
        try {
            for (int i = 1; i < 10; i++) {
                System.out.println("Consumer picked item from queue = " + consQueue.take());
            }
        }catch (InterruptedException ex){
            System.out.println("Exception in consumer.");
        }
    }
}

public class ArrayBlockingQueueExample {

    ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    Thread producer = new Thread(new ProducerA(queue));

    Thread consumer = new Thread(new ConsumerA(queue));
}
