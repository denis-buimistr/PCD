//------------------DENIS MAIN------------------

// // метода для вывода текста с задержкой 100 мс
// public static void printWithDelay(String text, int delayMS) {
//     for (int i = 0; i < text.length(); i++) {
//         System.out.println(text.charAt(i));
//         try {
//             Thread.sleep(delayMS);
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
//     System.out.println();
// }

// public static void main(String[] args){
//     System.out.println("Лабораторная работа №1: Создание потоков");

//     // генерирую массив из 100 случайных чисел от 1 до 100

//     int[] mas = new int[5];
//     System.out.println("Создание массива из 100 элементов");
//     System.out.println("\n");
//     for(int i = 0; i < mas.length; i++) {
//         mas[i] = (int)(Math.random() * 100) + 1;
//     }

//     //вывод массива

//      System.out.println("Генерация массива из 100 элементов: ");

//       for (int i = 0; i < 5; i++) {
//         System.out.println(mas[i] + " ");
//       }
//       System.out.println("..\n");

//       //подсчёт чётных чисел для информации

//        int evenCount = 0;
//        int expectedSum  = 0;
//        for (int num : mas) {
//         if (num % 2 == 0) {
//             evenCount++;
//             expectedSum += num;
//         }
//        }

//        System.out.println("В массиве найдено чётных чисел: " + evenCount);
//        System.out.println("Ожидаемая сумма всех чётных: " + expectedSum);

//        System.out.println("\n" + "".repeat(70));
//        System.out.println("Запуск потоков" );

//        //создание потоков

//        Variant1 thread1 = new Variant1(mas);
//        Task2 thread2 = new Task2(mas);

//        //устанавливаем имена

//        thread1.setName("Поток Th1 [->]");
//        thread2.setName("Поток Th2 [<-]");

//        //Запуск потоков

//        thread1.start();
//        thread2.start();

//        //ожидание завершения обоих потоков

//        try {
//         thread1.join(); // главный поток ждёт завершения thread1
//         thread2.join(); // главный поток ждёт завершения thread2
//        } catch (InterruptedException e) {
//         e.printStackTrace();
//        }

//        //Вывод результатов

//        System.out.println("\n" + "".repeat(70));
//        System.out.println("Финальные результаты работы потоков");

//        System.out.println("Поток Th1.      Сумма = " + thread1.getSum());
//        System.out.println("Поток Th2.      Сумма = " + thread2.getSum());
//        System.out.println("Ожидаемая       Сумма = " + expectedSum);
//        System.out.println("Проверка многопоточности:  " + 
//        (thread1.getSum() == thread2.getSum() && 
//        thread1.getSum() == expectedSum ? "PASSED" : "FAILED"));
//  }

































//------------------NIKITA_MAIN------------------