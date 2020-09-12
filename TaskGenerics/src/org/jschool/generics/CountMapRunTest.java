package org.jschool.generics;

public class CountMapRunTest {

    public static void main(String[] args) {
        CountMap<Integer> map = new CountMapImpl<>();
        map.add(10);
        map.add(10);
        map.add(5);
        map.add(5);
        map.add(5);
        map.add(10);
        map.add(10);
        map.add(11);
//        map.remove(10);

        CountMap<Integer> mapnew = new CountMapImpl<>();
        mapnew.add(5);
        mapnew.add(5);
        mapnew.add(7);
        mapnew.add(6);
        mapnew.add(10);
        mapnew.add(11);

        System.out.println("---Контейнер 1---");
        System.out.println("Количество уникальных элементов: " + map.size());

        for(Object rawElement : map.toMap().keySet()) {
            Integer element = (Integer) rawElement;
            System.out.println("Элемент \"" + element + "\" добавлен в 1-й контейнер " + map.getCount(element) + " раз");
        }

        System.out.println("---Контейнер 2---");
        System.out.println("Количество уникальных элементов: " + mapnew.size());

        for(Object rawElement : mapnew.toMap().keySet()) {
            Integer element = (Integer) rawElement;
            System.out.println("Элемент \"" + element + "\" добавлен во 2-й контейнер " + mapnew.getCount(element) + " раз");
        }

        map.addAll(mapnew);

        System.out.println("---Контейнер 1 после сложение с Контейнером 2---");
        System.out.println("Количество уникальных элементов: " + map.size());

        for(Object rawElement : map.toMap().keySet()) {
            Integer element = (Integer) rawElement;
            System.out.println("Элемент \"" + element + "\" добавлен в оба контейнера " + map.getCount(element) + " раз");
        }




    }
}
