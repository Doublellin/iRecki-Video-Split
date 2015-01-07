/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.http.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Loli
 */
public class UploadCameraImage  extends HttpServlet {

    public static HashMap IMGS = new HashMap();

    @Override
//    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String location = req.getParameter("location");

        HttpServletRequest r = (HttpServletRequest) req;

        Part p = r.getPart("file");

        InputStream is = p.getInputStream();

        byte[] b = new byte[(int) p.getSize()];

        is.read(b, 0, b.length);

        IMGS.put(location + id, b);

//        System.out.println("Camera : "+location + id);
//        System.out.println(b.length);
//        FileOutputStream fos = new FileOutputStream("1.jpg");
//        fos.write(b);
//        fos.close();
//        File f = new File("1.jpg");
//        FileStream fs;
////        String fileName = extractFileName(p);
//        p.write(id + "_Camera.jpg");
    }
}
