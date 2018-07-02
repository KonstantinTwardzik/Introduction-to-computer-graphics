package Testat02;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class SplinePoint {

    private SimpleDoubleProperty xPos;
    private SimpleDoubleProperty yPos;
    private Circle circle;
    private Pane drawPane;
    private SplineViewer main;

    SplinePoint(double xPos, double yPos, Pane drawPane, SplineViewer main) {
        this.xPos = new SimpleDoubleProperty(xPos);
        this.yPos = new SimpleDoubleProperty(yPos);
        this.drawPane = drawPane;
        this.main = main;

        circle = new Circle();
        circle.setRadius(8);
        circle.centerXProperty().bind(xPosProperty());
        circle.centerYProperty().bind(yPosProperty());
        drawPane.getChildren().add(circle);
        circle.setOnMousePressed(e -> deletePoint(e.getButton(), circle));
        circle.setOnMouseDragged(e -> dragPoint(e.getButton(), e.getX(), e.getY()));

    }

    private void dragPoint(MouseButton button, double xPos, double yPos) {
        if(button == MouseButton.PRIMARY) {
            this.xPos.set(xPos);
            this.yPos.set(yPos);
            main.updateSplines();
        }
    }

    private void deletePoint(MouseButton button, Circle c) {
        if (button == MouseButton.SECONDARY) {
            main.deletePoint(this);
            drawPane.getChildren().remove(c);
        }

    }

    double getxPos() {
        return xPos.get();
    }

    SimpleDoubleProperty xPosProperty() {
        return xPos;
    }

    double getyPos() {
        return yPos.get();
    }

    SimpleDoubleProperty yPosProperty() {
        return yPos;
    }

}
