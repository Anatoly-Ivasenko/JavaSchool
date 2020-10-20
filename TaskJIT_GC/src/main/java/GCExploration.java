import java.util.HashSet;
import java.util.Set;

public class GCExploration {

    public static void main(String[] args) {
        Set<StringBuilder> set = new HashSet<>();
        long c = 0;
        while (true) {
            StringBuilder a = new StringBuilder(Long.toString(c));
            if (c % 4 == 0) set.add(a);
            if (c%100000000 == 0) {
                System.out.println("----------------------------");
                set.clear();
            }
            c++;
//            System.out.println(c);
        }
    }

}
