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
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ---- TEST --- 
        //  for (int i = 1; i <= 3; i++) {
        //     System.out.printf("   %s выполняет шаг %d...%n", getName(), i);
        //     try {
        //         Thread.sleep(400);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }

        // System.out.printf("✓ Поток %s завершён%n%n", getName());
    }

    
    
}