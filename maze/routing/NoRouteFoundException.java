package maze.routing;

public class NoRouteFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public NoRouteFoundException(String error) {
		super("No Route");
	}

}

