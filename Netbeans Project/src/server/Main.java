package server;

import server.http.HttpServer;
import java.io.File;
import java.util.ArrayList;
import server.config.Config;
import server.tcp.SynchronousPlayback;
import server.ui.UIRoot;

public class Main {

    public static String PATH = System.getProperty("user.dir");
    
    public static ArrayList a = new ArrayList();

    public static void main(String[] args) throws Exception {

        Init();
        
        new Thread(new HttpServer()).start();
        new Thread(new SynchronousPlayback()).start();
        
        UIRoot r = new UIRoot();
        System.out.println("+++ ");
        r.display(args);
//        Log.ta.appendText("AAA");
        System.out.println("::: ");

//        System.out.println(Log.ta.toString());
        
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
