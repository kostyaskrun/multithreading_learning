import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//The TheadLocal construct allows us to store data that will be accessible only by a specific thread.
public class Lesson_16_ThreadLocal {
    public static void main(String[] args) throws InterruptedException {
        ContextWithoutThreadLocal firstUser = new ContextWithoutThreadLocal(1);
        ContextWithoutThreadLocal secondUser = new ContextWithoutThreadLocal(2);
        new Thread(firstUser).start();
        new Thread(secondUser).start();
        Thread.sleep(1000);
        ContextWithoutThreadLocal.userContextPerUserId.forEach((key, value) -> System.out.println(value));

        ContextWithThreadLocal firstUserThreadLocal = new ContextWithThreadLocal(1);
        ContextWithThreadLocal secondUserThreadLocal = new ContextWithThreadLocal(2);
        new Thread(firstUserThreadLocal).start();
        new Thread(secondUserThreadLocal).start();
        Thread.sleep(1000);
        System.out.println("You cannot see global result, you have to get context by specific thread, context = " +
                ContextWithThreadLocal.userContext.get());
    }
}


class Context {
    private String userName;

    public Context(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Context{" +
                "userName='" + userName + '\'' +
                '}';
    }
}

class UserRepository {
    public String getUserNameForUserId(Integer userId) {
        if (userId == 1) {
            return "user 1 secret";
        } else {
            return "user 2 secret";
        }
    }
}

class ContextWithoutThreadLocal implements Runnable {

    public static Map<Integer, Context> userContextPerUserId
            = new ConcurrentHashMap<>();
    private Integer userId;
    private UserRepository userRepository = new UserRepository();

    ContextWithoutThreadLocal(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContextPerUserId.put(userId, new Context(userName));
    }
}

class ContextWithThreadLocal implements Runnable {

    public static ThreadLocal<Context> userContext = new ThreadLocal<>();
    private Integer userId;
    private UserRepository userRepository = new UserRepository();

    ContextWithThreadLocal(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContext.set(new Context(userName));
        System.out.println("thread context for given userId: "
                + userId + " is: " + userContext.get());
    }

}