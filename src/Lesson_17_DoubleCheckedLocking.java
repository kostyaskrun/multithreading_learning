import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Lesson_17_DoubleCheckedLocking {
    VasyaContext context;

    public static void main(String... args) throws InterruptedException {
        final Lesson_17_DoubleCheckedLocking instance = new Lesson_17_DoubleCheckedLocking();
        final AtomicInteger notFound = new AtomicInteger();
        final AtomicInteger found = new AtomicInteger();
        List<Thread> threads = new ArrayList<>();

        for(int i=0; i<1000; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    VasyaContext vasyaInstanceContext = instance.getVasyaInstanceContext();
                    int resultSize = vasyaInstanceContext.vasyas.size() > 0 ? 1 : 0;
                    if(resultSize == 0)
                        notFound.incrementAndGet();
                    else if(resultSize == 1)
                        found.incrementAndGet();
                    else
                        throw new AssertionError();
                }
            };
            thread.start();
            threads.add(thread);
        }
        for(Thread thread : threads) thread.join();
        System.out.println("Not found: "+notFound);
        System.out.println("Found: "+found);
    }

    public VasyaContext getVasyaInstanceContext() {
        if (context == null) {
            synchronized (this) {
                if (context == null) {
                    context = new VasyaContext();
                    context.vasyas = new ArrayList<>();
                    context.vasyas.add(new Vasya("qweqwe", 12));
                    return context;
                }
            }
        }
        return context;
    }
}
class VasyaContext {
    List<Vasya> vasyas;

    public VasyaContext() {

    }
}

class Vasya {
    String surName;
    int age;

    public Vasya(String surName, int age) {
        this.surName = surName;
        this.age = age;
    }
}