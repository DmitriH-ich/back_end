package homework_01_inL02.task_02;

import homework_01_inL02.task_01.SingleThread;

public class DualThread {

    private static int counter;

    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();

        for (int i = 0; i < 1_000_000; i++) {
            if (i % 21 == 0 && String.valueOf(i).contains("3")) {
                counterInc();
            }

        }
            try {
                myThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        System.out.println("Сумма в два потока " + counter);

        // Compare results with SingleThread
        int singleThreadCounter = SingleThread.runSingleThreadTask();
        System.out.println("Сумма в один поток " + singleThreadCounter);

        if (counter == singleThreadCounter) {
            System.out.println("Результаты task_01 и task_02 совпадают.");
        } else {
            System.out.println("Сумма не совпадает.");
        }
    }
    public static synchronized void counterInc() {
        counter++;
    }
}
