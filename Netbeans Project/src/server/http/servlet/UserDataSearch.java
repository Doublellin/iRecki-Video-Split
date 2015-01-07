/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.http.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import server.database.SQL;

/**
 *
 * @author Loli
 */
public class UserDataSearch  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String isFBUser = req.getParameter("isFBUser");
        String sid = req.getParameter("id");
        
        System.out.println(isFBUser + " , "+sid);

//        System.out.println(page);
        SQL sql = new SQL();

        String id;
        String is_facebook_user;
        String ip;
        String purchase_date;
        String price;
        String location;
        String playmode;
        String start_play_time;
        String end_play_time;
        String file_name;
        String file_size;
        String file_target_encod;
        String file_dir;
        String is_purchase_finish;
        String purchase_log;
        String is_upload_finish;
        String upload_log;
        String is_encoding_finish;
        String encoding_log;
        String choose_table_id_1;
        String choose_table_id_2;
        String choose_table_id_3;
        String choose_table_id_4;
        String choose_table_id_5;
        String choose_table_id_6;

        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        Map m = new HashMap();

        try {
            ResultSet rs = sql.getData("SELECT * FROM data WHERE id='"+sid+"' AND is_facebook_user="+isFBUser+"  ORDER BY start_play_time ASC;");

            while (rs.next()) {
                m.put("id", rs.getString("id"));
                m.put("is_facebook_user", rs.getString("is_facebook_user").equals("t") ? "Facebook" : "iRecki");
                m.put("ip", rs.getString("ip"));
                m.put("purchase_date", rs.getString("purchase_date"));
                m.put("price", rs.getString("price"));
                m.put("location", rs.getString("location"));
                m.put("playmode", rs.getString("playmode"));
                m.put("start_play_time", rs.getString("start_play_time"));
                m.put("end_play_time", rs.getString("end_play_time"));
                m.put("file_name", rs.getString("file_name"));
                m.put("file_size", rs.getString("file_size"));
                m.put("file_target_encod", rs.getString("file_target_encod"));
                m.put("file_dir", rs.getString("file_dir"));
                m.put("is_purchase_finish", rs.getString("is_purchase_finish"));
                m.put("purchase_log", rs.getString("purchase_log"));
                m.put("is_upload_finish", rs.getString("is_upload_finish"));
                m.put("upload_log", rs.getString("upload_log"));
                m.put("is_encoding_finish", rs.getString("is_encoding_finish"));
                m.put("encoding_log", rs.getString("encoding_log"));
                m.put("choose_table_id_1", rs.getString("choose_table_id_1"));
                m.put("choose_table_id_2", rs.getString("choose_table_id_2"));
                m.put("choose_table_id_3", rs.getString("choose_table_id_3"));
                m.put("choose_table_id_4", rs.getString("choose_table_id_4"));
                m.put("choose_table_id_5", rs.getString("choose_table_id_5"));
                m.put("choose_table_id_6", rs.getString("choose_table_id_6"));

                list.put(m);
                m.clear();

            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

        PrintWriter out = resp.getWriter();
        obj.put("Data", list);
        out.print(obj.toString());
        out.close();
    }

}
