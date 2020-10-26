package ru.sbt.test.refactoring;

public class Field2D implements Field {

    private final int xSize;
    private final int ySize;

    public Field2D(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public int[] getSize() {
        return new int[] { xSize, ySize };
    }
}
