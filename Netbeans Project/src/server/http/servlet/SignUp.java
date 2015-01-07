package server.http.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.database.SQL;

public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String id = req.getParameter("id");
        String IsFacebookUser = req.getParameter("IsFacebookUser");
        String name = req.getParameter("name");
        String user_name = req.getParameter("user_name");
        String password = req.getParameter("password");

        if (IsFacebookUser.equals("false")) {
            SQL sql = new SQL();
            try {

//                id =  new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
                id = user_name;

                String s
                        = "INSERT INTO users ("
                        + "id, "
                        + "password, "
                        + "is_facebook_user, "
                        + "name, "
                        + "sign_up_date, "
                        + "sign_up_ip, "
                        + "point"
                        + ") VALUES ("
                        + "'" + id + "', "
                        + "'" + password + "', "
                        + "false, "
                        + "'" + name + "', "
                        + "now(), "
                        + "'" + req.getRemoteAddr() + ":" + req.getRemotePort() + "', "
                        + "0"
                        + ") ; ";
                sql.setData(s);

                res.getWriter().print("pass");

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                res.getWriter().print("error");
            }
        } else {
            SQL sql = new SQL();
            try {
                ResultSet rs = sql.getData("SELECT id FROM users WHERE id='" + id + "' AND is_facebook_user='" + IsFacebookUser + "' ; ");
                if (!rs.next()) {
                    rs.close();
                    String s
                            = "INSERT INTO users ("
                            + "id, "
                            + "is_facebook_user, "
                            + "name, "
                            + "sign_up_date, "
                            + "sign_up_ip, "
                            + "point"
                            + ") VALUES ("
                            + "'" + id + "',"
                            + "'" + IsFacebookUser + "',"
                            + "'" + name + "',"
                            + "now(),"
                            + "'" + req.getRemoteAddr() + ":" + req.getRemotePort() + "',"
                            + "0"
                            + ") ; ";
                    sql.setData(s);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                System.out.println(ex);
            }
        }

    }
}
