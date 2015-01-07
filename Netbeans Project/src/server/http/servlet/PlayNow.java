/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.http.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import server.database.SQL;

/**
 *
 * @author Loli
 */
public class PlayNow  extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String choose_table_id = "No";
        try (PrintWriter out = res.getWriter()) {
//            String location = req.getParameter("location");
            choose_table_id = req.getParameter("choose_table_id");

            System.out.println("@ : " + choose_table_id);

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            String now = year + "-" + month + "-" + day + " " + (hour + 1) + ":00:00";
            String now2 = year + "-" + month + "-" + day + " " + (hour) + ":00:00";

//            System.out.println(now);
//            System.out.println(now2);
            ResultSet r = new SQL().getData("SELECT * FROM data WHERE "
                    + "start_play_time < timestamp '" + now + "' AND "
                    + "start_play_time >= timestamp '" + (now2) + "' AND "
                    + "choose_table_id_" + choose_table_id + "=true ; ");

            String msg = "";

            if (r.next()) {
                msg += r.getString("file_dir") + ";" + r.getString("file_target_encod") + ";" + r.getString("start_play_time") + ";" + r.getString("end_play_time");
            }

            out.print(msg);
        } catch (Exception ex) {
            System.err.println("# : "+choose_table_id+"..."+ex.getMessage());
        }
    }
}
