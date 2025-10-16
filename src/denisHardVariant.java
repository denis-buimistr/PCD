public class denisHardVariant extends Thread {
    public denisHardVariant(ThreadGroup group, String name, int priority) {
        super(group, name);
        setPriority(priority);
    }

    @Override
    public void run() {
        System.out.printf(
            "Поток: %-5s | Группа: %-3s | Приоритет: %d%n",
            getName(),
            getThreadGroup().getName(),
            getPriority()
        );

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}