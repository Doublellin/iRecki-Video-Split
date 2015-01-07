package server.http.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import server.database.SQL;
import java.util.Arrays;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 500, // 10MB
        maxRequestSize = 1024 * 1024 * 500)   // 50MB

public class UploadFile extends HttpServlet {

    // getParameter ( )
    String isFBUser;
    String id;
    String location;
    String playMode;
    String year;
    String month;
    String day;
    String startHour;
    String endHour;
    String chooseTableID;
    String type;

    // InitPath ( )
    String userDir;
    String path;
    String fileDir;

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (PrintWriter out = resp.getWriter()) {
            try {
                getParameter(req);
                InitPath();
                debug();
                if (type.equals("Image")) {
                    uploadImage(req, resp);
                } else if (type.equals("Video")) {
                    uploadVideo(req, resp);
                }
                setPoint();
                out.print("pass");
            } catch (Exception e) {
                out.print("error");
            }
            System.out.println("Upload  Finish !!");
        }

    }

    void getParameter(HttpServletRequest req) {
        isFBUser = req.getParameter("isFBUser");
        id = req.getParameter("id");
        location = req.getParameter("location");
        playMode = req.getParameter("playMode");
        year = req.getParameter("year");
        month = req.getParameter("month");
        day = req.getParameter("day");
        startHour = req.getParameter("startHour");
        endHour = req.getParameter("endHour");
        chooseTableID = req.getParameter("chooseTableID");
        type = req.getParameter("type");
    }

    void debug() {
        System.out.println("isFBUser : " + isFBUser);
        System.out.println("id : " + id);
        System.out.println("location : " + location);
        System.out.println("playMode : " + playMode);
        System.out.println("year : " + year);
        System.out.println("month : " + month);
        System.out.println("day : " + day);
        System.out.println("startHour : " + startHour);
        System.out.println("endHour : " + endHour);
        System.out.println("chooseTableID : " + chooseTableID);
        System.out.println("type : " + type);
    }

    void InitPath() {
        // 未來要處理相同時間相同ID或某個平板ID存在的情況
        userDir = "";

        if (isFBUser.equals("true")) {
            userDir = "Facebook";
        } else {
            userDir = "iRecki";
        }

        path = "Data/" + userDir + "/" + id;

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        int i = 1;
        File f;
        while (true) {
            f = new File(path + "/" + i);
            if (!f.exists()) {
                path = path + "/" + i;
                f.mkdir();
                break;
            }
            i++;
        }
        fileDir = "Data/" + userDir + "/" + id + "/" + i;
    }

    void uploadImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
            NumberFormatException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException {

        HttpServletRequest r = (HttpServletRequest) req;
        String fileNames = "";
        String fileExtension = "";
        String fileExtensions = "";
        int i = 1;

        for (Part p : r.getParts()) {
            String urlEncoderFileName = extractFileName(p);
            String fileName = java.net.URLDecoder.decode(urlEncoderFileName, "UTF-8");
            fileNames += fileName + ",";
            fileExtension = getExtension(fileName);
            fileExtensions += fileExtension + ",";
            p.write(path + "/" + i + "." + fileExtension);
            i++;
        }

        // 刪除後面的逗號
        fileNames = fileNames.substring(0, fileNames.length() - 1);
        fileExtensions = fileExtensions.substring(0, fileExtensions.length() - 1);

        saveToDataBase(fileNames, fileExtensions);
    }

    void uploadVideo(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, NumberFormatException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException {

        HttpServletRequest r = (HttpServletRequest) req;
        Part p = r.getPart("file");
        String fileName = extractFileName(p);

        // 未來要處理圖片延遲幾秒的情況、最多撥放幾張圖片
        p.write(path + "/" + "1." + getExtension(fileName));

        saveToDataBase(fileName, getExtension(fileName));
    }

    void saveToDataBase(String fileName, String fileExtension) throws NumberFormatException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException {

        String sql = "INSERT INTO data ("
                + "id, "
                + "is_facebook_user, "
                + "location, "
                + "playmode, "
                + "start_play_time, "
                + "end_play_time, "
                + "file_name, "
                + "file_target_encod, "
                + "file_dir, "
                + "choose_table_id_1, "
                + "choose_table_id_2, "
                + "choose_table_id_3, "
                + "choose_table_id_4, "
                + "choose_table_id_5, "
                + "choose_table_id_6  "
                + ") VALUES ("
                + "'" + id + "', "
                + "" + isFBUser + ", "
                + "'" + location + "', "
                + "'" + playMode + "', "
                + "timestamp '" + year + "-" + month + "-" + day + " " + startHour + ":00:00" + " ', "
                + "timestamp '" + year + "-" + month + "-" + day + " " + endHour + ":00:00" + " ', "
                + "'" + fileName + "', "
                + "'" + fileExtension + "', "
                + "'" + fileDir + "', "
                + TableIDSQL(chooseTableID)
                + ");";

        new SQL().setData(sql);

    }

    String TableIDSQL(String chooseTableID) {
        String[] stid = chooseTableID.split(",");

        int[] itid = new int[stid.length];

        for (int k = 0; k < stid.length; k++) {
            itid[k] = Integer.parseInt(stid[k]);
        }
        Arrays.sort(itid);

        System.out.println("----------------------");

        String tidsql = "";

        int z = 0;
        for (int k = 1; k <= 6; k++) {
            if (itid[z] == k) {
                tidsql += "true, ";
                if (z < itid.length - 1) {
                    z++;
                }
            } else {
                tidsql += "false, ";
            }
        }

        // 刪除後面的逗號
        tidsql = tidsql.substring(0, tidsql.length() - 2);

        return tidsql;
    }

    // 未來要處理沒有附檔名的情況
    // 未來要處理未支援格式的情況
    public static String getExtension(String fileName) {
        int startIndex = fileName.lastIndexOf(46) + 1;
        int endIndex = fileName.length();
        return fileName.substring(startIndex, endIndex);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    void setPoint() throws NumberFormatException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, SQLException {
        String sql = "UPDATE users SET point = point - 1 WHERE id = '" + id + "' AND is_facebook_user = " + isFBUser + ";";
        new SQL().setData(sql);
    }

}
