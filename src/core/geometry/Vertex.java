package core.geometry;

public class Vertex {

    public static final int
        size = 8;

    private Vector
        pos,
        textureCords,
        normal;

    public Vertex(Vector pos) {
        this(pos,new Vector(0,0));
    }
    public Vertex(Vector pos, Vector textureCords) {
        this(pos,textureCords, new Vector(0,0,0));
    }
    public Vertex(Vector pos, Vector textureCords, Vector normal) {
        this.pos = pos;
        this.textureCords = textureCords;
        this.normal = normal;
    }


    //GET
    public Vector getPos() {
        return pos;
    }
    public Vector getNormal() {
        return normal;
    }
    public Vector getTextureCords() {
        return textureCords;
    }

    //SET
    public void setPos(Vector pos) {
        this.pos = pos;
    }
    public void setNormal(Vector normal) {
        this.normal = normal;
    }
    public void setTextureCords(Vector textureCords) {
        this.textureCords = textureCords;
    }
}
