import java.io.IOException;
import java.util.List;

import maze.*;
import maze.Maze.Coordinate;
import maze.routing.*;
public class MazeDriver {
	
	public static void main(String args[]) throws IOException {
		Maze maze = Maze.fromTxt("C:\\Users\\oijfo\\eclipse-workspace\\MazeProject\\src\\testmaze.txt");
        System.out.print(maze.toString());
        RouteFinder fullroute = new RouteFinder(maze);
        
        while (true) {
        	boolean test = fullroute.step();
        	if (test == true) {
        		break;
        	}
        }
        
        List<Tile> testlist = fullroute.getRoute();
        System.out.println(testlist.get(0).toString());

        
    }

	
	
}
