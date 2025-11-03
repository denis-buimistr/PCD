// Lab.java
import java.util.concurrent.CountDownLatch;

class Th1 implements Runnable {
    private final int[] arr;
    private final CountDownLatch latch;

    public Th1(int[] arr, CountDownLatch latch) {
        this.arr = arr;
        this.latch = latch;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        sb.append("Th1: Сумма чётных чисел по два (с начала массива)\n");

        int sum = 0;
        for (int i = 0; i < arr.length - 1; i += 2) {
            if (arr[i] % 2 == 0 && arr[i + 1] % 2 == 0) {
                sum += arr[i] + arr[i + 1];
                sb.append("Th1 → ").append(arr[i]).append(" + ").append(arr[i + 1]).append(" = ").append(sum).append("\n");
            }
            try { Thread.sleep(30); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        sb.append("Th1 завершён, итоговая сумма: ").append(sum).append("\n\n");

        synchronized (PrintLocks.LOCK_12) {
            System.out.print(sb.toString());
        }
        latch.countDown();
    }
}

class Th2 implements Runnable {
    private final int[] arr;
    private final CountDownLatch latch;

    public Th2(int[] arr, CountDownLatch latch) {
        this.arr = arr;
        this.latch = latch;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        sb.append("Th2: Сумма чётных чисел по два (с конца массива)\n");

        int sum = 0;
        for (int i = arr.length - 1; i > 0; i -= 2) {
            if (arr[i] % 2 == 0 && arr[i - 1] % 2 == 0) {
                sum += arr[i] + arr[i - 1];
                sb.append("Th2 → ").append(arr[i - 1]).append(" + ").append(arr[i]).append(" = ").append(sum).append("\n");
            }
            try { Thread.sleep(30); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        sb.append("Th2 завершён, итоговая сумма: ").append(sum).append("\n\n");

        synchronized (PrintLocks.LOCK_12) {
            System.out.print(sb.toString());
        }
        latch.countDown();
    }
}

class Th3 implements Runnable {
    private final CountDownLatch latch;

    public Th3(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        sb.append("Th3: Диапазон [100..500]\n");

        for (int i = 100; i <= 500; i += 100) {
            sb.append("Th3 → ").append(i).append("\n");
            try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        sb.append("Th3 завершён.\n\n");

        synchronized (PrintLocks.LOCK_34) {
            System.out.print(sb.toString());
        }
        latch.countDown();
    }
}

class Th4 implements Runnable {
    private final CountDownLatch latch;

    public Th4(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        sb.append("Th4: Диапазон [700..300] (в обратном порядке)\n");

        for (int i = 700; i >= 300; i -= 100) {
            sb.append("Th4 → ").append(i).append("\n");
            try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        sb.append("Th4 завершён.\n\n");

        synchronized (PrintLocks.LOCK_34) {
            System.out.print(sb.toString());
        }
        latch.countDown();
    }
}