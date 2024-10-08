package multithreading.Runnable_Callable_ThreadParent_1;

// https://javabypatel.blogspot.com/2016/09/java-multithreading-interview-questions-answers.html

import java.util.Random;
import java.util.concurrent.*;

class ThreadRunnable implements Runnable{
    // Runnable is useful for non-result oriented tasks like Update UI/ Logging/ Sending Automated emails/notifications
    // where response is not required
    // Since it does not return anything.
    // Also Runnable Does not throw a checked exception.
    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is executing. implementing the runnable interface..");
    }

    public static void main(String[] args) {
        ThreadRunnable t1 = new ThreadRunnable();
        Thread t2 = new Thread(t1);
        t2.start();
    }
}

class ThreadParent extends Thread{

    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is executing.. extending the Thread parent class.");
    }

    public static void main(String[] args) {
        ThreadParent t1 = new ThreadParent();
        Thread t2 = new Thread(t1);
        t2.start();
    }
}

class Result{
    public int code;
    public String message;
}

class ThreadCallable implements Callable<Integer>{
    // Callable is used when we want some response back from the thread or require exception handling
    // also it can throw a checked exception
    @Override
    public Integer call() throws Exception {

        System.out.println("Thread " + Thread.currentThread().getId() +  " is executing using Callable interface.");
         Random random = new Random();
         return  random.nextInt(100);
//        Result response = new Result();
//        response.code = 200;
//        response.message = "Success";
//        return response;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadCallable t1 = new ThreadCallable();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> t2 = pool.submit(t1);

        Integer res = t2.get();
        System.out.println(res);
    }
}
public class ThreadRunnableCallableDriver {

    public static void main(String[] args) {

        System.out.println("Main thread is = " + Thread.currentThread().getId());
        ThreadRunnable threadRunnable = new ThreadRunnable(); // 1 out of 3 ways to assign task to a thread
        Thread thread1 = new Thread(threadRunnable); // only way to create thread
        // once JVM enters main() of Driver class, we want to create an object of Runnable class
        // and assign it to a separate thread to run parallely

      //  thread1.run(); // will run main thread only, as we have not started thread1
//  calling run directly on the thread without start, will NOT create a separate new thread, but will call run() method of
//  current/calling thread, which is the main thread here

         thread1.start(); // start() will internally call run() inside a separate thread
         //thread1.run(); // will now run thread1

        // There is no relation between the newly created thread and the thread which created it. They are totally independent.
        // only Thread priority and Daemon property of the creator thread is copied into the new thread.
        // JVM will continue to exist, until any non-daemon thread is running...it will shut down only if the active ones are all daemon threads.

        try{
            ThreadParent threadParent = new ThreadParent(); // 2 out of 3 ways to assign task to a thread
            Thread thread2 = new Thread(threadParent); // only way to create thread
            thread2.start();
           // thread2.start(); // Throws  IllegalThreadStateException as for a thread to get started threadStatus value should be 0
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
