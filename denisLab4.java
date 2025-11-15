    // Store.java
    import java.util.ArrayList;

    class Store {
        private final ArrayList<Integer> stockList = new ArrayList<>();
        private final int capacity;
        private int totalConsumed = 0; // общее количество потреблённых
        private final int totalNeeded;  // Z * Y — сколько нужно всего

        public Store(int capacity, int totalNeeded) {
            this.capacity = capacity;
            this.totalNeeded = totalNeeded;
        }
        //для потребителей
        public synchronized void get(String name) {
            while (stockList.isEmpty()) {
                if (totalConsumed >= totalNeeded) return; // все потребители удовлетворены
                System.out.println("ERROR! " + name + " — склад пуст, жду поставку...");
                try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            int value = stockList.remove(stockList.size() - 1);
            totalConsumed++;
            System.out.println(name + " взял со склада: " + value);
            printStock();
            notifyAll();
        }

        //для производителей
        public synchronized void put(String name, int a, int b) {
            while (stockList.size() >= capacity) {
                if (totalConsumed >= totalNeeded) return;
                System.out.println("ERROR! " + name + " — склад полон, жду освобождения...");
                try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            stockList.add(a);
            stockList.add(b);
            System.out.println(name + " поместил на склад: " + a + " и " + b);
            printStock();
            notifyAll();
        }

        public synchronized boolean isFinished() {
            return totalConsumed >= totalNeeded;
        }

        private void printStock() {
            if (stockList.isEmpty()) {
                System.out.println("Склад пуст.");
            } else {
                System.out.print("На складе " + stockList.size() + " элементов: ");
                for (int x : stockList) System.out.print(x + " ");
                System.out.println();
            }
            System.out.println("Всего потреблено: " + totalConsumed + " / " + totalNeeded);
            System.out.println("----------------------------------");
        }
    }


    // Producer.java
    class Producer extends Thread {
        private final Store store;
        private volatile boolean running = true;

        public Producer(Store store, String name) {
            super(name);
            this.store = store;
        }

        @Override
        public void run() {
            while (running && !store.isFinished()) {
                int a = (int) (Math.random() * 10 + 1) * 2; // 2,4,...,20
                int b = (int) (Math.random() * 10 + 1) * 2;
                store.put(getName(), a, b);
                try { Thread.sleep((int) (Math.random() * 500)); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        }

        public void stopRunning() {
            running = false;
        }
    }

    // Consumer.java
    class Consumer extends Thread {
        private final Store store;
        private final int needCount;

        public Consumer(Store store, String name, int needCount) {
            super(name);
            this.store = store;
            this.needCount = needCount;
        }

        @Override
        public void run() {
            for (int i = 0; i < needCount; i++) {
                store.get(getName());
                try { Thread.sleep((int) (Math.random() * 400)); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            System.out.println(getName() + " получил " + needCount + " объектов и завершил работу.");
        }
    }
