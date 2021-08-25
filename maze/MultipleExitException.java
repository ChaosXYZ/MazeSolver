package maze;

public class MultipleExitException  extends InvalidMazeException {
		public MultipleExitException() {
			super("Multiple Exit Error");
	}
}