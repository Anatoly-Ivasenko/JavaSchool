import java.util.Scanner;

public class BubbleSorting {
    public static void main(String[] args) {
        // Запрос длины массива, его объявление, объявление "буфферного" элемента
        System.out.println("Введите количество элементов массива");
        Scanner in = new Scanner(System.in);
        long[] myArray = new long[in.nextInt()];
        long bufferElement;

        //Заполнение массива случайными числами и его вывод в консоль
        System.out.println("Заполнение массива случайными числами от 0 до 100");
        System.out.println("Исходный массив (" + myArray.length + " элементов):");
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = (int) (Math.random()*100);
            System.out.print(myArray[i]+" ");
        }

        //Сортировка
        System.out.println("");
        System.out.println("Сортировка");
        for (int j = myArray.length-1; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                if (myArray[i] > myArray[i + 1]) {
                    bufferElement = myArray[i];
                    myArray[i] = myArray[i + 1];
                    myArray[i + 1] = bufferElement;
                }
            }
        }

        //Вывод сортированного массива
        System.out.println("Сортированный массив:");
        for (int i = 0; i < myArray.length; i++) {
            System.out.print(myArray[i]+" ");
        }
    }
}
