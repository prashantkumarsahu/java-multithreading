package multithreading.Locks_ReentrantLock_Synchronized_2;

class ClassA extends Thread {

    private Object lock;

    ClassA(Object lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {
                // since objA now has acquired the lock, it is executing
                System.out.println();
                System.out.println("Lock acquired by object of class A");
                for (int i = 0; i < 5; i++) {
                    System.out.print("A ");
                    Thread.sleep(500);

                    //  lock.notifyAll();
                    lock.notify();
                    lock.wait();  // now class A releases the lock and waits
                }

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ClassB extends Thread {

    private Object lock;
    ClassB(Object lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println();
                System.out.println("Lock acquired by object of class B");

                for (int i = 0; i < 5; i++) {
                    System.out.print("B ");
                    Thread.sleep(500);

                    // lock.notifyAll();
                    lock.notify();
                    lock.wait(); //  now class B releases the lock and waits
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ClassC extends Thread {

    private Object lock;
    ClassC(Object lock){
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println();
                System.out.println("Lock acquired by object of class C");

                for (int i = 0; i < 5; i++) {
                    System.out.print("C ");
                    Thread.sleep(500);

                    // lock.notifyAll();
                    lock.notify();
                    lock.wait(); // now class C releases the lock and waits
                }

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class SequentialThreads_Synchronized_ObjectLock {

    /*
    There are two threads, one printing even number and 2nd printing odd number.
    write code to print number 1 to N in increasing order.
    (can be solved using wait and notify using a Boolean variable as shared lock).
     */
    public static void main(String[] args) {

        Object lock = new Object();
        // pass the same lock to 3 different objects. i.e the 3 object are sharing the same lock
        ClassA objA = new ClassA(lock);
        ClassB objB = new ClassB(lock);
        ClassC objC = new ClassC(lock);

        // create 3 different threads executing 3 different objects.
        Thread threadA = new Thread(objA);
        Thread threadB = new Thread(objB);
        Thread threadC = new Thread(objC);

        // start the 3 threads.
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

