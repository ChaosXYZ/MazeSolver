package maze;

import java.io.Serializable;

public class Tile implements Serializable {
    private Type type;
    private boolean trav;
    private boolean col = false;
    private Tile(Type tile) {
        this.type=tile;
    }

    protected static Tile fromChar(char x) {
        if (x == '.') {
            return new Tile(Type.CORRIDOR);
        } else if (x == 'e') {
            return new Tile(Type.ENTRANCE);
        } else if (x == 'x') {
            return new Tile(Type.EXIT);
        } else if (x == '#') {
            return new Tile(Type.WALL);
        } else {
        	return null;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTrav(boolean b) { this.trav = b; }
    public boolean getTrav() { return this.trav;}
    public void setCol(boolean b) { this.col = b; }
    public boolean getCol() { return this.col;}

    public boolean isNavigable() {
        if (this.type == Type.WALL || this.trav == true) {
            return false;
        } else {
            return true;
        }
    }

    public String toString() {
        if (this.type == Type.WALL) {
            return "#";
        } else if (this.type == Type.ENTRANCE) {
            return "e";
        } else if (this.type == Type.EXIT) {
            return "x";
        } else {
            return ".";
        }
    }

    public enum Type {
        CORRIDOR,ENTRANCE,EXIT,WALL;
    }
}

