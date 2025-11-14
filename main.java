import java.util.Random;

public class main {
    public static void main(String[] args) {
     int[] arr = new int[100];
        Random rand = new Random();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(100) + 1;
        }

        Thread t1 = new Thread(new Th1(arr), "Th1");
        Thread t2 = new Thread(new Th2(arr), "Th2");
        Thread t3 = new Thread(new Th3(), "Th3");
        Thread t4 = new Thread(new Th4(), "Th4");

        System.out.println("Старт потоков Th1, Th2, Th3 и Th4...\n");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Ждём завершения всех потоков
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n Все потоки завершены. Теперь вывод информации:\n");

        printSlowly("Buimistr");
        printSlowly("Denis");
        printSlowly("CR-233");
        printSlowly("Конкурентное и распределённое программирование");

        System.out.println("\nГлавный поток завершён.");
    }

    private static void printSlowly(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
}
