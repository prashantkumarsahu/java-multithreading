package multithreading.HardwareBased_AtomicVariables;


import java.util.concurrent.atomic.AtomicInteger;


class CompareAndSwapLock {
    private final AtomicInteger state = new AtomicInteger(0);

    public void applyLock() {
        while (!state.compareAndSet(0, 1)) {
            // Busy-wait
        }
    }

    public void releaseLock() {
        state.set(0);
    }
}
class CompareAndSwapExample implements Runnable{

    private CompareAndSwapLock lock;
    private int id;

    public CompareAndSwapExample(CompareAndSwapLock lock, int id) {
        this.lock = lock;
        this.id = id;
    }

    @Override
    public void run() {
        this.lock.applyLock();
        // critical section
        System.out.println("Thread " + id + " in critical section.");
//        Thread.sleep(1000);
        this.lock.releaseLock();
    }
}
public class CompareAndSwapDriver {

    public static void main(String[] args) {
        CompareAndSwapLock lock = new CompareAndSwapLock();

        Thread t1 = new Thread(new CompareAndSwapExample(lock,0));
        Thread t2 = new Thread(new CompareAndSwapExample(lock,1));

        t1.start();
        t2.start();
    }
}
