package ru.sbt.test.refactoring;

public class Tractor {

	private final Position position = new Position2D(0,0);
	private final Field field = new Field2D(5,5);
	private Orientation orientation = Orientation.NORTH;

	public void move(String command) {
        if (command.equals("F")) {
			position.moving(orientation, field);
		} else if (command.equals("T")) {
			orientation = orientation.turnClockwise();
		}
	}

	public int getPositionX() {
		return position.getCoordinates()[0];
	}

	public int getPositionY() {
		return position.getCoordinates()[1];
	}

	public Orientation getOrientation() {
		return orientation;
	}

}
