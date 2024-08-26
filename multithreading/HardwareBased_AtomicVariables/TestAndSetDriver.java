package multithreading.HardwareBased_AtomicVariables;


import java.util.concurrent.atomic.AtomicBoolean;

class TestAndSetLock{
    //
    private AtomicBoolean atomicLock;

    public TestAndSetLock() {
        this.atomicLock = new AtomicBoolean(false);
    }

    public void applyLock(){
        while(atomicLock.getAndSet(true)){
            //Busy wait
        }
    }

    public void releaseLock(){
        atomicLock.set(false); //
    }
}


class TestAndSetExample implements Runnable{

    private TestAndSetLock lock;
    private int id;

    public TestAndSetExample(TestAndSetLock lock, int id) {
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
public class TestAndSetDriver {

    public static void main(String[] args) {
        TestAndSetLock lock = new TestAndSetLock();

        Thread t1 = new Thread(new TestAndSetExample(lock,0));
        Thread t2 = new Thread(new TestAndSetExample(lock,1));

        t1.start();
        t2.start();
    }
}
