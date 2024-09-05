package multithreading.Locks_And_Synchronizers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ThreadAA extends Thread{
    ReentrantLock lock;
    Condition currentCondition;
    Condition nextCondition;

    ThreadAA(ReentrantLock lock, Condition current, Condition next){
        this.lock = lock;
        currentCondition = current;
        nextCondition = next;
    }

    @Override
    public void run() {
        try{
            // synchronized, wait, notify,notifyAll does not work with ReentrantLocks
            lock.lock();

            for (int i = 0; i < 100; i++) {
                System.out.print("A ");
                Thread.sleep(1000);
                nextCondition.signal();
                currentCondition.await();
            }
            nextCondition.signal();

        }catch (Exception ex){
            System.out.println("Exception 1 :"+ex.getMessage());
        }finally {
            lock.unlock();
        }
    }
}

class ThreadBB extends Thread{
    ReentrantLock lock;
    Condition currentCondition;
    Condition nextCondition;

    ThreadBB(ReentrantLock lock, Condition current, Condition next){
        this.lock = lock;
        currentCondition = current;
        nextCondition = next;
    }

    @Override
    public void run() {
        try{
            lock.lock();

            for (int i = 0; i < 100; i++) {
                System.out.print("B ");
                Thread.sleep(1000);
                nextCondition.signal();
                currentCondition.await();
            }
            nextCondition.signal();

        }catch (Exception ex){
            System.out.println("Exception 1 :"+ex.getMessage());
        }finally {
            lock.unlock();
        }
    }
}

class ThreadCC extends Thread{
    ReentrantLock lock;
    Condition currentCondition;
    Condition nextCondition;

    ThreadCC(ReentrantLock lock, Condition current, Condition next){
        this.lock = lock;
        currentCondition = current;
        nextCondition = next;
    }

    @Override
    public void run() {
        try{
            lock.lock();

            for (int i = 0; i < 100; i++) {
                System.out.print("C ");
                Thread.sleep(1000);
                nextCondition.signal();
                currentCondition.await();
            }
            nextCondition.signal();

        }catch (Exception ex){
            System.out.println("Exception 1 :"+ex.getMessage());
        }finally {
            lock.unlock();
        }
    }
}

public class ThreadSequential_ReentrantLock_Condition {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock(); // Re-entrant Lock is a Mutex object.
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        // currentCondition,nextCondition
        ThreadAA threadA = new ThreadAA(lock,conditionA, conditionB);
        ThreadBB threadB = new ThreadBB(lock,conditionB,conditionC);
        ThreadCC threadC = new ThreadCC(lock,conditionC,conditionA);

        threadA.start();
        threadB.start();
        threadC.start();


    }
}
