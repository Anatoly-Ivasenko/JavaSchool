package org.jschool.cachablefibonachi;

public @interface Cachable {
    Class<? extends Source> value();
}
