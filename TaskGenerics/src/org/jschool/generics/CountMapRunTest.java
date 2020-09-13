package org.jschool.generics;

import java.util.HashMap;

public class CountMapRunTest {

    public static void main(String[] args) {
        CountMap<Integer> map1 = new CountMapImpl<>();
        map1.add(10);
        map1.add(10);
        map1.add(5);
        map1.add(6);
        map1.add(5);
        map1.add(5);
        map1.add(10);
        map1.add(10);

        CountMap<Integer> map2 = new CountMapImpl<>();
        map2.add(5);
        map2.add(5);
        map2.add(7);
        map2.add(6);
        map2.add(10);

        System.out.println("---Контейнер 1---");
        System.out.println("Количество уникальных элементов: " + map1.size());

        for(Object rawElement : map1.toMap().keySet()) {
            Integer element = (Integer) rawElement;
            System.out.println("Элемент \"" + element + "\" добавлен в 1-й контейнер " + map1.getCount(element) + " раз");
        }
        System.out.println();
        System.out.println("Контейнер 1: Удаляем элемент \"10\". Он был добавлен " + map1.remove(10) + " раз");
        System.out.println("Теперь контейнер 1 содержит элемент \"10\" в количестве " + map1.getCount(10));
        System.out.println();
        System.out.println("---Контейнер 2---");
        System.out.println("Количество уникальных элементов: " + map2.size());

        for(Object rawElement : map2.toMap().keySet()) {
            Integer element = (Integer) rawElement;
            System.out.println("Элемент \"" + element + "\" добавлен во 2-й контейнер " + map2.getCount(element) + " раз");
        }

        System.out.println();
        map1.addAll(map2);

        System.out.println("---Контейнер 1 после сложения с Контейнером 2---");
        System.out.println("Количество уникальных элементов: " + map1.size());

        for(Object rawElement : map1.toMap().keySet()) {
            Integer element = (Integer) rawElement;
            System.out.println("Элемент \"" + element + "\" добавлен в оба контейнера " + map1.getCount(element) + " раз");
        }

        System.out.println("------------------");
        System.out.println("Тест метода void toMap(Map destination)");

        HashMap<Number, Integer> mapDestination = new HashMap<>();
        map1.toMap(mapDestination);
        System.out.println(mapDestination);

        System.out.println("------------------");

        CountMap<String> map3 = new CountMapImpl<>();
        map3.add("Art");
        map3.add("Art");
        map3.add("Fast");
        map3.add("Grow");

        System.out.println("---Контейнер 3---");
        System.out.println("Количество уникальных элементов: " + map3.size());

        for(Object rawElement : map3.toMap().keySet()) {
            String element = (String) rawElement;
            System.out.println("Элемент \"" + element + "\" добавлен в 3-й контейнер " + map3.getCount(element) + " раз");
        }
    }
}
