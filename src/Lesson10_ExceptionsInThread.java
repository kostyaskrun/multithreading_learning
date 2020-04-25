public class Lesson10_ExceptionsInThread {
    /*We are talking about unchecked exceptions thrown from Thread.run() method.
      By default, you will get sth like this in system error:*/

//    Exception in thread "Thread-0" java.lang.RuntimeException
//    at Main$1.run(Main.java:11)
//    at java.lang.Thread.run(Thread.java:619)

    /*This is the result of printStackTrace for unhandled exceptions.
    To handle it, you can add your own UncaughtExceptionHandler:*/

//    To sethandler forall threadsuse a static method Thread.setDefaultUncaughtExceptionHandler .
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            throw new RuntimeException();
        });
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("exception " + e + " from thread " + t);
            }
        });
        t.start();
    }

}
