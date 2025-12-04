
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Lab6_ProducerConsumer {

    private static final int X = 2, Y = 3, Z = 40, D = 8;
    private final Deque<Integer> warehouse = new ArrayDeque<>();
    private final Lock lock = new ReentrantLock();  // метод синхронизации 
    private final Condition empty = lock.newCondition();  // склад пуст
    private final Condition full  = lock.newCondition();  // склад полон

    private volatile int totalProduced = 0;
    private volatile int totalConsumed = 0;
    private final Random rnd = new Random();

    public static void main(String[] args) throws InterruptedException {
        new Lab6_ProducerConsumer().start();
    }

    private void start() throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(X + Y);

        for (int i = 1; i <= X; i++) exec.execute(new Producer(i));
        for (int i = 1; i <= Y; i++) exec.execute(new Consumer(i));

        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("Всё завершено. Произведено и потреблено: " + totalConsumed);
    }

    class Producer implements Runnable {
        private final int id;
        Producer(int id) { this.id = id; }

        @Override
        public void run() {
            try {
                while (totalProduced < Z) {
                    lock.lock();            // 1. Захватываем монитор
                    try {
                        // Ждём, пока склад полностью пуст
                        while (warehouse.size() > 0 && totalProduced < Z) {
                            empty.await();
                        }
                        if (totalProduced >= Z) break;

                        // Один производитель заполняет склад до конца
                        while (warehouse.size() < D && totalProduced < Z) {
                            int item = 2 + rnd.nextInt(10) * 2;
                            warehouse.addLast(item);
                            totalProduced++;
                            System.out.printf("Производитель %d → +%d [склад: %d/%d, всего: %d/%d]%n",
                                    id, item, warehouse.size(), D, totalProduced, Z);
                        }

                        if (warehouse.size() == D) {
                            System.out.println("Склад ПОЛОН! Потребители, ваша очередь!");
                            full.signalAll();
                        }
                    } finally {
                        lock.unlock();
                    }
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    class Consumer implements Runnable {
        private final int id;
        private int consumed = 0;

        Consumer(int id) { this.id = id; }

        @Override
        public void run() {
            try {
                while (totalConsumed < Z) {
                    lock.lock();
                    try {
                        while (warehouse.size() < D && totalConsumed < Z) {
                            full.await();
                        }
                        if (totalConsumed >= Z) break;

                        System.out.print("Потребитель " + id + " забирает: ");
                        while (!warehouse.isEmpty()) {
                            int item = warehouse.removeFirst();
                            consumed++;
                            totalConsumed++;
                            System.out.print(item + " ");
                        }
                        System.out.printf("→ потребил %d, всего: %d/%d%n", consumed, totalConsumed, Z);

                        System.out.println("Склад ПУСТ! Производители, ваша очередь!");
                        empty.signalAll();

                    } finally {
                        lock.unlock();
                    }
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}