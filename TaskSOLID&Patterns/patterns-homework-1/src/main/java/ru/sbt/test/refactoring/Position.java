package ru.sbt.test.refactoring;

public interface Position {

    void moving(Orientation orientation, Field field);

    int[] getCoordinates();
}
