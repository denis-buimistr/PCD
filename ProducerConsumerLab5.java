<<<<<<< HEAD:ProducerConsumerLab4.java
// import java.util.random.*;
// import java.util.HashMap;
// import java.util.Random;
// import java.util.concurrent.*;
// import java.util.concurrent.atomic.AtomicInteger;

// public class ProducerConsumerLab4 {
//     private static final int BUFFER_CAPACITY = 8;  // D (размер склада)
//     private static final int PRODUCER_COUNT = 2;   // X (кол-во производителей)
//     private static final int CONSUMER_COUNT = 3;   // Y (кол-во производителей)
//     private static final int CONSUMER_GOAL = 11;  // Z (кол-во объектов для каждого произвоителя)
//     private static final int TOTAL_OBJECTS = CONSUMER_COUNT * CONSUMER_GOAL;  // 33
=======
import java.util.random.*;
import java.util.HashMap;
import java.util.Random;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerLab4 {
    private static final int BUFFER_CAPACITY = 8;  // D (размер склада)
    private static final int PRODUCER_COUNT = 2;   // X (кол-во производителей)
    private static final int CONSUMER_COUNT = 3;   // Y (кол-во потребителя)
    private static final int CONSUMER_GOAL = 11;   // Z (кол-во объектов для каждого произвоителя)
    private static final int TOTAL_OBJECTS = CONSUMER_COUNT * CONSUMER_GOAL;  // 33
>>>>>>> af1d64f88fb7dcdde64a871e0f4504705c295301:ProducerConsumerLab5.java

//     private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_CAPACITY);

<<<<<<< HEAD:ProducerConsumerLab4.java
//     //Атомные счетчики
//     private static final AtomicInteger totalProduced = new AtomicInteger(0);
//     private static final AtomicInteger totalConsumed = new AtomicInteger(0);
//     private static final ConcurrentHashMap<Integer, AtomicInteger> consumerCounters = new ConcurrentHashMap<>();

//     public static void main(String[] args)  {
//         ExecutorService executor = Executors.newFixedThreadPool(PRODUCER_COUNT + CONSUMER_COUNT);
=======
    //Атомные счетчики
    private static final AtomicInteger totalProduced = new AtomicInteger(0);
    private static final AtomicInteger totalConsumed = new AtomicInteger(0);
    private static final ConcurrentHashMap<Integer, AtomicInteger> consumerCounters = new ConcurrentHashMap<>();
    public static void main(String[] args)  {
        ExecutorService executor = Executors.newFixedThreadPool(PRODUCER_COUNT + CONSUMER_COUNT);
>>>>>>> af1d64f88fb7dcdde64a871e0f4504705c295301:ProducerConsumerLab5.java
    
//         //Инициализируем счётчики потребителей
//         for (int i = 1; i <= CONSUMER_COUNT; i++ ) {
//             consumerCounters.put(i, new AtomicInteger(0));
//         }

//         //Запускаем производителей
//         for(int i = 1;  i <= PRODUCER_COUNT; i++) {
//             executor.execute(new Producer(i));
//         }

//         //Запускаем потребителей
//         for (int i = 1; i <= CONSUMER_COUNT; i++) {
//             executor.execute(new Consumer(i));
//         }

//         executor.shutdown();

<<<<<<< HEAD:ProducerConsumerLab4.java
//         try {
//             executor.awaitTermination(60,TimeUnit.SECONDS);
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
=======
        
        try {
            executor.awaitTermination(60,TimeUnit.SECONDS);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
>>>>>>> af1d64f88fb7dcdde64a871e0f4504705c295301:ProducerConsumerLab5.java

//         System.out.println("\n======== ИТОГОВЫЙ ОТЧЁТ ========");
//         System.out.println("Общее кол-во произведенных продуктов: " + totalProduced.get());
//         System.out.println("Общее кол-во потребленных продуктов: " +  totalConsumed.get());
//         consumerCounters.forEach((id, count) ->
//             System.out.println("Потребитель " + id + ": " + count.get() + "объектов потреблено"));
        
        
//     }


//     //Производитель 
    
//     static class Producer implements Runnable {
//         private final int id;
//         private final Random random = new Random();
    
//         Producer(int id) {
//             this.id = id;
//         }

//         @Override
//         public void run() {
//             try {
//                 while (totalProduced.get() < TOTAL_OBJECTS) {
//                     //генерируем два четных числа от 2 до 20
//                     int a = 2 + random.nextInt(10) * 2; //2,4,6, ... 20
//                     int b = 2 + random.nextInt(10) * 2; //

//                     buffer.put(a);
//                     buffer.put(b);

//                     int produced = totalProduced.addAndGet(2);

//                     System.out.printf("Производитель %d поместил %d и %d | Всего произведено: %d | Склад: %d/%d%n",
//                             id, a, b, produced, buffer.size(), BUFFER_CAPACITY);

//                     if (produced >= TOTAL_OBJECTS) {
//                         System.out.println("Производитель " + id + " завершил работу (цель достигнута)");
//                     }

<<<<<<< HEAD:ProducerConsumerLab4.java
//                     Thread.sleep(random.nextInt(300));
//                 }
//             } catch (Exception e) {
//                 Thread.currentThread().interrupt();
//             }
//         }
//     }
=======
                    Thread.sleep(random.nextInt(300));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
>>>>>>> af1d64f88fb7dcdde64a871e0f4504705c295301:ProducerConsumerLab5.java


//     //Потребитель
//     static class Consumer implements Runnable {
//         private final int id;
//         private final Random random = new Random();
    
//         Consumer(int id) {
//             this.id = id;
//         }

//       @Override
//       public void run() {
//         try {
//             while (consumerCounters.get(id).get() < CONSUMER_GOAL) {
//                 if (buffer.isEmpty()) {
//                     System.out.println(" Потребитель " + id + " — склад пуст, жду...");
//                 }

//                 int item = buffer.take();
//                 consumerCounters.get(id).incrementAndGet();
//                 int consumed = totalConsumed.incrementAndGet();

//                 System.out.printf("Потребитель %d взял %d | Лично взял: %d/%d | Всего потреблено: %d | Склад: %d/%d%n",
//                             id, item, consumerCounters.get(id).get(), CONSUMER_GOAL, consumed, buffer.size(), BUFFER_CAPACITY);

//                     Thread.sleep(random.nextInt(400));
//             }

//             System.out.println(" Потребитель " + id + " получил все " + CONSUMER_GOAL + " объектов и завершил работу!");

<<<<<<< HEAD:ProducerConsumerLab4.java
//         } catch (InterruptedException e ) {
//             Thread.currentThread().interrupt();
//         }
//         }
//     }
=======
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }
        }
    }
>>>>>>> af1d64f88fb7dcdde64a871e0f4504705c295301:ProducerConsumerLab5.java
    
    
// }


