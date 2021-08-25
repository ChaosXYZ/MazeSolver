package maze.routing;
import maze.Maze;
import java.io.*;
import maze.Tile;
import maze.Tile.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.io.Serializable;


public class RouteFinder implements Serializable {
    private Maze maze;
    private Stack<Tile> route = new Stack<Tile>();
    private boolean finished = false;

    public RouteFinder(Maze mazetosolve){
        this.maze = mazetosolve;
        
        route.push(maze.getEntrance());
    }

    public List<Tile> getRoute(){
        Stack<Tile> tempr =  new Stack<Tile>();
        tempr.addAll(route);
        List<Tile> tempList = new ArrayList<Tile>();
        while(!tempr.isEmpty()) {
            tempList.add(tempr.pop());
        }
        Collections.reverse(tempList);
        return tempList;
    }
    
    public boolean step(){
    	route.peek().setTrav(true);
    	if (route.peek().getType() == Tile.Type.EXIT) {
    		return true;
    	}
    	if (route.peek().getType() == Tile.Type.CORRIDOR || route.peek().getType() == Tile.Type.ENTRANCE) {
    		route.peek().setCol(true);
    	}

        if (maze.ifTileExists(Maze.Direction.SOUTH, route.peek())) {
            if (maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH).getType() == Tile.Type.EXIT) {
                route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH));
                finished = true;
                return true;
            } else if(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH).getType() == Tile.Type.CORRIDOR && maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH).getTrav()==false) {
                route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.SOUTH));
                return false;
            }
        }

        if (maze.ifTileExists(Maze.Direction.NORTH, route.peek())) {
            if (maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH).getType() == Tile.Type.EXIT) {
                route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH));
                finished = true;
                return true;
            } else if(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH).getType() == Tile.Type.CORRIDOR && maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH).getTrav()==false) {
                route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.NORTH));
                return false;
            }
        }

        if (maze.ifTileExists(Maze.Direction.WEST, route.peek())) {
            if (maze.getAdjacentTile(route.peek(), Maze.Direction.WEST).getType() == Tile.Type.EXIT) {
                route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST));
                finished = true;
                return true;
            } else if(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST).getType() == Tile.Type.CORRIDOR && maze.getAdjacentTile(route.peek(), Maze.Direction.WEST).getTrav()==false) {
                route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.WEST));
                return false;
            }
        }
        
        if (maze.ifTileExists(Maze.Direction.EAST, route.peek())) {
            if (maze.getAdjacentTile(route.peek(), Maze.Direction.EAST).getType() == Tile.Type.EXIT) {
                route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST));
                finished = true;
                return true;
            } else if(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST).getType() == Tile.Type.CORRIDOR && maze.getAdjacentTile(route.peek(), Maze.Direction.EAST).getTrav()==false) {
                route.push(maze.getAdjacentTile(route.peek(), Maze.Direction.EAST));
                return false;
            }
        }
    	if (route.peek().getType() == Tile.Type.CORRIDOR) {
    		route.peek().setCol(false);
    	}
        route.pop();
        try {
			if (route.size() == 0) {
				finished = true;
				throw new NoRouteFoundException("str");
				
			}
		} catch (NoRouteFoundException e) {
			// TODO Auto-generated catch block
			return finished;
		}
        
        
        if (finished == true) {
        	return true;
        } else {
        	return false;
        }
    }
    
    
    public void back() {
    	if (route.peek().getType() != Tile.Type.ENTRANCE) {
        	route.peek().setTrav(false);
        	route.peek().setCol(false);
        	route.pop();
    	}
    }
    
    public boolean isFinished() {
    	return this.finished;
    }
    
    public static HashMap<Maze, RouteFinder> loader(String fileName) throws IOException, ClassNotFoundException{
        try {
            FileInputStream fileInStr = new FileInputStream(fileName);
            FileInputStream fileInStr2 = new FileInputStream("mazedata");
            ObjectInputStream objInStr = new ObjectInputStream(fileInStr);
            ObjectInputStream objInStr2 = new ObjectInputStream(fileInStr2);
            RouteFinder loadedRoute = (RouteFinder) objInStr.readObject();
            Maze loadedmaze=(Maze) objInStr2.readObject();
            objInStr.close();
            fileInStr.close();
            objInStr2.close();
            fileInStr2.close();
            HashMap<Maze, RouteFinder> classobjects = new HashMap<Maze, RouteFinder>();
            classobjects.put(loadedmaze, loadedRoute);
            return classobjects;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static HashMap<Maze, RouteFinder> loaded(String fileName) throws IOException, ClassNotFoundException{
        try {
            FileInputStream fileInStr = new FileInputStream(fileName);
            FileInputStream fileInStr2 = new FileInputStream("tmpm");
            ObjectInputStream objInStr = new ObjectInputStream(fileInStr);
            ObjectInputStream objInStr2 = new ObjectInputStream(fileInStr2);
            RouteFinder loadedRoute = (RouteFinder) objInStr.readObject();
            Maze loadedmaze=(Maze) objInStr2.readObject();
            objInStr.close();
            fileInStr.close();
            objInStr2.close();
            fileInStr2.close();
            HashMap<Maze, RouteFinder> classobjects = new HashMap<Maze, RouteFinder>();
            classobjects.put(loadedmaze, loadedRoute);
            return classobjects;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static RouteFinder load(String fileName) throws IOException, ClassNotFoundException{
        try {
            FileInputStream fileInStr = new FileInputStream(fileName);
            ObjectInputStream objInStr = new ObjectInputStream(fileInStr);
            RouteFinder loadedRoute = (RouteFinder) objInStr.readObject();
            objInStr.close();
            fileInStr.close();
            return loadedRoute;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    


    public void save(String fileName) throws IOException {
        try {
            FileOutputStream fileOutStr = new FileOutputStream(fileName);
            FileOutputStream fileOutStr2 = new FileOutputStream("mazedata");
            ObjectOutputStream objOutStr = new ObjectOutputStream(fileOutStr);
            ObjectOutputStream objOutStr2 = new ObjectOutputStream(fileOutStr2);
            objOutStr.writeObject(this);
            objOutStr2.writeObject(maze);
            objOutStr.close();
            objOutStr2.close();
            fileOutStr2.close();
            fileOutStr.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void saver(String fileName) throws IOException {
        try {
            FileOutputStream fileOutStr = new FileOutputStream(fileName);
            FileOutputStream fileOutStr2 = new FileOutputStream("tmpm");
            ObjectOutputStream objOutStr = new ObjectOutputStream(fileOutStr);
            ObjectOutputStream objOutStr2 = new ObjectOutputStream(fileOutStr2);
            objOutStr.writeObject(this);
            objOutStr2.writeObject(maze);
            objOutStr.close();
            objOutStr2.close();
            fileOutStr2.close();
            fileOutStr.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public String toString() {
    	return "To-do : get the route and return it as a string";
    }
    
 
}
