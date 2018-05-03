package Testat02;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

class HermiteCurve {

    private ArrayList<SplinePoint> pointList;
    private Pane drawPane;
    private Line tangent1;
    private Line tangent2;
    private ArrayList<Line> lineArray;
    private boolean isThereACurve;

    HermiteCurve(Pane drawPane, ArrayList<SplinePoint> pointList) {
        this.pointList = pointList;
        this.drawPane = drawPane;
        this.isThereACurve = false;
        this.lineArray = new ArrayList<>();

    }

    void initiateCurve(boolean b) {
        if (pointList.size() == 4 && b && !isThereACurve) {
            drawCurve();
        } else {
            deleteCurve();
        }
    }

    private void drawCurve() {
        SimpleDoubleProperty oldX = pointList.get(0).xPosProperty();
        SimpleDoubleProperty oldY = pointList.get(0).yPosProperty();
        SimpleDoubleProperty x0, x1, x2, x3, y0, y1, y2, y3;
        x0 = pointList.get(0).xPosProperty();
        x1 = pointList.get(1).xPosProperty();
        x2 = pointList.get(2).xPosProperty();
        x3 = pointList.get(3).xPosProperty();
        y0 = pointList.get(0).yPosProperty();
        y1 = pointList.get(1).yPosProperty();
        y2 = pointList.get(2).yPosProperty();
        y3 = pointList.get(3).yPosProperty();

        tangent1 = new Line();
        tangent1.startXProperty().bind(x0);
        tangent1.startYProperty().bind(y0);
        tangent1.endXProperty().bind(x2);
        tangent1.endYProperty().bind(y2);
        tangent1.setStrokeWidth(2);
        drawPane.getChildren().add(tangent1);
        tangent2 = new Line();
        tangent2.startXProperty().bind(x1);
        tangent2.startYProperty().bind(y1);
        tangent2.endXProperty().bind(x3);
        tangent2.endYProperty().bind(y3);
        tangent2.setStrokeWidth(2);
        drawPane.getChildren().add(tangent2);

        double delta = 0.0125;

        for (double t = 0; t < 1; t += delta) {
            double[] h = bindFunction(t);
            SimpleDoubleProperty newX = new SimpleDoubleProperty();
            SimpleDoubleProperty newY = new SimpleDoubleProperty();
            newX.bind(x0.multiply(h[0]).add(x1.multiply(h[1])).add(x2.subtract(x0).multiply(h[2])).add(x1.subtract(x3).multiply(h[3])));
            newY.bind(y0.multiply(h[0]).add(y1.multiply(h[1])).add(y2.subtract(y0).multiply(h[2])).add(y1.subtract(y3).multiply(h[3])));
            Line line = new Line();
            line.setStrokeWidth(3);
            line.startXProperty().bind(oldX);
            line.startYProperty().bind(oldY);
            line.endXProperty().bind(newX);
            line.endYProperty().bind(newY);
            drawPane.getChildren().add(line);
            oldX = newX;
            oldY = newY;
            lineArray.add(line);
        }
        isThereACurve = true;
    }

    private void deleteCurve() {
        isThereACurve = false;
        for (Line aLineArray : lineArray) {
            drawPane.getChildren().remove(aLineArray);
        }
        drawPane.getChildren().remove(tangent1);
        drawPane.getChildren().remove(tangent2);
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

