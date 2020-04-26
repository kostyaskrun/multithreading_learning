import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lesson11_LiveLock_plus_ReentrantLock_class {

//    Для управления доступом к общему ресурсу в качестве альтернативы оператору
//    synchronized мы можем использовать блокировки. Функциональность блокировок заключена в пакете java.util.concurrent.locks.
//    Вначале поток пытается получить доступ к общему ресурсу. Если он свободен, то на него накладывает блокировку.
//    После завершения работы блокировка с общего ресурса снимается. Если же ресурс не свободен и на него уже наложена блокировка,
//    то поток ожидает, пока эта блокировка не будет снята.

    public static void log(String text) {
        String name = Thread.currentThread().getName(); //like Thread-1 or Thread-0
        int val = Integer.parseInt(name.substring(name.lastIndexOf("-") + 1));
        try {
            System.out.println(text + " " + name + ": wait for " + val + " sec");
            Thread.currentThread().sleep(val * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Были времена, когда единственный способ достижения взаимного исключения
        //был через ключевое слово synchronized, но он имеет несколько недостатков,
        //например нельзя расширить lock за пределами метода или блока кода и т.д.

        /*A ReentrantLock is unstructured, unlike synchronized constructs -- i.e.
          you don't need to use a block structure for locking and can even hold a lock across methods. An example:*/

        /*private ReentrantLock lock;

        public void foo() {
            ...
            lock.lock();
            ...
        }

        public void bar() {
            ...
            lock.unlock();
            ...
        }*/


        Lock first = new ReentrantLock();
        Lock second = new ReentrantLock();

        Runnable locker = () -> {
            boolean firstLocked = false;
            boolean secondLocked = false;
            try {
                while (!firstLocked || !secondLocked) {
                    firstLocked = first.tryLock(100, TimeUnit.MILLISECONDS);
                    log("First Locked: " + firstLocked);
                    secondLocked = second.tryLock(100, TimeUnit.MILLISECONDS);
                    log("Second Locked: " + secondLocked);
                }
                first.unlock();
                second.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(locker).start();
        new Thread(locker).start();
    }
}
