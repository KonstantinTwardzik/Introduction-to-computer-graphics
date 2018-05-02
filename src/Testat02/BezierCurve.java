package Testat02;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class BezierCurve {

    // schreibe Hilfsfunktion lür lineare Interopolation
    //lerp(t, anfang, ende)
    // t * (ende - anfang) + anfang
    //
    //for schleife mit t von 0 -1
    // rechne alle lerps aus bis nur noch 1 punkt übrigbleibt
    // zeichne Punkt

    // De Casteljau algorithmus

    private ArrayList<SplinePoint> pointList;
    private ArrayList<Line> lineArray;
    private ArrayList<Circle> circleArray;
    private Pane drawPane;
    private boolean isThereABezierCurve;
    private Line line;

    public BezierCurve(Pane drawPane, ArrayList<SplinePoint> pointList) {
        this.drawPane = drawPane;
        this.pointList = pointList;
        lineArray = new ArrayList<>();
        circleArray = new ArrayList<>();
        isThereABezierCurve = false;
    }

    public void updateCurve(boolean selected) {
        if(selected) {
            calculateCurve();
        } else {
            deleteCurve();
        }
    }

    public void calculateCurve() {
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
                double f = binomKof(n, i) * Math.pow(t, i) * Math.pow(1d - t, n - i);
                x += f * P[i][0];
                y += f * P[i][1];
            }

            Circle c = new Circle();
            c.setCenterX(x);
            c.setCenterY(y);
            c.setRadius(3);
            c.setFill(Color.BLACK);
            circleArray.add(c);
            drawPane.getChildren().add(c);
        }
        drawCurve();
    }

    public void drawCurve() {
        for (int i = 0; i < circleArray.size() -1; i++) {
            line = new Line();
            line.setStartX(circleArray.get(i).getCenterX());
            line.setStartY(circleArray.get(i).getCenterY());
            line.setEndX(circleArray.get(i+1).getCenterX());
            line.setEndY(circleArray.get(i+1).getCenterY());
            drawPane.getChildren().add(line);
            lineArray.add(line);
        }
        isThereABezierCurve = true;
    }

    public void deleteCurve() {
        for (int i = 0; i < circleArray.size(); ++i) {
            drawPane.getChildren().remove(circleArray.get(i));
        }
        for (int i = 0; i < lineArray.size(); ++i) {
            drawPane.getChildren().remove(lineArray.get(i));
        }
        lineArray.clear();
        circleArray.clear();
        isThereABezierCurve = false;
    }

    private static int binomKof(int n, int k) {
        if (k == 0)
            return 1;
        if (n < 2 * k)
            return binomKof(n, n - k);
        int result = n - k + 1;
        for (int i = 2; i <= k; ++i)
            result *= (n - k + i) / i;
        return result;
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
