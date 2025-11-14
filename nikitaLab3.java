import java.util.concurrent.CountDownLatch;

public class nikitaLab3 {

    // ====== СИНХРОНИЗАЦИЯ ======
    static final class PrintLocks {
        static final Object LOCK_12 = new Object();
        static final Object LOCK_34 = new Object();
    }

    static CountDownLatch latch;

    // ====== Th1: нечетные по два, с начала ======
    static class Th1 implements Runnable {
        private final int[] arr;

        Th1(int[] arr) {
            this.arr = arr;
        }

        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            sb.append("Th1: Суммы нечетных чисел по два (с начала массива)\n");

            int sum = 0;
            for (int i = 0; i < arr.length - 1; i += 2) {
                if (arr[i] % 2 != 0 && arr[i + 1] % 2 != 0) {
                    sum += arr[i] + arr[i + 1];
                    sb.append("Th1 → ").append(arr[i]).append(" + ").append(arr[i + 1])
                      .append(" = ").append(sum).append("\n");
                }
                try { Thread.sleep(30); } catch (InterruptedException ignored) {}
            }

            sb.append("Th1 завершён, итоговая сумма: ").append(sum).append("\n\n");

            synchronized (PrintLocks.LOCK_12) {
                System.out.print(sb);
            }

            latch.countDown();
            try { latch.await(); } catch (InterruptedException ignored) {}

            main.printSlowly("я напечатал");
        }
    }

    // ====== Th2: нечетные по два, с конца ======
    static class Th2 implements Runnable {
        private final int[] arr;

        Th2(int[] arr) {
            this.arr = arr;
        }

        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            sb.append("Th2: Суммы нечетных чисел по два (с конца массива)\n");

            int sum = 0;
            for (int i = arr.length - 1; i > 0; i -= 2) {
                if (arr[i] % 2 != 0 && arr[i - 1] % 2 != 0) {
                    sum += arr[i] + arr[i - 1];
                    sb.append("Th2 → ").append(arr[i - 1]).append(" + ").append(arr[i])
                      .append(" = ").append(sum).append("\n");
                }
                try { Thread.sleep(30); } catch (InterruptedException ignored) {}
            }

            sb.append("Th2 завершён, итоговая сумма: ").append(sum).append("\n\n");

            synchronized (PrintLocks.LOCK_12) {
                System.out.print(sb);
            }

            latch.countDown();
            try { latch.await(); } catch (InterruptedException ignored) {}
        }
    }

    // ====== Th3: интервал [0, 798] ======
    static class Th3 implements Runnable {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            sb.append("Th3: Пройти с начала интервала [0, 798]\n");

            for (int i = 0; i <= 798; i += 100) {
                sb.append("Th3 → ").append(i).append("\n");
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
            if (798 % 100 != 0) sb.append("Th3 → 798\n");

            sb.append("Th3 завершён.\n\n");
            synchronized (PrintLocks.LOCK_34) {
                System.out.print(sb);
            }

            latch.countDown();
            try { latch.await(); } catch (InterruptedException ignored) {}
        }
    }

    // ====== Th4: интервал [1456, 2111] с конца ======
    static class Th4 implements Runnable {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            sb.append("Th4: Пройти с конца интервала [1456, 2111]\n");

            for (int i = 2111; i >= 1456; i -= 100) {
                sb.append("Th4 → ").append(i).append("\n");
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
            if ((2111 - 1456) % 100 != 0) sb.append("Th4 → 1456\n");

            sb.append("Th4 завершён.\n\n");
            synchronized (PrintLocks.LOCK_34) {
                System.out.print(sb);
            }

            latch.countDown();
            try { latch.await(); } catch (InterruptedException ignored) {}
        }
    }

    // ====== Метод запуска ======
    public static void runVariant() {
        int[] arr = {3, 5, 2, 7, 9, 11, 8, 13, 15, 17, 4, 6, 19, 21, 23, 25};

        latch = new CountDownLatch(4);

        Thread t1 = new Thread(new Th1(arr), "Th1");
        Thread t2 = new Thread(new Th2(arr), "Th2");
        Thread t3 = new Thread(new Th3(), "Th3");
        Thread t4 = new Thread(new Th4(), "Th4");

        System.out.println("=== Запуск nikitaLab3 ===\n");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException ignored) {}

        System.out.println("=== nikitaLab3 завершён ===\n");
    }
}
