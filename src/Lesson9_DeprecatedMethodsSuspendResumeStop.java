public class Lesson9_DeprecatedMethodsSuspendResumeStop {
    public static void main(String[] args) throws InterruptedException {
        //example with stop
        JavaStop stopThreadExample = new JavaStop();
        stopThreadExample.start();
        Thread.sleep(1000);
        stopThreadExample.stop();


        // creating three threads
        JavaSuspendExp t1 = new JavaSuspendExp();
        JavaSuspendExp t2 = new JavaSuspendExp();
        JavaSuspendExp t3 = new JavaSuspendExp();
        // call run() method
        t1.start();
        t2.start();
        // suspend t2 thread
        t2.suspend();
        // call run() method
        t3.start();
        //sleep
        Thread.sleep(2000);
        //resume suspended thread
        t2.resume();
    }
}

class JavaSuspendExp extends Thread {
    public void run() {
        for (int i = 1; i < 5; i++) {
            try {
                // thread to sleep for 500 milliseconds
                sleep(500);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class JavaStop extends Thread {
    public void run() {
        for (int i = 1; i < 5; i++) {
            try {
                // thread to sleep for 500 milliseconds
                sleep(500);
                System.out.println("stop " + i);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}