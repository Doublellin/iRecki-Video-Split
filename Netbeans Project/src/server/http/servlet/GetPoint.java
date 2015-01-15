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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.database.SQL;
import server.ui.Log;

/**
 *
 * @author Loli
 */
public class GetPoint extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        
        
        
        String id = request.getParameter("id");
        String IsFacebookUser = request.getParameter("IsFacebookUser");
        SQL sql = new SQL();
        try {
            ResultSet rs = sql.getData("SELECT point FROM users WHERE id = '" + id + "' AND is_facebook_user = " + IsFacebookUser + " ; ");
            if(rs.next()){
                try(PrintWriter out = resp.getWriter()){
                    out.print(rs.getString("point"));
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println(ex);
        }
    }
}
