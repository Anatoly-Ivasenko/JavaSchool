import java.io.FileReader;
import java.util.*;

public class TextFileAnalyzer {

    public static class WordComparator implements Comparator<String> {
        /* Компаратор, который применяется для сортировки слов (String) по длине, а затем лексикографически.
        Используется для задания 2 */

        @Override
        public int compare(String obj1, String obj2) {
            if ((obj1.length() - obj2.length()) == 0) {
                return obj1.compareTo(obj2);
            }
            else return (obj1.length() - obj2.length());
        }
    }

    public static class LinesIterator implements Iterator<Object> {
        /* Итератор, для обхода списка в обратном порядке. Используется для задания 5 */

        private List<String> list;
        private int counter;

        public LinesIterator(List<String> list) {
            this.list = list;
            counter = list.size();
        }

        @Override
        public boolean hasNext() {
            return counter > 0;
        }

        @Override
        public Object next() {
            counter --;
            return list.get(counter);
        }
    }

    static ArrayList<String> FileToLines(String file) throws Exception {
        /* Метод для чтения данных из файла
           Формирует из текстового файла список строк
           Необходим для всех заданий */

        ArrayList<String> lines = new ArrayList<>();
        FileReader inputFile = new FileReader(file);
        Scanner scanFile = new Scanner(inputFile);
        while (scanFile.hasNext()) {
            lines.add(scanFile.nextLine().trim());
        }
        inputFile.close();

        for (String line : lines) {
            System.out.println(line);
        }

        return lines;
    }

    static TreeMap<String, Integer> GetWordsCount(ArrayList<String> lines) {
        /* Метод, который из списка строк формирует TreeMap<Слово,Кол-во вхождений>,
        отсортированный по длине слова, затем лексикографически.
        Позволяет за один проход по тексту решить задания 1, 2 и 3.
        Используется собственный компаратор WordComparator(см. выше) */

        TreeMap<String, Integer> wordsCounter = new TreeMap<>(new WordComparator());
        for (String line : lines) {
            for (String nextWord : line.split("([ ,.\"])")) {
                if (nextWord.length() > 0) {
                    String word = nextWord.toLowerCase();
                    if (!wordsCounter.containsKey(word)) {
                        wordsCounter.put(word, 1);
                    } else {
                        Integer wordCounter = wordsCounter.get(word);
                        wordCounter++;
                        wordsCounter.put(word, wordCounter);
                    }
                }
            }
        }
        return wordsCounter;
    }

    public static void main(String[] args) throws Exception {

        ArrayList<String> lines = FileToLines("src/file.txt");

        System.out.println("-----------Task 1--------------");
        System.out.println("В тексте встречается " + GetWordsCount(lines).size() + " разных слов");

        System.out.println("-----------Task 2--------------");
        System.out.println("Вот список слов, которые есть в тексте, кстати они отсортированы по длине и алфавиту");
        for (String word : GetWordsCount(lines).keySet()) {
            System.out.println(word);
        }

        System.out.println("-----------Task 3--------------");
        System.out.println("Ниже указано сколько раз каждое слово встречается в тексте");
        for (Map.Entry<String, Integer> entry : GetWordsCount(lines).entrySet()) {
            System.out.print("Слово \"" + entry.getKey()+"\" встречается в тексте " + entry.getValue() + " раз");
            System.out.println();
        }

        System.out.println("--------Task 4a (for)------------");
        System.out.println("Вывод текста в обратном порядке строк");     //Для вывода списка строк в обратном порядке
        for (int i = lines.size() - 1; i >= 0; i--) {                    //используется цикл for. Задание 4
            System.out.println(lines.get(i));
        }

        System.out.println("--------Task 4b (ListIterator)-----------");
        System.out.println("Вывод текста в обратном порядке строк");             //Для вывода списка строк в обратном порядке
        ListIterator<String> linesIterator = lines.listIterator(lines.size());   //используется ListIterator. Задание 4
        while(linesIterator.hasPrevious()) {
            System.out.println(linesIterator.previous());
        }
        System.out.println("--------Task 5 (My Iterator)-----------");
        System.out.println("Вывод текста в обратном порядке строк");             //Для вывода списка строк в обратном порядке
        LinesIterator myLinesIterator = new LinesIterator(lines);                //используется свой итератор (см. выше)
        while (myLinesIterator.hasNext()) {                                      //Задание 5
            System.out.println(myLinesIterator.next());
        }
        System.out.println("-----------Task 6--------------");
        System.out.println("Вывод строк по номерам");
        while (true) {
            System.out.print("Введите номер строки, или \"0\" чтобы завершить:");
            Scanner input = new Scanner(System.in);
            try {
                int inputLine = input.nextInt() - 1;
                if (inputLine == -1) {
                    System.out.print("До свидания");
                    break;
                }
                if (inputLine >= 0 && inputLine < (lines.size())) {
                    System.out.println(lines.get(inputLine));                    //Вывод строки по номеру индекса
                }                                                                //Задание 6
                else System.out.println("___Нет_такой_строки___");
            }
            catch (InputMismatchException e) {
                System.out.println("___Похоже_это_не_целое число___");
            }
        }
    }
}