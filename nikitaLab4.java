import java.io.IOException;
import java.util.ArrayList;

class Store {

    ArrayList<Integer> stockList = new ArrayList<>();

    // ====== ПОТРЕБИТЕЛЬ БЕРЁТ ОДНО ЧИСЛО ======
    public synchronized void get(String name) {
        while (stockList.isEmpty()) {
            try {
                System.out.println(name + " пытается взять, но склад пуст. Ждёт...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int value = stockList.get(stockList.size() - 1);
        stockList.remove(stockList.size() - 1);

        System.out.println(name + " взял со склада: " + value);
        printStockState();

        notifyAll();
    }

    // ====== ПРОИЗВОДИТЕЛЬ КЛАДЁТ 2 ЧИСЛА ЗА РАЗ ======
    public synchronized void put(String name, int a, int b) {
        // D = 12 — максимальный размер склада
        while (stockList.size() >= 12) {
            try {
                System.out.println(name + " хочет положить, но склад полон. Ждёт...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.print(name + " поместил в хранилище два числа: ");

        stockList.add(a);
        System.out.print(a + ", ");
        stockList.add(b);
        System.out.println(b);

        printStockState();

        notifyAll();
    }

    // Печать состояния склада
    private void printStockState() {
        if (!stockList.isEmpty()) {
            System.out.print("На складе имеется " + stockList.size() + " единиц -> ");
            for (int v : stockList) {
                System.out.print(v + " ");
            }
            System.out.println();
        } else {
            System.out.println("Склад пуст");
        }
    }
}

// ====== ПРОИЗВОДИТЕЛЬ ======
class Producer extends Thread {

    Store store;
    private static final int[] EVEN_NUMBERS = new int[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20};

    public Producer(Store s) {
        this.store = s;
    }

    @Override
    public void run() {
        while (true) {
            int a = EVEN_NUMBERS[(int) (Math.random() * EVEN_NUMBERS.length)];
            int b = EVEN_NUMBERS[(int) (Math.random() * EVEN_NUMBERS.length)];
            store.put(getName(), a, b);
            try {
                Thread.sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// ====== ПОТРЕБИТЕЛЬ ======
class Consumer extends Thread {

    Store store;

    public Consumer(Store s) {
        this.store = s;
    }

    @Override
    public void run() {
        // Z = 3 — каждый потребитель берёт по 3 объекта
        for (int i = 0; i < 3; i++) {
            store.get(getName());
            try {
                Thread.sleep((int) (Math.random() * 150));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName() + " взял 3 числа. Поток завершен.");
    }
}








