import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс со статическим методом для генерации последовательности чисел Трибоначчи заданной длины
 */

public class Tribonacci {

    /**
     * Метод возвращает List содержащий последовательность чисел Трибоначчи указанной длины.
     *
     * Элементом потока (на этапе генерации) является массив из трех элементов long.
     * Генерирование осуществляется методом iterate, первым элементом потока является три единицы (три первых
     * числа последовательности Трибоначчи), функция итерации сдвигает второй и третий элементы массива
     * на первый и второй соответствено, а третий элемент рассчитывается как сумма всех элемнтов массива.
     * В последовательность выводится только первый элемент массива.
     * @param n int длина последовательности Трибоначчи
     * @return  List    Список с последовательностью Трибоначчи указанной длины
     */
    public static List<Long> tribonacci(int n) {
        return Stream.iterate(new long[]{1L, 1L, 1L}, i -> new long[]{i[1], i[2], i[0] + i[1] + i[2]})
                .limit(n)
                .map(i -> i[0])
                .collect(Collectors.toList());
    }
}
