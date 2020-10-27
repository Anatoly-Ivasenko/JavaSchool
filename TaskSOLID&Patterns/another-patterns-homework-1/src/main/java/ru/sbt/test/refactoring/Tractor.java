package ru.sbt.test.refactoring;

public class Tractor {

	private int[] position = new int[] {0, 0};
	private final int[] field = new int[] {5, 5};
	private Orientation orientation = Orientation.NORTH;

	public void move(String command) {
        if (command.equals("F")) {
			position = orientation.moving(position);
			checkPosition();
		} else if (command.equals("T")) {
			orientation = orientation.turnClockwise();
		}
	}

	public int getPositionX() {
		return position[0];
	}

	public int getPositionY() {
		return position[1];
	}

	public Orientation getOrientation() {
		return orientation;
	}

	private void checkPosition() {
		int x = position[0];
		int y = position[1];
		if (x < 0 || x > field[0] || y < 0 || y > field[1]) {
			throw new TractorInDitchException();
		}
	}
}
