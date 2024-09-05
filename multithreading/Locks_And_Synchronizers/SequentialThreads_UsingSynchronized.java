package multithreading.Locks_And_Synchronizers;

class ThreadA extends Thread {

    // private Lock lock;
    private Object lock;

    ThreadA(Object lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            // synchronized, wait, notify,notifyAll does not work with ReentrantLocks
            synchronized (lock) {

//                while (!lock.tryLock()) {
//                    lock.wait();
//                }
//                lock.lock();

                for (int i = 0; i < 100; i++) {
                    System.out.print("A ");
                    Thread.sleep(1000);
                    // lock.unlock();
                    lock.notifyAll();
                    lock.wait();
                }

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ThreadB extends Thread {

    // private Lock lock;
    private Object lock;
    ThreadB(Object lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {

//                while (!lock.tryLock()) {
//                    lock.wait();
//                }
//                lock.lock();

                for (int i = 0; i < 100; i++) {
                    System.out.print("B ");
                    Thread.sleep(1000);
                    // lock.unlock();
                    lock.notifyAll();
                    lock.wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ThreadC extends Thread {

    // private Lock lock;
    private Object lock;
    ThreadC(Object lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {

//                while (!lock.tryLock()) {
//                    lock.wait();
//                }
//                lock.lock();

                for (int i = 0; i < 100; i++) {
                    System.out.print("C ");
                    Thread.sleep(1000);

                   // lock.unlock();
                    lock.notifyAll();
                    lock.wait();
                }

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class SequentialThreads_UsingSynchronized {


    /*
    There are two threads, one printing even number and 2nd printing odd number.
    write code to print number 1 to N in increasing order.
    (can be solved using wait and notify using a Boolean variable as shared lock).
     */
    public static void main(String[] args) {

        Object lock = new Object();

        Thread threadA = new Thread(new ThreadA(lock));
        Thread threadB = new Thread(new ThreadB(lock));
        Thread threadC = new Thread(new ThreadC(lock));

        threadA.start();
        threadB.start();
        threadC.start();

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

