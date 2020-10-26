package ru.sbt.test.refactoring;

public class Position2D implements Position{
    private int x;
    private int y;

    public Position2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void moving(Orientation orientation, Field field) {
        if (orientation == Orientation.NORTH) {
            y++;
        } else if (orientation == Orientation.EAST) {
            x++;
        } else if (orientation == Orientation.SOUTH) {
            y--;
        } else if (orientation == Orientation.WEST) {
            x--;
        }
        if (x < 0 || x > field.getSize()[0] || y < 0 || y > field.getSize()[1]) {
            throw new TractorInDitchException();
        }
    }

    @Override
    public int[] getCoordinates() {
        return new int[] { x, y };
    }
}
