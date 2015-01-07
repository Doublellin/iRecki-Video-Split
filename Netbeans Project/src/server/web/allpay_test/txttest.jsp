<%-- 
    Document   : txttest
    Created on : 2014/12/7, 下午 06:03:31
    Author     : user
--%>

<%@ page  contentType="text/html" pageEncoding="UTF-8" import = "java.util.*" import ="java.math.BigInteger" import ="java.security.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>

<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="javax.naming.*"%>
<%@ page import="javax.sql.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<% 
    
            FileWriter fw = null; //開檔
            BufferedWriter bw = null; //暫存檔
            try {
                File f = new File("test.txt");
                fw = new FileWriter(f, true); //預設是true~如資料夾中有資料會接續寫入。
                bw = new BufferedWriter(fw);
                bw.write("test");
                bw.close();
            }catch(IOException e) {
                 out.println(e.toString());
            } finally {
                
            }
            
    /*
        String path=application.getRealPath(request.getRequestURI());
        String dir=new File(path).getParent();
        out.println("當前JSP檔所在目錄的實體路徑"+dir);
        */
%>

