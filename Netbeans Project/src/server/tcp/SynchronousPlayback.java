package server.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.database.SQL;
//import static server.tcp.Synchronous.hsinchu;

public class SynchronousPlayback implements Runnable {

    @Override
    public void run() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(18081);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        System.out.println("伺服器已啟動...");

        new Thread(new Synchronous()).start();

        // Config.SERVER_IS_RUN
        while (true) {
            try {

                Socket s = ss.accept();
                Client c = new Client();
                c.s = s;
                new Thread(c).start();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

}

class Synchronous implements Runnable {

    public static HashMap taipei = new HashMap();
    public static HashMap hsinchu = new HashMap();

    String[] location = {"Taipei", "Hsinchu"};

    int i;
    
    @Override
    public void run() {

        while (true) {

//            System.out.println(getNow());

//            for (i = 0; i < location.length; i++) {
//                String sql = "SELECT * FROM data WHERE start_play_time = '" + getNow() + "' AND location='" + location[i] + "'   ;";
//                try {
//                    ResultSet rs = new SQL().getData(sql);
//                    while (rs.next()) {
//                        int count = 0;
//                        for (int k = 1; k <= 6; k++) {
//                            count += rs.getBoolean("choose_table_id_" + k) ? 1 : 0;
//                        }
//
//                        new Thread(new Runnable() {
//                            public void run() {
//                                while (true) {
//                                    if(location[i].equals("Taipei")){
//                                        
//                                    }else if(location[i].equals("Hsinchu")){
//                                        if(hsinchu.get("1") != null){
//                                        }
//                                    }
//                                    
//                                    
//                                    
//                                    
//                                    
//                                    PrintWriter w = (PrintWriter) taipei.get("1");
//                                    PrintWriter w2 = (PrintWriter) taipei.get("2");
//                                    w.println("y");
//                                    w2.println("y");
//                                    taipei.clear();
//                                    try {
//                                        Thread.sleep(3000);
//                                    } catch (InterruptedException ex) {
//                                    }
//                                }
//                            }
//                        }).start();
//                    }
//
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
//                    System.out.println(ex);
//                }
//            }

            if (taipei.size() == 2) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    System.err.println("# Synchronous Class : " + ex.getMessage());
                }
                PrintWriter w = (PrintWriter) taipei.get("1");
                PrintWriter w2 = (PrintWriter) taipei.get("2");
                w.println("y");
                w2.println("y");
                taipei.clear();
            }

//            System.out.println("----------------------------------- " + hsinchu.size() + " , " + (hsinchu.get("1") != null ? "1," : ",") + (hsinchu.get("2") != null ? "2," : ",") + (hsinchu.get("3") != null ? "3," : ",") + (hsinchu.get("4") != null ? "4," : ""));
            if (hsinchu.size() == 4) {

//                System.out.println("okokokokokokokokokokokokokokokoookokokokokokokokokokokokokokokokokokokokokokokok");
                PrintWriter w = (PrintWriter) hsinchu.get("1");
                PrintWriter w2 = (PrintWriter) hsinchu.get("2");
                PrintWriter w3 = (PrintWriter) hsinchu.get("3");
                PrintWriter w4 = (PrintWriter) hsinchu.get("4");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    System.err.println("# Synchronous Class : " + ex.getMessage());
                }
//                for (int i = 0; i < 1; i++) {
                w.println("y");
                w2.println("y");
                w3.println("y");
                w4.println("y");
//                }
                hsinchu.clear();
//                System.out.println("okokokokokokokokokokokokokokokoookokokokokokokokokokokokokokokokokokokokokokokok");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.err.println("# Synchronous Class : " + ex.getMessage());
            }
        }

    }

    String getNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR);
        return year + "-" + month + "-" + day + " " + hour + ":00:00";
    }

}

class Client implements Runnable {

    public Socket s;

    @Override
    public void run() {
        try {

            BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter w = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

            String line = r.readLine();

//            System.out.println("################ : " + line);
            String[] msg = line.split(",");

            String location = msg[0];
            String tableID = msg[1];

            if (location.equals("Taipei")) {
                Synchronous.taipei.put(tableID, w);
            } else if (location.equals("Hsinchu")) {
                Synchronous.hsinchu.put(tableID, w);
            }

            System.out.println("# Finish : " + tableID);
        } catch (IOException ex) {
            System.err.println("Client : " + ex.getMessage());
        }

    }

}
