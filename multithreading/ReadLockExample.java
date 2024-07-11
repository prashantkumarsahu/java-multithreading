package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class ReadLockExample {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    Logger logger = Logger.getLogger(ReadLockExample.class.getName());
    private Integer sharedResourceVar = 0;

    public Integer readFromSharedResource(Thread currentThread){
        readLock.lock();
        logger.info("Acquired lock by " + currentThread.getId());
        try{
            return sharedResourceVar;
        }finally {
           readLock.unlock();
           logger.info("Released lock by " + currentThread.getId());
        }
    }

    public static void main(String[] args) throws InterruptedException{

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        ReadLockExample readLockExample = new ReadLockExample();

        for(int i=0;i<10;i++){
            executorService.submit(() ->{
                System.out.println(readLockExample.readFromSharedResource(Thread.currentThread()));
            });
        }


        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.MINUTES);

    }
}
