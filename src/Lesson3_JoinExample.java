public class Lesson3_JoinExample {

    public static void main(String[] args) throws InterruptedException {
        Thread t2 = new SampleThread(1);
        t2.start();
        System.out.println("Invoking join");
        //Waits for this thread to die.
        t2.join();
        System.out.println("Thread isAlive? = " + t2.isAlive());
    }

    static class SampleThread extends Thread {
        public int processingCount = 0;

        SampleThread(int processingCount) {
            this.processingCount = processingCount;
        }

        @Override
        public void run() {
            System.out.println("Thread " + this.getName() + " started");
            while (processingCount > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + this.getName() + " interrupted");
                }
                processingCount--;
            }
            System.out.println("Thread " + this.getName() + " exiting");
        }
    }
}
