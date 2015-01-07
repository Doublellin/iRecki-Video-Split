package server;

import server.http.HttpServer;
import server.config.Config;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Frame implements Runnable {

    Container cp;
    JLabel labelServerStatus;
    JLabel labelServerIP;

    @Override
    public void run() {
        FrameInit();
         LabelServerIP();
        LabelServerPath();
        LabelServerStatus();
        ButtonServerStart();
        ButtonServerStop();
        ButtonOpenWeb();
        ButtonOpenPath();
    }

    void FrameInit() {
        JFrame f = new JFrame("影音串流伺服器 (陳大哥二案)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 600);
        f.setVisible(true);
        cp = f.getContentPane();
        cp.setLayout(null);
    }

    void LabelServerPath() {
        JLabel l = new JLabel("伺服器路徑：" + Main.PATH);
        l.setBounds(5, 30, 1000, 30);
        cp.add(l);
    }

    void LabelServerStatus() {
        labelServerStatus = new JLabel("伺服器狀態：已停止");
        labelServerStatus.setBounds(5, 60, 1000, 30);
        cp.add(labelServerStatus);
    }

    void LabelServerIP() {
        labelServerIP = new JLabel("IP：" + Config.HTTP_SERVER_IP + ":" + Config.HTTP_SERVER_PORT);
        labelServerIP.setBounds(5, 5, 1000, 30);
        cp.add(labelServerIP);
    }

    void ButtonServerStart() {
        JButton b = new JButton("啟動");
        b.setBounds(5, 100, 100, 30);
        cp.add(b);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpServer.SERVER.start();
                            Config.SERVER_IS_RUN = true;
                            labelServerStatus.setText("伺服器狀態：已啟動");
                            HttpServer.SERVER.join();
                        } catch (Exception ex) {
                        }
                    }
                }).start();
            }
        });
    }

    void ButtonServerStop() {
        JButton b = new JButton("停止");
        b.setBounds(150, 100, 100, 30);
        cp.add(b);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpServer.SERVER.stop();
                            Config.SERVER_IS_RUN = false;
                            labelServerStatus.setText("伺服器狀態：已停止");
                        } catch (Exception ex) {
                        }
                    }
                }).start();
            }
        });
    }

    void ButtonOpenWeb() {
        JButton b = new JButton("打開伺服器網頁");
        b.setBounds(5, 200, 150, 30);
        cp.add(b);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    Desktop.getDesktop().browse(new URL("http://127.0.0.1:" + Config.HTTP_SERVER_PORT).toURI());
                } catch (URISyntaxException | IOException ex) {
                }
            }
        });
    }

    void ButtonOpenPath() {
        JButton b = new JButton("打開伺服器路徑");
        b.setBounds(200, 200, 150, 30);
        cp.add(b);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    Desktop.getDesktop().browse(new URL("file://" + Main.PATH).toURI());
                } catch (URISyntaxException | IOException ex) {
                }
            }
        });
    }

}
