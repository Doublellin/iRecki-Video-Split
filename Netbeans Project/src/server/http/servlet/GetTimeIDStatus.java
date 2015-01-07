/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.http.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import server.database.SQL;

/**
 *
 * @author Loli
 */
public class GetTimeIDStatus extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");

        String day = req.getParameter("day");
        String start_time = req.getParameter("start_time");
        String end_time = req.getParameter("end_time");
        String location = req.getParameter("location");

        int tableCount = 6;

        String sql = "SELECT ";

        for (int i = 1; i <= tableCount; i++) {
            sql += " choose_table_id_" + i + (i == tableCount ? " " : ",");
        }
        sql += " FROM data WHERE location = '" + location + "' AND start_play_time >= '" + day + " " + start_time + "' AND end_play_time <= '" + day + " " + end_time + "';";

//        System.out.println(sql);
        
        boolean[] canNotChoose = new boolean[tableCount];

        try (PrintWriter out = res.getWriter()) {
            try (ResultSet rs = new SQL().getData(sql)) {
                while (rs.next()) {
                    for (int i = 0; i < canNotChoose.length; i++) {
                        if (canNotChoose[i] == false) {
                            canNotChoose[i] = rs.getBoolean("choose_table_id_" + (i + 1));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            for (int i = 0; i < canNotChoose.length; i++) {
                out.print(canNotChoose[i] + (i == canNotChoose.length - 1 ? "" : ","));
            }
        }
    }
}
