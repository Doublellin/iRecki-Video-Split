package server.ui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.application.Application;

public class UIRoot extends Application {

    public static int SCREEN_WIDTH = 500;
    public static int SCREEN_HEIGHT = 300;
    
    private Stage stage;
    private Group root;
    private Scene scene;
    private TabPane tp;
    private BorderPane bp;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        setInit();
        AddTab();
        uiSetting();
        closeListener();
    }

    private void setInit() {
        root = new Group();
        scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);
        tp = new TabPane();
        bp = new BorderPane();
    }

    private void AddTab() {
        tp.getTabs().add(new Home().getTab());
        tp.getTabs().add(new Log().getTab());
    }

    private void uiSetting() {        
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        bp.setCenter(tp);
        root.getChildren().add(bp);

        stage.setTitle("影音串流伺服器 (陳大哥二案)");
        stage.setScene(scene);
        stage.show();
    }

    private void closeListener() {
        stage.setOnCloseRequest(e -> {
            System.exit(1);
        });
    }

    public void display(String[] args) {
        launch(args);
    }
}
