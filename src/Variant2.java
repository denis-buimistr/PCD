import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Variant2 {
    
    private static int[] mas = new int[100];
    
    // Поток Th1: Суммы нечетных чисел по два, начиная с первого элемента
    static class Th1 implements Runnable {
        private List<Integer> results;
        
        public Th1(List<Integer> results) {
            this.results = results;
        }
        
        @Override
        public void run() {
            System.out.println("\n=== Поток Th1 запущен ===");
            System.out.println("Условие 1: Суммы нечетных чисел по два, начиная поиск и суммирование с первого элемента\n");
            
            int count = 0;
            int sum = 0;
            
            for (int i = 0; i < mas.length; i++) {
                if (mas[i] % 2 != 0) { // если число нечетное
                    sum += mas[i];
                    count++;
                    
                    if (count == 2) {
                        results.add(sum);
                        sum = 0;
                        count = 0;
                    }
                }
            }
            
            // Если осталось одно нечетное число
            if (count == 1) {
                results.add(sum);
            }
            
            System.out.println("Th1: Найдено пар нечетных чисел: " + results.size());
            System.out.println("Th1: Результаты суммирования: " + results);
            System.out.println("=== Поток Th1 завершен ===\n");
        }
    }
    
    // Метод для отображения текста с задержкой 100мс между буквами
    private static void displayWithDelay(String text) throws InterruptedException {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            Thread.sleep(100);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < mas.length; i++) {
            mas[i] = random.nextInt(100) + 1;
        }
        
        System.out.println("=== ЛАБОРАТОРНАЯ РАБОТА ===");
        System.out.println("Сгенерирована матрица mas[] размером 100 элементов");
        System.out.println("Значения от 1 до 100\n");
        System.out.print("Первые 20 элементов: ");
        for (int i = 0; i < 20; i++) {
            System.out.print(mas[i] + " ");
        }
        System.out.println("...\n");
        
        // Список для хранения результатов Th1
        List<Integer> resultsTh1 = new ArrayList<>();
        
        // Создание и запуск потока Th1
        Runnable task1 = new Th1(resultsTh1);
        Thread thread1 = new Thread(task1, "Th1");
        thread1.start();
        
        // Ожидание завершения потока
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n========================================");
        System.out.println("ПОТОК ЗАВЕРШЕН");
        System.out.println("========================================\n");
        
        try {
            displayWithDelay("Лабораторную работу выполнил:");
            displayWithDelay("Студент: Васильев Никита");
            displayWithDelay("Группа: CR-233");
            displayWithDelay("Дата: 09.10.2025");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== ПРОГРАММА ЗАВЕРШЕНА ===");
    }
}