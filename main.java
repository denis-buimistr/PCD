// main.java
public class main {
    public static void main(String[] args) throws InterruptedException {
        int X = 2, Y = 3, Z = 11, D = 8;
        int totalNeeded = Y * Z; // 3 * 11 = 33

        Store store = new Store(D, totalNeeded);

        Producer[] producers = {
            new Producer(store, "Производитель №1"),
            new Producer(store, "Производитель №2")
        };

        Consumer[] consumers = {
            new Consumer(store, "Потребитель №1", Z),
            new Consumer(store, "Потребитель №2", Z),
            new Consumer(store, "Потребитель №3", Z)
        };

        // Запуск
        for (Producer p : producers) p.start();
        for (Consumer c : consumers) c.start();

        // Ожидание потребителей
        for (Consumer c : consumers) c.join();

        // Останавливаем производителей
        for (Producer p : producers) p.stopRunning();

        System.out.println("Все потребители удовлетворены. Завершение программы.");
        System.exit(0);
    }
}