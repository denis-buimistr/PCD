<<<<<<< HEAD
public class main {
    public static void main(String[] args) {

        Store store = new Store();

        // ====== 2 ПРОИЗВОДИТЕЛЯ ======
        Producer p1 = new Producer(store);
        p1.setDaemon(true);
        p1.setName("Производитель №1");

        Producer p2 = new Producer(store);
        p2.setDaemon(true);
        p2.setName("Производитель №2");

        // ====== 5 ПОТРЕБИТЕЛЕЙ ======
        Consumer c1 = new Consumer(store);
        c1.setName("Потребитель №1");

        Consumer c2 = new Consumer(store);
        c2.setName("Потребитель №2");

        Consumer c3 = new Consumer(store);
        c3.setName("Потребитель №3");

        Consumer c4 = new Consumer(store);
        c4.setName("Потребитель №4");

        Consumer c5 = new Consumer(store);
        c5.setName("Потребитель №5");

        // ====== СТАРТ ПОТОКОВ ======
        p1.start();
        p2.start();

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();

        // Ждём, пока все потребители закончат (каждый берёт Z = 3 объекта)
        while (c1.isAlive() || c2.isAlive() || c3.isAlive() || c4.isAlive() || c5.isAlive()) {
        }

        System.out.println("Все потоки-потребители завершены. Главный поток завершён.");
    }
}

=======
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
>>>>>>> f34c9ec232b8f29ad0d10f171fe5bed0def921ba
