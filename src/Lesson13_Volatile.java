/*Все действия выполняет процессор. Но результаты вычислений нужно где-то хранить.
  Для этого есть основная память и есть аппаратный кэш у процессора.
  Эти кэши процессора — своего рода маленький кусочек памяти для более быстрого обращения к данным,
  чем обращения к основной памяти. Но у всего есть и минус:
  данные в кэше могут быть не актуальны (как в примере выше, когда значение флага не обновилось).
  Так вот, ключевое слово volatile указывает JVM, что мы не хотим кэшировать нашу переменную.
  Это позволяет увидеть актуальный результат во всех потоках.*/
public class Lesson13_Volatile {
    //try to add volatile
    public static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Runnable whileFlagFalse = () -> {
            while (!flag) {
            }
            System.out.println("Flag is now TRUE");
        };

        new Thread(whileFlagFalse).start();
        Thread.sleep(1000);
        flag = true;
    }
}
