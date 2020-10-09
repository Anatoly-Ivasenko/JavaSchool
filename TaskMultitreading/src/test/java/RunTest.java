import org.jschool.multithreading.ThreadPool;
import org.jschool.multithreading.ThreadPoolImpl;

public class RunTest {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPoolImpl();
        threadPool.start();
    }
}
