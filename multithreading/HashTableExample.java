package multithreading;


import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class HashTableExample {

    public static void main(String[] args) throws InterruptedException {

        Hashtable<Character, Integer> hashtable = new Hashtable<>();
        Long startTime = System.currentTimeMillis();
        Logger logger = Logger.getLogger(HashTableExample.class.getName());

        // create frequency map of chars
        Character[] letters = {'a', 'b', 'c', 'a', 'd', 'c'}; // a=2, b=1, c =2, d=1

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for(char letter: letters){
            executorService.submit(()-> {
                hashtable.put(letter, hashtable.getOrDefault(letter,0) + 1);
                // hashtable is a shared resource being used by different threads
                // hashtable put method will be synchronized; only one thread can access the hashtable at a time
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        hashtable.forEach((key,value) -> {
            logger.info("Letter = " + key +  " Count = " + value);
        });

        Long endTime = System.currentTimeMillis();
        logger.info("time taken = " + (endTime - startTime));
    }
}
