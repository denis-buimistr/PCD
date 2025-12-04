import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class nikitaLab5 {

    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 5;
    private static final int CONSUMER_GOAL = 3;
    private static final int BUFFER_CAPACITY = 12;
    private static final int F = 2;

    private static final int TOTAL_OBJECTS = CONSUMER_GOAL * CONSUMER_COUNT;

    private static final BlockingQueue<Integer> buffer =
            new ArrayBlockingQueue<>(BUFFER_CAPACITY);

    private static final AtomicInteger totalProduced = new AtomicInteger(0);
    private static final AtomicInteger totalConsumed = new AtomicInteger(0);

    private static final Map<Integer, AtomicInteger> consumerCounters =
            new ConcurrentHashMap<>();

    private static final AtomicBoolean allConsumersSatisfied = new AtomicBoolean(false);


    public static void main(String[] args) {

        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            consumerCounters.put(i, new AtomicInteger(0));
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(PRODUCER_COUNT + CONSUMER_COUNT);

        for (int i = 1; i <= PRODUCER_COUNT; i++) {
            executor.execute(new Producer(i));
        }

        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            executor.execute(new Consumer(i));
        }

        executor.shutdown();

        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n========== ИТОГОВЫЙ ОТЧЕТ ==========");
        System.out.println("Общее количество произведенных объектов: " + totalProduced.get());
        System.out.println("Общее количество потребленных объектов: " + totalConsumed.get());

        consumerCounters.forEach((id, count) ->
                System.out.println("Потребитель " + id + " получил: " + count.get() + " объектов"));

        System.out.println("Программа завершена.");
    }


    static class Producer implements Runnable {

        private final int id;
        private final Random random = new Random();
        private final int[] evenNumbers =
                new int[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20};

        Producer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {

                while (!allConsumersSatisfied.get()) {

                    if (buffer.remainingCapacity() == 0) {
                        System.out.println("[Производитель " + id + "] Склад заполнен, ожидает...");
                    }

                    for (int i = 0; i < F; i++) {

                        if (allConsumersSatisfied.get()) {
                            System.out.println("Производитель " + id +
                                    " завершил работу (все потребители удовлетворены).");
                            return;
                        }

                        int item = evenNumbers[random.nextInt(evenNumbers.length)];

                        boolean offered = buffer.offer(item, 100, TimeUnit.MILLISECONDS);

                        if (offered) {
                            int produced = totalProduced.incrementAndGet();

                            System.out.println("[Производитель " + id + "] произвел: " + item +
                                    " | Всего произведено: " + produced +
                                    " | На складе: " + buffer.size() + "/" + BUFFER_CAPACITY);
                        } else {
                            System.out.println("[Производитель " + id +
                                    "] не смог поместить объект (склад полон).");
                        }
                    }

                    Thread.sleep(150);
                }

                System.out.println("Производитель " + id +
                        " завершил работу (allConsumersSatisfied = true).");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class Consumer implements Runnable {

        private final int id;

        Consumer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {

                while (consumerCounters.get(id).get() < CONSUMER_GOAL) {

                    if (buffer.isEmpty()) {
                        System.out.println("[Потребитель " + id +
                                "] Склад пуст, ждёт...");
                    }

                    int item = buffer.take();

                    consumerCounters.get(id).incrementAndGet();
                    int consumedAll = totalConsumed.incrementAndGet();

                    System.out.println("[Потребитель " + id + "] взял: " + item +
                            " | получено этим: " + consumerCounters.get(id).get() +
                            " | всего съедено всеми: " + consumedAll +
                            " | на складе осталось: " + buffer.size());

                    boolean allDone = true;
                    for (int c = 1; c <= CONSUMER_COUNT; c++) {
                        if (consumerCounters.get(c).get() < CONSUMER_GOAL) {
                            allDone = false;
                            break;
                        }
                    }
                    if (allDone) {
                        allConsumersSatisfied.set(true);
                    }

                    Thread.sleep(200);
                }

                System.out.println("[Потребитель " + id +
                        "] завершил работу, получил свои " + CONSUMER_GOAL + " объектов.");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
