package multithreading;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

public class ConcurrentHashMapExample {


    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    ConcurrentHashMap<Character, Integer> freqMap = new ConcurrentHashMap();
    Character[] characters = {'a', 'b', 'c', 'a', 'd', 'c'};

    Logger logger = Logger.getLogger(ConcurrentHashMapExample.class.getName());
    private Long sharedResourceVar;

    public Integer readFromSharedResource(Character c){
        readLock.lock();
        logger.info("Acquired lock by " + Thread.currentThread().getId());
        try{
            return freqMap.get(c);
        }finally {
            readLock.unlock();
            logger.info("Released lock by " + Thread.currentThread().getId());
        }
    }

    private void writeToSharedResource(Character c){
        writeLock.lock();
        logger.info("Acquired lock by " + Thread.currentThread().getId());
        try{
            freqMap.put(c, freqMap.getOrDefault(c,0) + 1);
            logger.info("Updated value key = " + c + " value = "  +freqMap.get(c));
        }finally {
            writeLock.unlock();
            logger.info("Released lock by " + Thread.currentThread().getId());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // Refer this blog.
        // https://javabypatel.blogspot.com/2016/09/concurrenthashmap-interview-questions.html
        // ConcurrentHashMap combines good features of hashmap and hashtable and solves performance and thread safety problem nicely.

        Long startTime = System.currentTimeMillis();
        Logger logger = Logger.getLogger(ConcurrentHashMapExample.class.getName());
        ConcurrentHashMapExample concurrentHashMapExample = new ConcurrentHashMapExample();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for(Character c: concurrentHashMapExample.characters){
            executorService.submit(()->{
                concurrentHashMapExample.writeToSharedResource(c);
               // freqMap.put(c, freqMap.getOrDefault(c,0)+1);
              // freqMap.merge(c,1,Integer::sum);
            });
        }

//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.MINUTES);

        for(Character c: concurrentHashMapExample.characters){
            executorService.submit(()->{
                concurrentHashMapExample.readFromSharedResource(c);
                // freqMap.put(c, freqMap.getOrDefault(c,0)+1);
                // freqMap.merge(c,1,Integer::sum);
            });
        }

//        concurrentHashMapExample.freqMap.forEach((key,value) -> {
//            logger.info("Character = " + key + " Count = " + value);
//        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        Long endTime = System.currentTimeMillis();

        logger.info("Time taken = " + (endTime-startTime));

    }
}
