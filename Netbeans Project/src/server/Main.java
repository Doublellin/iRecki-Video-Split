package server;

import server.http.HttpServer;
import java.io.File;
import java.net.InetAddress;
import server.config.Config;
import server.tcp.SynchronousPlayback;

public class Main {

    public static String PATH = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {
        Init();
        new Thread(new Frame()).start();
        new Thread(new HttpServer()).start();
        new Thread(new SynchronousPlayback()).start();
        System.out.println(Config.HTTP_SERVER_IP);
        System.out.println(InetAddress.getLocalHost().getHostAddress());
    }

    static void Init() {
        new Config();

        File dir = new File("Data");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File dir2 = new File("Data/Facebook");
        if (!dir2.exists()) {
            dir2.mkdir();
        }
        File dir3 = new File("Data/iRecki");
        if (!dir3.exists()) {
            dir3.mkdir();
        }
                
    }

}
