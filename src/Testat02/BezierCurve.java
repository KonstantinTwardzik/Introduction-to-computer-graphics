package Testat02;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

class BezierCurve {

    private ArrayList<SplinePoint> pointList;
    private ArrayList<Line> lineArray;
    private ArrayList<Point2D> pointArray;
    private Pane drawPane;
    private boolean isThereABezierCurve;

    BezierCurve(Pane drawPane, ArrayList<SplinePoint> pointList) {
        this.drawPane = drawPane;
        this.pointList = pointList;
        lineArray = new ArrayList<>();
        pointArray = new ArrayList<>();
        isThereABezierCurve = false;
    }

    private static int binCoe(int n, int k) {
        if (k == 0)
            return 1;
        if (n < 2 * k)
            return binCoe(n, n - k);
        int result = n - k + 1;
        for (int i = 2; i <= k; ++i)
            result *= (n - k + i) / i;
        return result;
    }

    void updateCurve(boolean selected) {
        if (selected) {
            calculateCurve();
        } else {
            deleteCurve();
        }
    }

    private void calculateCurve() {
        if (isThereABezierCurve) {
            deleteCurve();
        }
        int n = pointList.size() - 1;

        if (n < 1)
            return;

        double[][] P = new double[n + 1][2];
        for (int i = 0; i <= n; ++i) {
            P[i][0] = pointList.get(i).getxPos();
            P[i][1] = pointList.get(i).getyPos();
        }

        double x, y;


        for (double t = 0; t < 1; t += 0.0125d) {
            x = 0;
            y = 0;

            for (int i = 0; i <= n; i++) {
                double f = binCoe(n, i) * Math.pow(t, i) * Math.pow(1d - t, n - i);
                x += f * P[i][0];
                y += f * P[i][1];
            }

            Point2D Point = new Point2D(x, y);
            pointArray.add(Point);
        }
        drawCurve();
    }

    private void drawCurve() {
        for (int i = 0; i < pointArray.size() - 1; i++) {
            Line line = new Line();
            line.setStartX(pointArray.get(i).getX());
            line.setStartY(pointArray.get(i).getY());
            line.setEndX(pointArray.get(i + 1).getX());
            line.setEndY(pointArray.get(i + 1).getY());
            line.setStrokeWidth(3);
            drawPane.getChildren().add(line);
            lineArray.add(line);
        }
        isThereABezierCurve = true;
    }

    private void deleteCurve() {
        for (Line aLineArray : lineArray) {
            drawPane.getChildren().remove(aLineArray);
        }
        lineArray.clear();
        pointArray.clear();
        isThereABezierCurve = false;
    }
}
