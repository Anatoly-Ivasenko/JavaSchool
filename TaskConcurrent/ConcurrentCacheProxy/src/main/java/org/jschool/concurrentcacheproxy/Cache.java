package org.jschool.concurrentcacheproxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотоция для маркировки методов предназначенных для кэширования.
 * Содержит настройки cacheType - тип кэширования (в память или ФС) (по умолчанию в память JVM),
 * namePrefix - префикс ключа/имени файла для хранения кэша (по умолчанию используется имя метода),
 * zipped - сжимать или не сжимать кэш при хранении в ФС (по умолчанию не сжимать),
 * identityBy - определяет список классов аргументов метода для создания
 * ключа кэша, аргументы не указанных классов не будут уитываться при создании ключа
 * (по умолчанию будут учитываться все аргументы), listCapacity - для результатов имплементирующих List
 * в кэш будет сохранено указанное количесво элементов с начала списка (по умолчанию все элементы)
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    CacheType cacheType() default CacheType.IN_MEMORY;
    String namePrefix() default "";
    boolean zipped() default false;
    Class[] identityBy() default {};
    int listCapacity() default 0;
}
