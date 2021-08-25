package maze;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
public class Maze implements Serializable {
    private Tile entrance = null;
    private Tile exit = null;
    private List<List<Tile>> tiles = new ArrayList<List<Tile>>();

    private Maze() {
        this.entrance = null;
    }

    public static Maze fromTxt(String path) throws IOException {
        Maze maze = new Maze();

        
        BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String str;
        List<String> list = new ArrayList<String>();
        while((str = in.readLine()) != null){
            list.add(str);
        }
        Collections.reverse(list);

        for (String k : list) {
            List<Tile> temptiles = new ArrayList<Tile>();
            char[] ch = k.toCharArray();
            for (int i = 0; i < k.length(); i++) {
                Tile temptilevar = Tile.fromChar(ch[i]);
                temptiles.add(temptilevar);
                if (ch[i] == 'e'){
                    maze.setEntrance(temptilevar);
                }
                if (ch[i] == 'x'){
                    maze.setExit(temptilevar);
                }
            }
            maze.tiles.add(temptiles);
        }
        int raggedcheck = maze.tiles.get(0).size();
        try {
			for (List k : maze.tiles) {
				if (k.size() != raggedcheck ) {
					throw new RaggedMazeException();
				}
			}
			if (maze.getEntrance() == null) {
				throw new NoEntranceException();
			}
			if (maze.getExit() == null) {
				throw new NoExitException();
			}
		} catch (RaggedMazeException e) {
			System.exit(1);
		} catch (NoEntranceException e2) {
			System.exit(1);
		} catch (NoExitException e3) {
			System.exit(1);
		}
        return maze;
    }

    public Coordinate getTileLocation(Tile tiletofind){
        for (int y = 0; y < tiles.size(); y++) {
            for (int x = 0; x < tiles.get(y).size(); x++) {
                if (tiles.get(y).get(x) == tiletofind) {
                    return new Coordinate(x, y);
                }
            }
        }
        return null;
    }

    public Tile getTileAtLocation(Coordinate pos){
        return tiles.get(pos.getY()).get(pos.getX());
    }

    public Tile getAdjacentTile(Tile curr, Direction dir) {
        Coordinate pos = getTileLocation(curr);
        if (dir == Direction.EAST) {
            return tiles.get(pos.getY()).get(pos.getX() + 1);
        } else if (dir == Direction.NORTH) {
            return tiles.get(pos.getY() +1).get(pos.getX());
        } else if (dir == Direction.SOUTH) {
            return tiles.get(pos.getY() -1).get(pos.getX());
        } else {
            return tiles.get(pos.getY()).get(pos.getX() - 1);
        }
    }

    public void setEntrance(Tile ent) {
    	try {
			if (this.entrance == null) {
				this.entrance = ent;
			} else {
				throw new MultipleEntranceException();
			}
		} catch (MultipleEntranceException e4) {
			System.exit(1);
		}
        
    }

    public void setExit(Tile ext) {
    	try {
			if (this.exit == null) {
				this.exit = ext;
			} else {
				throw new MultipleExitException();
			}
		} catch (MultipleExitException e3) {
			System.exit(1);
		}
        
    }

    public Tile getEntrance() {
        return entrance;
    }

    public Tile getExit() {
        return exit;
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }

    public String toString(){
        String mazeString = "";
        for (int i = tiles.size() - 1; i >= 0; i--) {
            for (Object colList : tiles.get(i)) {
                mazeString = mazeString + colList.toString();
            }
            mazeString = mazeString + "\n";
        }
        return mazeString;
    }

    public boolean ifTileExists(Direction dir, Tile currtile) {
        int x=getTileLocation(currtile).getX();
        int y=getTileLocation(currtile).getY();

        if (dir == Direction.SOUTH) {
            y=y-1;
        } else if (dir == Direction.NORTH) {
            y=y+1;
        } else if (dir == Direction.WEST) {
            x=x-1;
        } else {
            x=x+1;
        }

        if (y<0 || x<0 || y>=tiles.size() || x>= tiles.get(y).size()) {
            return false;
        } else {
            return true;
        }
    }

    public class Coordinate implements Serializable {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return (y);
        }

        public String toString() {
            return "("+x+", "+y+")";
        }
    }

    public enum Direction {
        NORTH,EAST,SOUTH,WEST;
    }

}
