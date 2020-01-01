package core.tile;

public abstract class Tile {
    public int
        x,y,
        width, height;
    public Tile(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
