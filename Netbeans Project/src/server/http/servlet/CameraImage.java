/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.http.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Loli
 */
public class CameraImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpg");

        String id = req.getParameter("id");
        String location = req.getParameter("location");

        byte[] b = (byte[]) UploadCameraImage.IMGS.get(location + id);

        if (b != null && b.length > 0) {
            resp.setContentType("image/jpg");
            resp.getOutputStream().write(b);
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        }
    }

}
