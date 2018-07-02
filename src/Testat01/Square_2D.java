package Testat01;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Square_2D extends Application {

    private GridPane root;
    private Pane drawPane;
    private Slider xPosSld, yPosSld, redSld, greenSld, blueSld, sizeSld, rotateSld;
    private Label xPosLbl, yPosLbl, redLbl, greenLbl, blueLbl, sizeLbl, rotateLbl;
    private Polygon square;
    private double[][] model = new double[][] {
            {  -50.0,   -50.0, 1.0},
            {50.0,   -50.0, 1.0},
            {50.0, 50.0, 1.0},
            {  -50.0, 50.0, 1.0}};
    private double[][] M = new double[][]{
            {1.0, 0.0, 0.0},
            {0.0, 1.0, 0.0},
            {0.0, 0.0, 1.0}
    };
    private double[][] Translation, Rotation, Scaling, positions;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new GridPane();
        Scene scene = new Scene(root);
        initSquare();
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

        drawPane.setPrefSize(1400, 1080);
        xPosSld = new Slider(0,1380,0);
        yPosSld = new Slider(0,1020,0);
        redSld = new Slider(0,255,50);
        greenSld = new Slider(0, 255, 50);
        blueSld = new Slider(0, 255, 50);
        sizeSld = new Slider(0, 10, 1);
        rotateSld = new Slider(0, 360, 0);
        xPosLbl = new Label("X Position: 30");
        yPosLbl = new Label("Y Position: 30");
        redLbl = new Label("Red: 50");
        greenLbl = new Label("Green: 50");
        blueLbl = new Label("Blue: 50");
        sizeLbl = new Label("Size: 1");
        rotateLbl = new Label("Rotation: 0");

        xPosLbl.setPrefSize(150, 100);
        yPosLbl.setPrefSize(150, 100);
        redLbl.setPrefSize(150, 100);
        greenLbl.setPrefSize(150, 100);
        blueLbl.setPrefSize(150, 100);
        sizeLbl.setPrefSize(150, 100);
        rotateLbl.setPrefSize(150, 100);
        xPosSld.setPrefSize(300, 100);
        yPosSld.setPrefSize(300, 100);
        redSld.setPrefSize(300, 100);
        greenSld.setPrefSize(300, 100);
        blueSld.setPrefSize(300, 100);
        sizeSld.setPrefSize(300, 100);
        rotateSld.setPrefSize(300, 100);

        root.add(drawPane, 0,0, 1, 8);
        root.add(xPosSld, 2,0);
        root.add(yPosSld, 2,1);
        root.add(redSld, 2,2);
        root.add(greenSld, 2,3);
        root.add(blueSld, 2,4);
        root.add(sizeSld, 2,5);
        root.add(rotateSld, 2, 6);
        root.add(xPosLbl, 1,0);
        root.add(yPosLbl, 1,1);
        root.add(redLbl, 1,2);
        root.add(greenLbl, 1,3);
        root.add(blueLbl, 1,4);
        root.add(sizeLbl, 1,5);
        root.add(rotateLbl, 1, 6);
        root.setPadding(new Insets(10));
        root.setHgap(10);

        xPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            xPosLbl.setText("X Position: " + newValue.intValue());
            updateTransformations(newValue.doubleValue(), yPosSld.getValue(), sizeSld.getValue(), rotateSld.getValue());
        });
        yPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            yPosLbl.setText("Y Position: " + newValue.intValue());
            updateTransformations(xPosSld.getValue(), newValue.doubleValue(), sizeSld.getValue(), rotateSld.getValue());
        });
        redSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            redLbl.setText("Red: " + newValue.intValue());
            square.setStroke(Color.rgb((int)redSld.getValue(), (int)greenSld.getValue(), (int)blueSld.getValue()));
        });
        greenSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            greenLbl.setText("Green: " + newValue.intValue());
            square.setStroke(Color.rgb((int)redSld.getValue(), (int)greenSld.getValue(), (int)blueSld.getValue()));
        });
        blueSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            blueLbl.setText("Blue: " + newValue.intValue());
            square.setStroke(Color.rgb((int)redSld.getValue(), (int)greenSld.getValue(), (int)blueSld.getValue()));
        });
        sizeSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            sizeLbl.setText("Size: " + newValue.intValue());
            updateTransformations(xPosSld.getValue(), yPosSld.getValue(), newValue.doubleValue(), rotateSld.getValue());
        });
        rotateSld.valueProperty().addListener((observable, oldValue, newValue) -> {
            rotateLbl.setText("Rotation: " + newValue.intValue());
            updateTransformations(xPosSld.getValue(), yPosSld.getValue(), sizeSld.getValue(),newValue.doubleValue());
        });
        drawPane.getChildren().add(square);

    }

    public void updateTransformations(double xTrans, double yTrans,double skalierung,double rotation) {
        Translation = new double[][]{
                {1.0, 0.0, xTrans},
                {0.0, 1.0, yTrans},
                {0.0, 0.0, 1.0},
        };
        Scaling = new double[][]{
                {skalierung, 0.0, 1.0},
                {0.0, skalierung, 1.0},
                {0.0, 0.0, 1.0}
        };
        double sin = Math.sin(Math.toRadians(rotation));
        double cos = Math.cos(Math.toRadians(rotation));
        Rotation = new double[][]{
                {cos, -sin, 0.0},
                {sin, cos, 0.0},
                {0.0, 0.0, 1.0}
        };

        M  = Matrix.matMult(Translation, Matrix.matMult(Rotation, Scaling));
        applyTransformation();
    }

    public void applyTransformation() {
        positions = new double[model.length][];
        for (int i = 0; i < model.length; i++) {
            positions[i] = Matrix.matMult(M, model[i]);
        }
        updateModel();
    }

    public void initSquare () {
        square = new Polygon();
        square.getPoints().addAll(model[0][0], model[0][1], model[1][0], model[1][1], model[2][0], model[2][1], model[3][0], model[3][1]);
        square.setStroke(Color.BLACK);
        square.setFill(Color.TRANSPARENT);
        square.setStrokeWidth(3);
    }

    public void updateModel() {
        square.getPoints().clear();
        square.getPoints().addAll(positions[0][0], positions[0][1], positions[1][0], positions[1][1], positions[2][0], positions[2][1], positions[3][0], positions[3][1]);
    }

    public static void main(String[] args) {
        launch(args);
    }


}

