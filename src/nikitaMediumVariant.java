public class nikitaMediumVariant extends Thread {

    nikitaMediumVariant(ThreadGroup group, String name, int prio) {
        super(group, name);
        this.setPriority(prio);
    }

        public void run () {
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

        ThreadGroup GN = new ThreadGroup(Main, "GN");
        ThreadGroup GH = new ThreadGroup(GN, "GH");
        ThreadGroup GM = new ThreadGroup(Main, "GM");

        Thread Tha = new nikitaMediumVariant(GH, "Tha", 4);
        Thread Thb = new nikitaMediumVariant(GH, "Thb", 3);
        Thread Thc = new nikitaMediumVariant(GH, "Thc", 6);
        Thread Thd = new nikitaMediumVariant(GH, "Thd", 3);

        Thread ThA = new nikitaMediumVariant(GN, "ThA", 3);

        Thread Th1_GM = new nikitaMediumVariant(GM, "Th1", 2);
        Thread Th2_GM = new nikitaMediumVariant(GM, "Th2", 3);
        Thread Th3_GM = new nikitaMediumVariant(GM, "Th3", 3);

        Thread Th1_Main = new nikitaMediumVariant(Main, "Th1", 8);
        Thread Th2_Main = new nikitaMediumVariant(Main, "Th2", 3);

        Tha.start(); Thb.start(); Thc.start(); Thd.start();
        ThA.start();
        Th1_GM.start(); Th2_GM.start(); Th3_GM.start();
        Th1_Main.start(); Th2_Main.start();
        root.list();
        try {
            Tha.join(); Thb.join(); Thc.join(); Thd.join();
            ThA.join();
            Th1_GM.join(); Th2_GM.join(); Th3_GM.join();
            Th1_Main.join(); Th2_Main.join();
        } catch (InterruptedException ignored) {}
        Main.list();

    }
}
