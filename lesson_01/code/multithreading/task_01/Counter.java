package multithreading.task_01;


// Имеется целочисленный счётчик.
// Задание:
// При помощи одного потока увеличить миллион раз счётчик на единицу
// и вывести его значение в консоль.


public class Counter {
    private static int counter;
    public static void  main(String[] args) {
        for (int i = 0; i < 1_000_000; i++) {
            counter++;
        }
        System.out.println("Value of counter:" + counter);
    }
}
