//package Testat03;
//
//import Testat03.Utils.Matrix;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.Slider;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//
//public class ModifierWindow extends GridPane {
//
//    private Stage stage;
//    private Slider xPosSld, yPosSld, zPosSld, xRotSld, yRotSld, zRotSld, scaleSld, xCntrSld, yCntrSld, zCntrSld;
//    private Label xPosLbl, yPosLbl, zPosLbl, xRotLbl, yRotLbl, zRotLbl, scaleLbl, xCntrLbl, yCntrLbl, zCntrLbl;
//    private double[][] UnitMatrix, translation, totalRotation, yRotation, zRotation, scaling, negativePivotPointTranslation, pivotPointTranslation, model, xRotation;
//    private SimpleDoubleProperty[][] workingModel, UnitMatrixProp, translationProp, xRotationProp, yRotationProp, zRotationProp, scalingProp, pivotPointTransProp, negativePivotPointTransProp, totalRotProp;
//    private SimpleDoubleProperty[] pivotPoint;
//
//    public void show() {
//        stage.show();
//    }
//
//    public void initView() {
//        stage = new Stage();
//        Scene scene = new Scene(this, 150, 800);
//        initLogic();
//        initInternView();
//        stage.setScene(scene);
//        stage.setTitle("Circle_2D transformation");
//        stage.show();
//    }
//
//    // initiates all matrices and points
//    private void initLogic() {
//        pivotPoint = new SimpleDoubleProperty[4];
//        pivotPoint[0] = new SimpleDoubleProperty(0.0);
//        pivotPoint[1] = new SimpleDoubleProperty(0.0);
//        pivotPoint[2] = new SimpleDoubleProperty(0.0);
//        pivotPoint[3] = new SimpleDoubleProperty(1.0);
//
//        model = new double[][]{
//                {-25.0, 20.0, -20.0, 1.0},
//                {25.0, 20.0, -20.0, 1.0},
//                {0.0, 20.0, 20.0, 1.0},
//                {0.0, -20.0, 0.0, 1.0}
//        };
//
//        UnitMatrix = new double[][]{
//                {1.0, 0.0, 0.0, 0.0},
//                {0.0, 1.0, 0.0, 0.0},
//                {0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        UnitMatrixProp = new SimpleDoubleProperty[UnitMatrix.length][UnitMatrix[0].length];
//        for (int i = 0; i < UnitMatrix.length; i++) {
//            for (int j = 0; j < UnitMatrix[i].length; j++) {
//                UnitMatrixProp[i][j] = new SimpleDoubleProperty(UnitMatrix[i][j]);
//            }
//        }
//        translation = new double[][]{
//                {1.0, 0.0, 0.0, 600.0},
//                {0.0, 1.0, 0.0, 500.0},
//                {0.0, 0.0, 1.0, 500.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        translationProp = new SimpleDoubleProperty[translation.length][translation[0].length];
//        for (int i = 0; i < translation.length; i++) {
//            for (int j = 0; j < translation[i].length; j++) {
//                translationProp[i][j] = new SimpleDoubleProperty(translation[i][j]);
//            }
//        }
//        double sin = Math.sin(Math.toRadians(0.0));
//        double cos = Math.cos(Math.toRadians(0.0));
//        xRotation = new double[][]{
//                {1.0, 0.0, 0.0, 0.0},
//                {0.0, cos, -sin, 0.0},
//                {0.0, sin, cos, 0.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        xRotationProp = new SimpleDoubleProperty[xRotation.length][xRotation[0].length];
//        for (int i = 0; i < xRotation.length; i++) {
//            for (int j = 0; j < xRotation[i].length; j++) {
//                xRotationProp[i][j] = new SimpleDoubleProperty(xRotation[i][j]);
//            }
//        }
//        yRotation = new double[][]{
//                {cos, 0.0, -sin, 0.0},
//                {0.0, 1.0, 0.0, 0.0},
//                {sin, 0.0, cos, 0.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        yRotationProp = new SimpleDoubleProperty[yRotation.length][yRotation[0].length];
//        for (int i = 0; i < yRotation.length; i++) {
//            for (int j = 0; j < yRotation[i].length; j++) {
//                yRotationProp[i][j] = new SimpleDoubleProperty(yRotation[i][j]);
//            }
//        }
//        zRotation = new double[][]{
//                {cos, -sin, 0.0, 0.0},
//                {sin, cos, 0.0, 0.0},
//                {0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        zRotationProp = new SimpleDoubleProperty[zRotation.length][zRotation[0].length];
//        for (int i = 0; i < zRotation.length; i++) {
//            for (int j = 0; j < zRotation[i].length; j++) {
//                zRotationProp[i][j] = new SimpleDoubleProperty(zRotation[i][j]);
//            }
//        }
//        scaling = new double[][]{
//                {1.0, 0.0, 0.0, 0.0},
//                {0.0, 1.0, 0.0, 0.0},
//                {0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        scalingProp = new SimpleDoubleProperty[scaling.length][scaling[0].length];
//        for (int i = 0; i < scaling.length; i++) {
//            for (int j = 0; j < scaling[i].length; j++) {
//                scalingProp[i][j] = new SimpleDoubleProperty(scaling[i][j]);
//            }
//        }
//        pivotPointTranslation = new double[][]{
//                {1.0, 0.0, 0.0, 0.0},
//                {0.0, 1.0, 0.0, 0.0},
//                {0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        pivotPointTransProp = new SimpleDoubleProperty[pivotPointTranslation.length][pivotPointTranslation[0].length];
//        for (int i = 0; i < pivotPointTranslation.length; i++) {
//            for (int j = 0; j < pivotPointTranslation[i].length; j++) {
//                pivotPointTransProp[i][j] = new SimpleDoubleProperty(pivotPointTranslation[i][j]);
//            }
//        }
//        negativePivotPointTranslation = new double[][]{
//                {1.0, 0.0, 0.0, 0.0},
//                {0.0, 1.0, 0.0, 0.0},
//                {0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        negativePivotPointTransProp = new SimpleDoubleProperty[negativePivotPointTranslation.length][negativePivotPointTranslation[0].length];
//        for (int i = 0; i < negativePivotPointTranslation.length; i++) {
//            for (int j = 0; j < negativePivotPointTranslation[i].length; j++) {
//                negativePivotPointTransProp[i][j] = new SimpleDoubleProperty(negativePivotPointTranslation[i][j]);
//            }
//        }
//        totalRotation = new double[][]{
//                {1.0, 0.0, 0.0, 0.0},
//                {0.0, 1.0, 0.0, 0.0},
//                {0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 0.0, 1.0}
//        };
//        totalRotProp = new SimpleDoubleProperty[totalRotation.length][totalRotation[0].length];
//        for (int i = 0; i < totalRotation.length; i++) {
//            for (int j = 0; j < totalRotation[i].length; j++) {
//                totalRotProp[i][j] = new SimpleDoubleProperty(totalRotation[i][j]);
//            }
//        }
//    }
//
//    // initiates the view (Labels, Matrix-views, Sliders) and applies Listeners
//    private void initInternView() {
//        //Sliders
//        xPosSld = new Slider(0, 1200, 600);
//        yPosSld = new Slider(0, 1200, 500);
//        zPosSld = new Slider(0, 1200, 500);
//        xRotSld = new Slider(0, 360, 0);
//        yRotSld = new Slider(0, 360, 0);
//        zRotSld = new Slider(0, 360, 0);
//        scaleSld = new Slider(0, 10, 1);
//        xCntrSld = new Slider(-500, 500, 0);
//        yCntrSld = new Slider(-500, 500, 0);
//        zCntrSld = new Slider(-500, 500, 0);
//        xPosLbl = new Label("X Position: 600");
//        yPosLbl = new Label("Y Position: 500");
//        zPosLbl = new Label("Z Position: 500");
//        xRotLbl = new Label("X Rotation: 0");
//        yRotLbl = new Label("Y Rotation: 0");
//        zRotLbl = new Label("Z Rotation: 0");
//        scaleLbl = new Label("Scale: 1");
//        xCntrLbl = new Label("X Center: 0");
//        yCntrLbl = new Label("Y Center: 0");
//        zCntrLbl = new Label("Z Center: 0");
//
//        xPosSld.setMajorTickUnit(200);
//        xPosSld.setMinorTickCount(5);
//        xPosSld.setShowTickLabels(true);
//        yPosSld.setMajorTickUnit(200);
//        yPosSld.setMinorTickCount(5);
//        yPosSld.setShowTickLabels(true);
//        zPosSld.setMajorTickUnit(200);
//        zPosSld.setMinorTickCount(5);
//        zPosSld.setShowTickLabels(true);
//        xRotSld.setMajorTickUnit(90);
//        xRotSld.setMinorTickCount(5);
//        xRotSld.setShowTickLabels(true);
//        yRotSld.setMajorTickUnit(90);
//        yRotSld.setMinorTickCount(5);
//        yRotSld.setShowTickLabels(true);
//        zRotSld.setMajorTickUnit(90);
//        zRotSld.setMinorTickCount(5);
//        zRotSld.setShowTickLabels(true);
//        scaleSld.setMajorTickUnit(5);
//        scaleSld.setMinorTickCount(5);
//        scaleSld.setShowTickLabels(true);
//        xCntrSld.setMajorTickUnit(500);
//        xCntrSld.setMinorTickCount(5);
//        xCntrSld.setShowTickLabels(true);
//        yCntrSld.setMajorTickUnit(500);
//        yCntrSld.setMinorTickCount(5);
//        yCntrSld.setShowTickLabels(true);
//        zCntrSld.setMajorTickUnit(500);
//        zCntrSld.setMinorTickCount(5);
//        zCntrSld.setShowTickLabels(true);
//        this.add(xPosSld, 0, 2);
//        this.add(yPosSld, 0, 4);
//        this.add(zPosSld, 0, 6);
//        this.add(xRotSld, 0, 8);
//        this.add(yRotSld, 0, 10);
//        this.add(zRotSld, 0, 12);
//        this.add(scaleSld, 0, 14);
//        this.add(xCntrSld, 0, 16);
//        this.add(yCntrSld, 0, 18);
//        this.add(zCntrSld, 0, 20);
//        this.add(xPosLbl, 0, 1);
//        this.add(yPosLbl, 0, 3);
//        this.add(zPosLbl, 0, 5);
//        this.add(xRotLbl, 0, 7);
//        this.add(yRotLbl, 0, 9);
//        this.add(zRotLbl, 0, 11);
//        this.add(scaleLbl, 0, 13);
//        this.add(xCntrLbl, 0, 15);
//        this.add(yCntrLbl, 0, 17);
//        this.add(zCntrLbl, 0, 19);
//
//        xPosLbl.setPrefSize(150, 120);
//        yPosLbl.setPrefSize(150, 120);
//        zPosLbl.setPrefSize(150, 120);
//        xRotLbl.setPrefSize(150, 120);
//        yRotLbl.setPrefSize(150, 120);
//        zRotLbl.setPrefSize(150, 120);
//        scaleLbl.setPrefSize(150, 120);
//        xCntrLbl.setPrefSize(150, 120);
//        yCntrLbl.setPrefSize(150, 120);
//        zCntrLbl.setPrefSize(150, 120);
//        xPosSld.setPrefSize(150, 120);
//        yPosSld.setPrefSize(150, 120);
//        zPosSld.setPrefSize(150, 120);
//        xRotSld.setPrefSize(150, 120);
//        yRotSld.setPrefSize(150, 120);
//        zRotSld.setPrefSize(150, 120);
//        scaleSld.setPrefSize(150, 120);
//        xCntrSld.setPrefSize(150, 120);
//        yCntrSld.setPrefSize(150, 120);
//        zCntrSld.setPrefSize(150, 120);
//
//        // Add everything to RootPane
//        this.setPadding(new Insets(5));
//        this.setHgap(5);
//        this.setVgap(5);
//        this.setPrefHeight(800);
//        this.setPrefWidth(150);
//
//        //Event handling
//        xPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
//            xPosLbl.setText("X Position: " + newValue.intValue());
//            updateTranslation(newValue.doubleValue(), yPosSld.getValue(), zPosSld.getValue());
//        });
//        yPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
//            yPosLbl.setText("Y Position: " + newValue.intValue());
//            updateTranslation(xPosSld.getValue(), newValue.doubleValue(), zPosSld.getValue());
//        });
//        zPosSld.valueProperty().addListener((ov, oldValue, newValue) -> {
//            zPosLbl.setText("Z Position: " + newValue.intValue());
//            updateTranslation(xPosSld.getValue(), yPosSld.getValue(), newValue.doubleValue());
//        });
//        xRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
//            xRotLbl.setText("X Rotation: " + newValue.intValue());
//            updateXRotation(newValue.doubleValue());
//        });
//        yRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
//            yRotLbl.setText("Y Rotation: " + newValue.intValue());
//            updateYRotation(newValue.doubleValue());
//        });
//        zRotSld.valueProperty().addListener((ov, oldValue, newValue) -> {
//            zRotLbl.setText("Z Rotation: " + newValue.intValue());
//            updateZRotation(newValue.doubleValue());
//        });
//        scaleSld.valueProperty().addListener((ov, oldValue, newValue) -> {
//            scaleLbl.setText("Scale: " + newValue.intValue());
//            updateScaling(newValue.doubleValue());
//        });
//        xCntrSld.valueProperty().addListener((observable, oldValue, newValue) -> {
//            xCntrLbl.setText("X Center Position: " + newValue.intValue());
//            updateCenterTranslation(newValue.doubleValue(), yCntrSld.getValue(), zCntrSld.getValue());
//        });
//        yCntrSld.valueProperty().addListener((observable, oldValue, newValue) -> {
//            yCntrLbl.setText("Y Center Position: " + newValue.intValue());
//            updateCenterTranslation(xCntrSld.getValue(), newValue.doubleValue(), zCntrSld.getValue());
//        });
//        zCntrSld.valueProperty().addListener((observable, oldValue, newValue) -> {
//            zCntrLbl.setText("Z Center Position: " + newValue.intValue());
//            updateCenterTranslation(xCntrSld.getValue(), yCntrSld.getValue(), newValue.doubleValue());
//        });
//    }
//
//    //updates Matrix
//    private void updateTranslation(double xTrans, double yTrans, double zTrans) {
//        translationProp[0][3].set(xTrans);
//        translationProp[1][3].set(yTrans);
//        translationProp[2][3].set(zTrans);
//        updateCenterTranslation(xCntrSld.getValue(), yCntrSld.getValue(), zCntrSld.getValue());
//    }
//
//    //updates Matrix
//    private void updateXRotation(double xRot) {
//        double sin = Math.sin(Math.toRadians(xRot));
//        double cos = Math.cos(Math.toRadians(xRot));
//        xRotationProp[1][1].set(cos);
//        xRotationProp[1][2].set(-sin);
//        xRotationProp[2][1].set(sin);
//        xRotationProp[2][2].set(cos);
//        applyTransformation();
//    }
//
//    //updates Matrix
//    private void updateYRotation(double yRot) {
//        double sin = Math.sin(Math.toRadians(yRot));
//        double cos = Math.cos(Math.toRadians(yRot));
//        yRotationProp[0][0].set(cos);
//        yRotationProp[0][2].set(-sin);
//        yRotationProp[2][0].set(sin);
//        yRotationProp[2][2].set(cos);
//        applyTransformation();
//    }
//
//    //updates Matrix
//    private void updateZRotation(double zRot) {
//        double sin = Math.sin(Math.toRadians(zRot));
//        double cos = Math.cos(Math.toRadians(zRot));
//        zRotationProp[0][0].set(cos);
//        zRotationProp[0][1].set(-sin);
//        zRotationProp[1][0].set(sin);
//        zRotationProp[1][1].set(cos);
//        applyTransformation();
//    }
//
//    //updates Matrix
//    private void updateScaling(double scale) {
//        scalingProp[0][0].set(scale);
//        scalingProp[1][1].set(scale);
//        scalingProp[2][2].set(scale);
//        applyTransformation();
//    }
//
//    //updates Matrices
//    private void updateCenterTranslation(double xCntrTrans, double yCntrTrans, double zCntrTrans) {
//        pivotPointTransProp[0][3].set(xCntrTrans + xPosSld.getValue());
//        pivotPointTransProp[1][3].set(yCntrTrans + yPosSld.getValue());
//        pivotPointTransProp[2][3].set(zCntrTrans + zPosSld.getValue());
//        negativePivotPointTransProp[0][3].set(-xCntrTrans - xPosSld.getValue());
//        negativePivotPointTransProp[1][3].set(-yCntrTrans - yPosSld.getValue());
//        negativePivotPointTransProp[2][3].set(-zCntrTrans - zPosSld.getValue());
//        applyTransformation();
//    }
//
//    //calculates the total matrix and applies it to the model
//    private void applyTransformation() {
//        // calculate Model
//        for (int i = 0; i < xRotation.length; i++) {
//            for (int j = 0; j < xRotation[i].length; j++) {
//                xRotation[i][j] = xRotationProp[i][j].getValue();
//                yRotation[i][j] = yRotationProp[i][j].getValue();
//                zRotation[i][j] = zRotationProp[i][j].getValue();
//                pivotPointTranslation[i][j] = pivotPointTransProp[i][j].getValue();
//                negativePivotPointTranslation[i][j] = negativePivotPointTransProp[i][j].getValue();
//                scaling[i][j] = scalingProp[i][j].getValue();
//                translation[i][j] = translationProp[i][j].getValue();
//            }
//        }
//        totalRotation = Matrix.matMult(xRotation, Matrix.matMult(yRotation, zRotation));
//
//        UnitMatrix = Matrix.matMult(pivotPointTranslation, Matrix.matMult(totalRotation, Matrix.matMult(scaling, Matrix.matMult(negativePivotPointTranslation, translation))));
//
//        // set Model
//        double helper[] = new double[workingModel[0].length];
//        for (int i = 0; i < workingModel.length; i++) {
//            helper = Matrix.matMult(UnitMatrix, model[i]);
//            for (int j = 0; j < workingModel[i].length; j++) {
//                workingModel[i][j].set(helper[j]);
//            }
//        }
//
//        for (int i = 0; i < UnitMatrix.length; i++) {
//            for (int j = 0; j < UnitMatrix[i].length; j++) {
//                UnitMatrixProp[i][j].set(UnitMatrix[i][j]);
//                totalRotProp[i][j].set(totalRotation[i][j]);
//            }
//        }
//
//    }
//
//
//}
//
