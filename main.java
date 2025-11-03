// main.java
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class main {
    public static void main(String[] args) {
        int[] arr = new int[100];
        Random rand = new Random();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(100) + 1;
        }

        CountDownLatch latch12 = new CountDownLatch(2);
        CountDownLatch latch34 = new CountDownLatch(2);

        Thread t1 = new Thread(new Th1(arr, latch12), "Th1");
        Thread t2 = new Thread(new Th2(arr, latch12), "Th2");
        Thread t3 = new Thread(new Th3(latch34), "Th3");
        Thread t4 = new Thread(new Th4(latch34), "Th4");

        System.out.println("🚀 Старт потоков Th1 и Th2...\n");
        t1.start();
        t2.start();

        // Ждём завершения 1 и 2
        try {
            latch12.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n✅ Потоки Th1 и Th2 завершены. Запуск Th3 и Th4...\n");
        t3.start();
        t4.start();

        // Ждём завершения 3 и 4
        try {
            latch34.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // После завершения всех потоков
        System.out.println("\n✅ Все потоки завершены. Теперь вывод информации:\n");

        printSlowly("Buimistr");
        printSlowly("Denis");
        printSlowly("CR-233");
        printSlowly("Конкурентное и распределённое программирование");

        System.out.println("\nГлавный поток завершён.");
    }

    private static void printSlowly(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println();
    }
}