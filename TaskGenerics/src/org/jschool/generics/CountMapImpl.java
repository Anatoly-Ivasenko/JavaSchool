package org.jschool.generics;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс контейнера для хранения и учета количества объектов типа T. Имплементирует интерфейс CountMap
 * @see org.jschool.generics.CountMap
 */
public class CountMapImpl<T> implements CountMap<T>  {

    /**
     * Основным хранилищем информации является объект типа java.util.Map, c объектами типа T в качестве ключей
     * и Integer в качестве значений.
     */
    private Map<T, Integer> countMap;

    /**
     * Конструктор объекта. Инициализирует хранилище и присваивает ему пустую HashMap.
     */
    public CountMapImpl() {
        this.countMap = new HashMap<>();
    }

    /**
     * Добавляет указанный объект типа T в контейнер.
     * Функция ищет в хранилище количество экземпляров указанного объекта,
     * если в хранилище нет экземпляров указанного объекта, создается запись в хранилище
     * с указанным объектом в качестве ключа и 1 в качестве значения,
     * если есть в хранилище значение, соответствующеее указанному объекту, увеличивается на 1.
     * @param element добавляемый в контейнер объект
     */
    public void add(T element) {
        countMap.compute(element, (elem, counter) -> (counter == null) ? 1 : counter + 1);
    }

    /**
     * Возвращает из хранилища значение, соответствующее ключу - указанному объекту.
     * @param element целевой объект
     * @return
     */
    public int getCount(T element) {
        return countMap.get(element);
    }

    /**
     * Возвращает из хранилища значение, соответствующее ключу - указанному объекту и уменьшает его на 1.
     * @param element целевой объект
     * @return
     */
    public int remove(T element) {
        Integer counter = countMap.getOrDefault(element, 0);
        countMap.put(element, counter - 1);
        return counter;
    }

    /**
     * Возвращает размер хранилища - количество различных объектов в контейнере.
     * @return размер хранилища - количество различных объектов в контейнере
     */
    public int size() {
        return countMap.size();
    }

    /**
     * Добавляет в контейнер все объекты указанного контейнера.
     * Указанный контейнер приводиться к java.util.Map, для возможности использования методов.
     * Затем каждомый объект, хранимый в указанном контейнере, ищется в текуще контейнере, если он есть,
     * то значения из текущего и указанного контейнера суммируются и помещаются в значение текущего контейнера,
     * если нет - то в текущем контейнере создается запись с объектом - ключом и значением из указанного контейнера.
     * Указанный контейнер должен содержать объекты типа T или любого наследуемого от T типа.
     * @param source  Контейнер объектов типа T или любого наследуемого от T типа
     */
    public void addAll(CountMap<? extends T> source) {
        Map<? extends T,Integer> addMap = (Map<? extends T, Integer>) source.toMap();
        addMap.forEach((addElement, addCounter) -> countMap.merge(addElement, addCounter, Integer::sum));
    }

    /**
     * Возвращает java.util.Map, содержащий ключами различные объекты типа T, хранимые в контейнере,
     * а значениями - количеством экземпляров этих объектов, хранимых в контейнере.
     * Поскольку внутри реализации хранилище является java.util.Map, возвращаем хранилище.
     * @return  java.util.Map, с ключамим - объектами и их количеством в качестве значений
     */
    public Map<T, Integer> toMap() {
        return countMap;
    }
    /**
     * Очищает указанный объект destination и заполняет его ключами - различными объектами, хранимыми в контейнере
     * и значениями - количеством экземпляров этих объектов, хранимых в контейнере.
     * Указанный объект должен быть java.util.Map (или его наследником) с ключами типа T или его "предками"
     * @param destination   указанный объект, в который записывает информация о хранимых в контейнере объектах
     */
    public void toMap(Map<? super T, Integer> destination) {
        destination.clear();
        countMap.forEach(destination::put);
    }
}
