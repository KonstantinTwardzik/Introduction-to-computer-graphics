package Testat02;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    private Pane drawPane;
    private CheckMenuItem hCurveMenu, hSplineNatMenu, hSplineParMenu, bCurveMenu, cSplineMenu;
    private Label PointCountLbl;
    private ArrayList<SplinePoint> pointList;
    private HermiteCurve hCurve;
    private HermiteSpline hSpline;
    private BezierCurve bCurve;
    private CatmullRomSpline cSpline;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initView();
        initLogic();
        Scene scene = new Scene(drawPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Splines - Computer Graphics");
        primaryStage.setWidth(1853);
        primaryStage.setHeight(1085);
        primaryStage.show();
    }

    private void initLogic() {
        pointList = new ArrayList<>();
        drawPane.setOnMousePressed(e -> createPoint(e.isControlDown(), e.getButton(), e.getX(), e.getY()));
        hCurve = new HermiteCurve(drawPane, pointList);
        hSpline = new HermiteSpline(drawPane, pointList);
        bCurve = new BezierCurve(drawPane, pointList);
        cSpline = new CatmullRomSpline(drawPane, pointList);
    }

    // Initiates the view
    private void initView() {

        //Initiate everything
        drawPane = new Pane();
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu options = new Menu("Options");
        Menu about = new Menu("Help");
        Menu hSplineMenu = new Menu("Hermite Spline");
        hCurveMenu = new CheckMenuItem("Hermite Curve");
        bCurveMenu = new CheckMenuItem("Bezier Curve");
        hSplineNatMenu = new CheckMenuItem("Natural");
        hSplineParMenu = new CheckMenuItem("Parabolic");
        cSplineMenu = new CheckMenuItem("Catmull-Rom Spline");
        PointCountLbl = new Label("Number of points: 0");

        // Connect everything
        menuBar.getMenus().addAll(file, options, about);
        hSplineMenu.getItems().addAll(hSplineNatMenu, hSplineParMenu);
        options.getItems().addAll(hCurveMenu, bCurveMenu, hSplineMenu, cSplineMenu);
        drawPane.getChildren().addAll(PointCountLbl, menuBar);

        // Set layout and design properties
        menuBar.setPrefWidth(2000);
        PointCountLbl.layoutYProperty().bind(drawPane.heightProperty().subtract(27));
        PointCountLbl.setLayoutX(10);

        // declare listener
        hCurveMenu.setOnAction(e -> hCurve.initiateCurve(hCurveMenu.isSelected()));
        hSplineNatMenu.setOnAction(e -> hSpline.initiateNatSpline(hSplineNatMenu.isSelected()));
        hSplineParMenu.setOnAction(e -> hSpline.initiateParSpline(hSplineParMenu.isSelected()));
        bCurveMenu.setOnAction(e -> bCurve.updateCurve(bCurveMenu.isSelected()));
        cSplineMenu.setOnAction(e -> cSpline.initiateSpline(cSplineMenu.isSelected()));
    }

    void updateSplines() {
        hSpline.updateNatSpline(hSplineNatMenu.isSelected());
        hSpline.updateParSpline(hSplineParMenu.isSelected());
        bCurve.updateCurve(bCurveMenu.isSelected());
        cSpline.initiateSpline(cSplineMenu.isSelected());
    }

    private void setLabel(String text) {
        PointCountLbl.setText(text);
    }

    private void createPoint(boolean strg, MouseButton button, double xPos, double yPos) {
        if (button == MouseButton.PRIMARY && strg) {
            SplinePoint p = new SplinePoint(xPos, yPos, drawPane, this);
            pointList.add(p);
            setLabel("Number of points: " + pointList.size());
            initiateSplines();
        }
    }

    void deletePoint(SplinePoint p) {
        pointList.remove(p);
        setLabel("Number of points: " + pointList.size());
        initiateSplines();
    }

    private void initiateSplines() {
        hCurve.initiateCurve(hCurveMenu.isSelected());
        hSpline.initiateNatSpline(hSplineNatMenu.isSelected());
        hSpline.initiateParSpline(hSplineParMenu.isSelected());
        bCurve.updateCurve(bCurveMenu.isSelected());
        cSpline.initiateSpline(cSplineMenu.isSelected());
    }


}
