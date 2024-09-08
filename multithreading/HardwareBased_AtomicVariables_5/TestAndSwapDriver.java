package multithreading.HardwareBased_AtomicVariables_5;


class TestAndSwapLock{
    //
    private boolean lock = false;

    // Atomic Test-and-Swap operation
    public synchronized boolean testAndSwap(boolean newValue) {
        boolean oldValue = lock; //
        lock = newValue; // values are being swapped at variable lock;
        return oldValue;
    }

    public void applyLock(){
        while(testAndSwap(true)){
            //Busy wait
        }
    }

    public void releaseLock(){
        lock = false; //
    }
}

class TestAndSwapExample implements Runnable{
    private TestAndSwapLock lock;
    private int id;
    TestAndSwapExample(TestAndSwapLock lock, int id){
        this.lock = lock;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.applyLock();
            // Critical section
            System.out.println("Thread " + id + " is in critical section.");
            lock.releaseLock();
        }
    }
}

public class TestAndSwapDriver {

    public static void main(String[] args) {
        TestAndSwapLock lock = new TestAndSwapLock();

        Thread t1 = new Thread(new TestAndSwapExample(lock,0));
        Thread t2 = new Thread(new TestAndSwapExample(lock,1));

        t1.start();
        t2.start();
    }
}
