// TASK_1 -> Суммы четных чисел по два, начинаяпоиск и суммирование с первого элемента

 class Variant1 extends Thread{  // суммирование с первого элемента 
      private int[] mas;
      private int sum;

 public Variant1(int[] mas) {
    this.mas = mas;
    this.sum = 0;
    }  

        @Override   
    public void run() {
        System.out.println("\n" + getName() + "Поиск и суммирование с первого элемента" );
        System.out.println("=".repeat(70));

        for(int i = 0; i < mas.length; i++) {
            if (mas[i] % 2 == 0) { // проверяю на чётность
                sum += mas[i];
                System.out.println();
            }

            try {
                Thread.sleep(1000); // сделал небольшую задержку для наглядности
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("=".repeat(70));
        System.out.println(getName() + "Сумма чётных: " + sum);
    }

    public int getSum() {
        return sum;
    }

}


// TASK_2 -> Суммы четных чисел по два, начиная поиск и суммирование с последнего элемента

     class Task2 extends Thread{  // суммирование с первого элемента 
      private int[] mas;
      private int sum;

 public Task2(int[] mas) {
    this.mas = mas;
    this.sum = 0;
    }  

        @Override
    public void run() {
        System.out.println("\n" + getName() + "Поиск и суммирование с первого элемента" );
        System.out.println("=".repeat(70));

        for(int i = mas.length - 1; i >= 0; i--) {
            if (mas[i] % 2 == 0) { // проверяю на чётность
                sum += mas[i];
                System.out.println();
            }

            try {
                Thread.sleep(1000); // сделал небольшую задержку для наглядности
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("=".repeat(70));
        System.out.println(getName() + "Сумма чётных: " + sum);
    }

    public int getSum() {
        return sum;
    }

}

