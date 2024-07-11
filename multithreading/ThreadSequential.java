package multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ThreadA extends Thread{
    ReentrantLock lock;
    Condition currentCondition;
    Condition nextCondition;

    ThreadA(ReentrantLock lock, Condition current, Condition next){
        this.lock = lock;
        currentCondition = current;
        nextCondition = next;
    }

    @Override
    public void run() {
        try{
            // synchronized, wait, notify,notifyAll does not work with ReentrantLocks
            // synchronized (lock) {
            lock.lock();

//            while (!lock.tryLock()) {
//                lock.wait();
//            }

            for (int i = 0; i < 100; i++) {
                System.out.print("A ");
                Thread.sleep(1000);
                nextCondition.signal();
                currentCondition.await();
//                lock.unlock();
//                lock.notifyAll();
            }
            nextCondition.signal();
            // }

        }catch (Exception ex){
            System.out.println("Exception 1 :"+ex.getMessage());
        }finally {
            lock.unlock();
        }
    }
}

class ThreadB extends Thread{
    ReentrantLock lock;
    Condition currentCondition;
    Condition nextCondition;

    ThreadB(ReentrantLock lock, Condition current, Condition next){
        this.lock = lock;
        currentCondition = current;
        nextCondition = next;
    }

    @Override
    public void run() {
        try{
            // synchronized (lock) {

//            while (!lock.tryLock()) {
//                lock.wait();
//            }
            lock.lock();

            for (int i = 0; i < 100; i++) {
                System.out.print("B ");
                Thread.sleep(1000);
                nextCondition.signal();
                currentCondition.await();
//                lock.unlock();
//                lock.notifyAll();
            }
            nextCondition.signal();
            //    }

        }catch (Exception ex){
            System.out.println("Exception 1 :"+ex.getMessage());
        }finally {
            lock.unlock();
        }
    }
}

class ThreadC extends Thread{
    ReentrantLock lock;
    Condition currentCondition;
    Condition nextCondition;

    ThreadC(ReentrantLock lock, Condition current, Condition next){
        this.lock = lock;
        currentCondition = current;
        nextCondition = next;
    }

    @Override
    public void run() {
        try{
            //    synchronized (lock) {

//                while(!lock.tryLock()){
//                    lock.wait();
//                }
            lock.lock();

            for (int i = 0; i < 100; i++) {
                System.out.print("C ");
                Thread.sleep(1000);
                nextCondition.signal();
                currentCondition.await();

                // lock.unlock();
                // lock.notifyAll();
            }
            nextCondition.signal();
            //    }

        }catch (Exception ex){
            System.out.println("Exception 1 :"+ex.getMessage());
        }finally {
            lock.unlock();
        }
    }
}

public class ThreadSequential {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        // currentCondition,nextCondition
        ThreadA threadA = new ThreadA(lock,conditionA, conditionB);
        ThreadB threadB = new ThreadB(lock,conditionB,conditionC);
        ThreadC threadC = new ThreadC(lock,conditionC,conditionA);

        threadA.start();
        threadB.start();
        threadC.start();


    }
}
