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
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import server.database.SQL;

/**
 *
 * @author Loli
 */
public class GetTrade extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");

        String id = req.getParameter("id");
        String is_facebook_user = req.getParameter("isFBUser");

        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        Map m = new HashMap();

        try (ResultSet rs = new SQL().getData("SELECT payment_date, payment_type, trade_amt, trade_date, merchant_trade_no, trade_no, rtn_msg  FROM trade WHERE id='" + id + "' AND is_facebook_user=" + is_facebook_user + ";")) {
            while (rs.next()) {
                m.put("payment_date", rs.getString("payment_date"));
                m.put("payment_type", rs.getString("payment_type"));
                m.put("trade_amt", rs.getString("trade_amt"));
                m.put("trade_date", rs.getString("trade_date"));
                m.put("merchant_trade_no", rs.getString("merchant_trade_no"));
                m.put("trade_no", rs.getString("trade_no"));
                m.put("rtn_msg", rs.getString("rtn_msg"));

                list.put(m);
                m.clear();
            }
            try (PrintWriter out = res.getWriter()) {
                obj.put("Data", list);
                out.print(obj.toString(2));
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
