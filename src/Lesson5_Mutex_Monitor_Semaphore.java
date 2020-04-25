import java.util.concurrent.Semaphore;

public class Lesson5_Mutex_Monitor_Semaphore {
    /*Few words about mutex
Мьютекс — это специальный объект для синхронизации потоков. Он «прикреплен» к каждому объекту в Java
Неважно, пользуешься ли ты стандартными классами или создал собственные классы,
скажем, Cat и Dog: у всех объектов всех классов есть мьютекс.*/

    //Контроль за доступом к объекту-ресурсу обеспечивает понятие монитора.
    //Монитор экземпляра может иметь только одного владельца.
    private Object obj = new Object();

    //ATTENTION!
//    Статические синхронизированные методы и нестатические синхронизированные методы не будет блокировать друг друга, никогда.
//    Статические методы блокируются на экземпляре класса Class в то время как нестатические
//    методы блокируются на текущем экземпляре (this). Эти действия не мешают друг другу.

    public void doSomething() {

        //...какая-то логика, доступная для всех потоков

        synchronized (obj) {

            //логика, которая одновременно доступна только для одного потока
        }
    }

    public static void main(String[] args) {

        Semaphore sem = new Semaphore(2);
        new Philosopher(sem, "Сократ").start();
        new Philosopher(sem, "Платон").start();
        new Philosopher(sem, "Аристотель").start();
        new Philosopher(sem, "Фалес").start();
        new Philosopher(sem, "Пифагор").start();
    }

    /*Семафор — это средство для синхронизации доступа к какому-то ресурсу.
    Его особенность заключается в том, что при создании механизма синхронизации он использует счетчик.*/
    static class Philosopher extends Thread {

        private Semaphore sem;

        // поел ли философ
        private boolean full = false;

        private String name;

        Philosopher(Semaphore sem, String name) {
            this.sem = sem;
            this.name = name;
        }

        public void run() {
            try {
                // если философ еще не ел
                if (!full) {
                    //Запрашиваем у семафора разрешение на выполнение
                    sem.acquire();
                    System.out.println(name + " садится за стол");

                    // философ ест
                    sleep(300);
                    full = true;

                    System.out.println(name + " поел! Он выходит из-за стола");
                    sem.release();

                    // философ ушел, освободив место другим
                    sleep(300);
                }
            } catch (InterruptedException e) {
                System.out.println("Что-то пошло не так!");
            }
        }
    }

}
