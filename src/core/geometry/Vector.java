package core.geometry;

public class Vector {
    public float
        x,y,z,w;
    public Vector(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
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

    public Vector rotate(float theta) {
        double
            radians = Math.toRadians(theta),
            cos = Math.cos(radians),
            sin = Math.sin(radians);

        return new Vector((float) (x * cos - y * sin), (float)(x * sin + y * cos));
    }
    public Vector rotate3D(float theta, Vector axis) {
        float sinHalfAngle = (float)Math.sin(Math.toRadians(theta / 2));
        float cosHalfAngle = (float)Math.cos(Math.toRadians(theta / 2));

        float rX = axis.getX() * sinHalfAngle;
        float rY = axis.getY() * sinHalfAngle;
        float rZ = axis.getZ() * sinHalfAngle;
        Vector rotation = new Vector(rX, rY, rZ, cosHalfAngle);
        Vector conjugate = rotation.inverse();

        Vector w = rotation.multiply(this.multiply(conjugate));

        return new Vector(w.getX(), w.getY(), w.getZ());
    }
    public float dot(Vector vect) {
        return x * vect.getX() + y + vect.getY() + z * vect.getZ();
    }
    public Vector cross(Vector vect) {
        float
            newX = x * vect.getZ() - z * vect.getY(),
            newY = y * vect.getX() - x * vect.getZ(),
            newZ = z * vect.getY() - y * vect.getX();
        return new Vector(newX, newY, newZ);
    }
    public float length() {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }
    public Vector normalize() {
        float length = length();
        return new Vector(x / length, y / length, z / length);
    }
    public Vector add(Vector vect) {
        return new Vector(x + vect.getX(), y + vect.getY(), z + vect.getZ());
    }
    public Vector add(float s) {
        return new Vector(x + s, y + s, z + s);
    }
    public Vector sub(Vector vect) {
        return new Vector(x - vect.getX(), y - vect.getY(), z - vect.getZ());
    }
    public Vector sub(float s) {
        return new Vector(x - s, y - s, z - s);
    }
    public Vector multiply(Vector vect) {
        return  new Vector(x * vect.getX(), y * vect.getY(), z * vect.getZ());
    }
    public Vector multiply(float m) {
        return  new Vector(x * m, y * m, z * m);
    }
    public Vector div(Vector vect) {
        if (vect.getZ() == 0) {
            return new Vector(x / vect.getX(), y / vect.getY(), 0);
        } else return new Vector(x / vect.getX(), y / vect.getY(), z / vect.getY());
    }
    public Vector div(float d) {
        return new Vector(x / d, y / d, z / d);
    }
    public Vector abs() {
        return new Vector(Math.abs(x), Math.abs(y), Math.abs(z));
    }
    public Vector inverse() {
        return new Vector(-x, -y, -z, -w);
    }
    //GET
    public float getX() {
        return x;
    }
    public float getW() {
        return w;
    }
    public float getY() {
        return y;
    }
    public float getZ() {
        return z;
    }

    //SET
    public void setX(float x) {
        this.x = x;
    }
    public void setW(float w) {
        this.w = w;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setZ(float z) {
        this.z = z;
    }
}
