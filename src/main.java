public class main { 
    public static void main(String[] args) {

        //создаю главную группу
        ThreadGroup mainGroup  = Thread.currentThread().getThreadGroup();

        // создаю подгруппы

        ThreadGroup g1 = new ThreadGroup(mainGroup, "G1");
        ThreadGroup g2 = new ThreadGroup(mainGroup, "G3");
        ThreadGroup g3 = new ThreadGroup(mainGroup, "G2");

        //потоки G3 
        Thread Tha = new denisHardVariant(g3, "Tha", 3);
        Thread Thb = new denisHardVariant(g3, "Thb", 3);
        Thread Thc = new denisHardVariant(g3, "Thc", 3);
        Thread Thd = new denisHardVariant(g3, "Thd", 3);

        //потоки G2

        Thread Th1_g2 = new denisHardVariant(g2, "Th1", 4);
        Thread Th2_g2 = new denisHardVariant(g2, "Th2", 4);
        Thread Th3_g2 = new denisHardVariant(g2, "Th3", 4);

        //Потоки G1

        Thread th1_g1 = new denisHardVariant(g1, "Th1", 7);
        Thread th1_g2 = new denisHardVariant(g1, "Th2", 7);
        Thread th1_g3 = new denisHardVariant(g1, "ThA", 3);

        // запускаю все потоки 

        Tha.start(); Thb.start(); Thc.start(); Thd.start();
        Th1_g2.start(); Th2_g2.start(); Th3_g2.start();
        th1_g1.start(); th1_g2.start(); th1_g3.start();
        
       
        // вызываем никиты вариант
        nikitaMediumVariant.runVariant();
    }



}