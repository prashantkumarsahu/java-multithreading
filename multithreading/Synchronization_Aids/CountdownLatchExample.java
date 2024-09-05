package multithreading.Synchronization_Aids;

import java.util.concurrent.CountDownLatch;


class CountdownLatchExample {

    private final CountDownLatch firstLatch;
    private final CountDownLatch secondLatch;

    public CountdownLatchExample() {
        firstLatch = new CountDownLatch(1);
        secondLatch = new CountDownLatch(1);
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        firstLatch.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        firstLatch.await();
        printSecond.run();
        secondLatch.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        secondLatch.await();
        printThird.run();
    }
}
