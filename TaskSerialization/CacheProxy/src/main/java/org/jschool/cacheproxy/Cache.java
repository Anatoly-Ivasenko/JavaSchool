package org.jschool.cacheproxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {               //TODO Расширить параметры аннотации
    CacheType cacheType() default CacheType.IN_MEMORY;
    String namePrefix() default "";
    boolean zipped() default false;
    Class[] identityBy() default {};
    int listCapacity() default 0;
}
