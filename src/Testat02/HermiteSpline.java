package Testat02;

import Testat01.Matrix;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class HermiteSpline {

    private double[][] P, AN, BN, AP, BP;
    private ArrayList<SplinePoint> pointList;
    private ArrayList<Line> lineArrayNat, lineArrayPar;
    private Pane drawPane;
    private boolean isThereANaturalSpline, isThereAParabolicSpline;
    private Line line;

    public HermiteSpline(Pane drawPane, ArrayList<SplinePoint> pointList) {
        this.pointList = pointList;
        this.drawPane = drawPane;
        lineArrayNat = new ArrayList<Line>();
        lineArrayPar = new ArrayList<Line>();
        isThereANaturalSpline = false;
        isThereAParabolicSpline = false;
    }

    public void initiateParSpline(boolean selected) {
        if (pointList.size() >= 3 && selected) {
            calculateParSpline();
        } else {
            deleteParSpline();
        }
    }

    public void initiateNatSpline(boolean selected) {
        if (pointList.size() >= 3 && selected) {
            calculateNatSpline();
        } else {
            deleteNatSpline();
        }
    }

    public void updateNatSpline(boolean selected) {
        if (pointList.size() >= 3 && selected) {
            updateNatSpline();
        }
    }

    public void updateParSpline(boolean selected) {
        if (pointList.size() >= 3 && selected) {
            updateParSpline();
        }
    }

    public void calculateParSpline() {

        P = new double[pointList.size()][2];
        for (int i = 0; i < pointList.size(); ++i) {
            P[i][0] = pointList.get(i).getxPos();
            P[i][1] = pointList.get(i).getyPos();
        }

        AP = new double[pointList.size()][pointList.size()];

        AP[0][0] = 1;
        AP[0][1] = 1;
        for (int i = 2; i < pointList.size(); ++i) {
            AP[0][i] = 0;
        }

        AP[pointList.size() - 1][pointList.size() - 2] = 1;
        AP[pointList.size() - 1][pointList.size() - 1] = 1;
        for (int i = 0; i < pointList.size() - 2; ++i) {
            AP[pointList.size() - 1][i] = 0;
        }

        int count = 0;
        for (int i = 1; i < pointList.size() - 1; ++i) {
            for (int j = 0; j < pointList.size(); ++j) {
                if (j == count || j == (count + 2)) {
                    AP[i][j] = 1;
                } else if (j == (count + 1)) {
                    AP[i][j] = 4;
                } else {
                    AP[i][j] = 0;
                }
            }
            ++count;
        }

        BP = new double[pointList.size()][pointList.size()];

        BP[0][0] = -2;
        BP[0][1] = 2;
        for (int i = 2; i < pointList.size(); ++i) {
            BP[0][i] = 0;
        }

        BP[pointList.size() - 1][pointList.size() - 2] = -2;
        BP[pointList.size() - 1][pointList.size() - 1] = 2;
        for (int i = 0; i < pointList.size() - 2; ++i) {
            BP[pointList.size() - 1][i] = 0;
        }

        int countBP = 0;
        for (int i = 1; i < pointList.size() - 1; ++i) {
            for (int j = 0; j < pointList.size(); ++j) {
                if (j == countBP) {
                    BP[i][j] = -3;
                } else if (j == (countBP + 2)) {
                    BP[i][j] = 3;
                } else {
                    BP[i][j] = 0;
                }

            }
            ++countBP;
        }

        updateParSpline();
    }

    public void calculateNatSpline() {

        P = new double[pointList.size()][2];
        for (int i = 0; i < pointList.size(); ++i) {
            P[i][0] = pointList.get(i).getxPos();
            P[i][1] = pointList.get(i).getyPos();
        }

        AN = new double[pointList.size()][pointList.size()];

        AN[0][0] = 2;
        AN[0][1] = 1;
        for (int i = 2; i < pointList.size(); ++i) {
            AN[0][i] = 0;
        }

        AN[pointList.size() - 1][pointList.size() - 2] = 1;
        AN[pointList.size() - 1][pointList.size() - 1] = 2;
        for (int i = 0; i < pointList.size() - 2; ++i) {
            AN[pointList.size() - 1][i] = 0;
        }

        int count = 0;
        for (int i = 1; i < pointList.size() - 1; ++i) {
            for (int j = 0; j < pointList.size(); ++j) {
                if (j == count || j == (count + 2)) {
                    AN[i][j] = 1;
                } else if (j == (count + 1)) {
                    AN[i][j] = 4;
                } else {
                    AN[i][j] = 0;
                }
            }
            ++count;
        }

        BN = new double[pointList.size()][pointList.size()];

        BN[0][0] = -3;
        BN[0][1] = 3;
        for (int i = 2; i < pointList.size(); ++i) {
            BN[0][i] = 0;
        }

        BN[pointList.size() - 1][pointList.size() - 2] = -3;
        BN[pointList.size() - 1][pointList.size() - 1] = 3;
        for (int i = 0; i < pointList.size() - 2; ++i) {
            BN[pointList.size() - 1][i] = 0;
        }

        int countB = 0;
        for (int i = 1; i < pointList.size() - 1; ++i) {
            for (int j = 0; j < pointList.size(); ++j) {
                if (j == countB) {
                    BN[i][j] = -3;
                } else if (j == (countB + 2)) {
                    BN[i][j] = 3;
                } else {
                    BN[i][j] = 0;
                }

            }
            ++countB;
        }

        updateNatSpline();
    }

    private void drawNatSpline(double p0x, double p0y, double p1x, double p1y, double t0x, double t0y, double t1x, double t1y) {
        double oldX = p0x, oldY = p0y;
        double delta = 0.0125;
        for (double t = 0; t <= 1; t += delta) {
            double[] h = bindFunction(t);
            double newX = (h[0] * p0x + h[1] * p1x + h[2] * t0x + h[3] * t1x);
            double newY = (h[0] * p0y + h[1] * p1y + h[2] * t0y + h[3] * t1y);
            line = new Line(oldX, oldY, newX, newY);
            line.setStrokeWidth(3);
            lineArrayNat.add(line);
            drawPane.getChildren().add(line);
            oldX = newX;
            oldY = newY;
        }
        isThereANaturalSpline = true;
    }

    private void drawParSpline(double p0x, double p0y, double p1x, double p1y, double t0x, double t0y, double t1x, double t1y) {
        double oldX = p0x, oldY = p0y;
        double delta = 0.0125;
        for (double t = 0; t <= 1; t += delta) {
            double[] h = bindFunction(t);
            double newX = (h[0] * p0x + h[1] * p1x + h[2] * t0x + h[3] * t1x);
            double newY = (h[0] * p0y + h[1] * p1y + h[2] * t0y + h[3] * t1y);
            line = new Line(oldX, oldY, newX, newY);
            line.setStrokeWidth(3);
            lineArrayPar.add(line);
            drawPane.getChildren().add(line);
            oldX = newX;
            oldY = newY;
        }
        isThereAParabolicSpline = true;
    }

    public void deleteNatSpline() {
        isThereANaturalSpline = false;
        for (int i = 0; i < lineArrayNat.size(); ++i) {
            drawPane.getChildren().remove(lineArrayNat.get(i));
        }
    }

    public void deleteParSpline() {
        isThereAParabolicSpline = false;
        for (int i = 0; i < lineArrayPar.size(); ++i) {
            drawPane.getChildren().remove(lineArrayPar.get(i));
        }
    }

    public void updateParSpline() {
        deleteParSpline();
        P = new double[pointList.size()][2];
        for (int i = 0; i < pointList.size(); ++i) {
            P[i][0] = pointList.get(i).getxPos();
            P[i][1] = pointList.get(i).getyPos();
        }
        double[][] APi = Matrix.invertiereMatrix(AP);
        double[][] APiB = Matrix.matMult(APi, BP);
        double[][] TP = Matrix.matMult(APiB, P);

        for (int segment = 0; segment < pointList.size() - 1; ++segment) {

            double p0x = P[segment][0];
            double p0y = P[segment][1];
            double p1x = P[segment + 1][0];
            double p1y = P[segment + 1][1];
            double t0x = TP[segment][0];
            double t0y = TP[segment][1];
            double t1x = TP[segment + 1][0];
            double t1y = TP[segment + 1][1];
            drawParSpline(p0x, p0y, p1x, p1y, t0x, t0y, t1x, t1y);
        }
    }

    private void updateNatSpline() {
        deleteNatSpline();
        P = new double[pointList.size()][2];
        for (int i = 0; i < pointList.size(); ++i) {
            P[i][0] = pointList.get(i).getxPos();
            P[i][1] = pointList.get(i).getyPos();
        }
        double[][] Ai = Matrix.invertiereMatrix(AN);
        double[][] AiB = Matrix.matMult(Ai, BN);
        double[][] TN = Matrix.matMult(AiB, P);

        for (int segment = 0; segment < pointList.size() - 1; ++segment) {

            double p0x = P[segment][0];
            double p0y = P[segment][1];
            double p1x = P[segment + 1][0];
            double p1y = P[segment + 1][1];
            double t0x = TN[segment][0];
            double t0y = TN[segment][1];
            double t1x = TN[segment + 1][0];
            double t1y = TN[segment + 1][1];
            drawNatSpline(p0x, p0y, p1x, p1y, t0x, t0y, t1x, t1y);
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
