package server.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import server.Main;
import server.config.Config;
import static server.config.Config.HTTP_SERVER_PUBLIC_IP;
import static server.config.Config.HTTP_SERVER_PRIVATE_IP;
import static server.config.Config.HTTP_SERVER_PORT;
import server.http.HttpServer;

public final class Home {

    private final Tab tab = new Tab();
    private final GridPane gp = new GridPane();

    private Label serverStatus;

    public Home() {
        tab.setText("Home");
        gp.getStylesheets().add(UIRoot.class.getResource("Home.css").toExternalForm());
        gp.setPadding(new Insets(20, 20, 20, 20)); //填充邊界
        tab.setContent(gp);

        addComponent();
    }

    void addComponent() {
        label_PublicIP();
        label_PrivateIP();
        label_ServerState();

        button_StartServer();
        button_StptServer();
        button_OpenServerWeb();
        button_OpenServerPath();
    }

    void label_PublicIP() {
        gp.add(new Label("外部 IP：" + HTTP_SERVER_PUBLIC_IP + ":" + HTTP_SERVER_PORT), 0, 0, 1, 1);
    }

    void label_PrivateIP() {
        gp.add(new Label("內部 IP：" + HTTP_SERVER_PRIVATE_IP + ":" + HTTP_SERVER_PORT), 0, 1, 1, 1);
    }

    void label_ServerState() {
        serverStatus = new Label("伺服器狀態：已停止");
        gp.add(serverStatus, 0, 2, 1, 1);
    }

    void button_StartServer() {
        Button btn = new Button("啟動");
        gp.add(btn, 1, 2, 1, 1);

        btn.setOnAction(e -> {
            HttpServer.start();
            serverStatus.setText("伺服器狀態：已啟動");
        });
    }

    void button_StptServer() {
        Button btn = new Button("停止");
        gp.add(btn, 2, 2, 1, 1);

        btn.setOnAction(e -> {
            HttpServer.stop();
            serverStatus.setText("伺服器狀態：已停止");
        });
    }

    void button_OpenServerWeb() {
        Button btn = new Button("打開伺服器網頁");
        gp.add(btn, 0, 3, 1, 1);

        btn.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URL("http://127.0.0.1:" + Config.HTTP_SERVER_PORT).toURI());
            } catch (URISyntaxException | IOException ex) {
            }
        });
    }

    void button_OpenServerPath() {
        Button btn = new Button("打開伺服器路徑");
        gp.add(btn, 1, 3, 1, 1);

        btn.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URL("file://" + Main.PATH).toURI());
            } catch (URISyntaxException | IOException ex) {
            }
        });
    }

    public Tab getTab() {
        return tab;
    }

}
