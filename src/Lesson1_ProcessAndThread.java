/*Процесс это некоторая единица операционной системы, которой выделена память и другие ресурсы.
Поток это единица исполнения кода.
Поток имеет стэк - некоторую свою память для исполнения.*/
/*Поток — определенный способ выполнения процесса.
        Когда один поток изменяет ресурс процесса, это изменение сразу же становится видно другим потокам этого процесса.*/
import java.util.concurrent.*;

public class Lesson1_ProcessAndThread {
    /*В каких состояниях может быть  поток в джава? Как вообще работает поток?*/

    /* 1.новый(это когда только создали экземпляр класса Thread)
       2.живой  или работоспособный(переходит в это состояние после запуска метода start(),
       но это не означает что поток уже работает!
       Или же он может перейти в это состояние из состояние работающий или блокированный)
       3. работающий(это когда метод run() начал выполнятся)
       4. 5. 6.
       ожидающий (waiting)/
       Заблокированный (blocked)/
       Спящий(sleeping). Эти состояния характеризуют поток как не готовый к работе.
       Я объединил эти состояния т.к. все они имеют общую черту – поток еще жив (alive),
       но в настоящее время не может быть выполнен.
       7. мертвый(состояние когда метод run() завершил свою работу)
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //all possible ways to create THREAD

        Runnable first = () -> System.out.println("First way");
        first.run();
        SecondWayToCreateThread second = new SecondWayToCreateThread();
        second.start();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Callable<String> third = () -> "Third way";
        Future<String> future = executorService.submit(third);
        System.out.println(future.get());
    }

    static class SecondWayToCreateThread extends Thread {
        @Override
        public void run() {
            System.out.println("Second way");
        }
    }
}
