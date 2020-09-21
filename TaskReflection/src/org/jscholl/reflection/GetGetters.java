package org.jscholl.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GetGetters {

    /**
     * Возвращает все геттеры класса в виде списка строк с наименованиями методов-геттеров
     * @param clazz
     * @return
     */
    public static List<String> getAllGetters (Class clazz) {
        List<String> allGetters = new ArrayList<>();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if ((method.getName().startsWith("get")) &&              //Проверяем, что имя метода начинается на "get"
                    (method.getParameterCount() == 0) &&             //Проверяем, что метод без входных параметров
                    (!void.class.equals(method.getReturnType()))) {  //Проверяем, что метод что-то возвращает
                allGetters.add(method.getName());
            }
        }
        return allGetters;
    }
}
