/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import server.config.Config;

/**
 *
 * @author Loli
 */
public class Log {

    private Tab tab = new Tab();
    public static TextArea ta = new TextArea();

    public static String a = "Loli";

    public Log() {
        tab.setText("Log");
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setPadding(new Insets(5, 5, 5, 5));

        ta.setMinSize(UIRoot.SCREEN_WIDTH - 10, UIRoot.SCREEN_HEIGHT - 40);
        ta.setMaxSize(1920, 1080);
        gp.add(ta, 0, 0);

        tab.setContent(gp);

//        update();
    }

    void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        ta.appendText("ok" + "\n");
//                        println("ok");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }

    public static void println(String s) {
//        System.out.println("ok");
        ta.appendText(s + "\n");
    }

    public Tab getTab() {
        return tab;
    }
}
