package Testat03.Utils;

public class MatrixHelper {

    static public float[][] matrixTranslate(float[] v) {
        return new float[][]{
                {1, 0, 0, v[0]},
                {0, 1, 0, v[1]},
                {0, 0, 1, v[2]},
                {0, 0, 0, 1}};
    }

    static public float[][] matrixIdentity() {
        return new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
    }

    static public float[][] matrixRotX(float angle) {
        float degToRad = (float) (Math.PI / 180.0);
        float cosA = (float) (Math.cos(angle * degToRad));
        float sinA = (float) (Math.sin(angle * degToRad));
        return new float[][]{
                {1, 0, 0, 0},
                {0, cosA, -sinA, 0},
                {0, sinA, cosA, 0},
                {0, 0, 0, 1}
        };
    }

    static public float[][] matrixRotY(float angle) {
        float degToRad = (float) (Math.PI / 180.0);
        float cosA = (float) (Math.cos(angle * degToRad));
        float sinA = (float) (Math.sin(angle * degToRad));
        return new float[][]{
                {cosA, 0, sinA, 0},
                {0, 1, 0, 0},
                {-sinA, 0, cosA, 0},
                {0, 0, 0, 1}
        };
    }

    static public float[][] matrixRotZ(float angle) {
        float degToRad = (float) (Math.PI / 180.0);
        float cosA = (float) (Math.cos(angle * degToRad));
        float sinA = (float) (Math.sin(angle * degToRad));
        return new float[][]{
                {cosA, -sinA, 0, 0},
                {sinA, cosA, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }

    // beware of degenerate case where center - eye becomes up or -up
    static public float[][] matrixLookAt(float[] eye, float[] up, float[] center) {
        float[] upNorm = vectorNormalize(up);
        float[] z = vectorNormalize(vectorSubtract(center, eye));
        float[] x = vectorCross(z, upNorm);
        float[] y = vectorCross(x, z);
        float[][] rot = {
                {x[0], x[1], x[2], 0},
                {y[0], y[1], y[2], 0},
                {-z[0], -z[1], -z[2], 0},
                {0, 0, 0, 1}};
        float[][] transl = {
                {1, 0, 0, -eye[0]},
                {0, 1, 0, -eye[1]},
                {0, 0, 1, -eye[2]},
                {0, 0, 0, 1}};
        return matrixMult(rot, transl);
    }

    // assuming near plane is symmetrical in width and height
    static public float[][] matrixPerspective(float width, float height, float n, float f) {
        float r = width / 2.0f;
        float t = height / 2.0f;
        return new float[][]{
                {n / r, 0, 0, 0},
                {0, n / t, 0, 0},
                {0, 0, -(f + n) / (f - n), (-2 * f * n) / (f - n)},
                {0, 0, -1, 0}};
    }

    // assuming near plane is symmetrical in width and height
    static public float[][] matrixOrtho(float width, float height, float n, float f) {
        float r = width / 2.0f;
        float t = height / 2.0f;
        float tz = -(f + n) / (f - n);
        return new float[][]{
                {1 / r, 0, 0, 0},
                {0, 1 / t, 0, 0},
                {0, 0, -2 / (f - n), tz},
                {0, 0, 0, 1}};
    }

    static public float[][] matrixMult(float[][] m1, float[][] m2) {
        float[][] result = new float[4][4];
        for (int zeile = 0; zeile < 4; zeile++) {
            for (int spalte = 0; spalte < 4; spalte++) {
                result[zeile][spalte] = 0.0f;
                for (int i = 0; i < 4; i++) {
                    result[zeile][spalte] += m1[zeile][i] * m2[i][spalte];
                }
            }
        }
        return result;
    }

    static private float vectorDot(float[] v1, float[] v2) {
        return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
    }

    static private float[] vectorCross(float[] v1, float[] v2) {
        return new float[]{v1[1] * v2[2] - v1[2] * v2[1],
                v1[2] * v2[0] - v1[0] * v2[2],
                v1[0] * v2[1] - v1[1] * v2[0]};
    }

    static public float vectorLength(float[] v) {
        return (float) Math.sqrt(vectorDot(v, v));
    }

    static public float[] vectorNormalize(float[] v) {
        float length = vectorLength(v);
        return new float[]{v[0] / length, v[1] / length, v[2] / length};
    }

    static public float[] vectorMultiplyScalar(float scalar, float[] v) {
        return new float[]{v[0] * scalar, v[1] * scalar, v[2] * scalar};
    }

    static private float[] vectorSubtract(float[] v1, float[] v2) {
        return new float[]{v1[0] - v2[0], v1[1] - v2[1], v1[2] - v2[2]};
    }

    static public float[] vectorAdd(float[] v1, float[] v2) {
        return new float[]{v1[0] + v2[0], v1[1] + v2[1], v1[2] + v2[2]};
    }
}
