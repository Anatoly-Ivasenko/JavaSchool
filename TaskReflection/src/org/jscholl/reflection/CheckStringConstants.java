package org.jscholl.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class CheckStringConstants {

    /**
     * Возвращает true если имена всех строковых(String) констант(public static final) класса указанного объекта
     * равны их значениям
     * @param object указанный объект
     * @return boolean true - если все имена всех строковых констант равны их значениям
     * @throws IllegalAccessException
     */
    public static boolean checkStringConstants(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getFields();
        for (Field field : fields) {
            if ((field.getModifiers() == (Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL)) &&
                    (field.getType() == String.class) && (!field.getName().equals(field.get(object).toString()))) {
                return false;
            }
        }
        return true;
    }
}
