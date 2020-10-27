package ru.sbt.test.refactoring;

public enum Orientation {

	NORTH  {
		public Orientation turnClockwise() { return EAST; }
		public int[] moving(int[] position) { return new int [] {position[0], position[1] + 1};	}
	},
	EAST {
		public Orientation turnClockwise() { return SOUTH; }
		public int[] moving(int[] position) { return new int [] {position[0] + 1, position[1]};	}
	},
	SOUTH {
		public Orientation turnClockwise() { return WEST; }
		public int[] moving(int[] position) { return new int [] {position[0], position[1] - 1};	}
	},
	WEST {
		public Orientation turnClockwise() { return NORTH; }
		public int[] moving(int[] position) { return new int [] {position[0] - 1, position[1]};	}
	};

	public abstract Orientation turnClockwise();
	public abstract int[] moving(int[] position);
}
