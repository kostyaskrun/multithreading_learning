/*Java предлагает два типа потоков: пользовательские потоки и потоки демонов.
 Пользовательские потоки являются приоритетными.
JVM будет ждать, пока какой-либо пользовательский поток завершит свою задачу, прежде чем завершить его.
С другой стороны, потоки демона - это потоки с низким приоритетом,
чья единственная роль заключается в предоставлении услуг потокам пользователя.
Поскольку потоки демона предназначены для обслуживания пользовательских потоков и необходимы только во время
работы пользовательских потоков, они не будут препятствовать выходу JVM после завершения всех пользовательских потоков.
вызов Thread.join () в работающем потоке демона может заблокировать завершение работы приложения.*/
public class Lesson2_DaemonThread {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("You cannot see this message, " +
                    "because this is Daemon thread! Main thread won't wait daemon thread execution");
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
}
