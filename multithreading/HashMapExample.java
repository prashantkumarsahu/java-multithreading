package multithreading;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class HashMapExample {

    public static void main(String[] args) throws InterruptedException {

        HashMap<Character, Integer> freqMap = new HashMap<>();

        Long startTime = System.currentTimeMillis();
        Logger logger = Logger.getLogger(HashMapExample.class.getName());

        Character[] characters = {'a', 'b', 'c', 'a', 'd', 'c'};

        // create hashmap of char and count;

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for(Character c: characters){
            executorService.submit(()-> {
                freqMap.put(c, freqMap.getOrDefault(c,0)+1);
                // this hashmap is a shared resource between 3 threads;
                // HashMap is not synchronized, so T2 can modify freqMap while T1 is also modifying freqMap;
                // this can result into inconsistent updates of freqMap
                // because HashMap not synchronized, it is faster than HashTable
            });
        }

        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.MINUTES);

        freqMap.forEach((key,value)->{
            logger.info("Character = " + key + " Count = " + value);
        });

        Long endTime = System.currentTimeMillis();
        logger.info("Time taken = " + (endTime - startTime));

    }
}
