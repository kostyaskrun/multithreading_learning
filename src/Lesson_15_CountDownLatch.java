import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/*
CountDownLatch - is a synchronization aid that allows one or more
threads to wait until a set of operations being performed in other threads completes.

Simply put, a CountDownLatch has a counter field, which you can decrement as we require.
We can then use it to block a calling thread until it's been counted down to zero.
If we were doing some parallel processing, we could instantiate the CountDownLatch with the same
value for the counter as a number of threads we want to work across.
Then, we could just call countdown() after each thread finishes,
guaranteeing that a dependent thread calling await() will block until the worker threads are finished.*/
public class Lesson_15_CountDownLatch {
    public static void main(String[] args) {
        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
                .limit(5)
                .collect(toList());

        workers.forEach(Thread::start);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        outputScraper.add("Latch released");
        System.out.println(outputScraper);
    }

}

class Worker implements Runnable {
    private List<String> outputScraper;
    private CountDownLatch countDownLatch;

    public Worker(List<String> outputScraper, CountDownLatch countDownLatch) {
        this.outputScraper = outputScraper;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " do some work");
        outputScraper.add("Counted down");
        countDownLatch.countDown();
    }
}
