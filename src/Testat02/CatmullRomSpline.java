package Testat02;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

class CatmullRomSpline {

    private ArrayList<SplinePoint> pointList;
    private ArrayList<Line> lineArray;
    private Pane drawPane;
    private boolean isThereACSpline;

    CatmullRomSpline(Pane drawPane, ArrayList<SplinePoint> pointList) {
        this.drawPane = drawPane;
        this.pointList = pointList;
        lineArray = new ArrayList<>();
        isThereACSpline = false;
    }

    void initiateSpline(boolean selected) {
        if (selected) {
            drawCSpline();
        } else {
            deleteSpline();
        }
    }

    private void deleteSpline() {
        for (Line aLineArray : lineArray) {
            drawPane.getChildren().remove(aLineArray);
        }
        lineArray.clear();
        isThereACSpline = false;
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

    private void drawCSpline() {
        if (isThereACSpline) {
            deleteSpline();
        }
        int n = pointList.size();

        if (n < 3)
            return;

        double[][] P = new double[pointList.size()][2];
        for (int i = 0; i < pointList.size(); ++i) {
            P[i][0] = pointList.get(i).getxPos();
            P[i][1] = pointList.get(i).getyPos();
        }

        // P' = 0.5*(P_k - P_k-1) + 0.5*(P_k+1 - P_k)
        // P' = Steigung
        double[][] steigungen = new double[pointList.size()][2];
        // Anfangspunkt
        steigungen[0][0] = (P[1][0] - P[0][0]) * 0.5;
        steigungen[0][1] = (P[1][1] - P[0][1]) * 0.5;

        // Mittelpunkte
        for (int i = 1; i < n - 1; ++i) {
            steigungen[i][0] = (P[i + 1][0] - P[i - 1][0]) * 0.5;
            steigungen[i][1] = (P[i + 1][1] - P[i - 1][1]) * 0.5;
        }

        //Endpunkte
        steigungen[n - 1][0] = (P[n - 1][0] - P[n - 2][0]) * 0.5;
        steigungen[n - 1][1] = (P[n - 1][1] - P[n - 2][1]) * 0.5;

        // zeichnen
        double xOld, yOld, xNew, yNew;

        Point2D P0, P1;
        Point2D T0, T1;
        for (int i = 0; i < n - 1; ++i) {
            P0 = new Point2D(pointList.get(i).getxPos(), pointList.get(i).getyPos());
            P1 = new Point2D(pointList.get(i+1).getxPos(), pointList.get(i+1).getyPos());

            T0 = new Point2D(steigungen[i][0], steigungen[i][1]);
            T1 = new Point2D(steigungen[i + 1][0], steigungen[i + 1][1]);

            xOld = P0.getX();
            yOld = P0.getY();

            double schritt = 0.0125;
            Line line;
            for (double t = schritt; t < 1d; t += schritt) {
                double[] H = bindFunction(t);
                xNew = (H[0] * P0.getX() + H[1] * P1.getX() + H[2] * T0.getX() + H[3] * T1.getX());
                yNew = (H[0] * P0.getY() + H[1] * P1.getY() + H[2] * T0.getY() + H[3] * T1.getY());
                line = new Line();
                line.setStartX(xOld);
                line.setStartY(yOld);
                line.setEndX(xNew);
                line.setEndY(yNew);
                line.setStrokeWidth(3);
                drawPane.getChildren().add(line);
                lineArray.add(line);
                xOld = xNew;
                yOld = yNew;
            }
            line = new Line();
            line.setStartX(xOld);
            line.setStartY(yOld);
            line.setEndX(P1.getX());
            line.setEndY(P1.getY());
            line.setStrokeWidth(3);
            drawPane.getChildren().add(line);
            lineArray.add(line);
            isThereACSpline = true;
        }
    }
}
