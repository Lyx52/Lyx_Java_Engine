package core.geometry;

public class Vector {
    float
        x,y,z,w;

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 1;
    }
    public void setVector(Vector v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        this.w = v.getW();
    }
    //Getters setters
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    //Vector functions
    public Vector sub(Vector vect) {
        setVector(new Vector(x - vect.getX(), y - vect.getY(), z - vect.getZ()));
        return this;
    }
    public Vector add(Vector vect) {
        setVector(new Vector(x + vect.getX(), y+ vect.getY(), z + vect.getZ()));
        return this;
    }
    public Vector multiply(float k) {
        setVector(new Vector(x * k, y * k, z * k));
        return this;
    }
    public Vector divide(float k) {
        setVector(new Vector(x / k, y / k, z / k));
        return this;
    }
    public float DotProduct(Vector vect) {
        return x * vect.getX() + y * vect.getY() + z * vect.getZ();
    }
    public float DotProduct(Vector vect1,Vector vect2) {
        return vect1.getX() * vect2.getX() + vect1.getY() * vect2.getY() + vect1.getZ() * vect2.getZ();
    }
    public float length() {
        return (float)(Math.sqrt(DotProduct(this)));
    }
    public float dist(Vector plane_n, Vector plane_p) {
        normalize();
        return (plane_n.getX() * x + plane_n.getY() * y + plane_n.getZ() * z - DotProduct(plane_n, plane_p));
    }
    public Vector normalize() {
        float length = length();
        setVector(new Vector(x / length, y / length, z / length));
        return this;
    }
    public Vector CrossProduct(Vector vect1, Vector vect2) {
        Vector tempVector = new Vector(0,0,0);
        tempVector.setX(vect1.getY() * vect2.getZ() - vect1.getZ() * vect2.getY());
        tempVector.setY(vect1.getZ() * vect2.getX() - vect1.getX() * vect2.getZ());
        tempVector.setZ(vect1.getX() * vect2.getY() - vect1.getY() * vect2.getX());
        return tempVector;
    }
    public Vector CrossProduct(Vector vector2) {
        Vector tempVector = new Vector(0,0,0);
        tempVector.setX(y * vector2.getZ() - z * vector2.getY());
        tempVector.setY(z * vector2.getX() - x * vector2.getZ());
        tempVector.setZ(x * vector2.getY() - y * vector2.getX());
        setVector(tempVector);
        return this;
    }

}
