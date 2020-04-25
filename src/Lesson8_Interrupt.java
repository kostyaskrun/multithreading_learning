public class Lesson8_Interrupt {

    /*Еще один способ вызова завершения или прерывания потока представляет метод interrupt().
    Вызов этого метода устанавливает у потока статус, что он прерван.
    Сам метод возвращает true, если поток может быть прерван, в ином случае возвращается false.
    При этом сам вызов этого метода НЕ завершает поток, он только устанавливает статус: в частности,
    метод isInterrupted() класса Thread будет возвращать значение true.
    Мы можем проверить значение возвращаемое данным методом и прозвести некоторые действия.

    Важно понимать что этот флаг мы можем создать сами в своем классе
    private AtomicBoolean running = new AtomicBoolean(false);
    */

    public static void main(String[] args) {

        System.out.println("Main thread started...");
        TestThread t = new TestThread("Thread");
        t.start();
        try {
            Thread.sleep(150);
            t.interrupt();

            Thread.sleep(150);
        } catch (InterruptedException e) {
            System.out.println("Thread has been interrupted");
        }
        System.out.println("Main thread finished...");
    }
}

class TestThread extends Thread {

    TestThread(String name) {
        super(name);
    }

    public void run() {

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        int counter = 1; // счетчик циклов
        while (!isInterrupted()) {
            System.out.println("Loop " + counter++);
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }
}
