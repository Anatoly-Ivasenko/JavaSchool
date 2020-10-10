import org.jschool.multithreading.ThreadPool;
import org.jschool.multithreading.ThreadPoolImpl;

import java.util.List;

public class RunTest {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPoolImpl();
        threadPool.start();


        for (int i = 0; i < 200; i++) {
            System.out.print("|");
            int finalI = i;
            threadPool.execute(() -> {
                List<Long> triboResult = Tribonacci.tribonacci(finalI);
                System.out.println(triboResult.get(triboResult.size()));
            });
        }
    }
}

