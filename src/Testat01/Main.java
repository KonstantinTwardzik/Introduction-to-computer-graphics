package Testat01;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private GridPane root;
    private Pane drawPane;
    private Slider xPosSld, yPosSld, zPosSld, xRotSld, yRotSld, zRotSld, scaleSld, xCntrSld, yCntrSld, zCntrSld;
    private Label xPosLbl, yPosLbl, zPosLbl, xRotLbl, yRotLbl, zRotLbl, scaleLbl, xCntrLbl, yCntrLbl, zCntrLbl;

    private double[][] Translation, Rotation, Skalierung, positions;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new GridPane();
        Scene scene = new Scene(root);
        initView();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Circle transformation");
        primaryStage.setWidth(1775);
        primaryStage.setHeight(1080);
        primaryStage.setFullScreen(true);
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

        drawPane.setPrefSize(1100, 800);
        xPosSld = new Slider(0,1000,500);
        yPosSld = new Slider(0,1000,500);
        zPosSld = new Slider(0, 1000, 500);
        xRotSld = new Slider(0,360,0);
        yRotSld = new Slider(0, 360, 0);
        zRotSld = new Slider(0, 360, 0);
        scaleSld = new Slider(0, 10, 1);
        xCntrSld = new Slider(0, 360, 0);
        yCntrSld = new Slider(-500, 500, 0);
        zCntrSld = new Slider(-500, 500, 0);
        xPosLbl = new Label("X Position: 500");
        yPosLbl = new Label("Y Position: 500");
        zPosLbl = new Label("Z Position: 500");
        xRotLbl = new Label("X Rotation: 0");
        yRotLbl = new Label("Y Rotation: 0");
        zRotLbl = new Label("Z Rotation: 0");
        scaleLbl = new Label("Scale: 1");
        xCntrLbl = new Label("X Center: 0");
        yCntrLbl = new Label("Y Center: 0");
        zCntrLbl = new Label("Z Center: 0");


        xPosLbl.setPrefSize(150, 80);
        yPosLbl.setPrefSize(150, 80);
        zPosLbl.setPrefSize(150, 80);
        xRotLbl.setPrefSize(150, 80);
        yRotLbl.setPrefSize(150, 80);
        zRotLbl.setPrefSize(150, 80);
        scaleLbl.setPrefSize(150, 80);
        xCntrLbl.setPrefSize(150, 80);
        yCntrLbl.setPrefSize(150, 80);
        zCntrLbl.setPrefSize(150, 80);
        xPosSld.setPrefSize(300, 80);
        yPosSld.setPrefSize(300, 80);
        zPosSld.setPrefSize(300, 80);
        xRotSld.setPrefSize(300, 80);
        yRotSld.setPrefSize(300, 80);
        zRotSld.setPrefSize(300, 80);
        scaleSld.setPrefSize(300, 80);
        xCntrSld.setPrefSize(300, 80);
        yCntrSld.setPrefSize(300, 80);
        zCntrSld.setPrefSize(300, 80);


        root.add(drawPane, 1,1, 1, 10);
        root.add(xPosSld, 3,1);
        root.add(yPosSld, 3,2);
        root.add(zPosSld, 3, 3);
        root.add(xRotSld, 3,4);
        root.add(yRotSld, 3,5);
        root.add(zRotSld, 3,6);
        root.add(scaleSld, 3,7);
        root.add(xCntrSld, 3, 8);
        root.add(yCntrSld, 3,9);
        root.add(zCntrSld, 3,10);
        root.add(xPosLbl, 2,1);
        root.add(yPosLbl, 2,2);
        root.add(zPosLbl, 2,3);
        root.add(xRotLbl, 2,4);
        root.add(yRotLbl, 2,5);
        root.add(zRotLbl, 2,6);
        root.add(scaleLbl, 2,7);
        root.add(xCntrLbl, 2, 8);
        root.add(yCntrLbl, 2,9);
        root.add(zCntrLbl, 2,10);
        root.setPadding(new Insets(10));
        root.setHgap(2);

        xPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            xPosLbl.setText("X Position: " + newValue.intValue());
            updateTransformations(newValue.doubleValue(), yPosSld.getValue(), scaleSld.getValue(), xCntrSld.getValue());
        });
        yPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            yPosLbl.setText("Y Position: " + newValue.intValue());
            updateTransformations(xPosSld.getValue(), newValue.doubleValue(), scaleSld.getValue(), xCntrSld.getValue());
        });
        xRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            xRotLbl.setText("Red: " + newValue.intValue());
        });
        yRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            yRotLbl.setText("Green: " + newValue.intValue());
        });
        zRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            zRotLbl.setText("Blue: " + newValue.intValue());
        });
        scaleSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            scaleLbl.setText("Size: " + newValue.intValue());
            updateTransformations(xPosSld.getValue(), yPosSld.getValue(), newValue.doubleValue(), xCntrSld.getValue());
        });
        xCntrSld.valueProperty().addListener((observable, oldValue, newValue) -> {
            xCntrLbl.setText("Rotation: " + newValue.intValue());
            updateTransformations(xPosSld.getValue(), yPosSld.getValue(), scaleSld.getValue(),newValue.doubleValue());
        });

        root.setGridLinesVisible(true);

    }

    public void updateTransformations(double xTrans, double yTrans,double skalierung,double rotation) {
        Translation = new double[][]{
                {1.0, 0.0, xTrans},
                {0.0, 1.0, yTrans},
                {0.0, 0.0, 1.0},
        };
        Skalierung = new double[][]{
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

        applyTransformation();
    }

    public void applyTransformation() {

        updateModel();
    }

    public void initModel () {

    }

    public void updateModel() {
    }

    public static void main(String[] args) {
        launch(args);
    }


}

