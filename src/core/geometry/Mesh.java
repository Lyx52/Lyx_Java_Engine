package core.geometry;

public abstract class Mesh {
    Triangle[] mesh;
    public Mesh(Triangle[] mesh) {
        this.mesh = mesh;
    }
    public Triangle[] getMesh() {
        return mesh;
    }
}
