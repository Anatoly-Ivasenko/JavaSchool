import org.jschool.stream.Gammacrypto;
import org.jschool.stream.MyLambda;
import org.jschool.stream.MyStream;
import org.jschool.stream.Tribonacci;

public class RunTest {

    public static void main(String[] args) {

        //Тест MyLambda
        MyLambda<String> myLambda1 = (a, b) -> a + b;
        MyLambda<Integer> myLambda2 = (a, b) -> a + b;
        System.out.println(myLambda1.getSum("Привет, ", "МИР"));
        System.out.println(myLambda2.getSum(3,2));

        //Тест Tribonacci
        System.out.println("---Tribonacci---");
        Tribonacci.tribonacci(7).forEach(System.out::println);

        //Тест Gammacrypto
        System.out.println("---Gammacrypto---");
        String message = "Шифрование методом гаммирования";
        String key = "Пароль";
        String cryptoMessage = Gammacrypto.encrypt(message, key);
        System.out.println(cryptoMessage);
        System.out.println(Gammacrypto.decrypt(cryptoMessage, key));

        //Тест MyStream
        System.out.println("---MyStream---");
        MyStream<Integer> integerStream = MyStream.of(1,2,2,3,4,5,6,7,8,8,9)
                .filter(i -> i%2 == 0)
                .map(i -> i*i)
                .distinct();
        integerStream.forEach(System.out::println);

        MyStream<String> stringStream = MyStream.of("1,2,2,3,4,5,6,7,8,8,9","Gthsd","dsjfkh sdkjhfl")
                .map(i -> i.toLowerCase())
                .map(i -> i.replaceAll("[, ]",""));
        stringStream.forEach(System.out::println);


    }
}
