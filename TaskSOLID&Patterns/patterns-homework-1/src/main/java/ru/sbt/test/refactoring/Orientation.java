package ru.sbt.test.refactoring;

public enum Orientation {

	NORTH  {
		public Orientation turnClockwise() { return EAST; }
	},
	EAST {
		public Orientation turnClockwise() { return SOUTH; }
	},
	SOUTH {
		public Orientation turnClockwise() { return WEST; }
	},
	WEST {
		public Orientation turnClockwise() { return NORTH; }
	};

	public abstract Orientation turnClockwise();
}
