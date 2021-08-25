package maze;

public class NoEntranceException  extends InvalidMazeException {
		public NoEntranceException() {
			super("Null Entrance Error");
	}
}