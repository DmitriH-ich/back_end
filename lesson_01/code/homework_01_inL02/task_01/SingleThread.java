package homework_01_inL02.task_01;
public class SingleThread {

    private static int counter;

    public static void main(String[] args) {
        int result = runSingleThreadTask();
        System.out.println("Сумма в один поток" + " " + result);
    }

    public static int runSingleThreadTask() {
        counter = 0; // Reset counter
        for (int i = 0; i < 2_000_000; i++) {
            if (i % 21 == 0 && String.valueOf(i).contains("3")) {
                counter++;
            }
        }
        return counter;
    }
}
