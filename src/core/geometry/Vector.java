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
}
