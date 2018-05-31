package Testat03;

public class Matrix {

    public static void print(String c, double[][] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.print(c + "[ " + i + "][*]: ");
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + ", ");
            }
            System.out.println(" ");
        }
    }

    public static void print(String c, double[] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.println(c + "[ " + i + "]: " + m[i] + ", ");
        }
    }

    public static double[][] cloneMatrix(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] clone = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = matrix[i][j];
            }
        }
        return clone;
    }

    public static double[] produkt(double s, double[] vector) {
        int n = vector.length;
        double[] newVec = new double[n];
        for (int i = 0; i < n; ++i) {
            newVec[i] = s * vector[i];
        }
        return newVec;
    }

    public static double[][] produkt(double s, double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        double[][] newMatrix = new double[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                newMatrix[i][j] = s * matrix[i][j];
            }
        }
        return newMatrix;
    }

    public static double[][] matMult(double[][] matrix1, double[][] matrix2) {
        // Dimensionen und Schleifengrenzen
        int l = matrix1.length; // Anzahl Zeilen Matrix1
        int m = matrix2.length; // Anzahl Zeilen Matris2
        int n = matrix2[0].length; // Anzahl Spalten Matrix2
        // System.out.println(l + " " + m + " "+ n);

        if (m != matrix1[0].length)
            // throw new RuntimeException();
            throw new IndexOutOfBoundsException("Array bounds incompatible: matrix1 [" + l + "][" + matrix1[0].length
                    + "] * matrix2[" + m + "][" + n + "]");
        else {
            double[][] matrix3 = new double[l][n];
            for (int i = 0; i < n; i++) { // f�r alle Spalten von matrix2 und
                // matrix3
                for (int j = 0; j < l; j++) { // f�r alle Zeilen von matrix1
                    // und matrix3
                    matrix3[j][i] = (double) 0.0;
                    for (int k = 0; k < m; k++) { // summiere f�r alle Spalten
                        // von matrix1 (Zeilen von
                        // Matrix2)
                        matrix3[j][i] += +matrix1[j][k] * matrix2[k][i];
                    }
                }
            }
            return matrix3;
        }
    }// MatMult

    public static double[] matMult(double[][] matrix1, double[] vektor) {
        // Dimensionen und Schleifengrenzen
        int l = matrix1.length; // Anzahl Zeilen Matrix1
        int m = vektor.length; // Anzahl Zeilen Matris2
        // System.out.println(l + " " + m + " "+ n);

        if (m != matrix1[0].length)
            // throw new RuntimeException();
            throw new IndexOutOfBoundsException(
                    "Array bounds incompatible: matrix1 [" + l + "][" + matrix1[0].length + "] * matrix2[" + m + "]");
        else {
            double[] matrix3 = new double[l];
            for (int j = 0; j < l; j++) { // f�r alle Zeilen von matrix1 und
                // matrix3
                matrix3[j] = (double) 0.0;
                for (int k = 0; k < m; k++) { // summiere f�r alle Spalten von
                    // matrix1 (Zeilen von Matrix2)
                    matrix3[j] += +matrix1[j][k] * vektor[k];
                }
            }
            return matrix3;
        }
    }// matMult

    public static double[][] invertiereMatrix(double[][] m) {
        int n = m.length;
        double[][] tmp = new double[n][2 * n];
        // print("Parameter", m);
        // --- Kopie der Matrix mit Einheitsmatrix rechts
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmp[i][j] = m[i][j];
            }
            tmp[i][i + n] = 1d;
        }
        // --- Dreieckselimination
        double[][] tmp1 = Gauss.dreiecksElimination(tmp);
        // --- Rueckwaertssubstitution
        return Gauss.rueckwaertsSubstitutionMehrereGLS(tmp1);

    }

}
