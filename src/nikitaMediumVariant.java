public class nikitaMediumVariant extends Thread {

    nikitaMediumVariant(ThreadGroup group, String name, int prio) {
        super(group, name);
        this.setPriority(prio);
    }

    public void run() {
        System.out.println("Поток " + this.getName() +
                " начал работу. Приоритет: " + this.getPriority() +
                ". Группа: " + this.getThreadGroup().getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runVariant() {
        System.out.println();
        System.out.println("────────────── Вариант Никита ──────────────");

        ThreadGroup root = Thread.currentThread().getThreadGroup();
        ThreadGroup Main = new ThreadGroup(root, "Main");

        ThreadGroup GE = new ThreadGroup(Main, "GE");
        ThreadGroup GH = new ThreadGroup(GE, "GH");
        ThreadGroup GK = new ThreadGroup(Main, "GK");

        Thread Tha = new nikitaMediumVariant(GH, "Tha", 4);
        Thread Thb = new nikitaMediumVariant(GH, "Thb", 3);
        Thread Thc = new nikitaMediumVariant(GH, "Thc", 2);
        Thread Thd = new nikitaMediumVariant(GH, "Thd", 1);

        Thread ThA = new nikitaMediumVariant(GE, "ThA", 3);

        Thread Th1_GK = new nikitaMediumVariant(GK, "Th1", 3);
        Thread Th2_GK = new nikitaMediumVariant(GK, "Th2", 6);
        Thread Th3_GK = new nikitaMediumVariant(GK, "Th3", 3);
        Thread Th1_Main = new nikitaMediumVariant(Main, "Th1", 3);
        Thread Th2_Main = new nikitaMediumVariant(Main, "Th2", 7);
        Tha.start(); Thb.start(); Thc.start(); Thd.start();
        ThA.start();
        Th1_GK.start(); Th2_GK.start(); Th3_GK.start();
        Th1_Main.start(); Th2_Main.start();
        root.list();

        try {
            Tha.join(); Thb.join(); Thc.join(); Thd.join();
            ThA.join();
            Th1_GK.join(); Th2_GK.join(); Th3_GK.join();
            Th1_Main.join(); Th2_Main.join();
        } catch (InterruptedException ignored) {}
    }
}
