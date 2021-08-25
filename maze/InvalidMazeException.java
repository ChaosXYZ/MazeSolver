package maze;

public class InvalidMazeException extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidMazeException(String error) {
		super(error);
	}

}
