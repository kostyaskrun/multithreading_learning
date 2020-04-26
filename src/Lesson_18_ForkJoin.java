import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/*Эта реализация разработана специально для упрощения распараллеливания рекурсивных задач и решает проблему с тем,
        что пока выполняется под-задача, поток, который её породил, не может быть использован.

RecursiveAction на случай, если никакого значения посчитать не нужно, а нужно лишь выполнить некоторое действие,
и RecursiveTask, когда всё же нужно что-то вернуть. Как вы могли заметить,
эти два класса аналогичны уже существующим Runnable и Callable.

Зачем мне это?
В первую очередь,
писать так существенно удобнее и интуитивно-понятнее, чем было с ипользованием Future<T>.
Более того, как я уже писал ранее, для выполнения fork-нутой задачи вовсе не обязателен выделенный настоящий поток.
Напротив, активно используются уже существующие потоки, которые в текущий момент находятся в join-е.
Это, очевидно, даёт существенный прирост к производительности.*/
public class Lesson_18_ForkJoin {
    public static void main(String[] args) {
        System.out.println(ForkJoinAdd.startForkJoinSum(1_000_000));
    }
}

class ForkJoinAdd extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;
    public static final long threshold = 10_000;

    public ForkJoinAdd(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinAdd(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        int length = end - start;
        if (length <= threshold) {
            return add();
        }

        ForkJoinAdd firstTask = new ForkJoinAdd(numbers, start, start + length / 2);
        firstTask.fork(); //start asynchronously

        ForkJoinAdd secondTask = new ForkJoinAdd(numbers, start + length / 2, end);

        Long secondTaskResult = secondTask.compute();
        Long firstTaskResult = firstTask.join();

        return firstTaskResult + secondTaskResult;

    }

    private long add() {
        long result = 0;
        for (int i = start; i < end; i++) {
            result += numbers[i];
        }
        return result;
    }

    public static long startForkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinAdd(numbers);
        return new ForkJoinPool().invoke(task);
    }
}

