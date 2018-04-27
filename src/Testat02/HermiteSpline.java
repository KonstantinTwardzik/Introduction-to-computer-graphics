package Testat02;

import Testat01.Matrix;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class HermiteSpline {

    private ArrayList<SplinePoint> pointList;
    private ArrayList<Line> lineArray;
    private Pane drawPane;
    private boolean isThereANaturalSpline, isThereAParabolicSpline;
    private Line line;

    public HermiteSpline(Pane drawPane, ArrayList<SplinePoint> pointList) {
        this.pointList = pointList;
        this.drawPane = drawPane;
        lineArray = new ArrayList<Line>();
        isThereANaturalSpline = false;
        isThereAParabolicSpline = false;
    }

    public void initiateParSpline(boolean selected) {

    }

    public void initiateNatSpline(boolean selected) {
        if (pointList.size() >= 3 && selected) {
            calculateSpline();
        } else {
            deleteSpline();
        }
    }


    public void calculateSpline() {
        if (isThereANaturalSpline) {
            deleteSpline();
        }

        double[][] P = new double[pointList.size()][2];
        for (int i = 0; i < pointList.size(); ++i) {
            P[i][0] = pointList.get(i).getxPos();
            P[i][1] = pointList.get(i).getyPos();
        }


        double[][] A = new double[pointList.size()][pointList.size()];

        A[0][0] = 2;
        A[0][1] = 1;
        for (int i = 2; i < pointList.size(); ++i) {
            A[0][i] = 0;
        }

        A[pointList.size() - 1][pointList.size() - 2] = 1;
        A[pointList.size() - 1][pointList.size() - 1] = 2;
        for (int i = 0; i < pointList.size() - 2; ++i) {
            A[pointList.size() - 1][i] = 0;
        }

        int count = 0;
        for (int i = 1; i < pointList.size() - 1; ++i) {
            for (int j = 0; j < pointList.size(); ++j) {
                if (j == count || j == (count + 2)) {
                    A[i][j] = 1;
                } else if (j == (count + 1)) {
                    A[i][j] = 4;
                } else {
                    A[i][j] = 0;
                }

            }
            ++count;
        }

        double[][] B = new double[pointList.size()][pointList.size()];

        B[0][0] = -3;
        B[0][1] = 3;
        for (int i = 2; i < pointList.size(); ++i) {
            B[0][i] = 0;
        }

        B[pointList.size() - 1][pointList.size() - 2] = -3;
        B[pointList.size() - 1][pointList.size() - 1] = 3;
        for (int i = 0; i < pointList.size() - 2; ++i) {
            B[pointList.size() - 1][i] = 0;
        }

        int countB = 0;
        for (int i = 1; i < pointList.size() - 1; ++i) {
            for (int j = 0; j < pointList.size(); ++j) {
                if (j == countB) {
                    B[i][j] = -3;
                } else if (j == (countB + 2)) {
                    B[i][j] = 3;
                } else {
                    B[i][j] = 0;
                }

            }
            ++countB;
        }

        for (int i = 0; i < pointList.size(); ++i) {
            for (int j = 0; j < pointList.size(); ++j) {
                System.out.print(B[i][j] + ", ");
            }
            System.out.println();
        }

        double[][] Ai = Matrix.invertiereMatrix(A);
        double[][] AiB = Matrix.matMult(Ai, B);
        double[][] T = Matrix.matMult(AiB, P);

        for (int segment = 0; segment < pointList.size()-1; ++segment) {

            double p0x = P[segment][0];
            double p0y = P[segment][1];
            double p1x = P[segment + 1][0];
            double p1y = P[segment + 1][1];
            double t0x = T[segment][0];
            double t0y = T[segment][1];
            double t1x = T[segment + 1][0];
            double t1y = T[segment + 1][1];
            drawSpline(p0x, p0y, p1x, p1y, t0x, t0y, t1x, t1y);
        }
//
    }

    private void drawSpline(double p0x, double p0y, double p1x, double p1y, double t0x, double t0y, double t1x, double t1y) {
        int oldX = (int) p0x, oldY = (int) p0y;
        double delta = 0.0125;
        for (double t = 0; t <= 1; t += delta) {
            double[] h = bindFunction(t);
            int newX = (int) (h[0] * p0x + h[1] * p1x + h[2] * t0x + h[3] * t1x);
            int newY = (int) (h[0] * p0y + h[1] * p1y + h[2] * t0y + h[3] * t1y);
            line = new Line(oldX, oldY, newX, newY);
            line.setStrokeWidth(3);
            lineArray.add(line);
            drawPane.getChildren().add(line);
            oldX = newX;
            oldY = newY;
        }
        isThereANaturalSpline = true;
    }

    public void deleteSpline() {
        isThereANaturalSpline = false;
        for (int i = 0; i < lineArray.size(); ++i) {
            drawPane.getChildren().remove(lineArray.get(i));
        }
    }

    private double[] bindFunction(double t) {
        double t2 = t * t;
        double t3 = t2 * t;
        return new double[]{
                2 * t3 - 3 * t2 + 1,
                -2 * t3 + 3 * t2,
                t3 - 2 * t2 + t,
                t3 - t2
        };
    }

}
