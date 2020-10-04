import org.jschool.stream.Gammacrypto;
import org.jschool.stream.MyLambda;
import org.jschool.stream.Tribonacci;

public class RunTest {

    public static void main(String[] args) {
        final int CYR = 1071;

        //Тест MyLambda
        MyLambda<String> myLambda1 = (a, b) -> a + b;
        MyLambda<Integer> myLambda2 = (a, b) -> a + b;
        System.out.println(myLambda1.getSum("Привет, ", "МИР"));
        System.out.println(myLambda2.getSum(3,2));

        //Тест Tribonacci
        System.out.println("---Tribonacci---");
        Tribonacci.tribonacci(50).forEach(System.out::println);

        //Тест Gammacrypto
        System.out.println("---Gammacrypto---");
        String message = "Шифрование методом гаммирования";
        String key = "Пароль";
        String cryptoMessage = Gammacrypto.encrypt(message, key);
        System.out.println(cryptoMessage);
        System.out.println(Gammacrypto.decrypt(cryptoMessage, key));

    }
}
