import org.jschool.memorymodel.Task;

import java.util.List;

public class RunTest {
    public static void main(String[] args) {
        Task<List<Long>> task = new Task<>(() -> Tribonacci.tribonacci(3));
        for (int i = 0; i < 10; i++) {
            System.out.println();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            (new Thread(() -> task.get().forEach(System.out::print))).start();
        }
    }
}
