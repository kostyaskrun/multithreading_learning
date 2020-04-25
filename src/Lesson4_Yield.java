public class Lesson4_Yield {
/*Как следует из официальной документации, yield () предоставляет механизм для информирования «планировщика» о том,
что текущий поток готов отказаться от своего текущего использования процессора,
но хотел бы, чтобы его запланировали как можно скорее.
«Планировщик» может свободно придерживаться или игнорировать эту информацию и на самом деле имеет различное
поведение в зависимости от операционной системы.*/
    public static void main(String[] args) {
        Thread producer = new Producer();
        Thread consumer = new Consumer();

//        Приоритет потока - это число от 1 до 10, в  зависимости от которого, планировщик потоков выбирает
//        какой поток  запускать. Однако полагаться на приоритеты
//        для предсказуемого выполнения многопоточной  программы нельзя!

//        try to run this program with commented Thread.yield();
        producer.setPriority(Thread.MIN_PRIORITY); //Min Priority
        consumer.setPriority(Thread.MAX_PRIORITY); //Max Priority

        producer.start();
        consumer.start();
    }

    static class Producer extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("I am Producer : Produced Item " + i);
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("I am Consumer : Consumed Item " + i);
                //Поступаємся потоку з меншою пріорітетністю
                Thread.yield();
            }
        }
    }
}
