package Testat01;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private GridPane root, pivotPointPane, negativePivotPointPane, totalRotationPane, scalePane, translationPane, totalPane, xRotationPane, yRotationPane, zRotationPane, sliderPane;
    private Pane drawPane;
    private Slider xPosSld, yPosSld, zPosSld, xRotSld, yRotSld, zRotSld, scaleSld, xCntrSld, yCntrSld, zCntrSld;
    private Label xPosLbl, yPosLbl, zPosLbl, xRotLbl, yRotLbl, zRotLbl, scaleLbl, xCntrLbl, yCntrLbl, zCntrLbl, ttlRotMat, xRotMat, yRotMat, zRotMat, pPointMat, npPointMat, scaleMat, translationMat, totalMat;
    private Circle p1, p2, p3, p4, pPivot;
    private Line l01, l12, l20, l03, l13, l23;
    private double[][] UnitMatrix, translation, totalRotation, yRotation, zRotation, scaling, negativePivotPointTranslation, pivotPointTranslation, model, xRotation;
    private SimpleDoubleProperty[][] workingModel, UnitMatrixProp, translationProp, xRotationProp, yRotationProp, zRotationProp, scalingProp, pivotPointTransProp, negativePivotPointTransProp, totalRotProp;
    private SimpleDoubleProperty[] pivotPoint;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new GridPane();
        Scene scene = new Scene(root);
        initLogic();
        initView();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Circle transformation");
        primaryStage.show();
    }
    
    // initiates all matrices and points
    private void initLogic() {
        pivotPoint = new SimpleDoubleProperty[4];
        pivotPoint[0] = new SimpleDoubleProperty(0.0);
        pivotPoint[1] = new SimpleDoubleProperty(0.0);
        pivotPoint[2] = new SimpleDoubleProperty(0.0);
        pivotPoint[3] = new SimpleDoubleProperty(1.0);

        model = new double[][] {
                {-25.0,  20.0, -20.0 , 1.0},
                { 25.0,  20.0, -20.0 , 1.0},
                {  0.0,  20.0,  20.0 , 1.0},
                {  0.0, -20.0,   0.0 , 1.0}
        };

        UnitMatrix = new double[][] {
                {1.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        UnitMatrixProp = new SimpleDoubleProperty[UnitMatrix.length][UnitMatrix[0].length];
        for (int i=0; i<UnitMatrix.length; i++) {
            for (int j=0; j<UnitMatrix[i].length; j++) {
                UnitMatrixProp[i][j] = new SimpleDoubleProperty(UnitMatrix[i][j]);
            }
        }
        translation = new double[][]{
                {1.0, 0.0, 0.0, 600.0},
                {0.0, 1.0, 0.0, 500.0},
                {0.0, 0.0, 1.0, 500.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        translationProp = new SimpleDoubleProperty[translation.length][translation[0].length];
        for (int i=0; i<translation.length; i++) {
            for (int j=0; j<translation[i].length; j++) {
                translationProp[i][j] = new SimpleDoubleProperty(translation[i][j]);
            }
        }
        double sin = Math.sin(Math.toRadians(0.0));
        double cos = Math.cos(Math.toRadians(0.0));
        xRotation = new double[][] {
                {1.0, 0.0,  0.0, 0.0},
                {0.0, cos, -sin, 0.0},
                {0.0, sin,  cos, 0.0},
                {0.0, 0.0,  0.0, 1.0}
        };
        xRotationProp = new SimpleDoubleProperty[xRotation.length][xRotation[0].length];
        for (int i=0; i<xRotation.length; i++) {
            for (int j=0; j<xRotation[i].length; j++) {
                xRotationProp[i][j] = new SimpleDoubleProperty(xRotation[i][j]);
            }
        }
        yRotation = new double[][] {
                {cos, 0.0, -sin, 0.0},
                {0.0, 1.0,  0.0, 0.0},
                {sin, 0.0,  cos, 0.0},
                {0.0, 0.0,  0.0, 1.0}
        };
        yRotationProp = new SimpleDoubleProperty[yRotation.length][yRotation[0].length];
        for (int i=0; i<yRotation.length; i++) {
            for (int j=0; j<yRotation[i].length; j++) {
                yRotationProp[i][j] = new SimpleDoubleProperty(yRotation[i][j]);
            }
        }
        zRotation = new double[][] {
                {cos, -sin, 0.0, 0.0},
                {sin,  cos, 0.0, 0.0},
                {0.0,  0.0, 1.0, 0.0},
                {0.0,  0.0, 0.0, 1.0}
        };
        zRotationProp = new SimpleDoubleProperty[zRotation.length][zRotation[0].length];
        for (int i=0; i<zRotation.length; i++) {
            for (int j=0; j<zRotation[i].length; j++) {
                zRotationProp[i][j] = new SimpleDoubleProperty(zRotation[i][j]);
            }
        }
        scaling = new double[][] {
                {1.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        scalingProp = new SimpleDoubleProperty[scaling.length][scaling[0].length];
        for (int i=0; i<scaling.length; i++) {
            for (int j=0; j<scaling[i].length; j++) {
                scalingProp[i][j] = new SimpleDoubleProperty(scaling[i][j]);
            }
        }
        pivotPointTranslation = new double[][]{
                {1.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        pivotPointTransProp = new SimpleDoubleProperty[pivotPointTranslation.length][pivotPointTranslation[0].length];
        for (int i=0; i<pivotPointTranslation.length; i++) {
            for (int j=0; j<pivotPointTranslation[i].length; j++) {
                pivotPointTransProp[i][j] = new SimpleDoubleProperty(pivotPointTranslation[i][j]);
            }
        }
        negativePivotPointTranslation = new double[][]{
                {1.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        negativePivotPointTransProp = new SimpleDoubleProperty[negativePivotPointTranslation.length][negativePivotPointTranslation[0].length];
        for (int i=0; i<negativePivotPointTranslation.length; i++) {
            for (int j=0; j<negativePivotPointTranslation[i].length; j++) {
                negativePivotPointTransProp[i][j] = new SimpleDoubleProperty(negativePivotPointTranslation[i][j]);
            }
        }
        totalRotation = new double[][]{
                {1.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        totalRotProp = new SimpleDoubleProperty[totalRotation.length][totalRotation[0].length];
        for (int i=0; i<totalRotation.length; i++) {
            for (int j=0; j<totalRotation[i].length; j++) {
                totalRotProp[i][j] = new SimpleDoubleProperty(totalRotation[i][j]);
            }
        }
    }

    // initiates the view (Labels, Matrix-views, Sliders) and applies Listeners
    private void initView() {
        //Clipping Drawpane & DrawPane
        Rectangle clipping = new Rectangle();
        drawPane = new Pane();
        drawPane.setPrefSize(1100, 1000);
        drawPane.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        drawPane.setClip(clipping);
        drawPane.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipping.setWidth(newValue.getWidth());
            clipping.setHeight(newValue.getHeight());
        });

        //Matrix Panes
        sliderPane = new GridPane();
        pivotPointPane = new GridPane();
        totalRotationPane = new GridPane();
        scalePane = new GridPane();
        translationPane = new GridPane();
        totalPane = new GridPane();
        xRotationPane = new GridPane();
        yRotationPane = new GridPane();
        zRotationPane = new GridPane();
        pivotPointPane = new GridPane();
        negativePivotPointPane = new GridPane();
        scalePane = new GridPane();
        translationPane = new GridPane();
        totalPane = new GridPane();

        //Sliders
        xPosSld = new Slider(0,1200,600);
        yPosSld = new Slider(0,1200,500);
        zPosSld = new Slider(0,1200, 500);
        xRotSld = new Slider(0,360,0);
        yRotSld = new Slider(0, 360, 0);
        zRotSld = new Slider(0, 360, 0);
        scaleSld = new Slider(0, 10, 1);
        xCntrSld = new Slider(-500, 500, 0);
        yCntrSld = new Slider(-500, 500, 0);
        zCntrSld = new Slider(-500, 500, 0);
        xPosLbl = new Label("X Position: 600");
        yPosLbl = new Label("Y Position: 500");
        zPosLbl = new Label("Z Position: 500");
        xRotLbl = new Label("X Rotation: 0");
        yRotLbl = new Label("Y Rotation: 0");
        zRotLbl = new Label("Z Rotation: 0");
        scaleLbl = new Label("Scale: 1");
        xCntrLbl = new Label("X Center: 0");
        yCntrLbl = new Label("Y Center: 0");
        zCntrLbl = new Label("Z Center: 0");
        ttlRotMat = new Label("Total Rotation:");
        xRotMat = new Label("X Rotation:");
        yRotMat = new Label("Y Rotation:");
        zRotMat = new Label("Z Rotation:");
        pPointMat = new Label("Pivot SplinePoint:");
        npPointMat = new Label("Negative Pivot SplinePoint:");
        scaleMat = new Label("Scale:");
        translationMat = new Label("Translation:");
        totalMat = new Label("Total:");
        xPosSld.setMajorTickUnit(200);
        xPosSld.setMinorTickCount(5);
        xPosSld.setShowTickLabels(true);
        yPosSld.setMajorTickUnit(200);
        yPosSld.setMinorTickCount(5);
        yPosSld.setShowTickLabels(true);
        zPosSld.setMajorTickUnit(200);
        zPosSld.setMinorTickCount(5);
        zPosSld.setShowTickLabels(true);
        xRotSld.setMajorTickUnit(90);
        xRotSld.setMinorTickCount(5);
        xRotSld.setShowTickLabels(true);
        yRotSld.setMajorTickUnit(90);
        yRotSld.setMinorTickCount(5);
        yRotSld.setShowTickLabels(true);
        zRotSld.setMajorTickUnit(90);
        zRotSld.setMinorTickCount(5);
        zRotSld.setShowTickLabels(true);
        scaleSld.setMajorTickUnit(5);
        scaleSld.setMinorTickCount(5);
        scaleSld.setShowTickLabels(true);
        xCntrSld.setMajorTickUnit(500);
        xCntrSld.setMinorTickCount(5);
        xCntrSld.setShowTickLabels(true);
        yCntrSld.setMajorTickUnit(500);
        yCntrSld.setMinorTickCount(5);
        yCntrSld.setShowTickLabels(true);
        zCntrSld.setMajorTickUnit(500);
        zCntrSld.setMinorTickCount(5);
        zCntrSld.setShowTickLabels(true);
        sliderPane.add(xPosSld, 1,1);
        sliderPane.add(yPosSld, 1,2);
        sliderPane.add(zPosSld, 1, 3);
        sliderPane.add(xRotSld, 1,4);
        sliderPane.add(yRotSld, 1,5);
        sliderPane.add(zRotSld, 1,6);
        sliderPane.add(scaleSld, 1,7);
        sliderPane.add(xCntrSld, 1, 8);
        sliderPane.add(yCntrSld, 1,9);
        sliderPane.add(zCntrSld, 1,10);
        sliderPane.add(xPosLbl, 0,1);
        sliderPane.add(yPosLbl, 0,2);
        sliderPane.add(zPosLbl, 0,3);
        sliderPane.add(xRotLbl, 0,4);
        sliderPane.add(yRotLbl, 0,5);
        sliderPane.add(zRotLbl, 0,6);
        sliderPane.add(scaleLbl, 0,7);
        sliderPane.add(xCntrLbl, 0, 8);
        sliderPane.add(yCntrLbl, 0,9);
        sliderPane.add(zCntrLbl, 0,10);


        // Matrices
        totalRotationPane.add(ttlRotMat, 0,0, 3, 1);
        xRotationPane.add(xRotMat, 0, 0, 3, 1);
        yRotationPane.add(yRotMat, 0, 0, 3, 1);
        zRotationPane.add(zRotMat, 0, 0, 3, 1);
        pivotPointPane.add(pPointMat, 0, 0, 3, 1);
        negativePivotPointPane.add(npPointMat, 0, 0, 3, 1);
        scalePane.add(scaleMat, 0, 0, 3, 1);
        translationPane.add(translationMat, 0, 0, 3, 1);
        totalPane.add(totalMat, 0, 0, 3, 1);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + totalRotation[i][j]);
                label.setPrefSize(50, 30);
                totalRotationPane.add(label, j, i+1);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + xRotation[i][j]);
                label.setPrefSize(50, 30);
                xRotationPane.add(label, j, i+1);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + yRotation[i][j]);
                label.setPrefSize(50, 30);
                yRotationPane.add(label, j, i+1);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + zRotation[i][j]);
                label.setPrefSize(50, 30);
                zRotationPane.add(label, j, i+1);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + pivotPointTranslation[i][j]);
                label.setPrefSize(50, 30);
                pivotPointPane.add(label, j, i+1);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + negativePivotPointTranslation[i][j]);
                label.setPrefSize(50, 30);
                negativePivotPointPane.add(label, j, i+1);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + scaling[i][j]);
                label.setPrefSize(50, 30);
                scalePane.add(label, j, i+1);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + translation[i][j]);
                label.setPrefSize(50, 30);
                translationPane.add(label, j, i+1);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label("" + UnitMatrix[i][j]);
                label.setPrefSize(50, 30);
                totalPane.add(label, j, i+1);
            }
        }
        xPosLbl.setPrefSize(120, 120);
        yPosLbl.setPrefSize(120, 120);
        zPosLbl.setPrefSize(120, 120);
        xRotLbl.setPrefSize(120, 120);
        yRotLbl.setPrefSize(120, 120);
        zRotLbl.setPrefSize(120, 120);
        scaleLbl.setPrefSize(120, 120);
        xCntrLbl.setPrefSize(120, 120);
        yCntrLbl.setPrefSize(120, 120);
        zCntrLbl.setPrefSize(120, 120);
        xPosSld.setPrefSize(450, 120);
        yPosSld.setPrefSize(450, 120);
        zPosSld.setPrefSize(450, 120);
        xRotSld.setPrefSize(450, 120);
        yRotSld.setPrefSize(450, 120);
        zRotSld.setPrefSize(450, 120);
        scaleSld.setPrefSize(450, 120);
        xCntrSld.setPrefSize(450, 120);
        yCntrSld.setPrefSize(450, 120);
        zCntrSld.setPrefSize(450, 120);

        // Add everything to RootPane
        root.add(drawPane, 0,1, 6, 1);
        root.add(sliderPane, 6, 1, 4, 1);
        root.add(totalRotationPane, 0, 0);
        root.add(xRotationPane, 1, 0);
        root.add(yRotationPane, 2, 0);
        root.add(zRotationPane, 3, 0);
        root.add(pivotPointPane, 4, 0);
        root.add(negativePivotPointPane, 5, 0);
        root.add(scalePane, 6, 0);
        root.add(translationPane, 7, 0);
        root.add(totalPane, 8, 0);
        root.setPadding(new Insets(10));
        root.setHgap(5);
        root.setVgap(5);

        //Event handling
        xPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            xPosLbl.setText("X Position: " + newValue.intValue());
            updateTranslation(newValue.doubleValue(), yPosSld.getValue(), zPosSld.getValue());
        });
        yPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            yPosLbl.setText("Y Position: " + newValue.intValue());
            updateTranslation(xPosSld.getValue(), newValue.doubleValue(), zPosSld.getValue());
        });
        zPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            zPosLbl.setText("Z Position: " + newValue.intValue());
            updateTranslation(xPosSld.getValue(), yPosSld.getValue(), newValue.doubleValue());
        });
        xRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            xRotLbl.setText("X Rotation: " + newValue.intValue());
            updateXRotation(newValue.doubleValue());
        });
        yRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            yRotLbl.setText("Y Rotation: " + newValue.intValue());
            updateYRotation(newValue.doubleValue());
        });
        zRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            zRotLbl.setText("Z Rotation: " + newValue.intValue());
            updateZRotation(newValue.doubleValue());
        });
        scaleSld.valueProperty().addListener((ov, oldValue, newValue) -> {
            scaleLbl.setText("Scale: " + newValue.intValue());
            updateScaling(newValue.doubleValue());
        });
        xCntrSld.valueProperty().addListener((observable, oldValue, newValue) -> {
            xCntrLbl.setText("X Center Position: " + newValue.intValue());
            updateCenterTranslation(newValue.doubleValue(), yCntrSld.getValue(), zCntrSld.getValue());
        });
        yCntrSld.valueProperty().addListener((observable, oldValue, newValue) -> {
            yCntrLbl.setText("Y Center Position: " + newValue.intValue());
            updateCenterTranslation(xCntrSld.getValue(), newValue.doubleValue(), zCntrSld.getValue());
        });
        zCntrSld.valueProperty().addListener((observable, oldValue, newValue) -> {
            zCntrLbl.setText("Z Center Position: " + newValue.intValue());
            updateCenterTranslation(xCntrSld.getValue(), yCntrSld.getValue(),newValue.doubleValue());
        });

        //Draw Model on drawPane & bind MatrcieViews to Matrices
        bindModelToView();
        bindMatricesToView();
    }

    //updates Matrix
    private void updateTranslation(double xTrans, double yTrans, double zTrans) {
        translationProp[0][3].set(xTrans);
        translationProp[1][3].set(yTrans);
        translationProp[2][3].set(zTrans);
        updateCenterTranslation(xCntrSld.getValue(), yCntrSld.getValue(), zCntrSld.getValue());
    }

    //updates Matrix
    private void updateXRotation (double xRot) {
        double sin = Math.sin(Math.toRadians(xRot));
        double cos = Math.cos(Math.toRadians(xRot));
        xRotationProp[1][1].set(cos);
        xRotationProp[1][2].set(-sin);
        xRotationProp[2][1].set(sin);
        xRotationProp[2][2].set(cos);
        applyTransformation();
    }

    //updates Matrix
    private void updateYRotation (double yRot) {
        double sin = Math.sin(Math.toRadians(yRot));
        double cos = Math.cos(Math.toRadians(yRot));
        yRotationProp[0][0].set(cos);
        yRotationProp[0][2].set(-sin);
        yRotationProp[2][0].set(sin);
        yRotationProp[2][2].set(cos);
        applyTransformation();
    }

    //updates Matrix
    private void updateZRotation (double zRot) {
        double sin = Math.sin(Math.toRadians(zRot));
        double cos = Math.cos(Math.toRadians(zRot));
        zRotationProp[0][0].set(cos);
        zRotationProp[0][1].set(-sin);
        zRotationProp[1][0].set(sin);
        zRotationProp[1][1].set(cos);
        applyTransformation();
    }

    //updates Matrix
    private void updateScaling (double scale) {
        scalingProp[0][0].set(scale);
        scalingProp[1][1].set(scale);
        scalingProp[2][2].set(scale);
        applyTransformation();
    }

    //updates Matrices
    private void updateCenterTranslation(double xCntrTrans, double yCntrTrans, double zCntrTrans) {
        pivotPointTransProp[0][3].set(xCntrTrans + xPosSld.getValue());
        pivotPointTransProp[1][3].set(yCntrTrans + yPosSld.getValue());
        pivotPointTransProp[2][3].set(zCntrTrans + zPosSld.getValue());
        negativePivotPointTransProp[0][3].set(-xCntrTrans - xPosSld.getValue());
        negativePivotPointTransProp[1][3].set(-yCntrTrans - yPosSld.getValue());
        negativePivotPointTransProp[2][3].set(-zCntrTrans - zPosSld.getValue());
        applyTransformation();
    }

    //calculates the total matrix and applies it to the model
    private void applyTransformation() {
        // calculate Model
        for (int i=0; i<xRotation.length; i++) {
            for (int j=0; j<xRotation[i].length; j++) {
                xRotation[i][j] = xRotationProp[i][j].getValue();
                yRotation[i][j] = yRotationProp[i][j].getValue();
                zRotation[i][j] = zRotationProp[i][j].getValue();
                pivotPointTranslation[i][j] = pivotPointTransProp[i][j].getValue();
                negativePivotPointTranslation[i][j] = negativePivotPointTransProp[i][j].getValue();
                scaling[i][j] = scalingProp[i][j].getValue();
                translation[i][j] = translationProp[i][j].getValue();
            }
        }
        totalRotation =  Matrix.matMult(xRotation, Matrix.matMult(yRotation, zRotation));

        UnitMatrix = Matrix.matMult(pivotPointTranslation, Matrix.matMult(totalRotation, Matrix.matMult(scaling, Matrix.matMult(negativePivotPointTranslation, translation))));

        // set Model
        double helper[] = new double[workingModel[0].length];
        for (int i = 0; i < workingModel.length; i++) {
            helper = Matrix.matMult(UnitMatrix, model[i]);
            for (int j = 0; j < workingModel[i].length; j++) {
                workingModel[i][j].set(helper[j]);
            }
        }

        for (int i=0; i<UnitMatrix.length; i++) {
            for (int j=0; j<UnitMatrix[i].length; j++) {
                UnitMatrixProp[i][j].set(UnitMatrix[i][j]);
                totalRotProp[i][j].set(totalRotation[i][j]);
            }
        }

    }

    //binds Matrices to the View
    private void bindMatricesToView() {
        Label label;
        label = (Label) totalRotationPane.getChildren().get(1);
        label.textProperty().bind(totalRotProp[0][0].asString());
        label = (Label) totalRotationPane.getChildren().get(2);
        label.textProperty().bind(totalRotProp[0][1].asString());
        label = (Label) totalRotationPane.getChildren().get(3);
        label.textProperty().bind(totalRotProp[0][2].asString());
        label = (Label) totalRotationPane.getChildren().get(5);
        label.textProperty().bind(totalRotProp[1][0].asString());
        label = (Label) totalRotationPane.getChildren().get(6);
        label.textProperty().bind(totalRotProp[1][1].asString());
        label = (Label) totalRotationPane.getChildren().get(7);
        label.textProperty().bind(totalRotProp[1][2].asString());
        label = (Label) totalRotationPane.getChildren().get(9);
        label.textProperty().bind(totalRotProp[2][0].asString());
        label = (Label) totalRotationPane.getChildren().get(10);
        label.textProperty().bind(totalRotProp[2][1].asString());
        label = (Label) totalRotationPane.getChildren().get(11);
        label.textProperty().bind(totalRotProp[2][2].asString());

        label = (Label) xRotationPane.getChildren().get(6);
        label.textProperty().bind(xRotationProp[1][1].asString());
        label = (Label) xRotationPane.getChildren().get(7);
        label.textProperty().bind(xRotationProp[1][2].asString());
        label = (Label) xRotationPane.getChildren().get(10);
        label.textProperty().bind(xRotationProp[2][1].asString());
        label = (Label) xRotationPane.getChildren().get(11);
        label.textProperty().bind(xRotationProp[2][2].asString());

        label = (Label) yRotationPane.getChildren().get(1);
        label.textProperty().bind(yRotationProp[0][0].asString());
        label = (Label) yRotationPane.getChildren().get(3);
        label.textProperty().bind(yRotationProp[0][2].asString());
        label = (Label) yRotationPane.getChildren().get(9);
        label.textProperty().bind(yRotationProp[2][0].asString());
        label = (Label) yRotationPane.getChildren().get(11);
        label.textProperty().bind(yRotationProp[2][2].asString());

        label = (Label) zRotationPane.getChildren().get(1);
        label.textProperty().bind(zRotationProp[0][0].asString());
        label = (Label) zRotationPane.getChildren().get(2);
        label.textProperty().bind(zRotationProp[0][1].asString());
        label = (Label) zRotationPane.getChildren().get(5);
        label.textProperty().bind(zRotationProp[1][0].asString());
        label = (Label) zRotationPane.getChildren().get(6);
        label.textProperty().bind(zRotationProp[1][1].asString());

        label = (Label) translationPane.getChildren().get(4);
        label.textProperty().bind(translationProp[0][3].asString());
        label = (Label) translationPane.getChildren().get(8);
        label.textProperty().bind(translationProp[1][3].asString());
        label = (Label) translationPane.getChildren().get(12);
        label.textProperty().bind(translationProp[2][3].asString());

        label = (Label) scalePane.getChildren().get(1);
        label.textProperty().bind(scalingProp[0][0].asString());
        label = (Label) scalePane.getChildren().get(6);
        label.textProperty().bind(scalingProp[1][1].asString());
        label = (Label) scalePane.getChildren().get(11);
        label.textProperty().bind(scalingProp[2][2].asString());

        label = (Label) pivotPointPane.getChildren().get(4);
        label.textProperty().bind(pivotPointTransProp[0][3].asString());
        label = (Label) pivotPointPane.getChildren().get(8);
        label.textProperty().bind(pivotPointTransProp[1][3].asString());
        label = (Label) pivotPointPane.getChildren().get(12);
        label.textProperty().bind(pivotPointTransProp[2][3].asString());

        label = (Label) negativePivotPointPane.getChildren().get(4);
        label.textProperty().bind(negativePivotPointTransProp[0][3].asString());
        label = (Label) negativePivotPointPane.getChildren().get(8);
        label.textProperty().bind(negativePivotPointTransProp[1][3].asString());
        label = (Label) negativePivotPointPane.getChildren().get(12);
        label.textProperty().bind(negativePivotPointTransProp[2][3].asString());

        label = (Label) totalPane.getChildren().get(1);
        label.textProperty().bind(UnitMatrixProp[0][0].asString());
        label = (Label) totalPane.getChildren().get(2);
        label.textProperty().bind(UnitMatrixProp[0][1].asString());
        label = (Label) totalPane.getChildren().get(3);
        label.textProperty().bind(UnitMatrixProp[0][2].asString());
        label = (Label) totalPane.getChildren().get(4);
        label.textProperty().bind(UnitMatrixProp[0][3].asString());
        label = (Label) totalPane.getChildren().get(5);
        label.textProperty().bind(UnitMatrixProp[1][0].asString());
        label = (Label) totalPane.getChildren().get(6);
        label.textProperty().bind(UnitMatrixProp[1][1].asString());
        label = (Label) totalPane.getChildren().get(7);
        label.textProperty().bind(UnitMatrixProp[1][2].asString());
        label = (Label) totalPane.getChildren().get(8);
        label.textProperty().bind(UnitMatrixProp[1][3].asString());
        label = (Label) totalPane.getChildren().get(9);
        label.textProperty().bind(UnitMatrixProp[2][0].asString());
        label = (Label) totalPane.getChildren().get(10);
        label.textProperty().bind(UnitMatrixProp[2][1].asString());
        label = (Label) totalPane.getChildren().get(11);
        label.textProperty().bind(UnitMatrixProp[2][2].asString());
        label = (Label) totalPane.getChildren().get(12);
        label.textProperty().bind(UnitMatrixProp[2][3].asString());;
    }

    //binds Model to the DrawPane
    private void bindModelToView() {
        for (int i = 0; i < 4; i++) {
            pivotPoint[i].bind(pivotPointTransProp[i][3]);
        }


        workingModel = new SimpleDoubleProperty[model.length][model[0].length];
        for (int i=0; i<model.length; i++) {
            for (int j=0; j<model[i].length; j++) {
                workingModel[i][j] = new SimpleDoubleProperty(model[i][j]);
            }
        }

        l01 = new Line();
        l12 = new Line();
        l20 = new Line();
        l03 = new Line();
        l13 = new Line();
        l23 = new Line();
        p1 = new Circle();
        p2 = new Circle();
        p3 = new Circle();
        p4 = new Circle();
        pPivot = new Circle();

        l01.startXProperty().bind(workingModel[0][0]);
        l01.startYProperty().bind(workingModel[0][1]);
        l01.endXProperty().bind(workingModel[1][0]);
        l01.endYProperty().bind(workingModel[1][1]);
        l12.startXProperty().bind(workingModel[1][0]);
        l12.startYProperty().bind(workingModel[1][1]);
        l12.endXProperty().bind(workingModel[2][0]);
        l12.endYProperty().bind(workingModel[2][1]);
        l20.startXProperty().bind(workingModel[2][0]);
        l20.startYProperty().bind(workingModel[2][1]);
        l20.endXProperty().bind(workingModel[0][0]);
        l20.endYProperty().bind(workingModel[0][1]);
        l03.startXProperty().bind(workingModel[0][0]);
        l03.startYProperty().bind(workingModel[0][1]);
        l03.endXProperty().bind(workingModel[3][0]);
        l03.endYProperty().bind(workingModel[3][1]);
        l13.startXProperty().bind(workingModel[1][0]);
        l13.startYProperty().bind(workingModel[1][1]);
        l13.endXProperty().bind(workingModel[3][0]);
        l13.endYProperty().bind(workingModel[3][1]);
        l23.startXProperty().bind(workingModel[2][0]);
        l23.startYProperty().bind(workingModel[2][1]);
        l23.endXProperty().bind(workingModel[3][0]);
        l23.endYProperty().bind(workingModel[3][1]);
        p1.centerXProperty().bind(workingModel[0][0]);
        p1.centerYProperty().bind(workingModel[0][1]);
        p2.centerXProperty().bind(workingModel[1][0]);
        p2.centerYProperty().bind(workingModel[1][1]);
        p3.centerXProperty().bind(workingModel[2][0]);
        p3.centerYProperty().bind(workingModel[2][1]);
        p4.centerXProperty().bind(workingModel[3][0]);
        p4.centerYProperty().bind(workingModel[3][1]);
        pPivot.centerXProperty().bind(pivotPoint[0]);
        pPivot.centerYProperty().bind(pivotPoint[1]);

        l01.setStroke(Color.ORANGE);
        l12.setStroke(Color.RED);
        l20.setStroke(Color.YELLOW);
        l03.setStroke(Color.BLACK);
        l13.setStroke(Color.BLUE);
        l23.setStroke(Color.GREEN);
        l01.setStrokeWidth(3);
        l12.setStrokeWidth(3);
        l20.setStrokeWidth(3);
        l03.setStrokeWidth(3);
        l13.setStrokeWidth(3);
        l23.setStrokeWidth(3);
        p1.setRadius(4);
        p2.setRadius(4);
        p3.setRadius(4);
        p4.setRadius(4);
        pPivot.setRadius(4);

        drawPane.getChildren().addAll(l01, l12, l20, l03, l13, l23, p1, p2, p3, p4, pPivot);
        applyTransformation();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

