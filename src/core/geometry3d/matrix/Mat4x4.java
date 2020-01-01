package core.geometry3d.matrix;

import core.geometry.Vector;

public class Mat4x4 {
    float[][] mat;

    public Mat4x4() {
        mat = new float[4][4];
    }
    public float[][] getMat() {
        return mat;
    }

    public void setMat(int index1, int index2, float value) {
        this.mat[index1][index2] = value;
    }
    public void setMat(float[][] mat) {
        this.mat = mat;
    }
    static Vector MatrixMultiplyVector(Mat4x4 mat, Vector vector1) {
        Vector vTemp = new Vector(0,0,0);

        vTemp.setX(vector1.getX() * mat.getMat()[0][0] + vector1.getY() * mat.getMat()[1][0] + vector1.getZ() * mat.getMat()[2][0] + vector1.getW() * mat.getMat()[3][0]);
        vTemp.setY(vector1.getX() * mat.getMat()[0][1] + vector1.getY() * mat.getMat()[1][1] + vector1.getZ() * mat.getMat()[2][1] + vector1.getW() * mat.getMat()[3][1]);
        vTemp.setZ(vector1.getX() * mat.getMat()[0][2] + vector1.getY() * mat.getMat()[1][2] + vector1.getZ() * mat.getMat()[2][2] + vector1.getW() * mat.getMat()[3][2]);
        vTemp.setW(vector1.getX() * mat.getMat()[0][3] + vector1.getY() * mat.getMat()[1][3] + vector1.getZ() * mat.getMat()[2][3] + vector1.getW() * mat.getMat()[3][3]);
        return vTemp;
    }
    //Convert to local functions
    public Mat4x4 MatrixMultiplyMatrix(Mat4x4 mat1, Mat4x4 mat2) {
        Mat4x4 matrix = new Mat4x4();

        for (int col = 0; col < 4; col++)
            for (int row = 0; row < 4; row++)
                matrix.setMat(row,col, mat1.getMat()[row][0] * mat2.getMat()[0][col] + mat1.getMat()[row][1] * mat2.getMat()[1][col] + mat1.getMat()[row][2] * mat2.getMat()[2][col] + mat1.getMat()[row][3] * mat2.getMat()[3][col]);

        return matrix;
    }
//
//    static Mat4x4 MatrixIdentity() {
//        Mat4x4 matrix = new Mat4x4();
//
//        matrix.setMat(0,0,1);
//        matrix.setMat(1,1,1);
//        matrix.setMat(2,2,1);
//        matrix.setMat(3,3,1);
//        return matrix;
//    }
//    static Mat4x4 MatrixRotateX(float angle) {
//        Mat4x4 matrix = new Mat4x4();
//
//        matrix.setMat(0,0,1);
//        matrix.setMat(1,1,(float) Math.cos(angle));
//        matrix.setMat(1,2,(float) Math.sin(angle));
//        matrix.setMat(2,1,(float) -Math.sin(angle));
//        matrix.setMat(2,2,(float) Math.cos(angle));
//        matrix.setMat(3,3,1);
//
//        return matrix;
//    }
//
//    static Mat4x4 MatrixRotateY(float angle) {
//        Mat4x4 matrix = new Mat4x4();
//
//        matrix.setMat(0,0,(float) Math.cos(angle));
//        matrix.setMat(0,2,(float) Math.sin(angle));
//        matrix.setMat(2,0,(float) -Math.sin(angle));
//        matrix.setMat(1,1,1);
//        matrix.setMat(2,2,(float) Math.cos(angle));
//        matrix.setMat(3,3,1);
//
//        return matrix;
//    }
//
//    static Mat4x4 MatrixRotateZ(float angle) {
//        Mat4x4 matrix = new Mat4x4();
//
//        matrix.setMat(0,0,(float) Math.cos(angle));
//        matrix.setMat(0,1,(float) Math.sin(angle));
//        matrix.setMat(1,0,(float) -Math.sin(angle));
//        matrix.setMat(1,1,(float) Math.cos(angle));
//        matrix.setMat(2,2,1);
//        matrix.setMat(3,3,1);
//
//        return matrix;
//    }
//
//    static Mat4x4 MatrixTranslate(float x, float y, float z) {
//        Mat4x4 matrix = new Mat4x4();
//
//        matrix.setMat(0,0,1);
//        matrix.setMat(1,1,1);
//        matrix.setMat(2,2,1);
//        matrix.setMat(3,3,1);
//
//        matrix.setMat(3,0,x);
//        matrix.setMat(3,1,y);
//        matrix.setMat(3,2,z);
//
//        return matrix;
//    }
//
//    static Mat4x4 MatrixProject(float fovDegrees, float aspectRatio, float far, float near) {
//        float fovRad = (float) (1.0f / Math.tan(fovDegrees * 0.5f / 180.0f * Math.PI));
//
//        Mat4x4 matrix = new Mat4x4();
//
//        matrix.setMat(0,0,aspectRatio * fovRad);
//        matrix.setMat(1,1, fovRad);
//        matrix.setMat(2,2, far / (far - near));
//        matrix.setMat(3,2, (-far * near) / (far - near));
//        matrix.setMat(2,3, 1.0f);
//        matrix.setMat(3,3, 0.0f);
//
//        return matrix;
//    }
//
//    public void MatrixPointAt(Vector pos, Vector target, Vector up) {
//        //Calculate new forward direction
//        Vector newForward = sub(target, pos);
//        newForward.normalize();
//
//        //Calculate new up direction
//        Vector temp = multiply(newForward, VectorDotProduct(up, newForward));
//        Vector newUp = sub(up,temp);
//        newUp = normalize(newUp);
//
//        //Calculate new right direction
//        Vector newRight = VectorCrossProduct(newUp, newForward);
//
//        //Construct final matrix
//
//        Mat4x4 matrix = new Mat4x4();
//        matrix.setMat(0,0, newRight.getX());
//        matrix.setMat(0,1, newRight.getY());
//        matrix.setMat(0,2, newRight.getZ());
//        matrix.setMat(0,3,0);
//
//        matrix.setMat(1,0, newUp.getX());
//        matrix.setMat(1,1, newUp.getY());
//        matrix.setMat(1,2, newUp.getZ());
//        matrix.setMat(1,3,0);
//
//        matrix.setMat(2,0, newUp.getX());
//        matrix.setMat(2,1, newUp.getY());
//        matrix.setMat(2,2, newUp.getZ());
//        matrix.setMat(2,3,0);
//
//        matrix.setMat(3,0, pos.getX());
//        matrix.setMat(3,1, pos.getY());
//        matrix.setMat(3,2, pos.getZ());
//        matrix.setMat(3,3,1);
//
//        return matrix;
//    }
//    public void QuickInverse() {
//
//        Mat4x4 matrix = new Mat4x4();
//
//        matrix.setMat(0,0, mat[0][0]);
//        matrix.setMat(0,1, mat[1][0]);
//        matrix.setMat(0,2, mat[2][0]);
//        matrix.setMat(0,3,0);
//
//        matrix.setMat(1,0, mat[0][1]);
//        matrix.setMat(1,1, mat[1][1]);
//        matrix.setMat(1,2, mat[2][1]);
//        matrix.setMat(1,3,0);
//
//        matrix.setMat(2,0, mat[0][2]);
//        matrix.setMat(2,1, mat[1][2]);
//        matrix.setMat(2,2, mat[2][2]);
//        matrix.setMat(2,3,0);
//
//        matrix.setMat(3,0, -(mat[3][0] * matrix.getMat()[0][0] + mat[3][1] * matrix.getMat()[1][0] + mat[3][2] * matrix.getMat()[2][0]));
//        matrix.setMat(3,1, -(mat[3][0] * matrix.getMat()[0][1] + mat[3][1] * matrix.getMat()[1][1] + mat[3][2] * matrix.getMat()[2][1]));
//        matrix.setMat(3,2, -(mat[3][0] * matrix.getMat()[0][2] + mat[3][1] * matrix.getMat()[1][2] + mat[3][2] * matrix.getMat()[2][2]));
//        matrix.setMat(3,3,1);
//
//        setMat(matrix.getMat());
//    }

}
