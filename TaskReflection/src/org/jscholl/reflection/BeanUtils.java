package org.jscholl.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanUtils {

    /**
     * Метод находит у объекта from все геттеры, а у объекта to соответствующие сеттеры,
     * если тип параметра сеттера является тем же классом или родительским классом по отношению
     * к классу возвращаемого значения соответствующего геттера, тогда вызывается этот сеттер объекта to
     * с параметром полученным в результате вызова геттера объекта from.
     *
     * Для поиска соответствия геттеров и сеттеров использованы Мапы.
     * Используются две мапы(String, Method), одна для геттеров, другая для сеттров,
     * ключами являются имена методов без "get" и "set" соответственно.
     * Поиск пересечений осуществляется в множестве Set(String) методом {@see retainAll}
     * Проверка совместимости осуществляется методом {@see isAssignableFrom} класса Class
     *
     * @param to   Объект, свойства которго будут устанавливаться
     * @param from Объект, чьи свойства будут использоваться для получения значений
     */
    public static void assign(Object to, Object from) throws Exception {
        Map<String, Method> gettersFrom = new HashMap<>();
        Map<String, Method> settersTo = new HashMap<>();
        for (Method method : from.getClass().getMethods()) {                  //Наполнение мапы геттеров
            if (method.getName().startsWith("get") && method.getParameterTypes().length == 0) {
                gettersFrom.put(method.getName().substring(3), method);
            }
        }
        for (Method method : to.getClass().getMethods()) {                    //Наполнение мапы сеттеров
            if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
                settersTo.put(method.getName().substring(3), method);
            }
        }
        Set<String> setFromTo = new HashSet<>(gettersFrom.keySet());
        setFromTo.retainAll(settersTo.keySet());                              //Получение пересечения геттеров и сеттеров
        for (String param : setFromTo) {
            Method getFromMethod = gettersFrom.get(param);
            Method setToMethod = settersTo.get(param);
            if (setToMethod.getParameterTypes()[0].isAssignableFrom(getFromMethod.getReturnType())) {    //Проверка совместимости классов
                setToMethod.invoke(to, getFromMethod.invoke(from));           //Установка значениия from -> to
            }
        }
    }
}

