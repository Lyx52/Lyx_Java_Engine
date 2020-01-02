package core.geometry3d.matrix;

import core.geometry.Vector;

public class Mat4x4 {
    float[][] mat;

    public Mat4x4() {
        mat = new float[4][4];
    }
    public Mat4x4 identity() {
        mat[0][0] = 1;
        mat[1][0] = 0;
        mat[2][0] = 0;
        mat[3][0] = 0;

        mat[0][1] = 0;
        mat[1][1] = 1;
        mat[2][1] = 0;
        mat[3][1] = 0;

        mat[0][2] = 0;
        mat[1][2] = 0;
        mat[2][2] = 1;
        mat[3][2] = 0;

        mat[0][3] = 0;
        mat[1][3] = 0;
        mat[2][3] = 0;
        mat[3][3] = 1;
        return this;
    }

    public Mat4x4 initTranslation(float x, float y, float z) {
        mat[0][0] = 1;
        mat[1][0] = 0;
        mat[2][0] = 0;
        mat[3][0] = 0;

        mat[0][1] = 0;
        mat[1][1] = 1;
        mat[2][1] = 0;
        mat[3][1] = 0;

        mat[0][2] = 0;
        mat[1][2] = 0;
        mat[2][2] = 1;
        mat[3][2] = 0;

        mat[0][3] = x;
        mat[1][3] = y;
        mat[2][3] = z;
        mat[3][3] = 1;

        return this;
    }

    public Mat4x4 initRotation(float x, float y, float z) {
        Mat4x4
            rx = new Mat4x4(),
            ry = new Mat4x4(),
            rz = new Mat4x4();

        x = (float) Math.toRadians(x);
        y = (float) Math.toRadians(y);
        z = (float) Math.toRadians(z);

        //RZ
        rz.mat[0][0] = (float)Math.cos(z);
        rz.mat[0][1] = -(float)Math.sin(z);
        rz.mat[0][2] = 0;
        rz.mat[0][3] = 0;

        rz.mat[1][0] = (float)Math.sin(z);
        rz.mat[1][1] = (float)Math.cos(z);
        rz.mat[1][2] = 0;
        rz.mat[1][3] = 0;

        rz.mat[2][0] = 0;
        rz.mat[2][1] = 0;
        rz.mat[2][2] = 1;
        rz.mat[2][3] = 0;

        rz.mat[3][0] = 0;
        rz.mat[3][1] = 0;
        rz.mat[3][2] = 0;
        rz.mat[3][3] = 1;

        //RX
        rx.mat[0][0] = 1;
        rx.mat[0][1] = 0;
        rx.mat[0][2] = 0;
        rx.mat[0][3] = 0;

        rx.mat[1][0] = 0;
        rx.mat[1][1] = (float)Math.cos(x);
        rx.mat[1][2] = -(float)Math.sin(x);
        rx.mat[1][3] = 0;

        rx.mat[2][0] = 0;
        rx.mat[2][1] = (float)Math.sin(x);
        rx.mat[2][2] = (float)Math.cos(x);
        rx.mat[2][3] = 0;

        rx.mat[3][0] = 0;
        rx.mat[3][1] = 0;
        rx.mat[3][2] = 0;
        rx.mat[3][3] = 1;

        //RY
        ry.mat[0][0] = (float)Math.cos(y);
        ry.mat[0][1] = 0;
        ry.mat[0][2] = -(float)Math.sin(y);
        ry.mat[0][3] = 0;

        ry.mat[1][0] = 0;
        ry.mat[1][1] = 1;
        ry.mat[1][2] = 0;
        ry.mat[1][3] = 0;

        ry.mat[2][0] = (float)Math.sin(y);
        ry.mat[2][1] = 0;
        ry.mat[2][2] = (float)Math.cos(y);
        ry.mat[2][3] = 0;

        ry.mat[3][0] = 0;
        ry.mat[3][1] = 0;
        ry.mat[3][2] = 0;
        ry.mat[3][3] = 1;

        mat = rz.multiply(ry.multiply(rx)).getMat();

        return this;
    }
    public Mat4x4 initScale(float x, float y, float z) {
        mat[0][0] = x;
        mat[1][0] = 0;
        mat[2][0] = 0;
        mat[3][0] = 0;

        mat[0][1] = 0;
        mat[1][1] = y;
        mat[2][1] = 0;
        mat[3][1] = 0;

        mat[0][2] = 0;
        mat[1][2] = 0;
        mat[2][2] = z;
        mat[3][2] = 0;

        mat[0][3] = 0;
        mat[1][3] = 0;
        mat[2][3] = 0;
        mat[3][3] = 1;
        return this;
    }
    public Mat4x4 initProjection(float fov, float width, float height, float near, float far) {
        float
            aspectRatio = width / height,
            tanHalfFov = (float)Math.tan(Math.toRadians(fov / 2)),
            zRange = near - far;

        mat[0][0] = 1.0f / (tanHalfFov * aspectRatio);
        mat[1][0] = 0;
        mat[2][0] = 0;
        mat[3][0] = 0;

        mat[0][1] = 0;
        mat[1][1] = 1.0f / tanHalfFov;
        mat[2][1] = 0;
        mat[3][1] = 0;

        mat[0][2] = 0;
        mat[1][2] = 0;
        mat[2][2] = (-near -far) / zRange;
        mat[3][2] = 1;

        mat[0][3] = 0;
        mat[1][3] = 0;
        mat[2][3] = 2 * far * near / zRange;
        mat[3][3] = 0;

        return this;
    }
    public Mat4x4 initCamera(Vector forward, Vector up) {
        Vector
            f = forward.normalize(),
            r = up.normalize();

        r = r.cross(f);

        Vector u = f.cross(r);

        mat[0][0] = r.getX();
        mat[1][0] = u.getX();
        mat[2][0] = f.getX();
        mat[3][0] = 0;

        mat[0][1] = r.getY();
        mat[1][1] = u.getY();
        mat[2][1] = f.getY();
        mat[3][1] = 0;

        mat[0][2] = r.getZ();
        mat[1][2] = u.getZ();
        mat[2][2] = f.getZ();
        mat[3][2] = 0;

        mat[0][3] = 0;
        mat[1][3] = 0;
        mat[2][3] = 0;
        mat[3][3] = 1;

        return this;


    }
    public Mat4x4 multiply(Mat4x4 matMultiply) {
        Mat4x4 res = new Mat4x4();

        for (int col = 0; col < 4; col++)
            for (int row = 0; row < 4; row++) {
                res.set(col, row,
                mat[col][0] * matMultiply.get(0, row) +
                    mat[col][1] * matMultiply.get(1, row) +
                    mat[col][2] * matMultiply.get(2, row) +
                    mat[col][3] * matMultiply.get(3, row));
            }

        return res;
    }
    //GET
    public float[][] getMat() {
        return mat;
    }
    public float get(int x, int y) {
        return mat[x][y];
    }

    //SET
    public void set(int x, int y, float value) {
        mat[x][y] = value;
    }
    public void setMat(float[][] mat) {
        this.mat = mat;
    }
}
