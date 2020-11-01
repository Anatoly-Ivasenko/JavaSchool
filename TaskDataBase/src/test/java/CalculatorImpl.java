import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculatorImpl implements Calculator {

    @Override
    public List<Integer> fibonachi(int n) {
        try {
            Thread.sleep(500);
            System.out.println("compute");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Stream.iterate(new int[]{1, 1}, i -> new int[]{i[1], i[0] + i[1]})
                .limit(n)
                .map(i -> i[0])
                .collect(Collectors.toList());
    }
}
