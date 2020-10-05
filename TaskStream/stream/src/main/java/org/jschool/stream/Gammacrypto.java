package org.jschool.stream;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Класс содержащий статические методы для шифрования и дешифрования методом гаммирования.
 *
 * ОГРАНИЧЕНИЯ:
 * 1. Шифрование и дешифрование возможно ключом того же алфавита,
 * что и шифруемое (дешифруемое) сообщение. Сообщение(и ключ) не может содержать
 * символы различных алфавитов, в этом случае генерируется исключение.
 * 2. Шифрование и дешифрование проводится для строчных букв, сообщения и ключи приводятся
 * к строчным буквам.
 * 3. При шифровании (дешифровании) из сообщения и ключа исключаются все цифры, непечатаемые символы,
 * знаки препинания, спецсимволы и пробелы.
 */

public class Gammacrypto {
    private final static int CYR_FIRST_CHAR = 1072;
    private final static int CYR_LENGTH = 32;
    private final static int LAT_FIRST_CHAR = 97;
    private final static int LAT_LENGTH = 26;


    /**
     * Метод возвращает зашифрованное сообщение для указанного сообщения и ключа
     * @param message
     * @param key
     * @return
     */
    public static String encrypt (String message, String key) {
        message = prepare(message);
        key = prepare(key);

        boolean cyrillic = checkAlphabet(message, key);

        String finalKey = key;
        String finalMessage = message;

        return Stream.iterate(0, i -> i + 1)
                .limit(message.length())
                .map(i -> cryptSym(finalMessage.toCharArray()[i], finalKey.toCharArray()[i % finalKey.length()], cyrillic))
                .map(sym -> String.valueOf((char)((int)sym)))
                .reduce("", String::concat);
    }

    /**
     * Метод возвращает дешифрованное сообщение для указанного шифрованного сообщения и ключа
     * @param message
     * @param key
     * @return
     */
    public static String decrypt (String message, String key) {
        message = prepare(message);
        key = prepare(key);

        boolean cyrillic = checkAlphabet(message, key);

        String finalKey = key;
        String finalMessage = message;

        return Stream.iterate(0, i -> i + 1)
                .limit(message.length())
                .map(i -> decryptSym(finalMessage.toCharArray()[i], finalKey.toCharArray()[i % finalKey.length()], cyrillic))
                .map(sym -> String.valueOf((char)((int)sym)))
                .reduce("", String::concat);
    }

    /**
     * Метод возвращает строку для указанной строки, приводит к строчным буквам,
     * удаляет все цифры, непечатаемые символы, знаки препинания, спецсимволы и пробелы
     * @param text
     * @return
     */
    private static String prepare (String text) {
                return Stream.of(text.toLowerCase())
                .map(t -> t.split(""))
                .flatMap(Arrays::stream)
                .map(a -> (int)a.toCharArray()[0])
                .filter(sym -> checkSym(sym, true) || checkSym(sym, false))
                .map(sym -> String.valueOf((char)((int)sym)))
                .reduce("", String::concat);
    }

    /**
     * Метод проверяет сообщение и ключ и возвращает True для кирилицы и False для латиницы.
     *
     * Определение осуществляется по первому символу сообщения, затем текст сообщения и ключа
     * проверяется на содержание символов другого алфавита. В случае их наличия генерируется исключение.
     * @param message
     * @param key
     * @return
     */
    private static boolean checkAlphabet (String message, String key)  {
        boolean cyrillic = false;
        int firstChar = message.toCharArray()[0];
        if (firstChar >= CYR_FIRST_CHAR && firstChar < CYR_FIRST_CHAR + CYR_LENGTH) {
            cyrillic = true;
        }
        boolean finalCyrillic = cyrillic;
        if (!message.chars().allMatch(sym -> checkSym(sym, finalCyrillic))) {
            throw new RuntimeException("В тексте и/или ключе содержаться символы разных алфавитов");
        }
        if (!key.chars().allMatch(sym -> checkSym(sym, finalCyrillic))) {
            throw new RuntimeException("В тексте и/или ключе содержаться символы разных алфавитов");
        }
        return cyrillic;
    }

    /**
     * Метод возвращает true если указанный символ (код UTF-16)
     * относится к указанному алфавиту (cyrillic = true для кириллицы, cyrillic = false для латиницы)
     * @param sym
     * @param cyrillic
     * @return
     */
    private static boolean checkSym (int sym, boolean cyrillic) {
        if (cyrillic) {
            return sym >= CYR_FIRST_CHAR && sym < CYR_FIRST_CHAR + CYR_LENGTH;
        } else {
            return sym >= LAT_FIRST_CHAR && sym < LAT_FIRST_CHAR + LAT_LENGTH;
        }
    }

    /**
     * Метод шифрования для одного симвовла методом гаммирования
     *
     * @param messageSym
     * @param keySym
     * @param cyrillic
     * @return
     */
    private static int cryptSym (int messageSym, int keySym, boolean cyrillic) {
        int firstChar;
        int alphaLength;

        if (cyrillic) {
            firstChar = CYR_FIRST_CHAR;
            alphaLength = CYR_LENGTH;
        } else {
            firstChar = LAT_FIRST_CHAR;
            alphaLength = LAT_LENGTH;
        }

        return ((messageSym + keySym - 2 * firstChar) % alphaLength) + firstChar;
    }
    /**
     * Метод дешифрования для одного симвовла методом гаммирования
     *
     * @param messageSym
     * @param keySym
     * @param cyrillic
     * @return
     */
    private static int decryptSym (int messageSym, int keySym, boolean cyrillic) {
        int firstChar;
        int alphaLength;

        if (cyrillic) {
            firstChar = CYR_FIRST_CHAR;
            alphaLength = CYR_LENGTH;
        } else {
            firstChar = LAT_FIRST_CHAR;
            alphaLength = LAT_LENGTH;
        }

        int delta = (messageSym - keySym);
        if (delta >= 0) {
            return delta % alphaLength + firstChar;
        } else {
            return delta % alphaLength + firstChar + alphaLength;
        }
    }

//    @FunctionalInterface
//    public interface chSym {
//        boolean check(int sym, );
//    }
}
