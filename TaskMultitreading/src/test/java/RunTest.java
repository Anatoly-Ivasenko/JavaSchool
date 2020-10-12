import org.jschool.multithreading.ScalableThreadPool;
import org.jschool.multithreading.ThreadPool;
import org.jschool.multithreading.FixedThreadPool;

import java.util.List;

public class RunTest {

    public static void main(String[] args) {
//        ThreadPool threadPool = new FixedThreadPool(3);
        ThreadPool threadPool = new ScalableThreadPool(2, 5);
        threadPool.start();


        for (int i = 1; i < 21; i++) {
            int finalI = i;
            Runnable task = () -> {
                List<Long> triboResult = Tribonacci.tribonacci(finalI);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("I'm busy");
                }
                System.out.println("Tribo(" + finalI + ")=" + triboResult.get(triboResult.size()-1));
            };
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPool.execute(task);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 21; i < 41; i++) {
            int finalI = i;
            Runnable task = () -> {
                List<Long> triboResult = Tribonacci.tribonacci(finalI);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("I'm busy");
                }
                System.out.println("Tribo(" + finalI + ")=" + triboResult.get(triboResult.size()-1));
            };
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPool.execute(task);
        }
    }
}

