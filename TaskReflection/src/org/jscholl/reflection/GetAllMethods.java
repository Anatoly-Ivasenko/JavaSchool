package org.jscholl.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class GetAllMethods {

    public static void main(String[] args) {
        Class targetClass = TreeSet.class;

        System.out.println("Все методы (публичные и не публичные) класса \"" + targetClass.getName() + "\":");
        getAllDeclaredMethods(targetClass).forEach(System.out::println);  //Выводим все методы класса
        System.out.println();
        System.out.println("Унаслендованные методы:");

        do {                                                              //Рекурсивно перебираем "родителей"
            targetClass = targetClass.getSuperclass();                    //Получаем имя родителя
            System.out.println();
            System.out.println("Все методы (публичные и не публичные) класса \"" + targetClass.getName() + "\":");
            getAllDeclaredMethods(targetClass).forEach(System.out::println);  ////Выводим все методы класса родителя
        } while (targetClass != Object.class);                                //И так до Object
    }


    /**
     * Метод возвращает все (public, private & protected) методы указанного класса
     * в виде списка строк.
     * @param clazz Class
     * @return List
     */
    public static List<String> getAllDeclaredMethods(Class clazz) {
        List<String> allDeclaredMethods = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            allDeclaredMethods.add(method.getName());
        }
        return allDeclaredMethods;
    }
}
