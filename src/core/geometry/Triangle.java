package core.geometry;

public class Triangle {
    Vector[] points;

    public Triangle(Vector p1, Vector p2, Vector p3) {
        points = new Vector[]{p1,p2,p3};
    }
    public Vector getFirst() {
        return points[0];
    }
    public Vector getSecond() {
        return points[1];
    }
    public Vector getThird() {
        return points[2];
    }
    public Vector[] getVectors() {
        return points;
    }
}

