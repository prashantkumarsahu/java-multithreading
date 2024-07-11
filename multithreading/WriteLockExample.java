package multithreading;

import lombok.extern.java.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class WriteLockExample {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    Logger logger = Logger.getLogger(WriteLockExample.class.getName());

    private Long sharedResource;

    private void writeToSharedResource(Thread currentThread, Long value){
        writeLock.lock();
        logger.info("Acquired lock by " + currentThread.getId());
        try{
            sharedResource = value;
            logger.info("Updated value = " + sharedResource);
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
            logger.info("Released lock by " + currentThread.getId());
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        WriteLockExample writeLockExample = new WriteLockExample();

        for(int i=0;i<10;i++){
            executorService.submit(()->{
                writeLockExample.writeToSharedResource(Thread.currentThread(), Thread.currentThread().getId()%10);
            });
        }

        executorService.shutdown();
    }
}
