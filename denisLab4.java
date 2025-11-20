<<<<<<< HEAD
// // Lab.java
// import java.util.concurrent.CountDownLatch;

// class Th1 implements Runnable {
//     private final int[] arr;
//     private final CountDownLatch latch;

//     public Th1(int[] arr, CountDownLatch latch) {
//         this.arr = arr;
//         this.latch = latch;
//     }

//     @Override
//     public void run() {
//         StringBuilder sb = new StringBuilder();
//         sb.append("Th1: Сумма чётных чисел по два (с начала массива)\n");

//         int sum = 0;
//         for (int i = 0; i < arr.length - 1; i += 2) {
//             if (arr[i] % 2 == 0 && arr[i + 1] % 2 == 0) {
//                 sum += arr[i] + arr[i + 1];
//                 sb.append("Th1 → ").append(arr[i]).append(" + ").append(arr[i + 1]).append(" = ").append(sum).append("\n");
//             }
//             try { Thread.sleep(30); } catch (InterruptedException e) { e.printStackTrace(); }
//         }

//         sb.append("Th1 завершён, итоговая сумма: ").append(sum).append("\n\n");

//         synchronized (PrintLocks.LOCK_12) {
//             System.out.print(sb.toString());
//         }
//         latch.countDown();
//     }
// }

// class Th2 implements Runnable {
//     private final int[] arr;
//     private final CountDownLatch latch;

//     public Th2(int[] arr, CountDownLatch latch) {
//         this.arr = arr;
//         this.latch = latch;
//     }

//     @Override
//     public void run() {
//         StringBuilder sb = new StringBuilder();
//         sb.append("Th2: Сумма чётных чисел по два (с конца массива)\n");

//         int sum = 0;
//         for (int i = arr.length - 1; i > 0; i -= 2) {
//             if (arr[i] % 2 == 0 && arr[i - 1] % 2 == 0) {
//                 sum += arr[i] + arr[i - 1];
//                 sb.append("Th2 → ").append(arr[i - 1]).append(" + ").append(arr[i]).append(" = ").append(sum).append("\n");
//             }
//             try { Thread.sleep(30); } catch (InterruptedException e) { e.printStackTrace(); }
//         }

//         sb.append("Th2 завершён, итоговая сумма: ").append(sum).append("\n\n");

//         synchronized (PrintLocks.LOCK_12) {
//             System.out.print(sb.toString());
//         }
//         latch.countDown();
//     }
// }

// class Th3 implements Runnable {
//     private final CountDownLatch latch;

//     public Th3(CountDownLatch latch) {
//         this.latch = latch;
//     }

//     @Override
//     public void run() {
//         StringBuilder sb = new StringBuilder();
//         sb.append("Th3: Диапазон [100..500]\n");

//         for (int i = 100; i <= 500; i += 100) {
//             sb.append("Th3 → ").append(i).append("\n");
//             try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
//         }

//         sb.append("Th3 завершён.\n\n");

//         synchronized (PrintLocks.LOCK_34) {
//             System.out.print(sb.toString());
//         }
//         latch.countDown();
//     }
// }

// class Th4 implements Runnable {
//     private final CountDownLatch latch;

//     public Th4(CountDownLatch latch) {
//         this.latch = latch;
//     }

//     @Override
//     public void run() {
//         StringBuilder sb = new StringBuilder();
//         sb.append("Th4: Диапазон [700..300] (в обратном порядке)\n");

//         for (int i = 700; i >= 300; i -= 100) {
//             sb.append("Th4 → ").append(i).append("\n");
//             try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
//         }

//         sb.append("Th4 завершён.\n\n");

//         synchronized (PrintLocks.LOCK_34) {
//             System.out.print(sb.toString());
//         }
//         latch.countDown();
//     }
// }
=======
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
>>>>>>> f34c9ec232b8f29ad0d10f171fe5bed0def921ba
