import org.jschool.cachableList.cacheproxy.Cachable;
import org.jschool.cachableList.datasources.H2DB;

import java.util.List;

public interface Calculator {
    @Cachable(H2DB.class)
    List<Integer> fibonachi(int n);
}
