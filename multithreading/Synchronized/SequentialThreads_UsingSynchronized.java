package multithreading.Synchronized;

import java.util.concurrent.locks.*;

class ThreadA extends Thread {

    private Lock lock;

    ThreadA(Lock lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            // synchronized, wait, notify,notifyAll does not work with ReentrantLocks
            synchronized (lock) {

                while (!lock.tryLock()) {
                    lock.wait();
                }
                lock.lock();

                for (int i = 0; i < 100; i++) {
                    System.out.print("A ");
                    Thread.sleep(1000);
                    lock.unlock();
                    lock.notifyAll();
                }

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ThreadB extends Thread {

    private Lock lock;
    ThreadB(Lock lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {

                while (!lock.tryLock()) {
                    lock.wait();
                }
                lock.lock();

                for (int i = 0; i < 100; i++) {
                    System.out.print("B ");
                    Thread.sleep(1000);
                    lock.unlock();
                    lock.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ThreadC extends Thread {

    private Lock lock;
    ThreadC(Lock lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {

                while (!lock.tryLock()) {
                    lock.wait();
                }
                lock.lock();

                for (int i = 0; i < 100; i++) {
                    System.out.print("C ");
                    Thread.sleep(1000);

                    lock.unlock();
                    lock.notifyAll();
                }

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class SequentialThreads_UsingSynchronized {


    public static void main(String[] args) {



        // currentCondition,nextCondition
//        ThreadA threadA = new ThreadA();
//        ThreadB threadB = new ThreadB();
//        ThreadC threadC = new ThreadC();
//
//        threadA.start();
//        threadB.start();
//        threadC.start();


    }
}

