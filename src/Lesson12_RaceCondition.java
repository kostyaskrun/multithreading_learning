import java.util.concurrent.atomic.AtomicInteger;

public class Lesson12_RaceCondition {
    public static int value = 0;
    public static AtomicInteger fixSimpleIntValue = new AtomicInteger(0);

/*  Атомарные операции — это операции, которые нельзя разделить.
    Например, операция присваивания значения переменной — атомарная.
    К сожалению, инкремент не является атомарной операцией, т.к.
    для инкремента требуется аж три операции: получить старое значение, прибавить к нему единицу, сохранить значение.*/

    public static void main(String[] args) throws InterruptedException {
        Runnable problemTask = () -> {
            for (int i = 0; i < 10000; i++) {
                ++value;
            }
        };
        Runnable resolvedTask = () -> {
            for (int i = 0; i < 10000; i++) {
                ++value;
            }
            for (int i = 0; i < 10000; i++) {
                fixSimpleIntValue.incrementAndGet();
            }
        };
        for (int i = 0; i < 3; i++) {
            new Thread(problemTask).start();
            new Thread(resolvedTask).start();
        }
        Thread.sleep(3000);
        System.out.println("race condition " + value);
        System.out.println("without race condition " + fixSimpleIntValue);
    }
}
