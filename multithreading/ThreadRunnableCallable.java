package multithreading;

// https://javabypatel.blogspot.com/2016/09/java-multithreading-interview-questions-answers.html

import java.util.Random;
import java.util.concurrent.*;

class ThreadRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is executing...");
    }
}

class ThreadParent extends Thread{

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is executing...");
    }
}

class Result{
    public int code;
    public String message;
}
class ThreadCallable implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
         Random random = new Random();
         return  random.nextInt(100);
//        Result response = new Result();
//        response.code = 200;
//        response.message = "Sucess";
//        return response;
    }
}
public class ThreadRunnableCallable {

    public static void main(String[] args) {

        System.out.println("Main thread is = " + Thread.currentThread().getId());
        ThreadRunnable threadRunnable = new ThreadRunnable(); // 1 out of 3 ways to assign task to a thread
        Thread thread1 = new Thread(threadRunnable); // only way to create thread
         thread1.start();
        // thread1.run();
        // calling run directly on the thread without start, will NOT create a separate new thread, but will call run() method of
        // current/calling thread, which is the main thread here
        // There is no relation between the newly created thread and the thread which created it. They are totally independent.
        // only Thread priority and Daemon property of the creator thread is copied into the new thread.
        // JVM will continue to exist, until any non-daemon thread is running...it will shut down only if the active ones are all daemon threads.

        try{
            ThreadParent threadParent = new ThreadParent(); // 2 out of 3 ways to assign task to a thread
            Thread thread2 = new Thread(threadParent); // only way to create thread
            thread2.start();
            thread2.start(); // Throws  IllegalThreadStateException as for a thread to get started threadStatus value should be 0
            // and when once completed threadStatus value = 2;
            // when we call start() again, its value is not 0, so exception is thrown
        }catch (IllegalThreadStateException ex){
            System.out.println("Cannot start the same thread twice");
        }


        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // this will create a new thread pool and start the thread
        // but what task they will execute that will be passed to thread using submit() method.

        ThreadCallable threadCallable = new ThreadCallable(); // 3 out of 3 ways to assign task to a thread
        Future<Integer> resultFuture1 =  executorService.submit(threadCallable);
        Future<Integer> resultFuture2 =  executorService.submit(threadCallable);


        Result result = null;
        try{
            Integer i1 = resultFuture1.get(); // get() will block the program until resultFuture is not completed
            Integer i2 = resultFuture2.get();

            System.out.println("i1 = " + i1);
            System.out.println("i2 = " + i2);
            System.out.println("i1+i2 = " + i1 + i2);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }
}
