package Testat01;


import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static javafx.scene.layout.BorderWidths.DEFAULT;

public class Circle_2D extends Application {
    private GridPane root;
    private Pane drawPane;
    private Slider xPosSld, yPosSld, redSld, greenSld, blueSld, sizeSld;
    private Label xPosLbl, yPosLbl, redLbl, greenLbl, blueLbl, sizeLbl;
    private javafx.scene.shape.Circle circle;


    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new GridPane();
        Scene scene = new Scene(root);
        initView();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Circle_2D transformation");
        primaryStage.setFullScreen(true);
        primaryStage.setWidth(1860);
        primaryStage.setHeight(1080);
        primaryStage.show();
    }

    public void initView() {
        Rectangle clipping = new Rectangle();
        drawPane = new Pane();
        drawPane.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        drawPane.setClip(clipping);
        drawPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipping.setWidth(newValue.getWidth());
            clipping.setHeight(newValue.getHeight());
        });
        circle = new javafx.scene.shape.Circle();
        drawPane.setPrefSize(1400, 1080);
        xPosSld = new Slider(0,1380,30);
        yPosSld = new Slider(0,1020,30);
        redSld = new Slider(0,255,50);
        greenSld = new Slider(0, 255, 50);
        blueSld = new Slider(0, 255, 50);
        sizeSld = new Slider(0, 300, 30);
        xPosLbl = new Label("X Position: 30");
        yPosLbl = new Label("Y Position: 30");
        redLbl = new Label("Red: 50");
        greenLbl = new Label("Green: 50");
        blueLbl = new Label("Blue: 50");
        sizeLbl = new Label("Size: 30");

        xPosLbl.setPrefSize(150, 100);
        yPosLbl.setPrefSize(150, 100);
        redLbl.setPrefSize(150, 100);
        greenLbl.setPrefSize(150, 100);
        blueLbl.setPrefSize(150, 100);
        sizeLbl.setPrefSize(150, 100);
        xPosSld.setPrefSize(300, 100);
        yPosSld.setPrefSize(300, 100);
        redSld.setPrefSize(300, 100);
        greenSld.setPrefSize(300, 100);
        blueSld.setPrefSize(300, 100);
        sizeSld.setPrefSize(300, 100);

        root.add(drawPane, 0,0, 1, 7);
        root.add(xPosSld, 2,0);
        root.add(yPosSld, 2,1);
        root.add(redSld, 2,2);
        root.add(greenSld, 2,3);
        root.add(blueSld, 2,4);
        root.add(sizeSld, 2,5);
        root.add(xPosLbl, 1,0);
        root.add(yPosLbl, 1,1);
        root.add(redLbl, 1,2);
        root.add(greenLbl, 1,3);
        root.add(blueLbl, 1,4);
        root.add(sizeLbl, 1,5);
        root.setPadding(new Insets(10));
        root.setHgap(10);

        circle.centerXProperty().bindBidirectional(xPosSld.valueProperty());
        circle.centerYProperty().bindBidirectional(yPosSld.valueProperty());
        circle.radiusProperty().bind(sizeSld.valueProperty());
        xPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            xPosLbl.setText("X Position: " + newValue.intValue());
        });
        yPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            yPosLbl.setText("Y Position: " + newValue.intValue());
        });
        redSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            redLbl.setText("Red: " + newValue.intValue());
            circle.setFill(Color.rgb((int)redSld.getValue(), (int)greenSld.getValue(), (int)blueSld.getValue()));
        });
        greenSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            greenLbl.setText("Green: " + newValue.intValue());
            circle.setFill(Color.rgb((int)redSld.getValue(), (int)greenSld.getValue(), (int)blueSld.getValue()));
        });
        blueSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            blueLbl.setText("Blue: " + newValue.intValue());
            circle.setFill(Color.rgb((int)redSld.getValue(), (int)greenSld.getValue(), (int)blueSld.getValue()));
        });
        sizeSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            sizeLbl.setText("Size: " + newValue.intValue());
        });

        drawPane.getChildren().add(circle);

    }

    public static void main(String[] args) {
        launch(args);
    }

}

